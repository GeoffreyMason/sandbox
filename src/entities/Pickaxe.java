package entities;

import com.badlogic.gdx.graphics.Texture;
import com.geoxpm.sandboxgame.MainClass;

import managers.EntityManager;
import tiles.TileGrid;
import utilities.Clock;
import utilities.Maths;
import utilities.Timer;

/**
 * Created by Geoffrey Mason on 28/01/2018.
 */

public class Pickaxe extends DynamicEntity {

    private DynamicEntity entity;
    private Timer timer;

    public Pickaxe(DynamicEntity entity, TileGrid grid) {
        super(new Texture("pickaxe.png"), entity.getXCentre(), entity.getYCentre(), grid.getTileSize(), grid.getTileSize(), grid);
        this.entity = entity;
        setGravityEffect(0);
        setOrigin(0, 0);
        //setRendering(false);
        setAngle(45);
        timer = new Timer(0.5f);
        timer.swing();
    }

    @Override
    public void update(MainClass game) {
        if (isRendering()) {
            timer.update();
            setX(entity.getXCentre());
            setY(entity.getYCentre());
            int dir = (int) ((entity.getX() - game.mousePosition.x) / Math.abs(entity.getX() - game.mousePosition.x));
            if (dir < 0) {
                if (getAngle() < -90)
                    setAngle(60);
                else
                    setAngle(getAngle() + 500 * Clock.getDeltaTime() * dir);
            } else {
                if (getAngle() > 180)
                    setAngle(30);
                else
                    setAngle(getAngle() + 500 * Clock.getDeltaTime() * dir);
            }
            //setAngle(Maths.cosineInterpolate(90, -90 - 180, timer.getProgress() / 2f));
        } else {
            setAngle(45);
            timer.setTime(0);
        }
    }
}
