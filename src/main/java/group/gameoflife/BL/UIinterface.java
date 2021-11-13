package group.gameoflife.BL;

public interface UIinterface{
    
    public void makeCellALive(int x,int y);
    public void makeCellDead(int x,int y);
    public boolean isCellAlive(int x, int y);

    public void nextState();
    public int[] getGridSize(); //Grid = int x[0] * int y[1]
    public cell[][] getGrid();
    public void clearGrid();

    public void setGridZoom(int zoom_level);
    public void setGridSpeed(int grid_speed);
    public int  getGridZoom();
    public int  getGridSpeed();
    public int  getNoOfStates();

    public void startGame();
    public void stopGame();

    public void saveGame();
    public cell[][] loadGame(int id);
    public void deleteState(int id);
}
