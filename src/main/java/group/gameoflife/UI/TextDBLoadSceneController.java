package group.gameoflife.UI;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;
import group.gameoflife.DB.SQL_DB;
import group.gameoflife.DB.textDB;
import group.gameoflife.main;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.sql.Array;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class TextDBLoadSceneController {
    private Stage stage;
    private Graphical_UI GUI;
    private textDB TextDatabase;
    private SQL_DB SQLDatabase;
    private String StageName;
    @FXML
    private Pane pane;

    public void loadGUI(Graphical_UI GUI) //Saves previous GUI for GO-BACK Implementation
    {
        this.GUI=GUI;
    }
    public void back (ActionEvent e) throws IOException //Go back Implementation
    {
        //TEXTFILE DATABASE
        //...
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene.fxml"));
        MainSceneController Controller = new MainSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        scene.getStylesheets().add(main.class.getResource("Stylesheet.css").toExternalForm());
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.setGamefromGUI(GUI);
        Controller.loadTextDB(TextDatabase);
        Controller.loadSQLDB(SQLDatabase);
        //...
        //ENDS HERE


    }
    public void load_TextDB(textDB DB) throws IOException //Load Text Database
    {
        TextDatabase=DB;
        setList();
    }
    public void load_SQLDB(SQL_DB DB) //Load SQL Database
    {
        this.SQLDatabase=DB;
    }

    private void setList() throws IOException //get load games from Text File Database
    {

        String[] savedGames;
        int[] noOfSavedGames = new int[1];
        //JSON NO OF SAVED GAMES
        TextDatabase.giveNo_ofSavedGames();
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        noOfSavedGames = gson1.fromJson(Reader1, int[].class);
        //JSON SAVED GAMES NAMES
        TextDatabase.savedGamesName();
        Gson gson2 = new Gson();
        FileReader Reader2=new FileReader("output.json");
        savedGames = gson1.fromJson(Reader2, String[].class);


        ListView<String> list_=new ListView<String>(); //List view will be added to the pane

        if (noOfSavedGames[0]!=0)
        {
            for(int i=0;i<noOfSavedGames[0];i++) {
                list_.getItems().add(savedGames[i]);
            }

        }
        //whenever selects a game set StageName
        list_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<?extends String> observableValue, String o, String t1) {
                StageName=list_.getSelectionModel().getSelectedItem();
            }
        });
        pane.getChildren().add(list_);
    }


    public void load(ActionEvent e) throws IOException
    {
        if (StageName!=null)
        {
            //Text File Database
            //...
            int size[]=new int[2];

            //JSON READ SIZE
            //....
            Writer writer1 =  new FileWriter("output.json");

            Gson gson3 = new Gson();
            gson3.toJson(StageName,writer1);
            writer1.flush();
            writer1.close();

            TextDatabase.giveSize();
            Gson gson1 = new Gson();
            FileReader Reader1=new FileReader("output.json");
            size = gson1.fromJson(Reader1, int[].class);
            //....


            //JSON READ GRID
            //....
            Writer writer =  new FileWriter("output.json");

            Gson gson2 = new Gson();
            gson2.toJson(StageName,writer);
            writer.flush();
            writer.close();
            TextDatabase.loadGame();
            Gson gson = new Gson();
            cell loaded_grid[][] = new cell[size[0]][size[1]];
            for (int i =0; i<size[0]; i++)
                for (int j =0; j<size[1];j++)
                {
                    loaded_grid[i][j]=new cell();
                }
            FileReader Reader=new FileReader("output.json");
            loaded_grid = gson.fromJson(Reader, cell[][].class);
            //....


            GUI.clearGrid();
            GUI.setSize(size);
            GUI.setGrid(loaded_grid);


            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene.fxml"));
            MainSceneController Controller = new MainSceneController();
            fxmlLoader.setController(Controller);
            Scene scene = new Scene(fxmlLoader.load(), 970, 730);
            scene.getStylesheets().add(main.class.getResource("Stylesheet.css").toExternalForm());
            stage.setTitle("Game Of Life");
            stage.setScene(scene);
            stage.show();
            Controller.setGamefromGUI(GUI);
            Controller.loadTextDB(TextDatabase);
            Controller.loadSQLDB(SQLDatabase);
            //...
            //ENDS HERE

        }
    }

    public void delete(ActionEvent e) throws IOException
    {
        if (StageName!=null)
        {
            //Text File Database
            //...


            //JSON Write Stage name

            Writer writer =  new FileWriter("output.json");

            Gson gson = new Gson();
            gson.toJson(StageName,writer);
            writer.flush();
            writer.close();
            TextDatabase.deleteGame();

            String[] savedGames;
            int[] noOfSavedGames=new int[1];
            TextDatabase.giveNo_ofSavedGames();
            Gson gson1 = new Gson();
            FileReader Reader1=new FileReader("output.json");
            noOfSavedGames = gson1.fromJson(Reader1, int[].class);

            TextDatabase.savedGamesName();
            Gson gson2 = new Gson();
            FileReader Reader2=new FileReader("output.json");
            savedGames = gson1.fromJson(Reader2, String[].class);

            ListView<String> list_=new ListView<String>();
            if (noOfSavedGames[0]!=0)
            {
                for(int i=0;i<noOfSavedGames[0];i++) {
                    list_.getItems().add(savedGames[i]);
                }


            }
            list_.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<?extends String> observableValue, String o, String t1) {
                    StageName=list_.getSelectionModel().getSelectedItem();
                }
            });
            pane.getChildren().removeAll();
            pane.getChildren().add(list_);
            StageName=null;
            //...
            //ENDS HERE

        }
    }

}
