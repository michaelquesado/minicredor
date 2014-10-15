/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fjn.edu.br.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fillipquesado
 */
public class Conn {

    private static final String url = "jdbc:postgresql://localhost/credicard";
    private final String USER = "postgres";
    private final String PASS = "h4ck3v1m2";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
 
}
