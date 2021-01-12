package utn.frsf.tpdam.activities.Notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import utn.frsf.tpdam.R;

public class NotesAdapater extends RecyclerView.Adapter<NotesViewHolder> {
    private ArrayList<String> notes;
    private NotesAdapater self = this;

    public NotesAdapater(ArrayList<String> notes) {
        this.notes = notes;
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
        holder.setText(this.notes.get(position));
        holder.setOnRemove(new OnRemove(holder));
    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

    public class OnRemove implements View.OnClickListener {
        private NotesViewHolder viewHolder;

        public OnRemove(NotesViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        public void onClick(View v) {
            notes.remove(this.viewHolder.getAdapterPosition());
            self.notifyItemRemoved(this.viewHolder.getAdapterPosition());
        }
    }
}
