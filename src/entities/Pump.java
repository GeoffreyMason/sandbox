package entities;

import com.badlogic.gdx.graphics.Texture;
import com.geoxpm.sandboxgame.MainClass;

import managers.EntityManager;
import tiles.TileGrid;
import utilities.Maths;
import utilities.Timer;

/**
 * Created by Geoffrey Mason on 28/01/2018.
 */

public class Pump extends DynamicEntity {

    private Texture pumpTrigger;
    private Timer timer;
    private float triggerWidth, triggerHeight;

    public Pump(Texture texture, float x, float y, float width, float height, TileGrid grid) {
        super(texture, x, y, width, height, grid);
        pumpTrigger = new Texture("pumpTrigger.png");
        triggerWidth = width;
        triggerHeight = height / 2f;
        timer = new Timer(0.8f);
        timer.swing();
    }

    @Override
    public void update(MainClass game) {
        timer.update();
        game.spriteBatch.draw(pumpTrigger, getX(), Maths.cosineInterpolate(getY() + getHeight(), getY() + getHeight() - triggerHeight * 3f / 4f, timer.getProgress()), triggerWidth, triggerHeight);
    }
}
