package utn.frsf.tpdam.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import utn.frsf.tpdam.R;
import utn.frsf.tpdam.activities.Notes.Notes;

public class Login extends AppCompatActivity {
    private final int LOGIN_RESULT = 3;
    private EditText emailInput;
    private EditText passwordInput;
    private TextView errorMsg;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private SignInButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new OnLogin());
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            Log.i("INFO",user.toString());
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.googleId))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private class OnLogin implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivityForResult(googleSignInClient.getSignInIntent(),LOGIN_RESULT);
        };
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_RESULT) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.i("INFO", String.format("Account logued: %s",account.getEmail()));
                startActivity(new Intent(this, Notes.class));
            } catch (ApiException e) {
                Log.w("WARNING",String.format("We had a problem %s",e.toString()));
            }
        }
    }
}