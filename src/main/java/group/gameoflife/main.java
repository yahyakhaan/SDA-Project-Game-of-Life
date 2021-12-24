package group.gameoflife;
import javafx.stage.Screen;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import group.gameoflife.BL.grid;
import group.gameoflife.DB.SQL_DB;
import group.gameoflife.DB.textDB;

//Uncomment this for Combined Project
import group.gameoflife.UI.Graphical_UI;
import group.gameoflife.UI.MainSceneController;

//Uncomment this for SQLOnly Project
//import group.gameoflife.SQL_GUI.Graphical_UI;
//import group.gameoflife.SQL_GUI.MainSceneController;

//Uncomment this for TextDB_Only Project
//import group.gameoflife.TEXTDB_GUI.Graphical_UI;
//import group.gameoflife.TEXTDB_GUI.MainSceneController;


import javafx.application.Application;
import javafx.css.Style;
import javafx.css.Stylesheet;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //initial Grid Size
        int grid_xSize=50;
        int grid_ySize=50;

        grid game = new grid(grid_xSize,grid_ySize); //BL Obj

        textDB TextDatabase = new textDB(game); //DB Obj
        SQL_DB sql =new SQL_DB(game); //DB obj

        Graphical_UI GUI = new Graphical_UI(game); //UI Obj


        //GUI SETUP


        //Load only those resources that are required in project
        //...
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene_SQL.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene_TEXTDB.fxml"));
        //...


        MainSceneController Controller = new MainSceneController();
        fxmlLoader.setController(Controller);
        Controller.loadGUI(GUI); // Pass UI Obj

        //Load only those DBs that are required in project
        //...
        Controller.loadTextDB(TextDatabase); // Pass SQL-DB Obj
        Controller.loadSQLDB(sql); //Pass SQL-DB Obj
        //...

        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        scene.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm()); //Pass CSS Styling
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.game_set();

    }

    public static void main(String[] args) {
        launch();
    }

}