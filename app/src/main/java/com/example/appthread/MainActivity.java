package com.example.appthread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            myThread = new MyThread();
            myThread.start();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            myThread.interrupt();
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

    private static class MyThread extends Thread {
        @Override
        public void run() {
            longTask();
        }
    }
}