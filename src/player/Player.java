package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.geoxpm.sandboxgame.MainClass;

import components.ExplosionComponent;
import entities.DynamicEntity;
import entities.Pickaxe;
import managers.EntityManager;
import player.Nameplate;
import tiles.TileGrid;
import utilities.Maths;
import utilities.Timer;

/**
 * Created by Geoffrey Mason on 17/01/2018.
 */

public class Player extends DynamicEntity {

    private float speed, acceleration, jumpPower, jumpCount;
    private Nameplate nameplate;
    private Timer timer;
    private ExplosionComponent explosionComponent;

    public Player(TileGrid grid) {
        super(new Texture("companionCube.png"), grid.getTilesWide() * grid.getTileSize() / 2f, (grid.getTilesHigh() - 6) * grid.getTileSize(), 16, 16, grid);
        speed = grid.getTileSize() * 6;
        acceleration = speed / 4f;
        jumpPower = speed * 3;
        nameplate = new Nameplate("GeoX_", this);
        timer = new Timer(1);
        explosionComponent = new ExplosionComponent(grid, this);
    }

    public void update(MainClass game) {
        //float angle = getAngle();
        //setAngle(-90 + (float) Math.toDegrees(Math.atan((getYCentre() - getGrid().getGravitySource().y) / (getXCentre() - getGrid().getGravitySource().x))));
        timer.update();
        Vector3 cameraPosition = game.camera.position;
        cameraPosition.lerp(new Vector3(Maths.clamp(getXCentre(), game.WIDTH / 2, getGrid().getWorldWidth() - game.WIDTH / 2), Maths.clamp(getYCentre(), game.HEIGHT / 2, getGrid().getWorldHeight() - game.HEIGHT / 2), 0), 0.1f);
        //game.camera.rotateAround(new Vector3(getXCentre(), getYCentre(), 0), new Vector3(0, 0, 1), angle - getAngle());
        explosionComponent.update(game);
        //nameplate.render(game);
    }

    public ExplosionComponent getExplosionComponent() {
        return explosionComponent;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getSpeed() {
        return speed;
    }

    public float getJumpPower() {
        return jumpPower;
    }
}
