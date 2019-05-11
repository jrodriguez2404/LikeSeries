package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class AdaptadorComentarios extends RecyclerView.Adapter<AdaptadorComentarios.ViewHolderComentarios> {
    public class ViewHolderComentarios extends RecyclerView.ViewHolder {
        private TextView nombreComentario,comentario,eliminacion;
        private FirebaseUser user;
        private FirebaseAuth mAuth;
        public ViewHolderComentarios(@NonNull View itemView) {
            super(itemView);
            nombreComentario = itemView.findViewById(R.id.nombre_rv_comentario);
            comentario = itemView.findViewById(R.id.texto_rv_comentario);
            eliminacion = itemView.findViewById(R.id.eliminartucomentario_rv_comentario);
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
        }
    }
    private ArrayList<Comentario> listaComentarios ;
    Context context;
    public AdaptadorComentarios(Context contexto, ArrayList<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
        context = contexto;
    }
    public void add(ArrayList<Comentario> datos)
    {
        listaComentarios.clear();
        listaComentarios.addAll(datos);
    }

    public void refrescar()
    {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorComentarios.ViewHolderComentarios onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_comentarios,viewGroup,false);
        return new ViewHolderComentarios(view);
    }

    /**
     * A perfeccionar
     * @param viewHolderComentarios
     * @param i
     */
    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorComentarios.ViewHolderComentarios viewHolderComentarios, final int i) {
        if(listaComentarios.get(i).getUidusuario().equals(viewHolderComentarios.user.getUid()))
        {
        viewHolderComentarios.eliminacion.setVisibility(View.VISIBLE);
        viewHolderComentarios.eliminacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Elimina el comentario
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.eliminarcomentario);
                builder.setMessage(R.string.seguro);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LLamar a un metodo de firebase para eliminar el comentario del elemento seleccionado

                        dialog.dismiss();
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

        }
        viewHolderComentarios.nombreComentario.setText(listaComentarios.get(i).getNombreusuario());
        viewHolderComentarios.comentario.setText(listaComentarios.get(i).getComentario());
    }


    @Override
    public int getItemCount() {
        return listaComentarios.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
