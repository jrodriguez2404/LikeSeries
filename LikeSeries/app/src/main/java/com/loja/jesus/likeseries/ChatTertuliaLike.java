package com.loja.jesus.likeseries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatTertuliaLike extends AppCompatActivity {
private RecyclerView RVCT;
private FirebaseAuth mAuth;
private FirebaseUser user;
private FirebaseFirestore db;
private Context contexto = this;
private ArrayList<ChatGeneral>arraychat = new ArrayList<>();
private ArrayList<Chat>array=new ArrayList<>();
private ArrayList<Tertulia>arrayTertulia = new ArrayList<>();
private Intent intent;
private EditText cajaTexto_chat_tertulia;
private TextView botoncomentar;
private TextView vaciarchat,cerrarchattertulia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_tertulia_like);
        intent = getIntent();

        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RVCT = findViewById(R.id.RVCT);
        vaciarchat = findViewById(R.id.eliminartodo_administrador_tertulia);
        cajaTexto_chat_tertulia = findViewById(R.id.cajaTexto_chat_tertulia);
        botoncomentar = findViewById(R.id.botonmandarComentario_chat_tertulia);
        cerrarchattertulia=findViewById(R.id.cerrar_chat_tertulia);
        cerrarchattertulia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cajaTexto_chat_tertulia.setText("");
                finish();
            }
        });
        actualizarRecyclerenTiempoReal();
        actualizarRecycler();

        botoncomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarRecycler();

            }
        });
        mAuth=FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("usuarios").document(user.getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               Usuario user = documentSnapshot.toObject(Usuario.class);
                if(user.getAdministrador()!=0) {
                    vaciarchat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final FirebaseFirestore db;
                            db = FirebaseFirestore.getInstance();
                            array.clear();
                            arrayTertulia.clear();
                            DocumentReference docRef = db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ", ""));
                            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Tertulias tertulias = documentSnapshot.toObject(Tertulias.class);
                                    Tertulia tertulia1 = new Tertulia(tertulias.getTertulia().get(0).getNombretertulia(), tertulias.getTertulia().get(0).getHorainicio(), tertulias.getTertulia().get(0).getHorafin(), tertulias.getTertulia().get(0).getActivado(), array);
                                    arrayTertulia.add(tertulia1);
                                    Tertulias tertulias1 = new Tertulias(arrayTertulia);
                                    db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ", "")).set(tertulias1);
                                }
                            });
                        }
                    });
                }
                else
                {
                   vaciarchat.setVisibility(View.GONE);
                }
            }
        });

    }
    private void actualizarRecyclerenTiempoReal()
    {
        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ",""));
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Tertulias tertulias = snapshot.toObject(Tertulias.class);
                    arraychat.clear();
                    try {
                        for (int i = 0; i < tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().size(); i++) {
                            arraychat.add(tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i));
                        }
                    }
                    catch (Exception u)
                    {

                    }
                    AdaptadorChat adaptadorChat = new AdaptadorChat(contexto, arraychat,"tertulia");
                    RVCT.setAdapter(adaptadorChat);
                    RVCT.setHasFixedSize(true);
                    adaptadorChat.refrescar();
                } else {
                }
            }
        });
    }
    private void actualizarRecycler()
    {
        LinearLayoutManager llmRVCT = new LinearLayoutManager(this);
        RVCT.addItemDecoration(new ChatTertuliaLike.SpaceItemDecoration(this, R.dimen.list_space, true, true));
        RVCT.setHasFixedSize(true);
        RVCT.setLayoutManager(llmRVCT);





    }
    private void cargarRecycler()
    {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ",""));
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                arraychat.clear();
                arrayTertulia.clear();
                array.clear();
                Tertulias tertulias = documentSnapshot.toObject(Tertulias.class);
                if(!cajaTexto_chat_tertulia.getText().toString().equals("")) {
                    ChatGeneral chatGeneral = new ChatGeneral(user.getDisplayName(), user.getUid(), cajaTexto_chat_tertulia.getText().toString(), intent.getStringExtra("nombredocumento").replace(" ",""));
                    arraychat.add(chatGeneral);
                }
                try {

                    for (int i = 0; i < tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().size(); i++) {

                        if(!tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getMensaje().equals(cajaTexto_chat_tertulia.getText().toString())) {
                            arraychat.add(tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i));
                        }
                        else if(tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getMensaje().equals(cajaTexto_chat_tertulia.getText().toString()))
                        {


                        }



                    }
                }
                catch (Exception e)
                {

                }
                    array.clear();
                    Chat chat = new Chat(arraychat);
                    array.add(chat);
                    Tertulia tertulia1 = new Tertulia(tertulias.getTertulia().get(0).getNombretertulia(),tertulias.getTertulia().get(0).getHorainicio(),tertulias.getTertulia().get(0).getHorafin(),tertulias.getTertulia().get(0).getActivado(),array);
                    arrayTertulia.add(tertulia1);
                    tertulias = new Tertulias(arrayTertulia);
                    db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ","")).set(tertulias);
                    AdaptadorChat adaptadorChat = new AdaptadorChat(contexto, arraychat,"tertulia");
                    RVCT.setAdapter(adaptadorChat);
                    RVCT.setHasFixedSize(true);
                    adaptadorChat.refrescar();







            }
        });

    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        public SpaceItemDecoration(ChatTertuliaLike chatTertuliaLike, int list_space, boolean b, boolean b1) {
        }
    }
}

