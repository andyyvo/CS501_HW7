package com.example.w7_p3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "BOSTON";

    //language variables
    private ListView lvLanguages;
    private final String[] languages = {"Vietnamese","Korean","Russian","Chinese","Spanish","French"};
    private String getLanguage;

    // speech to text
    static final int SPEECH_TO_TEXT = 9999; // speech to text flag to track result of intent
    private EditText edtSpeechToText;
    private Button btnClearText;
    private Button btnSpeak;

    // vacation variables
    private final String[] locations = {"Da+Nang+Vietnam", "Busan+South Korea", "St+Petersburg+Russia", "Wuhan+China", "Madrid+Spain", "Cannes+France"};
    private String chooseVacation;

    // accelerometer shake variables
    private float lastX, lastY, lastZ;  //old coordinate positions from accelerometer, needed to calculate delta.
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private static int SIGNIFICANT_SHAKE = 5000;

    //Media player
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disableAccelerometerListening();

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
                // set accelerometer acceleration values
                acceleration = 0.00f;                                         //Initializing Acceleration data.
                currentAcceleration = SensorManager.GRAVITY_EARTH;            //We live on Earth.
                lastAcceleration = SensorManager.GRAVITY_EARTH;               //Ctrl-Click to see where else we could use our phone.
                enableAccelerometerListening();                               //enable the accelerometer to start listening

                // get user input
                getLanguage = String.valueOf(parent.getItemAtPosition(position));

                if (getLanguage.equals("Vietnamese")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);

                    /* selecting location */
                    chooseVacation = locations[0];
                } else if (getLanguage.equals("Korean")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);

                    /* selecting location */
                    chooseVacation = locations[1];
                } else if (getLanguage.equals("Russian")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);

                    /* selecting location */
                    chooseVacation = locations[2];
                } else if (getLanguage.equals("Chinese")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);

                    /* selecting location */
                    chooseVacation = locations[3];
                } else if (getLanguage.equals("Spanish")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);

                    /* selecting location */
                    chooseVacation = locations[4];
                } else if (getLanguage.equals("French")) {
                    /* enable after language selected */
                    edtSpeechToText.setEnabled(true);
                    btnClearText.setEnabled(true);
                    btnSpeak.setEnabled(true);

                    /* selecting location */
                    chooseVacation = locations[5];
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
                    // mandarin
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,"zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "zh");
                    newIntent.putExtra(RecognizerIntent.EXTRA_RESULTS, "zh");
                } else if (getLanguage.equals("Spanish")) {
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
                } else if (getLanguage.equals("French")) {
                    newIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fr-FR");
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
    protected void onPause() {
        disableAccelerometerListening();
        super.onPause();
    }

    @Override
    protected void onStop() {
        disableAccelerometerListening();
        super.onStop();
    }

    /* activity intent methods */

    private void vacation() {
        //"Vanilla flavored" Implicit Intent to send a location to an App, typically some mapping app.
        //But it really depends on who's listening.  It doesn't have to be a map that consumes
        //our intent!

        try {
            //the geoLocationIntent URI doesn't like spaces...
            Intent geoLocationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + chooseVacation));  //A URI is just a consistent way of identifying a resource.

            if (getLanguage.equals("Vietnamese")) {
                /* enable after language selected */
                mp = MediaPlayer.create(MainActivity.this, R.raw.vietnamese);
            } else if (getLanguage.equals("Korean")) {
                /* enable after language selected */
                mp = MediaPlayer.create(MainActivity.this, R.raw.korean);
            } else if (getLanguage.equals("Russian")) {
                /* enable after language selected */
                mp = MediaPlayer.create(MainActivity.this, R.raw.russian);
            } else if (getLanguage.equals("Chinese")) {
                /* enable after language selected */
                mp = MediaPlayer.create(MainActivity.this, R.raw.chinese);
            } else if (getLanguage.equals("Spanish")) {
                /* enable after language selected */
                mp = MediaPlayer.create(MainActivity.this, R.raw.spanish);
            } else if (getLanguage.equals("French")) {
                /* enable after language selected */
                mp = MediaPlayer.create(MainActivity.this, R.raw.french);
            }

            // A URL is an example of a URI!
            startActivity(geoLocationIntent);  //Broadcasting our implicit intent. Let's see who answers the Bat Signal.
            // Wait, that was it?
            // Yep, Android Framework makes it quick and easy to open other Apps.
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
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



    /* accelerometer methods */

    // enable listening for accelerometer events
    private void enableAccelerometerListening() {
        // The Activity has a SensorManager Reference.
        // This is how we get the reference to the device's SensorManager.
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(
                        Context.SENSOR_SERVICE);    //The last parm specifies the type of Sensor we want to monitor


        //Now that we have a Sensor Handle, let's start "listening" for movement (accelerometer).
        //3 parms, The Listener, Sensor Type (accelerometer), and Sampling Frequency.
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);   //don't set this too high, otw you will kill user's battery.
    }

    // disable listening for accelerometer events
    private void disableAccelerometerListening() {

        //Disabling Sensor Event Listener is two step process.
        //1. Retrieve SensorManager Reference from the activity.
        //2. call unregisterListener to stop listening for sensor events
        //THis will prevent interruptions of other Apps and save battery.

        // get the SensorManager
        SensorManager sensorManager =
                (SensorManager) this.getSystemService(
                        Context.SENSOR_SERVICE);

        // stop listening for accelerometer events
        sensorManager.unregisterListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            // required to implement sensorEventListener()
        }

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            // get x, y, and z values for the SensorEvent
            //each time the event fires, we have access to three dimensions.
            //compares these values to previous values to determine how "fast"
            // the device was shaken.
            //Ref: http://developer.android.com/reference/android/hardware/SensorEvent.html

            float x = sensorEvent.values[0];   //obtaining the latest sensor data.
            float y = sensorEvent.values[1];   //sort of ugly, but this is how data is captured.
            float z = sensorEvent.values[2];

            // save previous acceleration value
            lastAcceleration = currentAcceleration;

            // calculate the current acceleration
            currentAcceleration = x * x + y * y + z * z;   //This is a simplified calculation, to be real we would need time and a square root.

            // calculate the change in acceleration        //Also simplified, but good enough to determine random shaking.
            acceleration = currentAcceleration *  (currentAcceleration - lastAcceleration);

            /* open maps with geo url for vacation spot */
            if (acceleration > SIGNIFICANT_SHAKE) {
                vacation();
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }
    };
}