package modelo.conexao;

import modelo.conexao.Conectar;

import java.sql.*;
import java.util.ArrayList;

public class Executar {
    private boolean executou;

    public boolean getExecutou(){
        return executou;
    }

    public boolean select(String sel, Conectar conexao){
        executou = false;
        try {
            conexao.abrirConexao();
            if (conexao.conectou()) {
                Statement ps = conexao.getC().createStatement();
                ResultSet rs = ps.executeQuery(sel);
                ResultSetMetaData metadata = rs.getMetaData();
                int columnCount = metadata.getColumnCount();


                ArrayList<String> colunas = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    colunas.add(metadata.getColumnName(i));

                }
                System.out.println(metadata.getTableName(columnCount));

                while(rs.next()){
                    System.out.println("modelo.Database name: " + rs.getString(1));
                }

                ps.close();
                conexao.fecharConexao();
                executou = true;
            }
            if (conexao.conectou()) conexao.fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return executou;
    }

    public ArrayList<String> getNomeBanco(Conectar conexao){
        executou = false;
        ArrayList<String> resposta = new ArrayList<>();
        String sel = "select name from master.sys.databases";

        try {
            conexao.abrirConexao();
            if (conexao.conectou()) {
                Statement ps = conexao.getC().createStatement();
                ResultSet rs = ps.executeQuery(sel);
                ResultSetMetaData metadata = rs.getMetaData();
                int columnCount = metadata.getColumnCount();


                ArrayList<String> colunas = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    colunas.add(metadata.getColumnName(i));

                }
                System.out.println(metadata.getTableName(columnCount));

                while(rs.next()){
                    resposta.add( rs.getString(1) );
                }

                ps.close();
                conexao.fecharConexao();
                executou = true;
            }
            if (conexao.conectou()) conexao.fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return resposta;
    }

    public ArrayList<String> getTabelasBanco(Conectar conexao) {
        executou = false;
        ArrayList<String> tabelas = new ArrayList<>();
        try {
            conexao.abrirConexao();
            if (conexao.conectou()) {
                Statement ps = conexao.getC().createStatement();

                String sel = "select * from INFORMATION_SCHEMA.TABLES";

                ResultSet rs = ps.executeQuery(sel);

                while(rs.next()){
                    if (rs.getString("TABLE_TYPE").equals("BASE TABLE")) {
                        tabelas.add(rs.getString("TABLE_NAME"));
                    }
                }

                ps.close();
                conexao.fecharConexao();
                executou = true;
            }
            if (conexao.conectou()) conexao.fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return tabelas;
    }

    public ArrayList<String> selectTableNames(Conectar conexao, String database){
        executou = false;
        ArrayList<String> tabelas = new ArrayList<>();
        try {
            conexao.abrirConexao();
            if (conexao.conectou()) {
                Statement ps = conexao.getC().createStatement();

                String sel = "select * from INFORMATION_SCHEMA.TABLES";

                ResultSet rs = ps.executeQuery(sel);

                while(rs.next()){
                    if (rs.getString("TABLE_TYPE").equals("BASE TABLE")) {
                        tabelas.add(rs.getString("TABLE_NAME"));
                    }
                }

                ps.close();
                conexao.fecharConexao();
                executou = true;
            }
            if (conexao.conectou()) conexao.fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return tabelas;
    }

    public ArrayList<String> getTabelaAtributos(Conectar conexao, String tabela){
        executou = false;
        ArrayList<String> tabelas = new ArrayList<>();
        try {
            conexao.abrirConexao();
            if (conexao.conectou()) {

                String sel = "select * from INFORMATION_SCHEMA.columns WHERE TABLE_NAME = ? ";

                PreparedStatement ps = conexao.getC().prepareStatement(sel);
                ps.setString(1, tabela);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    //tabelas.add(rs.getString("COLUMN_NAME") + "," + rs.getString("DATA_TYPE"));
                    tabelas.add(rs.getString("COLUMN_NAME"));
                }


                ps.close();
                conexao.fecharConexao();
                executou = true;
            }
            if (conexao.conectou()) conexao.fecharConexao();

        } catch (Exception e) {
            System.out.println(e);
        }
        return tabelas;
    }

}
