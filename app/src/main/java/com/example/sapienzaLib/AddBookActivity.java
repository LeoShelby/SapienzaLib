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

    //String APP_KEY = "ASlc/xM3DhXYEzgsggQvaCUMxci2CkamC1pMHMlhhPIjYd7wRx0Km9dz1s9yD8RNtnTAuew6rVMye9oiLzOzXnImgiQbfMxNRyRut5dlFtK4ZJPvKkQyuGJRHXWlXqUbyg+K85AHsN7jJzhfetJFguTVwrZQaX75lPG0SEb8TwIuawEdXkZJxlcLJdK7ksqkeGjRVqF0xsEpx0xMCBohcG8XJgCCkSAvRuAhMwf2dazetacc/bzevexRuOHqinEF9ORIV8UFGZpPPqOfMKAYXfbrithZdVzuH9pv6+aIeGi7htAYTbHWYh7YeiZ9Xmgyyu4JofahR8KMPQd8Anw4SpksMkWx0yOI38Bei1TJ5m8O51r0vQUOj6kIByk0dWxTvAt7GZrNZcEPhZDLjlQDBOL+k3DwWoAeObHtyByjiQaoXM2qws4tce5GYaxvXZWyl4DsYgtcLdNbQM6+OxmY7Tfhh+ss9Qq8gZAvKq1hoU7IICF+WELz8SgCiRr/Nslh91ecyeB+SGygNgPQo84X6tzxx5Xt7OidTTkl/oWpJ+KHHmedjugKyjfjyJh4toXQ1IlvQXfL+jLmekfnnavLx/iqcw8tjA040HYHhtZcIzaFXO1wAf6WTA/L3PaVDY7D3jmHLIlrggAUselddX6CAQalItiwAFmhwdRaMsCe1fyQujU8FhysbvK935UUZVm+vqMlgF0/bxy1euSWRwH36LoG74yw68P6xUka/x3GUeyctrRKk5xlxFVCKDFH81jIII8ED9dtLev6Gro0UTKJKrNJHN4A4Yf/GXgSsFxgQlCPqxYU3tyDsIE=";
    //String APP_KEY = "AWBsgABhHaxPG18CMjDPp6YARpP2P9ZpF3oj+VB5cvr6XpNlcEzy5NxZID9QBInI7UHvhnkTCJskcNFWPGhEZcFpQHgmZfGIqg4ldXBRldRWWlxpAUiY0P59a6wVbT+ze108gOdNVm+YYSUfpm9PpIdH84+PWvqlVFecjGQxVztDA3AomXbKT7B7Uuutef+JdEKDyj5RMVXWUhcpJGDokTRfSQpKBE4i43RUYVV1Z+tIZHR06EkMiH1cjWi3KOLa4269mN1D2JLqLSYETE9+6yh2DykoSmfsHl1RPMRAM8L0eu45AVmMGip1dEf/Rx9zTWJ/ISRpppf2dPrXcSXsy0phfLxaXoGx8lGarKhlT/llXWWqKkdEV8FW4HRGXQ1+xnrsbI9u82pnTco0JENQlId3UAt+WheCNn9g5eFssrCcSblVo13ZEAluzT4ZQnR8WknMZfFoKiUVL5ybn2uJBPBT3Ef/UcK4E0aHlodTrVUxX4zdgWzlBsJ4wv6Pc4r9jCg8DXw6PMWiAz5YlvW700ye8nnbQUTwkB2O7iadsAnaOsffO2h3CzByTQnNJzlh1ttjpwDeuZ30tVHF0HLn25awmgS5AcVIaYS8ZIsLOMwX/nNMMI+813HVJrM7tEvBFY0tHU6/X/C/VkKawCcQD2r6/qGiosCQhPtA4hZx0iqAdsqso24ezAPop6iy7Gg1ZXXOfl91EjF1pxExyPmTlcOBbDxMluD6knWf7Wakrfqm0DpDOxSNUm7SShI/iZ2Tl7Uz2R0V9/j+Wu1fnROpZqN7zEn/iketSJ6eIjuBcNd9UCSfxdgAvdVhZaNUsVUfv+tqARLvYIc9VhWUdAthFNpTOkIM9H4XnoqmRxB1NvFAtO8r3Ziv7GLW49dGQRb2MhbDQduEWTCUBl56O4Npe+iMrZU6M6EiC5cCjU1Jk2ioWkEXwUJnZbYBaXL7DdJl8xFAVKvLIPHsm3oizB8C74b9XM3ULNNxJELhSFjqUzSqBeQwyzM1tcFqvGdcj4AtXyBE9N03ezjmUMn5jX9dPu/jB8ut/BAUNsESMtzK0DPp1q8jYM2AjVcqhSDCeywNTWOv1KCo7v1OD5IQi0j44ENiV63YwluSeoXrQqc10DI6beq1emG6FtKkZoj0agCHzZ/ar/0j5zOj0pIj7r9ZaTN4V2mVq6vKDnDYtlePyXLwOsa26zJcm92H";
    String APP_KEY = "Ae88T1lhRPW5Lvn7V0EKWzVFZTuJBRkzNEJQmElqe1t0V83AFWk3JpsM9z2+NCzaKH4xIU4oHJAVWYeI+w8pytF5Rx03RDASWSbQt5pP++VpZVJFplXucZl0Ri/KZ/qrOxsrfzEvoa7tfpNZhH+QeuquNzCIYQowKFjpxJ0sVXNTzS+vCs4dLk6i8hrAoxxpZovtVDIp3NHvZcNdirpyPadgRZGz77o/8CTk0w3/p92jkZBetPl83Z1smNO6hmeVkd+0PZKwoFAG+RLEziE1St6chdz1htc1qW41l5KtBAb3+DQWvBHXTtrrLEIwNMq2ybqmMlDl25U42jzhDBk1+C58drr8DC37iHmJVyOSwxXGD54+5Z2T5i0tmbX/q94ch62u+bQ46ZwNmDb5vH5PKHoTZE0eTxap+SBfgmg1IJpDeHXtLDk7AsuHbyQK4SvA7S2qnQAlHsdchPwNR1CZ5eaWqgvym/jxiMUduQjRUNANKHp5xIl9cKT/IQoyZVEyQ/rG6zL2NUpfqbzAigZCmfaquGdxYbSEGJTe/LZeQaDpVUMiE3k8qnR9X8TocH1jhu6fjg7YqKVlZlsLJP1PD5hCuzMHsJNjdxJKEiH/gAA7eIt8UVXPtAju1OjEkycNIIrxGDlONA52bcGJWTJzE1qN16xdHtGI/1a39BziS7Q2nzGbHqD3X1jsrvyBMWdxecYVwTwwXHbPxKi1X8UPKl+/Cq5h9Jo5DRvpIvhpCvy7PUMFWMqdv4isR650CKTqvd4qc0+ZCSpswPIxtfvjFDgf69zH9ie1Yd8BJE/qNRrXlvPlSnwgCrg=";
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
        Snackbar.make(layout, "Booked added", Snackbar.LENGTH_INDEFINITE)
                .setAction("Go back to the homepage", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).show();


    }
}
