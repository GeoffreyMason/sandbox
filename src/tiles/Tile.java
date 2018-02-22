package tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import entities.Entity;
import managers.EntityManager;
import utilities.Clock;
import utilities.Maths;
import utilities.SpriteAnimation;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public class Tile extends Entity {

    private TileType type;
    private SpriteAnimation breakAnimation;
    private float durability, regenerationDuration;
    private boolean beingMined;

    public Tile(float x, float y, float width, float height, TileType type) {
        super(x, y, width, height);
        this.type = type;
        breakAnimation = new SpriteAnimation("breakSheet", this, 10, 1, type.getDurability());
        this.durability = type.getDurability();
        this.regenerationDuration = 0.5f;
    }

    public void render(SpriteBatch spriteBatch) {
        if (type.isFluid()) {
            spriteBatch.draw(type.getTexture(), getX(), getY(), getWidth(), getHeight() * durability / type.getDurability());
        } else {
            spriteBatch.draw(type.getTexture(), getX(), getY(), getWidth(), getHeight());
        }
        if (type.isMineable()) {
            if (durability < type.getDurability()) {
                float progress = 1 - durability / type.getDurability();
                breakAnimation.animate(spriteBatch, progress);
            }
            if (!beingMined) {
                durability += type.getDurability() / regenerationDuration * Clock.getDeltaTime();
            }
            durability = Maths.clamp(durability, 0, type.getDurability());
        }
        beingMined = false;
    }

    public void mine(float miningSpeed, EntityManager entityManager) {
        beingMined = true;
        durability -= miningSpeed * Clock.getDeltaTime();
        if (durability <= 0) {
            breakTile(entityManager);
        }
    }

    public void breakTile(EntityManager entityManager) {
        drop(entityManager);
        type = TileType.AIR;
        durability = type.getDurability();
    }

    private void drop(EntityManager entityManager) {
        entityManager.addItem(type.getTexture(), getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    public void dispose() {
        breakAnimation.dispose();
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
        durability = type.getDurability();
    }

    public float getDurability() {
        return durability;
    }

    public void setDurability(float durability) {
        this.durability = durability;
    }
}
