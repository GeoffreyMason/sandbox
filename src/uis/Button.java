package uis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Entity;

/**
 * Created by Geoffrey Mason on 21/01/2018.
 */

public class Button extends Entity {

    private Texture texture;

    public Button(float x, float y, float width, float height) {
        super(x, y, width, height);
        texture = new Texture("buttonMine.png");
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
