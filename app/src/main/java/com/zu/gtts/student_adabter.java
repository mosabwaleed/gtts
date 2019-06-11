package com.zu.gtts;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class student_adabter extends RecyclerView.Adapter<student_adabter.MyViewHolder> {
    ArrayList<Info> dataset;
    Context context;

    public student_adabter(ArrayList<Info> dataset) {
        this.dataset = dataset;
    }


    public void removeall(){
        this.dataset.clear();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card,parent,false);
        view.setOnClickListener(student_list.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        context = parent.getContext();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        CardView cardView = myViewHolder.cardView;
        TextView name = myViewHolder.name;
        TextView id = myViewHolder.id;

        name.setText(dataset.get(i).getName());
        id.setText(dataset.get(i).getId());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                Intent intent = new Intent(context,student.class);
                intent.putExtra("name",dataset.get(i).getName());
                intent.putExtra("id",dataset.get(i).getId());
                intent.putExtra("career",dataset.get(i).getCareer());
                intent.putExtra("major",dataset.get(i).getMajor());
                intent.putExtra("phone",dataset.get(i).getPhone());
                intent.putExtra("company",dataset.get(i).getCompany());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView name,id;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.studentcard);
            this.name = itemView.findViewById(R.id.name);
            this.id = itemView.findViewById(R.id.id);
        }
    }
}
