package group.gameoflife;

import group.gameoflife.BL.grid;
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
        int grid_xSize=10;
        int grid_ySize=10;
        grid game = new grid(grid_xSize,grid_ySize);
        Graphical_UI GUI = new Graphical_UI(game);
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