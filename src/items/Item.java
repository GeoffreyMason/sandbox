package items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geoxpm.sandboxgame.MainClass;

import entities.DynamicEntity;
import managers.EntityManager;
import tiles.TileGrid;
import utilities.Maths;
import utilities.Timer;

/**
 * Created by Geoffrey Mason on 18/01/2018.
 */

public class Item extends DynamicEntity {

    private Timer timer;
    private boolean immune, useable;
    private int amount;
    private DynamicEntity entity;

    public Item(Texture texture, float x, float y, TileGrid grid) {
        super(texture, x - grid.getTileSize() / 2, y - grid.getTileSize() / 2, grid.getTileSize() / 2, grid.getTileSize() / 2, grid);
        //impulse(Maths.random(-10, 10), 50);
        timer = new Timer(0.5f);
        amount = 1;
    }

    public Item(Texture texture, float x, float y, float width, float height, TileGrid grid) {
        super(texture, x - 2, y - 2, width, height, grid);
        //impulse(Maths.random(-10, 10), 50);
        timer = new Timer(0.5f);
        amount = 1;
    }

    public void update(MainClass game) {
        if (immune)
            timer.update();
        if (timer.isCompleted())
            immune = false;
    }

    public boolean isImmune() {
        return immune;
    }

    public void setImmune(boolean immune) {
        this.immune = immune;
        timer.setTime(0);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isUseable() {
        return useable;
    }

    public DynamicEntity getEntity() {
        return entity;
    }
}
