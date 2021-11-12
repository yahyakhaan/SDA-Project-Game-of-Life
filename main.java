public class main {

    public static void setValue (int[] size) {
        size[0]=10;
        size[1]=20;
    }

    public static void main(String[] args)
    {
        // grid master_grid=new grid(10, 10);
        // gui master_gui=new gui(master_grid);
        // master_gui.makeAlive(1, 1);
        // master_grid.isAlive(1, 1);

        // int x=0,y=0;
        // setValue(x,y);
        // System.out.println(x);

        int size[]=new int[2];
        setValue(size);
        System.out.println(size[0]+" "+size[1]);
    }
    
}
