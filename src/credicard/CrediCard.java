/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credicard;

import static credicard.ArquivoRetorno.gravarArquivoTxt;
import fjn.edu.br.Model.Retorno;
import fjn.edu.br.Model.SolicitacaoCompra;
import fjn.edu.br.dao.SolicitacaoCompraDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author fillipquesado
 * @author UelioNobre
 */
public class CrediCard {

    

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
       
        try {
            SolicitacaoCompraDAO registros = new SolicitacaoCompraDAO();
            List<SolicitacaoCompra> compras = registros.getSolicitacaoDeCompra(3);
            System.out.println(compras.size());
            String acumuladorLinhas = "";

            for (SolicitacaoCompra compra : compras) {

                Retorno ret = new Retorno();

                // Calcula o valor da parcela
                double valorParcela = Math.ceil(compra.getValorTotal() / compra.getQtdParcelas());
                System.out.println(valorParcela);

                ret.setCodigoVenda(compra.getCodigoVenda());
                ret.setIdCredor(1); // Nosso ID
                ret.setIdCartao(compra.getCartaoId());
                ret.setDataEnvio(compra.getDataCompra());
                ret.setValorParcela(valorParcela);
                ret.setTotalParcela(compra.getQtdParcelas());

                String[] dataVenda = compra.getDataCompra().split("/");
                int dataVendaDia = Integer.parseInt(dataVenda[0]);
                int dataVendaMes = Integer.parseInt(dataVenda[1]);
                int dataVendaAno = Integer.parseInt(dataVenda[2]);

                // Deixar para o final... na hora de gravar o arquivo de texto 
                // ret.setNumeroParcela(numeroParcela);
                for (int i = 1; i <= compra.getQtdParcelas(); i++) {
                    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = new GregorianCalendar(dataVendaAno, dataVendaMes, dataVendaDia);

                    // Adiciona 30 dias para cada data 
                    c.add(Calendar.DAY_OF_MONTH, 30 * i);
                    String novaDataEnvio = sd.format(c.getTime());
                    ret.setNumeroParcela(i);
                    ret.setDataEnvio(novaDataEnvio.toString());
                    acumuladorLinhas += ret.toString() + "\r\n";

                }

                ArquivoRetorno.gravarArquivoTxt(acumuladorLinhas, "ArquivoRetorno.txt");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
