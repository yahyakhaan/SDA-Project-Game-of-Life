package group.gameoflife.BL;

public class cell {

    boolean state;

    public cell()
    {
        state=false;
    }

    public void makeAlive()
    {
        state=true;
    }

    public void makeDead()
    {
        state=false;
    }

    public boolean isAlive()
    {
        if(state)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
