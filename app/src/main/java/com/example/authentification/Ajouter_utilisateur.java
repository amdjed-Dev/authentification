package com.example.authentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Ajouter_utilisateur extends AppCompatActivity {
    Button btn;
    EditText email,pass,confirm_pass;
    TextView titre;

    DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_utilisateur);
        getSupportActionBar().hide();

        db=new DataBase(this);

        email=(EditText) findViewById(R.id.ajout_email);
        pass=(EditText) findViewById(R.id.ajout_pass);
        confirm_pass=(EditText) findViewById(R.id.confirme_pass);
        titre=(TextView) findViewById(R.id.ajout_text);
        btn=(Button) findViewById(R.id.ajouter);
       // logout=(Button) findViewById(R.id.logout);

        Intent intent=getIntent();
        String user=intent.getStringExtra("USER");
        titre.setText(user);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String champ1 = email.getText().toString();
                String champ2 = pass.getText().toString();
                String champ3=confirm_pass.getText().toString();

                if (champ1.isEmpty() || champ2.isEmpty() || champ3.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   if (champ2.equals(champ3))
                   {
                       boolean verifier = db.verifierEmail(champ1);

                       if (verifier == true) {
                           boolean inserer = db.insert(champ1, champ2);
                           if (inserer == true) {
                               Intent intent = new Intent(Ajouter_utilisateur.this, MainActivity.class);
                               startActivity(intent);

                               Toast.makeText(getBaseContext(), " Utilisateur enregistré", Toast.LENGTH_SHORT).show();
                           }
                       } else {
                           Toast.makeText(getBaseContext(), "Utilisateur existe déja", Toast.LENGTH_SHORT).show();
                       }
                   }
                   else
                    Toast.makeText(getApplicationContext(), "les mots de passes sont différents", Toast.LENGTH_SHORT).show();
            }
            }


        });


    }

}