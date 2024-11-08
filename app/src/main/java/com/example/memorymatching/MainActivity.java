package com.example.memorymatching;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Difficulty difficulty = Difficulty.Easy;
    public static Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpSpinner();

        reset = findViewById(R.id.restartBtn);
        reset.setOnClickListener(v -> resetGame());

        resetGame();
    }

    private void setUpSpinner() {
        Spinner difficultyDropdown = findViewById(R.id.difficultySpinner);

        String[] items = {Difficulty.Easy.name(), Difficulty.Medium.name(), Difficulty.Hard.name()};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items);
        difficultyDropdown.setAdapter(spinnerAdapter);

        difficultyDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficulty = Difficulty.valueOf(items[position]);
                resetGame();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private int randomColor() {
        Random random = new Random();

        int red = random.nextInt(255) + 1;
        int green = random.nextInt(255) + 1;
        int blue = random.nextInt(255) + 1;


        return Color.rgb(red, green, blue);
    }

    private void resetGame() {
        reset.setVisibility(View.GONE);

        ArrayList<Card> cards = new ArrayList<>();

        int totalCards = difficulty.rows * difficulty.columns;
        for (int i = 0; i < totalCards / 2; i++) {
            int color = randomColor();
            cards.add(new Card(color));
            cards.add(new Card(color));
        }

        Collections.shuffle(cards);

        GridView cardView = findViewById(R.id.cardView);
        cardView.setNumColumns(difficulty.columns);

        CardAdapter adapter = new CardAdapter(this, cards);
        cardView.setAdapter(adapter);

        cardView.postDelayed(adapter::hideAllCards, 5000);
    }
}
