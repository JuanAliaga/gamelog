package com.example.projetogamelog;

public enum StatusJogo {
    BACKLOG("Backlog",1),
    JOGANDO("Jogando",2),
    CONCLUIDO("Concluido",3);

    private String descricao;
    private int codigo;

    StatusJogo(String descricao, int codigo) {
        this.descricao = descricao;
        this.codigo=codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCodigo() {
        return codigo;
    }
}
