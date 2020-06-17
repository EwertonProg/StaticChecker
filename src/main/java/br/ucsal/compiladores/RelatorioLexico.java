package br.ucsal.compiladores;

import java.util.List;

public class RelatorioLexico {
    private final List<Elemento> elementos;

    public RelatorioLexico(List<Elemento> elementos) {
        this.elementos = elementos;
    }

    public List<Elemento> getElementos() {
        return elementos;
    }
}
