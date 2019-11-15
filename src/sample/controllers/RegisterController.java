package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Main;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController {


    @FXML
    private TextField txtName, txtLogin, txtPassword;

    public static Connection getConexao(){
        Connection conexao = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/financeira?useTimezone=true&serverTimezone=UTC", "root", "1234");
            //conexao = DriverManager.getConnection("jdbc:mysql://localhost/Financeira", "root");
            System.out.println("Conectado");
        }
        catch(SQLException e){
            System.out.println("Erro ao conectar");
        }
        return conexao;
    }

    public static void inserir(String nome, String login, String senha){
        try(Connection c = getConexao()){
            String query = "{CALL Inserir_BD(?,?,?)}";
            CallableStatement stmt = c.prepareCall(query);
            stmt.setString("NOME", nome);
            stmt.setString("Conta_Usuario", login);
            stmt.setString("Senha", senha);
            stmt.executeUpdate();
            System.out.println("Inserido com sucesso!");
        }
        catch(SQLException e){
            e.getStackTrace();
        }
    }

    public void ReturnLogin(javafx.event.ActionEvent actionEvent) {
        Main.changeScreen("login");
    }
    public void Close(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }
    @FXML
    public void Save(javafx.event.ActionEvent actionEvent){
        inserir("SILVIO","SILVIO","1234");
        JOptionPane.showMessageDialog(null,"Successful Registration");
        Main.changeScreen("login");
    }


}
