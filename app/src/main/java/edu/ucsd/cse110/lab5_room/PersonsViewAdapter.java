package edu.ucsd.cse110.lab5_room;

import android.content.Context;
import android.content.Intent;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import edu.ucsd.cse110.lab5_room.model.Match;

public class PersonsViewAdapter extends RecyclerView.Adapter<PersonsViewAdapter.ViewHolder> {
    private final Match[] persons;

    public PersonsViewAdapter(Match[] persons) {
        super();
        this.persons = persons;
    }

    @NonNull
    @Override
    public PersonsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.person_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonsViewAdapter.ViewHolder holder, int position) {
        holder.setPerson(persons[position]);
    }

    @Override
    public int getItemCount() {
        return this.persons.length;
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView personNameView;
        private final ImageView personPictureView;

        private Match person;



        ViewHolder(View itemView) {
            super(itemView);
            this.personNameView = itemView.findViewById(R.id.person_row_name);
            this.personPictureView = itemView.findViewById(R.id.person_row_picture);
            itemView.setOnClickListener(this);
        }

        public void setPerson(Match person) {
            this.person = person;
            this.personNameView.setText(person.getName());
            Picasso.get().load(person.getPhotoURL()).into(personPictureView);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            //Utilities.showAlert(context, "You clicked on " + this.person.getName() + "!");
            Intent intent = new Intent(context, PersonDetailActivity.class);
            intent.putExtra("student_name", this.person.getName());
//            intent.putExtra("student_is_close", this.person.isClose());
            intent.putExtra("student_classes", (Parcelable) this.person.getClasses());
            intent.putExtra("student_photo_url", this.person.getPhotoURL());
            context.startActivity(intent);
        }
    }
}