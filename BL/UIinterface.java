package BL;

public interface UIinterface{
    
    public void makeCellALive(int x,int y);
    public void makeCellDead(int x,int y);
    public boolean isCellAlive();

    public void nextState();
    public void getGridSize(int[] gridSize);
    public cell[] getGrid();
    public void clearGrid();

    public void setGridZoom();
    public void setGridSpeed();
    public int  getGridZoom();
    public int  getGridSpeed();
    public int  getNoOfStates();

    public void startGame();
    public void stopGame();

    public void saveGame();
    public cell[] loadGame(int id);
    public void deleteState(int id);
}
