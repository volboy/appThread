package com.example.appthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new myThread().start();

            }
        });

    }

    private static void longTask() {
        try {
            Thread.sleep(3000);
            Log.i("MY_THREAD", "Task was done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class myThread extends Thread{
        @Override
        public void run() {
            longTask();
        }
    }
}