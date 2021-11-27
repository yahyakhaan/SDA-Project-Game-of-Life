package group.gameoflife.DB;

import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL_DB {

    private SQL_DB_Controller SQL_DB_Controller;
    public SQL_DB(grid grid)
    {
        SQL_DB_Controller=new SQL_DB_Controller(grid);
    }

    public int saveState(String name)
    {
        int status;

        String[] checkIfAlreadyExists=viewStates();
        for(int i=0;i<checkIfAlreadyExists.length;i++)
        {
            if(checkIfAlreadyExists[i]==name)
            {
                return -1;
            }
        }
        try
        {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/GameOfLifeDB","root","123456");
            Statement statement=connection.createStatement();
            String query="";
            int isAlive=0;

            for(int i=0;i<SQL_DB_Controller.getGridSize()[0];i++)
            {
                for(int j=0;j<SQL_DB_Controller.getGridSize()[1];j++)
                {
                    if(SQL_DB_Controller.isCellAlive(i,j))
                    {
                        isAlive=1;
                    }
                    else
                    {
                        isAlive=0;
                    }
                    query="call saveState('"+name+"'"+","+i+","+j+","+isAlive+","+SQL_DB_Controller.getGridSize()[0]+","+SQL_DB_Controller.getGridSize()[1]+");";
                    statement.executeQuery(query);
                }
            }

            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteState(String name)
    {
        int status=-1;
        String[] checkIfAlreadyExists=viewStates();
        for(int i=0;i<checkIfAlreadyExists.length;i++)
        {
            if(checkIfAlreadyExists[i]==name)
            {
                status=0;
            }
        }
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameOfLifeDB", "root", "123456");
            Statement statement = connection.createStatement();
            statement.executeQuery("call deleteState ('"+name+"');");
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return status;
    }

    public String[] viewStates(int[] size)
    {
        int counter=0;
        String[] savedStateList=new String[100];
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameOfLifeDB", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet=statement.executeQuery("call viewState");
            while(resultSet.next())
            {
                savedStateList[counter]=resultSet.getString(1);
                counter++;
            }
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        size[0]=counter;
        return savedStateList;
    }

    public cell[][] loadState(String name,int size[])
    {
        cell[][] loaded_grid = new cell[0][];
        try
        {
            int MaxRowSize,MaxColSize;

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameOfLifeDB", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("call loadState ('"+name+"');");

            resultSet.next();

            MaxRowSize=resultSet.getInt("MaxRowSize");
            MaxColSize=resultSet.getInt("MaxColSize");

            size[0]=MaxRowSize;
            size[1]=MaxColSize;

            loaded_grid=new cell[MaxRowSize][MaxColSize];

            for(int i= 0;i<MaxRowSize;i++)
            {
                for(int j=0;j<MaxColSize;j++)
                {
                    loaded_grid[i][j]=new cell();
                }
            }

            ResultSet loadStateGrid = statement.executeQuery("call loadState ('"+name+"');");
            int isAlive;

            for(int i= 0;i<MaxRowSize;i++)
            {
                for(int j=0;j<MaxColSize;j++)
                {
                    while(loadStateGrid.next())
                    {
                        isAlive=loadStateGrid.getInt("isAlive");
                        if(isAlive==1)
                        {
                            loaded_grid[i][j].makeAlive();
                        }
                    }
                }
            }
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return loaded_grid;
    }
}
