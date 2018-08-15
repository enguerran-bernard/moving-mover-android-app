package com.movingmover.oem.movingmover;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private Button mLoginButton;
    private EditText mPasswordEditText;
    private Button mConnectionButton;

    private View.OnClickListener mConnectionButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, IASelectionActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginButton = findViewById(R.id.login_button);
        mConnectionButton = findViewById(R.id.connection_button);
        mConnectionButton.setOnClickListener(mConnectionButtonListener);
    }


}
