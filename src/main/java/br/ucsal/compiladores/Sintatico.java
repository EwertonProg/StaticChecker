package br.ucsal.compiladores;

import java.io.*;
import java.util.Scanner;

public class Sintatico {
    private static String fileName;
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Insira o nome do arquivo: ");
            String nomeArquivo = scanner.next();
            File file = new File(nomeArquivo + ".201");
            fileName = file.getName().replace(".201","");
            Lexico lexico = new Lexico(new BufferedReader(new FileReader(file)));
            gerarRelatorioLexico(lexico.fazerAnalise());
            gerarRelatorioTabelaSimbolos();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gerarRelatorioTabelaSimbolos() throws IOException {
        File relatorio = new File(fileName+".TAB");
        FileWriter relatorioWriter = new FileWriter(relatorio);
        for (ItemTabelaDeSimbolos item : TabelaDeSimbolos.getItens()) {
            relatorioWriter.append("\n");
            for (int i = 0; i <= 6; i++) {
                switch (i) {
                    case 0:
                        relatorioWriter.append("Numero_Entrada: ").append(String.valueOf(item.getId())).append(", ");
                        break;
                    case 1:
                        relatorioWriter.append("Codigo_Atomo: ").append(String.valueOf(item.getElemento().getPalavraReservada().getCodigo())).append(", ");
                        break;
                    case 2:
                        relatorioWriter.append("Lexeme: ").append(item.getElemento().getConteudo()).append(", ");
                        break;
                    case 3:
                        relatorioWriter.append("Qtd_chars_antes_trunc: ").append(String.valueOf(item.getElemento().getTamanhoAntesTruncagem())).append(", ");
                        break;
                    case 4:
                        relatorioWriter.append("Qtd_chars_depois_trunc: ").append(String.valueOf(item.getElemento().getTamanho())).append(", ");
                        break;
                    case 5:
                        String tipoSimbolo = item.getTipoDoSimbolo();
                        if (tipoSimbolo != null) {
                            relatorioWriter.append("Tipo_simbolo: ").append(tipoSimbolo).append(", ");
                        } else {
                            relatorioWriter.append("Tipo_simbolo: ").append("-").append(", ");
                        }
                        break;
                    case 6:
                        relatorioWriter.append("Linhas_aparicoes: {").append(item.getLinhasPrimeirasAparicoes().stream().map(Object::toString).reduce((conjunto, elemento) -> conjunto + ", " + elemento).get()).append("}.");
                        break;
                }
            }
        }
        relatorioWriter.close();
    }

    public static void gerarRelatorioLexico(RelatorioLexico relatorio) throws IOException {
        File relatorioFile = new File(fileName+".LEX");
        FileWriter relatorioWriter = new FileWriter(relatorioFile);
        relatorioWriter.append("Codigo da Equipe: 02\n");
        relatorioWriter.append("Componentes: \n");
        relatorioWriter.append("\tEwerton Luis de Jesus Santana; ewerton.santana@ucsal.edu.br; (71)98824-0646\n");
        relatorioWriter.append("\tJean Lima de Souza Junior; jeanl.junior@ucsal.edu.br; (75)99222-8501\n");
        for (Elemento elemento : relatorio.getElementos()) {
            relatorioWriter.append("\n");
            for (int i = 0; i <= 3; i++) {
                switch (i) {
                    case 0:
                        relatorioWriter.append("Lexeme: ").append(elemento.getConteudo()).append(", ");
                        break;
                    case 1:
                        relatorioWriter.append("Codigo_Atomo: ").append(String.valueOf(elemento.getPalavraReservada().getCodigo())).append(", ");
                        break;
                    case 2:
                        relatorioWriter.append("Indice_Tab_Simbolo: ").append(String.valueOf(elemento.getIndiceTabelaSimbolo())).append(", ");
                        break;
                    case 3:
                        relatorioWriter.append("Linha: ").append(String.valueOf(elemento.getLinha())).append(".");
                        break;
                }
            }
        }
        relatorioWriter.close();
    }
}
