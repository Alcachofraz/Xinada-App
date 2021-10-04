package com.alcachofra.xinada;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.Set;

public class SetupNamesActivity extends XinadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.template(R.layout.activity_setup_names);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        AppCompatTextView playerNumber = findViewById(R.id.playerNumber);
        AppCompatEditText playerName = findViewById(R.id.playerName);
        AppCompatButton continueButton = findViewById(R.id.continueButton);

        // Update variables
        progressBar.setMax(Game.PLAYERS);
        Set<String> names = new HashSet<>();
        progressBar.setProgress(0);

        // When continueButton is pressed:
        continueButton.setOnClickListener(v -> {
            if (playerName.getText() == null) {
                // Output error:
                Snackbar.make(getWindow().getDecorView().getRootView(), R.string.error, Snackbar.LENGTH_LONG)
                        .show();
            }
            String name = playerName.getText().toString(); // Name input
            if (name.equals("")) { // If name is blank...
                // Output error:
                Snackbar.make(getWindow().getDecorView().getRootView(), R.string.invalid_name, Snackbar.LENGTH_LONG)
                        .show();
            }
            else { // Otherwise
                if (names.add(name)) {
                    progressBar.setProgress(names.size()); // Update progress bar
                    if (names.size() < Game.PLAYERS) { // If Array of strings end hasn't been reached...
                        playerNumber.setText(String.valueOf(names.size() + 1));
                        playerName.getText().clear();
                    } else { // If it has...
                        Game.setNames(names); // When leaving, make aux Global
                        startActivity(new Intent(getApplicationContext(), StartRoundActivity.class));
                    }
                }
                else {
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.name_already_set, Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
}
