package sample.controllers;
import DAO.ConexaoBD;
import DAO.UsuarioDAO;
import VO.UsuarioVO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.Main;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class LoginController  {

    @FXML private TextField txtLogin, txtPassword;

    public static UsuarioVO consultar(String login, String senha){
        try(Connection c = ConexaoBD.getInstance().getConexao()){
            String query = "{CALL sp_validar_usuario(?,?)}";
            CallableStatement stmt = c.prepareCall(query);
            stmt.setString("conta", login);
            stmt.setString("pass",senha);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs == null)
                return null;
            else {
                UsuarioDAO u = new UsuarioDAO();
                UsuarioVO.getInstance().getId();
                return u.criaUsuario(rs);
            }
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    private void executaPython(){
        try{
            int id = UsuarioVO.getInstance().getId();
            System.out.println("ID: " + id);
            PrintStream escrever = new PrintStream("Id_Usuario.txt");
            escrever.println(id);

            String path1 = "Regressao\\Regressao.py";
            String command1 = " cmd.exe /c start /min python " + path1;
            Process p1 = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + command1);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void SignIn(javafx.event.ActionEvent actionEvent) {
       if(consultar(txtLogin.getText(), txtPassword.getText()) !=null) {
            Main.changeScreen("main");
            executaPython();
        }
        else
            JOptionPane.showMessageDialog(null,"Usuário Inválido");
    }

    public void Register()
    {
        txtLogin.clear();
        txtPassword.clear();
        Main.changeScreen("register");
    }
    public void Close(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }
}
