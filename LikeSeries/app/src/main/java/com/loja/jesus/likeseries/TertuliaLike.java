package com.loja.jesus.likeseries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TertuliaLike extends AppCompatActivity {
    private RecyclerView RVT;
    private ArrayList<Tertulia> tertulia_array = new ArrayList<>();
    private FirebaseFirestore db;
    private Context context =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tertulia_like);
        Toolbar appToolbar = (Toolbar) findViewById(R.id.appbar);
// *************************************************
// Pongo el titulo en la toolbar
        appToolbar.setTitle(getResources().getString(R.string.titulotertulia));
// Asigno la flecha de atras a la toolbar
        appToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
// Hago que cuando se pulse la flecha de atras se cierre la actividad
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RVT = findViewById(R.id.RVT);
        cargarRecyclerTertulias();
        agregarTertulias();
    }
    private void cargarRecyclerTertulias()
    {
        LinearLayoutManager llmRVT = new LinearLayoutManager(this);
        RVT.addItemDecoration(new TertuliaLike.SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVT.setHasFixedSize(true);
        RVT.setLayoutManager(llmRVT);
    }
    private void agregarTertulias() {

            db = FirebaseFirestore.getInstance();
            db.collection("tertulia")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                try {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Tertulias tertulias = document.toObject(Tertulias.class);

                                    Tertulia tertulia = new Tertulia(tertulias.getTertulia().get(0).getNombretertulia(),tertulias.getTertulia().get(0).getHorainicio(),tertulias.getTertulia().get(0).getHorafin(),tertulias.getTertulia().get(0).getActivado(),tertulias.getTertulia().get(0).getChattertulia());
                                    tertulia_array.add(tertulia);
                                }
                                    AdaptadorTertulia adaptadorTertulia = new AdaptadorTertulia(context, tertulia_array,"");
                                    RVT.setAdapter(adaptadorTertulia);
                                    RVT.setHasFixedSize(true);
                                    adaptadorTertulia.refrescar();



                                }
                                catch (Exception e)
                                {
                                }
                            } else {

                            }
                        }
                    });

    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        public SpaceItemDecoration(TertuliaLike tertuliaLike, int list_space, boolean b, boolean b1) {
        }
    }
}
