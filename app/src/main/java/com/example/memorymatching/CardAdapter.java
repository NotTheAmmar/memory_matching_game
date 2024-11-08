package com.example.memorymatching;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<Card> {
    private boolean matchCard = false;
    private boolean locked = false;
    private int previousFlippedCardPos = -1;
    private ImageView previousFlipped;
    private final ArrayList<Card> cards;
    private boolean isInitialFlip = true;


    public CardAdapter(@NonNull Context context, ArrayList<Card> cards) {
        super(context, R.layout.card, cards);

        this.cards = cards;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.card, parent, false);
        }

        Card card = getItem(position);

        ImageView img = view.findViewById(R.id.Img);

        if (isInitialFlip) {
            img.setBackgroundColor(card.getColor());
        } else {
            img.setBackgroundColor(0);
        }

        img.setOnClickListener(v -> handleCardFlip(img, card, position));

        return view;
    }

    public void hideAllCards() {
        isInitialFlip = false;
        notifyDataSetChanged();
    }

    private void handleCardFlip(ImageView img, Card card, int position) {
        if (card.isMatched() || locked || previousFlippedCardPos == position || isInitialFlip)
            return;

        img.setBackgroundColor(card.getColor());

        if (matchCard) {
            locked = true;
            img.postDelayed(() -> {
                boolean result = card.match(getItem(previousFlippedCardPos));

                if (!result) {
                    img.setBackgroundColor(0);
                    previousFlipped.setBackgroundColor(0);
                } else {
                    checkForGameCompletion();
                }

                resetState();
            }, 500);
        } else {
            previousFlippedCardPos = position;
            previousFlipped = img;
            matchCard = true;
        }
    }

    private void resetState() {
        locked = false;
        matchCard = false;
        previousFlippedCardPos = -1;
        previousFlipped = null;
    }

    private void checkForGameCompletion() {
        boolean allMatched = true;
        for (Card card : cards) {
            if (!card.isMatched()) {
                allMatched = false;
                break;
            }
        }

        if (allMatched) {
            MainActivity.reset.setVisibility(View.VISIBLE);
        }
    }

}
