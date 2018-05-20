package com.example.user.abeacon_test;

/*
 * Copyright (C) 2014 youten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.abeacon_test.BleUuid;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    // BT
    private BluetoothAdapter mBTAdapter;
    private BluetoothLeAdvertiser mBTAdvertiser;
    private BluetoothGattServer mGattServer;
    // View
    private Button mIBeaconButton;
    private Button mStopButton;

    private AdvertiseCallback mAdvCallback = new AdvertiseCallback() {
        public void onStartSuccess(android.bluetooth.le.AdvertiseSettings settingsInEffect) {

            mIBeaconButton.setEnabled(false);
            mStopButton.setEnabled(true);
        }

//        public void onStartFailure(int errorCode) {
//            Log.d(TAG, "onStartFailure errorCode=" + errorCode);
//        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    @Override
    protected void onStop() {
        super.onStop();
        stopAdvertise();
    }

    private void init() {
        // BLE check
        if (!BleUtil.isBLESupported(this)) {
            finish();
            return;
        }

        // BT check
        BluetoothManager manager = BleUtil.getManager(this);
        if (manager != null) {
            mBTAdapter = manager.getAdapter();
        }
        if ((mBTAdapter == null) || (!mBTAdapter.isEnabled())) {
            //Toast.makeText(this, R.string.bt_unavailable, Toast.LENGTH_SHORT).show();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        mIBeaconButton = (Button) findViewById(R.id.adv_on);
        mIBeaconButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startIBeaconAdvertise();
            }
        });

        mStopButton = (Button) findViewById(R.id.adv_stop);
        mStopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAdvertise();
            }
        });
    }

    // start Advertise as iBeacon
    private void startIBeaconAdvertise() {
        if (mBTAdapter == null) {
            return;
        }
        if (mBTAdvertiser == null) {
            mBTAdvertiser = mBTAdapter.getBluetoothLeAdvertiser();
        }
        if (mBTAdvertiser != null) {
            mBTAdvertiser.startAdvertising(
                    BleUtil.createAdvSettings(true,0),
                    BleUtil.createIBeaconAdvertiseData(),
                    mAdvCallback
            );
        }
    }


    private void stopAdvertise() {
        if (mGattServer != null) {
            mGattServer.clearServices();
            mGattServer.close();
            mGattServer = null;
        }
        if (mBTAdvertiser != null) {
            mBTAdvertiser.stopAdvertising(mAdvCallback);
            mBTAdvertiser = null;
        }
        mIBeaconButton.setEnabled(true);
        mStopButton.setEnabled(true);
    }
}

