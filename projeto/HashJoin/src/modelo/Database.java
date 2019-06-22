package modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private HashMap<String, ArrayList<String>> tabelas;
    private String nome;

    public Database(String nome, HashMap<String, ArrayList<String>> tabelas) {
        this.nome = nome;
        this.tabelas = tabelas;
    }

    public HashMap<String, ArrayList<String>> getTabelas() {
        return tabelas;
    }


}
