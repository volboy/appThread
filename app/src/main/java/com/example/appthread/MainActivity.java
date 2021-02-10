package com.example.appthread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Thread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            myThread = new MyThread(this, handler);
            myThread.start();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            myThread.interrupt();
        });

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        private static final int STATE_START = 0;
        private static final int STATE_LOADING = 1;
        private static final int STATE_DONE = 2;

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case STATE_START:
                    Toast.makeText(MainActivity.this, "START", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_LOADING:
                    Toast.makeText(MainActivity.this, "LOADING", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_DONE:
                    Toast.makeText(MainActivity.this, "DONE " + ((SimpleClass) msg.obj).text, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;

            }
        }
    };


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
                handler.sendEmptyMessage(0);
                Thread.sleep(3000);
                //Log.i("MY_THREAD", "Task was done");
                //activity.runOnUiThread(() -> Toast.makeText(activity, "Task was done", Toast.LENGTH_LONG).show());
                //handler.post(() -> Toast.makeText(activity, "Task was done", Toast.LENGTH_LONG).show());
                //new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(activity, "Task was done", Toast.LENGTH_LONG).show());
                handler.sendMessage(handler.obtainMessage(2, new SimpleClass("done message")));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SimpleClass {
        private String text;

        public SimpleClass(String text) {
            this.text = text;
        }
    }
}
