package utn.frsf.tpdam.activities.Notes;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import utn.frsf.tpdam.R;

public class Recorder {
    private AppCompatActivity context;
    private ConstraintLayout backdrop;
    private Button stopButton;
    private Recorder self = this;

    public Recorder(AppCompatActivity context) {
        this.context = context;
        this.backdrop = this.context.findViewById(R.id.recorderBackdrop);
        this.stopButton = this.context.findViewById(R.id.recorderStopButton);
        this.stopButton.setOnClickListener(new OnStop());
    };

    public void start() {
        this.backdrop.setVisibility(View.VISIBLE);
    };

    public void stop() {
        this.backdrop.setVisibility(View.GONE);
    }

    public class OnStop implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            self.stop();
        }
    }
}
