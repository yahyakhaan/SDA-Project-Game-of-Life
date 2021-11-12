package group.gameoflife.UI;

import group.gameoflife.BL.UIinterface;
import group.gameoflife.BL.cell;

public class Graphical_UI implements UIinterface {
    @Override
    public void makeCellALive(int x,int y){}
    @Override
    public void makeCellDead(int x,int y){}
    @Override
    public boolean isCellAlive(){
        boolean value = true;
        return value;
    }
    @Override
    public void nextState(){}
    @Override
    public void getGridSize(int[] gridSize){}
    @Override
    public cell[][] getGrid(){
        cell[][] grid = new cell[10][10];
        return grid;
    }
    @Override
    public void clearGrid(){}
    @Override
    public void setGridZoom(){}
    @Override
    public void setGridSpeed(){}
    @Override
    public int  getGridZoom(){
        int value = 1;
        return value;
    }
    @Override
    public int  getGridSpeed(){
        int value = 1;
        return value;
    }
    @Override
    public int  getNoOfStates(){
        int value = 1;
        return value;
    }
    @Override
    public void startGame(){}
    @Override
    public void stopGame(){}
    @Override
    public void saveGame(){}
    @Override
    public cell[][] loadGame(int id){
        cell[][] grid = new cell[10][10];
        return grid;
    }
    @Override
    public void deleteState(int id){}

}
