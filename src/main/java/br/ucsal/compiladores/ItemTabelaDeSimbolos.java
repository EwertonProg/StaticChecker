package br.ucsal.compiladores;

import java.util.ArrayList;
import java.util.List;

public class ItemTabelaDeSimbolos {
    private final Elemento elemento;
    private final List<Integer> linhasPrimeirasAparicoes = new ArrayList<>();
    private Integer id = 0;
    private String tipoDoSimbolo;

    public ItemTabelaDeSimbolos(Elemento elemento, String tipoDoSimbolo) {
        this.elemento = elemento;
        this.tipoDoSimbolo = tipoDoSimbolo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public String getTipoDoSimbolo() {
        return tipoDoSimbolo;
    }

    public void setTipoDoSimbolo(String tipoDoSimbolo) {
        this.tipoDoSimbolo = tipoDoSimbolo;
    }

    public List<Integer> getLinhasPrimeirasAparicoes() {
        return linhasPrimeirasAparicoes;
    }

    public void addAparicao(Integer linha) {
        if (linhasPrimeirasAparicoes.size() < 5) {
            linhasPrimeirasAparicoes.add(linha);
        }
    }
}
