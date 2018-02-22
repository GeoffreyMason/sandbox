package uis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.geoxpm.sandboxgame.MainClass;

import entities.Entity;

/**
 * Created by geoxp on 31/01/2018.
 */

public class Inventory extends Entity {

    private InventorySlot[][] inventorySlots;
    private int slotsWide, slotsHigh;

    public Inventory(float x, float y, float width, float height, int slotsWide, int slotsHigh) {
        super(x, y, width, height);
        this.slotsWide = slotsWide;
        this.slotsHigh = slotsHigh;
        inventorySlots = new InventorySlot[slotsWide][slotsHigh];
        setX(x - ((slotsWide * (width + 2)) - 2) / 2f);
        setY(y - ((slotsHigh * (height + 2)) - 2) / 2f);
        for (int i = 0; i < slotsWide; i++) {
            for (int j = 0; j < slotsHigh; j++) {
                inventorySlots[i][j] = new InventorySlot(getX() + (getWidth() + 2) * i, getY() + (getHeight() + 2) * j, getWidth(), getHeight());
            }
        }
    }

    public void render(MainClass game) {
        for (int i = 0; i < slotsWide; i++) {
            for (int j = 0; j < slotsHigh; j++) {
                inventorySlots[i][j].render(game);
            }
        }
    }

}
