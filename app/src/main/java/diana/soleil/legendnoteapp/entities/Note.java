package diana.soleil.legendnoteapp.entities;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "notes")
public class Note implements Serializable {
        @PrimaryKey (autoGenerate = true)
        private int id;

        @ColumnInfo (name = "web_link")
        private String webLink;
        @ColumnInfo (name = "title")
        private String title;
        @ColumnInfo (name = "date_time")
        private String dateTime;
        @ColumnInfo (name = "subtitle")
        private String subtitle;
        @ColumnInfo (name = "note")
        private String note;
        @ColumnInfo (name = "image_path")
        private String imagePath;
        @ColumnInfo (name = "color")
        private String color;

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @NonNull
    @Override
    public String toString() {
        return title + " : " + dateTime;
    }
}
