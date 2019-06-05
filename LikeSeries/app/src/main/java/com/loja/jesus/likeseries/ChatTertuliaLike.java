package com.loja.jesus.likeseries;

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
private ArrayList<ChatGeneral>chatgeneral = new ArrayList<>();
private ArrayList<Chat>arraychat = new ArrayList<>();
private ArrayList<Tertulia>arraytertulia = new ArrayList<>();
private Intent intent;
private EditText cajaTexto_chat_tertulia;
private TextView botoncomentar;
private TextView vaciarchat;
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
        vaciarchat=findViewById(R.id.eliminartodo_administrador_tertulia);
        cajaTexto_chat_tertulia=findViewById(R.id.cajaTexto_chat_tertulia);
        botoncomentar=findViewById(R.id.botonmandarComentario_chat_tertulia);
        actualizarRecycler();
        botoncomentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarRecycler();
                actualizarRecyclerenTiempoReal();
            }
        });
        vaciarchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                arraychat.clear();
                DocumentReference docRef = db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ",""));
                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Chat chat = new Chat();
                        Tertulia tertulia = documentSnapshot.toObject(Tertulia.class);
                        arraychat.add(chat);
                        Tertulia tertulia1 = new Tertulia(tertulia.getNombretertulia(),tertulia.getHorainicio(),tertulia.getHorafin(),tertulia.getActivado(),arraychat);
                        db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ","")).set(tertulia1);
                    }
                });
            }
        });




    }
    private void actualizarRecyclerenTiempoReal()
    {
        final DocumentReference doc = db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ",""));
        doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    try {
                        Tertulia tertulia = snapshot.toObject(Tertulia.class);
                        Tertulias tertulias = snapshot.toObject(Tertulias.class);
                        for (int i = 0; i < tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().size(); i++) {

                            ChatGeneral chatGeneratertulia = new ChatGeneral(tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getNombre(), tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getUid(), tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getMensaje(), tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getNombredocumento());
                            chatgeneral.add(chatGeneratertulia);
                        }
                        AdaptadorChat adaptadorChat = new AdaptadorChat(contexto, chatgeneral, "tertulia");
                        RVCT.setAdapter(adaptadorChat);
                        RVCT.setHasFixedSize(true);
                        adaptadorChat.refrescar();
                    }
                    catch (Exception x)
                    {

                    }
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

        DocumentReference docRef = db.collection("tertulia").document(intent.getStringExtra("nombredocumento").replace(" ",""));
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                chatgeneral.clear();
                arraychat.clear();
                Tertulias tertulias = documentSnapshot.toObject(Tertulias.class);
                ChatGeneral chatGeneraltertulia= new ChatGeneral(user.getDisplayName(),user.getUid(),cajaTexto_chat_tertulia.getText().toString(),intent.getStringExtra("nombredocumento").replace(" ",""));
                chatgeneral.add(chatGeneraltertulia);
                try {
                    for (int i = 0; i < tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().size(); i++) {

                        ChatGeneral chatGeneratertulia = new ChatGeneral(tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getNombre(),tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getUid(),tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getMensaje(),tertulias.getTertulia().get(0).getChattertulia().get(0).getChatgeneral().get(i).getNombredocumento());
                        chatgeneral.add(chatGeneratertulia);
                    }
                }
                catch (Exception e)
                {

                }
                AdaptadorChat adaptadorChat = new AdaptadorChat(contexto, chatgeneral,"tertulia");
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
