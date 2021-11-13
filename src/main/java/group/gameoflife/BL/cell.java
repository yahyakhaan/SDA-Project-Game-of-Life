package group.gameoflife.BL;

public class cell {

    int state;

    public cell()
    {
        state=0;
    }

    public void makeAlive()
    {
        state=3;
    }

    public void makeHalfAlive(){state=2;}

    public void makeHalfDead(){state=1;}

    public void makeDead()
    {
        state=0;
    }

    public boolean isAlive()
    {
        if(state==3)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isDead()
    {
        if(state==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isHalfAlive()
    {
        if(state==2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public boolean isHalfDead()
    {
        if(state==1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
