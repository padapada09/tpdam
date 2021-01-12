package utn.frsf.tpdam.activities.Notes;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import utn.frsf.tpdam.R;

public class NotesList {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManger;
    private NotesAdapater adapter;
    private ArrayList<String> notes;
    private AppCompatActivity context;

    public NotesList (AppCompatActivity context) {
        this.context = context;
        this.recyclerView = this.context.findViewById(R.id.notesList);
        this.notes = new ArrayList<String>();
        this.layoutManger = new LinearLayoutManager(context);
        this.adapter = new NotesAdapater(notes);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(this.layoutManger);
        this.recyclerView.setAdapter(this.adapter);
    };

    public void add(String newNote) {
        this.notes.add(newNote);
        this.adapter.notifyItemInserted(this.notes.size() - 1);
    };
}
