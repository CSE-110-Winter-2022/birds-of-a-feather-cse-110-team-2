package edu.ucsd.cse110.lab5_room;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import edu.ucsd.cse110.lab5_room.internal.MatchList;
import edu.ucsd.cse110.lab5_room.model.Match;

public class MatchListView extends FrameLayout {
    // creates an internal RecyclerView
    private final RecyclerView rv;
    private final Context context;
    private MLAdapter adapter;

    SortType filter = SortType.DEFAULT;
    public enum SortType {
        DEFAULT,
        FAVORITES,
        CLASS_SIZE,
        CLASS_RECENT
    }

    public MatchListView(Context context) {
        super(context);
        this.context = context;
        this.rv = createRecyclerView(context, null);
        init();
    }
    public MatchListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.rv = createRecyclerView(context, attrs);
        init();
    }
    public MatchListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.rv = createRecyclerView(context, attrs);
        init();
    }

    private void init() {
        // make sure the RecyclerView actually shows up
        this.addView(this.rv);
    }

    private RecyclerView createRecyclerView(Context context, @Nullable AttributeSet attrs) {
        try {
            return (RecyclerView) LayoutInflater
                    .from(context)
                    .createView("RecyclerView", "androidx.recyclerview.widget.", attrs);
        }
        catch (ClassNotFoundException e) {
            // should never reach here
            e.printStackTrace();
        }

        return null;
    }

    // set the StudentListView to list students
    public void trackStudents(MatchList matches) {
        this.rv.setLayoutManager(new LinearLayoutManager(context));
        this.adapter = new MLAdapter(matches, this.filter);
        this.rv.setAdapter(this.adapter);
    }

    public void sortBy(SortType s) {
        this.adapter.setFilter(s);
        this.adapter.notifyDataSetChanged();
    }

    // MLAdapter creates the list from a data source
    private static class MLAdapter extends RecyclerView.Adapter<MLViewHolder> {
        private MatchList matches;
        private SortType filter;

        public MLAdapter(MatchList matches, SortType filter) {
            this.matches = matches;
            this.filter   = filter;
        }

        void setFilter(SortType filter) {
            this.filter = filter;
        }

        @NonNull
        @Override
        public MLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.person_row, parent, false);

            return new MLViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MLViewHolder holder, int position) {
            holder.setStudent(this.matches.get(position, this.filter));
        }

        @Override
        public int getItemCount() {
            return this.matches.size();
        }
    }

    // ViewHolder defines the behavior of a single list item
    private static class MLViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView nameView;
        private Match match;
        private final ImageView personPictureView;

        public MLViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameView = itemView.findViewById(R.id.person_row_name);
            this.personPictureView = itemView.findViewById(R.id.person_row_picture);
            itemView.setOnClickListener(this);
        }

        public void setStudent(Match m) {
            this.match = m;
            this.nameView.setText(m.getName());
            Picasso.get().load(match.getPhotoURL()).into(personPictureView);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            //Utilities.showAlert(context, "You clicked on " + this.person.getName() + "!");
            // TODO fix
            Intent intent = new Intent(context, PersonDetailActivity.class);
            intent.putExtra("student_name", this.match.getName());
            intent.putExtra("student_classes", (Parcelable) this.match.getClasses());
            intent.putExtra("student_photo_url", this.match.getPhotoURL());
            context.startActivity(intent);
        }
    }


}
