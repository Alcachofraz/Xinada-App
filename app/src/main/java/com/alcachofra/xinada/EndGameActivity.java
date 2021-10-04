package com.alcachofra.xinada;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alcachofra.xinada.utils.Roles;
import com.alcachofra.xinada.utils.Score;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EndGameActivity extends XinadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.template(R.layout.activity_end_game);

        RecyclerView recycler = findViewById(R.id.scoreboard);
        AppCompatButton continueButton = findViewById(R.id.continueButton);
        AppCompatTextView round = findViewById(R.id.round);

        round.setText(getString(R.string.round, Game.round));

        List<Score> scores = new ArrayList<>();

        for (Map.Entry<String, Roles.Role> entry : Game.roles.entrySet())
            scores.add(new Score(entry.getKey(), entry.getValue()));

        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setAdapter(new EndGameActivity.ScoreListAdapter(scores));

        // When continue button is clicked...
        continueButton.setOnClickListener(v -> {
            List<Score> list = ((ScoreListAdapter) Objects.requireNonNull(recycler.getAdapter())).getItems();
            boolean cond = false;
            for (Score score : list) {
                if (score.isChecked()) {
                    cond = true;
                    Game.addPoint(score.getName());
                }
            }
            if (cond) startActivity(new Intent(getApplicationContext(), StartRoundActivity.class));
            else {
                Snackbar.make(getWindow().getDecorView().getRootView(), R.string.someone_must_have_won, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ScoreViewHolder> {
        private final List<Score> list;
        final StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold

        public ScoreListAdapter(List<Score> list) {
            super();
            this.list = list;
        }

        class ScoreViewHolder extends RecyclerView.ViewHolder {
            private final AppCompatCheckBox checkbox;

            public ScoreViewHolder(ViewGroup container) {
                super(LayoutInflater.from(EndGameActivity.this).inflate(R.layout.checkbox_text, container, false));
                checkbox = itemView.findViewById(R.id.checkbox);
            }
        }

        @NonNull
        @Override
        public ScoreListAdapter.ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ScoreListAdapter.ScoreViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ScoreListAdapter.ScoreViewHolder holder, int position) {
            Score score = list.get(position);

            SpannableStringBuilder aux = new SpannableStringBuilder(score.getName() + " [" + score.getRole().getName() + "]");

            aux.setSpan( // Make the role bold
                    bold,
                    score.getName().length() + 1,
                    score.getName().length() + 2 + score.getRole().getName().length() + 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
            );

            aux.setSpan( // Make the role blue (Polícia)
                    score.getRole().colorSpan,
                    score.getName().length() + 1,
                    score.getName().length() + 2 + score.getRole().getName().length() + 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
            );

            holder.checkbox.setText(aux);
            holder.checkbox.setChecked(score.isChecked());

            holder.checkbox.setOnClickListener(v -> {
                if (holder.checkbox.isChecked()) score.check();
                else score.uncheck();
            });
        }

        @Override
        public int getItemCount() {
            return this.list.size();
        }

        public List<Score> getItems() {
            return list;
        }
    }
}
