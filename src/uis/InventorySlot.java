package uis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.geoxpm.sandboxgame.MainClass;

import entities.DynamicEntity;
import entities.Entity;
import items.Item;

/**
 * Created by geoxp on 31/01/2018.
 */

public class InventorySlot extends Entity {

    private Texture texture;
    private Item item;
    private int itemAmount;
    private GlyphLayout itemAmountLayout, itemInfoLayout;

    public InventorySlot(float x, float y, float width, float height) {
        super(x, y, width, height);
        texture = new Texture("hotbarIcon.png");
        itemAmountLayout = new GlyphLayout();
        itemInfoLayout = new GlyphLayout();
    }

    public void render(MainClass game) {
        game.spriteBatch.draw(texture, getX(), getY(), getWidth(), getHeight());
        if (item != null) {
            itemAmount = item.getAmount();
            game.spriteBatch.draw(item.getTexture(), getX() + getWidth() * 1 / 8f , getY() + getHeight() * 1 / 8f, getWidth() * 3 / 4f, getHeight() * 3 / 4f);
            itemAmountLayout.setText(game.font, Integer.toString(itemAmount), Color.WHITE, getWidth(), Align.right, false);
            game.font.draw(game.spriteBatch, itemAmountLayout, getX(), getY() + itemAmountLayout.height);
            if (isHoveredOver(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()))) {
                itemInfoLayout.setText(game.font, item.getTexture().toString(), Color.WHITE, getWidth(), Align.left, false );
                game.font.draw(game.spriteBatch, itemInfoLayout, Gdx.input.getX() + 16, Gdx.graphics.getHeight() - Gdx.input.getY());
            }
        }
        if (itemAmount == 0) {
            item = null;
        }
    }

    public void addItem(Item item) {
        itemAmount += item.getAmount();
        this.item = item;
        item.setAmount(itemAmount);
    }

    public Item getItem() {
        return item;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void removeItem() {
        item = null;
        itemAmount = 0;

    }
}
