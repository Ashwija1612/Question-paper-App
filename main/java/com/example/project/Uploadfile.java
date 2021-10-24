package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Uploadfile extends AppCompatActivity{
    private static final int PICK_PDF_CODE = 234 ;
    private Button uploadfile, choosefile;

    private Uri pathfile;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfile);

        storageReference = FirebaseStorage.getInstance().getReference();
        uploadfile = (Button) findViewById(R.id.btn_upload);
        choosefile = (Button) findViewById(R.id.btn_choose);

        uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadFileToFirebase();
            }
        });
        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("BTN", "Pressed choose files");
                showFileChooser();
            }
        });
    }

    private void showFileChooser(){
        Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
        intentPDF.setType("application/pdf");
        intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intentPDF, "Select a pdf file"), PICK_PDF_CODE);
    }

    public   void uploadFileToFirebase() {

        if (pathfile != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading....");
            progressDialog.show();

            StorageReference riversRef = storageReference.child("application/pdf");

            riversRef.putFile(pathfile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            return;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) + "% Uploading...");
                        }
                    })
            ;
        } else {
            //display a toast

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_CODE && resultCode ==RESULT_OK && data !=null && data.getData() !=null){
            pathfile = data.getData();
        }
    }

//    @Override
//    public void onClick(View v) {
//        if (v == choosefile)
//        {
//            //choose the pdf
//            showFileChooser();
//        } else if (v == uploadfile)
//        {
//            //store the file in the firebase
//            uploadFile();
//        }
//    }
}