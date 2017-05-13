package com.android_tutorials.lakshminarayan.mydreamlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int ADD_DREAM_CODE = 1;

    //define UI objects
    TextView titleTextView;
    Button addDreamBtn;
    ListView dreamsList;
    ArrayList<String> mList;
    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize UI objects
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        addDreamBtn = (Button) findViewById(R.id.addDreamBtn);
        dreamsList = (ListView) findViewById(R.id.dreamsListView);
        mList = new ArrayList<String>();

        //link the array list to list view using adapter. here adapter takes 3 arguments(context, type of list and list)
        mAdapter = new ArrayAdapter(this,
                                    android.R.layout.simple_expandable_list_item_1,
                                    mList);
        dreamsList.setAdapter(mAdapter);



    }

    //method to handle add dream button click
    protected void addDreamClicked(View view){
        //intent to start a new activity(intent sent with key)
        Intent intent = new Intent(MainActivity.this,DreamDescriptionActivity.class);
        startActivityForResult(intent, ADD_DREAM_CODE);
    }

}
