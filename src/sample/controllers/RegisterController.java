package sample.controllers;

import DAO.UsuarioDAO;
import VO.UsuarioVO;
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
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/financeira?useTimezone=true&serverTimezone=UTC", "root", "");
            //conexao = DriverManager.getConnection("jdbc:mysql://localhost/financeira", "root");
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
        try{
            UsuarioVO usuario = new UsuarioVO();
            usuario.setNome("Silvio");
            usuario.setLogin("Silvio");
            usuario.setSenha("1234");
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.inserir(usuario);
            //inserir("SILVIO","SILVIO","1234");
            //inserir(txtName.getText(), txtLogin.getText(), txtPassword.getText());
            JOptionPane.showMessageDialog(null,"Successful Registration");
            Main.changeScreen("login");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        /*
        UsuarioVO usuario = new UsuarioVO();
        usuario.setNome("Silvio");
        usuario.setLogin("Silvio");
        usuario.setSenha("1234");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.inserir(usuario);
        inserir("SILVIO","SILVIO","1234");
        //inserir(txtName.getText(), txtLogin.getText(), txtPassword.getText());
        JOptionPane.showMessageDialog(null,"Successful Registration");
        Main.changeScreen("login");
         */
    }

}
