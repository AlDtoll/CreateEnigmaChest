package com.example.pusika.createenigmachest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnigmaTextActivity extends AppCompatActivity {

    EditText enigmaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enigma_text);
        enigmaText = findViewById(R.id.enigmaText);
        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            String enigma = arguments.get("enigma").toString();
            enigmaText.setText(enigma);
        }
    }

    public void copy(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", enigmaText.getText().toString());
        clipboard.setPrimaryClip(clip);
    }
}
