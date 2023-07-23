package com.example.managerappv1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.chrono.JapaneseChronology;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ActionBar actionbar;
    String TAG = "MainActivity";
    TextView textview;
    ArrayList<Order> orderqueue = new ArrayList<Order>();
    Button button;
    BluetoothAdapter bAdapter;
    BluetoothServerSocket bSS;
    BluetoothSocket bS = null;
    AcceptThread insecureAT;
    InputStream ipstream;
    OutputStream opstream;
    ScanningThread scThread;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<OrderItem> orders;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Manager App");
        actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e2b147")));
        actionbar.setTitle(Html.fromHtml("<font color=\"black\">" + "Manager App" + "</font>"));
        textview = (TextView) findViewById(R.id.textView);
        textview.setVisibility(View.GONE);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setText("Start Taking Orders");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        orders = new ArrayList<>();
        adapter = new MyAdapter(orders);
        recyclerView.setAdapter(adapter);
        bAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bAdapter != null){
            if(!bAdapter.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(enableBT);
            }
        }
        textview.setText(orderqueue.toString());
        orders.add(new OrderItem("Test","Name","Number","This_2 is_4 a_5 Debugging_3 Order_1", "00:00:00"));
        adapter.notifyItemInserted(orders.size()-1);

    }
    void openSS(){
        insecureAT = new AcceptThread();
        insecureAT.start();

    }
    void testMethod(){
        Toast.makeText(this,"textMethod",Toast.LENGTH_LONG).show();
    }
    void deleteOrder(int pos){
        if(!orderqueue.isEmpty() && pos>1){
            orderqueue.remove(pos-2);
        }
        if(!orders.isEmpty()){
            orders.remove(pos);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textview.setText(orderqueue.toString());
                adapter.notifyItemRemoved(pos);
            }
        });
    }

    public void onClick(View view){
        if(view.getId()==R.id.button){
            openSS();
            //orders.add(new OrderItem("Opened SS","","",""));
            //adapter.notifyItemInserted(orders.size()-1);
            textview.setText(orderqueue.toString());
            button.setVisibility(View.GONE);
            Toast.makeText(this,"Opened SS",Toast.LENGTH_LONG).show();
        }
    }

    class AcceptThread extends Thread{
        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            try {
                tmp = bAdapter.listenUsingInsecureRfcommWithServiceRecord("managerapp", MY_UUID_INSECURE);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bSS = tmp;
        }
        @SuppressLint("LongLogTag")
        public void run(){
            Log.d(TAG,"running new AcceptThread");
            try {
                Log.d(TAG,"going to accept");
                bS = bSS.accept();
                Log.d(TAG,"accpeted");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bS!=null){
                gotClient();
            }

        }
        @SuppressLint("LongLogTag")
        public void cancel(){
            Log.d(TAG, "canceling AcceptThread");
            try{
                bSS.close();
            }
            catch(IOException e){
                Log.d(TAG,"failed to close bss");
            }
        }
    }
    public void gotClient(){
        insecureAT.interrupt();
        try {
            ipstream = bS.getInputStream();
        } catch (IOException e) {
            Log.d(TAG,"failed getting inputstream");
        }
        scThread = new ScanningThread(ipstream);
        scThread.start();

    }
    class ScanningThread extends Thread{
        InputStream instream;
        public ScanningThread(InputStream ipstream){
            instream = ipstream;
        }
        public void run(){
            String order;
            byte[] buffer = new byte[1024];
            int bytes;
            while(true){
                try{
                    bytes = instream.read(buffer);
                    order = new String(buffer,0,bytes);
                    if(order.equals("---")){
                        cleanup();
                        openSS();
                        break;
                    }
                    orderqueue.add(new Order(order));
                    orders.add(new OrderItem(orderqueue.get(orderqueue.size()-1).getTableno(),orderqueue.get(orderqueue.size()-1).getName(),orderqueue.get(orderqueue.size()-1).getPhno(),orderqueue.get(orderqueue.size()-1).getOrderstring(),orderqueue.get(orderqueue.size()-1).getDatetime()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //textview.setText(orderqueue.toString());
                            adapter.notifyItemInserted(orders.size()-1);
                        }
                    });
                }
                catch(IOException e){
                    Log.d(TAG,"failed to read");
                    cleanup();
                    openSS();
                    break;
                }
            }
        }
    }
    public void cleanup(){
        insecureAT = null;
        ipstream = null;
        try {
            bSS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bSS = null;
        bS = null;

    }
}