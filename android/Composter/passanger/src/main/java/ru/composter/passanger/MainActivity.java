package ru.composter.passanger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.composter.passanger.http.response.ProfileResponse;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Passanger";
    static final String INFO = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProfileResponse info = (ProfileResponse) getIntent().getSerializableExtra(INFO);
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                serverIntent.putExtra(INFO, info);
                startActivity(serverIntent);
            }
        });
    }
}

