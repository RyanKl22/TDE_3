public class TabelaHashFuncao1 extends TabelaHash {

    public TabelaHashFuncao1(int tamanho) {
        super(tamanho);
    }

    @Override
    protected int calcularHash(String chave) {

        int sum = 0;

        for (int i = 0; i < chave.length(); i++) {
            sum += (int) (chave.charAt(i));
        }

        return sum % tamanho;
    }
}
