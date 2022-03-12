package edu.ucsd.cse110.lab5_room.auth;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class AuthActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // accumulate and pass on intent
        this.intent = getIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected static void checkBluetoothStatus(Activity context) {
        BoFApplication application = (BoFApplication) context.getApplication();
        application.executorService.submit(() -> {
            String[] permissions = {
                    Manifest.permission.BLUETOOTH,
//                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADMIN,
//                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };

            if (!EasyPermissions.hasPermissions(context, permissions)) {
                context.runOnUiThread(() -> {
                    final BottomSheetDialog btDialog = new BottomSheetDialog(context);
                    btDialog.setContentView(R.layout.bt_layout);

                    // button to request permissions
                    Button requestBtn = btDialog.findViewById(R.id.promptbt_btn_request);
                    if (requestBtn != null) {
                        requestBtn.setOnClickListener((v) ->
                            EasyPermissions.requestPermissions(
                                context,
                                context.getString(R.string.bluetooth_prompt),
                                3,
                                permissions
                            ));
                    }

                    // button to go to settings
                    Button settingsBtn = btDialog.findViewById(R.id.promptbt_btn_settings);
                    if (settingsBtn != null) {
                        settingsBtn.setOnClickListener((v) -> {
                            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                            context.startActivity(intent);
                        });
                    }

                    btDialog.show();
                });
            }
        });
    }

    protected String getAccumulatedString(String extraName) {
        return this.intent.getStringExtra(extraName);
    }

    protected List<?> getAccumulatedList(String extraName) {
        return this.intent.getParcelableArrayListExtra(extraName);
    }

    protected void accumulateString(String extraName, String value) {
        this.intent.putExtra(extraName, value);
    }

    protected void accumulateList(String extraName, ArrayList<Parcelable> value) {
        this.intent.putExtra(extraName, value);
    }

    protected void moveOn(Class<? extends AuthActivity> next) {
        this.intent.setClass(this, next);
        startActivity(this.intent);
    }
}
