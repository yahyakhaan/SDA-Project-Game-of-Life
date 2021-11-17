package group.gameoflife.UI;

import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;

public class ConsoleUIController {

    private Console_UI ui_controller;

    public ConsoleUIController(grid console_grid)
    {
        ui_controller=new Console_UI(console_grid);
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
}
