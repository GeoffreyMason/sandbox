package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geoxpm.sandboxgame.MainClass;

import components.ExplosionComponent;
import items.Item;
import managers.EntityManager;
import particles.ParticleSystem;
import screens.TestScreen;
import tiles.TileGrid;
import utilities.Timer;

/**
 * Created by Geoffrey Mason on 25/01/2018.
 */

public class Dynamite extends DynamicEntity {

    private Timer timer;
    private ExplosionComponent explosionComponent;
    private int explosionRadius, explosionPower;
    private ParticleSystem trailParticles;
    private Texture iconTexture;

    public Dynamite(float x, float y, TileGrid grid) {
        super(new Texture("itemDynamite.png"), x, y, 5, 16, grid);
        explosionComponent = new ExplosionComponent(grid, this);
        trailParticles = new ParticleSystem(grid, 1, 1, 40);
        timer = new Timer(2);
        explosionRadius = 5;
        explosionPower = 10;
        setRotationSpeed(500);
        addComponent(explosionComponent);
        addParticleEffect(trailParticles);
        iconTexture = new Texture("itemDynamite.png");
        setUseable(true);
    }

    public void update(MainClass game) {
        trailParticles.emit(getX(), getY() + getHeight());
        if (getxVelocity() >= 0)
            setRotationSpeed(-Math.abs(getRotationSpeed()));
        else
            setRotationSpeed(Math.abs(getRotationSpeed()));
        if (timer.isCompleted()) {
            explosionComponent.explode(game.testScreen.entityManager, explosionRadius, explosionPower);
            die();
        }
        timer.update();
    }

    public void use() {

    }
}
