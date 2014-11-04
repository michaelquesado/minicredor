package fjn.edu.br.dao;

import fjn.edu.br.Connection.Conn;
import fjn.edu.br.Model.Retorno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Neto
 */
public class RetornoDAO {

    Connection conexao;

    public RetornoDAO() {
        this.conexao = new Conn().getConnection();
    }

    public void insert(Retorno r) {

//        if(vendaExiste(r)){
//            return;
//        }
        try {
            String sql = "INSERT INTO Retornos (codVenda, idCredor, "
                    + "idCartao, valorParcela, numeroParcela, totalParcela, "
                    + "dataEnvio) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stm = conexao.prepareStatement(sql);

            stm.setInt(1, r.getCodigoVenda());
            stm.setInt(2, r.getIdCredor());
            stm.setString(3, r.getIdCartao());
            stm.setDouble(4, r.getValorParcela());
            stm.setInt(5, r.getNumeroParcela());
            stm.setInt(6, r.getTotalParcela());
            stm.setString(7, r.getDataEnvio());

            vendaExiste(r);
            stm.execute();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(RetornoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Verifica se a solicitação já existe já existe a venda cadastrada
    public boolean vendaExiste(Retorno r) {
        String sql = "SELECT COUNT(*) AS total FROM retornos WHERE codvenda = ? and idcartao = ? ";
        try {
            PreparedStatement stm = conexao.prepareStatement(sql);
            stm.setInt(1, r.getCodigoVenda());
            stm.setString(2, r.getIdCartao());
            ResultSet rs = stm.executeQuery();

            // Passa para a primeira linha de resultado.
            rs.next();

            // Armazena a quantidade de registros repetidos
            int totalRegistros = rs.getInt("total");
            stm.close();
            return (totalRegistros > 0);

        } catch (SQLException ex) {
            System.out.println("ERRO");
            System.out.println("RetornoDAO.java");
            System.out.println("Método::VendaExiste");
            System.out.println(ex.getStackTrace());
            Logger.getLogger(RetornoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}
