package com.android_tutorials.lakshminarayan.mydreamlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DreamDescriptionActivity extends AppCompatActivity {

    TextView descriptionLableTextView;
    EditText descriptionEditView;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_description);

        descriptionEditView = (EditText) findViewById(R.id.addDreamEditView);



    }
}
