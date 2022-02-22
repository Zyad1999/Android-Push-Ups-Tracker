package com.example.pushups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;


//A Full explanation of the code is included in the report file please check it if
//any part of the code is not understandable.

public class MainActivity extends AppCompatActivity {
    //Declare Views
    TextView day;
    TextView month;
    TextView week;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign Views
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        week = findViewById(R.id.week);

        //Declare Variables
        //current dates
        int today;
        int thisMonth;
        int thisWeek;
        //we have to use Integer instead of int because we will need to use .toString() on them.
        //counters stored in the shared preferences
        Integer pday;
        Integer pmonth;
        Integer pweek;

        //Assigning the current date
        SharedPreferences h=getSharedPreferences("MR",MODE_PRIVATE);
        Calendar cal = Calendar.getInstance();
        thisMonth = cal.get(Calendar.DAY_OF_MONTH);
        thisWeek = cal.get(Calendar.WEEK_OF_MONTH);
        today = cal.get(Calendar.DAY_OF_WEEK);

        //If there is a user we get his shared preferences
        if(h.getInt("month",0)!=0){

            //Getting the counters data stored in the shared preferences
            pday = h.getInt("pday",0);
            pmonth = h.getInt("pmonth",0);
            pweek = h.getInt("pweek",0);

            //Check if the month changed
            if(thisMonth == h.getInt("month",0)){
                //set the text to the value stored in the shared preferences
                month.setText(pmonth.toString());
                //Check if the week changed
                if(thisWeek == h.getInt("week",0)){
                    week.setText(pweek.toString());
                    //check if the day changed
                    if(today == h.getInt("day",0)){
                        day.setText(pday.toString());
                    }else {
                        //only day changed so we set the value to zero
                        h.edit().putInt("pday",0).commit();
                        pday = h.getInt("day",0);
                        day.setText(pday.toString());
                        //store the new date in the shared preferences
                        h.edit().putInt("month",thisMonth).putInt("week",thisWeek).putInt("day",today).commit();
                    }
                }else{
                    //week and day changed so we set their value to zero
                    h.edit().putInt("pweek",0).putInt("pday",0).commit();
                    pday = h.getInt("day",0);
                    pweek = h.getInt("week",0);
                    week.setText(pweek.toString());
                    day.setText(pday.toString());
                    //store the new date in the shared preferences
                    h.edit().putInt("month",thisMonth).putInt("week",thisWeek).putInt("day",today).commit();
                }
            }else{
                //month and week and day changed so we set their values to zero
                h.edit().putInt("pmonth",0).putInt("pweek",0).putInt("pday",0).commit();
                pday = h.getInt("day",0);
                pweek = h.getInt("week",0);
                pmonth = h.getInt("month",0);
                week.setText(pweek.toString());
                day.setText(pday.toString());
                month.setText(pmonth.toString());
                //store the new date in the shared preferences
                h.edit().putInt("month",thisMonth).putInt("week",thisWeek).putInt("day",today).commit();
            }
        }else{
            //this case a new user with no shred preferences
            getSharedPreferences("MR",MODE_PRIVATE)
                    .edit()
                    .putInt("month",thisMonth)
                    .putInt("week",thisWeek)
                    .putInt("day",today)
                    .commit();
        }
    }

    public void train(View view) {
        startActivity(new Intent(this,Train.class));
    }
}