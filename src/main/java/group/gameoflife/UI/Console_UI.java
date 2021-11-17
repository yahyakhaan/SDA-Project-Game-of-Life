package group.gameoflife.UI;

import group.gameoflife.BL.UIinterface;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;

public class Console_UI implements UIinterface
{

    private grid m_console_grid;

    public Console_UI(grid console_grid)
    {
        m_console_grid=console_grid;
    }

    @Override
    public void makeCellALive(int x, int y) {
        m_console_grid.makeCellAlive(x,y);
    }

    @Override
    public void makeCellDead(int x, int y) {
        m_console_grid.makeCellDead(x,y);
    }

    @Override
    public boolean isCellAlive(int x, int y) {
        return m_console_grid.isCellAlive(x,y);
    }

    @Override
    public int[] getGridSize() {
        return m_console_grid.getGridSize();
    }

    @Override
    public cell[][] getGrid() {
        return m_console_grid.getGrid();
    }

    @Override
    public void nextState() {
        m_console_grid.nextState();
    }

    @Override
    public void clearGrid(){
        m_console_grid.clearGrid();
    }
    @Override
    public void setGridZoom(int zoom_level){
        m_console_grid.setGridZoom(zoom_level);
    }
    @Override
    public void setGridSpeed(int speed_level){
        m_console_grid.setGridSpeed(speed_level);
    }
    @Override
    public int  getGridZoom(){
        return m_console_grid.getGridZoom();
    }
    @Override
    public int  getGridSpeed(){
        return m_console_grid.getGridSpeed();
    }
    @Override
    public int  getNoOfStates(){
        return m_console_grid.getNoOfStates();
    }
    @Override
    public void startGame(){
        m_console_grid.startGame();
    }
    @Override
    public void stopGame(){
        m_console_grid.stopGame();
    }
    @Override
    public void saveGame(){
        m_console_grid.saveGame();
    }
    @Override
    public cell[][] loadGame(int id) {
        return m_console_grid.loadGame(id);

    }
    @Override
    public void deleteState(int id){

        m_console_grid.deleteGame(id);
    }
}
