package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.nearby.NearbyMessageHandler;

public class NearbyMockActivity extends AppCompatActivity
    implements View.OnClickListener {

    private BoFApplication app;
    private EditText mockBox;
    private NearbyMessageHandler listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_mock);

        mockBox = findViewById(R.id.mockBox);

        app = (BoFApplication) getApplication();
        listener = NearbyMessageHandler.singleton(this);

        Button mockButton = findViewById(R.id.mockButton);
        mockButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String message = mockBox.getText().toString();
        if (message.isEmpty())
            return;

        app.executorService.submit(() -> {
            listener.parseRaw(message);
            runOnUiThread(() -> {
                Toast.makeText(this, R.string.mock_confirm, Toast.LENGTH_LONG)
                    .show();
                mockBox.setText("");
            });
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}