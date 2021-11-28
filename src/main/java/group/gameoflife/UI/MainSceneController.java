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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainSceneController {
    private int Grid_Hgap;
    private int Grid_Vgap;
    private double Grid_Xscale;
    private double Grid_Yscale;
    private double speedFactor;
   // private int check;
    private Stage stage;
    private boolean game_status;
    private  GridPane Grid_;
    private textDB TextDatabase;
    private SQL_DB SQLDatabase;
    private Graphical_UI GUI;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane plane;
    @FXML
    private Label welcomeText;
    @FXML
    private Slider zoomSlider;
    @FXML
    private Slider speedSlider;
    @FXML
    private Label noOfStage;
    @FXML

    private int[] returnButtonId(String s)
    {
        String[] ID = s.split(":");
        int[] ID_ = new int[2];
        ID_[0]=Integer.parseInt(ID[0]);
        ID_[1]=Integer.parseInt(ID[1]);
        return ID_;
    }

    private Button getButtonInGrid ( int r,  int c, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> nodesInside = gridPane.getChildren();

        for (Node node : nodesInside) {
            if(gridPane.getRowIndex(node) == r && gridPane.getColumnIndex(node) == c) {
                result = node;
                break;
            }
        }

        return (Button)result;
    }
    public void loadTextDB(textDB DB)
    {
        this.TextDatabase=DB;
    }
    public void loadSQLDB(SQL_DB SQLDB)
    {
        SQLDatabase=SQLDB;

    }
    public void loadGUI(Graphical_UI GUI)
    {
       // check =1;
        this.GUI=GUI;
        int[] gridSize= GUI.getGridSize();
        System.out.println("GridSize from GRID: "+ gridSize[0]);
    }
    @FXML
    public void updateCells()
    {
        noOfStage.setText(String.valueOf(GUI.getNoOfStates()));
        int gridSize[];
        gridSize = GUI.getGridSize();
        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {
                    Button b = getButtonInGrid(r,c,Grid_);
               /* System.out.println("Tracked from Grid : "+b.getId());*/
                int [] IDs = this.returnButtonId(b.getId());
                Boolean check = GUI.isCellAlive(IDs[0],IDs[1]);
                if (check == true)
                {
                    b.setStyle("-fx-background-color: #00FF00; -fx-border-style: solid;");
                }
                else b.setStyle("-fx-background-color: #FFF; -fx-border-style: solid;");
            }
        }

    }



    public void loadBlankGrid()
    {
        //System.out.println(check);
        int[] gridSize;
        gridSize = this.GUI.getGridSize();
        Grid_ = new GridPane();
        Grid_.setPadding(new Insets(5));
        Grid_.setHgap(5);
        Grid_.setVgap(5);


        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {
                System.out.println("In loop");
                Button button = new Button(String.valueOf("  "));
                Grid_Xscale=button.getScaleX();
                Grid_Yscale=button.getScaleY();
                button.setId(Integer.toString(r)+":"+Integer.toString(c));
                button.setStyle("-fx-background-color: #FFF; -fx-border-style: solid;");
                System.out.println(button.getId());
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int[] buttonID;
                        String BID_Str;
                        Button button_ = (Button) actionEvent.getSource();
                        BID_Str= button_.getId();
                        buttonID = returnButtonId(BID_Str);
                        System.out.println(buttonID[0]+" "+buttonID[1]);
                        Boolean Alive = GUI.isCellAlive(buttonID[0],buttonID[1]);
                        if (Alive==false) {
                            //Button Selected
                            System.out.println(BID_Str + " IS CLICKED IN");
                            GUI.makeCellALive(buttonID[0],buttonID[1]);
                            System.out.println("ALIVE: "+buttonID[0]+" "+buttonID[1]);
                            updateCells();
                        }
                        else {
                           //Button Deselected
                            System.out.println(BID_Str + " IS CLICKED");
                            GUI.makeCellDead(buttonID[0],buttonID[1]);
                            updateCells();
                        }
                    }
                });
                Grid_.add(button, c, r);
            }
        }
        this.scrollPane.setContent(Grid_);

        //updateCells();

    }
    public void clearStage(ActionEvent e)
    {
        GUI.clearGrid();
        updateCells();
    }

    public void setGamefromGUI(Graphical_UI GUI)
    {
        this.GUI=GUI;
        int[] gridSize= GUI.getGridSize();
        Grid_Hgap=5;
        Grid_Vgap=5;
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setFitToWidth(true);
        GUI.startGame();
        loadBlankGrid();
        updateCells();
        welcomeText.setText("Game of Life");

        zoomSlider.setValue(47);
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                System.out.println("Old Value: " + oldValue.intValue() + "New Value: " + newValue.intValue()/* +"Multiple: " + multiple*/);
                if (oldValue.intValue() - newValue.intValue() < 0) {
                    zoomIn(newValue.intValue() - oldValue.intValue());
                } else if (oldValue.intValue() - newValue.intValue() > 0) {
                    zoomOut(oldValue.intValue() - newValue.intValue());
                }
            }
        });
        speedSlider.setValue(0.5);
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                // System.out.println("Old Value: " + oldValue.intValue() + "New Value: " + newValue.intValue()/* +"Multiple: " + multiple*/);

                speedUp(newValue.doubleValue());

            }
        });
        this.game_status = true;
    }
    public void game_set()
    {

        Grid_Hgap=5;
        Grid_Vgap=5;
        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setFitToWidth(true);
        GUI.startGame();
        GUI.clearGrid();
        loadBlankGrid();
        welcomeText.setText("Game of Life");

        zoomSlider.setValue(47);
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {

        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            System.out.println("Old Value: " + oldValue.intValue() + "New Value: " + newValue.intValue()/* +"Multiple: " + multiple*/);
            if (oldValue.intValue() - newValue.intValue() < 0) {
                zoomIn(newValue.intValue() - oldValue.intValue());
            } else if (oldValue.intValue() - newValue.intValue() > 0) {
                zoomOut(oldValue.intValue() - newValue.intValue());
            }
        }
        });
        speedSlider.setValue(0.5);
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
               // System.out.println("Old Value: " + oldValue.intValue() + "New Value: " + newValue.intValue()/* +"Multiple: " + multiple*/);

                   speedUp(newValue.doubleValue());

            }
        });
        this.game_status = true;

    }
    public void start(ActionEvent e)
    {
            GUI.startGame();
          //  GUI.clearGrid();
            updateCells();
            welcomeText.setText("Game Started");
            this.game_status = true;
            gameLoop();
    }
    public void stop(ActionEvent e)
    {
        GUI.stopGame();
        this.game_status=false;
    }

    public void gameLoop()
    {

        final long[] startnanoTime = {System.nanoTime()};
        AnimationTimer at = new AnimationTimer()
        {
            @Override
            public void handle(long l) {/*
                long t = (long) (l- startnanoTime[0] /1e9);*/

                if (l - (long) startnanoTime[0] >= speedFactor*1e9) {
                    /*startnanoTime[0] = t;*/
                    startnanoTime[0] = l;
                    GUI.nextState();
                    updateCells();
                }
                if (game_status==false)
                {
                    stop();
                }
            }

            @Override
            public void start() {
                super.start();
            }

            @Override
            public void stop() {
                super.stop();
            }
        };
        at.start();

    }

    public void nextStage(ActionEvent e)
    {
        GUI.nextState();
        this.updateCells();
    }


    public void zoomIn(int difference)
    {
        Grid_.setHgap(Grid_.getHgap()+(difference*0.5));
        Grid_.setVgap(Grid_.getVgap()+(difference*0.5));
        int gridSize[];
        gridSize = GUI.getGridSize();

        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {
                Button b = getButtonInGrid(r,c,Grid_);
              //  System.out.println("ZOOM from Grid : "+b.getId());
                b.setScaleY(b.getScaleY()+(difference*0.01));
                b.setScaleX(b.getScaleX()+(difference*0.01));
            }
        }

    }
    public Boolean game_status()
    {
        return game_status;

    }
    public void zoomOut(int difference)
    {

        Grid_.setHgap(Grid_.getHgap()-(difference*0.5));
        Grid_.setVgap(Grid_.getVgap()-(difference*0.5));
        int gridSize[];
        gridSize = GUI.getGridSize();

        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {
                Button b = getButtonInGrid(r,c,Grid_);
              //  System.out.println("ZOOM from Grid : "+b.getId());
                b.setScaleY(b.getScaleY()-(difference*0.01));
                b.setScaleX(b.getScaleX()-(difference*0.01));
            }
        }

    }
    public void speedUp(double factor)
    {
        speedFactor=factor;
        System.out.println(factor);
    }
    public void saveGame(ActionEvent e) throws IOException {
/*        System.out.println("Save Game");
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Save_Scene.fxml"));
        SaveSceneController Controller = new SaveSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.loadGUI(GUI);
        Controller.load_TextDB(TextDatabase);*/

        System.out.println("Save Game");
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Save_Scene.fxml"));
        SaveSceneController Controller = new SaveSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.loadGUI(GUI);
        Controller.load_TextDB(TextDatabase);
        Controller.load_SQLDB(SQLDatabase);
    }
    public void loadGame (ActionEvent e) throws IOException
    {
/*        System.out.println("Load Game");
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Load_Scene.fxml"));
        LoadSceneController Controller = new LoadSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.loadGUI(GUI);
        Controller.setList(TextDatabase);*/

        System.out.println("Load Game");
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Load_Scene.fxml"));
        LoadSceneController Controller = new LoadSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.loadGUI(GUI);
        /*Controller.setList(TextDatabase);*/
        Controller.setListSQL(SQLDatabase);
    }
}