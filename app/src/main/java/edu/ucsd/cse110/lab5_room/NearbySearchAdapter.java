package edu.ucsd.cse110.lab5_room;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesClient;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.NearbyPermissions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.internal.NearbyMessage;
import edu.ucsd.cse110.lab5_room.model.DummyStudent;
import edu.ucsd.cse110.lab5_room.model.Student;

public class NearbySearchAdapter extends RecyclerView.Adapter<NearbySearchAdapter.ViewHolder> {
    private final Context context;
    private final MessagesClient mClient;

    public List<Student> students = new ArrayList<>();
//    public final Map<Integer, Student> students = new HashMap<>();
    private final NearbyStudentListener listener = new NearbyStudentListener();
    private final Gson studentGson = new GsonBuilder()
            .registerTypeAdapter(Student.class, new DummyStudent.StudentShortSerializer())
            .registerTypeAdapter(NearbyMessage.class, new NearbyMessage.MessageSerializer())
            .create();

    public NearbySearchAdapter(Context context) {
        this.context = context;
        this.mClient = Nearby.getMessagesClient(this.context, new MessagesOptions.Builder()
            .setPermissions(NearbyPermissions.BLUETOOTH)
            .build());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        this.context = parent.getContext();
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.person_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPerson(students.get(position));
    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    // start scanning if enable, stop otherwise
    public void enableScan(boolean enabled) {
        // TODO make payload current user
        NearbyMessage rawMessage;
        if (enabled) {
            // start scanning and make us public
//            rawMessage = new NearbyMessage(Constants.ACTION_ADD, DummyStudent.getCurrent(this.context));
            Task<Void> subscribeTask = this.mClient.subscribe(this.listener);
            subscribeTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("NSA", "fail");
                }
            });
        }
        else {
//            // stop scanning and tell everyone else to remove us
//            rawMessage = new NearbyMessage(Constants.ACTION_REMOVE, DummyStudent.getCurrent(this.context));
//            this.mClient.unsubscribe(this.listener);
//            // TODO remove all nearby users
        }

        // send whatever message was created above
//        Message message = new Message(this.studentGson.toJson(rawMessage, NearbyMessage.class).getBytes());
//        this.mClient.publish(message);
    }

    protected class NearbyStudentListener extends MessageListener {
        @Override
        public void onFound(@NonNull Message message) {
            String messageContent = new String(message.getContent());
            NearbyMessage parsedMessage = Constants.GSON.fromJson(messageContent, NearbyMessage.class);

            switch (parsedMessage.action) {
                case Constants.ACTION_ADD: {
                    Student s = parsedMessage.getStudent();
                    students.add(s);

                    // TODO only add people with the same classes
                    break;
                }

                case Constants.ACTION_REMOVE: {
                    // TODO remove from list
//                    Student s = Constants.GSON.fromJson(parsedMessage.payload, DummyStudent.class);
//                    students.remove(s.getStudentId(), s);
                    break;
                }

                default: {}
            }
        }

        @Override
        public void onLost(Message message) {
            Log.d("NearbyStudentListener", "Lost sight of message: " + new String(message.getContent()));
        }
    }

    // describe behavior of individual list item
    protected static class ViewHolder
                    extends RecyclerView.ViewHolder
                    implements View.OnClickListener {
        private final TextView personNameView;
        private final ImageView personPictureView;

        private Student person;

        ViewHolder(View itemView) {
            super(itemView);
            this.personNameView = itemView.findViewById(R.id.person_row_name);
            this.personPictureView = itemView.findViewById(R.id.person_row_picture);
            itemView.setOnClickListener(this);
        }

        public void setPerson(Student person) {
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
            intent.putExtra("student_is_close", this.person.isClose());
            intent.putExtra("student_classes", this.person.getClasses().toArray(new String[]{}));
            intent.putExtra("student_photo_url", this.person.getPhotoURL());
            context.startActivity(intent);
        }
    }
}
