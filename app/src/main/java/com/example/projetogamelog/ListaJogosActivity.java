package com.example.projetogamelog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetogamelog.persistence.JogoDatabase;
import com.example.projetogamelog.utils.UtilsGUI;
import java.util.Date;
import java.util.List;

public class ListaJogosActivity extends AppCompatActivity {

    ListView listViewJogos;
    List<Jogo> jogos;
    JogoAdapter jogoAdapter;

    private ActionMode actionMode;
    private int        posicaoSelecionada = -1;
    private View       viewSelecionada;

    private static final String ARQUIVO =
            "br.juanaliaga.sharedpreferences.TEMAS";

    private static final String DARKMODE = "DARKMODE";

    private int temaDefault = 2;
    private int novoTema;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflate = mode.getMenuInflater();
            inflate.inflate(R.menu.listagem_item_selecionado_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch(item.getItemId()){
                case R.id.editMenu:
                    editarJogo();
                    mode.finish();
                    return true;

                case R.id.deleteMenu:
                    excluirJogo();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode         = null;
            viewSelecionada    = null;
            listViewJogos.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_jogos);


        listViewJogos = findViewById(R.id.listViewJogos);

        listViewJogos.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id){
                Jogo jogo = (Jogo) listViewJogos.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),jogo.getNome() + " - " + jogo.getPlataforma(),Toast.LENGTH_SHORT).show();


            }
        });

        listViewJogos.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        if (actionMode != null){
                            return false;
                        }
                        view.setBackgroundColor(getResources().getColor(R.color.green));
                        viewSelecionada = view;
                        posicaoSelecionada = position;
                        listViewJogos.setEnabled(false);
                        actionMode = startSupportActionMode(mActionModeCallback);

                        return true;
                    }
                });
        lerPreferences();
        popularListaJogos();
    }

    private void lerPreferences() {
        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                Context.MODE_PRIVATE);

        temaDefault = shared.getInt(DARKMODE, temaDefault);

        trocarTema();
    }

    private void salvarPreferencias(){
        SharedPreferences shared = getSharedPreferences(ARQUIVO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(DARKMODE, novoTema);

        editor.commit();
        temaDefault = novoTema;
        trocarTema();
    }

    @SuppressLint("WrongConstant")
    private void trocarTema() {
            AppCompatDelegate.setDefaultNightMode(temaDefault);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listagem_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.sobreMenu:
                sobre();
                break;

            case R.id.adicionarMenu:
                adicionarJogo();
                break;

            case R.id.DarkMode:
                if(temaDefault==2){
                   trocarTema();
                    novoTema = 1;
                }else{
                   trocarTema();
                    novoTema = 2;
                }
                 salvarPreferencias();
                break;
            default:
                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private void popularListaJogos() {
        JogoDatabase jogoDatabase = JogoDatabase.getDatabase(ListaJogosActivity.this);

        jogos= jogoDatabase.JogoDao().jogosAll();
        jogoAdapter = new JogoAdapter(this, jogos);

        listViewJogos.setAdapter(jogoAdapter);


    }

    public void sobre(){
        Intent intent = new Intent(ListaJogosActivity.this, AboutActivity.class);

        startActivity(intent);
    }

    public void adicionarJogo(){
        AdicionarJogoActivity.novoJogo(this);
    }

    private void excluirJogo(){
        Jogo jogo = jogos.get(posicaoSelecionada);
        JogoDatabase jogoDatabase = JogoDatabase.getDatabase(ListaJogosActivity.this);

        String msg = getString(R.string.deleteMsg) + jogo.getNome() + "?";
        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                jogoDatabase.JogoDao().delete(jogo);
                                jogos.remove(jogo);
                                jogoAdapter.notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
        UtilsGUI.alertDialogConfirm(this,msg,listener);
    }

    private void editarJogo() {
        Jogo jogo = jogos.get(posicaoSelecionada);
        AdicionarJogoActivity.editarJogo(this,jogo);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        JogoDatabase jogoDatabase = JogoDatabase.getDatabase(ListaJogosActivity.this);
        if(resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();

            if(bundle != null){
                Jogo jogo = new Jogo();
                jogo.setNome(bundle.getString(AdicionarJogoActivity.NOMEJOGO));
                jogo.setPlataforma(PlataformaJogo.valueOf(bundle.getString(AdicionarJogoActivity.PLATAFORMAJOGO)));
                jogo.setStatus(StatusJogo.valueOf(bundle.getString(AdicionarJogoActivity.STATUSJOGO)));
                jogo.setConcluidoTodasConquistas(Boolean.parseBoolean(bundle.getString(AdicionarJogoActivity.TODASCONQUISTAS)));
                long time = bundle.getLong(AdicionarJogoActivity.DATAINICIO);
                long timeEnd= bundle.getLong(AdicionarJogoActivity.DATAFIM);
                if(time != 0){
                    jogo.setDataInicio(new Date(time));
                }
                if(timeEnd != 0){
                    jogo.setDataFim(new Date(timeEnd));
                }

                if(requestCode == AdicionarJogoActivity.EDITARJOGO){
                    Jogo jogoAntigo = jogos.get(posicaoSelecionada);
                    jogoAntigo.setNome(jogo.getNome());
                    jogoAntigo.setPlataforma(jogo.getPlataforma());
                    jogoAntigo.setStatus(jogo.getStatus());
                    jogoAntigo.setConcluidoTodasConquistas(jogo.isConcluidoTodasConquistas());
                    jogoAntigo.setDataInicio(jogo.getDataInicio());
                    jogoAntigo.setDataFim(jogo.getDataFim());
                    jogoDatabase.JogoDao().update(jogoAntigo);
                }else{
                    jogos.add(jogo);
                    jogoDatabase.JogoDao().insert(jogo);
                }
                jogoAdapter.notifyDataSetChanged();
            }
        }
    }
}