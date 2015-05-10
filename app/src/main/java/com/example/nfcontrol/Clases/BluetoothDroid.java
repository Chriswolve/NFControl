package com.example.nfcontrol.Clases;

/**
 * Created by Cristhian on 5/10/15.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothDroid  {

    // singleton
    private static BluetoothDroid instance;

    // variables
    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothAdapter mBluetoothAdapter;

    public static final String TAG = "BluettothDroid";

    // variables for BT devices
    private static List<BluetoothDevice> device = new ArrayList<BluetoothDevice>();

    private static Boolean isBTConected = false;

    private static BluetoothSocket mSocket;
    private static OutputStream mOutStream;
    private static InputStream mInStream;
    public static void Disconect (){
        instance = null;
        device = new ArrayList<BluetoothDevice>();
        isBTConected = false;
        mBluetoothAdapter = null;
        mSocket = null;
        mOutStream =null;
        mInStream = null;
    }

    public static void ClearDevices (){

        device = new ArrayList<BluetoothDevice>();

    }

    private BluetoothDroid(Context context) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            mLog("Device does not support Bluetooth");
            return;
        }

        // Enable Bluetooth
        if (mBluetoothAdapter != null) {
            Intent enableBTIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivity(enableBTIntent);
        }
    }

    public static synchronized BluetoothDroid getInstance(Context context) {
        if (instance == null)
            instance = new BluetoothDroid(context);

        return instance;
    }

    public static void LoadBT() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
                .getBondedDevices();

        for (BluetoothDevice mBluetoothDevice : pairedDevices) {
            device.add(mBluetoothDevice);
        }
    }

    public String[] getNameBtDevices() {
        LoadBT();
        String[] nameDevices = new String[device.size()];
        for (int i = 0; i < device.size(); i++) {
            nameDevices[i] = device.get(i).getName();
        }
        return nameDevices;
    }

    public void connectDevice(int idDevice) {
        BluetoothDevice actualDevice = device.get(idDevice);
        Log.d("BluetoothDroid: ", "Conecting with " + actualDevice.getName());

        BluetoothSocket tmp = null;
        try {
            tmp = actualDevice
                    .createInsecureRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
        }

        mSocket = tmp;
        try {
            mSocket.connect();
            isBTConected = true;
            iniConection();
        } catch (IOException e) {
            isBTConected = false;
        }
    }

    private void iniConection() {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try {
            tmpIn = mSocket.getInputStream();
            tmpOut = mSocket.getOutputStream();
        } catch (IOException e) {
            Log.e("Error", e + "");
        }

        mInStream = tmpIn;
        mOutStream = tmpOut;

        mLog("Conected");
    }

    public Boolean isBTConected() {
        return isBTConected;
    }

    public boolean enviarPaqBT(String msg) {
        if (isBTConected == true) {
            for (Byte letra : msg.getBytes())
                try {
                    mOutStream.write(letra);

                    mLog("Sending = " + letra);
                } catch (IOException e) {
                    mLog("Error sending = " + letra);
                    return false;
                }
        } else {
            return false;
        }

        return true;
    }

    private void mLog(String msg) {
        Log.d(TAG, msg);
    }
}

