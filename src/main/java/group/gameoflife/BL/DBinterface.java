package group.gameoflife.BL;

import java.io.IOException;

public interface DBinterface{
//    public void saveGame();
//    public void loadGame(cell[] grid);
//    public void deleteState(int stateId);
      public cell[][] getGrid() throws IOException;
      public int[] getGridSize() throws IOException;

      public boolean isCellAlive(int x,int y) throws IOException;
}
