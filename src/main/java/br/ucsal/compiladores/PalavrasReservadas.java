package br.ucsal.compiladores;

public enum PalavrasReservadas {
    BOOL("BOOL", 310, "BOO", "ABO"),
    END("END", 318),
    DIFERENTE_DE("!=", 410),
    DIFERENTE("!", 421),
    CHARACTER(null, 510),
    WHILE("WHILE", 311),
    RETURN("RETURN", 319),
    SHARP("#", 410),
    RESTO_DIV("%", 422),
    CONSTANT_STRING(null, 511),
    BREAK("BREAK", 312),
    FALSE("FALSE", 320),
    AND("&", 411),
    FECHA_PARENT(")", 423),
    FLOAT_NUMBER(null, 512),
    VOID("VOID", 313, "VOI"),
    PROGRAM("PROGRAM", 321),
    ABRE_PARENT("(", 412),
    MULTIPLY("*", 424),
    FUNCTION(null, 513),
    CHAR("CHAR", 314, "CHC", "ACH"),
    FLOAT("FLOAT", 322, "PFO", "APF"),
    DIV("/", 413),
    VIRGULA(",", 425),
    IDENTIFIER(null, 514),
    TRUE("TRUE", 315),
    INT("INT", 323, "INT", "AIN"),
    PONTO_VIRGULA(";", 414),
    FECHA_COLCHE("]", 426),
    INTEGER_NUMBER(null, 515),
    ELSE("ELSE", 316),
    IF("IF", 324),
    ABRE_COLCHE("[", 415),
    PIPE("|", 427),
    STRING("STRING", 317, "STR", "AST"),
    ABRE_CHAVE("{", 416),
    FECHA_CHAVE("}", 428),
    SOMA("+", 417),
    MENOR_QUE("<", 429),
    MENOR_IGUAL("<=", 418),
    IGUAL("==", 430),
    ATRIBUICAO("=", 419),
    MAIOR_QUE(">", 431),
    MAIOR_IGUAL(">=", 420),
    SUBITRACAO("-", 432),
    BEGIN("BEGIN", 325);

    private final String atomo;
    private final Integer codigo;
    private final String siglaTipo;
    private final String siglaTipoArray;

    PalavrasReservadas(String atomo, Integer codigo, String siglaTipo, String siglaTipoArray) {
        this.atomo = atomo;
        this.codigo = codigo;
        this.siglaTipo = siglaTipo;
        this.siglaTipoArray = siglaTipoArray;
    }

    PalavrasReservadas(String atomo, Integer codigo, String siglaTipo) {
        this.atomo = atomo;
        this.codigo = codigo;
        this.siglaTipo = siglaTipo;
        this.siglaTipoArray = null;
    }

    PalavrasReservadas(String atomo, Integer codigo) {
        this.atomo = atomo;
        this.codigo = codigo;
        this.siglaTipo = null;
        this.siglaTipoArray = null;
    }

    public static PalavrasReservadas getEqual(String atomo) {
        for (PalavrasReservadas palavraReservada : PalavrasReservadas.values()) {
            if (palavraReservada.getAtomo() != null) {
                if (palavraReservada.getAtomo().equals(atomo)) {
                    return palavraReservada;
                }
            }
        }
        return null;
    }

    public String getAtomo() {
        return atomo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getSiglaTipo() {
        return siglaTipo;
    }

    public String getSiglaTipoArray() {
        return siglaTipoArray;
    }
}
