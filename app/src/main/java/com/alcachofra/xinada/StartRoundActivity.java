package com.alcachofra.xinada;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class StartRoundActivity extends XinadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.template(R.layout.activity_start_round);

        AppCompatTextView winner = findViewById(R.id.winner);
        AppCompatTextView scoreboard = findViewById(R.id.scoreboard);
        AppCompatButton begin = findViewById(R.id.begin);
        AppCompatButton end = findViewById(R.id.end);

        // Display points:
        for (Map.Entry<String, Integer> entry : Game.points.entrySet()) {
            final SpannableStringBuilder aux = new SpannableStringBuilder(entry.getKey() + ":  " + entry.getValue() + " " + Xinada.string(R.string.points) + "\n");

            final StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
            final ForegroundColorSpan red = new ForegroundColorSpan(getResources().getColor(R.color.primary)); // Span to make text red
            aux.setSpan(red, 0, entry.getKey().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            aux.setSpan(bold, 0, entry.getKey().length()+3+String.valueOf(entry.getValue()).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            scoreboard.append(aux);
        }

        if (Game.round >= Game.ROUNDS) {
            Collection<String> winners = findWinners();
            if (winners.isEmpty()) {
                winner.setText(R.string.error);
            }
            else if (winners.size() > 1) {
                winner.setText(R.string.draw);
            }
            else {
                winner.setText(getString(R.string.won, winners.iterator().next()));
            }
            Game.round = 1;
            Game.zero();
            Game.draw();
            begin.setText(R.string.play_again);
        }
        else {
            Game.round++;
            Game.draw();
            begin.setText(getString(R.string.start_round, Game.round));
        }

        begin.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RandomizeActivity.class)));

        end.setOnClickListener(v -> {
            Game.points.clear();
            Game.round = 0;
            startActivity(new Intent(getApplicationContext(), SetupRoundActivity.class));
        });
    }

    private Collection<String> findWinners() {
        int max = 0;
        Collection<String> ret = new HashSet<>();
        for (Map.Entry<String, Integer> entry : Game.points.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                ret.clear();
                ret.add(entry.getKey());
            }
            else if (entry.getValue() == max) {
                ret.add(entry.getKey());
            }
        }
        return ret;
    }
}