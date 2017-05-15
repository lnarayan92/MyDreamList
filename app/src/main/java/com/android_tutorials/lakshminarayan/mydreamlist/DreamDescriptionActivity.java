package com.android_tutorials.lakshminarayan.mydreamlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DreamDescriptionActivity extends AppCompatActivity {

    public static final String KEY_DREAM_DESC = "dream";

    TextView descriptionLableTextView;
    EditText descriptionEditView;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dream_description);

        descriptionEditView = (EditText) findViewById(R.id.addDreamEditView);


    }

    public void addDreamToBucket(View view){

        String dreamDescription = descriptionEditView.getText().toString();

        if(!dreamDescription.isEmpty()){

            Intent result = new Intent();
            result.putExtra(KEY_DREAM_DESC,dreamDescription);
            setResult(RESULT_OK, result);
        }
        else{
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
