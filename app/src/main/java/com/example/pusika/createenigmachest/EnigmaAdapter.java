package com.example.pusika.createenigmachest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EnigmaAdapter extends ArrayAdapter<Enigma> {

    private LayoutInflater inflater;
    private int layout;
    private List<Enigma> enigmas;

    public EnigmaAdapter(Context context, int resource, List<Enigma> enigmas) {
        super(context, resource, enigmas);
        this.enigmas = enigmas;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView word = view.findViewById(R.id.word);
        TextView describe = view.findViewById(R.id.describe);

        Enigma enigma = enigmas.get(position);

        word.setText(enigma.getWord());
        describe.setText(enigma.getDescribe());

        return view;
    }
}
