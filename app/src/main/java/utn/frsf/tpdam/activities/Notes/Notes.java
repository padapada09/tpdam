package utn.frsf.tpdam.activities.Notes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import utn.frsf.tpdam.R;
import utn.frsf.tpdam.activities.Login;

public class Notes extends AppCompatActivity {
    private NotesList notesList;
    private Notes self = this;
    private FloatingActionButton addButton;
    private EditText messageInput;
    private Recorder recorder;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        notesList = new NotesList(this);
        firebaseAuth = FirebaseAuth.getInstance();
        messageInput = findViewById(R.id.messageInput);
        messageInput.addTextChangedListener(new OnEdit());
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this.notesList.onAdd(messageInput));
        recorder = new Recorder(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.googleId))
                .requestEmail()
                .build();
        googleClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                // Google sign out
                googleClient.signOut().addOnCompleteListener(this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(self, Login.class));
                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void record(View v) {
        this.recorder.start();
    };

    public class OnEdit implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { };

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { };

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().isEmpty()) {
                self.addButton.setImageResource(R.drawable.send_icon);
            } else {
                self.addButton.setImageResource(R.drawable.record_icon);
            }
        };
    };

}