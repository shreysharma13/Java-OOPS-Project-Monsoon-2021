package com.example.tableappv1;

import static com.example.tableappv1.Raw_Code.cancelWaitingTime;
import static com.example.tableappv1.Raw_Code.startWaitingTime;
import static com.example.tableappv1.Raw_Code.uniqueWords_byLetter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String CODE = "code";
    ActionBar actionbar;
    String TAG = "tableappmain";
    TextView textview;
    TextView headingMenu;
    Button button;
    Button buttonSend;
    Button mojito;
    EditText tablenofield;
    EditText name;
    EditText phno;
    EditText orderBox;
    Editable tableEdt;
    Editable nameEdt;
    Editable phnoEdt;
    Editable orderEdt;
    Editable codeEdt;
    TextView menu1heading;
    TextView appetizer;
    ImageView snackpic;
    TextView drink;
    ImageView drinkpic;
    Button next2;
    Button chickenwing;
    Button onionring;
    Button coke;
    Button coldcoffee;
    Button fries;
    Button nachos;
    Button gingerale;
    TextView menu2heading;
    TextView maincourseveg;
    TextView maincoursenonveg;
    Button next3;
    ImageView vegpic;
    Button bakedveg;
    Button vegpasta;
    Button burger;
    Button sandwich;
    Button chickenpasta;
    Button lasagne;
    Button hamburger;
    Button chickensandwich;
    ImageView hotdog;
    TextView menu3heading;
    TextView dessert;
    ImageView dessertpic;
    Button next4;
    Button cheesecake;
    Button brownie;
    Button sundae;
    Button custard;
    TextView scantopay;
    TextView billtext;
    Button next5;
    EditText codebox;
    TextView reciept;
    ImageView qrcode;
    TextView paymenttest;
    TextView thanks;
    TextView waittime;
    Button next6;
    Button wait;

    Timer timer;
    TimerTask timertask;
    static int i = 0;

    String[] dishesArr;
    BluetoothAdapter bAdapter;
    BluetoothSocket bSocket;
    Set<BluetoothDevice> pairedDevices;
    String targetName = "2fa0b6a2";
    String targetMAC;
    UUID targetuuid = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    InputStream ipstream;
    OutputStream opstream;
    Order order;
    String tablenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Table App");
        textview = (TextView) findViewById(R.id.textView);
        textview.setText("Welcome to our Restaurant!");
        actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e2b147")));
        actionbar.setTitle(Html.fromHtml("<font color=\"black\">" + "Table App" + "</font>"));

        button = (Button) findViewById(R.id.button);
        button.setText("Browse Menu");
        mojito = (Button) findViewById(R.id.button_mojito);
        mojito.setVisibility(View.GONE);
        menu1heading = (TextView) findViewById(R.id.textViewMenu1);
        menu1heading.setVisibility(View.GONE);
        appetizer = (TextView) findViewById(R.id.textView7);
        appetizer.setVisibility(View.GONE);
        snackpic = (ImageView) findViewById(R.id.imageView);
        snackpic.setVisibility(View.GONE);
        drink = (TextView) findViewById(R.id.textView6);
        drink.setVisibility(View.GONE);
        drinkpic = (ImageView) findViewById(R.id.imageView2);
        drinkpic.setVisibility(View.GONE);
        next2 = (Button) findViewById(R.id.button_next2);
        next2.setOnClickListener(this);
        next2.setVisibility(View.GONE);
        fries = (Button) findViewById(R.id.button_frenchFries);
        fries.setVisibility(View.GONE);
        nachos = (Button) findViewById(R.id.button_nachos);
        nachos.setVisibility(View.GONE);
        onionring = (Button) findViewById(R.id.button_onionRings);
        onionring.setVisibility(View.GONE);
        chickenwing = (Button) findViewById(R.id.button_ChickenWings);
        chickenwing.setVisibility(View.GONE);
        coke = (Button) findViewById(R.id.button_coke);
        coke.setVisibility(View.GONE);
        gingerale = (Button) findViewById(R.id.button_gingerale);
        gingerale.setVisibility(View.GONE);
        coldcoffee = (Button) findViewById(R.id.button_ColdCoffee);
        coldcoffee.setVisibility(View.GONE);

        menu2heading = (TextView) findViewById(R.id.textViewMenu2);
        maincourseveg = (TextView) findViewById(R.id.textView8);
        maincoursenonveg = (TextView) findViewById(R.id.textView9);
        next3 = (Button) findViewById(R.id.button_next3);
        next3.setOnClickListener(this);
        vegpic = (ImageView) findViewById(R.id.imageView3);
        bakedveg = (Button) findViewById(R.id.button_bakedVegetables);
        vegpasta = (Button) findViewById(R.id.button_VegPasta);
        burger = (Button) findViewById(R.id.button_cheeseburger);
        sandwich = (Button) findViewById(R.id.button_VegSandwich);
        chickenpasta = (Button) findViewById(R.id.button_chickenPasta);
        lasagne = (Button) findViewById(R.id.button_chickenLasagne);
        hamburger = (Button) findViewById(R.id.button_hamburger);
        chickensandwich = (Button) findViewById(R.id.button_chickenSandwich);
        hotdog = (ImageView) findViewById(R.id.imageView5);
        menu2heading.setVisibility(View.GONE);
        maincourseveg.setVisibility(View.GONE);
        maincoursenonveg.setVisibility(View.GONE);
        next3.setVisibility(View.GONE);
        vegpic.setVisibility(View.GONE);
        bakedveg.setVisibility(View.GONE);
        vegpasta.setVisibility(View.GONE);
        burger.setVisibility(View.GONE);
        sandwich.setVisibility(View.GONE);
        chickenpasta.setVisibility(View.GONE);
        lasagne.setVisibility(View.GONE);
        hamburger.setVisibility(View.GONE);
        chickensandwich.setVisibility(View.GONE);
        hotdog.setVisibility(View.GONE);

        menu3heading = (TextView) findViewById(R.id.textView17);
        dessert = (TextView) findViewById(R.id.textView18);
        dessertpic = (ImageView) findViewById(R.id.imageView4);
        next4 = (Button) findViewById(R.id.button_next4);
        next4.setOnClickListener(this);
        cheesecake = (Button) findViewById(R.id.button_cheesecake);
        brownie = (Button) findViewById(R.id.button_chocolateBrownie);
        sundae = (Button) findViewById(R.id.button_iceCream);
        custard = (Button) findViewById(R.id.button_custard);
        menu3heading.setVisibility(View.GONE);
        dessert.setVisibility(View.GONE);
        dessertpic.setVisibility(View.GONE);
        next4.setVisibility(View.GONE);
        cheesecake.setVisibility(View.GONE);
        brownie.setVisibility(View.GONE);
        sundae.setVisibility(View.GONE);
        custard.setVisibility(View.GONE);

        scantopay = (TextView) findViewById(R.id.textView16);
        billtext = (TextView) findViewById(R.id.text_bill);
        next5 = (Button) findViewById(R.id.button_next5);
        next5.setOnClickListener(this);
        codebox = (EditText) findViewById(R.id.box_code);
        reciept = (TextView) findViewById(R.id.text_reciept);
        qrcode = (ImageView) findViewById(R.id.imageView6);
        paymenttest = (TextView) findViewById(R.id.textView10);
        scantopay.setVisibility(View.GONE);
        billtext.setVisibility(View.GONE);
        next5.setVisibility(View.GONE);
        codebox.setVisibility(View.GONE);
        reciept.setVisibility(View.GONE);
        qrcode.setVisibility(View.GONE);
        paymenttest.setVisibility(View.GONE);

        thanks = (TextView) findViewById(R.id.text_thank);
        waittime = (TextView) findViewById(R.id.text_waittime);
        next6 = (Button) findViewById(R.id.button_next6);
        next6.setOnClickListener(this);
        wait = (Button) findViewById(R.id.button_waittime);
        thanks.setVisibility(View.GONE);
        waittime.setVisibility(View.GONE);
        next6.setVisibility(View.GONE);
        wait.setVisibility(View.GONE);

        tablenofield = (EditText) findViewById(R.id.tableNoBox);
        name = (EditText) findViewById(R.id.editText);
        phno = (EditText) findViewById(R.id.editText2);
        buttonSend = (Button) findViewById(R.id.button3);
        buttonSend.setOnClickListener(this);
        buttonSend.setVisibility(View.GONE);

        orderBox = (EditText) findViewById(R.id.orderBox3);
        orderBox.setVisibility(View.GONE);
        headingMenu = (TextView) findViewById(R.id.headingMenu2);
        headingMenu.setVisibility(View.GONE);
        headingMenu.setTextSize(25.0F);
        orderBox.setText("");
        button.setOnClickListener(this);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View view){
        if(view.getId()==R.id.button){

            tableEdt = tablenofield.getText();
            nameEdt = name.getText();
            phnoEdt = phno.getText();
            if(phnoEdt.toString().length()==10){
                connect(targetuuid);
                if(nameEdt.toString().length()==0){
                    order = new Order("Anonymous",phnoEdt.toString(),tableEdt.toString());
                }
                else{
                    order = new Order(nameEdt.toString(),phnoEdt.toString(),tableEdt.toString());
                }

                textview.setVisibility(View.GONE);
                tablenofield.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                phno.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                /*orderBox.setText("");
                headingMenu.setVisibility(View.VISIBLE);
                orderBox.setVisibility(View.VISIBLE);
                buttonSend.setVisibility(View.VISIBLE);*/
                mojito.setVisibility(View.VISIBLE);
                menu1heading.setVisibility(View.VISIBLE);
                appetizer.setVisibility(View.VISIBLE);
                snackpic.setVisibility(View.VISIBLE);
                drink.setVisibility(View.VISIBLE);
                drinkpic.setVisibility(View.VISIBLE);
                next2.setVisibility(View.VISIBLE);
                fries.setVisibility(View.VISIBLE);
                nachos.setVisibility(View.VISIBLE);
                onionring.setVisibility(View.VISIBLE);
                chickenwing.setVisibility(View.VISIBLE);
                coke.setVisibility(View.VISIBLE);
                gingerale.setVisibility(View.VISIBLE);
                coldcoffee.setVisibility(View.VISIBLE);
            }
            else{
                Toast.makeText(this,"Please Enter a Valid Phone Number",Toast.LENGTH_LONG).show();
            }






        }
        if(view.getId()==R.id.button3){
            orderEdt = orderBox.getText();
            dishesArr = orderEdt.toString().split(" ");
            for(String dish: dishesArr){
                order.addDish(dish);
            }
            name.setText("");
            phno.setText("");
            order.setDateTime();
            send(order.toString());
            int delay = 500;
            long start = System.currentTimeMillis();
            while(start > System.currentTimeMillis() - delay);
            send("---");
            cleanup();
            buttonSend.setVisibility(View.GONE);
            orderBox.setVisibility(View.GONE);
            headingMenu.setVisibility(View.GONE);
            textview.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            phno.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
        if(view.getId()==R.id.button_next2){
            mojito.setVisibility(View.GONE);
            menu1heading.setVisibility(View.GONE);
            appetizer.setVisibility(View.GONE);
            snackpic.setVisibility(View.GONE);
            drink.setVisibility(View.GONE);
            drinkpic.setVisibility(View.GONE);
            next2.setVisibility(View.GONE);
            fries.setVisibility(View.GONE);
            nachos.setVisibility(View.GONE);
            onionring.setVisibility(View.GONE);
            chickenwing.setVisibility(View.GONE);
            coke.setVisibility(View.GONE);
            gingerale.setVisibility(View.GONE);
            coldcoffee.setVisibility(View.GONE);
            menu2heading.setVisibility(View.VISIBLE);
            maincourseveg.setVisibility(View.VISIBLE);
            maincoursenonveg.setVisibility(View.VISIBLE);
            next3.setVisibility(View.VISIBLE);
            vegpic.setVisibility(View.VISIBLE);
            bakedveg.setVisibility(View.VISIBLE);
            vegpasta.setVisibility(View.VISIBLE);
            burger.setVisibility(View.VISIBLE);
            sandwich.setVisibility(View.VISIBLE);
            chickenpasta.setVisibility(View.VISIBLE);
            lasagne.setVisibility(View.VISIBLE);
            hamburger.setVisibility(View.VISIBLE);
            chickensandwich.setVisibility(View.VISIBLE);
            hotdog.setVisibility(View.VISIBLE);
        }
        if(view.getId()==R.id.button_next3){

            menu2heading.setVisibility(View.GONE);
            maincourseveg.setVisibility(View.GONE);
            maincoursenonveg.setVisibility(View.GONE);
            next3.setVisibility(View.GONE);
            vegpic.setVisibility(View.GONE);
            bakedveg.setVisibility(View.GONE);
            vegpasta.setVisibility(View.GONE);
            burger.setVisibility(View.GONE);
            sandwich.setVisibility(View.GONE);
            chickenpasta.setVisibility(View.GONE);
            lasagne.setVisibility(View.GONE);
            hamburger.setVisibility(View.GONE);
            chickensandwich.setVisibility(View.GONE);
            hotdog.setVisibility(View.GONE);
            menu3heading.setVisibility(View.VISIBLE);
            next4.setVisibility(View.VISIBLE);
            dessert.setVisibility(View.VISIBLE);
            dessertpic.setVisibility(View.VISIBLE);
            cheesecake.setVisibility(View.VISIBLE);
            brownie.setVisibility(View.VISIBLE);
            sundae.setVisibility(View.VISIBLE);
            custard.setVisibility(View.VISIBLE);
        }

        if(view.getId()==R.id.button_next4){

            menu3heading.setVisibility(View.GONE);
            dessert.setVisibility(View.GONE);
            dessertpic.setVisibility(View.GONE);
            next4.setVisibility(View.GONE);
            cheesecake.setVisibility(View.GONE);
            brownie.setVisibility(View.GONE);
            sundae.setVisibility(View.GONE);
            custard.setVisibility(View.GONE);

            scantopay.setVisibility(View.VISIBLE);
            billtext.setVisibility(View.VISIBLE);
            billtext.setText("Your net amount is Rs."+ Stuff.bill );
            next5.setVisibility(View.VISIBLE);
            codebox.setText("");
            codebox.setVisibility(View.VISIBLE);
            reciept.setVisibility(View.VISIBLE);
            String str = Raw_Code.uniqueWords_byLetter(Stuff.tableOrders);
            String str1= str.substring(1,str.indexOf("}"));
            reciept.setText(" " + str1.replace("+", " ").replace("=", " "));
            qrcode.setVisibility(View.VISIBLE);
            paymenttest.setVisibility(View.VISIBLE);

        }
        if(view.getId()==R.id.button_next5){
            codeEdt = codebox.getText();
            if(codeEdt.toString().equals(CODE)){

                scantopay.setVisibility(View.GONE);
                billtext.setVisibility(View.GONE);
                next5.setVisibility(View.GONE);
                codebox.setVisibility(View.GONE);
                reciept.setVisibility(View.GONE);
                qrcode.setVisibility(View.GONE);
                paymenttest.setVisibility(View.GONE);
                thanks.setVisibility(View.VISIBLE);
                waittime.setVisibility(View.VISIBLE);
                wait.setVisibility(View.VISIBLE);
                next6.setVisibility(View.VISIBLE);
                startWaitingTime();

                /*timer = new Timer();

                timertask = new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                while(true){
                                    waittime.setText("Your order will be ready in another " + Stuff.waitingTime / 60 + " mins " + Stuff.waitingTime % 60 + " seconds ");
                                }

                            }

                        });
                        i++;

                    }
                };
                timer.scheduleAtFixedRate(timertask,0, 1000);*/


            }
            else{
                Toast.makeText(this,"Enter Valid Code",Toast.LENGTH_LONG).show();
            }

        }
        if(view.getId()==R.id.button_next6){
            cancelWaitingTime();
            order.setDateTime();
            String stuffOrder = uniqueWords_byLetter(Stuff.tableOrders);
            stuffOrder = stuffOrder.replace("{","").replace("}","").replace("=","_").replace(",","");
            dishesArr = stuffOrder.split(" ");

            for(String dish: dishesArr){
                order.addDish(dish);
            }
            Log.d("MAP",order.toString());
            send(order.toString());
            int delay = 500;
            long start = System.currentTimeMillis();
            while(start > System.currentTimeMillis() - delay);
            send("---");
            cleanup();
            tablenofield.setText("");
            name.setText("");
            phno.setText("");
            thanks.setVisibility(View.GONE);
            waittime.setVisibility(View.GONE);
            //timer.cancel();
            //i = 0;
            next6.setVisibility(View.GONE);
            wait.setVisibility(View.GONE);
            textview.setVisibility(View.VISIBLE);
            tablenofield.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            phno.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            Stuff.tableOrders.clear();
            Stuff.bill = 0;
            Stuff.waitingTime = 0;
        }

    }

    public void connect(UUID uuid){
        bAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = bAdapter.getBondedDevices();
        for(BluetoothDevice device : pairedDevices){
            if(device.getName().equals(targetName)){
                Log.d(TAG,device.getName());
                targetMAC = device.getAddress();
                try {
                    bSocket = device.createRfcommSocketToServiceRecord(uuid);
                } catch (IOException e) {
                    Log.d(TAG,"failed to open socket");
                }
                break;
            }
        }
        try {
            bSocket.connect();
            //textview.setText("connected");
        } catch (IOException e) {
            Log.d(TAG,"failed to connect");
        }
        try {
            opstream = bSocket.getOutputStream();
            //textview.setText(textview.getText() + ", got stream");
        } catch (IOException e) {
            Log.d(TAG,"failed to get output stream");
        }
    }
    public void send(String order){
        byte[] data = order.getBytes();
        try {
            opstream.write(data);
            //textview.setText(textview.getText() + ", sent food");
        } catch (IOException e) {
            Log.d(TAG,"failed to send data");
        }
    }
    public void cleanup(){
        try {
            bSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bSocket = null;
        opstream = null;
    }

    public void orderFrenchFries(View view)
    {
        Button button_frenchFries = (Button) findViewById(R.id.button_frenchFries);

        //insert code to order french Fries here
        Stuff.bill = Stuff.bill + Stuff.fries.getPrice();
        Stuff.waitingTime = Stuff.waitingTime + Stuff.fries.getBrewingTime();
        Stuff.tableOrders.add(Stuff.fries);
        Log.i("info","French Fries ordered");
        Toast.makeText(getApplicationContext(), "French Fries Ordered", Toast.LENGTH_SHORT).show();


    }

    public void orderNachos(View view)
    {
        Button button_nachos = (Button) findViewById(R.id.button_nachos);
        Stuff.bill = Stuff.bill + Stuff.nachos.getPrice();
        Stuff.waitingTime = Stuff.waitingTime + Stuff.nachos.getBrewingTime();
        Stuff.tableOrders.add(Stuff.nachos);
        Log.i("info","Nachos ordered");
        Toast.makeText(getApplicationContext(), "Nachos Ordered", Toast.LENGTH_SHORT).show();
    }


    public void orderOnionRings(View view)
    {
        Button button_onionRings = (Button) findViewById(R.id.button_onionRings);
        Stuff.bill = Stuff.bill + Stuff.onionRings.getPrice();
        Stuff.waitingTime = Stuff.waitingTime + Stuff.onionRings.getBrewingTime();
        Stuff.tableOrders.add(Stuff.onionRings);
        Log.i("info","Onion Rings ordered");
        Toast.makeText(getApplicationContext(), "Onion Rings Ordered", Toast.LENGTH_SHORT).show();
    }


    public void orderChickenWings(View view)
    {
        Button button_ChickenWings = (Button) findViewById(R.id.button_ChickenWings);
        Stuff.bill = Stuff.bill + Stuff.ChickenWings.getPrice();
        Stuff.waitingTime = Stuff.waitingTime + Stuff.ChickenWings.getBrewingTime();
        Stuff.tableOrders.add(Stuff.ChickenWings);
        Log.i("info","Chicken Wings ordered");
        Toast.makeText(getApplicationContext(), "Chicken Wings Ordered", Toast.LENGTH_SHORT).show();
    }


// Drinks

    public void orderMojito(View view)
    {
        Button button_mojito = (Button) findViewById(R.id.button_mojito);
        Stuff.bill = Stuff.bill + Stuff.mojito.getPrice();
        Stuff.waitingTime = Stuff.waitingTime + Stuff.mojito.getBrewingTime();
        Stuff.tableOrders.add(Stuff.mojito);
        Log.i("info","Mojito ordered");
        Toast.makeText(getApplicationContext(), "Mojito Ordered", Toast.LENGTH_SHORT).show();
    }

    public void orderCoke (View view)
    {
        Button button_coke = (Button) findViewById(R.id.button_coke);

        //insert code to order coke here

        Stuff.bill = Stuff.bill + Stuff.coke.getPrice();
        if (Stuff.waitingTime > Stuff.coke.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.coke.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.coke);

    }

    public void orderGingerale (View view)
    {
        Button button_gingerale = (Button) findViewById(R.id.button_gingerale);

        //insert code to order gingerale here
        Stuff.bill = Stuff.bill + Stuff.gingerale.getPrice();
        if (Stuff.waitingTime > Stuff.gingerale.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.gingerale.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.gingerale);

    }

    public void orderColdCoffee (View view)
    {
        Button button_ColdCoffee = (Button) findViewById(R.id.button_ColdCoffee);

        //insert code to order Cold Coffee here
        Stuff.bill = Stuff.bill + Stuff.ColdCoffee.getPrice();
        if (Stuff.waitingTime > Stuff.ColdCoffee.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.ColdCoffee.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.ColdCoffee);


    }
    public void orderBakedVegetables (View view)
    {
        Button button_bakedVegetables = (Button) findViewById(R.id.button_bakedVegetables);
        //inset code to order baked vegetables here
        Stuff.bill = Stuff.bill + Stuff.bakedVegetables.getPrice();
        if (Stuff.waitingTime > Stuff.bakedVegetables.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.bakedVegetables.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.bakedVegetables);
        Toast.makeText(getApplicationContext(), "Vegetables Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Baked Vegetables ordered");

    }

    public void orderVegPasta (View view)
    {
        Button button_ = (Button) findViewById(R.id.button_bakedVegetables);

        //inset code to order veg pasta here
        Stuff.bill = Stuff.bill + Stuff.VegPasta.getPrice();
        if (Stuff.waitingTime > Stuff.VegPasta.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.VegPasta.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.VegPasta);
        Toast.makeText(getApplicationContext(), "Veg Pasta Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Veg Pasta ordered");

    }

    public void orderCheeseburger (View view)
    {
        Button button_ = (Button) findViewById(R.id.button_cheeseburger);

        //inset code to order cheeseburger here
        Stuff.bill = Stuff.bill + Stuff.cheeseburger.getPrice();
        if (Stuff.waitingTime > Stuff.cheeseburger.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.cheeseburger.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.cheeseburger);
        Toast.makeText(getApplicationContext(), "Cheese Burger Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Cheese Burger ordered");

    }

    public void orderVegSandwich (View view)
    {
        Button button_ = (Button) findViewById(R.id.button_VegSandwich);

        //inset code to order Veg Club Sandwich here
        Stuff.bill = Stuff.bill + Stuff.VegSandwich.getPrice();
        if (Stuff.waitingTime > Stuff.VegSandwich.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.VegSandwich.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.VegSandwich);
        Toast.makeText(getApplicationContext(), "Veg Sandwich Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Veg Sandwich ordered");

    }

    public void orderChickenPasta (View view)
    {
        Button button_ = (Button) findViewById(R.id.button_chickenPasta);

        //inset code to order Chicken Pasta here
        Stuff.bill = Stuff.bill + Stuff.chickenPasta.getPrice();
        if (Stuff.waitingTime > Stuff.chickenPasta.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.chickenPasta.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.chickenPasta);
        Toast.makeText(getApplicationContext(), "Chicken Pasta Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Chicken Pasta ordered");

    }

    public void orderChickenLasagne (View view)
    {
        Button button_ = (Button) findViewById(R.id.button_chickenLasagne);

        //inset code to order Chicken Lasagne here
        Stuff.bill = Stuff.bill + Stuff.chickenLasagne.getPrice();
        if (Stuff.waitingTime > Stuff.chickenLasagne.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.chickenLasagne.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.chickenLasagne);
        Toast.makeText(getApplicationContext(), "Chicken Lasagne Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Chicken Lasagne ordered");

    }

    public void orderHamburger (View view)
    {
        Button button_ = (Button) findViewById(R.id.button_hamburger);

        //inset code to order Hamburgers here
        Stuff.bill = Stuff.bill + Stuff.hamburger.getPrice();
        if (Stuff.waitingTime > Stuff.hamburger.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.hamburger.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.hamburger);
        Toast.makeText(getApplicationContext(), "Hamburger Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Hamburger ordered");

    }

    public void orderChickenSandwich (View view)
    {
        Button button_ = (Button) findViewById(R.id.button_chickenSandwich);

        //inset code to order Chicken Club Sandwich here
        Stuff.bill = Stuff.bill + Stuff.chickenSandwich.getPrice();
        if (Stuff.waitingTime > Stuff.chickenSandwich.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.chickenSandwich.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.chickenSandwich);
        Toast.makeText(getApplicationContext(), "Chicken Sandwich Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Chicken Sandwich ordered");


    }
    public void orderCheesecake(View view)
    {
        //Button button_cheescake = (Button) findViewById(R.id.button_cheesecake);
        Stuff.bill = Stuff.bill + Stuff.cheesecake.getPrice();
        if (Stuff.waitingTime > Stuff.cheesecake.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.cheesecake.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.cheesecake);
        Toast.makeText(getApplicationContext(), "Cheese Cake Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Cheese Cake ordered");
    }

    public void orderBrownie(View view)
    {
        //Button button_brownie = (Button) findViewById(R.id.button_chocolateBrownie);
        Stuff.bill = Stuff.bill + Stuff.chocolatebrownie.getPrice();
        if (Stuff.waitingTime > Stuff.chocolatebrownie.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.chocolatebrownie.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.chocolatebrownie);
        Toast.makeText(getApplicationContext(), "Chocolate Brownie Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Chocolate Brownie ordered");
    }

    public void orderSundae(View view)
    {
        //Button button_sundae = (Button) findViewById(R.id.button_iceCream);
        Stuff.bill = Stuff.bill + Stuff.icecream.getPrice();
        if (Stuff.waitingTime > Stuff.icecream.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.icecream.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.icecream);
        Toast.makeText(getApplicationContext(), "Ice Cream Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Ice Cream ordered");
    }

    public void orderCustard(View view)
    {
        //Button button_custard = (Button) findViewById(R.id.button_custard);
        Stuff.bill = Stuff.bill + Stuff.custard.getPrice();
        if (Stuff.waitingTime > Stuff.custard.getBrewingTime()){
            Stuff.waitingTime = Stuff.waitingTime; }
        else{
            Stuff.waitingTime = Stuff.custard.getBrewingTime(); }
        Stuff.tableOrders.add(Stuff.custard);
        Toast.makeText(getApplicationContext(), "Custard Ordered", Toast.LENGTH_SHORT).show();
        Log.i("info","Custard ordered");

    }
    public void payBill(View view)
    {
        //Button button_bill = (Button) findViewById(R.id.button_bill);
        //button_bill.setText("Your net amount is Rs."+ Stuff.bill );
    }

    public void showWaitingTime(View view)
    {
        Button button_waittime = (Button) findViewById(R.id.button_waittime);
        TextView text_wait = (TextView) findViewById(R.id.text_waittime);
        button_waittime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_wait.setText("Your order will be ready in another " + Stuff.waitingTime / 60 + " mins " + Stuff.waitingTime % 60 + " seconds ");
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showReciept(View view)
    {
        TextView rec = (TextView) findViewById(R.id.text_reciept);
        String str = Raw_Code.uniqueWords_byLetter(Stuff.tableOrders);
        String str1= str.substring(1,str.indexOf("}"));
        rec.setText(" "+ str1);
    }
}