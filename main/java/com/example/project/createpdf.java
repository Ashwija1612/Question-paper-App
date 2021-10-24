package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.Manifest;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.content.DialogInterface;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

import static android.os.Environment.getExternalStorageDirectory;

final public class createpdf extends AppCompatActivity implements pdfutility.OnDocumentClose {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AppCompatEditText rowCount;
    private EditText coursename, coursecode,datee, testtime, Seme, faculty, marks1, marks2, marks3, marks4;
    Button  que1a, que1b, que2a, que2b;
    TextView que1;
    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    DatabaseReference database;
    String message;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpdf);
        rowCount = findViewById(R.id.rowCount);
        coursename = findViewById(R.id.CourseName);
        coursecode = findViewById(R.id.CourseCode);
        datee = findViewById(R.id.Date);
        testtime = findViewById(R.id.Time);
        Seme = findViewById(R.id.Sem);
        faculty = findViewById(R.id.FacultyName);
        marks1 = findViewById(R.id.mark1);
        marks2 = findViewById(R.id.marks2);
        marks3 = findViewById(R.id.marks3);
        marks4 = findViewById(R.id.marks4);
        que1a = findViewById(R.id.que1a);
        que1b = findViewById(R.id.que1b);
        que2a = findViewById(R.id.que2a);
        que2b = findViewById(R.id.que2b);
        AppCompatButton button1 = findViewById(R.id.button1);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("pdf");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                message = snapshot.getValue(String.class);
                /*Date date = Calendar.getInstance().getTime();
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        message = snapshot.getValue(String.class);
                    }
                });*/
                //message = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(createpdf.this, "Error Loading pdf", Toast.LENGTH_SHORT).show();
            }
        });
        que1a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                CharSequence options[] = new CharSequence[]{
                        "View",
                        "Cancel"
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                builder.setTitle("Choose One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == 0){
                            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                            startActivity(intent);
                        }
                        if (which == 1){
                            Log.e("TEST", "Passing url " + message);
                            Intent intent = new Intent(view.getContext(), ViewpdfActivity.class);
                            intent.putExtra("url",message);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });

        que1a.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(que1a.getContext(), "Long-tapped on: "+que1a.getText(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        que1b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                CharSequence options[] = new CharSequence[]{
                        "View",
                        "Cancel"
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                builder.setTitle("Choose One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == 0){
                            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(message));

                            startActivity(intent);
                        }
                        if (which == 1){
                            Log.e("TEST", "passing 2 url " + message);
                            Intent intent = new Intent(view.getContext(), ViewpdfActivity.class);
                            intent.putExtra("url",message);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
        que1b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(que1b.getContext(), "Long-tapped on: "+que1b.getText(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        que2a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                CharSequence options[] = new CharSequence[]{
                        "View",
                        "Cancel"
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                builder.setTitle("Choose One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which== 0){
                            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(message));

                            startActivity(intent);
                        }
                        if (which == 1){
                            Log.e("TEST", "passing 3 url " + message);
                            Intent intent = new Intent(view.getContext(), ViewpdfActivity.class);
                            intent.putExtra("url",message);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
        que2a.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(que2a.getContext(), "Long-tapped on: "+que2a.getText(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        que2b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                CharSequence options[] = new CharSequence[]{
                        "View",
                        "Cancel"
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                builder.setTitle("Choose One");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which==0){
                            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(message));

                            startActivity(intent);
                        }
                        if (which ==1){
                            Log.e("TEST", "passing 4 url " + message);
                            Intent intent = new Intent(view.getContext(), ViewpdfActivity.class);
                            intent.putExtra("url",message);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
        que2b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(que2b.getContext(), "Long-tapped on: "+que2b.getText(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //String path = getExternalStorageDirectory().toString() + "/Sample.pdf";
                File path= new File(Environment.getExternalStorageDirectory() + "/Sample.pdf");

                Log.e("PATH_TEST", "PATH is " + path.getAbsolutePath());
//                FileOutputStream path= null;
//                try {
//                    path = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Sample.pdf");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                try
                {
                    pdfutility.createPdf(v.getContext(),createpdf.this,getSampleData(), String.valueOf(path),true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("TEST_PDF", "Error is " + e.toString());
                    Log.e(TAG,"Error Creating Pdf");
                    Toast.makeText(v.getContext(),"Error Creating Pdf",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPDFDocumentClose(File file)
    {
        Toast.makeText(this,"Sample Pdf Created",Toast.LENGTH_SHORT).show();
    }

    private List<Paragraph> getSampleData()
    {
        int count = 1;
        if(!TextUtils.isEmpty(rowCount.getText()))
        {
            count = Integer.parseInt(rowCount.getText().toString());
        }

        List<Paragraph> temp = new ArrayList<>();
        for (int i = 0; i < 1; i++)
        {
            //temp.add(new Paragraph(new Phrase("Course Name: "+coursename.getText()+" Course Code: "+coursecode.getText())));
            temp.add(new Paragraph(new Phrase("Course Name: "+coursename.getText())));
            temp.add(new Paragraph(new Phrase("Course Code: "+coursecode.getText())));
            temp.add(new Paragraph(new Phrase("Date: "+datee.getText())));
            temp.add(new Paragraph(new Phrase("Test Timing: "+testtime.getText())));
            temp.add(new Paragraph(new Phrase("Faculty Name: "+faculty.getText())));
            temp.add(new Paragraph(new Phrase("Semester: "+Seme.getText())));
            temp.add(new Paragraph(new Phrase("1. a: Explain Data Warehousing. ")));
            temp.add(new Paragraph(new Phrase(" Marks & CO: "+marks1.getText())));
//            temp.add(new Paragraph(new Phrase("  ")));
//            temp.add(new Paragraph(new Phrase("1. a: Explain Data Warehousing. "+ que1a.getText())));
            temp.add(new Paragraph(new Phrase("1. b: Schemas of Multi-dimensional data models with examples. ")));
//            temp.add(new Paragraph(new Phrase("1. b: Schemas of Multi-dimensional data models with examples. "+ que1b.getText())));
            temp.add(new Paragraph(new Phrase(" Marks & CO: "+marks2.getText())));
//            temp.add(new Paragraph(new Phrase("  ")));
            temp.add(new Paragraph(new Phrase("               OR               ")));
            temp.add(new Paragraph(new Phrase("  ")));
            temp.add(new Paragraph(new Phrase("2. a: Data Warehouse Design Process ")));
//            temp.add(new Paragraph(new Phrase("2. a: Data Warehouse Design Process "+ que2a.getText())));
            temp.add(new Paragraph(new Phrase(" Marks & CO: "+marks3.getText())));
//            temp.add(new Paragraph(new Phrase("  ")));
            temp.add(new Paragraph(new Phrase("2. b: Explain Datamining, applications, challenge ")));
//            temp.add(new Paragraph(new Phrase("2. b: Explain Datamining, applications, challenge "+ que2b.getText())));
            temp.add(new Paragraph(new Phrase(" Marks & CO: "+marks4.getText())));
            temp.add(new Paragraph(new Phrase("  ")));
        }
        return  temp;
    }

}