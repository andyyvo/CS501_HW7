package com.example.w7_p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private EditText edittext;
    ImageView imageView;
    int image1;
    int image2;
    int image3;
    int image4;
    int image5;
    int imageId;
    int[] cards;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //reload
//        retrieveSharedPreferenceInfo();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView =(ImageView)findViewById(R.id.imageView);
        btn = findViewById(R.id.button);
        edittext = findViewById(R.id.edt);
        btn.setOnClickListener(this);

        // load all images as objects
        image1 = R.drawable.apple;
        image2 = R.drawable.banana;
        image3 = R.drawable.lemon;
        image4 = R.drawable.pineapple;
        image5 = R.drawable.watermellon;
        cards= new int[]{image1, image2, image3, image4, image5};
        retrieveSharedPreferenceInfo();
    }

    @Override
    public void onClick(View view)
    {
        cards= new int[]{image1, image2, image3, image4, image5};
        Random r = new Random();
        imageId=r.nextInt(5);
        imageView.setImageResource(cards[imageId]);
    }
    public void onDestroy() {
        //save state
        saveSharedPreferenceInfo();

        super.onDestroy();

    }

    void saveSharedPreferenceInfo(){
        //1. Refer to the SharedPreference Object.
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);
        //Private means no other Apps can access this.

        //2. Create an Shared Preferences Editor for Editing Shared Preferences.
        //Note, not a real editor, just an object that allows editing...

        SharedPreferences.Editor editor = simpleAppInfo.edit();

        //3. Store what's important!  Key Value Pair, what else is new...
//           editor.putString("btnClickMe", btnClickMe.getText().toString());
        editor.putString("edtText", edittext.getText().toString());
        editor.putInt("imageId", imageId);
//           editor.putString("txtView", txtView.getText().toString());

        //4. Save your information.
        editor.apply();

        Toast.makeText(this, "Shared Preference Data Updated.", Toast.LENGTH_LONG).show();
    }

    void retrieveSharedPreferenceInfo(){

        //Todo, exception handling likely needed, just in case ActivityOneInfo doesn't exit.
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);

//        String s1 = simpleAppInfo.getString("btnClickMe", "<missing>");
//        String s2 = simpleAppInfo.getString("edtText", "<missing>");
//        String s3 = simpleAppInfo.getString("txtView", "<missing>");

        //Retrieving data from shared preferences hashmap.
//        btnClickMe.setText(simpleAppInfo.getString("btnClickMe", "<missing>"));  //The second parm is the default value, eg, if the value doesn't exist.
        edittext.setText(simpleAppInfo.getString("edtText", "Default Text"));        //Shared Preferences use internal memory, not SD.
//        txtView.setText(simpleAppInfo.getString("txtView", "<missing>"));
        imageView.setImageResource(cards[simpleAppInfo.getInt("imageId", 3)]);

//

//        Toast.makeText(this, s1, Toast.LENGTH_LONG).show();


    }
}