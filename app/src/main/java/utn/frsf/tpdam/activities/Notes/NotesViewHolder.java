package utn.frsf.tpdam.activities.Notes;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import utn.frsf.tpdam.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private Button removeButton;

    public NotesViewHolder(View view) {
        super(view);
        this.textView = view.findViewById(R.id.textView);
        this.removeButton = view.findViewById(R.id.removeButton);
    }

    public void setOnRemove(View.OnClickListener onRemove) {
        this.removeButton.setOnClickListener(onRemove);
    }

    public void setText(String text) {
        this.textView.setText(text);
    }
}
