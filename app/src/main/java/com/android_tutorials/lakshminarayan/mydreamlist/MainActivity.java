package com.android_tutorials.lakshminarayan.mydreamlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private final int ADD_DREAM_CODE = 1;
    private final String KEY_PREF = "pref_key";
    private final String KEY_DREAMS_LIST = "dreams_key";
    String date;

    //define UI objects
    TextView titleTextView;
    Button addDreamBtn;
    TextView dateTextView;
    ListView dreamsList;
    ArrayList<String> mList;
    ArrayAdapter mAdapter;
    BroadcastReceiver mReciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize UI objects
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        addDreamBtn = (Button) findViewById(R.id.addDreamBtn);
        dreamsList = (ListView) findViewById(R.id.dreamsListView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        mList = new ArrayList<String>();
        //use shared preferences to restore the dreams list on close and start of app
        String dreamsStr = getSharedPreferences(KEY_PREF,MODE_PRIVATE).getString(KEY_DREAMS_LIST,null);
        if(dreamsStr != null){
            String[] dreamsArray = dreamsStr.split(",");
            mList = new ArrayList<String>(Arrays.asList(dreamsArray));
        }

        //link the array list to list view using adapter. here adapter takes 3 arguments(context, type of list and list)
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, mList);
        dreamsList.setAdapter(mAdapter);

        //call method to delete an item from the list when user taps on a specific item
        dreamsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dreamSelected(position);
            }
        });

        //broadcast receiver to handle current time display
         mReciver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Intent.ACTION_TIME_TICK)){
                    dateTextView.setText(getCurrentTime());
                }
            }
        };


    }

    //method to display date and time in IST
    private String getCurrentTime(){
        TimeZone timeZone = TimeZone.getDefault();
        Calendar calendar = Calendar.getInstance(timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    //method to delete item from list
    private void dreamSelected(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setTitle("Dream");

        alertDialogBuilder.setMessage(mList.get(position))
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    //method to handle add dream button click
    protected void addDreamClicked(View view){
        //intent to start a new activity(intent sent with key)
        Intent intent = new Intent(MainActivity.this,DreamDescriptionActivity.class);
        startActivityForResult(intent, ADD_DREAM_CODE);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == ADD_DREAM_CODE){
            if(resultCode == RESULT_OK){
                String dream = data.getStringExtra(DreamDescriptionActivity.KEY_DREAM_DESC);
                mList.add(dream);
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onPause(){
        super.onPause();

        if(mReciver != null){
            try{
                unregisterReceiver(mReciver);
            } catch (IllegalArgumentException e){
                Log.e(TAG,"time tick receiver not received",e);
            }
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        registerReceiver(mReciver, new IntentFilter(Intent.ACTION_TIME_TICK));
        dateTextView.setText(getCurrentTime());
    }

    @Override
    protected void onStop(){
        super.onStop();

        //use shared preference to store dreams list so that the app can be remember after app close and reopen
        StringBuilder dreamsStr = new StringBuilder();
        for(String s : mList){
            dreamsStr.append(s);
            dreamsStr.append(",");
        }
        getSharedPreferences(KEY_PREF,MODE_PRIVATE).edit()
                .putString(KEY_DREAMS_LIST,dreamsStr.toString()).commit();
    }


}
