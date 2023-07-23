package com.example.tableappv1;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
public class Raw_Code {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String uniqueWords_byLetter(ArrayList<Dishes> input) {
        ArrayList<Dishes> words = input;//so that arraylist uniqueWords contains all unique words
        String sorted="";
        Map<String, Integer> map = new HashMap<>();
        List<String> outputArray = new ArrayList<>();
        List<Map<String, Integer>> trial = new ArrayList<>();
        for (Dishes current : words) {
            String s = current.dishName;
            int count = map.getOrDefault(s, 0);
            map.put(s, count + 1);
            outputArray.add(s);
            trial.add(map);
        }
        System.out.println(map);
        sorted= map.toString();
        Log.d("MAP",sorted);
        return sorted;
    }


    public static class Dishes {
        int price; //costprice of every meal
        int brewingTime; //prep time for every meal
        String dishName;

        Dishes(int price,int brewingTime,String dishName){
            //declare every meal
            this.price=price;
            this.brewingTime=brewingTime;
            this.dishName=dishName;
        }
        public int getPrice(){
            return price;
        }
        public int getBrewingTime(){
            return brewingTime;
        }
        public String toString(){
            return this.dishName;
        }

    }

    public static class Appetizers {
        int num; //num of items
        String dishName;

        Appetizers(int num,String dishName){
            this.num = num;
            this.dishName = dishName;
        }
        public int getNum(){ return num; }
        public String toString(){
            return this.dishName;
        }
    }


    //  public static class Hotel {
    //    static double bill = 0; //total bill
    //   static int waitingTime = 0; //total prep time which counts down wvery minute
    //   static boolean exit=false; // to exit the hotel
    //    static boolean payment=false; //to check if payment is done before leaving
    //   static Timer timer = new Timer();  //to do a countdiwn for watinig time for the order
    //  static TimerTask task = new Stopper();

    //   static ArrayList<Dishes> tableOrders= new ArrayList<>();


/*
            public static void Options() {

              //  startWaitingTime(); //timer declaration
                while(!Hotel.exit) {
                    //while loop executes while exit is false
                    System.out.println("\n****press 1 for MENU" +
                            "\npress 2 for bill" +
                            "\npress 3 for waiting time" +
                            "\npress 4 to see orders" +
                            "\n press 5 to exit");
                    Scanner sc = new Scanner(System.in);
                    int opt = sc.nextInt();
                    switch (opt) {
                        case 1: {
                            Menu();
                            break;
                        }

                        case 2: {
                            Bill(); ///Bill function asks for coupon
                            System.out.println(bill); //prints bill
                            payment=true;
                            break;
                        }


                        case 3: {
                            System.out.println(waitingTime + " mins");
                            break;
                        }
                        case 4:{
                            System.out.println(tableOrders.toString());// tableOrders is an arralist of type dishes which contains every ordered dish..
                            //dishes is a class with string dishName which i want to print here for every dish in the arraylist
                            break;
                        }
                        case 5: {

                            if(!payment) {
                                System.out.println("\nPLEASE PAY THE BILL");
                            }
                            else{
                                exit = true;
                                //    task.cancel();//terminates timer task
                               //      timer.cancel();
                            }
                            break;
                        }
                    }
                }

            }

 */
/*
            public static void Menu() {
                System.out.println("press 1 for continental" +
                        "\n press 2 for chinese");
                Scanner sc = new Scanner(System.in);
                int opt = sc.nextInt();
                switch (opt) {
                    case 1: {
                        Cuisine.Continental();
                        break;
                    }
                    case 2: {
                        Cuisine.Chinese();
                        break;
                    }
                }
            }

 */

    public static double Bill(){
        System.out.println("Enter Discount coupon:");
        Scanner sc = new Scanner(System.in);
        String couponCode= sc.next();
        if(couponCode.equals("freefood"))
            Stuff.bill= Stuff.bill - Stuff.bill*0.2;      //20 percent discount
        else System.out.println("no disount");
        return Stuff.bill;
    }
    static class Stopper extends TimerTask //definfing task to be performed
            // ...waiting time decreases by 1 every minute until its 0
    {
        public static int i = 0;
        public void run()
        {
            if(Stuff.waitingTime>0){
                Stuff.waitingTime--;

            }

            else System.out.println("no waiting time");

        }
    }
    public static void startWaitingTime(){

//starts timer task and declares
        Stuff.timer.schedule(Stuff.task, 2000, 1000);

    }
    public static void  cancelWaitingTime(){
        Stuff.timer.cancel();
    }



// contains cuisine NEED TO ADD COMMON FEATURES AND INHERITANCE
    // MAKE DISHES AS OBJECTS WITH UNIQUE PRICE AND WAITING TIME
    // BILL ADDS PRICE OF EACH OBJECT AS SOON AS THEY GET ADDED THEREFORE BILL= BILL + OBJECT.PRICE AND TIME = TIME + OBJECT.BREWTIME
    //STIRNG ARRAYLIST KEEPS TRAC OF ORDERS AND PRINTS WITH BILL
/*
            public static class Cuisine {
                static Dishes burger = new Dishes(20,40,"burger");
                static Dishes pasta = new Dishes(40,35,"pasta");
                static Dishes noodles = new Dishes(44,15,"noodles");
                static Dishes momos = new Dishes(10,55,"momos");


                //to order continental
                public static void Continental() {

                    System.out.println("press 1 for burger for Rs20 and waiting time 40 mins" +
                            "\npress 2 for pasta for Rs40 and waiting time 35" +
                            "mins");
                    Scanner sc = new Scanner(System.in);
                    int opt = sc.nextInt();
                    switch (opt) {
                        case 1: {
                            bill = bill+ burger.getPrice();
                            waitingTime = waitingTime + burger.getBrewingTime();
                            tableOrders.add(burger);
                            break;
                        }
                        case 2: {
                            bill = bill + pasta.getPrice();
                            waitingTime = waitingTime + pasta.getBrewingTime();
                            tableOrders.add(pasta);
                            break;
                        }
                    }
                }

                //to order chinese cuisine
                public static void Chinese() {
                    System.out.println("press 1 for noodles for Rs.15 and waiting time 15 mins" +
                            "\n press 2 for momos for Rs10 and waiting time 55 mins");
                    Scanner sc = new Scanner(System.in);
                    int opt = sc.nextInt();
                    switch (opt) {
                        case 1: {
                            bill = bill + noodles.getPrice();
                            waitingTime = waitingTime + noodles.getBrewingTime();
                            tableOrders.add(noodles);
                            break;
                        }
                        case 2: {
                            bill = bill + momos.getPrice();
                            waitingTime = waitingTime + momos.getBrewingTime();
                            tableOrders.add(momos);
                            break;
                        }
                    }
                }
            }


 */

    // }




        /* public static void main(String[] args) {
            System.out.println("Welcome to our Virtual Restaurant");
            }
*/
}
