package fjn.edu.br.Model;

/**
 *
 * @author UelioNobre
 */
public class Retorno {

    private int codigoVenda;
    private int idCredor;
    private String idCartao;
    private double valorParcela;
    private int numeroParcela;
    private int totalParcela;
    private String dataEnvio;

    private SolicitacaoCompra solicitacaoCompra;

    public int getCodigoVenda() {
        return codigoVenda;
    }

    /**
     *
     * @param codigoVenda ID da venda no banco de dados
     */
    public void setCodigoVenda(int codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public int getIdCredor() {
        return idCredor;
    }

    public void setIdCredor(int idCredor) {
        this.idCredor = idCredor;
    }

    public String getIdCartao() {
        return idCartao;
    }

    /**
     *
     * @param idCartao N~umero do cartão de credito
     */
    public void setIdCartao(String idCartao) {
        this.idCartao = idCartao;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    /**
     *
     * @param valorParcela Valor de cada parcela
     */
    public void setValorParcela(double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public int getNumeroParcela() {
        return numeroParcela;
    }

    /**
     *
     * @param numeroParcela Número da parcela
     */
    public void setNumeroParcela(int numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public int getTotalParcela() {
        return totalParcela;
    }

    /**
     *
     * @param totalParcela Quantidade total das parcelas.
     *
     */
    public void setTotalParcela(int totalParcela) {
        this.totalParcela = totalParcela;
    }

    public String getDataEnvio() {
        return dataEnvio;
    }

    /**
     *
     * @param dataEnvio Data em que o arquivo foi enviaro
     */
    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String output() {
        return ""
                + codigoVenda + "\t"
                + idCredor + "\t"
                + idCartao + "\t"
                + valorParcela + "\t"
                + numeroParcela + "\t"
                + totalParcela + "\t"
                + dataEnvio + "\t";
    }

    @Override
    public String toString() {
        return ""
                + codigoVenda + ","
                + idCredor + ","
                + idCartao + ","
                + valorParcela + ","
                + numeroParcela + ","
                + totalParcela + ","
                + dataEnvio;
    }

}
