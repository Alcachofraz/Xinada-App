package com.alcachofra.xinada;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.SeekBar;

public class SetupRoundActivity extends XinadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.template(R.layout.activity_setup_round);

        AppCompatSeekBar playerNumberSeekBar = findViewById(R.id.playerNumberSeekBar);
        AppCompatSeekBar roundNumberSeekBar = findViewById(R.id.roundNumberSeekBar);
        AppCompatTextView playerNumber = findViewById(R.id.playerNumber);
        AppCompatTextView roundNumber = findViewById(R.id.roundNumber);
        AppCompatButton continueButton = findViewById(R.id.continueButton);
        AppCompatImageView gears = findViewById(R.id.gears);


        BroadcastReceiver languageChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                ((AppCompatTextView) findViewById(R.id.howManyRounds)).setText(R.string.how_many_rounds);
                ((AppCompatTextView) findViewById(R.id.howManyPlayers)).setText(R.string.how_many_players);
                ((AppCompatButton) findViewById(R.id.continueButton)).setText(R.string.proceed);
            }
        };
        // Register receiver
        registerReceiver(languageChangedReceiver, new IntentFilter("Language.changed"));


        playerNumberSeekBar.setProgress(6 - 3);
        roundNumberSeekBar.setProgress(5 - 1);

        // Listen to Seek Bar:
        playerNumberSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                playerNumber.setText(String.valueOf(progress + 3));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        roundNumberSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roundNumber.setText(String.valueOf(progress + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        gears.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SettingsActivity.class)));

        // If continueButton is pressed:
        continueButton.setOnClickListener(v -> {
            Game.PLAYERS = playerNumberSeekBar.getProgress() + 3;
            Game.ROUNDS = roundNumberSeekBar.getProgress() + 1;
            startActivity(new Intent(getApplicationContext(), SetupNamesActivity.class));
        });
    }
}