package ru.composter.passanger.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.composter.passanger.R;
import ru.composter.passanger.model.User;
import ru.composter.passanger.utils.DateFormatUtils;

public class MainActivity extends Activity {

    static final String TAG = "Passanger";
    static final String INFO = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = User.getUserInfo(this);
        ((TextView) findViewById(R.id.balance)).setText(user.getBalance() + " Р на счёте");
        ((TextView) findViewById(R.id.last_balance_sync)).setText("Обновлено: " + DateFormatUtils.format(user.getTimestamp(), "dd.MM.yyyy hh:mm"));
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(serverIntent);
            }
        });
    }
}

