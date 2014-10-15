/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fjn.edu.br.dao;

import fjn.edu.br.Connection.Conn;
import fjn.edu.br.Model.Loja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author UelioNobre
 */
public class LojaDAO {

    private Connection conn;
    public ArrayList<Loja> lojas;

    public LojaDAO() {
        this.conn = new Conn().getConnection();
    }

    public List<Loja> getAllLojas() {
        String sql = "SELECT * FROM lojas ORDER BY nome_loja ";
        Loja loja;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet dados = stmt.executeQuery();

            List<Loja> listaDeLojas = new ArrayList<>();

            while (dados.next()) {
                loja = new Loja();

                loja.setId(dados.getInt("id"));
                loja.setNomeLoja(dados.getString("nome_loja"));
                
                listaDeLojas.add(loja);
            }

            dados.close();
            stmt.close();

            return listaDeLojas;

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }
}
