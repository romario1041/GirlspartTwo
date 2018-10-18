package com.example.aula_01.recyclefatima;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FatimaAdapter extends RecyclerView.Adapter<FatimaAdapter.MyViewHolder> {

    List<MinasProgramacao> list;
    List<MinasProgramacao> minasChecked;
    Context context;


    public FatimaAdapter(List<MinasProgramacao> list, Context context){
        this.list = list;
        this.context = context;
        this.minasChecked = new ArrayList<>();
    }

    @NonNull
    @Override
    public FatimaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(inflate.inflate(R.layout.layout_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FatimaAdapter.MyViewHolder holder, int position) {
        MinasProgramacao minasProgramacao = list.get(position);
       holder.bind(minasProgramacao.getNome(), minasProgramacao.getImagem());
       holder.checkBox.setChecked(minasProgramacao.isItSelected());
       holder.setClick_listener(new Click_Listener() {
           @Override
           public void onItemClick(View view, int position) {
               CheckBox checkBox = (CheckBox) view;
               MinasProgramacao minaCurrent = list.get(position);

               if(checkBox.isChecked()){
                   minaCurrent.setSelected(true);
                   minasChecked.add(minaCurrent);
               }else if(!checkBox.isChecked()){
                   minaCurrent.setSelected(false);
                   minasChecked.remove(minaCurrent);
               }
           }
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // aqui se encerra a classe FatimaAdapter e abaixo inicia a classe viewHolder, porque viewHolder trabalha junto com adapter
    // neste caso, por trabalharem juntas, podemos ter "classe dentro de classe"



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nome;
        ImageView imagem;
        CheckBox checkBox;
        public Click_Listener click_listener;
        public Context context;



        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.nome = itemView.findViewById(R.id.textodaimagem);
            this.imagem = itemView.findViewById(R.id.imagem);
            this.checkBox = itemView.findViewById(R.id.check);
            this.checkBox.setOnClickListener(this);
        }

        public void setClick_listener(Click_Listener click_listener){
            this.click_listener = click_listener;
        }

        @Override
        public void onClick(View view){
            this.click_listener.onItemClick(view, getLayoutPosition());
        }

        public int getImageDrawableResId(String imageId ){
            Resources resources = context.getResources();
            return resources.getIdentifier(imageId, "drawable", context.getPackageName());
        }

        public void bind(String nome, String imagem){
            this.nome.setText(nome);
            this.imagem.setImageResource(getImageDrawableResId(imagem));
        }


    }
}
