package com.example.user.adv_test1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.ParcelUuid;

public class BleUtil {
    // Util
    private BleUtil() {
        // Util
    }

    /** check if BLE Supported device */
    public static boolean isBLESupported(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /** get BluetoothManager */
    public static BluetoothManager getManager(Context context) {
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    /** create AdvertiseSettings */
    public static AdvertiseSettings createAdvSettings(boolean connectable, int timeoutMillis) {
        AdvertiseSettings.Builder builder = new AdvertiseSettings.Builder();
        builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED);
        // ConnectableをtrueにするとFlags AD typeの3byteがManufacturer specific data等の前につくようになります。
        builder.setConnectable(connectable);
        builder.setTimeout(timeoutMillis);
        builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
        return builder.build();
    }

    /** create AdvertiseDate for iBeacon */
    public static AdvertiseData createIBeaconAdvertiseData() {
        // UUID to byte[]
        // ref. http://stackoverflow.com/questions/6881659/how-to-convert-two-longs-to-a-byte-array-how-to-convert-uuid-to-byte-array
        byte[] manufacturerData = new byte[15];
        ByteBuffer bb = ByteBuffer.wrap(manufacturerData);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.put((byte) 0x03);
        bb.put((byte) 0x04);
        bb.put((byte) 0x05);
        bb.put((byte) 0x06);
        bb.put((byte) 0x07);
        bb.put((byte) 0x08);
        bb.put((byte) 0x09);
        bb.put((byte) 0x10);
        bb.put((byte) 0x11);
        bb.put((byte) 0x12);
        bb.put((byte) 0x13);
        bb.put((byte) 0x14);
        bb.put((byte) 0x15);
        bb.put((byte) 0x16);
        bb.put((byte) 0x17);

        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addManufacturerData(0x0201, manufacturerData);
        AdvertiseData adv = builder.build();
        return adv;
    }

    /** create AdvertiseDate for FMP(Find Me Profile, include IAS and DIS) */
    public static AdvertiseData createFMPAdvertiseData() {
        AdvertiseData.Builder builder = new AdvertiseData.Builder();

//        builder.addServiceUuid(new ParcelUuid(UUID.fromString(BleUuid.SERVICE_DEVICE_INFORMATION)));
//        builder.addServiceUuid(new ParcelUuid(UUID.fromString(BleUuid.SERVICE_IMMEDIATE_ALERT)));
        AdvertiseData adv = builder.build();
        return adv;
    }
}
