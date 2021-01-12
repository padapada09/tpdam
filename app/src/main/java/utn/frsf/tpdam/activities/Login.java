package utn.frsf.tpdam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import utn.frsf.tpdam.R;
import utn.frsf.tpdam.activities.Notes.Notes;

public class Login extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        errorMsg = findViewById(R.id.errorMsg);
    }

    public void onLogin(View v) {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
//        if (!email.equals("email@email.com") || !password.equals("password")) errorMsg.setText(R.string.loginErrorMsg);
//        else {
//            errorMsg.setText("");
            startActivity(new Intent(this, Notes.class));
//        }
    };
}