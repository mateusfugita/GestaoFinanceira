package sample.controllers;

import DAO.ConexaoBD;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.control.Label;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class MenuController implements Initializable {
    int Balance,Spent = 0;
    @FXML
    private Pane pnlHigherExpenses,pnlTransactions,pnlChart,pnlHome;
    @FXML
    private PieChart pieBalanceSpent;
    @FXML
    private BarChart<?, ?> barChartExpenses,barChartExpenses1;
    @FXML
    private Label lblBalance,lblSpent,lblPerfil;
    @FXML
    private TableView<Transactions> tblTransaction,tblTransaction1;

    public static Connection getConnection(){
        Connection conexao = ConexaoBD.getInstance().getConexao();
        /*try{
            //conexao = DriverManager.getConnection("jdbc:mysql://localhost/financeira?useTimezone=true&serverTimezone=UTC", "root", "1234");
            conexao = DriverManager.getConnection("jdbc:mysql://localhost/Financeira?useTimezone=true&serverTimezone=UTC", "root", "");
        }
        catch(SQLException e){
            System.out.println("Erro ao conectar");
        }*/
        return conexao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadBalanceSpent(pieBalanceSpent);
            loadExpenses1(barChartExpenses1);
            loadExpensesPerCategory(barChartExpenses);
            ShowTransactions();
            ShowTransactions1();
            perfilUsuario();

            String path = "Lp\\Perfil_Usuarios.py";
            String command = " cmd.exe /c start /min python " + path;
            Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + command);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                loadBalanceSpent(pieBalanceSpent);
                perfilUsuario();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void loadBalanceSpent(PieChart pieChart) {
        try {
            String query = "SELECT tbl_usuario.Id_Usuario 'ID',tbl_usuario.Saldo 'SALDO',SUM(tbl_gasto.Valor) 'GASTO TOTAL' FROM tbl_usuario\n" +
                    "INNER JOIN tbl_gasto\n" +
                    "ON tbl_usuario.Id_Usuario = tbl_gasto.Id_Usuario\n" +
                    "WHERE tbl_usuario.Id_Usuario = 4\n" +
                    "GROUP BY tbl_usuario.Id_Usuario;";

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                Balance = rs.getInt("SALDO");
                Spent = rs.getInt("GASTO TOTAL");
                ObservableList<PieChart.Data> data =
                        FXCollections.observableArrayList(
                                new PieChart.Data("Balance", Balance),
                                new PieChart.Data("Spent",Spent)
                        );
                pieChart.setData(data);
                lblBalance.setText("R$ " + Integer.toString(Balance));
                lblSpent.setText("R$ " + Integer.toString(Spent));
            }
            st.close();
        }
        catch (Exception e ) {
            System.out.println(e.getMessage());
        }
    }

    private void loadExpenses1(BarChart barChart){
        try{
            String query = "SELECT E.Nome_Empresa 'EMPRESA', SUM(G.Valor) 'GASTO TOTAL' FROM tbl_gasto G\n" +
                    "INNER JOIN tbl_empresa E\n" +
                    "ON G.Id_Empresa = E.Id_Empresa\n" +
                    "WHERE G.Id_Usuario = 4\n" +
                    "GROUP BY E.Nome_Empresa\n" +
                    "ORDER BY SUM(G.VALOR) DESC\n" +
                    "LIMIT 3";

            ArrayList<String> result = new ArrayList<String>();
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                result.add(rs.getString("EMPRESA") + " - " + rs.getString("GASTO TOTAL"));
            }

            String[] primeiro = result.get(0).split("-");
            String[] segundo = result.get(1).split("-");
            String[] terceiro = result.get(2).split("-");

            XYChart.Series set1 = new XYChart.Series<>();
            XYChart.Series set2 = new XYChart.Series<>();
            XYChart.Series set3 = new XYChart.Series<>();

            set1.getData().add(new XYChart.Data(primeiro[0].toString(),Double.parseDouble(primeiro[1])));
            set2.getData().add(new XYChart.Data(segundo[0].toString(),Double.parseDouble(segundo[1])));
            set3.getData().add(new XYChart.Data(terceiro[0].toString(),Double.parseDouble(terceiro[1])));

            barChart.getData().addAll(set1);
            barChart.getData().addAll(set2);
            barChart.getData().addAll(set3);
            st.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void loadExpensesPerCategory(BarChart barChart){
        try{
            String query = "SELECT C.DescCategoria 'CATEGORIA', SUM(G.Valor) 'GASTO' FROM tbl_gasto G\n" +
                    "INNER JOIN tbl_empresa E\n" +
                    "ON G.Id_Empresa = E.Id_Empresa\n" +
                    "INNER JOIN tbl_catempresa C\n" +
                    "ON E.Id_Categoria = C.Id_Categoria\n" +
                    "WHERE G.Id_Usuario = 4\n" +
                    "GROUP BY C.DescCategoria\n" +
                    "ORDER BY SUM(G.VALOR) DESC\n" +
                    "LIMIT 3";

            ArrayList<String> result = new ArrayList<String>();
            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                result.add(rs.getString("CATEGORIA") + " - " + rs.getString("GASTO"));
            }

            String[] primeiro = result.get(0).split("-");
            String[] segundo = result.get(1).split("-");
            String[] terceiro = result.get(2).split("-");

            XYChart.Series set1 = new XYChart.Series<>();
            XYChart.Series set2 = new XYChart.Series<>();
            XYChart.Series set3 = new XYChart.Series<>();

            set1.getData().add(new XYChart.Data(primeiro[0].toString(),Double.parseDouble(primeiro[1])));
            set2.getData().add(new XYChart.Data(segundo[0].toString(),Double.parseDouble(segundo[1])));
            set3.getData().add(new XYChart.Data(terceiro[0].toString(),Double.parseDouble(terceiro[1])));

            barChart.getData().addAll(set1);
            barChart.getData().addAll(set2);
            barChart.getData().addAll(set3);
            st.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void ShowTransactions(){
        try {
            ArrayList<String> transactions = new ArrayList<String>();
            
            String query = "SELECT G.Data_Gasto 'DATE',G.Valor 'SPENT',E.Nome_Empresa 'COMPANY' FROM tbl_gasto G\n" +
                    "INNER JOIN tbl_empresa E\n" +
                    "ON G.Id_Empresa = E.Id_Empresa\n" +
                    "ORDER BY G.Data_Gasto DESC";

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                transactions.add(rs.getDate("DATE").toString() + " " + rs.getDouble("SPENT")+ " " + rs.getString("COMPANY"));
            }

            TableColumn<Transactions, String> colDate = new TableColumn("DATE");
            colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
            colDate.setMinWidth(250);

            TableColumn<Transactions,Double> colSpent = new TableColumn("SPENT(R$)");
            colSpent.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSpent()).asObject());
            colSpent.setMinWidth(250);

            TableColumn<Transactions,String> colCompany = new TableColumn("COMPANY");
            colCompany.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCompany()));
            colCompany.setMinWidth(250);

            tblTransaction.getColumns().addAll(colDate,colSpent,colCompany);

            for(int i= 0; i < transactions.size(); i++) {
                String[] dados = transactions.get(i).split(" ");
                String company = "";
                for(int j = 2; j < dados.length;j++){
                    company += dados[j] + " ";
                }
                tblTransaction.getItems().add(new Transactions(dados[0], Double.parseDouble(dados[1]), company));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void ShowTransactions1(){
        try {
            ArrayList<String> transactions = new ArrayList<String>();

            String query = "SELECT E.Data_Entrada 'DATA',E.VALOR 'VALOR' FROM tbl_entrada E\n" +
                    "INNER JOIN tbl_gasto U\n" +
                    "ON E.Id_Usuario = U.Id_Usuario\n" +
                    "ORDER BY E.DATA_ENTRADA DESC";

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                transactions.add(rs.getDate("DATA").toString() + " " + rs.getDouble("VALOR"));
            }

            TableColumn<Transactions, String> colDate = new TableColumn("DATE");
            colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
            colDate.setMinWidth(350);

            TableColumn<Transactions,Double> colValue = new TableColumn("VALUE(R$)");
            colValue.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValue()).asObject());
            colValue.setMinWidth(350);

            tblTransaction1.getColumns().addAll(colDate,colValue);

            for(int i= 0; i < transactions.size(); i++) {
                tblTransaction1.getItems().add(new Transactions(transactions.get(i).split(" ")[0],Double.parseDouble(transactions.get(i).split(" ")[1])));
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void perfilUsuario()  {
        try {
            String query = "SELECT P.Descricao 'PERFIL' FROM tbl_usuario U\n"+
            "INNER JOIN tbl_perfilgasto P\n"+
            "ON U.Id_Perfil = P.Id_PerfilGasto\n"+
            "WHERE U.Id_Usuario = 4;";

            Statement st = getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                lblPerfil.setText(rs.getString("PERFIL"));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void GoHome(javafx.event.ActionEvent actionEvent){
        pnlHome.toFront();
    }
    @FXML
    private void GoTransactions(javafx.event.ActionEvent actionEvent){
        pnlTransactions.toFront();
    }
    @FXML
    private void GoCharts(javafx.event.ActionEvent actionEvent){
        pnlChart.toFront();
    }
    @FXML
    private void GoHigherExpenses(javafx.event.ActionEvent actionEvent){
        pnlHigherExpenses.toFront();
    }
    public void Close(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }
    @FXML
    private void ShowDetails() {
        Main.changeScreen("details");
    }
}
