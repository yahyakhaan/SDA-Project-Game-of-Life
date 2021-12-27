package group.gameoflife.SQL_GUI;

import com.google.gson.Gson;
import group.gameoflife.BL.cell;
import group.gameoflife.DB.SQL_DB;
import group.gameoflife.DB.textDB;
import group.gameoflife.main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class SQLDBLoadSceneController {
    private Stage stage;
    private Graphical_UI GUI;
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

        //SQL DATABASE
        //...
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene_SQL.fxml"));
        MainSceneController Controller = new MainSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        scene.getStylesheets().add(main.class.getResource("Stylesheet.css").toExternalForm());
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.setGamefromGUI(GUI);
        Controller.loadSQLDB(SQLDatabase);

        //...
        //ENDS HERE
    }

    public void load_SQLDB(SQL_DB DB) throws IOException //Load SQL Database
    {
        this.SQLDatabase=DB;
        setListSQL();
    }


    private void setListSQL() throws IOException //get load games from SQL Database
    {
        String[] savedGames;
        int[] noOfSavedGames=new int[1];


        SQLDatabase.giveNo_ofSavedGames();
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        noOfSavedGames = gson1.fromJson(Reader1, int[].class);
        //JSON SAVED GAMES NAMES
        SQLDatabase.viewStates();
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
        pane.getChildren().add(list_);
    }


    public void load(ActionEvent e) throws IOException
    {
        if (StageName!=null)
        {
            //SQL Database
            //...
            int size[]=new int[2];

            Writer writer1 =  new FileWriter("output.json");

            Gson gson3 = new Gson();
            gson3.toJson(StageName,writer1);
            writer1.flush();
            writer1.close();

            SQLDatabase.giveSize();
            Gson gson1 = new Gson();
            FileReader Reader1=new FileReader("output.json");
            size = gson1.fromJson(Reader1, int[].class);

            Writer writer =  new FileWriter("output.json");

            Gson gson2 = new Gson();
            gson2.toJson(StageName,writer);
            writer.flush();
            writer.close();

            SQLDatabase.loadState();
            Gson gson = new Gson();
            cell loaded_grid[][] = new cell[size[0]][size[1]];
            for (int i =0; i<size[0]; i++)
                for (int j =0; j<size[1];j++)
                {
                    loaded_grid[i][j]=new cell();
                }
            FileReader Reader=new FileReader("output.json");
            loaded_grid = gson.fromJson(Reader, cell[][].class);

            GUI.clearGrid();
            GUI.setSize(size);
            GUI.setGrid(loaded_grid);


            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene_SQL.fxml"));
            MainSceneController Controller = new MainSceneController();
            fxmlLoader.setController(Controller);
            Scene scene = new Scene(fxmlLoader.load(), 970, 730);
            scene.getStylesheets().add(main.class.getResource("Stylesheet.css").toExternalForm());
            stage.setTitle("Game Of Life");
            stage.setScene(scene);
            stage.show();
            Controller.setGamefromGUI(GUI);
            Controller.loadSQLDB(SQLDatabase);
            //...
            //ENDS HERE

        }
    }

    public void delete(ActionEvent e) throws IOException
    {
        if (StageName!=null)
        {
            //SQL Database
            //...
            Writer writer =  new FileWriter("output.json");

            Gson gson = new Gson();
            gson.toJson(StageName,writer);
            writer.flush();
            writer.close();
            SQLDatabase.deleteState();

            String[] savedGames;
            int[] noOfSavedGames=new int[1];


            SQLDatabase.giveNo_ofSavedGames();
            Gson gson1 = new Gson();
            FileReader Reader1=new FileReader("output.json");
            noOfSavedGames = gson1.fromJson(Reader1, int[].class);
            //JSON SAVED GAMES NAMES
            SQLDatabase.viewStates();
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
