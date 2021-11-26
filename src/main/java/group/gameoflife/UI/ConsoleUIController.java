package group.gameoflife.UI;

//import java.util.concurrent.;
import java.lang.Thread;
import java.io.*;
import java.util.concurrent.TimeUnit;

import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;
import group.gameoflife.DB.textDB;

import java.util.Scanner;

public class ConsoleUIController {

    private Console_UI ui_controller;
    private textDB text_db;

    public ConsoleUIController(grid console_grid)
    {
        ui_controller=new Console_UI(console_grid);
        text_db=new textDB(console_grid);
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
        System.out.println(name);
    }

    public void stratGame() throws IOException {
        ui_controller.startGame();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for(int x=0;x<=ui_controller.getGridSize()[0];x++)
                    {
                        System.out.println();
                    }
                    System.out.flush();
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

    public void game() throws IOException {
        int option=1;
        this.setSpeed();
        do {
            if(option>=1&&option<=3)
                this.printGrid();

            option = this.menu();
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
        }while (option!=0);
    }

    public static void main(String[] args) throws IOException {
        grid consoleGrid=new grid(10,10);
        ConsoleUIController consoleGame=new ConsoleUIController(consoleGrid);
        consoleGame.game();
    }

}
