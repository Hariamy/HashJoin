package modelo.conexao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conectar {
    private Connection c;
    private boolean conectado;
    private String username = "sa";         // sa
    private String password = "admin123";   // admin123 OU admin123H no ubuntu
    private String database = "";
    private String porta = "1433";          // 1433
    private String server = "HARIAMY-PC";   // HARIAMY-PC

    public void setLogin(String username, String password) {
        this.username = username;
        this.password = password;
        this.database = "";
    }

    public void setServer(String server, String porta) {
        this.server = server;
        this.porta = porta;
        this.database = "";
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Connection getC() { return c; }

    public boolean conectou(){ return conectado; }

    public void abrirConexao() {
        conectado = false;
        try {
            String url = "jdbc:sqlserver://"+server+":"+porta;
            url = database.equals("") ? url : url + ";databaseName=" + database;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url, username, password);
            conectado = true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();

        }
    }

    public void fecharConexao() {
        try {
            c.close();
            conectado = false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

