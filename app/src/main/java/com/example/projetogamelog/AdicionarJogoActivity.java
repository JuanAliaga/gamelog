package com.example.projetogamelog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AdicionarJogoActivity extends AppCompatActivity {

    private EditText editNomeJogo;
    private RadioGroup radioPlataform;
    private Spinner spinnerStatus;
    private CheckBox checkPlatina;
    private TextView titleActivity;

    public static final String MODO = "MODO";

    public static final int NOVOJOGO    = 1;
    public static final int EDITARJOGO = 2;
    private int modo;
    private String nomeAntigoJogo;
    private int plataformaAntigo;
    private int statusAntigo;
    private boolean checkPlatinaAntigo;

    public static final String NOMEJOGO = "NOMEJOGO";
    public static final String PLATAFORMAJOGO = "PLATAFORMAJOGO";
    public static final String STATUSJOGO = "STATUSJOGO";
    public static final String TODASCONQUISTAS = "TODASCONQUISTAS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editNomeJogo = findViewById(R.id.addNomeJogo);
        radioPlataform= findViewById(R.id.plataforma);
        spinnerStatus = findViewById(R.id.statusSpinner);
        checkPlatina = findViewById(R.id.checkPlatinado);
        titleActivity = findViewById(R.id.titleActivity);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){

            modo = bundle.getInt(MODO, NOVOJOGO);

            if (modo == NOVOJOGO){
                setTitle(getString(R.string.btnAdicionarJogo));
            }else{

                nomeAntigoJogo = bundle.getString(NOMEJOGO);
                plataformaAntigo = bundle.getInt(PLATAFORMAJOGO);
                statusAntigo = bundle.getInt(STATUSJOGO);
                checkPlatinaAntigo = bundle.getBoolean(TODASCONQUISTAS);

                editNomeJogo.setText(nomeAntigoJogo);
                radioPlataform.check(radioPlataform.getChildAt(plataformaAntigo).getId());
                spinnerStatus.setSelection(statusAntigo);
                checkPlatina.setChecked(checkPlatinaAntigo);
                setTitle(getString(R.string.editarButton));
                titleActivity.setText(R.string.editarButton);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adicionar_jogo_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.salvarMenu:
                salvarJogo();
                break;

            case R.id.limparMenu:r:
                limparCampos();
                break;

            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void limparCampos(){
        editNomeJogo.setText(null);
        radioPlataform.clearCheck();
        spinnerStatus.setSelection(0);
        checkPlatina.setChecked(false);
        Toast.makeText(getBaseContext(),getString(R.string.limparCampos), Toast.LENGTH_LONG).show();
    }

    public void salvarJogo(){
        if(validar()){
            Intent intent = new Intent();
            intent.putExtra(NOMEJOGO, editNomeJogo.getText().toString());
            String plataforma;
            switch (radioPlataform.getCheckedRadioButtonId()){
                case R.id.plataformaXbox:
                    plataforma = PlataformaJogo.XBOX.getDescricao();
                    break;
                case R.id.plataformaPC:
                    plataforma = PlataformaJogo.PC.getDescricao();
                    break;
                case R.id.plataformaNintendo:
                    plataforma = PlataformaJogo.NINTENDO.getDescricao();
                    break;
                case R.id.plataformaPS:
                    plataforma = PlataformaJogo.PLAYSTATION.getDescricao();
                    break;
                default:plataforma = PlataformaJogo.PC.getDescricao();
            }
            intent.putExtra(PLATAFORMAJOGO,plataforma);
            intent.putExtra(STATUSJOGO,spinnerStatus.getSelectedItem().toString());
            intent.putExtra(TODASCONQUISTAS,String.valueOf(checkPlatina.isChecked()));
            Toast.makeText(getBaseContext(),getString(R.string.saveSucess), Toast.LENGTH_LONG).show();
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }

    public boolean validar(){
        String[] status = getResources().getStringArray(R.array.status);
        if(editNomeJogo.getText().toString().isEmpty()){
            editNomeJogo.setError(getString(R.string.erroNomeVazio));
            editNomeJogo.requestFocus();
            Toast.makeText(getBaseContext(),getString(R.string.erroNomeVazio), Toast.LENGTH_LONG).show();
            return false;
        }
        if(radioPlataform.getCheckedRadioButtonId()==-1){
            Toast.makeText(getApplicationContext(),getString(R.string.erroPlataformaVazia), Toast.LENGTH_LONG).show();
            return false;
        }if(spinnerStatus.getSelectedItem().equals(status[0])){
            Toast.makeText(getApplicationContext(),getString(R.string.erroStatusInvalido), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static void editarJogo(AppCompatActivity activity, Jogo jogo){

        Intent intent = new Intent(activity, AdicionarJogoActivity.class);

        intent.putExtra(MODO, EDITARJOGO);
        intent.putExtra(NOMEJOGO, jogo.getNome());
        intent.putExtra(PLATAFORMAJOGO,jogo.getPlataforma().getCodigo());
        intent.putExtra(STATUSJOGO,jogo.getStatus().getCodigo());
        intent.putExtra(TODASCONQUISTAS,jogo.isConcluidoTodasConquistas());


        activity.startActivityForResult(intent, EDITARJOGO);
    }

    public static void novoJogo(AppCompatActivity activity){

        Intent intent = new Intent(activity, AdicionarJogoActivity.class);

        intent.putExtra(MODO, NOVOJOGO);

        activity.startActivityForResult(intent, NOVOJOGO);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
    }
}