package group.gameoflife.DB;

import com.google.gson.Gson;
import group.gameoflife.BL.DBinterface;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class SQL_DB_Controller implements DBinterface {

    private grid m_grid;
    public SQL_DB_Controller(grid grid)
    {
        m_grid=grid;
    }

    @Override
    public cell[][] getGrid() throws IOException {
        m_grid.getGrid();
        cell[][] Cells;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        Cells= gson1.fromJson(Reader1, cell[][].class);
        return Cells;
    }

    @Override
    public int[] getGridSize() throws IOException {
        m_grid.getGridSize();
        int[] returnVal = new int[1];
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        returnVal= gson1.fromJson(Reader1, int[].class);
        return returnVal;
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
        m_grid.isCellAlive();
        boolean returnVal;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        returnVal= gson1.fromJson(Reader1, boolean.class);
        return returnVal;
    }
}
