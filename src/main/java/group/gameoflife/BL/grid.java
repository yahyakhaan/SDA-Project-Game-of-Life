package group.gameoflife.BL;

import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;

public class grid {

    boolean m_start;
    int m_zoom_level;
    int m_speed_level;
    int m_number_of_states;
    int[] m_size;
    cell[][] m_grid;

    public grid(int x,int y)
    {
        m_size=new int[2];
        m_size[0]=x;
        m_size[1]=y;


        m_grid =new cell[m_size[0]][m_size[1]];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                m_grid[i][j]=new cell();
            }
        }

        m_zoom_level=0;
        m_speed_level=0;
        m_number_of_states=0;
        m_start=false;
    }

    public grid(int x,int y,int speed , int zoom,int number_states)
    {
        m_size=new int[2];
        m_size[0]=x;
        m_size[1]=y;

        m_grid =new cell[m_size[0]][m_size[1]];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                m_grid[i][j]=new cell();
            }
        }

        m_zoom_level =zoom;
        m_speed_level =speed;
        m_number_of_states =number_states;
        m_start=false;
    }

    public void makeCellAlive() throws FileNotFoundException {
        int coordinates[] = new int [2];
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        coordinates= gson1.fromJson(Reader1, int[].class);
        Scanner input = null;
        if(coordinates[0]<m_size[0] && coordinates[1]<m_size[1])
        {
            m_grid[coordinates[0]][coordinates[1]].makeAlive();
        }
    }

    public void makeCellDead() throws FileNotFoundException {
        int coordinates[] = new int [2];
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        coordinates= gson1.fromJson(Reader1, int[].class);
        Scanner input = null;
        if(coordinates[0]<m_size[0] && coordinates[1]<m_size[1])
        {
            m_grid[coordinates[0]][coordinates[1]].makeDead();
        }
    }

    public void isCellAlive() throws IOException {
        int coordinates[] = new int [2];
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        coordinates= gson1.fromJson(Reader1, int[].class);
        boolean returnVal;
        if(coordinates[0]<m_size[0] && coordinates[1]<m_size[1])
        {
            returnVal= m_grid[coordinates[0]][coordinates[1]].isAlive();
            Writer writer =  new FileWriter("output.json");
            Gson gson = new Gson();
            gson.toJson(returnVal,writer);
            writer.flush();
            writer.close();
        }
        else
        {
            returnVal= false;
            Writer writer =  new FileWriter("output.json");
            Gson gson = new Gson();
            gson.toJson(returnVal,writer);
            writer.flush();
            writer.close();
        }
    }

    public void nextState()
    {
        for(int i=0;i<m_size[0];i++)
        {
            for(int j=0;j<m_size[1];j++)
            {
                int no_of_neighbours_alive=0;
                int nei_x=0;
                int nei_y=0;

                nei_x=i-1;
                nei_y=j-1;
                if(nei_x>=0&&nei_y>=0)
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i+1;
                nei_y=j-1;
                if(nei_x<m_size[0]&&nei_y>=0)
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i-1;
                nei_y=j+1;
                if(nei_x>=0&&nei_y<m_size[1])
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i+1;
                nei_y=j+1;
                if(nei_x<m_size[0]&&nei_y<m_size[1])
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i-1;
                nei_y=j;
                if(nei_x>=0)
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i+1;
                nei_y=j;
                if(nei_x<m_size[0])
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i;
                nei_y=j-1;
                if(nei_y>=0)
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i;
                nei_y=j+1;
                if(nei_y<m_size[1])
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                if(m_grid[i][j].isAlive()||m_grid[i][j].isHalfAlive())
                {
                    if(no_of_neighbours_alive<=1||no_of_neighbours_alive>=4)
                    {
                        m_grid[i][j].makeHalfDead();
                    }
                }
                else if(m_grid[i][j].isHalfDead()||m_grid[i][j].isDead())
                {
                    if(no_of_neighbours_alive==3)
                    {
                        m_grid[i][j].makeHalfAlive();
                    }
                }
            }
        }
        this.makeGridFullyAlive();
        this.makeGridFullyDead();
        m_number_of_states++;
    }

    public void getGridSize() throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(m_size,writer);
        writer.flush();
        writer.close();
    }

    public void getGrid() throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(m_grid,writer);
        writer.flush();
        writer.close();
    }

    public void clearGrid()
    {
        for(int i=0;i<m_size[0];i++)
        {
            for(int j=0;j<m_size[1];j++)
            {
                m_grid[i][j].makeDead();
            }
        }

        m_number_of_states=0;
    }

    public void setGridZoom() throws FileNotFoundException {
        int lvl;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        lvl= gson1.fromJson(Reader1, int.class);
        m_zoom_level=lvl;
    }

    public void setGridSpeed() throws FileNotFoundException {
        int lvl;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        lvl= gson1.fromJson(Reader1, int.class);
        m_speed_level=lvl;
    }

    public void getGridZoom() throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(m_zoom_level,writer);
        writer.flush();
        writer.close();
    }

    public void getGridSpeed() throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(m_speed_level,writer);
        writer.flush();
        writer.close();
    }


    public void getNoOfStates() throws IOException {
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(m_number_of_states,writer);
        writer.flush();
        writer.close();

    }

    public void startGame()
    {
        m_start=true;
    }

    public void stopGame()
    {
        m_start=false;
    }

    public void setSize() throws FileNotFoundException {
        int size[] = new int[2];
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        size= gson1.fromJson(Reader1, int[].class);
        m_size=size;
    }

    public void setCells() throws FileNotFoundException {
        cell[][] Cells;
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        Cells= gson1.fromJson(Reader1, cell[][].class);
        m_grid=Cells;
    }

    private void makeGridFullyAlive()
    {
        for(int i=0;i<m_size[0];i++)
        {
            for(int j=0;j<m_size[1];j++)
            {
                if(m_grid[i][j].isHalfAlive())
                {
                    m_grid[i][j].makeAlive();
                }
            }
        }
    }

    private void makeGridFullyDead()
    {
        for(int i=0;i<m_size[0];i++)
        {
            for(int j=0;j<m_size[1];j++)
            {
                if(m_grid[i][j].isHalfDead())
                {
                    m_grid[i][j].makeDead();
                }
            }
        }
    }
}
