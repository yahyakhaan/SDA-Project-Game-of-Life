package group.gameoflife;

import group.gameoflife.UI.Graphical_UI;
import group.gameoflife.UI.MainSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Graphical_UI GUI = new Graphical_UI();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene.fxml"));
        MainSceneController Controller = new MainSceneController();
        Controller.loadGUI(GUI);
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}