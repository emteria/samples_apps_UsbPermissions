package com.emteria.usbpermissions;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private static final String ACTION_USB_PERMISSION = "com.emteria.usbpermissions.USB_PERMISSION";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent == null)
        {
            Log.e("UsbPermissions", "INTENT IS NULL");
            return;
        }

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        if (manager == null)
        {
            Log.e("UsbPermissions", "No device manager found!");
            return;
        }

        //
        // WARNING: Android seem only to enumerate first 6 USB devices
        // If nothing happens in your setup, make sure you don't have too many USB devices connected
        //

        PendingIntent permissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        if (device == null)
        {
            Log.e("UsbPermissions", "DEVICE IS NULL, enumerate");
            HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
            for (UsbDevice usbDevice : deviceList.values())
            {
                int deviceId = usbDevice.getDeviceId();
                int vendorId = usbDevice.getVendorId();
                String name = usbDevice.getDeviceName();
                int protocol = usbDevice.getDeviceProtocol();

                Log.e("UsbPermissions", "deviceId: " + deviceId);
                Log.e("UsbPermissions", "vendorId: " + vendorId);
                Log.e("UsbPermissions", "name: " + name);
                Log.e("UsbPermissions", "protocol: " + protocol);

                Log.e("UsbPermissions", "Request permission");
                manager.requestPermission(usbDevice, permissionIntent);
            }
        }
        else
        {
            Log.e("UsbPermissions", "DEVICE IS NOT NULL");

            int deviceId = device.getDeviceId();
            int vendorId = device.getVendorId();
            String name = device.getDeviceName();
            int protocol = device.getDeviceProtocol();

            Log.e("UsbPermissions", "deviceId: " + deviceId);
            Log.e("UsbPermissions", "deviceId: " + vendorId);
            Log.e("UsbPermissions", "name: " + name);
            Log.e("UsbPermissions", "protocol: " + protocol);

            Log.e("UsbPermissions", "Request permission");
            manager.requestPermission(device, permissionIntent);
        }
    }
}
