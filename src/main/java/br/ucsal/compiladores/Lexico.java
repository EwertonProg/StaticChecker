package br.ucsal.compiladores;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lexico {
    private static final List<Character> simbolos = Arrays.asList('\'', '$', '_', '.', '\"', '|', '&', '!', '=', '>', '<', '#', '+', '-', '*', '/', '[', ']', ';', ',', '(', ')', '{', '}', ' ', (char) 10, (char) 9, (char) 13);
    private static final List<Character> naoSimbolos = Arrays.asList('Ã', 'Õ', 'º', 'ª', 'Ç');
    private final BufferedReader bufferDeEntrada;
    private final List<Elemento> elementosEncontrados = new ArrayList<>();
    private int characterAtual = 0;
    private int posicaoLinha = 1;
    private int linha = 1;
    private boolean continuarMesmoCharacter = false;
    private Character charCache = null;
    private PalavrasReservadas ultimoTipoEncontrado;
    private String siglaUltimoTipo;

    public Lexico(BufferedReader bufferDeEntrada) {
        this.bufferDeEntrada = bufferDeEntrada;
    }

    private Elemento findAtomo() throws IOException {
        String atomoAnterior = "";
        StringBuilder atomoAtual = new StringBuilder();
        Elemento elemento = null;
        boolean aspas = false;
        boolean comentarioLinha = false;
        boolean comentarioBloco = false;
        Character character;
        while ((character = getChar()) != null) {
            continuarMesmoCharacter = false;
                atomoAtual.append(character);
            if (!(comentarioLinha || comentarioBloco)) {
                if ((character == ' ' && !aspas) || character == '\n' || character == '\t') {
                    return elemento;
                }
                PalavrasReservadas pr = PalavrasReservadas.getEqual(atomoAtual.toString());
                if (atomoAtual.toString().equals("//")) {
                    comentarioLinha = true;
                    atomoAtual = new StringBuilder();
                } else if (atomoAtual.toString().equals("/*")) {
                    comentarioBloco = true;
                    atomoAtual = new StringBuilder();
                } else if (pr != null) {
                    atomoAnterior = atomoAtual.toString();
                    elemento = new Elemento(pr, atomoAnterior, atomoAtual.length(), linha, posicaoLinha, atomoAnterior.length());
                } else if (character == '\"') {
                    if (elemento != null && (elemento.getPalavraReservada() != PalavrasReservadas.CONSTANT_STRING)) {
                        continuarMesmoCharacter = true;
                        return elemento;
                    }
                    if (aspas) {
                        if (elemento != null) {
                            elemento.setTamanhoAntesTruncagem(atomoAtual.length());
                            return elemento;
                        } else {
                            java.lang.String conteudo = atomoAtual.toString();
                            return new Elemento(PalavrasReservadas.CONSTANT_STRING, conteudo, conteudo.length(), linha, posicaoLinha, conteudo.length());
                        }
                    }
                    aspas = true;
                } else if (aspas) {
                    if (isString(atomoAtual + "\"")) {
                        atomoAnterior = atomoAtual.toString();
                    } else if (elemento == null) {
                        java.lang.String conteudo = atomoAnterior + "\"";
                        elemento = new Elemento(PalavrasReservadas.CONSTANT_STRING, conteudo, conteudo.length(), linha, posicaoLinha, 0);
                    }
                } else if (isIdentifier(atomoAtual.toString())) {
                    atomoAnterior = atomoAtual.toString();
                    elemento = new Elemento(PalavrasReservadas.IDENTIFIER, atomoAnterior, atomoAnterior.length(), linha, posicaoLinha, atomoAnterior.length());
                } else if (isFunctionIdentifier(atomoAtual.toString())) {
                    atomoAnterior = atomoAtual.toString();
                    elemento = new Elemento(PalavrasReservadas.FUNCTION, atomoAnterior, atomoAnterior.length(), linha, posicaoLinha, atomoAnterior.length());
                } else if (isCharacter(atomoAtual.toString())) {
                    atomoAnterior = atomoAtual.toString();
                    return new Elemento(PalavrasReservadas.CHARACTER, atomoAnterior, atomoAnterior.length(), linha, posicaoLinha, atomoAnterior.length());
                } else if (isInteger(atomoAtual.toString())) {
                    atomoAnterior = atomoAtual.toString();
                    elemento = new Elemento(PalavrasReservadas.INTEGER_NUMBER, atomoAnterior, atomoAnterior.length(), linha, posicaoLinha, atomoAnterior.length());
                } else if (isFloat(atomoAtual.toString() + '0')) {
                    Character c = getChar();
                    atomoAnterior = atomoAtual.toString();
                    elemento = new Elemento(PalavrasReservadas.FLOAT_NUMBER, atomoAnterior, atomoAnterior.length(), linha, posicaoLinha, atomoAnterior.length());
                    if (c != null) {
                        atomoAtual.append(c);
                        atomoAnterior = atomoAtual.toString();
                        if (isFloat(atomoAtual.toString())) {
                            elemento = new Elemento(PalavrasReservadas.FLOAT_NUMBER, atomoAnterior, atomoAnterior.length(), linha, posicaoLinha, atomoAnterior.length());
                        } else {
                            charCache = (char) characterAtual;
                        }
                    }
                } else if (elemento != null) {
                    continuarMesmoCharacter = true;
                    return elemento;
                } else {
                    return null;
                }
            } else {
                elemento = null;
                if (comentarioLinha) {
                    if (atomoAtual.toString().equals("\n")) {
                        comentarioLinha = false;
                    }
                    atomoAtual = new StringBuilder();
                } else {
                    if (atomoAtual.toString().trim().equals("*/")) {
                        comentarioBloco = false;
                        atomoAtual = new StringBuilder();
                    } else if (atomoAtual.length() >= 2) {
                        atomoAtual = new StringBuilder();
                    }
                }
            }
        }
        return elemento;
    }

    private Character getChar() throws IOException {
        if (charCache != null) {
            Character c = charCache;
            charCache = null;
            return c;
        }
        if (continuarMesmoCharacter) {
            return Character.toUpperCase((char) characterAtual);
        }
        if ((characterAtual = bufferDeEntrada.read()) != -1) {
            if (characterAtual == 10) {
                linha++;
                posicaoLinha = 1;
                return '\n';
            }
            posicaoLinha++;
            if (isValid((char) characterAtual)) {
                return Character.toUpperCase((char) characterAtual);
            }
            return null;
        }
        return null;
    }

    public RelatorioLexico fazerAnalise() throws IOException {
        while (hasText()) {
            Elemento elemento = findAtomo();
            if (elemento != null) {
                if (elemento.getTamanhoAntesTruncagem() > 30) {
                    elemento.setTamanho(30);
                    if (elemento.getPalavraReservada() == PalavrasReservadas.CONSTANT_STRING) {
                        elemento.setConteudo(elemento.getConteudo().substring(0, 28) + "\"");
                    }
                    elemento.setConteudo(elemento.getConteudo().substring(0, 29));
                }
                elementosEncontrados.add(elemento);
                switch (elemento.getPalavraReservada()) {
                    case STRING:
                    case INT:
                    case CHAR:
                    case BOOL:
                    case VOID:
                    case FLOAT:
                        ultimoTipoEncontrado = elemento.getPalavraReservada();
                        siglaUltimoTipo = elemento.getPalavraReservada().getSiglaTipo();
                        break;
                    case ABRE_COLCHE:
                        if (ultimoTipoEncontrado != null) {
                            siglaUltimoTipo = ultimoTipoEncontrado.getSiglaTipoArray();
                        }
                        break;
                }
                if (elemento.getPalavraReservada().getAtomo() == null) {
                    switch (elemento.getPalavraReservada()) {
                        case IDENTIFIER:
                        case FUNCTION:
                            TabelaDeSimbolos.adicionaritem(new ItemTabelaDeSimbolos(elemento, siglaUltimoTipo));
                            siglaUltimoTipo = null;
                            ultimoTipoEncontrado = null;
                            break;
                    }
                }
            }
        }
        return new RelatorioLexico(elementosEncontrados);
    }

    private boolean hasText() {
        return characterAtual != -1;
    }

    private boolean isSimbolo(Character character) {
        return simbolos.contains(character);
    }

    private boolean isValid(Character character) {
        return (Character.isLetter(character) || Character.isDigit(character) || isSimbolo(character)) && !naoSimbolos.contains(character);
    }

    private boolean isIdentifier(java.lang.String identifier) {
        return identifier.matches("([_A-Z][_A-Z0-9]*[A-Z_])|[_]|[A-Z]");
    }

    private boolean isFunctionIdentifier(java.lang.String identifier) {
        return identifier.matches("([A-Z_][_A-Z0-9]*[A-Z0-9])|[A-Z]");
    }

    private boolean isInteger(java.lang.String identifier) {
        return identifier.matches("[\\d]+");
    }

    private boolean isFloat(java.lang.String identifier) {
        return identifier.matches("([0-9]+[.][0-9]+)[E][-+]?[\\d]+|[E]") || identifier.matches("[0-9]+[.][0-9]+");
    }

    private boolean isCharacter(java.lang.String identifier) {
        return identifier.matches("['][A-Z][']");
    }

    private boolean isString(java.lang.String identifier) {
        return identifier.matches("[\"][A-Z\\d\\s$_.]+[\"]");
    }

}
