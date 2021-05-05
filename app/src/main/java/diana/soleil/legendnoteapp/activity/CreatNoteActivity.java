package diana.soleil.legendnoteapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import diana.soleil.legendnoteapp.R;
import diana.soleil.legendnoteapp.database.NoteDatabase;
import diana.soleil.legendnoteapp.entities.Note;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreatNoteActivity extends AppCompatActivity  {

    EditText subtitle, title, note;
    TextView dateNTime;
    String currentDateandTime;
    ImageView checkMarkSave;
    View subtitleIndicator;
    String selectedNoteColor;
    String pathToSelectedImage;
    ImageView imageViewNote;

    static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    static final int REQUEST_CODE_IMAGE_SELECTED = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_note);
        ImageView back = findViewById(R.id.backArrowIcon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        subtitle = findViewById(R.id.subtitleEditText);
        title = findViewById(R.id.editTextNoteTitle);
        note = findViewById(R.id.editTextNoteMain);
        dateNTime = findViewById(R.id.textViewTimeDate);
        checkMarkSave = findViewById(R.id.checkMarkIcon);
        subtitleIndicator = findViewById(R.id.subtitleSelector);
        imageViewNote = findViewById(R.id.noteEditImageView);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());

        dateNTime.setText(currentDateandTime);
        checkMarkSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        selectedNoteColor = "#333333";
        pathToSelectedImage = "";

        initMiscellaneous();
        setSubtitleIndicatorColor();

    }
    public void saveNote() {

        if (title.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Enter a Title", Toast.LENGTH_SHORT).show();

        } else if (subtitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please Enter a Subtitle for your note", Toast.LENGTH_SHORT).show();
        } else if (note.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please write some note", Toast.LENGTH_SHORT).show();
        } else {
            Note noteClass = new Note();
            noteClass.setTitle(title.getText().toString());
            noteClass.setSubtitle(subtitle.getText().toString());
            noteClass.setNote(note.getText().toString());
            noteClass.setDateTime(dateNTime.getText().toString());
            noteClass.setColor(selectedNoteColor);
            noteClass.setImagePath(pathToSelectedImage);
            @SuppressLint("StaticFieldLeak")
            class TaskAsync extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    NoteDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(noteClass);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Intent intent = new Intent();
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
            new TaskAsync().execute();
        }
    }
    private void initMiscellaneous() {
        LinearLayout miscellaneousLayout = findViewById(R.id.miscellaneous_layout);
        BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(miscellaneousLayout);
        miscellaneousLayout.findViewById(R.id.miscellanousTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        ImageView imageViewColor1 = miscellaneousLayout.findViewById(R.id.checkboxedInImage1);
        ImageView imageViewColor2 = miscellaneousLayout.findViewById(R.id.checkboxedInImage2);
        ImageView imageViewColor3 = miscellaneousLayout.findViewById(R.id.checkboxedInImage3);
        ImageView imageViewColor4 = miscellaneousLayout.findViewById(R.id.checkboxedInImage4);
        ImageView imageViewColor5 = miscellaneousLayout.findViewById(R.id.checkboxedInImage5);

        miscellaneousLayout.findViewById(R.id.colorNote1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#333333";
                imageViewColor1.setImageResource(R.drawable.ic_badone);
                imageViewColor2.setImageResource(0);
                imageViewColor3.setImageResource(0);
                imageViewColor4.setImageResource(0);
                imageViewColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });
        miscellaneousLayout.findViewById(R.id.colorNote2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#FDBE3B";
                imageViewColor1.setImageResource(0);
                imageViewColor2.setImageResource(R.drawable.ic_badone);
                imageViewColor3.setImageResource(0);
                imageViewColor4.setImageResource(0);
                imageViewColor5.setImageResource(0);
                setSubtitleIndicatorColor();
            }
        });
        miscellaneousLayout.findViewById(R.id.colorNote3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#FF4842";
                imageViewColor1.setImageResource(0);
                imageViewColor2.setImageResource(0);
                imageViewColor3.setImageResource(R.drawable.ic_badone);
                imageViewColor4.setImageResource(0);
                imageViewColor5.setImageResource(0);
                setSubtitleIndicatorColor();


            }
        });
        miscellaneousLayout.findViewById(R.id.colorNote4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#3A52FC";
                imageViewColor1.setImageResource(0);
                imageViewColor2.setImageResource(0);
                imageViewColor3.setImageResource(0);
                imageViewColor4.setImageResource(R.drawable.ic_badone);
                imageViewColor5.setImageResource(0);
                setSubtitleIndicatorColor();

            }
        });
        miscellaneousLayout.findViewById(R.id.colorNote5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedNoteColor = "#000000";
                imageViewColor1.setImageResource(0);
                imageViewColor2.setImageResource(0);
                imageViewColor3.setImageResource(0);
                imageViewColor4.setImageResource(0);
                imageViewColor5.setImageResource(R.drawable.ic_badone);
                setSubtitleIndicatorColor();
            }
        });

        miscellaneousLayout.findViewById(R.id.imageViewLinearLayoutInMiscellaneous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreatNoteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_STORAGE_PERMISSION);

                } else {
                    selectImage();
                }
            }
        });
    }
    private void setSubtitleIndicatorColor(){
        GradientDrawable gradientDrawable = (GradientDrawable) subtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
    }
    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent,REQUEST_CODE_IMAGE_SELECTED);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length>0) {
           if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
           } else {
               Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
           }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_SELECTED && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uriImage = data.getData();
                if (uriImage != null) {
                    try {
                        InputStream in = getContentResolver().openInputStream(uriImage);
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        imageViewNote.setImageBitmap(bitmap);
                        imageViewNote.setVisibility(View.VISIBLE);
                        pathToSelectedImage = getURIPATH(uriImage);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }
    public String getURIPATH(Uri uriContent) {
        String uriPath;

        Cursor cursor = getContentResolver().query(uriContent,null,null,null,null);
        if (cursor == null) {
            uriPath = uriContent.getPath();
        } else {
            cursor.moveToNext();
            int index = cursor.getColumnIndex("_data");
            uriPath = cursor.getString(index);
            cursor.close();
        }
        return uriPath;
    }
}