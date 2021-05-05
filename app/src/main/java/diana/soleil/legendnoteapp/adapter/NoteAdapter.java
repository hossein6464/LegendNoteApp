package diana.soleil.legendnoteapp.adapter;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import diana.soleil.legendnoteapp.R;
import diana.soleil.legendnoteapp.entities.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,subtitle,dateNTime;
        LinearLayout layoutNote;
        RoundedImageView imageInRecyclerView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            title = (TextView) view.findViewById(R.id.titleTextViewRecyclerView);
            subtitle = (TextView) view.findViewById(R.id.subtitleTextViewRecyclerView);
            dateNTime = (TextView) view.findViewById(R.id.dateNTimeTextViewRecyclerView);
            layoutNote =  view.findViewById(R.id.linearLayoutforRecyclerView);
            imageInRecyclerView = view.findViewById(R.id.roundeImageInRecyclerView);
        }
        public void setNote(Note note) {
            title.setText(note.getTitle());
            subtitle.setText(note.getSubtitle());
            dateNTime.setText(note.getDateTime());
            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (note.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }
            if (note.getImagePath() != null) {
                imageInRecyclerView.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageInRecyclerView.setVisibility(View.VISIBLE);
            } else {
                imageInRecyclerView.setVisibility(View.GONE);
            }

        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param notes String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.setNote(notes.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

