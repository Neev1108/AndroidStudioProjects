package edu.sjsu.android.androidmultithreading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ThreadDownloadActivity extends Activity {

    ImageView IMAGE;
    TextView URL_TEXTBOX;
    ProgressDialog DOWNLOAD_PROGRESS;
    Bitmap BITMAP = null;
    URL url;


    //Messages Model Handler
    //main UI thread will set handler for seeing messages from background thread
    //checks to see if image is not null, or sends a toast
    Handler message_handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (BITMAP == null){
                Toast.makeText(getApplicationContext(), "Message download failed or image not received", Toast.LENGTH_SHORT).show();
            }
            IMAGE.setImageBitmap(BITMAP);
            DOWNLOAD_PROGRESS.dismiss();
        }
    };


    // Handler and background/foreground runnable for runnable model

    Handler runnable_handler = new Handler();
    Runnable foregroundRunnable = new Runnable(){
        @Override
        public void run() {
            if (BITMAP == null){
                Toast.makeText(getApplicationContext(), "Runnable download failed or image not received", Toast.LENGTH_SHORT).show();
            }
            IMAGE.setImageBitmap(BITMAP);
            DOWNLOAD_PROGRESS.dismiss();
        }
    };

    private Runnable background_task = new Runnable() {
        @Override
        public void run() {
            try{
               BITMAP = downloadBitmap(URL_TEXTBOX.getText().toString());
            } catch(Exception e){
                e.printStackTrace();
            }
            runnable_handler.post(foregroundRunnable);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IMAGE = (ImageView) findViewById(R.id.imageView);
        URL_TEXTBOX = (TextView) findViewById(R.id.editText);

        DOWNLOAD_PROGRESS = new ProgressDialog(this);
        DOWNLOAD_PROGRESS.setTitle("Download");
        DOWNLOAD_PROGRESS.setCancelable(false);


    }



    Bitmap downloadBitmap (String url) throws IOException {
        // ... you fill in here
        this.url = new URL(url);
        HttpURLConnection urlC = (HttpURLConnection) this.url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlC.getInputStream());
            Bitmap myBitmap = BitmapFactory.decodeStream(in);
            if (myBitmap == null){
                System.out.println("Images are not downloading");
            }
            else{
                System.out.println("Images are downloading");
            }
            return myBitmap;
        } finally {
            urlC.disconnect();
        }
    }

    public void runRunnable(View view) {
        // ... you fill in here
        DOWNLOAD_PROGRESS.setMessage("Downloading via runnable thread");
        DOWNLOAD_PROGRESS.show();

        //create new thread
        Thread background = new Thread(background_task, "back_alias1");
        background.start();
    }


    //very simple messages model that uses handler to send and obtain messages and runs 1 background thread
    public void runMessages(View view) {
        // ... you fill in here
        DOWNLOAD_PROGRESS.setMessage("Downloading via message");
        DOWNLOAD_PROGRESS.show();

        //background thread
        Thread background = new Thread(new Runnable(){
            public void run(){
                Bitmap bitmap = null;
                try{
                    bitmap = downloadBitmap(URL_TEXTBOX.getText().toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
                //send bitmap to UI thread
                Message msg = message_handler.obtainMessage(0, bitmap);
                message_handler.sendMessage(msg);
            }
        });
        background.start();
    }


    public void runAsyncTask(View view) {
        // ... you fill in here
        new Task().execute();


    }
    public void resetImage(View view) {
        // ... you fill in here
        IMAGE.setImageResource(R.drawable.apple);
    }

    private class Task extends AsyncTask<Void, Void, Bitmap> {

        protected void onPreExecute() {
            DOWNLOAD_PROGRESS.setMessage("downloading via AsyncTask");
            DOWNLOAD_PROGRESS.show();
        }

        @Override
        protected Bitmap doInBackground(Void... unused) {
            try {
                Bitmap bitmap = downloadBitmap(URL_TEXTBOX.getText().toString());
                return bitmap;
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null){
                Toast.makeText(getApplicationContext(), "AsyncTask download failed or image not received", Toast.LENGTH_SHORT).show();
            }
            IMAGE.setImageBitmap(bitmap);
            DOWNLOAD_PROGRESS.dismiss();
        }
    }

}
