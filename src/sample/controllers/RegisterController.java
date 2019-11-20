package sample.controllers;

import DAO.UsuarioDAO;
import VO.UsuarioVO;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Main;

import javax.swing.*;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField txt1, txt2;
    @FXML
    private PasswordField txt3;

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
            usuario.setNome(txt1.getText());
            usuario.setLogin(txt2.getText());
            usuario.setSenha(txt3.getText());
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.inserir(usuario);
            JOptionPane.showMessageDialog(null,"Successful Registration");
            Main.changeScreen("login");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
