package com.zu.gtts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class student_list extends AppCompatActivity {

    public static View.OnClickListener myOnClickListener;

    RecyclerView recyclerView;
    ArrayList<Info> infoArrayList;
    private RecyclerView.LayoutManager layoutManager;
    student_adabter adapter ;

    ProgressBar progressBar;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        infoArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("student")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                infoArrayList.add(new Info(document.get("id").toString(),
                                        document.get("email").toString(),
                                        document.get("company").toString(),
                                        document.get("career").toString(),
                                        document.get("major").toString(),
                                        document.get("name").toString(),
                                        document.get("phone").toString()));

                                adapter = new student_adabter(infoArrayList);
                                recyclerView.setAdapter(adapter);
                            }
                            progressBar.setVisibility(View.GONE);



                        }
                    }
                });


    }
}
