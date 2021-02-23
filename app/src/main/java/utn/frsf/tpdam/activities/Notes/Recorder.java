package utn.frsf.tpdam.activities.Notes;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import java.io.IOException;

public class Recorder {
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private AppCompatActivity context;
    private ConstraintLayout backdrop;
    private Button stopButton;
    private Recorder self = this;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionGranted;
    private MediaRecorder recorder = null;
    public boolean recording = false;
    private View.OnClickListener onStartListener;
    private View.OnClickListener onEndListener;
    public String recordingPath;

    public Recorder(AppCompatActivity context) {
        this.context = context;
    };

    public void setOnStartListener(View.OnClickListener onStartListener) {
        this.onStartListener = onStartListener;
    };

    public  void setOnEndListener(View.OnClickListener onEndListener) {
        this.onEndListener = onEndListener;
    }

    public View.OnTouchListener onTouch() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (context.checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                            recorder = new MediaRecorder();
                            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            recordingPath = context.getExternalFilesDir(null).getAbsolutePath() + "/recording";
                            recorder.setOutputFile(recordingPath);
                            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                            try {
                                recorder.prepare();
                                recorder.start();
                                recording = true;
                                onStartListener.onClick(null);
                            } catch (IOException e) {
                                recordingPath = null;
                                Log.e("ERROR", e.toString());
                                recorder.release();
                                recorder = null;
                            }
                        } else {
                            ActivityCompat.requestPermissions(context, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (recorder != null) {
                            recorder.stop();
                            recorder.release();
                            recorder = null;
                            recording = false;
                            onEndListener.onClick(null);
                        }
                        break;
                }
                return false;
            };
        };
    };

    public String getRecordingPath() {
        return this.recordingPath;
    };
};
