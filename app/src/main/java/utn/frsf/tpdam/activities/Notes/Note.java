package utn.frsf.tpdam.activities.Notes;

public class Note {
    public String id;
    public String message;
    public String audioUrl;

    public Note() { }

    public Note(String id, String message) {
        this.id = id;
        this.message = message;
    };

    public Note(String id, String message, String audioUrl) {
        this.id = id;
        this.message = message;
        this.audioUrl = audioUrl;
    }

    public String toString() {
        return this.message;
    }

    public void setId(String id) {
        this.id = id;
    };

    public void setMessage(String message) {
        this.message = message;
    };

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    };

    public String getId() {
        return this.id;
    };

    public String getMessage() {
        return this.message;
    };

    public String getAudioUrl() {
        return this.audioUrl;
    };
};
