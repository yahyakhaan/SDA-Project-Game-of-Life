package group.gameoflife.TEXTDB_GUI;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSceneController {

    private double speedFactor;
    private Stage stage;
    private boolean game_status; //game_start or game_stop
    private  GridPane Grid_; //will contain all cells
    private textDB TextDatabase; //File Database
    private Graphical_UI GUI; //Implementation of GUI_Interface
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane plane;
    @FXML
    private Label titleLabel;
    @FXML
    private Slider zoomSlider;
    @FXML
    private Slider speedSlider;
    @FXML
    private Label noOfStage;
    @FXML

    private int[] returnButtonId(String s) //takes String as Cell's ID and return Col No and Row No
    {
        String[] ID = s.split(":");
        int[] ID_ = new int[2];
        ID_[0]=Integer.parseInt(ID[0]);
        ID_[1]=Integer.parseInt(ID[1]);
        return ID_;
    }

    private Button getButtonInGrid ( int r,  int c, GridPane gridPane) //returns the button(cell) at RowNo and ColNo on Grid
    {
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
    } //initialize File Database

    public void loadGUI(Graphical_UI GUI) throws IOException //Initialize GUI_Object
    {

        this.GUI=GUI;
        int[] gridSize= GUI.getGridSize();

    }
    @FXML
    private void updateCells() throws IOException //Updates the Cells on Screen (UI) from GUI.Grid
    {
        noOfStage.setText(String.valueOf(GUI.getNoOfStates()));
        int gridSize[];
        gridSize = GUI.getGridSize();
        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {
                    Button b = getButtonInGrid(r,c,Grid_);

                int [] IDs = this.returnButtonId(b.getId());
                Boolean check = GUI.isCellAlive(IDs[0],IDs[1]);
                if (check == true)
                {
                    b.setStyle("-fx-background-color: #FFFFFF; -fx-border-color:#55031F ; -fx-border-width:2; ");
                }
                else b.setStyle("-fx-background-color: #51515A; -fx-border-color:#55031F ; -fx-border-width:2;");
            }
        }

    }


    private void loadBlankGrid() throws IOException // Creates a blank grid on screen
    {

        int[] gridSize;
        gridSize = this.GUI.getGridSize();
        Grid_ = new GridPane();
        Grid_.setStyle("-fx-background-color: #090510;");
        Grid_.setPadding(new Insets(5));
        Grid_.setHgap(5);
        Grid_.setVgap(5);


        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {

                Button button = new Button(String.valueOf("  "));

                button.setId(Integer.toString(r)+":"+Integer.toString(c));
                button.setStyle("-fx-background-color: #51515A; -fx-border-color:#55031F ; -fx-border-width:2;");

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int[] buttonID;
                        String BID_Str;
                        Button button_ = (Button) actionEvent.getSource();
                        BID_Str= button_.getId();
                        buttonID = returnButtonId(BID_Str);

                        Boolean Alive = null;
                        try {
                            Alive = GUI.isCellAlive(buttonID[0],buttonID[1]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (Alive==false) {
                            //Button Selected

                            try {
                                GUI.makeCellALive(buttonID[0],buttonID[1]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                updateCells();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                           //Button Deselected

                            try {
                                GUI.makeCellDead(buttonID[0],buttonID[1]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                updateCells();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                Grid_.add(button, c, r);
            }
        }
        this.scrollPane.setContent(Grid_);

        //updateCells();

    }
    public void clearStage(ActionEvent e) throws IOException //Clear grid
    {
        GUI.clearGrid();
        updateCells();
    }

    public void setGamefromGUI(Graphical_UI GUI) throws IOException //Load a game from GUI_Object
    {
        this.GUI=GUI;
        int[] gridSize= GUI.getGridSize();

        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setFitToWidth(true);
        GUI.startGame();
        loadBlankGrid();
        updateCells();
        titleLabel.setText("Game of Life");

        zoomSlider.setValue(45);
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                //System.out.println("Old Value: " + oldValue.intValue() + "New Value: " + newValue.intValue()/* +"Multiple: " + multiple*/);
                if (oldValue.intValue() - newValue.intValue() < 0) {
                    try {
                        zoomIn(newValue.intValue() - oldValue.intValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (oldValue.intValue() - newValue.intValue() > 0) {
                    try {
                        zoomOut(oldValue.intValue() - newValue.intValue());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        speedUp(1.5);
        speedSlider.setValue(1.5);
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                // System.out.println("Old Value: " + oldValue.intValue() + "New Value: " + newValue.intValue()/* +"Multiple: " + multiple*/);

                speedUp(newValue.doubleValue());

            }
        });
        this.game_status = false;
    }
    public void game_set() throws IOException //Create a new game (after reseting all parameters)
    {


        this.scrollPane.setFitToHeight(true);
        this.scrollPane.setFitToWidth(true);
        GUI.startGame();
        GUI.clearGrid();
        loadBlankGrid();
        titleLabel.setText("Game of Life");

        zoomSlider.setValue(45);
        zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {

        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            //System.out.println("Old Value: " + oldValue.intValue() + "New Value: " + newValue.intValue()/* +"Multiple: " + multiple*/);
            if (oldValue.intValue() - newValue.intValue() < 0) {
                try {
                    zoomIn(newValue.intValue() - oldValue.intValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (oldValue.intValue() - newValue.intValue() > 0) {
                try {
                    zoomOut(oldValue.intValue() - newValue.intValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        });
        speedUp(1.5);
        speedSlider.setValue(1.5);
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
               // System.out.println("Old Value: " + oldValue.intValue() + "New Value: " + newValue.intValue()/* +"Multiple: " + multiple*/);

                   speedUp(newValue.doubleValue());

            }
        });
        this.game_status = false;

    }
    public void start(ActionEvent e) throws IOException //start the game loop
    {
           if (game_status()==false) {
               GUI.startGame();
               //  GUI.clearGrid();
               updateCells();
               // titleLabel.setText("Game Started");
               this.game_status = true;
               gameLoop();
           }
    }
    public void stop(ActionEvent e) //stops the game loop
    {
        GUI.stopGame();
        this.game_status=false;
    }

    private void gameLoop() //game loop
    {

        final long[] startnanoTime = {System.nanoTime()}; //previous time snap
        AnimationTimer at = new AnimationTimer() //sets up a time difference for next cycle of gameLoop
        {
            @Override
            public void handle(long l) {
                //compare previous time snap with current
                if (l - (long) startnanoTime[0] >= speedFactor*1e9) {
                    //run if its time to update cells
                    startnanoTime[0] = l;
                    GUI.nextState();
                    try {
                        updateCells();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //if game is stopped
                if (game_status()==false)
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
        at.start();//run start method in Animation timer

    }

    public void nextStage(ActionEvent e) throws IOException // go to next stage if game is not in loop
    {
        if(game_status()==false) {
            GUI.nextState();
            this.updateCells();
        }
    }


    private void zoomIn(int difference) throws IOException {
        //scale up the gaps around the cells in grid
        Grid_.setHgap(Grid_.getHgap()+(difference*0.5));
        Grid_.setVgap(Grid_.getVgap()+(difference*0.5));
        int gridSize[];
        gridSize = GUI.getGridSize();
        //scale up individual elements
        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {
                Button b = getButtonInGrid(r,c,Grid_);
                b.setScaleY(b.getScaleY()+(difference*0.01));
                b.setScaleX(b.getScaleX()+(difference*0.01));
            }
        }

    }
    private Boolean game_status() //return game status
    {
        return game_status;

    }
    private void zoomOut(int difference) throws IOException {
        //scale down the gaps around the cells in grid
        Grid_.setHgap(Grid_.getHgap()-(difference*0.5));
        Grid_.setVgap(Grid_.getVgap()-(difference*0.5));
        int gridSize[];
        gridSize = GUI.getGridSize();
        //scale down individual cells
        for (int r = 0; r < gridSize[0]; r++) {
            for (int c = 0; c < gridSize[1]; c++) {
                Button b = getButtonInGrid(r,c,Grid_);
              //  System.out.println("ZOOM from Grid : "+b.getId());
                b.setScaleY(b.getScaleY()-(difference*0.01));
                b.setScaleX(b.getScaleX()-(difference*0.01));
            }
        }

    }
    private void speedUp(double factor) //change speed factor
    {
        speedFactor=factor;
    }

    public void saveGameInTextDB(ActionEvent e) throws IOException {

        //SAVE GAME IMPLEMENTATION FOR TEXT-FILE DATABASE
        //....
        ActionEvent stopGame = new ActionEvent();
        this.stop(stopGame);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Save_Scene_TEXTDB.fxml"));
        TextDBSaveSceneController Controller = new TextDBSaveSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        scene.getStylesheets().add(main.class.getResource("Stylesheet.css").toExternalForm());
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.loadGUI(GUI);
        Controller.load_TextDB(TextDatabase);
        //....
        //ENDS HERE


    }

    public void loadGameFromTextDB (ActionEvent e) throws IOException
    {
        //LOAD GAME IMPLEMENTATION FOR SQL DATABASE
        //...
        ActionEvent stopGame = new ActionEvent();
        this.stop(stopGame);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Load_Scene_TEXTDB.fxml"));
        TextDBLoadSceneController Controller = new TextDBLoadSceneController();
        fxmlLoader.setController(Controller);
        Scene scene = new Scene(fxmlLoader.load(), 970, 730);
        scene.getStylesheets().add(main.class.getResource("Stylesheet.css").toExternalForm());
        stage.setTitle("Game Of Life");
        stage.setScene(scene);
        stage.show();
        Controller.loadGUI(GUI);
        Controller.load_TextDB(TextDatabase);

        //...
        //ENDS HERE

    }
}