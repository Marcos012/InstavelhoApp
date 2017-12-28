package br.org.fundatec.instavelhoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void openQuarto1(View view) {
        Intent intent = new Intent( this, FotosActivity.class);
        startActivity(intent);
    }
    public void openQuarto2(View view) {
        Intent intent = new Intent( this, FotosActivity2.class);
        startActivity(intent);
    }

}
