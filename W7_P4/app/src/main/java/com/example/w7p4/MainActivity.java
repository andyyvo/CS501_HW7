package com.example.w7p4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView cat;
    private Animation animation1;
    private Animation animation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cat = (ImageView) findViewById(R.id.cat);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation1);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation2);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
    //respond to menu item selection
        switch (item.getItemId()) {
            case R.id.PlayAnimation1:
                //startActivity(new Intent(this, About.class));
                cat.startAnimation(animation1);
                return true;
            case R.id.PlayAnimation2:
                //startActivity(new Intent(this, Help.class));
                cat.startAnimation(animation2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}