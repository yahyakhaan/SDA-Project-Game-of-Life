package group.gameoflife.UI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;

public class MainSceneController {
    private int check;
    private  GridPane Grid_;
    private Graphical_UI GUI;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane plane;
    @FXML
    private Label welcomeText;

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
                    b.setStyle("-fx-background-color: #00FF00;");
                }
                else b.setStyle("-fx-background-color: #FFF;");
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
                button.setId(Integer.toString(r)+":"+Integer.toString(c));
                button.setStyle("-fx-background-color: #FFF;");
                System.out.println(button.getId());
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int buttonID;
                        if (button.getStyle()=="-fx-background-color: #FFF;") {
                            //Button Selected
                            Button b = (Button) actionEvent.getSource();
                            System.out.println(b.getId() + " IS CLICKED");
                            b.setStyle("-fx-background-color: #00FF00;");
                            int [] IDs = returnButtonId(b.getId());
                            GUI.makeCellALive(IDs[0],IDs[1]);
                        }
                        else if (button.getStyle()=="-fx-background-color: #00FF00;") {
                           //Button Deselected
                            Button b = (Button) actionEvent.getSource();
                            System.out.println(b.getId() + " IS CLICKED");
                            b.setStyle("-fx-background-color: #FFF;");
                            int [] IDs = returnButtonId(b.getId());
                            GUI.makeCellDead(IDs[0],IDs[1]);
                        }
                    }
                });
                Grid_.add(button, c, r);
            }
        }
        this.scrollPane.setContent(Grid_);
        updateCells();

    }
    public void start(ActionEvent e)
    {
        welcomeText.setText("Game Started");
        loadBlankGrid();

    }
    public void nextStage(ActionEvent e)
    {
        GUI.nextState();
        this.updateCells();
    }
}