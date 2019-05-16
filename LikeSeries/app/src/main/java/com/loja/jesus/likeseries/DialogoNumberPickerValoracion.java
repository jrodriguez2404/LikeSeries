package com.loja.jesus.likeseries;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.google.firebase.auth.FirebaseAuth;

public class DialogoNumberPickerValoracion {
    private NumberPicker valoracion;
    private Button aceptarvaloracion;


    public interface finalizarDialog {
        void resultado(int num);
    }

    private Class<ContenedorMultimedia> interfaz;

    public DialogoNumberPickerValoracion(Context contexto, Class<ContenedorMultimedia> actividad, final ImageView imagenvoto) {

        interfaz = actividad;
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.numberpicker_valoracion);


        // NumberPicker's:
        valoracion = dialogo.findViewById(R.id.valoracion);
        valoracion.setMinValue(0);
        valoracion.setMaxValue(10);
        aceptarvaloracion=dialogo.findViewById(R.id.aceptarvotacion);
        aceptarvaloracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("hola , me han elegido como ,"+valoracion.getValue());

                AlertDialog.Builder builder = new AlertDialog.Builder(dialogo.getContext());
                builder.setMessage(R.string.estaseguro);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LLamar a un metodo de firebase para actualizar el voto medio de esta persona

                        imagenvoto.setImageResource(R.drawable.estrella_on);
                        dialogo.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
                AlertDialog dialog = builder.create();
                builder.show();
            }
        });
        dialogo.show();
    }



}


