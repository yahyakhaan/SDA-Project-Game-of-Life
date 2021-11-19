package group.gameoflife.UI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

public class MainSceneController {
    private int Grid_Hgap;
    private int Grid_Vgap;
    private double Grid_Xscale;
    private double Grid_Yscale;
    private int check;
    private boolean game_status;
    private  GridPane Grid_;
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

    public void loadGUI(Graphical_UI GUI)
    {
        check =1;
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
                System.out.println("Tracked from Grid : "+b.getId());
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
        System.out.println(check);
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
    public void start(ActionEvent e)
    {
        Grid_Hgap=5;
        Grid_Vgap=5;
        if (game_status==false) {

            GUI.startGame();
            GUI.clearGrid();
            loadBlankGrid();
            welcomeText.setText("Game Started");


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
            this.game_status = true;


        }
        else {

            GUI.startGame();
            GUI.clearGrid();
            updateCells();
            welcomeText.setText("Game Started");
            this.game_status = true;


        }
    }
    public void stop(ActionEvent e)
    {
        GUI.stopGame();
        this.game_status=false;
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
}