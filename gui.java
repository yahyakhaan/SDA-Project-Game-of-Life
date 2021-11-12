import BL.UIinterface;

public class gui implements uiInterface {

    grid ui_grid;

    gui(grid grid_obj)
    {
        ui_grid=grid_obj;
    }

    @Override
    public void makeAlive(int x, int y) {
        ui_grid.makeAlive(x, y);
    }
}