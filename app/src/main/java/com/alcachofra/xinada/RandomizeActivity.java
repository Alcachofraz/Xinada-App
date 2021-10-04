package com.alcachofra.xinada;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;

import com.alcachofra.xinada.utils.Roles;

import java.util.Iterator;

public class RandomizeActivity extends XinadaActivity {

    String currentName;
    boolean leave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.template(R.layout.activity_randomize);

        AppCompatTextView name = findViewById(R.id.name);
        AppCompatTextView roleName = findViewById(R.id.roleName);
        AppCompatTextView roleDescription = findViewById(R.id.roleDescription);
        ContentLoadingProgressBar progressBar = findViewById(R.id.progressBar);
        AppCompatButton show = findViewById(R.id.show);
        AppCompatImageButton restart = findViewById(R.id.restart);

        // Hide role for now
        roleName.setVisibility(View.INVISIBLE);
        roleDescription.setVisibility(View.INVISIBLE);

        // Update variables
        progressBar.setMax(Game.PLAYERS);
        Iterator<String> nameIterator = Game.names.iterator();
        currentName = nameIterator.next();
        name.setText(getString(R.string.you_are_the, currentName));

        // When show button is clicked:
        show.setOnClickListener(v -> {
            if (leave) startActivity(new Intent(getApplicationContext(), HoldActivity.class));
            else {
                Roles.Role role = Game.getRole(currentName);
                if (roleName.getVisibility() == View.VISIBLE) { // If role is not hidden
                    name.setText(getString(R.string.you_are_the, currentName));

                    show.setText(R.string.show);
                    roleName.setVisibility(View.INVISIBLE);
                    roleDescription.setVisibility(View.INVISIBLE);

                    progressBar.incrementProgressBy(1);
                } else { // If role is hidden
                    SpannableStringBuilder aux = new SpannableStringBuilder(role.getName());
                    aux.setSpan(role.colorSpan, 0, role.getName().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    roleName.setText(aux);
                    roleDescription.setText(role.getDescription());

                    show.setText(R.string.next);
                    roleName.setVisibility(View.VISIBLE);
                    roleDescription.setVisibility(View.VISIBLE);

                    if (nameIterator.hasNext()) currentName = nameIterator.next();
                    else leave = true;
                }
            }
        });

        // Restart Draw
        restart.setOnClickListener(v -> {
            Game.round--;
            startActivity(new Intent(getApplicationContext(), StartRoundActivity.class));
        });
    }
}
