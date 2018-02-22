package player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geoxpm.sandboxgame.MainClass;

import entities.Entity;

/**
 * Created by Geoffrey Mason on 18/01/2018.
 */

public class Nameplate {

    private String playerName;
    private Entity entity;
    private float stringWidth, stringHeight;

    public Nameplate(String playerName, Entity entity) {
        this.playerName = playerName;
        this.entity = entity;
        stringWidth = 44;
        stringHeight = 16;
    }

    public void render(MainClass game) {
        game.font.draw(game.spriteBatch, playerName, entity.getX() + (entity.getWidth() - stringWidth) / 2f, entity.getY() + entity.getHeight() + stringHeight);
    }
}
