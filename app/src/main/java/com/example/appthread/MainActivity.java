package com.example.appthread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
            myThread = new MyThread(this, new Handler());
            myThread.start();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            myThread.interrupt();
        });

    }


    private static class MyThread extends Thread {
        private Activity activity;
        private Handler handler;

        public MyThread(Activity activity, Handler handler) {
            super();
            this.activity = activity;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                //Log.i("MY_THREAD", "Task was done");
                //activity.runOnUiThread(() -> Toast.makeText(activity, "Task was done", Toast.LENGTH_LONG).show());
                handler.post(() -> Toast.makeText(activity, "Task was done", Toast.LENGTH_LONG).show());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
