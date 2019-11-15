package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import javax.swing.*;

public class MenuController {

    @FXML
    private Pane pnlFeed,pnlAbout,pnlHigherExpenses,pnlTransactions,pnlChart;

    @FXML
    private void GoFeed(javafx.event.ActionEvent actionEvent){
        pnlFeed.toFront();

        JOptionPane.showMessageDialog(null,"");
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
    @FXML
    private void GoAbout(javafx.event.ActionEvent actionEvent){
        pnlAbout.toFront();
    }

    public void Close(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }
    public MenuController(){

    }
}
