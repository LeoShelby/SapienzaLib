package com.example.sapienzaLib;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.scandit.barcodepicker.BarcodePicker;
import com.scandit.barcodepicker.OnScanListener;
import com.scandit.barcodepicker.ScanSession;
import com.scandit.barcodepicker.ScanSettings;
import com.scandit.barcodepicker.ScanditLicense;
import com.scandit.recognition.Barcode;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddBookActivity extends BaseBaseActivity implements OnScanListener {

    String APP_KEY = "ASlc/xM3DhXYEzgsggQvaCUMxci2CkamC1pMHMlhhPIjYd7wRx0Km9dz1s9yD8RNtnTAuew6rVMye9oiLzOzXnImgiQbfMxNRyRut5dlFtK4ZJPvKkQyuGJRHXWlXqUbyg+K85AHsN7jJzhfetJFguTVwrZQaX75lPG0SEb8TwIuawEdXkZJxlcLJdK7ksqkeGjRVqF0xsEpx0xMCBohcG8XJgCCkSAvRuAhMwf2dazetacc/bzevexRuOHqinEF9ORIV8UFGZpPPqOfMKAYXfbrithZdVzuH9pv6+aIeGi7htAYTbHWYh7YeiZ9Xmgyyu4JofahR8KMPQd8Anw4SpksMkWx0yOI38Bei1TJ5m8O51r0vQUOj6kIByk0dWxTvAt7GZrNZcEPhZDLjlQDBOL+k3DwWoAeObHtyByjiQaoXM2qws4tce5GYaxvXZWyl4DsYgtcLdNbQM6+OxmY7Tfhh+ss9Qq8gZAvKq1hoU7IICF+WELz8SgCiRr/Nslh91ecyeB+SGygNgPQo84X6tzxx5Xt7OidTTkl/oWpJ+KHHmedjugKyjfjyJh4toXQ1IlvQXfL+jLmekfnnavLx/iqcw8tjA040HYHhtZcIzaFXO1wAf6WTA/L3PaVDY7D3jmHLIlrggAUselddX6CAQalItiwAFmhwdRaMsCe1fyQujU8FhysbvK935UUZVm+vqMlgF0/bxy1euSWRwH36LoG74yw68P6xUka/x3GUeyctrRKk5xlxFVCKDFH81jIII8ED9dtLev6Gro0UTKJKrNJHN4A4Yf/GXgSsFxgQlCPqxYU3tyDsIE=";
    private BarcodePicker picker;
    private FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ScanditLicense.setAppKey(APP_KEY);
        ScanSettings settings = ScanSettings.create();
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_EAN13, true);
        settings.setSymbologyEnabled(Barcode.SYMBOLOGY_UPCA, true);
// Instantiate the barcode picker by using the settings defined above.
        picker = new BarcodePicker(this, settings);
// Set the on scan listener to receive barcode scan events.
        picker.setOnScanListener(this);

        CircleImageView cim = findViewById(R.id.add_image);
        cim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout = (FrameLayout) findViewById(R.id.add_frame);
                layout.removeAllViewsInLayout();
                layout.addView(picker);
            }
        });

        Button mButton = findViewById(R.id.button2);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), SearchBookActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        picker.startScanning();
        super.onResume();
    }
    @Override
    protected void onPause() {
        picker.stopScanning();
        super.onPause();
    }


    @Override
    public void didScan(ScanSession scanSession) {
        List<Barcode> bc = scanSession.getNewlyRecognizedCodes();
        try {
            BackendUtilities.postBookByISBN(bc.get(0).getData());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scanSession.stopScanning();
        Snackbar.make(layout, "Hai aggiunto stocazzo de libro", Snackbar.LENGTH_INDEFINITE)
                .setAction("Click to close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();


    }
}
