package group.gameoflife.DB;
import com.google.gson.Gson;
import group.gameoflife.BL.grid;
import group.gameoflife.BL.cell;

import java.io.*;
import java.util.Scanner;

public class textDB {

    private textDBController textDBController;

    public textDB(grid grid) {
        textDBController = new textDBController(grid);
    }
    public void giveSize() throws IOException {
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        String name = gson1.fromJson(Reader1, String.class);
        Scanner input = null;

        try {
            input = new Scanner(new File(name));
        } catch (Exception ex) {
            System.out.println("Can not open file.");
            System.exit(0);
        }
        int size[] = new int [2];
        size[0] = input.nextInt();
        size[1] = input.nextInt();
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(size,writer);
        writer.flush();
        writer.close();

    }
    public void saveGame() throws IOException {
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        String name = gson1.fromJson(Reader1, String.class);

        int returnVal;

        try {
            File myObj = new File("saved.txt");
            if (myObj.createNewFile()) {

            } else {

            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        try {
            File myObj = new File(name + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                try {
                    FileWriter nameWriter = new FileWriter("saved.txt", true);
                    nameWriter.write(name + ".txt" + "\n");
                    nameWriter.close();
                } catch (IOException e) {

                }
            } else {
                returnVal=-1;
                Writer writer =  new FileWriter("output.json");
                Gson gson = new Gson();
                gson.toJson(returnVal,writer);
                writer.flush();
                writer.close();
                return;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(name + ".txt");
            myWriter.write(textDBController.getGridSize()[0] + "\n");
            myWriter.write(textDBController.getGridSize()[1] + "\n");

            for (int i = 0; i < textDBController.getGridSize()[0]; i++) {
                for (int j = 0; j < textDBController.getGridSize()[1]; j++) {
                    if (textDBController.isCellAlive(i, j)) {
                        myWriter.write(1 + " ");
                    } else {
                        myWriter.write(0 + " ");
                    }

                }
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        returnVal= 0;
        Writer writer2 =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(returnVal,writer2);
        writer2.flush();
        writer2.close();

    }
    public void giveNo_ofSavedGames() throws IOException {
        Scanner input = null;

        String[] savedNames = new String[100];
        int counter = 0;

        try {
            input = new Scanner(new File("saved.txt"));
        } catch (Exception ex) {
            System.out.println("Can not open file.");
            System.exit(0);
        }
        while (input.hasNextLine()) {
            String name = input.nextLine();
            savedNames[counter] = name;
            counter++;
        }
        input.close();
        int size[] = new int[1];
        size[0] = counter;
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(size,writer);
        writer.flush();
        writer.close();
    }
    public void savedGamesName() throws IOException {
        Scanner input = null;

        String[] savedNames = new String[100];
        int counter = 0;

        try {
            input = new Scanner(new File("saved.txt"));
        } catch (Exception ex) {
            System.out.println("Can not open file.");
            System.exit(0);
        }
        while (input.hasNextLine()) {
            String name = input.nextLine();
            savedNames[counter] = name;
            counter++;
        }
        input.close();
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(savedNames,writer);
        writer.flush();
        writer.close();


    }

    private String[] savedGamesNamePrivate(int[] size) {
        Scanner input = null;

        String[] savedNames = new String[100];
        int counter = 0;

        try {
            input = new Scanner(new File("saved.txt"));
        } catch (Exception ex) {
            System.out.println("Can not open file.");
            System.exit(0);
        }
        while (input.hasNextLine()) {
            String name = input.nextLine();
            savedNames[counter] = name;
            counter++;
        }
        input.close();

        size[0] = counter;

        return savedNames;

    }



    public void loadGame() throws IOException {
        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        String name = gson1.fromJson(Reader1, String.class);

        int size[]= new int[2];
        Scanner input = null;

        try {
            input = new Scanner(new File(name));
        } catch (Exception ex) {
            System.out.println("Can not open file.");
            System.exit(0);
        }

        size[0] = input.nextInt();
        size[1] = input.nextInt();

        cell[][] loaded_grid = new cell[size[0]][size[1]];

        for (int i = 0; i < size[0]; i++) {
            for (int j = 0; j < size[1]; j++) {
                loaded_grid[i][j] = new cell();
            }
        }

        int status;
        for (int i = 0; i < size[0]; i++) {
            for (int j = 0; j < size[1]; j++) {
                status = input.nextInt();
                if (status == 1) {
                    loaded_grid[i][j].makeAlive();
                }
            }
        }
        Writer writer =  new FileWriter("output.json");
        Gson gson = new Gson();
        gson.toJson(loaded_grid,writer);
        writer.flush();
        writer.close();

    }

    public void deleteGame() throws FileNotFoundException {

        Gson gson1 = new Gson();
        FileReader Reader1=new FileReader("output.json");
        String fileName = gson1.fromJson(Reader1, String.class);

        int[] noofsavedStates = new int[1];
        String savedStates[] = this.savedGamesNamePrivate(noofsavedStates);

        boolean flag = false;
        for (int i = 0; i < noofsavedStates[0]; i++) {
            if (savedStates[i].equals(fileName)) {
                flag = true;
            }
        }

        if (flag == false) {
            return;
        }

        File myObj = new File(fileName);
        if (myObj.delete()) {
        } else {
            System.out.println("Failed to delete the file.");
        }

        try {
            FileWriter nameWriter = new FileWriter("saved.txt");
            for (int i = 0; i < noofsavedStates[0]; i++) {
                if (!(savedStates[i].equals(fileName))) {
                    nameWriter.write(savedStates[i] + "\n");
                }
            }
            nameWriter.close();
        } catch (IOException e) {

        }
        return;
    }
}