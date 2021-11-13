package group.gameoflife.BL;

public class grid {

    boolean m_start;
    int m_zoom_level;
    int m_speed_level;
    int m_number_of_states;
    int[] m_size;
    cell[][] m_grid;

    public grid(int x,int y)
    {
        m_size=new int[2];
        m_size[0]=x;
        m_size[1]=y;


        m_grid =new cell[m_size[0]][m_size[1]];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                m_grid[i][j]=new cell();
            }
        }

        m_zoom_level=0;
        m_speed_level=0;
        m_number_of_states=0;
        m_start=false;
    }

    public grid(int x,int y,int speed , int zoom,int number_states)
    {
        m_size=new int[2];
        m_size[0]=x;
        m_size[1]=y;

        m_grid =new cell[m_size[0]][m_size[1]];
        for(int i=0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                m_grid[i][j]=new cell();
            }
        }

        m_zoom_level =zoom;
        m_speed_level =speed;
        m_number_of_states =number_states;
        m_start=false;
    }

    public void makeCellAlive(int x,int y)
    {
        if(x<m_size[0] && y<m_size[1])
        {
            m_grid[x][y].makeAlive();
        }
    }

    public void makeCellDead(int x,int y)
    {
        if (x < m_size[0] && y < m_size[1]) {
            m_grid[x][y].makeDead();
        }
    }

    public boolean isCellAlive(int x,int y)
    {
        if(x<m_size[0] && y<m_size[1])
        {
            return m_grid[x][y].isAlive();
        }
        else
        {
            return false;
        }
    }

    public void nextState()
    {
        for(int i=0;i<m_size[0];i++)
        {
            for(int j=0;j<m_size[1];j++)
            {
                int no_of_neighbours_alive=0;
                int nei_x=0;
                int nei_y=0;

                nei_x=i-1;
                nei_y=j-1;
                if(nei_x>=0&&nei_y>=0)
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i+1;
                nei_y=j-1;
                if(nei_x<m_size[0]&&nei_y>=0)
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i-1;
                nei_y=j+1;
                if(nei_x>=0&&nei_y<m_size[1])
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i+1;
                nei_y=j+1;
                if(nei_x<m_size[0]&&nei_y<m_size[1])
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i-1;
                nei_y=j;
                if(nei_x>=0)
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i+1;
                nei_y=j;
                if(nei_x<m_size[0])
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i;
                nei_y=j-1;
                if(nei_y>=0)
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                nei_x=i;
                nei_y=j+1;
                if(nei_y<m_size[1])
                {
                    if(m_grid[nei_x][nei_y].isAlive()||m_grid[nei_x][nei_y].isHalfDead())
                    {
                        no_of_neighbours_alive++;
                    }
                }

                if(m_grid[i][j].isAlive()||m_grid[i][j].isHalfAlive())
                {
                    if(no_of_neighbours_alive<=1||no_of_neighbours_alive>=4)
                    {
                        m_grid[i][j].makeHalfDead();
                    }
                }
                else if(m_grid[i][j].isHalfDead()||m_grid[i][j].isDead())
                {
                    if(no_of_neighbours_alive==3)
                    {
                        m_grid[i][j].makeHalfAlive();
                    }
                }
            }
        }
        this.makeGridFullyAlive();
        this.makeGridFullyDead();
        m_number_of_states++;
    }

    public int[] getGridSize()
    {
        return m_size;
    }

    public cell[][] getGrid()
    {
        return  m_grid;
    }

    public void clearGrid()
    {
        for(int i=0;i<m_size[0];i++)
        {
            for(int j=0;j<m_size[1];j++)
            {
                m_grid[i][j].makeDead();
            }
        }
    }

    public void setGridZoom(int zoom_level)
    {
        m_zoom_level=zoom_level;
    }

    public void setGridSpeed(int speed_level)
    {
        m_speed_level=speed_level;
    }

    public int getGridZoom()
    {
        return m_zoom_level;
    }

    public int getGridSpeed()
    {
        return m_speed_level;
    }


    public int getNoOfStates()
    {
        return m_number_of_states;
    }

    public void startGame()
    {
        m_start=true;
    }

    public void stopGame()
    {
        m_start=false;
    }

    public void saveGame()
    {

    }

    public cell[][] loadGame(int id)
    {
        return null;
    }

    public void deleteGame(int id)
    {

    }

    private void makeCellHalfAlive(int x,int y)
    {
        if(x<m_size[0] && y<m_size[1])
        {
            m_grid[x][y].makeHalfAlive();
        }
    }

    private void makeGridFullyAlive()
    {
        for(int i=0;i<m_size[0];i++)
        {
            for(int j=0;j<m_size[1];j++)
            {
                if(m_grid[i][j].isHalfAlive())
                {
                    m_grid[i][j].makeAlive();
                }
            }
        }
    }

    private void makeGridFullyDead()
    {
        for(int i=0;i<m_size[0];i++)
        {
            for(int j=0;j<m_size[1];j++)
            {
                if(m_grid[i][j].isHalfDead())
                {
                    m_grid[i][j].makeDead();
                }
            }
        }
    }
}
