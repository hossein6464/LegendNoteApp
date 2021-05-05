package diana.soleil.legendnoteapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import diana.soleil.legendnoteapp.dao.NoteDao;
import diana.soleil.legendnoteapp.entities.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase noteDatabase;

    public static synchronized NoteDatabase getDatabase (Context context) {

        if (noteDatabase == null) {
            noteDatabase = Room.databaseBuilder(context,NoteDatabase.class, "notes2_db").build();
        }
    return noteDatabase;
    }
    public abstract NoteDao noteDao();

}
