package br.ucsal.compiladores;

public class Elemento {
    private PalavrasReservadas palavraReservada;
    private String conteudo;
    private int tamanho;
    private int linha;
    private int posicaoLinha;
    private int tamanhoAntesTruncagem;
    private int indiceTabelaSimbolo;

    public Elemento(PalavrasReservadas palavraReservada, java.lang.String conteudo, int tamanho, int linha, int posicaoLinha, int tamanhoAntesTruncagem) {
        this.palavraReservada = palavraReservada;
        this.conteudo = conteudo;
        this.tamanho = tamanho;
        this.linha = linha;
        this.posicaoLinha = posicaoLinha;
        this.tamanhoAntesTruncagem = tamanhoAntesTruncagem;
    }

    public PalavrasReservadas getPalavraReservada() {
        return palavraReservada;
    }

    public void setPalavraReservada(PalavrasReservadas palavraReservada) {
        this.palavraReservada = palavraReservada;
    }

    public java.lang.String getConteudo() {
        return conteudo;
    }

    public void setConteudo(java.lang.String conteudo) {
        this.conteudo = conteudo;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public int getTamanhoAntesTruncagem() {
        return tamanhoAntesTruncagem;
    }

    public void setTamanhoAntesTruncagem(int tamanhoAntesTruncagem) {
        this.tamanhoAntesTruncagem = tamanhoAntesTruncagem;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getPosicaoLinha() {
        return posicaoLinha;
    }

    public void setPosicaoLinha(int posicaoLinha) {
        this.posicaoLinha = posicaoLinha;
    }

    public int getIndiceTabelaSimbolo() {
        return indiceTabelaSimbolo;
    }

    public void setIndiceTabelaSimbolo(int indiceTabelaSimbolo) {
        this.indiceTabelaSimbolo = indiceTabelaSimbolo;
    }
}
