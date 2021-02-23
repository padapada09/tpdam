package utn.frsf.tpdam.activities.Notes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

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
    private String recordingPath;
    private boolean texting = false;
    private StorageReference storageReference;
    private TextView recorderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        recorder = new Recorder(this);
        recorder.setOnEndListener(new OnEndRecording());
        recorder.setOnStartListener(new OnStartRecording());
        notesList = new NotesList(this);
        firebaseAuth = FirebaseAuth.getInstance();
        messageInput = findViewById(R.id.messageInput);
        messageInput.addTextChangedListener(new OnEdit());
        addButton = findViewById(R.id.addButton);
        recorderText = findViewById(R.id.recorderText);
        addButton.setOnTouchListener(this.recorder.onTouch());
        storageReference = FirebaseStorage.getInstance().getReference();
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

    public class OnEdit implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { };

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { };

        @Override
        public void afterTextChanged(Editable s) {
            if (!s.toString().isEmpty()) {
                texting = true;
                self.addButton.setImageResource(R.drawable.send_icon);
                self.addButton.setOnClickListener(self.notesList.onAdd(messageInput,recordingPath,recorderText));
                self.addButton.setOnTouchListener(null);
            } else {
                texting = false;
                self.addButton.setImageResource(R.drawable.record_icon);
                self.addButton.setOnTouchListener(self.recorder.onTouch());
                self.addButton.setOnClickListener(null);
            }
        };
    };

    public class OnStartRecording implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            recorderText.setVisibility(View.VISIBLE);
            recorderText.setText("Grabando");
            recorderText.setTextColor(ContextCompat.getColor(self, R.color.red));
        };
    };

    public class OnEndRecording implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            recorderText.setTextColor(ContextCompat.getColor(self, R.color.white));
            if (recorder.getRecordingPath() != null) {
                Uri file = Uri.fromFile(new File(recorder.getRecordingPath()));

                storageReference.child(UUID.randomUUID().toString()).putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        recordingPath = uri.toString();
                                        recorderText.setText("Grabación guardada con éxito");
                                    }
                                });
                            };
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("ERROR",e.toString());
                                recorderText.setText("Error en la grabación");
                            }
                        });
            }
        };
    };
}