package group.gameoflife.DB;

import group.gameoflife.BL.DBinterface;
import group.gameoflife.BL.cell;
import group.gameoflife.BL.grid;


public class textDBController implements DBinterface {

    private grid m_grid;
    public textDBController(grid grid)
    {
        m_grid=grid;
    }

    @Override
    public cell[][] getGrid() {
        return m_grid.getGrid();
    }

    @Override
    public int[] getGridSize() {
        return m_grid.getGridSize();
    }

    @Override
    public boolean isCellAlive(int x, int y) {
        return m_grid.isCellAlive(x,y);
    }
}
