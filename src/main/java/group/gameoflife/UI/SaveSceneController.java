package group.gameoflife.UI;
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
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class SaveSceneController {
    private Stage stage;
    private  GridPane Grid_;
    private Graphical_UI GUI;
    private textDB TextDatabase;
    private SQL_DB SQLDatabase;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField Stage_text;
    @FXML
    private Label Status;

    public void loadGUI(Graphical_UI GUI)
    {
        // check =1;
        this.GUI=GUI;
        int[] gridSize= GUI.getGridSize();
        gridSize = this.GUI.getGridSize();
        Grid_ = new GridPane();
        Grid_.setPadding(new Insets(5));
        Grid_.setHgap(5);
        Grid_.setVgap(5);
        Grid_.setScaleX(0.5);
        Grid_.setScaleY(0.5);
        Grid_.setStyle("-fx-background-color: #090510;");
        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {
                System.out.println("In loop");
                Button button = new Button(String.valueOf("  "));
                button.setId(Integer.toString(r)+":"+Integer.toString(c));
                if (GUI.isCellAlive(r,c)) {
                    button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color:#55031F ; -fx-border-width:2; ");
                }
                else{
                    button.setStyle("-fx-background-color: #51515A; -fx-border-color:#55031F ; -fx-border-width:2;");
                }
                Grid_.add(button, c, r);
            }
        }
        this.scrollPane.setContent(Grid_);


    }
    public void load_TextDB(textDB DB)
    {
        TextDatabase=DB;
    }
    public void load_SQLDB(SQL_DB DB)
    {
        this.SQLDatabase=DB;
    }
    public void save (ActionEvent e)
    {
        String name;
        name = Stage_text.getText();
        System.out.println(name);
        if (name != null)
        {
            int check = TextDatabase.saveGame(name);
            if (check==-1)
            {
                Status.setText("Same Stage Name already exists !");
            }
            else{

                Status.setText("Game Saved !!");
            }
        }

/*        String name;
        name = Stage_text.getText();
        System.out.println(name);
        if (name != null)
        {
            int check = SQLDatabase.saveState(name);
            if (check==-1)
            {
                Status.setText("Same Stage Name already exists !");
            }
            else{

                Status.setText("Game Saved !!");
            }
        }*/
    }
    public void back (ActionEvent e) throws IOException
    {
        System.out.println("Go Back");
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


/*        System.out.println("Go Back");
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
        Controller.loadSQLDB(SQLDatabase);*/
    }
}
