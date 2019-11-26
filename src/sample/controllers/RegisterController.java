package sample.controllers;

import DAO.ConexaoBD;
import DAO.UsuarioDAO;
import VO.UsuarioVO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import sample.Main;
import javax.swing.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RegisterController {
    @FXML
    private TextField txtName, txtLogin,txtAge;

    @FXML
    private PasswordField txtPassword;

    public static void inserir(String nome, String login, String senha){
        try(Connection c = ConexaoBD.getInstance().getConexao()){
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

    public void Limpar(){
        txtName.clear();
        txtPassword.clear();
        txtLogin.clear();
        txtAge.clear();
    }

    public void ReturnLogin(javafx.event.ActionEvent actionEvent) {
        Main.changeScreen("login");
        Limpar();
    }
    public void Close(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }
    @FXML
    public void Save(javafx.event.ActionEvent actionEvent){
        try {
            UsuarioVO usuario = UsuarioVO.getInstance();
            usuario.setNome(txtName.getText());
            usuario.setLogin(txtLogin.getText());
            usuario.setSenha(txtPassword.getText());
            usuario.setIdade(Integer.parseInt(txtAge.getText()));
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.inserir(usuario);
            Limpar();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
            Main.changeScreen("login");
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
