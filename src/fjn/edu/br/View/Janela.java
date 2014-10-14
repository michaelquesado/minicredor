package fjn.edu.br.View;

import credicard.LeitorArquivoRemesa;
import fjn.edu.br.Model.SolicitacaoCompra;
import fjn.edu.br.dao.SolicitacaoCompraDAO;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.util.List;

/**
 * @author Antonio Siqueira
 * @author Fillip Quesado
 * @author Uelio Nobre
 */
public class Janela extends JFrame implements ActionListener {

    private JButton botaoAbrir, botaoGravar, botaoGerarRetorno;
    private JTextField tx;
    private JTable table;
    private DefaultTableModel defaultTableModel;
    private String campo[] = new String[8];
    private int intTotalRegistro, intNumRegistro, intRegistro, qtdePar;
    private String numSeg;
    private double valorT;
    private String nomeArquivo = "gerado.txt";
    private String linha, mostra = "";
    private String[] dadosCompra = null;
    private String[] columnNames = {"Loja", "Nº do Cartão", "Cliente", "Validade", "Cod. segurança", "Valor Total", "Qtde. Parcelas", "Data Compra"};

    public Janela() {
        this.tx = new JTextField(50);
        this.botaoAbrir = new JButton("Abrir");
        this.botaoGravar = new JButton("Gravar no Banco");
        this.botaoGerarRetorno = new JButton("Gerar Retorno");

        this.botaoAbrir.addActionListener(this);
        this.botaoGravar.addActionListener(this);
        setLayout(new FlowLayout());

        defaultTableModel = new DefaultTableModel(columnNames, intNumRegistro);

        table = new JTable(defaultTableModel) {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };

        table.setDefaultRenderer(Object.class, new CellRenderer());
        table.getTableHeader().setReorderingAllowed(false);
        table.setPreferredScrollableViewportSize(new Dimension(1100, 300));
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        JScrollPane scroolPane = new JScrollPane(table);
        add(tx);
        add(botaoAbrir);
        add(botaoGravar);
        add(botaoGerarRetorno);
        add(scroolPane);

        this.preencheJTable();

    } // Fim construtor

    public static void main(String[] args) {
        JFrame gui = new Janela();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        gui.setSize(1200, 400);
        gui.setLocation(50, 100);
        gui.setTitle("Dados da Venda");
    }

    public void selecionar() {
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int i = file.showSaveDialog(null);
        if (i == 1) {
            tx.setText("");
        } else {
            File arquivo = file.getSelectedFile();
            tx.setText(arquivo.getPath());
        }

    }

    public void gerar() {
        try {
            LeitorArquivoRemesa.LerArquivo(tx.getText());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Preenche o JTable com os dados do banco de dados
     */
    private void preencheJTable() {
        SolicitacaoCompraDAO solicitacaoCompraDAO = new SolicitacaoCompraDAO();
        List<SolicitacaoCompra> solicitacoesCompras = solicitacaoCompraDAO.getSolicitacaoDeCompra(10);

        for (int i = 0; i < solicitacoesCompras.size(); i++) {
            intRegistro = solicitacoesCompras.get(i).getLojaId();

            numSeg = solicitacoesCompras.get(i).getNumSeguranca();

            valorT = solicitacoesCompras.get(i).getValorTotal();
            qtdePar = solicitacoesCompras.get(i).getQtdParcelas();
            campo[0] = Integer.toString(intRegistro);
            campo[1] = solicitacoesCompras.get(i).getCartaoId();
            campo[2] = solicitacoesCompras.get(i).getNomeCliente();
            campo[3] = solicitacoesCompras.get(i).getDataValidade();
            campo[4] = solicitacoesCompras.get(i).getNumSeguranca();
            campo[5] = Double.toString(valorT);
            campo[6] = Integer.toString(qtdePar);
            campo[7] = solicitacoesCompras.get(i).getDataCompra();
            this.defaultTableModel.insertRow(intNumRegistro, campo);
            intNumRegistro++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoAbrir) {
            selecionar();
        }
        if (e.getSource() == botaoGravar) {
            // Busca novamente os dados do banco de dados
            // atualiza o jtable
            
            
            gerar();
            this.table.repaint();
            this.preencheJTable();
        }

    }
}
