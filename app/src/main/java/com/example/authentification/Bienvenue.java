package com.example.authentification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Bienvenue extends AppCompatActivity {

    TextView bien;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenue);
        getSupportActionBar().hide();

        bien=(TextView) findViewById(R.id.bienV);
       // btn=(Button) findViewById(R.id.logout);

        Intent intent=getIntent();
        String user=intent.getStringExtra("USER");
        bien.setText("Bienvenue Mr "+user);

     /*   btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Bienvenue.this,MainActivity.class);
                startActivity(intent);
            }
        });

        */

    }
}