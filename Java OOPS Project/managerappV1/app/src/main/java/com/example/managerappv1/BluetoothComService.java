package com.example.managerappv1;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class BluetoothComService {
    private static final String TAG = "BluetoothComService";
    private static final String appName = "managerapp";
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private final BluetoothAdapter ba;
    Context mycontext;
    private AcceptThread insecureAT;
    private ConnectThread insecureCT;
    private ConnectedThread insecureCdT;
    private BluetoothDevice Device;
    private UUID DeviceUUID;
    private ProgressDialog myDialog;
    public BluetoothComService(Context context){
        mycontext = context;
        ba = BluetoothAdapter.getDefaultAdapter();
        //start();
    }
    private class AcceptThread extends Thread{
        private final BluetoothServerSocket bss;
        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            try {
                tmp = ba.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bss = tmp;
        }
        @SuppressLint("LongLogTag")
        public void run(){
            Log.d(TAG,"running new AcceptThread");
            BluetoothSocket bs = null;
            try {
                Log.d(TAG,"going to accept");
                bs = bss.accept();
                Log.d(TAG,"waiting for connection...");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bs != null){
                connected(bs,Device);
            }
        }
        @SuppressLint("LongLogTag")
        public void cancel(){
            Log.d(TAG, "canceling AcceptThread");
            try{
                bss.close();
            }
            catch(IOException e){
                Log.d(TAG,"failed to close bss");
            }
        }
    }

    private class ConnectThread extends Thread{
        private BluetoothSocket bs;
        @SuppressLint("LongLogTag")
        public ConnectThread(BluetoothDevice device, UUID uuid){
            Log.d(TAG,"ConnectThread Created");
            Device = device;
            DeviceUUID = uuid;
        }
        @SuppressLint("LongLogTag")
        public void run(){
            BluetoothSocket tmp = null;
            Log.d(TAG,"ConnecThread started");
            try {
                Log.d(TAG,"Trying to create RFcom socket");
                tmp = Device.createRfcommSocketToServiceRecord(DeviceUUID);
            } catch (IOException e) {
                Log.d(TAG,"failed to create RFcom socket");
            }
            bs = tmp;
            ba.cancelDiscovery();
            try {
                Log.d(TAG,"connecting");
                bs.connect();
                Log.d(TAG,"connected");
            } catch (IOException e) {
                Log.d(TAG,"failed to connect");
                try {
                    Log.d(TAG,"closing socket");
                    bs.close();
                    Log.d(TAG,"socket closed");
                } catch (IOException ioException) {
                    Log.d(TAG,"failed to close socket");
                }
            }
            connected(bs,Device);
        }
        @SuppressLint("LongLogTag")
        public void cancel(){
            Log.d(TAG,"cancelling Connection Thread");
            try{
                Log.d(TAG,"closing socket");
                bs.close();
                Log.d(TAG,"socket closed");
            }
            catch(Exception e){
                Log.d(TAG,"failed to close socket");
            }
        }
    }
    @SuppressLint("LongLogTag")
    public synchronized void start(){
        Log.d(TAG,"start method");
        if(insecureCT != null){
            insecureCT.cancel();
            insecureCT = null;
        }

        if(insecureAT == null){
            insecureAT = new AcceptThread();
            insecureAT.start();
        }
        Log.d(TAG,"start method2");
    }

    @SuppressLint("LongLogTag")
    public void startClient(BluetoothDevice device, UUID uuid){
        Log.d(TAG,"startClient Method");
        myDialog = ProgressDialog.show(mycontext,"Making Bluetooth Connection","Please Wait...",true);
        insecureCT = new ConnectThread(device,uuid);
        insecureCT.start();
    }

    private class ConnectedThread extends Thread{
        private final BluetoothSocket bs;
        private final InputStream ipstream;
        private final OutputStream opstream;
        @SuppressLint("LongLogTag")
        public ConnectedThread(BluetoothSocket socket){
            Log.d(TAG,"created ConnectedThread");
            bs = socket;
            InputStream ipstreamtmp = null;
            OutputStream opstreamtmp = null;
            myDialog.dismiss();
            try {
                ipstreamtmp = bs.getInputStream();
                opstreamtmp = bs.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ipstream = ipstreamtmp;
            opstream = opstreamtmp;
        }
        @SuppressLint("LongLogTag")
        public void run(){
            byte[] buffer = new byte[1024];
            int bytes;
            String incomingMessage;
            while(true){
                try {
                    bytes = ipstream.read(buffer);
                    incomingMessage = new String(buffer,0,bytes);
                    Log.d(TAG,"ip:"+incomingMessage);
                } catch (IOException e) {
                    Log.d(TAG,"failed to read");
                    break;
                }
            }
        }

        @SuppressLint("LongLogTag")
        public void write(byte[] bytes){
            String message = new String(bytes, Charset.defaultCharset());
            Log.d(TAG,"sending: "+ message);
            try {
                opstream.write(bytes);
            } catch (IOException e) {
                Log.d(TAG,"could not write");
            }
        }

        @SuppressLint("LongLogTag")
        public void cancel(){
            Log.d(TAG,"cancelling ConnectedThread");
            try{
                bs.close();
            } catch (IOException e) {
                Log.d(TAG,"failed to close socket");
            }
        }
    }

    @SuppressLint("LongLogTag")
    private void connected(BluetoothSocket socket, BluetoothDevice device){
        Log.d(TAG,"connected method");
        insecureCdT = new ConnectedThread(socket);
        insecureCdT.start();
    }

    @SuppressLint("LongLogTag")
    public void write(byte[] bytes){
        Log.d(TAG,"outer write method");
        insecureCdT.write(bytes);
    }
}
