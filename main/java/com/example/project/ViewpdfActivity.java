package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ViewpdfActivity extends AppCompatActivity {
    String urls;
    PDFView pdfview;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);
        pdfview = findViewById(R.id.abc);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        urls = getIntent().getStringExtra("url");
        Log.e("TEST", "URLs are " + urls);
        new RetrivePdfStream().execute("https://firebasestorage.googleapis.com/v0/b/mini-project-6ae5c.appspot.com/o/_question%20bank%20(1).pdf?alt=media&token=03c1b136-d96d-428f-9506-674a34111286");
        //new RetrivePdfStream().execute("https://firebasestorage.googleapis.com/v0/b/mini-project-6ae5c.appspot.com/o/Coursera%2053AQS3ZNCAXS.pdf?alt=media&token=3430735b-c306-4f3d-a5cf-bc9dd580ac59");
    }

    class RetrivePdfStream extends AsyncTask<String, Void, InputStream> implements com.example.project.RetrivePdfStream {

        @Override
        protected InputStream doInBackground(String... strings) {

            FirebaseStorage storage = FirebaseStorage.getInstance();

            StorageReference storageRef = storage.getReference();
            StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/mini-project-6ae5c.appspot.com/o/_question%20bank%20(1).pdf?alt=media&token=03c1b136-d96d-428f-9506-674a34111286");

            try {
                File localFile = File.createTempFile("test", "pdf");

                httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Local temp file has been created
                        Log.e("TEST", "File downloaded");
                        dialog.dismiss();
                        pdfview.fromFile(localFile).load();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.e("TEST", "Eror" + exception.toString());
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);

                Log.e("TEST", "URL is " + strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                Log.e("TEST", "Response code: " + urlConnection.getResponseCode());
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                }

            } catch (IOException e) {
                Log.e("TEST", e.toString());
                return null;
            }
            return inputStream;
        }

        @Override
        public void onPossExecute(InputStream inputStream) {
            Log.e("TEST", "on post execute: " + inputStream);
            pdfview.fromStream(inputStream).load();
            dialog.dismiss();
        }

        }
}