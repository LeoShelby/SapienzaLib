package com.example.sapienzaLib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.example.sapienzaLib.BackendUtilities;
import com.example.sapienzaLib.LoginActivity;
import com.example.sapienzaLib.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.GregorianCalendar;

public class BaseBaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        checkJWT();

    }

    public void checkJWT() {
        if (BackendUtilities.JWT.equals("") || BackendUtilities.EXPIREDATE == null || (new GregorianCalendar().getTime().compareTo(BackendUtilities.EXPIREDATE.getTime()) > 1)) {
            //Perform logout
            //Google sign-in with domain only from sapienza
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.client_id_token))
                    .requestEmail()
                    .setHostedDomain("studenti.uniroma1.it")
                    .build();
            // Build a GoogleSignInClient with the options specified by gso.
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }
}