/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credicard;

import fjn.edu.br.Model.Retorno;
import fjn.edu.br.Model.SolicitacaoCompra;
import fjn.edu.br.dao.RetornoDAO;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author UelioNobre
 */
public class ArquivoRetorno {

    private static final String NOME_ARQUIVO = "gerado.txt";
    private String nomeArquivoRetorno;
    private ArrayList<SolicitacaoCompra> solicitacaoCompras;

    public ArquivoRetorno(ArrayList<SolicitacaoCompra> solicitacaoCompras) {
        this.solicitacaoCompras = solicitacaoCompras;
    }

    public static void gravarArquivoTxt(String string, String nomeArquivo) {
        try {
            Formatter saida = new Formatter(nomeArquivo);
            saida.format(string);
            saida.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao gerar aquivo " + e.getMessage());
        }
    }

    public static void gerarArquivoRetorno(List<SolicitacaoCompra> solicitacaoCompra) {
        String aux = "";
        for (SolicitacaoCompra solicitacao : solicitacaoCompra) {
            Retorno ret = new Retorno();

            // Calcula o valor da parcela
            double valorParcela = solicitacao.getValorTotal() / solicitacao.getQtdParcelas();

            ret.setCodigoVenda(solicitacao.getCodigoVenda());
            ret.setIdCredor(1); // Nosso ID
            ret.setIdCartao(solicitacao.getCartaoId());
            ret.setDataEnvio(solicitacao.getDataCompra());
            ret.setValorParcela(valorParcela);
            ret.setTotalParcela(solicitacao.getQtdParcelas());

            String[] dataVenda = solicitacao.getDataCompra().split("/");

            int dataVendaDia = Integer.parseInt(dataVenda[0]);
            int dataVendaMes = Integer.parseInt(dataVenda[1]);
            int dataVendaAno = Integer.parseInt(dataVenda[2]);
            
            RetornoDAO dao = new RetornoDAO();
        // Deixar para o final... na hora de gravar o arquivo de texto 
            // ret.setNumeroParcela(numeroParcela);
            for (int i = 1; i <= solicitacao.getQtdParcelas(); i++) {
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = new GregorianCalendar(dataVendaAno, dataVendaMes, dataVendaDia);

                // Adiciona 30 dias para cada data 
                c.add(Calendar.DAY_OF_MONTH, 30 * i);
                String novaDataEnvio = sd.format(c.getTime());
                ret.setNumeroParcela(i);
                ret.setDataEnvio(novaDataEnvio.toString());

                System.out.println(ret.output());
                aux += ret.toString() + "\r\n";
                dao.insert(ret);
            }

        }
        gravarArquivoTxt(aux, "Arquivo teste da boba serena.txt");

    }

}
