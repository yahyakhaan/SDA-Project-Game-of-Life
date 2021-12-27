package group.gameoflife.SQL_GUI;

import com.google.gson.Gson;
import group.gameoflife.DB.SQL_DB;
import group.gameoflife.DB.textDB;
import group.gameoflife.main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class SQLDBSaveSceneController {
    private Stage stage;
    private  GridPane Grid_;
    private Graphical_UI GUI;
    private SQL_DB SQLDatabase;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField Stage_text;
    @FXML
    private Label Status;

    public void loadGUI(Graphical_UI GUI) throws IOException //load GUI object for GO-BACK implentation
    {
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

    public void load_SQLDB(SQL_DB DB) //Load SQL Database
    {
        this.SQLDatabase=DB;
    }

    public void save (ActionEvent e) throws IOException {
        String name;
        name = Stage_text.getText();

        if (name != null)
        {
            Writer writer =  new FileWriter("output.json");
            Gson gson = new Gson();
            gson.toJson(name,writer);
            writer.flush();
            writer.close();
            SQLDatabase.saveState();
            FileReader Reader1=new FileReader("output.json");
            int check = gson.fromJson(Reader1, int.class);
            if (check==-1)
            {
                Status.setText("Same Stage Name already exists !");
            }
            else{

                Status.setText("Game Saved !!");
            }
        }


        //...
        //Ends Here
    }

    public void back (ActionEvent e) throws IOException
    {
        //SQL Database
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
        //Ends Here
    }
}
