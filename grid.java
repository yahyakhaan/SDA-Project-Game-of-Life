public class grid {

    public int arr[][];

    grid(int row,int col)
    {
        arr =new int [row][col];

    }
    
    public void makeAlive(int x,int y)
    {
        arr[x][y]=1;
    }

    void isAlive(int x,int y)
    {
        System.out.println(arr[x][y]);
    }
}
