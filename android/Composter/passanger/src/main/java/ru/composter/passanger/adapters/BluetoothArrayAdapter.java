package ru.composter.passanger.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ru.composter.passanger.DeviceListActivity;
import ru.composter.passanger.R;

public class BluetoothArrayAdapter extends ArrayAdapter<DeviceListActivity.Bluetooth> {

    public BluetoothArrayAdapter(Context context) {
        super(context, R.layout.bluetooth_list_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceListActivity.Bluetooth bluetooth = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.bluetooth_list_item, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        if (bluetooth.getName() != null) {
            name.setText(bluetooth.getName());
        } else {
            name.setText("Без названия");
        }
        return convertView;
    }
}
