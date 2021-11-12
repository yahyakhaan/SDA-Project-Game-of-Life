package group.gameoflife.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainSceneController {
    private Graphical_UI GUI;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("This is game of Life!");
    }

    public void loadGUI(Graphical_UI GUI)
    {
        this.GUI=GUI;
    }
}