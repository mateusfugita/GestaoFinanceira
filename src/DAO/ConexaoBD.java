package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static ConexaoBD uniqueInstance;

    private ConexaoBD(){
    }

    public static synchronized ConexaoBD getInstance(){
        if(uniqueInstance == null)
            uniqueInstance = new ConexaoBD();
        return uniqueInstance;
    }

    public static Connection getConexao(){
        Connection conexao = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/Financeira1?useTimezone=true&serverTimezone=UTC", "root", "1234");
            //conexao = DriverManager.getConnection("jdbc:mysql://localhost/financeira", "root");
            System.out.println("Conectado");
        }
        catch(SQLException e){
            System.out.println("Erro ao conectar");
        }
        return conexao;
    }
}
