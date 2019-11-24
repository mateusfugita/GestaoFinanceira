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
    private TextField txtName, txtLogin, txtAge;

    @FXML
    private PasswordField txtPassword;

    public void ReturnLogin(javafx.event.ActionEvent actionEvent) {
        Main.changeScreen("login");
    }
    public void Close(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }
    @FXML
    public void Save(javafx.event.ActionEvent actionEvent){
        try {
            //inserir(txtName.getText(), txtLogin.getText(), txtPassword.getText());
            UsuarioVO usuario = new UsuarioVO();
            usuario.setNome(txtName.getText());
            usuario.setLogin(txtLogin.getText());
            usuario.setSenha(txtPassword.getText());
            usuario.setIdade(Integer.parseInt(txtAge.getText()));
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.inserir(usuario);
            JOptionPane.showMessageDialog(null, "Successful Registration");
            Main.changeScreen("login");
        }
        catch (Exception e){
            System.out.print(e.getMessage());
        }
    }


}
