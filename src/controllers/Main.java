package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Font.loadFont(getClass().getResource("/fonts/VarelaRound-Regular.ttf").toExternalForm(),10);
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/sample.fxml")); 
        primaryStage.setTitle("Pomodoro");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    private SVGPath setUpPlayButtonSvg() {
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
