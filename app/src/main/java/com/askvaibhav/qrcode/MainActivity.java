package com.askvaibhav.qrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.ClipData;
import android.content.ClipboardManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;


import android.content.DialogInterface;

import android.graphics.Bitmap;

import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Switch;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.Intents.Scan;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private EditText mytext;
    private static final int SELECT_PHOTO = 100;
    //for easy manipulation of the result
    public String barcode;
    private ImageButton threedark;
    private Button scan;
    private Button generate;

    private ImageView qrgenerated;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        // final Switch myButton=(Switch) SettingsActivity.myActivity.findViewById(R.id.switch2);

        threedark= findViewById(R.id.imageButton3);
        generate = findViewById(R.id.Generate);
        scan = findViewById(R.id.scan);

        mytext = findViewById(R.id.mytext);
        qrgenerated = findViewById(R.id.qrgenerated);

        if (SettingsActivity.flag==1) {


            threedark.setImageResource(R.mipmap.threedotdark);

        }

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mytext.getText().toString();
                if (text.isEmpty()) {
                    AlertDialog.Builder builder
                            = new AlertDialog
                            .Builder(MainActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage("Enter URL or something");

                    // Set Alert Title
                    builder.setTitle("INSTRUCTIONS");

                    // Set Cancelable false
                    // for when the user clicks on the outside
                    // the Dialog Box then it will remain show
                    builder.setCancelable(false);

                    // Set the positive button with yes name
                    // OnClickListener method is use of
                    // DialogInterface interface.

                    builder
                            .setPositiveButton(
                                    "OK",
                                    new DialogInterface
                                            .OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            // When the user click yes button
                                            // then app will close
                                            dialog.cancel();

                                        }
                                    });


                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();

                    // Show the Alert Dialog box
                    alertDialog.show();

                }
                if (text != null && !text.isEmpty()) {

                    AlertDialog.Builder builder
                            = new AlertDialog
                            .Builder(MainActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage("Select Format");

                    // Set Alert Title
                    builder.setTitle("INSTRUCTIONS");


                    // Set Cancelable false
                    // for when the user clicks on the outside
                    // the Dialog Box then it will remain show
                    builder.setCancelable(false);


                    // Set the positive button with yes name
                    // OnClickListener method is use of
                    // DialogInterface interface.

                    builder
                            .setPositiveButton(
                                    "QR Code",
                                    new DialogInterface
                                            .OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            // When the user click yes button
                                            // then app will close

                                            String text = mytext.getText().toString();
                                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                            BitMatrix bitMatrix = null;
                                            try {
                                                bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                                            } catch (WriterException e) {
                                                e.printStackTrace();
                                            }
                                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                            qrgenerated.setImageBitmap(bitmap);
                                            dialog.cancel();

                                        }
                                    })

                            .setNegativeButton(
                                    "Barcode",
                                    new DialogInterface
                                            .OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            // When the user click yes button
                                            // then app will close
                                            String text = mytext.getText().toString();
                                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                            BitMatrix bitMatrix = null;
                                            try {
                                                bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_128, 500, 500);
                                            } catch (WriterException e) {
                                                e.printStackTrace();
                                            }
                                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                            qrgenerated.setImageBitmap(bitmap);
                                            dialog.cancel();

                                        }
                                    });/*
                            .setPositiveButton(
                                    "Code 39",
                                    new DialogInterface
                                            .OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            // When the user click yes button
                                            // then app will close
                                            type=1;
                                            dialog.cancel();

                                        }
                                    })
                            .setPositiveButton(
                                    "UPC A",
                                    new DialogInterface
                                            .OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            // When the user click yes button
                                            // then app will close
                                            type=6;
                                            dialog.cancel();

                                        }
                                    });
                            .setPositiveButton(
                                    "Code 128",
                                    new DialogInterface
                                            .OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            // When the user click yes button
                                            // then app will close
                                            type=5;
                                            dialog.cancel();

                                        }
                                    })
                            .setPositiveButton(
                                    "Data Matrix",
                                    new DialogInterface
                                            .OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                            // When the user click yes button
                                            // then app will close
                                            type=4;
                                            dialog.cancel();

                                        }
                                    });*/


                    // Create the Alert dialogAlertDialog alertDialog = builder.create();

                    // Show the Alert Dialog box
                    // alertDialog.show();
                        /*

                        if (type==2){
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrgenerated.setImageBitmap(bitmap);
                        }
                        if (type==3){
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_128, 500, 500);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrgenerated.setImageBitmap(bitmap);
                        }
                        if (type==7){
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.PDF_417, 500, 500);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrgenerated.setImageBitmap(bitmap);
                        }

                        if (type==1){
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_39, 500, 500);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrgenerated.setImageBitmap(bitmap);
                        }
                        if (type==5){
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_128, 500, 500);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrgenerated.setImageBitmap(bitmap);
                        }
                        if (type==6){
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.UPC_A, 500, 500);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrgenerated.setImageBitmap(bitmap);
                        }
                        if (type==7){
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.UPC_E, 500, 500);
                            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                            qrgenerated.setImageBitmap(bitmap);
                        }*/
                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();

                    // Show the Alert Dialog box
                    alertDialog.show();


                }
            }
        });

        //set a new custom listener
        //scan.setOnClickListener(this);
        //launch gallery via intent
       /* galleryy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPic = new Intent(Intent.ACTION_PICK);
                photoPic.setType("image/*");
                startActivityForResult(photoPic, SELECT_PHOTO);

            }
        });*/
       /* Intent photoPic = new Intent(Intent.ACTION_PICK);
        photoPic.setType("image/*");
        startActivityForResult(photoPic, SELECT_PHOTO);*/

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setCameraId(0);
                intentIntegrator.setPrompt("Use Volume up/down keys for torch on/off");
                intentIntegrator.setOrientationLocked(false);
                if (SettingsActivity.love == 1) {
                    intentIntegrator.setBeepEnabled(true);
                } else if (SettingsActivity.love == 0) {
                    intentIntegrator.setBeepEnabled(false);

                }

                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!= null && result.getContents()!= null){
            new AlertDialog.Builder(MainActivity.this).setTitle("Scanned Result").setMessage(result.getContents()).setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData data = ClipData.newPlainText("result", result.getContents());
                    manager.setPrimaryClip(data);

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

        //do necessary coding for each ID
/*
        public void onClick (View v){
            switch (v.getId()) {
                case R.id.scan:
                    //launch gallery via intent
                    Intent photoPic = new Intent(Intent.ACTION_PICK);
                    photoPic.setType("image/*");
                    startActivityForResult(photoPic, SELECT_PHOTO);
                    break;
            }
        }

        //call the onactivity result method


        @Override
        protected void onActivityResult (int requestCode, int resultCode, Intent
        imageReturnedIntent){
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
            switch (requestCode) {
                case SELECT_PHOTO:
                    if (resultCode == RESULT_OK) {
//doing some uri parsing
                        Uri selectedImage = imageReturnedIntent.getData();
                        InputStream imageStream = null;
                        try {
                            //getting the image
                            imageStream = getContentResolver().openInputStream(selectedImage);
                        } catch (FileNotFoundException e) {
                            Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        //decoding bitmap
                        Bitmap bMap = BitmapFactory.decodeStream(imageStream);
                        // To display selected image in image view
                        int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
                        // copy pixel data from the Bitmap into the 'intArray' array
                        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(),
                                bMap.getHeight());

                        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(),
                                bMap.getHeight(), intArray);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                        Reader reader = new MultiFormatReader();// use this otherwise
                        // ChecksumException
                        try {
                            Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
                            decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                            decodeHints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

                            Result result = reader.decode(bitmap, decodeHints);
                            //*I have created a global string variable by the name of barcode to easily manipulate data across the application
                            barcode = result.getText().toString();

                            //do something with the results for demo i created a popup dialog
                            if (barcode != null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("Scan Result");
                                builder.setIcon(R.mipmap.logoforapp);
                                builder.setMessage("" + barcode);
                                AlertDialog alert1 = builder.create();
                                alert1.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(i);
                                    }
                                });

                                alert1.setCanceledOnTouchOutside(false);

                                alert1.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("Scan Result");
                                builder.setIcon(R.mipmap.logoforapp);
                                builder.setMessage("Nothing found try a different image or try again");
                                AlertDialog alert1 = builder.create();
                                alert1.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(i);
                                    }
                                });

                                alert1.setCanceledOnTouchOutside(false);

                                alert1.show();

                            }
                            //the end of do something with the button statement.

                        } catch (NotFoundException e) {
                            Toast.makeText(getApplicationContext(), "Nothing Found", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (ChecksumException e) {
                            Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (FormatException e) {
                            Toast.makeText(getApplicationContext(), "Wrong Barcode/QR format", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
            }

*/
/*

     scan.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                     IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                                     intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                                     intentIntegrator.setCameraId(0);
                                     intentIntegrator.setPrompt("Use Volume up/down keys for torch on/off");
                                     intentIntegrator.setOrientationLocked(false);
                                     if (SettingsActivity.love == 1) {
                                         intentIntegrator.setBeepEnabled(true);
                                     } else if (SettingsActivity.love == 0) {
                                         intentIntegrator.setBeepEnabled(false);

                                     }

                                     intentIntegrator.setBarcodeImageEnabled(true);
                                     intentIntegrator.initiateScan();
                                 }
                             });*/








            // }


            //);










    public void showpopup1(View view){
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.scanmenu, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
               /* Toast.makeText(MainActivity.this,
                        "Clicked popup menu item " + item.getTitle(),
                        Toast.LENGTH_SHORT).show();
                return true;*/
                if (item.getItemId() == R.id.search) {

                    Intent intent = new Intent(MainActivity.this,scanviagallery.class);
                    startActivity(intent);

                }
                if (item.getItemId() == R.id.add) {

                    Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
                    startActivity(intent);

                }





                return true;

            }
        });


        popup.show();

    }



/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!= null && result.getContents()!= null){
            new AlertDialog.Builder(MainActivity.this).setTitle("Scanned Result").setMessage(result.getContents()).setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData data = ClipData.newPlainText("result", result.getContents());
                    manager.setPrimaryClip(data);

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

*/
    @Override
    public void onBackPressed()
    {

        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Do you want to exit ?");

        // Set Alert Title
        builder.setTitle("Alert !");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                finish();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionmenu,menu);















        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.share) {

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            String aEmailList[] = {"askvaibhavsharma@gmail.com"};
            emailIntent.setType("plain/text");


            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
            startActivity(Intent.createChooser(emailIntent, "Share with"));
        }

        if (item.getItemId() == R.id.settings) {

            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
}







