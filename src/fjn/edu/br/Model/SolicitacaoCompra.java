/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fjn.edu.br.Model;

/**
 *
 * @author fillipquesado
 * @author Uelio Nobre
 */
public class SolicitacaoCompra {

    private int id;
    private int lojaId;
    private String cartaoId;
    private int codigoVenda;
    private String nomeCliente;
    private String dataValidade;
    private String numSeguranca;
    private double valorTotal;
    private int qtdParcelas;
    private String dataCompra;

    // Construtor simples
    public SolicitacaoCompra() {
    }

    /**
     *
     * @param lojaId ID da loja
     * @param cartaoId ID do cartão de crédito
     * @param codigoVenda
     * @param nomeCliente Nome do cliente
     * @param dataValidade Data de validade do cartao
     * @param numSeguranca Numero de seguranca do cartao
     * @param valorTotal Valor total da compra
     * @param qtdParcelas Quantidade de parcelas
     * @param dataCompra Data da compra
     */
    public SolicitacaoCompra(int lojaId, String cartaoId, int codigoVenda, String nomeCliente,
            String dataValidade, String numSeguranca, double valorTotal,
            int qtdParcelas, String dataCompra) {
        this.lojaId = lojaId ;
        this.cartaoId = cartaoId;
        this.codigoVenda = codigoVenda;
        this.nomeCliente = nomeCliente;
        this.dataValidade = dataValidade;
        this.numSeguranca = numSeguranca;
        this.valorTotal = valorTotal;
        this.qtdParcelas = qtdParcelas;
        this.dataCompra = dataCompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(int codigoVenda) {
        this.codigoVenda = codigoVenda;
    }
    
    public int getLojaId() {
        return lojaId;
    }

    public void setLojaId(int LojaId) {
        this.lojaId = LojaId;
    }

    public String getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(String CartaoId) {
        this.cartaoId = CartaoId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String NomeCliente) {
        this.nomeCliente = NomeCliente;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String DataValidade) {
        this.dataValidade = DataValidade;
    }

    public String getNumSeguranca() {
        return numSeguranca;
    }

    public void setNumSeguranca(String NumSeguranca) {
        this.numSeguranca = NumSeguranca;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double ValorTotal) {
        // Não pode haver pagamentos com valores inferiores a zero
        if (ValorTotal < 0) {
            this.valorTotal = 0;
        }
        this.valorTotal = ValorTotal;
    }

    public int getQtdParcelas() {
        if (this.qtdParcelas <= 0) {
            this.qtdParcelas = 1;
        }
        return qtdParcelas;
    }

    public void setQtdParcelas(int QtdParcelas) {
        this.qtdParcelas = QtdParcelas;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String DataCompra) {
        this.dataCompra = DataCompra;
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.getLojaId() + "," + this.getCartaoId() + "," + this.getNomeCliente()
                + "," + this.getDataValidade() + "," + this.getNumSeguranca() + ","
                + this.getValorTotal() + "," + this.getQtdParcelas()
                + "," + this.getDataCompra();
    }

}
