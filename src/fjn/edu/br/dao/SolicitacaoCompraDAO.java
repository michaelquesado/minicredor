package fjn.edu.br.dao;

import fjn.edu.br.Connection.Conn;
import fjn.edu.br.Model.SolicitacaoCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fillipquesado
 */
public class SolicitacaoCompraDAO {

    private final Connection conn;
    private PreparedStatement stmt;

    public SolicitacaoCompraDAO() {
        this.conn = new Conn().getConnection();
    }

    public void adicionarSolicitacaoCompra(SolicitacaoCompra compra) {
        String sql = "INSERT INTO solicitacao_compras (loja_id, cartao_id "
                + ", nome_cliente, data_validade, num_seguranca, valor_total"
                + ", qtd_parcelas, data_compra, codigo_venda) VALUES (?,?,?,?,?,?,?,?,?)";

        /**
         * Antes de cadastrar, verificamos se a compra já existe. Neste caso se
         * der verdadeiro, então retorne e não deixe cair no try
         */
        if (solicitacaoExiste(compra)) {
            return;
        }

        try {

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, compra.getLojaId());
            stmt.setString(2, compra.getCartaoId());
            stmt.setString(3, compra.getNomeCliente());
            stmt.setString(4, compra.getDataValidade());
            stmt.setInt(5, Integer.parseInt(compra.getNumSeguranca()));
            stmt.setDouble(6, compra.getValorTotal());
            stmt.setInt(7, compra.getQtdParcelas());
            stmt.setString(8, compra.getDataCompra());
            stmt.setInt(9, compra.getCodigoVenda());

            stmt.execute();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //throw new RuntimeException(e);
        }

    }

    //retorna uma lista de solicitacao de compras
    public List<SolicitacaoCompra> getSolicitacaoDeCompra(int limit) {
        String sql = "SELECT * FROM solicitacao_compras "
                        + "ORDER BY id DESC LIMIT "+limit;
        return this.setList(sql);
    }

    //retorna uma lista de solicitacao de compras por loja
    public List<SolicitacaoCompra> getSolicitacaoDeCompraPorLoja(String loja) {
        String sql = "select sc.* from solicitacao_compras sc"
                + " inner join lojas l on l.id = sc.loja_id"
                + " where l.nome_loja = '" + loja + "'";
        return this.setList(sql);
    }

    // Verifica se a solicitação já existe já existe a venda cadastrada
    public boolean solicitacaoExiste(SolicitacaoCompra s) {
        System.out.println("############## " + s.getCodigoVenda());
        String sql = "SELECT COUNT(*) as total FROM solicitacao_compras WHERE codigo_venda = ? and loja_id = ? ";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, s.getCodigoVenda());
            stmt.setInt(2, s.getLojaId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int totalRegistros = rs.getInt("total");
            stmt.close();
            return (totalRegistros > 0);

        } catch (SQLException ex) {
            Logger.getLogger(SolicitacaoCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }
    
    //Agora eu posso reutilizar esse bloco em metodos que retornam lista de compras
    //sem precisar deixar a cargo de quem chama passando a sql
    private List<SolicitacaoCompra> setList(String sql) {
        SolicitacaoCompra solicitacaoCompra;
        try {

            List<SolicitacaoCompra> compras = new ArrayList<>();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                solicitacaoCompra = new SolicitacaoCompra();

                solicitacaoCompra.setId(rs.getInt("id"));
                solicitacaoCompra.setLojaId(rs.getInt("loja_id"));
                solicitacaoCompra.setCartaoId(rs.getString("cartao_id"));
                solicitacaoCompra.setCodigoVenda(rs.getInt("codigo_venda"));
                solicitacaoCompra.setNomeCliente(rs.getString("nome_cliente"));
                solicitacaoCompra.setDataValidade(rs.getString("data_validade"));
                solicitacaoCompra.setNumSeguranca(rs.getString("num_seguranca"));
                solicitacaoCompra.setValorTotal(rs.getDouble("valor_total"));
                solicitacaoCompra.setQtdParcelas(rs.getInt("qtd_parcelas"));
                solicitacaoCompra.setDataCompra(rs.getString("data_compra"));
                compras.add(solicitacaoCompra);
            }
            rs.close();
            stmt.close();
            return compras;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao tentar retornar ultimas compras \n " + e.getMessage());
        }
    }

}
