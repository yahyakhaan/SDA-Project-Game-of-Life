package group.gameoflife.UI;

import com.google.gson.Gson;
import group.gameoflife.BL.UIinterface;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Graphical_UI implements UIinterface {

    private grid game;
    public Graphical_UI(grid game)
    {
            this.game=game;
    }

    @Override
    public void setSize(int[] size) throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(size,writer);
        writer.flush();
        writer.close();
        game.setSize();
    }

    @Override
    public void setGrid(cell[][] grid) throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(grid,writer);
        writer.flush();
        writer.close();
        game.setCells();
    }


    @Override
    public void makeCellALive(int x,int y) throws IOException {
        int coordinates[] = new int [2];
        coordinates[0]=x;
        coordinates[1]=y;
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(coordinates,writer);
        writer.flush();
        writer.close();
        game.makeCellAlive();
    }
    @Override
    public void makeCellDead(int x,int y) throws IOException {
        int coordinates[] = new int [2];
        coordinates[0]=x;
        coordinates[1]=y;
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(coordinates,writer);
        writer.flush();
        writer.close();
        game.makeCellDead();
    }
    @Override
    public boolean isCellAlive(int x,int y) throws IOException {
        int coordinates[] = new int [2];
        coordinates[0]=x;
        coordinates[1]=y;
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(coordinates,writer);
        writer.flush();
        writer.close();
        game.isCellAlive();
        boolean returnVal;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        returnVal= gson1.fromJson(Reader1, boolean.class);
        return returnVal;
    }
    @Override
    public void nextState(){
        game.nextState();
    }
    @Override
    public int[] getGridSize() throws IOException {
        game.getGridSize();
        int[] returnVal = new int[1];
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        returnVal= gson1.fromJson(Reader1, int[].class);
        return returnVal;
    }
    @Override
    public cell[][] getGrid() throws IOException {
        game.getGrid();
        cell[][] Cells;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        Cells= gson1.fromJson(Reader1, cell[][].class);
        return Cells;
    }
    @Override
    public void clearGrid(){
        game.clearGrid();
    }
    @Override
    public void setGridZoom(int zoom_level) throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(zoom_level,writer);
        writer.flush();
        writer.close();
        game.setGridZoom();
    }
    @Override
    public void setGridSpeed(int speed_level) throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(speed_level,writer);
        writer.flush();
        writer.close();
        game.setGridSpeed();
    }
    @Override
    public int  getGridZoom() throws IOException {
        game.getGridZoom();
        FileReader Reader1=new FileReader("output.json");
        Gson gson1 = new Gson();
        int lvl= gson1.fromJson(Reader1, int.class);
        return lvl;
    }
    @Override
    public int  getGridSpeed() throws IOException {
        game.getGridSpeed();
        FileReader Reader1=new FileReader("output.json");
        Gson gson1 = new Gson();
        int lvl= gson1.fromJson(Reader1, int.class);
        return lvl;
    }
    @Override
    public int  getNoOfStates() throws IOException {
        game.getNoOfStates();
        FileReader Reader1=new FileReader("output.json");
        Gson gson1 = new Gson();
        int noOfStates= gson1.fromJson(Reader1, int.class);
        return noOfStates;
    }
    @Override
    public void startGame(){
        game.startGame();
    }
    @Override
    public void stopGame(){
        game.stopGame();
    }

}
