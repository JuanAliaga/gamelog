package com.example.projetogamelog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class JogoAdapter extends BaseAdapter {

    private Context context;
    private List<Jogo> jogos;


    public static class JogoHolder{
        private TextView txtNomeJogo;
        private TextView txtPlataforma;
        private TextView txtStatus;
        private TextView txtConcluidoTodasConquistas;
    }

    public JogoAdapter(Context context, List<Jogo> jogos) {
        this.context = context;
        this.jogos = jogos;
    }

    @Override
    public int getCount() {
        return jogos.size();
    }

    @Override
    public Object getItem(int i) {
        return jogos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        JogoHolder holder;

        if(view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_jogo_lista, viewGroup, false);

            holder = new JogoHolder();

            holder.txtNomeJogo = view.findViewById(R.id.txtNomeJogo);
            holder.txtPlataforma = view.findViewById(R.id.txtPlataforma);
            holder.txtStatus = view.findViewById(R.id.txtStatus);
            holder.txtConcluidoTodasConquistas = view.findViewById(R.id.txtConcluidoTodasConquistas);

            view.setTag(holder);
        }else{
            holder = (JogoHolder) view.getTag();
        }

        //TextView de Nome do Jogo
        holder.txtNomeJogo.setText(jogos.get(i).getNome());

        // TextView de 100% conquistas
        if (jogos.get(i).isConcluidoTodasConquistas()) {
            holder.txtConcluidoTodasConquistas.setVisibility(View.VISIBLE);
        } else {
            holder.txtConcluidoTodasConquistas.setVisibility(View.INVISIBLE);
        }

        //TextView de Status
        switch(jogos.get(i).getStatus()){
            case BACKLOG:
                holder.txtStatus.setText("Backlog");
                break;
            case JOGANDO:
                holder.txtStatus.setText("Jogando");
                break;
            case CONCLUIDO:
                holder.txtStatus.setText("Concluído");
                break;
        }

        //TextView de Plataforma
        switch(jogos.get(i).getPlataforma()){
            case XBOX:
                holder.txtPlataforma.setText("Xbox");
                break;
            case PC:
                holder.txtPlataforma.setText("PC");
                break;
            case NINTENDO:
                holder.txtPlataforma.setText("Nintendo");
                break;
            case PLAYSTATION:
                holder.txtPlataforma.setText("Playstation");
                break;
        }

        return view;
    }
}
