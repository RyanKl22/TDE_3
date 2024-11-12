import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InterfaceTabelaHash extends JFrame {
    private JButton botaoCarregar, botaoInserir, botaoRelatorio;
    private JTextArea areaRelatorio;
    private TabelaHashFuncao1 tabelaHash1;
    private TabelaHashFuncao2 tabelaHash2;
    private List<String> nomes;

    public InterfaceTabelaHash() {
        setTitle("Comparativo de Tabelas Hash");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        botaoCarregar = new JButton("Carregar CSV");
        botaoInserir = new JButton("Inserir Dados");
        botaoRelatorio = new JButton("Gerar Relatório");
        areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);
        areaRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel painelSuperior = new JPanel();
        painelSuperior.add(botaoCarregar);
        painelSuperior.add(botaoInserir);
        painelSuperior.add(botaoRelatorio);

        add(painelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);

        botaoCarregar.addActionListener(new AcaoCarregar());
        botaoInserir.addActionListener(new AcaoInserir());
        botaoRelatorio.addActionListener(new AcaoRelatorio());

        tabelaHash1 = new TabelaHashFuncao1(10000);
        tabelaHash2 = new TabelaHashFuncao2(10000);
    }

    private class AcaoCarregar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser seletorArquivo = new JFileChooser();
            int opcao = seletorArquivo.showOpenDialog(InterfaceTabelaHash.this);
            if (opcao == JFileChooser.APPROVE_OPTION) {
                File arquivo = seletorArquivo.getSelectedFile();
                nomes = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        nomes.add(linha.trim());
                    }
                    areaRelatorio.append("Arquivo carregado com sucesso: " + arquivo.getName() + "\n");
                } catch (IOException ex) {
                    areaRelatorio.append("Erro ao carregar o arquivo.\n");
                }
            }
        }
    }

    private class AcaoInserir implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (nomes == null || nomes.isEmpty()) {
                areaRelatorio.append("Por favor, carregue um arquivo primeiro.\n");
                return;
            }
            long tempoInicio1 = System.nanoTime();
            for (String nome : nomes) {
                tabelaHash1.inserir(nome);
            }
            long tempoFim1 = System.nanoTime();

            long tempoInicio2 = System.nanoTime();
            for (String nome : nomes) {
                tabelaHash2.inserir(nome);
            }
            long tempoFim2 = System.nanoTime();

            areaRelatorio.append("Inserção completa.\n");
            areaRelatorio.append("Tempo de inserção Tabela 1: " + (tempoFim1 - tempoInicio1) / 1_000_000 + " ms\n");
            areaRelatorio.append("Tempo de inserção Tabela 2: " + (tempoFim2 - tempoInicio2) / 1_000_000 + " ms\n");
        }
    }

    private class AcaoRelatorio implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (nomes == null || nomes.isEmpty()) {
                areaRelatorio.append("Por favor, carregue e insira os dados primeiro.\n");
                return;
            }

            StringBuilder relatorio = new StringBuilder();
            relatorio.append("\n--- Relatório Final ---\n\n");

            relatorio.append(String.format("%-25s %-15s %-15s\n", "Resultados", "Tabela Hash 1", "Tabela Hash 2"));
            relatorio.append(String.format("%-25s %-15s %-15s\n", "-------------------------", "---------------", "---------------"));

            relatorio.append(String.format("%-25s %-15d %-15d\n", "Número de Colisões", tabelaHash1.getContagemColisoes(), tabelaHash2.getContagemColisoes()));

            relatorio.append(String.format("%-25s %-15s %-15s\n", "Tempo de Inserção (ms)", calcularTempoInsercao(tabelaHash1), calcularTempoInsercao(tabelaHash2)));

            relatorio.append(String.format("%-25s %-15d %-15d\n", "Distribuição de Chaves", tabelaHash1.getDistribuicao(), tabelaHash2.getDistribuicao()));

            areaRelatorio.setText(relatorio.toString());
        }

        private String calcularTempoInsercao(TabelaHash tabela) {
            long tempoInicio = System.nanoTime();
            for (String nome : nomes) {
                tabela.inserir(nome);
            }
            long tempoFim = System.nanoTime();
            return String.valueOf((tempoFim - tempoInicio) / 1_000_000);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceTabelaHash gui = new InterfaceTabelaHash();
            gui.setVisible(true);
        });
    }
}
