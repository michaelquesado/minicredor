
package fjn.edu.br.dao;

import fjn.edu.br.Connection.Conn;
import fjn.edu.br.Model.Retorno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author José Neto
 */
public class RetornoDAO {
    Connection conexao;
    
    public RetornoDAO(){
        this.conexao = new Conn().getConnection();
    }
    
    public void insert(Retorno r){
        
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
            
            stm.execute();
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(RetornoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
//    boolean vendaExiste(int codVenda){
//        return false;
//    }
}
