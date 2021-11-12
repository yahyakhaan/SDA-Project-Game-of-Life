package BL;

public interface DBinterface{
    public void saveGame();
    public void loadGame(cell[] grid);
    public void deleteState(int stateId);
}
