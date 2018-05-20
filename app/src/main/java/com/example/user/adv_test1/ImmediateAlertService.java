package com.example.user.adv_test1;

import java.util.UUID;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.util.Log;

public class ImmediateAlertService {
    private static final String TAG = "IAS";
    private byte[] mAlertLevel = new byte[]{
            (byte) 0x00
    };

    private BluetoothGattServer mGattServer;

    public void setupServices(BluetoothGattServer gattServer) {
        if (gattServer == null) {
            throw new IllegalArgumentException("gattServer is null");
        }
        mGattServer = gattServer;

        // setup services
        { // immediate alert

        }

        { // device information

        }
    }

    public void onServiceAdded(int status, BluetoothGattService service) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.d(TAG, "onServiceAdded status=GATT_SUCCESS service="
                    + service.getUuid().toString());
        } else {
            Log.d(TAG, "onServiceAdded status!=GATT_SUCCESS");
        }
    }

    public void onConnectionStateChange(android.bluetooth.BluetoothDevice device, int status,
                                        int newState) {
        // Log.d(TAG, "onConnectionStateChange status=" + status + "->" + newState);
    }


}
