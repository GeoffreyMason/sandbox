package particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.geoxpm.sandboxgame.MainClass;

import entities.DynamicEntity;
import entities.Entity;
import tiles.TileGrid;
import utilities.Clock;
import utilities.Timer;

/**
 * Created by Geoffrey Mason on 20/01/2018.
 */

public class Particle extends Entity {

    private Color colour;
    private Timer timer;
    private boolean alive;
    private float xVelocity, yVelocity;

    public Particle(float x, float y, float radius, float r, float g, float b, float lifespan, TileGrid grid) {
        super(x, y, radius, radius);
        colour = new Color(r, g, b, 0);
        timer = new Timer(lifespan);
        alive = true;
    }

    public void render(MainClass game) {
        setX(getX() + xVelocity * Clock.getDeltaTime());
        setY(getY() + yVelocity * Clock.getDeltaTime());
        xVelocity *= 0.94f;
        yVelocity *= 0.94f;
        if (timer.isCompleted())
            alive = false;
        game.spriteBatch.end();
        game.shapeRenderer.setColor(colour);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.circle(getX(), getY(), getWidth() * (1 - timer.getProgress()));
        game.shapeRenderer.end();
        game.spriteBatch.begin();
        timer.update();
    }

    public void impulse(float xVelocity, float yVelocity) {
        this.xVelocity += xVelocity;
        this.yVelocity += yVelocity;
    }

    public boolean isAlive() {
        return alive;
    }
}
