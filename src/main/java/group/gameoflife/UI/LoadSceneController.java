package group.gameoflife.UI;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;
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

import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class LoadSceneController {
    private Stage stage;
    private Graphical_UI GUI;
    private textDB TextDatabase;
    private String StageName;
    @FXML
    private Pane pane;

    public void loadGUI(Graphical_UI GUI)
    {
        this.GUI=GUI;
    }

    public void back (ActionEvent e) throws IOException
    {
        System.out.println("Go Back");
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene.fxml"));
        MainSceneController Controller = new MainSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.setGamefromGUI(GUI);
        Controller.loadTextDB(TextDatabase);
    }
    public void setList(textDB DB)
    {
        TextDatabase=DB;
        String[] savedGames;
        int[] noOfSavedGames=new int[1];
        savedGames=TextDatabase.savedGamesName(noOfSavedGames);
        for(int i=0;i<noOfSavedGames[0];i++) {
            System.out.println(savedGames[i]);
        }
        ListView<String> list_=new ListView<String>();
        if (noOfSavedGames[0]!=0)
        {
            for(int i=0;i<noOfSavedGames[0];i++) {
                list_.getItems().add(savedGames[i]);
            }
           // list_.getItems().addAll(savedGames);

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
            int size[]=new int[2];
            cell loaded_grid[][];
            loaded_grid = TextDatabase.loadGame(StageName,size);
            GUI.clearGrid();
            GUI.setSize(size);
            GUI.setGrid(loaded_grid);

            System.out.println("Loaded Game");
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("MainScene.fxml"));
            MainSceneController Controller = new MainSceneController();
            fxmlLoader.setController(Controller);
            Scene scene = new Scene(fxmlLoader.load(), 970, 730);
            stage.setTitle("Game Of Life");
            stage.setScene(scene);
            stage.show();
            Controller.setGamefromGUI(GUI);
            Controller.loadTextDB(TextDatabase);
        }
    }

    public void delete(ActionEvent e) throws IOException
    {
        if (StageName!=null)
        {
            TextDatabase.deleteGame(StageName);

            String[] savedGames;
            int[] noOfSavedGames=new int[1];
            savedGames=TextDatabase.savedGamesName(noOfSavedGames);
            for(int i=0;i<noOfSavedGames[0];i++) {
                System.out.println(savedGames[i]);
            }
            ListView<String> list_=new ListView<String>();
            if (noOfSavedGames[0]!=0)
            {
                for(int i=0;i<noOfSavedGames[0];i++) {
                    list_.getItems().add(savedGames[i]);
                }
                // list_.getItems().addAll(savedGames);

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
        }
    }

}
