package com.example.pusika.createenigmachest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Enigma> enigmas = new ArrayList();
    EnigmaAdapter adapter;

    ArrayList<Enigma> selectedEnigmas = new ArrayList();
    ListView enigmasList;

    EditText timeEditText;
    EditText enigmaWord;
    EditText enigmaDescribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        enigmasList = findViewById(R.id.enigmasList);
        timeEditText = findViewById(R.id.time);
        enigmaWord = findViewById(R.id.enigmaWord);
        enigmaDescribe = findViewById(R.id.enigmaDescribe);
        enigmaDescribe.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        add(v);
                        v.setFocusableInTouchMode(true);
                        enigmaWord.requestFocus();
                        return true;
                }
                return false;
            }
        });
        adapter = new EnigmaAdapter(this, R.layout.item, enigmas);
        enigmasList.setAdapter(adapter);

        // обработка установки и снятия отметки в списке
        final Context context = getApplicationContext();
        enigmasList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // получаем нажатый элемент
                Enigma enigma = adapter.getItem(position);
                if (enigmasList.isItemChecked(position)) {
                    selectedEnigmas.add(enigma);
                } else {
                    selectedEnigmas.remove(enigma);
                }
                Toast toast = Toast.makeText(context, enigma.getWord(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void add(View view) {
        String word = enigmaWord.getText().toString();
        String describe = enigmaDescribe.getText().toString();
        if (!word.isEmpty() && !enigmas.contains(word) && !enigmas.contains(describe)) {
            adapter.add(new Enigma(word, describe));
            enigmaWord.setText("");
            enigmaDescribe.setText("");
            adapter.notifyDataSetChanged();
        }
    }

    public void remove(View view) {
        // получаем и удаляем выделенные элементы
        for (int i = 0; i < selectedEnigmas.size(); i++) {
            adapter.remove(selectedEnigmas.get(i));
        }
        // снимаем все ранее установленные отметки
        enigmasList.clearChoices();
        // очищаем массив выбраных объектов
        selectedEnigmas.clear();

        adapter.notifyDataSetChanged();
    }

    public void createEnigmaChest(View view) {
        String enigma = encrypt();
        Intent intent = new Intent(this, EnigmaTextActivity.class);
        intent.putExtra("enigma", enigma);
        startActivity(intent);
    }

    private String encrypt() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < enigmas.size(); i++) {
            stringBuilder.append(enigmas.get(i).getWord())
                    .append("PART")
                    .append(enigmas.get(i).getDescribe())
                    .append("END");
        }
        // todo техническая информация
        String mode = "usual";
        stringBuilder.append("MODE:").append(mode).append("ROW");
        String time = String.valueOf(timeEditText.getText().toString());
        stringBuilder.append("TIME:").append(time).append("ROW");
        Date date = new Date();
        String key = date.toString();
        stringBuilder.append("KEY:").append(key).append("ROW");
        String string = stringBuilder.toString();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((int) chars[i] - 13);
        }
        return new String(chars);
    }
}
