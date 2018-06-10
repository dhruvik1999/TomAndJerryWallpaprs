package com.example.dhruvik.tomjerrywallpaprs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class FirstHome extends AppCompatActivity {
    ImageView imageView;
    Button next;
    Bitmap myBitmap;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_home);

        imageView = (ImageView)this.findViewById(R.id.image);
        next = (Button)this.findViewById(R.id.next);

        next.setVisibility(View.INVISIBLE);

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                DownloadImg downloadImg = new DownloadImg();
                try {
                    Log.i("BOund",Data.photoUrl.get(1));
                    String path = Data.photoUrl.get(count);
                    myBitmap = downloadImg.execute(path).get();
                    imageView.setImageBitmap(myBitmap);

                    next.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                DownloadImg downloadImg = new DownloadImg();
                try {
                    String path = Data.photoUrl.get(count);
                    myBitmap = downloadImg.execute(path).get();
                    imageView.setImageBitmap(myBitmap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });


        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.backgroundimageshd.com/gallery/tom-jerry-wallpapers/");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    int data = reader.read();

                    while (data != -1) {
                        Data.htmlData = Data.htmlData + (char) data;

                        data = reader.read();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    Log.i("MSG","all process is done");
                    Data.getPhotoUrl();
                    handler.sendEmptyMessage(0);
                }
            }
        };

        Thread thread = new Thread(run);
        thread.start();
    }

    public class DownloadImg extends AsyncTask<String , Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream is = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
