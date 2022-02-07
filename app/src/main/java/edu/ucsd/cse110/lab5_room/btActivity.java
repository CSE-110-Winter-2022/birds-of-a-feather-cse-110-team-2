package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothServerSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class btActivity extends AppCompatActivity {
    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> pairedBtDevices;
    List<String> listPairedBtDevices;
    Handler btHandler;
    //ConnectedBluetoothThread threadConnectedBt;
    BluetoothDevice btDevice;
    BluetoothSocket btSocket;

    final static int BT_ENABLED = 1;
    final static int BT_STATUS = 3;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    //variables
    private Button settingButton = (Button) findViewById(R.id.btn_Setting);
    private Button notNowButton = (Button) findViewById(R.id.btn_not);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_layout);

        /*
         *  click go to setting button if bluetooth is not on
         *  and user can pick set the bluetooth now by going to the settings on their phone.
         *  Or, they can choose just ignore it for now.
         *  if bluetooth is on, doesn't show anything.
         *
         * */
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            btSetting();
        }
        else {
            if (!btAdapter.isEnabled()) {
                btSetting();
                Intent intent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
                //startActivityForResult(intent, BT_ENABLED);
            }
        }
    }

    public void btSetting() {
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivityForResult(intent, 0);
            }
        });
    }

}