package com.example.projetogamelog;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "jogos")
public class Jogo {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @NonNull
    private String nome;

    @NonNull
    private PlataformaJogo plataforma;

    private StatusJogo status;
    private boolean concluidoTodasConquistas;
    private Date dataInicio;
    private Date dataFim;

    public Jogo() {
    }

    public Jogo(String nome, PlataformaJogo plataforma) {
        this.nome = nome;
        this.plataforma = plataforma;
        this.status = StatusJogo.BACKLOG;
    }

    public Jogo(String nome, PlataformaJogo plataforma, StatusJogo status, boolean concluidoTodasConquistas, Date dataInicio, Date dataFim) {
        this.nome = nome;
        this.plataforma = plataforma;
        this.status = status;
        this.concluidoTodasConquistas = concluidoTodasConquistas;
        //this.dataInicio = dataInicio;
        //this.dataFim = dataFim;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public PlataformaJogo getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(PlataformaJogo plataforma) {
        this.plataforma = plataforma;
    }

    public StatusJogo getStatus() {
        return status;
    }

    public void setStatus(StatusJogo status) {
        this.status = status;
    }

    public boolean isConcluidoTodasConquistas() {
        return concluidoTodasConquistas;
    }

    public void setConcluidoTodasConquistas(boolean concluidoTodasConquistas) {
        this.concluidoTodasConquistas = concluidoTodasConquistas;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return nome + " | " + plataforma + " | Status: " + status;
    }
}
