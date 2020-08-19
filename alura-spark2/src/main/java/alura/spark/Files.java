package alura.spark;

public enum Files {

    CONTABILIDADE_CSV_O1("contabilidade01.csv"),
    CONTABILIDADE_CSCV_O2("contabilidade02.csv"),
    LOJA_ORK("loja.orc"),
    SITE_JSON("site.json");

    private String fileName;

    private Files(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

}
