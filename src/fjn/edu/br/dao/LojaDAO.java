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

/**
 *
 * @author UelioNobre
 */
public class LojaDAO {

    private Connection conn;
    public ArrayList<Loja> lojas;

    public Loja getLojaById(int idLoja) {
        String sql = "SELECT * FROM lojas WHERE id=?";
        this.conn = new Conn().getConnection();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idLoja);

            ResultSet dados = stmt.executeQuery();
            Loja loja = new Loja();

            while (dados.next()) {
                loja.setId(dados.getInt("id"));
                loja.setId(dados.getInt("loja"));
            }

            return loja;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
