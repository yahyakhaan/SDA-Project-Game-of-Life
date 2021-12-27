package group.gameoflife.DB;

import com.google.gson.Gson;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SQL_DB {

    private SQL_DB_Controller SQL_DB_Controller;
    public SQL_DB(grid grid)
    {
        SQL_DB_Controller=new SQL_DB_Controller(grid);
    }
    public void giveSize() throws IOException {
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        String name = gson1.fromJson(Reader1, String.class);


        int size[] = new int[2];
        cell[][] loaded_grid=null;// = new cell[0][];
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

            loaded_grid = new cell[size[0]][size[1]];

            System.out.println("Grid Size:"+size[0]+":"+size[1]);
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
                    loadStateGrid.next();

                    isAlive=loadStateGrid.getInt("isAlive");
                    if(isAlive==1)
                    {
                        loaded_grid[i][j].makeAlive();
                    }

                }
            }
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(size,writer);
        writer.flush();
        writer.close();

    }

    public void saveState() throws IOException {
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        String name = gson1.fromJson(Reader1, String.class);
        int returnVal;
        int status;
        int[] noOfSavedStates = new int[1];
        String[] checkIfAlreadyExists=viewStatesPrivate(noOfSavedStates);
        for(int i=0;i<noOfSavedStates[0];i++)
        {
            if(checkIfAlreadyExists[i].equals(name))
            {
                System.out.println("Game with this name already exists!");
                returnVal=-1;
                Writer writer =  new FileWriter("output.json");
                Gson gson = new Gson();
                gson.toJson(returnVal,writer);
                writer.flush();
                writer.close();
                return;
            }
        }
        try
        {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/GameOfLifeDB","root","123456");
            Statement statement=connection.createStatement();
            System.out.println("saveState connected");
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
            System.out.println("Game Saved!");
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        returnVal= 0;
        Writer writer2 =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(returnVal,writer2);
        writer2.flush();
        writer2.close();
    }

    public void deleteState() throws FileNotFoundException {
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        String name = gson1.fromJson(Reader1, String.class);

        int status=-1;
        int[] noOfSavedStates = new int[1];
        String[] checkIfAlreadyExists=viewStatesPrivate(noOfSavedStates);
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
        return;
    }
    public void giveNo_ofSavedGames() throws IOException {
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
        int size[] = new int[1];
        size[0] = counter;
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(size,writer);
        writer.flush();
        writer.close();
    }

    public void viewStates() throws IOException {

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
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(savedStateList,writer);
        writer.flush();
        writer.close();

    }

    private String[] viewStatesPrivate(int[] size)
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

    public void loadState() throws IOException {
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        String name = gson1.fromJson(Reader1, String.class);

        cell[][] loaded_grid;
        int size[] = new int [2];
        try
        {
            int MaxRowSize,MaxColSize;

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GameOfLifeDB", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("call loadState ('"+name+"')");

            resultSet.next();

            MaxRowSize=resultSet.getInt("MaxRowSize");
            MaxColSize=resultSet.getInt("MaxColSize");

            size[0]=MaxRowSize;
            size[1]=MaxColSize;

            loaded_grid = new cell[size[0]][size[1]];

            System.out.println("Grid Size:"+size[0]+":"+size[1]);
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
                    loadStateGrid.next();

                        isAlive=loadStateGrid.getInt("isAlive");
                        if(isAlive==1)
                        {
                            loaded_grid[i][j].makeAlive();
                        }

                }
            }

            Writer writer =  new FileWriter("output.json");
            Gson gson = new Gson();
            gson.toJson(loaded_grid,writer);
            writer.flush();
            writer.close();

            connection.close();
        }
        catch (Exception e)
        {

            e.printStackTrace();
        }


    }
}