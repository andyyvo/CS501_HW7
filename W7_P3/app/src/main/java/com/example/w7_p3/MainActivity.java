package com.example.w7_p3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvLanguages;
    final String[] languages = {"Vietnamese","Korean","Russian","Chinese"};

    private EditText edtSpeechToText;
    private Button btnClearText;
    private Button btnSpeak;

    private String getLanguage;

    static final int SPEECH_TO_TEXT = 9999; // speech to text flag to track result of intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* boiler plate stuffs :) */
        lvLanguages = (ListView) findViewById(R.id.lvLanguages);
        edtSpeechToText = (EditText) findViewById(R.id.edtSpeechToText);
        btnClearText = (Button) findViewById(R.id.btnClearText);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);

        /* disabling the edit text and buttons because we don't want user to use before language selected */
        edtSpeechToText.setEnabled(false);
        btnClearText.setEnabled(false);
        btnSpeak.setEnabled(false);

        /* arrayadapter interface */
        ArrayAdapter ListAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_activated_1, // type of list (simple)
                languages); // data for list

        /* display listView bind to adapter */
        lvLanguages.setAdapter(ListAdapter);

        /* onClick for ListView ITEMS */
        lvLanguages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get user input
                getLanguage = String.valueOf(parent.getItemAtPosition(position));

                if (getLanguage.equals("Vietnamese")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);
                } else if (getLanguage.equals("Korean")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);
                } else if (getLanguage.equals("Russian")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);
                } else if (getLanguage.equals("Chinese")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);
                }
            }
        });

        /* clear text in edit text */
        btnClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSpeechToText.setText("");
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // specifies free form speech
                newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                // set languages
                if (getLanguage.equals("Vietnamese")) {
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
                } else if (getLanguage.equals("Korean")) {
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                } else if (getLanguage.equals("Russian")) {
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU");
                } else if (getLanguage.equals("Chinese")) {
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,"zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_RESULTS, "zh");
                }

                // friendly prompt message
                newIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now...");

                // error catching for device exception
                try {
                    // apparently startActivityForResult is deprecated
                    // new method: https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
                    startActivityForResult(newIntent, SPEECH_TO_TEXT);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Device Not Supported " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // error checking if we got something valid
        if (!(resultCode == RESULT_OK)) {
            Toast.makeText(getApplicationContext(), "Start Activity for Result Failed. Does your device support this feature?", Toast.LENGTH_LONG).show();
            return;
        } else {
            switch (requestCode) {
                case SPEECH_TO_TEXT:
                    ArrayList<String> strData = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtSpeechToText.setText(strData.get(0)); // first item contains text (highest prob of correctness)
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}