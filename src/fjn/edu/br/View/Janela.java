package fjn.edu.br.View;

import credicard.ArquivoRetorno;
import credicard.LeitorArquivoRemesa;
import fjn.edu.br.Model.SolicitacaoCompra;
import fjn.edu.br.dao.LojaDAO;
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
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

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
    private String campo[] = new String[9];
    private int intTotalRegistro, intNumRegistro, intRegistro, qtdePar;
    private String numSeg;
    private double valorT;
    private String nomeArquivo = "gerado.txt";
    private String linha, mostra = "";
    private String[] dadosCompra = null;
    private String[] columnNames = {"Loja", "Código da Venda", "Nº do Cartão", "Cliente", "Validade", "Cod. segurança", "Valor Total", "Qtde. Parcelas", "Data Compra"};
    private JComboBox jComboboxlojas;

    public Janela() {

        this.tx = new JTextField(50);
        this.botaoAbrir = new JButton("Abrir");
        this.botaoGravar = new JButton("Gravar no Banco");
        this.botaoGerarRetorno = new JButton("Gerar Retorno");

        this.botaoAbrir.addActionListener(this);
        this.botaoGravar.addActionListener(this);
        this.botaoGerarRetorno.addActionListener(this);
        setLayout(new FlowLayout());

        defaultTableModel = new DefaultTableModel(columnNames, intNumRegistro);

        this.preencherJcomboBox();

        add(jComboboxlojas);
        add(tx);
        add(botaoAbrir);
        add(botaoGravar);
        add(botaoGerarRetorno);

        this.criaJTable();
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
            tx.setText(null);
        } else {
            File arquivo = file.getSelectedFile();
            tx.setText(arquivo.getPath());
        }

    }

    public void gerar() {
        try {
            LeitorArquivoRemesa.LerArquivo(tx.getText());
            tx.setText(null);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    private void preencherJcomboBox() {
        LojaDAO ldao = new LojaDAO();
        List<String> lojas = ldao.getAllLojas();
        jComboboxlojas = new JComboBox(new Vector<>(lojas));
    }

    /**
     * Preenche o JTable com os dados do banco de dados
     */
    private void preencheJTable() {

        SolicitacaoCompraDAO solicitacaoCompraDAO = new SolicitacaoCompraDAO();
        List<SolicitacaoCompra> solicitacoesCompras = solicitacaoCompraDAO.
                getSolicitacaoDeCompra("SELECT * FROM solicitacao_compras "
                        + "ORDER BY id DESC LIMIT 10");

        // Zera os indices que são usados no defaultaTableModel.
        intNumRegistro = 0;

        for (int i = 0; i < solicitacoesCompras.size(); i++) {
            intRegistro = solicitacoesCompras.get(i).getLojaId();

            numSeg = solicitacoesCompras.get(i).getNumSeguranca();

            valorT = solicitacoesCompras.get(i).getValorTotal();
            qtdePar = solicitacoesCompras.get(i).getQtdParcelas();
            campo[0] = Integer.toString(intRegistro);
            campo[1] = Integer.toString(solicitacoesCompras.get(i).getCodigoVenda());
            campo[2] = solicitacoesCompras.get(i).getCartaoId();
            campo[3] = solicitacoesCompras.get(i).getNomeCliente();
            campo[4] = solicitacoesCompras.get(i).getDataValidade();
            campo[5] = solicitacoesCompras.get(i).getNumSeguranca();
            campo[6] = Double.toString(valorT);
            campo[7] = Integer.toString(qtdePar);
            campo[8] = solicitacoesCompras.get(i).getDataCompra();
            this.defaultTableModel.insertRow(intNumRegistro, campo);
            intNumRegistro++;
        }

    }

    // Remove os itens do defaultTableModel
    // fazendo com que os registros não se repitam.
    public void limpaJTable() {
        while (this.defaultTableModel.getRowCount() > 0) {
            this.defaultTableModel.removeRow(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoAbrir) {
            selecionar();
        }
        if (e.getSource() == botaoGravar) {

            if (tx.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Selecione um arquivo para continuar!");
                return;
            }

            // Busca novamente os dados do banco de dados
            // atualiza o jtable
            gerar();
            this.limpaJTable();
            this.table.repaint();
            this.preencheJTable();

            // Apaga o texto do arquivo de texto
            this.tx.setText("");
        }
        if (e.getSource() == botaoGerarRetorno) {
            System.out.println("Arquivo retorno sendo gerado aguarde");
            String lojaComboSelecionada = (String) jComboboxlojas.getSelectedItem();

            if (!lojaComboSelecionada.equals("Selecione uma Loja")) {
                SolicitacaoCompraDAO solicitacaoCompraDAO = new SolicitacaoCompraDAO();
                List<SolicitacaoCompra> solicitacoesCompras = solicitacaoCompraDAO.
                        getSolicitacaoDeCompra("select sc.* from solicitacao_compras sc"
                                + " inner join lojas l on l.id = sc.loja_id"
                                + " where l.nome_loja = '" + lojaComboSelecionada + "'");

                ArquivoRetorno.gerarArquivoRetorno(solicitacoesCompras);
            }else{
                JOptionPane.showMessageDialog(null, "Selecione uma loja para gerar \n o arquivo retorno.");
            }

        }

    }

    public SolicitacaoCompra getCompraArquivoRetorno() {

        SolicitacaoCompra listaCompra = new SolicitacaoCompra();
        listaCompra.setLojaId(Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0)));
        listaCompra.setCodigoVenda(Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 1)));
        listaCompra.setCartaoId((String) table.getValueAt(table.getSelectedRow(), 2));
        listaCompra.setNomeCliente((String) table.getValueAt(table.getSelectedRow(), 3));
        listaCompra.setDataValidade((String) table.getValueAt(table.getSelectedRow(), 4));
        listaCompra.setNumSeguranca((String) table.getValueAt(table.getSelectedRow(), 5));
        listaCompra.setValorTotal(Double.parseDouble((String) table.getValueAt(table.getSelectedRow(), 6)));
        listaCompra.setQtdParcelas(Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 7)));
        listaCompra.setDataCompra((String) table.getValueAt(table.getSelectedRow(), 8));
        return listaCompra;
    }

    /**
     * Cria a tabela
     */
    public void criaJTable() {
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
        add(scroolPane);
    }
}
