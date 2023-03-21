package com.example.projetogamelog;

public enum PlataformaJogo {
    PC ("PC",0),
    PLAYSTATION("PLAYSTATION",1),
    XBOX ("XBOX",2),
    NINTENDO("NINTENDO",3);

    private String descricao;
    private int codigo;

    PlataformaJogo(String descricao, int codigo) {
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

