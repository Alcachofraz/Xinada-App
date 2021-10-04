package com.alcachofra.xinada;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.alcachofra.xinada.utils.Roles;

public class InfoActivity extends XinadaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.template(R.layout.activity_info);

        AppCompatTextView infoText = findViewById(R.id.infoText);
        AppCompatImageButton cancel = findViewById(R.id.cancel);

        for (Roles.Role role : Roles.Role.values()) {
            // Bold span
            StyleSpan bold = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold

            final SpannableStringBuilder aux = new SpannableStringBuilder("   " + role.getName() + ":  " + role.getDescription() + "\n");
            aux.setSpan(bold, 0, role.getName().length()+4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            aux.setSpan(role.colorSpan, 0, role.getName().length()+4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            infoText.append(aux);
        }

        cancel.setOnClickListener(v -> finish());
    }
}
