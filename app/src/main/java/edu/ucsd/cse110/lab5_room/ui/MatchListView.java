package edu.ucsd.cse110.lab5_room.ui;

import android.content.Context;
import android.content.Intent;
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

import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.PersonDetailActivity;
import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.model.Student;

public class MatchListView extends FrameLayout {
    // creates an internal RecyclerView
    private final RecyclerView rv;
    private final Context context;
    private MLAdapter adapter;

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
        this.rv.setLayoutManager(new LinearLayoutManager(context));
        this.addView(this.rv);
        this.adapter = new MLAdapter();
        this.rv.setAdapter(this.adapter);
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

    public void updateList(Student[] students) {
        //init();

        this.adapter.setStudents(students);
    }

    public void updateList() {
        this.adapter.notifyDataSetChanged();
    }

    // MLAdapter creates the list from a data source
    private static class MLAdapter extends RecyclerView.Adapter<MLViewHolder> {
        private Student[] students;


        public void setStudents(Student[] students) {
            this.students = students;
            notifyDataSetChanged();
            //notifyAll();
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
            holder.setStudent(students[position]);
        }

        @Override
        public int getItemCount() {
            return this.students.length;
        }
    }

    // ViewHolder defines the behavior of a single list item
    private static class MLViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView nameView;
        private Student match;
        private final ImageView personPictureView;
        private final TextView courseView;

        public MLViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameView = itemView.findViewById(R.id.person_row_name);
            this.personPictureView = itemView.findViewById(R.id.person_row_picture);
            this.courseView = itemView.findViewById(R.id.course);
            itemView.setOnClickListener(this);
        }

        public void setStudent(Student m) {
            this.match = m;
            this.nameView.setText(m.getName());
            //TODO: implement student matching class method
            this.courseView.setText("1");
            Picasso.get().load(match.getPhotoURL()).into(personPictureView);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();

            Intent intent = new Intent(context, PersonDetailActivity.class);
            intent.putExtra(Constants.USER_ID, this.match.getId().toString());
            context.startActivity(intent);
        }
    }


}
