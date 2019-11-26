package sample.controllers;

import DAO.ConexaoBD;
import VO.UsuarioVO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import sample.Main;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    @FXML
    private Label lblFirstSpend,lblSecondSpend,lblThirdSpend;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            Details();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void Details(){
        try {
            ArrayList<String> result = new ArrayList<String>();
            String query = "SELECT E.Nome_Empresa 'EMPRESA',SUM(G.Valor)'GASTO TOTAL',C.DescCategoria 'CATEGORIA' FROM tbl_gasto G\n" +
                    "INNER JOIN tbl_empresa E\n" +
                    "ON G.Id_Empresa = E.Id_Empresa\n" +
                    "INNER JOIN tbl_catempresa C\n" +
                    "ON C.Id_Categoria = E.Id_Categoria\n" +
                    "WHERE G.Id_Usuario = " + UsuarioVO.getInstance().getId() + "\n" +
                    "GROUP BY E.Nome_Empresa\n" +
                    "ORDER BY SUM(G.VALOR) DESC\n" +
                    "LIMIT 3;";

            Statement st = ConexaoBD.getInstance().getConexao().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                result.add(rs.getString("EMPRESA") + " - " + rs.getString("CATEGORIA") + " - " + rs.getDouble("GASTO TOTAL"));
            }

            if(result.size() != 0){
                lblFirstSpend.setText("TESTE01");
                lblFirstSpend.setText("1º\n" + "EMPRESA: " + result.get(0).split(" - ")[0] + "\n" + "CATEGORIA: " + result.get(0).split(" - ")[1] + "\n" + "VALOR: R$" + result.get(0).split(" - ")[2]);
                lblSecondSpend.setText("2º\n" +"EMPRESA: " + result.get(1).split(" - ")[0] + "\n" + "CATEGORIA: " + result.get(1).split(" - ")[1] + "\n" + "VALOR: R$" + result.get(1).split(" - ")[2]);
                lblThirdSpend.setText("3º\n" +"EMPRESA: " + result.get(2).split(" - ")[0] + "\n" + "CATEGORIA: " + result.get(2).split(" - ")[1] + "\n" + "VALOR: R$" + result.get(2).split(" - ")[2]);
            }
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
