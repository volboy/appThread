package com.example.appthread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            myThread = new MyThread(this);
            myThread.start();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            myThread.interrupt();
        });

    }

    private static void longTask(Activity activity) {
        try {
            Thread.sleep(3000);
            //Log.i("MY_THREAD", "Task was done");
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Task was done", Toast.LENGTH_LONG).show();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class MyThread extends Thread {
        private Activity activity;

        public MyThread(Activity activity) {
            super();
            this.activity = activity;
        }

        @Override
        public void run() {
            for (int i = 0; i < 200; i++) {
                if (isInterrupted()) {
                    return;
                }
                longTask(activity);
            }
        }
    }
}