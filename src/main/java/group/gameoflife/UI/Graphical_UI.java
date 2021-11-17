package group.gameoflife.UI;

import group.gameoflife.BL.UIinterface;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;

public class Graphical_UI implements UIinterface {

    private grid game;
    public Graphical_UI(grid game)
    {
            this.game=game;
    }
    @Override
    public void makeCellALive(int x,int y){
        game.makeCellAlive(x,y);
    }
    @Override
    public void makeCellDead(int x,int y){
        game.makeCellDead(x,y);
    }
    @Override
    public boolean isCellAlive(int x,int y){
        boolean value = game.isCellAlive(x,y);
        return value;
    }
    @Override
    public void nextState(){
        game.nextState();
    }
    @Override
    public int[] getGridSize(){
        return game.getGridSize();
    }
    @Override
    public cell[][] getGrid(){
        return game.getGrid();
    }
    @Override
    public void clearGrid(){
        game.clearGrid();
    }
    @Override
    public void setGridZoom(int zoom_level){
        game.setGridZoom(zoom_level);
    }
    @Override
    public void setGridSpeed(int speed_level){
        game.setGridSpeed(speed_level);
    }
    @Override
    public int  getGridZoom(){
        return game.getGridZoom();
    }
    @Override
    public int  getGridSpeed(){
        return game.getGridSpeed();
    }
    @Override
    public int  getNoOfStates(){
        return game.getNoOfStates();
    }
    @Override
    public void startGame(){
        game.startGame();
    }
    @Override
    public void stopGame(){
        game.stopGame();
    }
    @Override
    public void saveGame(){
        game.saveGame();
    }
    @Override
    public cell[][] loadGame(int id){
        return game.loadGame(id);
    }
    @Override
    public void deleteState(int id){
        game.deleteGame(id);
    }
}
