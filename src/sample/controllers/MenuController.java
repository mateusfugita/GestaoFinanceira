package sample.controllers;

import DAO.ConexaoBD;
import DAO.UsuarioDAO;
import VO.UsuarioVO;
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

import javax.swing.*;
import java.io.FileInputStream;
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
    private Label lblBalance,lblSpent,lblPerfil,lblTip1,lblTip2,lblName;
    @FXML
    private TableView<Transactions> tblTransaction,tblTransaction1;

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
            loadBalanceSpent(pieBalanceSpent);
            perfilUsuario();

            /*API a = new API();
            a.callAPI();*/

            String path = "Lp\\Perfil_Usuarios.py";
            String command = " cmd.exe /c start /min python " + path;
            Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + command);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                loadBalanceSpent(pieBalanceSpent);
                lblName.setText(UsuarioVO.getInstance().getNome());
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
                    "WHERE tbl_usuario.Id_Usuario = " + UsuarioVO.getInstance().getId() + "\n" +
                    "GROUP BY tbl_usuario.Id_Usuario;";

            Statement st = ConexaoBD.getInstance().getConexao().createStatement();
            ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    Balance = rs.getInt("SALDO");
                    Spent = rs.getInt("GASTO TOTAL");
                    ObservableList<PieChart.Data> data =
                            FXCollections.observableArrayList(
                                    new PieChart.Data("Balance", Balance),
                                    new PieChart.Data("Spent", Spent)
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

    private boolean validaExpenses1(ResultSet rs){
        try{
            if(rs.getString("EMPRESA").trim().length() > 0)
                return true;
            else
                return  false;
        }
        catch (Exception e){
            return false;
        }
    }

    private void loadExpenses1(BarChart barChart){
        try{
            String[] primeiro,segundo,terceiro;
            barChart.getData().clear();
            barChart.layout();
            String query = "SELECT E.Nome_Empresa 'EMPRESA', SUM(G.Valor) 'GASTO TOTAL' FROM tbl_gasto G\n" +
            "INNER JOIN tbl_empresa E\n" +
                    "ON G.Id_Empresa = E.Id_Empresa\n" +
                    "WHERE G.Id_Usuario = " + UsuarioVO.getInstance().getId() + "\n" +
                    "GROUP BY E.Nome_Empresa\n" +
                    "ORDER BY SUM(G.VALOR) DESC\n" +
                    "LIMIT 3";

            ArrayList<String> result = new ArrayList<String>();
            Statement st = ConexaoBD.getInstance().getConexao().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                result.add(rs.getString("EMPRESA") + " - " + rs.getString("GASTO TOTAL"));
            }

            if(result.size() != 0) {
                if (result.get(0) != null) {
                    primeiro = result.get(0).split("-");
                    XYChart.Series set1 = new XYChart.Series<>();
                    set1.getData().add(new XYChart.Data(primeiro[0].toString(), Double.parseDouble(primeiro[1])));
                    barChart.getData().addAll(set1);
                }
                if (result.get(1) != null) {
                    segundo = result.get(1).split("-");
                    XYChart.Series set2 = new XYChart.Series<>();
                    set2.getData().add(new XYChart.Data(segundo[0].toString(), Double.parseDouble(segundo[1])));
                    barChart.getData().addAll(set2);
                }
                if (result.get(2) != null) {
                    terceiro = result.get(2).split("-");
                    XYChart.Series set3 = new XYChart.Series<>();
                    set3.getData().add(new XYChart.Data(terceiro[0].toString(), Double.parseDouble(terceiro[1])));
                    barChart.getData().addAll(set3);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Nenhum gasto foi encontrado");
            }

            st.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private boolean validaExpensesPerCategory(ResultSet rs){
        try{
            if(rs.getString("CATEGORIA").trim().length() > 0)
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
    }

    private void loadExpensesPerCategory(BarChart barChart){
        try{
            barChart.getData().clear();
            barChart.layout();
            String[] primeiro,segundo,terceiro;
            String query = "SELECT C.DescCategoria 'CATEGORIA', SUM(G.Valor) 'GASTO' FROM tbl_gasto G\n" +
                    "INNER JOIN tbl_empresa E\n" +
                    "ON G.Id_Empresa = E.Id_Empresa\n" +
                    "INNER JOIN tbl_catempresa C\n" +
                    "ON E.Id_Categoria = C.Id_Categoria\n" +
                    "WHERE G.Id_Usuario = " + UsuarioVO.getInstance().getId() + "\n" +
                    "GROUP BY C.DescCategoria\n" +
                    "ORDER BY SUM(G.VALOR) DESC\n" +
                    "LIMIT 3";

            ArrayList<String> result = new ArrayList<String>();
            Statement st = ConexaoBD.getInstance().getConexao().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                result.add(rs.getString("CATEGORIA") + " - " + rs.getString("GASTO"));
            }

            if(result.size() !=0) {
                if (result.get(0) != null) {
                    primeiro = result.get(0).split("-");
                    XYChart.Series set1 = new XYChart.Series<>();
                    set1.getData().add(new XYChart.Data(primeiro[0].toString(), Double.parseDouble(primeiro[1])));
                    barChart.getData().addAll(set1);
                }
                if (result.get(1) != null) {
                    segundo = result.get(1).split("-");
                    XYChart.Series set2 = new XYChart.Series<>();
                    set2.getData().add(new XYChart.Data(segundo[0].toString(), Double.parseDouble(segundo[1])));
                    barChart.getData().addAll(set2);
                }
                if (result.get(2) != null) {
                    terceiro = result.get(2).split("-");
                    XYChart.Series set3 = new XYChart.Series<>();
                    set3.getData().add(new XYChart.Data(terceiro[0].toString(), Double.parseDouble(terceiro[1])));
                    barChart.getData().addAll(set3);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Nenhuma transação encontrada");
            }

            st.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private boolean validaTransactions(ResultSet rs){
        try{
            System.out.println(rs.getString("SPENT"));
            if(rs.getString("SPENT").trim().length() > 0)
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
    }

    private void ShowTransactions(){
        try {
            tblTransaction.getItems().clear();
            ArrayList<String> transactions = new ArrayList<String>();
            
            String query = "SELECT G.Data_Gasto 'DATE',G.Valor 'SPENT',E.Nome_Empresa 'COMPANY' FROM tbl_gasto G\n" +
                    "INNER JOIN tbl_empresa E\n" +
                    "ON G.Id_Empresa = E.Id_Empresa\n" +
                    "WHERE G.Id_Usuario = " + UsuarioVO.getInstance().getId() + "\n" +
                    "ORDER BY G.Data_Gasto DESC";

            Statement st = ConexaoBD.getInstance().getConexao().createStatement();
            ResultSet rs = st.executeQuery(query);

            //if(validaTransactions(rs)){
            while (rs.next()){
                transactions.add(rs.getDate("DATE").toString() + " " + rs.getDouble("SPENT")+ " " + rs.getString("COMPANY"));
              //  }
            //}
            //else{
                //JOptionPane.showMessageDialog(null,"Nenhuma transação encontrada");
            }

            if(transactions.size()!=0) {

                TableColumn<Transactions, String> colDate = new TableColumn("DATE");
                colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));
                colDate.setMinWidth(250);

                TableColumn<Transactions, Double> colSpent = new TableColumn("SPENT(R$)");
                colSpent.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSpent()).asObject());
                colSpent.setMinWidth(250);

                TableColumn<Transactions, String> colCompany = new TableColumn("COMPANY");
                colCompany.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCompany()));
                colCompany.setMinWidth(250);

                tblTransaction.getColumns().addAll(colDate, colSpent, colCompany);

                for (int i = 0; i < transactions.size(); i++) {
                    String[] dados = transactions.get(i).split(" ");
                    String company = "";
                    for (int j = 2; j < dados.length; j++) {
                        company += dados[j] + " ";
                    }
                    tblTransaction.getItems().add(new Transactions(dados[0], Double.parseDouble(dados[1]), company));
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Nenhuma transação encontrada");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void ShowTransactions1(){
        try {
            tblTransaction1.getItems().clear();
            ArrayList<String> transactions = new ArrayList<String>();
            String query = "SELECT E.Data_Entrada 'DATA',E.VALOR 'VALOR' FROM tbl_entrada E\n" +
                    "WHERE E.Id_Usuario = " + UsuarioVO.getInstance().getId() + "\n" +
                    "ORDER BY E.DATA_ENTRADA DESC;";

            Statement st = ConexaoBD.getInstance().getConexao().createStatement();
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
            "WHERE U.Id_Usuario = " + UsuarioVO.getInstance().getId() + "\n";

            Statement st = ConexaoBD.getInstance().getConexao().createStatement();
            ResultSet rs = st.executeQuery(query);

            if(rs != null){
                while(rs.next()){
                    lblPerfil.setText(rs.getString("PERFIL"));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadTips(){
        try{
            lblTip1.setText("");
            lblTip2.setText("");
            ArrayList<String> result = new ArrayList<String>();
            Scanner read = new Scanner(new FileInputStream("Saida.txt"));
            while(read.hasNextLine()){
                result.add(read.nextLine());
            }

            if(result.size() !=0) {
                lblTip1.setText("- " + result.get(0) + ".");
                lblTip2.setText("- " + result.get(1) + ".");
            }
            else{
                JOptionPane.showMessageDialog(null,"Não há nenhuma Sugestão");
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void GoHome(javafx.event.ActionEvent actionEvent){
        pnlHome.toFront();
        loadTips();
        perfilUsuario();
    }
    @FXML
    private void GoTransactions(javafx.event.ActionEvent actionEvent){
        pnlTransactions.toFront();
        ShowTransactions();
        ShowTransactions1();
    }
    @FXML
    private void GoCharts(javafx.event.ActionEvent actionEvent){
        pnlChart.toFront();
        loadExpensesPerCategory(barChartExpenses);
    }
    @FXML
    private void GoHigherExpenses(javafx.event.ActionEvent actionEvent){
        pnlHigherExpenses.toFront();
        loadExpenses1(barChartExpenses1);
    }
    public void Close(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }
    @FXML
    private void ShowDetails() {
        Main.changeScreen("details");
        DetailsController d = new DetailsController();
        d.Details();
    }
}
