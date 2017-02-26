package com.example.luxiaoju.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button mBtnDownload;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDownload = (Button) findViewById(R.id.btn_download);
        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download();
                    }
                }).start();
            }
        });
    }

    private void download() {
        String urlStr = "https://raw.githubusercontent.com/ddup4/filedownload/master/README.md";

        Request request = new Request.Builder().url(urlStr).build();
        FileOutputStream fos = null;
        try {
            Response response = client.newCall(request).execute();

            File filePath  = Environment.getExternalStorageDirectory();
            File fileName = new File(filePath,"text.txt");
            if (!fileName.exists()) {
                fileName.createNewFile();
            }
            fos = new FileOutputStream(fileName);
            fos.write(response.body().bytes());
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

}
