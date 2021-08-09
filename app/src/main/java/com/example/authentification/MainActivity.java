package com.example.authentification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    EditText email,pass;
    Button btn;
   // Button empreinte;
    ImageButton empreinte;
    TextView inc;

    DataBase db;
    int compteur=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);


        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.pass);

        btn=(Button) findViewById(R.id.connecter);

        empreinte=(ImageButton) findViewById(R.id.print_button);

        inc=(TextView) findViewById(R.id.register);
      //  empreinte=(Button) findViewById(R.id.fingerPrint_btn);

        db=new DataBase(this);
        empreinte();

    btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String champ1 = email.getText().toString();
        String champ2 = pass.getText().toString();

        if (champ1.isEmpty() || champ2.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
        else
            {
            if (compteur == 0)
            {
                finish();
            }
            else
                {
                    if (champ1.equals("Admin") && champ2.equals("Admin"))
                    {
                        Intent intent = new Intent(MainActivity.this, Ajouter_utilisateur.class);
                        intent.putExtra("USER", champ1);
                        startActivity(intent);
                    }
                    else {
                        boolean login = db.auth(champ1, champ2);
                        if (login == true) {
                            Toast.makeText(getApplicationContext(), "Connexion effectuée", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, Bienvenue.class);
                            intent.putExtra("USER", champ1);
                            startActivity(intent);
                        }
                            else
                            {
                            compteur--;
                            Toast.makeText(getApplicationContext(), "Connexion echouée", Toast.LENGTH_SHORT).show();
                              }
                    }
                 }

             }
    }

});






    }

    public void empreinte() {
        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Connexion effectuée", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Ajouter_utilisateur.class);
                intent.putExtra("USER", "Admin");
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        //Biometric dialog

        BiometricPrompt.PromptInfo promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Connecter")
                .setDescription("Utilisez votre empreinte pour connecter")
                .setNegativeButtonText("Anuller")
                .build();


        //Code d'empreinte
        //Vérifier si l'utilisateur peut utiliser l'empreinte ou non

        BiometricManager biometricManager=BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                inc.setText("Vous pouvez utiliser l'empreinte");
                inc.setTextColor(Color.parseColor("#Fafafa"));
                biometricPrompt.authenticate(promptInfo);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                inc.setText("Vous ne disposez pas d'empreinte");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                inc.setText("l'empreinte n'est pas disponible pour le moment ");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                inc.setText("Votre portable ne dispose pas d'une empreinte sauvgardée");
                break;
        }
        empreinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}