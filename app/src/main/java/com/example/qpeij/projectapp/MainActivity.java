package com.example.qpeij.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    static final int PICK_FILE_FROM_IMAGE = 1;
    static final int PICK_FILE_FROM_IMAGE2 = 2;
    static final int PICK_FILE_FROM_IMAGE3 = 3;
    static final int PICK_FILE_FROM_IMAGE4 = 4;

    final int btn_img1 = R.id.btn_image1;
    final int btn_img2 = R.id.btn_image2;
    final int btn_img3 = R.id.btn_image3;
    final int btn_img4 = R.id.btn_image4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case btn_img1:
                takeImageFromGallery("1");
                break;
            case btn_img2:
                takeImageFromGallery("2");
                break;
            case btn_img3:
                takeImageFromGallery("3");
                break;
            case btn_img4:
                takeImageFromGallery("4");
                break;
        }
    }

    public void takeImageFromGallery(String str) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");

        switch (str){
            case "1":
                startActivityForResult(
                        Intent.createChooser(intent, "Choose an image")
                        , PICK_FILE_FROM_IMAGE);
                break;
            case "2":
                startActivityForResult(
                        Intent.createChooser(intent, "Choose an image")
                        , PICK_FILE_FROM_IMAGE2);
                break;

            case "3":
                startActivityForResult(
                        Intent.createChooser(intent, "Choose an image")
                        , PICK_FILE_FROM_IMAGE3);
                break;
            case "4":
                startActivityForResult(
                        Intent.createChooser(intent, "Choose an image")
                        , PICK_FILE_FROM_IMAGE4);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICK_FILE_FROM_IMAGE:
                ImageButton imageButton_1 = (ImageButton)findViewById(R.id.btn_image1);
                imageButton_1.setImageURI(data.getData());
                break;
            case PICK_FILE_FROM_IMAGE2:
                ImageButton imageButton_2 = (ImageButton)findViewById(R.id.btn_image2);
                imageButton_2.setImageURI(data.getData());
                break;
            case PICK_FILE_FROM_IMAGE3:
                ImageButton imageButton_3 = (ImageButton)findViewById(R.id.btn_image3);
                imageButton_3.setImageURI(data.getData());
                break;
            case PICK_FILE_FROM_IMAGE4:
                ImageButton imageButton_4 = (ImageButton)findViewById(R.id.btn_image4);
                imageButton_4.setImageURI(data.getData());
                break;
        }
    }
}
