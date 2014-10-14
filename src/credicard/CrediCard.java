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

    private static final String NOME_ARQUIVO = "gerado.txt";

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        String linha, mostra = "";

        //SolicitacaoCompra arquivo = new SolicitacaoCompra(1, "1834592401295349", "UELIO NOBRE", "11/18", "115", 250, 8, "09/10/2014");
        //ArquivoRetorno.gravarArquivoTxt(arquivo.toString(), "Arquivo Teste.txt");
        // tentando criar o arquivo e escrever e salvo o arquivo.
        // GravaLerArquivo.gravarArquivoTxt(arquivo);
        // abrindo o arquivo e removendo para um array, os valores que estão 
        // gravados no arquivo
        // Salva os arquivo em GERADO.TXT
//        try {
//            //abrindo arquivo para leitura
//            FileReader reader = new FileReader(NOME_ARQUIVO);
//
//            //leitor do arquivo
//            BufferedReader leitor = new BufferedReader(reader);
//
//            while (true) {
//                linha = leitor.readLine();
//                if (linha == null) {
//                    break;
//                }
//
//                SolicitacaoCompra solicitacaoCompra = new SolicitacaoCompra();
//                String[] dadosDaLinha = linha.split(",");
//
//                solicitacaoCompra.setLojaId(Integer.parseInt(dadosDaLinha[0]));
//                solicitacaoCompra.setCartaoId(dadosDaLinha[1]);
//                solicitacaoCompra.setNomeCliente(dadosDaLinha[2]);
//                solicitacaoCompra.setDataValidade(dadosDaLinha[3]);
//                solicitacaoCompra.setNumSeguranca(dadosDaLinha[4]);
//                solicitacaoCompra.setValorTotal(Double.parseDouble(dadosDaLinha[5]));
//                solicitacaoCompra.setQtdParcelas(Integer.parseInt(dadosDaLinha[6]));
//                solicitacaoCompra.setDataCompra(dadosDaLinha[7]);
//
//                SolicitacaoCompraDAO registrarCompra = new SolicitacaoCompraDAO();
//                registrarCompra.adicionarSolicitacaoCompra(solicitacaoCompra);
//
//                System.out.println("DADOS DA COMPRA\n" + solicitacaoCompra);
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        try {
            SolicitacaoCompraDAO registros = new SolicitacaoCompraDAO();
            List<SolicitacaoCompra> compras = registros.getSolicitacaoDeCompra(5);
            System.out.println(compras.size());
            String acumuladorLinhas = "";
            
            for (SolicitacaoCompra compra : compras) {

                Retorno ret = new Retorno();

                // Calcula o valor da parcela
                double valorParcela = Math.ceil(compra.getValorTotal() / compra.getQtdParcelas());
                System.out.println(valorParcela);

                ret.setCodigoVenda(compra.getId());
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
