package com.loja.jesus.likeseries;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class Administrador extends AppCompatActivity {
private LinearLayout insertarpeli,insertarserie,administrarusuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        insertarpeli=findViewById(R.id.insertarpelicula_view);
        insertarserie=findViewById(R.id.insertarserie_view);
        administrarusuario = findViewById(R.id.administrarusuario_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        toolbar.setTitle(R.string.titulo_Administración);
        setSupportActionBar(toolbar);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(R.string.insertar_pelicula_admin));
        tabs.addTab(tabs.newTab().setText(R.string.insertar_serie_admin));
        tabs.addTab(tabs.newTab().setText(R.string.administrarusuarios));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getPosition());
                if(tab.getPosition()==0)
                {
                    insertarpeli.setVisibility(View.VISIBLE);
                    insertarserie.setVisibility(View.GONE);
                    administrarusuario.setVisibility(View.GONE);
                }
                else if(tab.getPosition()==1)
                {
                    insertarpeli.setVisibility(View.GONE);
                    insertarserie.setVisibility(View.VISIBLE);
                    administrarusuario.setVisibility(View.GONE);
                }
                else if(tab.getPosition()==2)
                {
                    insertarpeli.setVisibility(View.GONE);
                    insertarserie.setVisibility(View.GONE);
                    administrarusuario.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
