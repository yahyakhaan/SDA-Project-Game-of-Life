package group.gameoflife.UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
    protected void onHelloButtonClick() {
        welcomeText.setText("This is game of Life!");
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
                Grid_.getChildren();

            }
        }

    }
    public void clickOnButton(ActionEvent e)
    {
        int buttonID;
        Button b = (Button)e.getSource();
        System.out.println(b.getId()+" IS CLICKED");

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
                        }
                        else if (button.getStyle()=="-fx-background-color: #00FF00;") {
                           //Button Deselected
                            Button b = (Button) actionEvent.getSource();
                            System.out.println(b.getId() + " IS CLICKED");
                            b.setStyle("-fx-background-color: #FFF;");
                        }
                    }
                });
                Grid_.add(button, c, r);
            }
        }
        //this.scrollpane.setScaleY(10);
      //  plane.setVisible(false);
        this.scrollPane.setContent(Grid_);
        //this.scrollPane.();



    }
    public void start(ActionEvent e)
    {
        welcomeText.setText("Game Started");
        loadBlankGrid();

    }
    public void doit(ActionEvent e)
    {
        welcomeText.setText("GG");

    }
    public void nextStage(ActionEvent e)
    {


    }
}