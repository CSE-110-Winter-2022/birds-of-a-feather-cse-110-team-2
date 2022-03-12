package edu.ucsd.cse110.lab5_room.nearby;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesClient;

import edu.ucsd.cse110.lab5_room.internal.BoFApplication;

public class NearbyMessageListener extends MessageListener {
    private final NearbyMessageHandler handler;
    private final MessagesClient client;
//    private final BoFApplication app;

    private boolean isActive = false;
    public NearbyMessageListener(Context c, NearbyMessageHandler handler) {
//        this.app = (BoFApplication) c.getApplicationContext();
        this.handler = handler;
        this.client  = Nearby.getMessagesClient(c);
    }

    public void start() {
        Log.d(this.getClass().getSimpleName(), "Starting Nearby");
        this.client.subscribe(this);
        isActive = true;
    }

    public void stop() {
        Log.d(this.getClass().getSimpleName(), "Stopping Nearby");
        this.client.unsubscribe(this);
        isActive = false;
    }

    public void send(Message message) {
        Log.d(this.getClass().getSimpleName(), "Sending message: " + message.getContent().toString());
        if (isActive)
            this.client.publish(message);
    }

    @Override
    public void onFound(Message messageRaw) {
        String message = new String(messageRaw.getContent());
        handler.parseRaw(message);
    }
}
