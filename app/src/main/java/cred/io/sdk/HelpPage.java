package cred.io.sdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import network.ChatAPI;

public class HelpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        ChatAPI chatAPI = new ChatAPI(HelpPage.this);

        Toast.makeText(getApplicationContext(), chatAPI.getTxnHistory(), Toast.LENGTH_LONG)
                .show();
    }
}
