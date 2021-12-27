package group.gameoflife.UI;

import com.google.gson.Gson;
import group.gameoflife.BL.UIinterface;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;

import java.io.*;

public class Console_UI implements UIinterface
{

    private grid m_console_grid;

    public Console_UI(grid console_grid)
    {
        m_console_grid=console_grid;
    }

    @Override
    public void setSize(int[] size) throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(size,writer);
        writer.flush();
        writer.close();
        m_console_grid.setSize();
    }

    @Override
    public void setGrid(cell[][] grid) throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(grid,writer);
        writer.flush();
        writer.close();
        m_console_grid.setCells();
    }

    @Override
    public void makeCellALive(int x, int y) throws IOException {
        int coordinates[] = new int [2];
        coordinates[0]=x;
        coordinates[1]=y;
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(coordinates,writer);
        writer.flush();
        writer.close();
        m_console_grid.makeCellAlive();
    }

    @Override
    public void makeCellDead(int x, int y) throws IOException {
        int coordinates[] = new int [2];
        coordinates[0]=x;
        coordinates[1]=y;
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(coordinates,writer);
        writer.flush();
        writer.close();
        m_console_grid.makeCellDead();
    }

    @Override
    public boolean isCellAlive(int x, int y) throws IOException {
        int coordinates[] = new int [2];
        coordinates[0]=x;
        coordinates[1]=y;
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(coordinates,writer);
        writer.flush();
        writer.close();
        m_console_grid.isCellAlive();
        boolean returnVal;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        returnVal= gson1.fromJson(Reader1, boolean.class);
        return returnVal;
    }

    @Override
    public int[] getGridSize() throws IOException {
        m_console_grid.getGridSize();
        int[] returnVal = new int[1];
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        returnVal= gson1.fromJson(Reader1, int[].class);
        return returnVal;

    }

    @Override
    public cell[][] getGrid() throws IOException {

        m_console_grid.getGrid();
        cell[][] Cells;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        Cells= gson1.fromJson(Reader1, cell[][].class);
        return Cells;
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
    public void setGridZoom(int zoom_level) throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(zoom_level,writer);
        writer.flush();
        writer.close();
        m_console_grid.setGridZoom();
    }
    @Override
    public void setGridSpeed(int speed_level) throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(speed_level,writer);
        writer.flush();
        writer.close();
        m_console_grid.setGridSpeed();
    }
    @Override
    public int  getGridZoom() throws IOException {
        m_console_grid.getGridZoom();
        FileReader Reader1=new FileReader("output.json");
        Gson gson1 = new Gson();
        int lvl= gson1.fromJson(Reader1, int.class);
        return lvl;
    }
    @Override
    public int  getGridSpeed() throws IOException {
        m_console_grid.getGridSpeed();
        FileReader Reader1=new FileReader("output.json");
        Gson gson1 = new Gson();
        int lvl= gson1.fromJson(Reader1, int.class);
        return lvl;
    }
    @Override
    public int  getNoOfStates() throws IOException {
        m_console_grid.getNoOfStates();
        FileReader Reader1=new FileReader("output.json");
        Gson gson1 = new Gson();
        int noOfStates= gson1.fromJson(Reader1, int.class);
        return noOfStates;
    }
    @Override
    public void startGame(){
        m_console_grid.startGame();
    }
    @Override
    public void stopGame(){
        m_console_grid.stopGame();
    }
}
