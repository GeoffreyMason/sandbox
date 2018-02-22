package uis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.geoxpm.sandboxgame.MainClass;

import entities.DynamicEntity;
import entities.Entity;
import items.Item;

/**
 * Created by Geoffrey Mason on 27/01/2018.
 */

public class Hotbar extends Entity {

    private InventorySlot[] inventorySlots;
    private InventorySlot selectedSlot;
    private Texture selectedIconTexture;

    public Hotbar(float x, float y, float width, float height) {
        super(x, y, width, height);
        inventorySlots = new InventorySlot[9];
        setX(x - ((inventorySlots.length * (width + 2)) - 2) / 2f);
        for (int i = 0; i < inventorySlots.length; i++) {
            inventorySlots[i] = new InventorySlot(getX() + (getWidth() + 2) * i, getY(), getWidth(), getHeight());
        }
        selectedSlot = inventorySlots[0];
        selectedIconTexture = new Texture("hotbarIconBorder.png");
    }

    public void render(MainClass game) {
        for (int i = 0; i < inventorySlots.length; i++) {
            if (inventorySlots[i].isJustTouched(game.testScreen.staticMousePosition)) {
                game.testScreen.playerInput.setHeldItem(inventorySlots[i].getItem());
                inventorySlots[i].removeItem();
            }
            /*if (inventorySlots[i] == selectedSlot)
                game.spriteBatch.draw();*/
            inventorySlots[i].render(game);
            if (inventorySlots[i] == selectedSlot)
                game.spriteBatch.draw(selectedIconTexture, selectedSlot.getX(), selectedSlot.getY(), selectedSlot.getWidth(), selectedSlot.getHeight());
        }
    }

    public void addItem(Item item) {
        for (int i = 0; i < inventorySlots.length; i++) {
            InventorySlot inventorySlot = inventorySlots[i];
            if (inventorySlot.getItem() == null) {
                inventorySlot.addItem(item);
                return;
            } else if (inventorySlot.getItem().getTexture() == item.getTexture()) {
                inventorySlot.addItem(item);
                return;
            }
        }
    }

    public void setSelectedSlotNumber(int selectedSlotNumber) {
        selectedSlot = inventorySlots[selectedSlotNumber];
    }

    public InventorySlot getSelectedSlot() {
        return selectedSlot;
    }
}
