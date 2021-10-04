package com.alcachofra.xinada;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

public class HoldActivity extends XinadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.template(R.layout.activity_hold);

        AppCompatButton revealButton = findViewById(R.id.reveal);
        AppCompatTextView round = findViewById(R.id.round);

        round.setText(getString(R.string.round, Game.round));

        // Reveal:
        revealButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EndGameActivity.class)));
    }
}
