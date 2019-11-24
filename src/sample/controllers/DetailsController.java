package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Main;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    @FXML
    private Label lblFirstSpend,lblSecondSpend,lblThirdSpend;

    public static Connection getConnection(){
        Connection conexao = null;
        try{
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/financeira?useTimezone=true&serverTimezone=UTC", "root", "1234");
        }
        catch(SQLException e){
            System.out.println("Erro ao conectar");
        }
        return conexao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            ArrayList<String> result = new ArrayList<String>();
            String query = "SELECT E.Nome_Empresa 'EMPRESA', SUM(G.Valor) 'GASTO TOTAL',C.DescCategoria 'CATEGORIA' FROM tbl_gasto G\n" +
                    "INNER JOIN tbl_empresa E\n" +
                    "ON G.Id_Empresa = E.Id_Empresa\n" +
                    "INNER JOIN tbl_catempresa C\n" +
                    "ON C.Id_Categoria = E.Id_Categoria\n" +
                    "WHERE G.Id_Usuario = 4\n" +
                    "GROUP BY E.Nome_Empresa\n" +
                    "ORDER BY SUM(G.VALOR) DESC\n" +
                    "LIMIT 3;";

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                result.add(rs.getString("EMPRESA") + " - " + rs.getString("CATEGORIA") + " - " + rs.getDouble("GASTO TOTAL"));
            }

            lblFirstSpend.setText("1º\n" + "EMPRESA: " + result.get(0).split(" - ")[0] + "\n" + "CATEGORIA: " + result.get(0).split(" - ")[1] + "\n" + "VALOR: R$" + result.get(0).split(" - ")[2]);
            lblSecondSpend.setText("2º\n" +"EMPRESA: " + result.get(1).split(" - ")[0] + "\n" + "CATEGORIA: " + result.get(1).split(" - ")[1] + "\n" + "VALOR: R$" + result.get(1).split(" - ")[2]);
            lblThirdSpend.setText("3º\n" +"EMPRESA: " + result.get(2).split(" - ")[0] + "\n" + "CATEGORIA: " + result.get(2).split(" - ")[1] + "\n" + "VALOR: R$" + result.get(2).split(" - ")[2]);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void Back(){
        Main.changeScreen("main");
    }


}
