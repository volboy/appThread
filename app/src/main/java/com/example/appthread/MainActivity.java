package com.example.appthread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Thread myThread;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            myThread = new MyThread(this, handler);
            myThread.start();
        });

        findViewById(R.id.btn2).setOnClickListener(v -> {
            //myThread.interrupt();
            new AsyncTask<String, Integer, Void>() {
                @SuppressLint("StaticFieldLeak")

                @Override
                protected void onPreExecute() {
                    Toast.makeText(MainActivity.this, "onPreExecute", Toast.LENGTH_SHORT).show();
                }

                @Override
                protected Void doInBackground(String... urls) {
                    Log.i("MYASYNC", "start " + urls[0]);
                    try {
                        for (int i=0; i<=100; i++){
                            Thread.sleep(100);
                            publishProgress(i);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("MYASYNC", "done");
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    Toast.makeText(MainActivity.this, "onPostExecute", Toast.LENGTH_SHORT).show();
                }

                @Override
                protected void onProgressUpdate(Integer... values) {
                    ((SeekBar) findViewById(R.id.seekBar)).setProgress(values[0]);
                }
            }.execute("http://site.com/movie.avi");
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
                    Toast.makeText(MainActivity.this, "DONE  " + ((SimpleClass) msg.obj).text, Toast.LENGTH_SHORT).show();
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
