package utn.frsf.tpdam.activities.Notes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import utn.frsf.tpdam.R;

public class Notes extends AppCompatActivity {
    private NotesList notesList;
    private Notes self = this;
    private FloatingActionButton addButton;
    private EditText messageInput;
    private Recorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        notesList = new NotesList(this);
        addButton = findViewById(R.id.addButton);
        messageInput = findViewById(R.id.messageInput);
        messageInput.addTextChangedListener(new OnEdit());
        recorder = new Recorder(this);
    }

    public void addNote(View v) {
        this.notesList.add(this.messageInput.getText().toString());
        this.messageInput.setText("");
    };

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
                self.addButton.setImageResource(R.drawable.plus_icon);
            } else {
                self.addButton.setImageResource(R.drawable.record_icon);
            }
        };
    };
}