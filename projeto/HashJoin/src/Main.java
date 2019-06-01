import com.sun.applet2.preloader.event.ConfigEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server name: ");
        String servername = scanner.next();

        System.out.print("Enter server port: ");
        String serverport = scanner.next();

        System.out.print("Enter your username: ");
        String username = scanner.next();

        System.out.print("Enter your password: ");
        String password = scanner.next();



        Conectar conexao = new Conectar();
        // conexao.setLogin(username, password);
        // conexao.setServer(servername, serverport);

        String consulta = "select * from master.sys.databases";
        Executar ex = new Executar();
        boolean resp = ex.select(consulta, conexao);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter database name: ");
        String database = scanner.next();

        conexao.setDatabase(database);
        consulta = "select * from INFORMATION_SCHEMA.TABLES";
        resp = ex.select(consulta, conexao);
        */

        Conectar conexao = new Conectar();
        conexao.setLogin("sa", "admin123");
        conexao.setServer("HARIAMY-PC", "1433");
        conexao.setDatabase("lojas");

        HashMap<String, ArrayList<String>> databaseValues = new HashMap<>();


        Executar ex = new Executar();
        ArrayList<String> resp = ex.selectTableNames(conexao, "lojas");

        for (int i = 0; i < resp.size(); i ++){
            databaseValues.put(resp.get(i), ex.selectTableAtributos(conexao, resp.get(i)));
        }

        for (int i = 0; i < resp.size(); i ++){
            System.out.print(resp.get(i) + " possui os atributos: ");
            for (int j = 0; j < databaseValues.get(resp.get(i)).size(); j++) {
                System.out.print(databaseValues.get(resp.get(i)).get(j) + " :: ");
            }
            System.out.println("\n");
        }

    }
}
