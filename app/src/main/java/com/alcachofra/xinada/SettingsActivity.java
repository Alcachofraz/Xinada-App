package com.alcachofra.xinada;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.alcachofra.xinada.utils.Roles;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends XinadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.template(R.layout.activity_settings);

        RecyclerView recycler = findViewById(R.id.roles);
        AppCompatImageButton cancel = findViewById(R.id.cancel);

        AppCompatSpinner dropdown = findViewById(R.id.language);
        String[] items = new String[]{"English", "Português", "Español", "Français", "Italiano", "Pусский"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        int position = 0;
        switch (Xinada.getLanguage()) {
            case "en":
                position = 0;
                break;
            case "pt":
                position = 1;
                break;
            case "es":
                position = 2;
                break;
            case "fr":
                position = 3;
                break;
            case "it":
                position = 4;
                break;
            case "ru":
                position = 5;
                break;
        }
        dropdown.setSelection(position, false);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String lang = "en";
                switch (position) {
                    case 0:
                        lang = "en";
                        break;
                    case 1:
                        lang = "pt";
                        break;
                    case 2:
                        lang = "es";
                        break;
                    case 3:
                        lang = "fr";
                        break;
                    case 4:
                        lang = "it";
                        break;
                    case 5:
                        lang = "ru";
                        break;
                }
                Xinada.updateLanguage(lang);
                recycler.swapAdapter(new RoleListAdapter(Arrays.asList(Roles.Role.values())), true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Snackbar.make(getWindow().getDecorView().getRootView(), R.string.error, Snackbar.LENGTH_LONG)
                        .show();
            }
        });

        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setAdapter(new RoleListAdapter(Arrays.asList(Roles.Role.values())));

        cancel.setOnClickListener(v -> finish());
    }

    class RoleListAdapter extends RecyclerView.Adapter<SettingsActivity.RoleListAdapter.RoleViewHolder> {
        private final List<Roles.Role> list;
        StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold

        public RoleListAdapter(List<Roles.Role> list) {
            super();
            this.list = list;
        }

        class RoleViewHolder extends RecyclerView.ViewHolder {
            private final AppCompatCheckBox checkbox;

            public RoleViewHolder(ViewGroup container) {
                super(LayoutInflater.from(SettingsActivity.this).inflate(R.layout.checkbox_text, container, false));
                checkbox = itemView.findViewById(R.id.checkbox);
            }
        }

        @NonNull
        @Override
        public SettingsActivity.RoleListAdapter.RoleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SettingsActivity.RoleListAdapter.RoleViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SettingsActivity.RoleListAdapter.RoleViewHolder holder, int position) {
            Roles.Role role = list.get(position);

            SpannableStringBuilder aux = new SpannableStringBuilder(role.getName() + ":  " + role.getDescription());
            aux.setSpan(bold, 0, role.getName().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            aux.setSpan(role.colorSpan, 0, role.getName().length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.checkbox.append(aux);

            holder.checkbox.setText(aux);
            holder.checkbox.setChecked(role.isChecked());

            holder.checkbox.setOnClickListener(v -> {
                if (role.getName().equals(getString(R.string.cop)) || role.getName().equals(getString(R.string.murderer))) {
                    holder.checkbox.setChecked(true);
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.cannot_uncheck_role, Snackbar.LENGTH_LONG)
                            .show();
                }
                else if (Roles.getCheckedRoles().size() <= 3) {
                    holder.checkbox.setChecked(true);
                    Snackbar.make(getWindow().getDecorView().getRootView(), R.string.at_least_three_roles, Snackbar.LENGTH_LONG)
                            .show();
                }
                role.setChecked(holder.checkbox.isChecked());
            });
        }

        @Override
        public int getItemCount() {
            return this.list.size();
        }

        public List<Roles.Role> getItems() {
            return list;
        }
    }
}