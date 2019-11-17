package cf.ericrocha.listmoviefirebase.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import cf.ericrocha.listmoviefirebase.R;
import cf.ericrocha.listmoviefirebase.adapter.Adapter;
import cf.ericrocha.listmoviefirebase.model.Movie;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Filmes");
    public Long tam;
    private AlertDialog alert;
    public Integer idnext;




    private List<Movie> listMovies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView_main);

        this.createMoviesFirebase();

        // Read from the databasegg
        myRef.addValueEventListener(new ValueEventListener() {
            //Analisa as alterações
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("Firebase",dataSnapshot.getValue().toString());
                tam = dataSnapshot.getChildrenCount();
                Movie movie;
                listMovies.clear();
                for (int i = 1; i <= tam; i ++) {
                    String indice = Integer.toString(i);
                    if(dataSnapshot.child(indice).exists()){
                        String titlo = dataSnapshot.child(indice).child("titleMovie").getValue().toString();
                        String ano = dataSnapshot.child(indice).child("year").getValue().toString();
                        movie = new Movie(titlo, ano);
                        listMovies.add(movie);
                        idnext = i;
                    }else{
                        tam = tam +1;
                    }

                }

                Adapter adapter = new Adapter( listMovies );

                RecyclerView.LayoutManager layoutManager = new
                        LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);

                recyclerView.setHasFixedSize(true);

                recyclerView.addItemDecoration( new DividerItemDecoration(MainActivity.this,
                        LinearLayout.VERTICAL));
                recyclerView.setAdapter( adapter );



                Log.i("Firebase",dataSnapshot.getValue().toString());
            }
            //Tratar erro
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase", "Failed to read value.", databaseError.toException());

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_add){
            addMovie();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMovie(){


        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Novo filme")
                .setMessage("Adicione um novo filme com titulo e ano:");

        final AlertDialog alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();

        final FrameLayout frameView = new FrameLayout(this);
        builder.setView(frameView);

        final String id = Integer.toString(idnext+1);



        View dialoglayout = inflater.inflate(R.layout.addlayout, frameView);

        final EditText titulo = frameView.findViewById(R.id.ed_titulo);
        final EditText ano = frameView.findViewById(R.id.ed_ano);
        final Button ok = frameView.findViewById(R.id.btn_ok);
        final Button cancel = frameView.findViewById(R.id.btn_cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String tituloFilme = titulo.getText().toString().trim();
                String anoFilme = ano.getText().toString().trim();
                if(!tituloFilme.isEmpty() && !anoFilme.isEmpty()){
                    Movie movie = new Movie(tituloFilme,anoFilme);
                    myRef.child(id).setValue(movie);
                    alert.cancel();
                }else{
                    if(tituloFilme.isEmpty()){
                        titulo.setError("Digite o titulo");
                        titulo.requestFocus();
                    }else{
                        if(anoFilme.isEmpty()){
                            ano.setError("Digite o ano");
                            ano.requestFocus();
                        }
                    }


                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alert.cancel();
            }
        });

        alert = builder.create();
        alert.show();


    }

    public void createMoviesFirebase(){
        myRef.child("1").child("titleMovie").setValue("Spider-Man: Homecoming");
        myRef.child("1").child("year").setValue("2017");

        myRef.child("2").child("titleMovie").setValue("Wonder Woman");
        myRef.child("2").child("year").setValue("2017");

        myRef.child("3").child("titleMovie").setValue("Justice League");
        myRef.child("3").child("year").setValue("2017");

        myRef.child("4").child("titleMovie").setValue("Captain America: Civil War");
        myRef.child("4").child("year").setValue("2016");

        myRef.child("5").child("titleMovie").setValue("It");
        myRef.child("5").child("year").setValue("2017");

        myRef.child("6").child("titleMovie").setValue("Woody Woodpecker");
        myRef.child("6").child("year").setValue("2017");

        myRef.child("7").child("titleMovie").setValue("The Mummy");
        myRef.child("7").child("year").setValue("2017");

        myRef.child("8").child("titleMovie").setValue("Beauty and the Beast");
        myRef.child("8").child("year").setValue("2017");

        myRef.child("9").child("titleMovie").setValue("Despicable Me 3");
        myRef.child("9").child("year").setValue("2017");

        myRef.child("10").child("titleMovie").setValue("Cars 3");
        myRef.child("10").child("year").setValue("2017");
    }


}
