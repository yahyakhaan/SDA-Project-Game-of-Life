package group.gameoflife.DB;
import group.gameoflife.BL.grid;
import group.gameoflife.BL.cell;

import java.io.File;  // Import the File class
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class textDB {

    private textDBController textDBController;

    public textDB(grid grid)
    {
        textDBController=new textDBController(grid);
    }

    public int saveGame(String name) {

        try {
            File myObj = new File("saved.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        try {
            File myObj = new File(name+".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                try
                {
                    FileWriter nameWriter =new FileWriter("saved.txt",true);
                    nameWriter.write(name+".txt"+"\n");
                    nameWriter.close();
                }
                catch (IOException e)
                {

                }
            } else {
                return -1;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(name+".txt");
            myWriter.write(textDBController.getGridSize()[0]+"\n");
            myWriter.write(textDBController.getGridSize()[1]+"\n");

            for(int i=0;i<textDBController.getGridSize()[0];i++)
            {
                for(int j=0;j<textDBController.getGridSize()[1];j++)
                {
                    if(textDBController.isCellAlive(i,j))
                    {
                        myWriter.write(1+" ");
                    }
                    else
                    {
                        myWriter.write(0+" ");
                    }

                }
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 0;
    }

    public String[] savedGamesName(int[] size)
    {
        Scanner input = null;

        String[] savedNames=new String[100];
        int counter=0;

        try {
            input = new Scanner(new File("saved.txt"));
        } catch (Exception ex) {
            System.out.println("Can not open file.");
            System.exit(0);
        }
        while(input.hasNextLine()) {
            String name = input.nextLine();
            savedNames[counter]=name;
            counter++;
        }
        input.close();

        size[0]=counter;
        return savedNames;
    }

    public cell[][] loadGame(String name,int[] size)
    {
        Scanner input=null;

        try {
            input = new Scanner(new File(name));
        } catch (Exception ex) {
            System.out.println("Can not open file.");
            System.exit(0);
        }

        size[0]=input.nextInt();
        size[1]=input.nextInt();

        cell[][] loaded_grid=new cell[size[0]][size[1]];

        for(int i= 0;i<size[0];i++)
        {
            for(int j=0;j<size[1];j++)
            {
                loaded_grid[i][j]=new cell();
            }
        }

        int status;
        for(int i= 0;i<size[0];i++)
        {
            for(int j=0;j<size[1];j++)
            {
                status=input.nextInt();
                if(status==1)
                {
                    loaded_grid[i][j].makeAlive();
                }
            }
        }

        return loaded_grid;
    }

    public int deleteGame(String fileName)
    {
        int[] noofsavedStates=new int[1];
        String savedStates[]=this.savedGamesName(noofsavedStates);

        boolean flag=false;
        for(int i=0;i<noofsavedStates[0];i++)
        {
            if(savedStates[i].equals(fileName))
            {
                flag=true;
            }
        }

        if(flag==false) {
            return -1;
        }

        File myObj = new File(fileName);
        if (myObj.delete()) {
        } else {
            System.out.println("Failed to delete the file.");
        }

        try
        {
            FileWriter nameWriter =new FileWriter("saved.txt");
            for(int i=0;i<noofsavedStates[0];i++)
            {
                if(!(savedStates[i].equals(fileName)))
                {
                    nameWriter.write(savedStates[i]+"\n");
                }
            }
            nameWriter.close();
        }
        catch (IOException e)
        {

        }

        return 1;
    }

    public static void main(String[] args) {
//        grid temp_grid=new grid(10,10);
//        textDB textDB=new textDB(temp_grid);
//        if(textDB.saveGame("testSave3")==-1)
//        {
//         System.out.println("This name is already taken");
//        }
//        int[] noofsavedgames=new int[1];
//        String[] names = textDB.savedGamesName(noofsavedgames);
//
//        for(int i=0;i<noofsavedgames[0];i++)
//        {
//            if(names[i]!=null)
//                System.out.println(names[i]);
//        }

        //       textDB.deleteGame("testSave3.txt");

//        cell[][] temp_grid_1;
//        int[] size=new int[2];
//        temp_grid_1=textDB.loadGame("testSave2.txt",size);
//
//        temp_grid.setSize(size);
//        temp_grid.setCells(temp_grid_1);
//
//        for(int i=0;i<size[0];i++)
//        {
//            for(int j=0;j<size[1];j++)
//            {
//                if(temp_grid.isCellAlive(i,j))
//                {
//                    System.out.print("* ");
//                }
//                else
//                {
//                    System.out.print("_ ");
//                }
//            }
//            System.out.println();
//        }
    }
}
