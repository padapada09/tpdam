package utn.frsf.tpdam.activities.Notes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import utn.frsf.tpdam.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private FloatingActionButton removeButton;
    private Note note;

    public NotesViewHolder(View view) {
        super(view);
        this.textView = view.findViewById(R.id.textView);
        this.removeButton = view.findViewById(R.id.removeButton);
    }

    public void setOnRemove(View.OnClickListener onRemove) {
        this.removeButton.setOnClickListener(onRemove);
    }

    public void setNote(Note note) {
        this.note = note;
        this.textView.setText(note.message);
    }
}
