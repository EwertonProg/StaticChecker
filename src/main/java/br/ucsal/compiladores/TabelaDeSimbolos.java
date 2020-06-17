package br.ucsal.compiladores;

import java.util.ArrayList;
import java.util.Optional;

public class TabelaDeSimbolos {
    private static final ArrayList<ItemTabelaDeSimbolos> itens = new ArrayList<>();
    private static int sequence = 0;

    public static void adicionaritem(ItemTabelaDeSimbolos item) {
        Optional<ItemTabelaDeSimbolos> optionalitem;
        if ((optionalitem = itens.stream().filter(i -> i.getElemento().getConteudo().equals(item.getElemento().getConteudo())).findFirst()).isPresent()) {
            ItemTabelaDeSimbolos itemJaAdicionado = optionalitem.get();
            itemJaAdicionado.addAparicao(item.getElemento().getLinha());
            if (itemJaAdicionado.getTipoDoSimbolo() == null && item.getTipoDoSimbolo() != null) {
                itemJaAdicionado.setTipoDoSimbolo(item.getTipoDoSimbolo());
            }
        } else {
            item.addAparicao(item.getElemento().getLinha());
            item.setId(getNextId());
            itens.add(item);
        }
    }

    public static ArrayList<ItemTabelaDeSimbolos> getItens() {
        return itens;
    }

    public static int getNextId() {
        sequence = ++sequence;
        return sequence;
    }
}
