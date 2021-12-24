package group.gameoflife.UI;

//import java.util.concurrent.;
import java.lang.Thread;
import java.io.*;
import java.util.concurrent.TimeUnit;

import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;
import group.gameoflife.DB.textDB;
import group.gameoflife.DB.SQL_DB;

import java.util.Scanner;

public class ConsoleUIController {

    private Console_UI ui_controller;
    private textDB text_db;
    private SQL_DB sql_db;

    public ConsoleUIController(grid console_grid)
    {
        ui_controller=new Console_UI(console_grid);
        text_db=new textDB(console_grid);
        sql_db=new SQL_DB(console_grid);
    }

    public void printGrid()
    {
        for(int i=0;i<ui_controller.getGridSize()[0];i++)
        {
            for(int j=0;j<ui_controller.getGridSize()[1];j++)
            {
                if(ui_controller.isCellAlive(i,j))
                {
                    System.out.print("* ");
                }
                else
                {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    public void makeCellAlive()
    {
        int x,y;
        Scanner input_scanner = new Scanner(System.in);
        System.out.print("Enter Row Number: ");
        x=input_scanner.nextInt();
        System.out.print("Enter Column Number: ");
        y=input_scanner.nextInt();
        ui_controller.makeCellALive(x,y);
    }

    public void makeCellDead()
    {
        int x,y;
        Scanner input_scanner = new Scanner(System.in);
        System.out.print("Enter Row Number: ");
        x=input_scanner.nextInt();
        System.out.print("Enter Column Number: ");
        y=input_scanner.nextInt();
        ui_controller.makeCellDead(x,y);
    }

    public int menu()
    {
        System.out.println("1-> Make A Cell Alive");
        System.out.println("2-> Make A Cell Dead");
        System.out.println("3-> Next State");
        System.out.println("4-> Start Game");
        System.out.println("5-> Increase Speed");
        System.out.println("6-> Decrease Speed");
        System.out.println("7-> Save Game");
        System.out.println("8-> Load Game");
        System.out.println("9-> Delete Game");
        System.out.println("0-> Exit");
        System.out.print("Select an option: ");
        int option=0;
        Scanner input_scanner = new Scanner(System.in);
        option = input_scanner.nextInt();
        return option;
    }

    public void setSpeed()
    {
        if(ui_controller.getGridSpeed()==0)
        {
            ui_controller.setGridSpeed(1);
        }
    }

    public void increaseSpeed()
    {
        if(ui_controller.getGridSpeed()!=1)
        {
            ui_controller.setGridSpeed(ui_controller.getGridSpeed()-1);
        }
    }

    public void decreaseSpeed()
    {
        if(ui_controller.getGridSpeed()!=10)
        {
            ui_controller.setGridSpeed(ui_controller.getGridSpeed()+1);
        }
    }

    public void saveGame()
    {
        System.out.print("Enter Name: ");
        String name;
        Scanner input_scanner = new Scanner(System.in);
        name = input_scanner.nextLine();
        if(/*text_db.saveGame(name)==-1*/ sql_db.saveState(name)==-1)
        {
            System.out.println("A Game with this name already exists. Use a different name");
        }
        else {
            System.out.println("Saved Successfully");
        }
    }

    public void loadGame()
    {
        int size[]=new int[2];
        cell loaded_grid[][];
        String name;

        name=this.listSavedGames();

        if(name!=null)
        {
            //loaded_grid = text_db.loadGame(name,size);
            loaded_grid=sql_db.loadState(name,size);
            ui_controller.setSize(size);
            ui_controller.setGrid(loaded_grid);
        }
        else{
            System.out.println("There are no saved states");
        }
    }

    public void deleteState()
    {
        String name;
        name=this.listSavedGames();

        if(name!=null)
        {
            //text_db.deleteGame(name);
            sql_db.deleteState(name);
        }
        else{
            System.out.println("There are no saved states");
        }
    }

    public String listSavedGames()
    {
        String[] savedGames;
        int[] noOfSavedGames=new int[1];

        //savedGames=text_db.savedGamesName(noOfSavedGames);
        savedGames=sql_db.viewStates(noOfSavedGames);

        if(noOfSavedGames[0]==0)
        {
            return null;
        }

        for(int i=0;i<noOfSavedGames[0];i++)
        {
            System.out.println(i+1+"-> "+savedGames[i]);
        }

        System.out.print("Select a saved state: ");
        int number;
        Scanner input_scanner = new Scanner(System.in);
        number = input_scanner.nextInt();

        if(number>=1&&number<=noOfSavedGames[0])
        {
            return savedGames[number-1];
        }
        else {
            return null;
        }
    }

    public void stratGame() throws IOException {
        ui_controller.startGame();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for(int x=0;x<=ui_controller.getGridSize()[0];x++)
                    {
                        for(int y=0;y<ui_controller.getGridSize()[1];y++)
                        {
                            System.out.println(System.lineSeparator().repeat(100));
                        }
                    }
                    ui_controller.nextState();
                    printGrid();
                    try {
                        TimeUnit.SECONDS.sleep(ui_controller.getGridSpeed());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        t.start();
        System.in.read();
        t.stop();
    }

    public void clear() {

        for(int i = 0; i <=32; i++) {
            System.out.println();
        }
    }

    public void game() throws IOException {
        int option=1;
        this.setSpeed();
        do {
            if(option>=1&&option<=8)
            {
                    this.clear();
                this.printGrid();
                option = this.menu();
            }

            if(option==1)
            {
                this.makeCellAlive();
            }
            else if(option==2)
            {
                this.makeCellDead();
            }
            else if(option==3) {
                ui_controller.nextState();
            }
            else if(option==4)
            {
                this.stratGame();
            }
            else if(option==5)
            {
                this.increaseSpeed();
            }
            else if(option==6)
            {
                this.decreaseSpeed();
            }
            else if(option==7)
            {
                this.saveGame();
            }
            else if(option==8)
            {
                this.loadGame();
            }
            else if(option==9)
            {
                this.deleteState();
            }
        }while (option!=0);
    }

    public static void main(String[] args) throws IOException {
        grid consoleGrid=new grid(10,10);
        ConsoleUIController consoleGame=new ConsoleUIController(consoleGrid);
        consoleGame.game();
    }
}
