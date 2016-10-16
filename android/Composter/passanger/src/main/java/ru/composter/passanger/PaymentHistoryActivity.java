package ru.composter.passanger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.composter.passanger.model.Payment;

/**
 * @author Sergey Elizarov (elizarov1988@gmail.com)
 *         on 16.10.16 11:42.
 */

public class PaymentHistoryActivity extends AppCompatActivity {
    private ListView list;
    private TextView empty;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);
        list = (ListView) findViewById(android.R.id.list);
        empty = (TextView) findViewById(android.R.id.empty);
        list.setEmptyView(empty);
        adapter = Payment.getAdapter(this, new ArrayList<Payment>());
        list.setAdapter(adapter);

    }

    private void refresh() {
        //TODO сделать метод, вызвать создание нового адаптера и установку его listView
    }
}
