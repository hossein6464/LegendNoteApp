package diana.soleil.legendnoteapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import diana.soleil.legendnoteapp.R;
import diana.soleil.legendnoteapp.activity.CreatNoteActivity;
import diana.soleil.legendnoteapp.adapter.NoteAdapter;
import diana.soleil.legendnoteapp.database.NoteDatabase;
import diana.soleil.legendnoteapp.entities.Note;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int Note_Code = 1;
    RecyclerView recyclerView;
    private List<Note> notesList;
    NoteAdapter adapterNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.addNoteButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent( getApplicationContext(), CreatNoteActivity.class),
                        Note_Code);
            }
        });
        recyclerView = findViewById(R.id.mainRecyclerView);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        notesList = new ArrayList<>();
        adapterNote = new NoteAdapter(notesList);
        recyclerView.setAdapter(adapterNote);


        getNote();
    }
    public void getNote() {



            @SuppressLint("StaticFieldLeak")
            class TaskAsyncGetNotes extends AsyncTask<Void, Void, List<Note>> {


                @Override
                protected List<Note> doInBackground(Void... voids) {
                    return NoteDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
                }

                @Override
                protected void onPostExecute(List<Note> notes) {
                    super.onPostExecute(notes);
                    if (notesList.size() == 0) {
                        notesList.addAll(notes);
                        adapterNote.notifyDataSetChanged();
                    } else {
                        notesList.add(0,notes.get(0));
                        adapterNote.notifyDataSetChanged();
                    }
                    recyclerView.smoothScrollToPosition(0);
                }
            }
            new TaskAsyncGetNotes().execute();
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Note_Code) {
            getNote();
        }
    }
}
