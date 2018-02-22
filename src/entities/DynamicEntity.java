package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.geoxpm.sandboxgame.MainClass;

import java.util.ArrayList;
import java.util.List;

import components.Component;
import particles.ParticleSystem;
import tiles.TileGrid;
import utilities.Clock;
import utilities.Maths;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public abstract class DynamicEntity extends Entity {

    public final float GRAVITY = -800, FRICTION = 0.85f;

    private float xVelocity, yVelocity, xAcceleration, yAcceleration, gravityEffect = 1, jumpPower, angle, rotationSpeed, alpha = 1, originX, originY;
    private TileGrid grid;
    private boolean grounded, flushLeft, flushRight, collideable, jump, alive, rendering;
    private Texture texture;
    private List<Component> componentList;
    private List<ParticleSystem> particleSystemList;
    private Vector2 gravityDirection;

    public DynamicEntity(Texture texture, float x, float y, float width, float height, TileGrid grid) {
        super(x, y, width, height);
        this.texture = texture;
        this.grid = grid;
        collideable = true;
        alive = true;
        componentList = new ArrayList<Component>();
        particleSystemList = new ArrayList<ParticleSystem>();
        originX = 0.5f;
        originY = 0.5f;
        rendering = true;
        gravityDirection = new Vector2(0, 1);
    }

    public void movement() {
        if (grid.getGravitySource() != null) {
            gravityDirection.set(Maths.normalisedDirection(getXCentre(), getYCentre(), grid.getGravitySource().x, grid.getGravitySource().y));
        }
        float dt = Clock.getDeltaTime();
        float friction = grid.getTile(getXCentre(), getYCentre()).getType().getFrictionCoefficient();
        yVelocity *= friction;
        if (grounded) {
            if (FRICTION < friction)
                friction = FRICTION;
        }
        xVelocity *= friction;
        rotationSpeed *= Maths.clamp(friction + 0.06f, 0, 1);
        if (Math.abs(xVelocity) < 0.01f)
            xVelocity = 0;
        if (Math.abs(yVelocity) < 0.01f)
            yVelocity = 0;
        if (Math.abs(rotationSpeed) < 0.01f) {
            rotationSpeed = 0;
        }
        xVelocity += GRAVITY * gravityEffect * gravityDirection.x * dt;
        yVelocity += GRAVITY * gravityEffect * gravityDirection.y * dt;
        if (jump) {
            //if (grounded) {
            yVelocity = jumpPower;
            jump = false;
            //}
        }

        if (!collideable || !grid.detectTerrainCollisionX(this)) {
            setX(getX() + xVelocity * dt);
        } else {
            xVelocity = -xVelocity / 1.8f;
            rotationSpeed = -rotationSpeed / 1.8f;
        }
        if (!collideable || !grid.detectTerrainCollisionY(this))
            setY(getY() + yVelocity * dt);
        else
            yVelocity = -yVelocity / 1.8f;
    }

    public void render(MainClass game) {
        if (alive) {
            update(game);
            if (rendering) {
                angle += rotationSpeed * Clock.getDeltaTime();
                movement();
                Color colour = game.spriteBatch.getColor();
                game.spriteBatch.setColor(colour.r, colour.g, colour.b, getAlpha());
                game.spriteBatch.draw(texture, getX(), getY(), getWidth() * originX, getHeight() * originY, getWidth(), getHeight(), 1, 1, angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
                game.spriteBatch.setColor(colour);
            }
        }

        for (Component component : componentList) {
            component.update(game);
        }

        for (ParticleSystem particleSystem : particleSystemList) {
            particleSystem.render(game);
        }
    }

    public abstract void update(MainClass game);

    public void jump(float jumpPower) {
        this.jumpPower = jumpPower;
        jump = true;
    }

    public void impulse(float xVelocity, float yVelocity) {
        this.xVelocity += xVelocity;
        this.yVelocity += yVelocity;
    }

    public void impulseAngle(float speed, float angle) {
        this.xVelocity += (float) (Math.cos(angle) * speed);
        this.yVelocity += (float) (Math.sin(angle) * speed);
    }

    public void attract(DynamicEntity entity) {
        int direction = 0;
        if (entity.getX() > getX())
            direction = -1;
        else if (entity.getX() < getX())
            direction = 1;
        entity.setxVelocity(entity.getxVelocity() + 10 * direction);
        direction = 0;
        if (entity.getY() > getY())
            direction = -1;
        else if (entity.getY() < getY())
            direction = 1;
        entity.setyVelocity(entity.getxVelocity() + 10 * direction);
    }

    public void addComponent(Component component) {
        componentList.add(component);
    }

    public void addParticleEffect(ParticleSystem particleSystem) {
        particleSystemList.add(particleSystem);
    }

    public void die() {
        alive = false;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public float getGravityEffect() {
        return gravityEffect;
    }

    public void setGravityEffect(float gravityEffect) {
        this.gravityEffect = gravityEffect;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isCollideable() {
        return collideable;
    }

    public void setCollideable(boolean collideable) {
        this.collideable = collideable;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean isAlive() {
        if (alive)
            return true;
        for (Component component : componentList) {
            if (component.isAlive())
                return true;
        }
        for (ParticleSystem particleSystem : particleSystemList) {
            if (particleSystem.isAlive())
                return true;
        }
        return false;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public TileGrid getGrid() {
        return grid;
    }

    public boolean isFlushLeft() {
        return flushLeft;
    }

    public void setFlushLeft(boolean flushLeft) {
        this.flushLeft = flushLeft;
    }

    public boolean isFlushRight() {
        return flushRight;
    }

    public void setFlushRight(boolean flushRight) {
        this.flushRight = flushRight;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }

    public boolean isRendering() {
        return rendering;
    }

    public void setRendering(boolean rendering) {
        this.rendering = rendering;
    }
}
