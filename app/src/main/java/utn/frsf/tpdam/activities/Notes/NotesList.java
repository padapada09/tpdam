package utn.frsf.tpdam.activities.Notes;
import android.view.View;
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
    private ArrayList<Note> notes;
    private AppCompatActivity context;

    public NotesList (AppCompatActivity context) {
        this.context = context;
        this.recyclerView = this.context.findViewById(R.id.notesList);
        this.notes = new ArrayList<Note>();
        this.layoutManger = new LinearLayoutManager(context);
        this.adapter = new NotesAdapater(context,notes);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(this.layoutManger);
        this.recyclerView.setAdapter(this.adapter);
    };

    public View.OnClickListener onAdd(EditText newNoteInput) {
        return this.adapter.onAdd(newNoteInput);
    }
}
