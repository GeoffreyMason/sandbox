package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Geoffrey Mason on 27/01/2018.
 */

public class Bar extends Entity {

    private Texture foreGround, backGround;
    private float total, currentTotal;

    public Bar(float x, float y, float width, float height, float total) {
        super(x, y, width, height);
        this.total = total;
        currentTotal = total / 2f;
        foreGround = new Texture("foreGround.png");
        backGround = new Texture("backGround.png");
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(backGround, getX(), getY(), getWidth(), getHeight());
        spriteBatch.draw(foreGround, getX(), getY(), getWidth() * currentTotal / total, getHeight());
    }

}
