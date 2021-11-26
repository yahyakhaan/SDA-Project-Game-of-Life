package group.gameoflife.BL;

public interface DBinterface{
//    public void saveGame();
//    public void loadGame(cell[] grid);
//    public void deleteState(int stateId);
      public cell[][] getGrid();
      public int[] getGridSize();

      public boolean isCellAlive(int x,int y);
}
