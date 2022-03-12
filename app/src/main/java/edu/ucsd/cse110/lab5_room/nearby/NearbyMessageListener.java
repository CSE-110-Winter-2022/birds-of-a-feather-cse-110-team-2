package edu.ucsd.cse110.lab5_room.nearby;

import android.content.Context;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesClient;

public class NearbyMessageListener extends MessageListener {
    private final NearbyMessageHandler handler;
    private final MessagesClient client;

    private boolean isActive = false;
    public NearbyMessageListener(Context c, NearbyMessageHandler handler) {
        this.handler = handler;
        this.client  = Nearby.getMessagesClient(c);
    }

    public void start() {
        this.client.subscribe(this);
        isActive = true;
    }

    public void stop() {
        this.client.unsubscribe(this);
        isActive = false;
    }

    public void send(Message message) {
        if (isActive)
            this.client.publish(message);
    }

    @Override
    public void onFound(Message messageRaw) {
        String message = new String(messageRaw.getContent());
        handler.parseRaw(message);
    }
}
