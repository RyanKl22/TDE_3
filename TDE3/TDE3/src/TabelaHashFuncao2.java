public class TabelaHashFuncao2 extends TabelaHash {

    public TabelaHashFuncao2(int tamanho) {
        super(tamanho);
    }

    @Override
    protected int calcularHash(String chave) {
        int hash = 0;
        int primo = 31;
        for (int i = 0; i < chave.length(); i++) {
            hash = primo * hash + chave.charAt(i);
        }
        return Math.abs(hash) % tamanho;
    }
}
