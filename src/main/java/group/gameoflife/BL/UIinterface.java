package group.gameoflife.BL;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface UIinterface{

    public void setSize(int[] size) throws IOException;
    public void setGrid(cell[][] grid) throws IOException;
    
    public void makeCellALive(int x,int y) throws IOException;
    public void makeCellDead(int x,int y) throws IOException;
    public boolean isCellAlive(int x, int y) throws IOException;

    public void nextState();
    public int[] getGridSize() throws IOException; //Grid = int x[0] * int y[1]
    public cell[][] getGrid() throws IOException;
    public void clearGrid();

    public void setGridZoom(int zoom_level) throws IOException;
    public void setGridSpeed(int grid_speed) throws IOException;
    public int  getGridZoom() throws IOException;
    public int  getGridSpeed() throws IOException;
    public int  getNoOfStates() throws IOException;

    public void startGame();
    public void stopGame();
}
