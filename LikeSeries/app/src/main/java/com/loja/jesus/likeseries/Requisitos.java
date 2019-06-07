package com.loja.jesus.likeseries;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Requisitos extends AppCompatActivity {
    private TextView manual;
    private Context context= this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisitos);
        android.support.v7.widget.Toolbar appToolbar = (Toolbar) findViewById(R.id.appbar);
// Pongo el titulo en la toolbar
        appToolbar.setTitle(getResources().getString(R.string.titulorequisito));
// Asigno la flecha de atras a la toolbar
        appToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
// Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        manual=findViewById(R.id.manualusuario);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId())
                {
                    case R.id.manualusuario:
                        Intent intent = new Intent(context,FullScreenYoutube.class);
                        intent.putExtra("trailer","6V0pAgIi9gg");
                        startActivity(intent);
                        break;
                }
            }
        });
    }

}
