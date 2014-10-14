/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package credicard;

import fjn.edu.br.Model.SolicitacaoCompra;
import fjn.edu.br.dao.SolicitacaoCompraDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author fillipquesado
 */
public class LeitorArquivoRemesa {

    
    
    public static void LerArquivo(String pathFila) {
        String linha, mostra = "";

        /*
         SolicitacaoCompra arquivo = new SolicitacaoCompra(1, "1834592401295349", "UELIO NOBRE", "11/18", "115", 250, 8, "09/10/2014");
         ArquivoRetorno.gravarArquivoTxt(arquivo.toString(), "Arquivo Teste.txt");
         */
        // tentando criar o arquivo e escrever e salvo o arquivo.
        // GravaLerArquivo.gravarArquivoTxt(arquivo);
        // abrindo o arquivo e removendo para um array, os valores que estão 
        // gravados no arquivo
        // Salva os arquivo em GERADO.TXT
        try {
            //abrindo arquivo para leitura
            FileReader reader = new FileReader(pathFila);

            //leitor do arquivo
            BufferedReader leitor = new BufferedReader(reader);

            while (true) {
                linha = leitor.readLine();
                if (linha == null) {
                    break;
                }

                SolicitacaoCompra solicitacaoCompra = new SolicitacaoCompra();
                String[] dadosDaLinha = linha.split(",");

                solicitacaoCompra.setLojaId(Integer.parseInt(dadosDaLinha[0]));
                solicitacaoCompra.setCartaoId(dadosDaLinha[1]);
                solicitacaoCompra.setCodigoVenda(Integer.parseInt(dadosDaLinha[2]));
                solicitacaoCompra.setNomeCliente(dadosDaLinha[3]);
                solicitacaoCompra.setDataValidade(dadosDaLinha[4]);
                solicitacaoCompra.setNumSeguranca(dadosDaLinha[5]);
                solicitacaoCompra.setValorTotal(Double.parseDouble(dadosDaLinha[6]));
                solicitacaoCompra.setQtdParcelas(Integer.parseInt(dadosDaLinha[7]));
                solicitacaoCompra.setDataCompra(dadosDaLinha[8]);

                SolicitacaoCompraDAO registrarCompra = new SolicitacaoCompraDAO();
                registrarCompra.adicionarSolicitacaoCompra(solicitacaoCompra);

                System.out.println("DADOS DA COMPRA\n" + solicitacaoCompra);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
