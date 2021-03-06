package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorSeries extends RecyclerView.Adapter<AdaptadorSeries.ViewHolderSeries> {


    public class ViewHolderSeries extends RecyclerView.ViewHolder {
        private TextView titulo,numvotosmas,numvotosmenos,notamedia,votomas,votomenos;
        private ImageView imagen;
        private LinearLayout seleccion,recyclerviewvacio;

        public ViewHolderSeries(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            numvotosmas = itemView.findViewById(R.id.numvotosmas);
            numvotosmenos = itemView.findViewById(R.id.numvotosmenos);
            imagen = itemView.findViewById(R.id.imagen);
            seleccion = itemView.findViewById(R.id.seleccion);
            notamedia = itemView.findViewById(R.id.notamedia_recycler);
            votomas = itemView.findViewById(R.id.imagenmas);
            votomenos = itemView.findViewById(R.id.imagenmenos);
            recyclerviewvacio = itemView.findViewById(R.id.recyclerviewvacio);
        }
    }
    private ArrayList<Serie> listaSeries =new ArrayList<>();
    private String vacio;
    Context context;
    public AdaptadorSeries(Context contexto, ArrayList<Serie> listaSeries, String vacio) {
        this.listaSeries.clear();

        context = contexto;
        this.vacio=vacio;
if(vacio.equals("vacio"))
{
    Serie serie = new Serie(null,null,null,null,null,null,null,null,null,null,null,0,0,0,0,0,null,null,null,null);
    this.listaSeries.add(serie);
}
else
{
    this.listaSeries = listaSeries;
}
    }
    public void add(ArrayList<Serie> datos)
    {
        listaSeries.clear();
        listaSeries.addAll(datos);
    }

    public void refrescar()
    {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdaptadorSeries.ViewHolderSeries onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview, viewGroup, false);

        return new ViewHolderSeries(view);
    }

    /**
     * A perfeccionar
     * @param ViewHolderSeries
     * @param i
     */
    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull final AdaptadorSeries.ViewHolderSeries ViewHolderSeries, final int i) {
        if(!vacio.equals("vacio")) {
            ViewHolderSeries.recyclerviewvacio.setVisibility(View.GONE);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            ArrayList<String> arrayusuarios = new ArrayList<>(), arraynombres = new ArrayList<>(), arraycomentarios = new ArrayList<>(), arrayvotantes = new ArrayList<>(), arrayreglas = new ArrayList<>();
//Este bucle recorre todos los usuarios que han votado alguna vez en la app ,despues con el usuario actual comprueba si existen en el mapa , y recoge los parametros
try {

    for (int j = 0; j < listaSeries.get(i).getVotosusuarios().size(); j++) {
        arrayvotantes.add(listaSeries.get(i).getVotosusuarios().get(j).getUsuariovoto());
        arrayreglas.add(listaSeries.get(i).getVotosusuarios().get(j).getReglas());
    }
    //Este bucle recoge todos los comentarios de la app , aun tenemos que hacer que el boton eliminar se muestre para los comentarios del usuario actual
    for (int j = 0; j < listaSeries.get(i).getComentarios().size(); j++) {
        arrayusuarios.add(listaSeries.get(i).getComentarios().get(j).getUsuario());
        arraynombres.add(listaSeries.get(i).getComentarios().get(j).getNombre());
        arraycomentarios.add(listaSeries.get(i).getComentarios().get(j).getComentario());


    }
}catch (Exception e)
{

}
            DecimalFormat formato = new DecimalFormat("#.#");
            Resources res = ViewHolderSeries.itemView.getContext().getResources();
            final Intent intent = new Intent(context, ContenedorMultimedia.class);
            intent.putExtra("ID", listaSeries.get(i).getID_Serie());
            intent.putExtra("titulo", listaSeries.get(i).getTitulo_Serie());
            intent.putExtra("descripcion", listaSeries.get(i).getDescripcion_Serie());
            intent.putExtra("genero", listaSeries.get(i).getGenero_Serie());
            intent.putExtra("votosmas", listaSeries.get(i).getVotosPositivos_Serie());
            intent.putExtra("votosmenos", listaSeries.get(i).getVotosNegativos_Serie());
            intent.putExtra("votantes", listaSeries.get(i).getVotantes_Serie());
            intent.putExtra("productora", listaSeries.get(i).getProductora_Serie());
            intent.putExtra("nmedia", formato.format(listaSeries.get(i).getNotamedia_Serie()));
            intent.putExtra("arrayusuarios", arrayusuarios);
            intent.putExtra("arraynombres", arraynombres);
            intent.putExtra("arraycomentarios", arraycomentarios);
            intent.putExtra("arrayvotantes", arrayvotantes);
            intent.putExtra("arrayreglas", arrayreglas);
            intent.putExtra("pelioserie", "series");
            intent.putExtra("urlimagen", listaSeries.get(i).getImagen_Serie());
            intent.putExtra("director", listaSeries.get(i).getDirector_Serie());
            intent.putExtra("fechaestreno", listaSeries.get(i).getPrimeraEmision_Serie());
            intent.putExtra("trailer", listaSeries.get(i).getTrailer_Serie());
            intent.putExtra("duracion", listaSeries.get(i).getDuración_Serie());
            if(!listaSeries.get(i).getGenero_Serie().get(3).equals("Anime")) {
                intent.putExtra("actores", listaSeries.get(i).getActores_series());
                intent.putExtra("actoresno","si");
            }
            else
            {
                intent.putExtra("actoresno","no");
            }
            FirebaseStorage storage = FirebaseStorage.getInstance();
            try {
                StorageReference gsReference = storage.getReferenceFromUrl(listaSeries.get(i).getImagen_Serie() + "");
                final long ONE_MEGABYTE = 1024 * 1024;
                gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        ViewHolderSeries.imagen.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
            }catch (Exception a)
            {

            }
            ViewHolderSeries.titulo.setText(listaSeries.get(i).getTitulo_Serie());
            ViewHolderSeries.numvotosmas.setText(res.getString(R.string.votomas, listaSeries.get(i).getVotosPositivos_Serie()));
            ViewHolderSeries.votomas.setBackgroundResource(R.drawable.like);
            ViewHolderSeries.numvotosmenos.setText(res.getString(R.string.votomenos, listaSeries.get(i).getVotosNegativos_Serie()));
            ViewHolderSeries.votomenos.setBackgroundResource(R.drawable.nolike);
            ViewHolderSeries.notamedia.setText(res.getString(R.string.notamedia, listaSeries.get(i).getNotamedia_Serie()));
            ViewHolderSeries.seleccion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(intent);
                    try {
                        new Like().cerrarActividad();
                    } catch (LikeSeriesExceptionClass likeSeriesExceptionClass) {
                        Toast.makeText(context.getApplicationContext(),
                                "Error inesperado , disculpe las molestias", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else
        {

            ViewHolderSeries.recyclerviewvacio.setVisibility(View.VISIBLE);
            ViewHolderSeries.seleccion.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return listaSeries.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public void addItem(Serie serie,int index)
    {
        listaSeries.add(serie);
        notifyItemInserted(index);
    }

}
