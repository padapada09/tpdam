package utn.frsf.tpdam.activities.Notes;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import utn.frsf.tpdam.R;
import utn.frsf.tpdam.activities.Login;

public class NotesAdapater extends RecyclerView.Adapter<NotesViewHolder> {
    private ArrayList<Note> notes;
    private NotesAdapater self = this;
    private DatabaseReference notesDatabase;
    private GoogleSignInAccount user;

    public NotesAdapater(AppCompatActivity context, ArrayList<Note> notes) {
        this.notes = notes;
        user = GoogleSignIn.getLastSignedInAccount(context);
        if (user == null) context.startActivity(new Intent(context, Login.class));
        notesDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getId()).child("notes");
        notesDatabase.addListenerForSingleValueEvent(new OnInitialLoading());
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_view, parent, false);

        NotesViewHolder viewHolder = new NotesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.setNote(this.notes.get(position));
        holder.setOnRemove(onRemove(holder));
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public View.OnClickListener onRemove (final NotesViewHolder viewHolder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesDatabase.child(notes.get(viewHolder.getAdapterPosition()).id).setValue(null).isSuccessful();
                notes.remove(viewHolder.getAdapterPosition());
                self.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };
    }

    public View.OnClickListener onAdd (final EditText newNoteInput) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNoteId = notesDatabase.push().getKey();
                String newNoteMessage = newNoteInput.getText().toString();
                Note newNote = new Note(newNoteId,newNoteMessage);
                notes.add(newNote);
                notesDatabase.child(newNoteId).setValue(newNoteMessage);
                self.notifyItemInserted(notes.size() - 1);
                newNoteInput.setText("");
                newNoteInput.clearFocus();
            }
        };
    };

    private class OnInitialLoading implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot data : snapshot.getChildren()) {
                Note newNote = new Note(data.getKey(),data.getValue(String.class));
                notes.add(newNote);
                self.notifyItemInserted(notes.size() - 1);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}
