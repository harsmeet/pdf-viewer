package com.example.harsmeet.pdfview;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Button buttonOpenPdf, btnUploadPdf;
    TextView tv_pdf;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    public int PICK_PDF_REQUEST = 1;
    Uri filePath;
    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        buttonOpenPdf = findViewById(R.id.button);
        btnUploadPdf = findViewById(R.id.btn_upload_pdf);
        tv_pdf = findViewById(R.id.tv_pdf);


        buttonOpenPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(view.getContext(), MainActivity.class);

                startActivity(i);


            }
        });

        btnUploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                requestStoragePermission();

            }
        });



    }




    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            showFileChooser();

        if (ActivityCompat.shouldShowRequestPermissionRationale(Main2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(Main2Activity.this, "Permission granted. You can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(Main2Activity.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }







    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the ima chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                path = PdfFilePath.getPath(Main2Activity.this, filePath);
            } catch (Exception ignored) {
                path = "";
            }
            tv_pdf.setText(path);
        }
    }







}
