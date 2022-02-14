package edu.ucsd.cse110.lab5_room.auth;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import pub.devrel.easypermissions.EasyPermissions;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    protected static void checkBluetoothStatus(Activity context) {
        BoFApplication application = (BoFApplication) context.getApplication();
        application.executorService.submit(() -> {
            List<String> perms = new ArrayList<>(Arrays.asList(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                perms.addAll(Arrays.asList(
                        Manifest.permission.BLUETOOTH_ADVERTISE,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN)
                );
            }
            else {
                perms.addAll(Arrays.asList(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                ));
            }

            String[] permissions = perms.toArray(new String[0]);

            if (!EasyPermissions.hasPermissions(context, permissions)) {
                context.runOnUiThread(() -> {
                    final BottomSheetDialog btDialog = new BottomSheetDialog(context);
                    btDialog.setContentView(R.layout.bt_layout);

                    // button to request permissions
                    Button requestBtn = btDialog.findViewById(R.id.promptbt_btn_request);
                    if (requestBtn != null) {
                        requestBtn.setOnClickListener((v) -> {
                            EasyPermissions.requestPermissions(context,
                                    context.getString(R.string.bluetooth_prompt),
                                    3,
                                    permissions
                            );
                        });
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
}
