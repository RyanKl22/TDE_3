import java.util.Arrays;

public abstract class TabelaHash {
    protected String[] tabela;
    protected int tamanho;
    protected int contagemColisoes;

    public TabelaHash(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new String[tamanho];
        Arrays.fill(this.tabela, null);
        this.contagemColisoes = 0;
    }

    protected abstract int calcularHash(String chave);

    public void inserir(String chave) {
        int hash = calcularHash(chave);
        int posicao = hash;

        while (tabela[posicao] != null) {
            if (tabela[posicao].equals(chave)) {
                return;
            }
            contagemColisoes++;
            posicao = (posicao + 1) % tamanho;
        }

        tabela[posicao] = chave;
    }

    public int getContagemColisoes() {
        return contagemColisoes;
    }

    public int getDistribuicao() {
        int ocupados = 0;
        for (String chave : tabela) {
            if (chave != null) {
                ocupados++;
            }
        }
        return ocupados;
    }
}
