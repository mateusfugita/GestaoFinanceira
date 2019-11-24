package sample.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.Main;
import java.sql.*;

public class LoginController  {

    @FXML private TextField txtLogin, txtPassword;

    public static Connection getConexao(){
        Connection conexao = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/financeira?useTimezone=true&serverTimezone=UTC", "root", "1234");
            System.out.println("Conectado");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conexao;
    }

    public static String consultar(int id){
        try(Connection c = getConexao()){
            String query = "{CALL Consultar_BD(?)}";
            CallableStatement stmt = c.prepareCall(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getString("Nome_Usuario");
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
        }
        return "";
    }

    public static void inserir(String nome, String login, String senha){
        try(Connection c = getConexao()){
            String query = "{CALL Inserir_BD(?,?,?)}";
            CallableStatement stmt = c.prepareCall(query);
            stmt.setString(1, nome);
            stmt.setString(2, login);
            stmt.setString(3, senha);
            stmt.executeUpdate();
            System.out.println("Inserido com sucesso!");
        }
        catch(SQLException e){
            e.getStackTrace();
        }
    }

    public void SignIn(javafx.event.ActionEvent actionEvent) {
        //consultar(1);
        Main.changeScreen("main");
    }

    public void Register()
    {
        Main.changeScreen("register");
    }
    public void Close(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }
}
