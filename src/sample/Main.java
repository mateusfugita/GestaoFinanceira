package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private static Stage stage;
    private static Scene loginScene,homescene,registerscene,detailscene;
    private double x,y;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        Parent login = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
        loginScene = new Scene(login);

        Parent home = FXMLLoader.load(getClass().getResource("fxml/Menu.fxml"));
        homescene = new Scene(home);

        Parent register = FXMLLoader.load(getClass().getResource("fxml/Register.fxml"));
        registerscene = new Scene(register);

        Parent details = FXMLLoader.load(getClass().getResource("fxml/Details.fxml"));
        detailscene = new Scene(details);

        home.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        home.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });

        details.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        details.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScreen(String scr) {
        switch (scr) {
            case "main":
                stage.setScene(homescene);
                break;
            case "register":
                stage.setScene(registerscene);
                break;
            case "login":
                stage.setScene(loginScene);
                break;
            case "details":
                stage.setScene(detailscene);
                break;
        }

    }
}