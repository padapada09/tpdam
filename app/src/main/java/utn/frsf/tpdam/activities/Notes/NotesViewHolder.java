package utn.frsf.tpdam.activities.Notes;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import utn.frsf.tpdam.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private FloatingActionButton removeButton;
    private FloatingActionButton shareButton;
    private Note note;
    private ImageButton playButton;
    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    private Timer playbackTimer;
    private AppCompatActivity context;
    private ConstraintLayout playbackControls;

    public NotesViewHolder(View view, AppCompatActivity context) {
        super(view);
        this.progressBar = view.findViewById(R.id.progressBar);
        this.textView = view.findViewById(R.id.textView);
        this.removeButton = view.findViewById(R.id.removeButton);
        this.shareButton = view.findViewById(R.id.shareButton);
        this.playButton = view.findViewById(R.id.playButton);
        this.shareButton.setOnClickListener(new OnShare());
        this.playButton.setOnClickListener(this.onPlay());
        this.playbackControls = view.findViewById(R.id.playbackControls);
        this.context = context;
    };

    public void setOnRemove(View.OnClickListener onRemove) {
        this.removeButton.setOnClickListener(onRemove);
    };

    public void setNote(Note note) {
        this.note = note;
        this.textView.setText(note.message);
        if (note.audioUrl == null) {
            playbackControls.setVisibility(View.GONE);
        }
    };

    public View.OnClickListener onPlay () {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(note.audioUrl);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        playButton.setImageResource(android.R.drawable.ic_media_pause);
                        mediaPlayer.setOnCompletionListener(onPlaybackEnd());
                        playbackTimer = new Timer();
                        playbackTimer.schedule(onPlaybackProgressUpdate(),0,1000);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("ERROR","Tuvimos un error",e);
                    };
                } else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playButton.setImageResource(android.R.drawable.ic_media_play);
                    } else {
                        mediaPlayer.start();
                        playButton.setImageResource(android.R.drawable.ic_media_pause);
                    }
                }
            };
        };
    };

    public MediaPlayer.OnCompletionListener onPlaybackEnd() {
        return new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer = null;
                playButton.setImageResource(android.R.drawable.ic_media_play);
            };
        };
    };

    public TimerTask onPlaybackProgressUpdate() {
        return new TimerTask() {
            private TimerTask self = this;
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            Double current = (double) (mediaPlayer.getCurrentPosition());
                            Double duration = (double) (mediaPlayer.getDuration());
                            Integer progress = (int) ((current/duration)*100);
                            progressBar.setProgress(progress);
                        } else {
                            progressBar.setProgress(0);
                            self.cancel();
                        }
                    }
                });
            };
        };
    };

    public class OnShare implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, note.message + "\nCon audio: " + note.audioUrl);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            context.startActivity(shareIntent);
        }
    }
}
