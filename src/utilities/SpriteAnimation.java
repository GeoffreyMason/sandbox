package utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import entities.Entity;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public class SpriteAnimation extends Entity {

    private Texture spriteSheet;
    private int tilesWide, tilesHigh;
    private Timer timer;
    private Entity entity;

    public SpriteAnimation(String textureName, Entity entity, int tilesWide, int tilesHigh, float duration) {
        super(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
        spriteSheet = new Texture(textureName + ".png");
        this.tilesWide = tilesWide;
        this.tilesHigh = tilesHigh;
        this.timer = new Timer(duration);
    }

    public void animate(SpriteBatch spriteBatch) {
        timer.update();
        int index = (int) Maths.clamp((float) Math.floor(timer.getProgress() * tilesWide * tilesHigh), 0, tilesWide * tilesHigh - 1);
        spriteBatch.draw(spriteSheet, getX(), getY(), getWidth(), getHeight(), (int) (((index % tilesWide) / (float) tilesWide) * spriteSheet.getWidth()), (int) (Math.floor(index / tilesWide) / (float) tilesHigh * spriteSheet.getHeight()), spriteSheet.getWidth() / tilesWide, spriteSheet.getHeight() / tilesHigh, false, false);
    }

    public void animate(SpriteBatch spriteBatch, float progress) {
        int index = (int) Maths.clamp((float) Math.floor(progress * tilesWide * tilesHigh), 0, tilesWide * tilesHigh - 1);
        spriteBatch.draw(spriteSheet, getX(), getY(), getWidth(), getHeight(), (int) (((index % tilesWide) / (float) tilesWide) * spriteSheet.getWidth()), (int) (Math.floor(index / tilesWide) / (float) tilesHigh * spriteSheet.getHeight()), spriteSheet.getWidth() / tilesWide, spriteSheet.getHeight() / tilesHigh, false, false);
    }

    public void dispose() {
        spriteSheet.dispose();
    }
}
