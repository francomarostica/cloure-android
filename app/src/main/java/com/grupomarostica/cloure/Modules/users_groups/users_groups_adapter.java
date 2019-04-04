package com.grupomarostica.cloure.Modules.users_groups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupomarostica.cloure.R;

import java.util.ArrayList;

public class users_groups_adapter extends ArrayAdapter<UserGroup> {
    private final Context context;
    private final ArrayList<UserGroup> values;

    public users_groups_adapter(Context context, ArrayList<UserGroup> values) {
        super(context, R.layout.row_single_adapter, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserGroup registro_tmp = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_single_adapter, parent, false);
        TextView lbl_nombre = rowView.findViewById(R.id.single_adapter_label);

        lbl_nombre.setText(registro_tmp.Name);
        return rowView;
    }
}
