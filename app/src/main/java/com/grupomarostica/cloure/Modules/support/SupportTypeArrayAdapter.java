package com.grupomarostica.cloure.Modules.support;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.grupomarostica.cloure.R;

import java.util.ArrayList;

public class SupportTypeArrayAdapter extends ArrayAdapter<SupportType> {
    private final Context context;
    private final ArrayList<SupportType> values;

    public SupportTypeArrayAdapter(Context context, ArrayList<SupportType> values){
        super(context, R.layout.adapter_single_row, values);

        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SupportType register = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_single_row, parent, false);

        TextView lbl_nombre = rowView.findViewById(R.id.single_adapter_label);
        lbl_nombre.setText(register.Title);

        return rowView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SupportType register = values.get(position);

        LayoutInflater mInflater = LayoutInflater.from(context);;
        final View view = mInflater.inflate(R.layout.adapter_single_row, parent, false);

        TextView lbl_nombre = view.findViewById(R.id.single_adapter_label);
        lbl_nombre.setText(register.Title);

        return view;
    }
}