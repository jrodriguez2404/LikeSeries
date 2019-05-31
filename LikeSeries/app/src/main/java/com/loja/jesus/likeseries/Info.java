package com.loja.jesus.likeseries;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Info extends AppCompatActivity implements View.OnClickListener {
private TextView github,manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        android.support.v7.widget.Toolbar appToolbar = (Toolbar) findViewById(R.id.appbar);
// Pongo el titulo en la toolbar
        appToolbar.setTitle(getResources().getString(R.string.tituloinfo));
// Asigno la flecha de atras a la toolbar
        appToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
// Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        github=findViewById(R.id.github);
        manual=findViewById(R.id.manualusuario);
        github.setOnClickListener(this);
        manual.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.github:
                Uri uri = Uri.parse("https://github.com/jrodriguez2404");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;

            case R.id.manualusuario:

                break;
        }
    }
}
