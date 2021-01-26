package utn.frsf.tpdam.activities.Notes;

public class Note {
    public String id;
    public String message;

    public Note(String id, String message) {
        this.id = id;
        this.message = message;
    };

    public String toString() {
        return this.message;
    }
};
