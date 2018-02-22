package entities;

import com.badlogic.gdx.graphics.Texture;
import com.geoxpm.sandboxgame.MainClass;

import tiles.TileGrid;

/**
 * Created by geoxp on 02/02/2018.
 */

public class Chest extends DynamicEntity {

    public enum CHEST_TYPE {
        WOODEN("woodenChest", 6, 3), IRON("ironChest", 8, 5), GOLDEN("goldenChest", 10, 6);

        private Texture texture;
        private int rowLength, columnLength;

        CHEST_TYPE(String textureName, int rowLength, int columnLength) {
            texture = new Texture(textureName + ".png");
        }

        public Texture getTexture() {
            return texture;
        }
    }


    public Chest(CHEST_TYPE type, float x, float y, float width, float height, TileGrid grid) {
        super(type.getTexture(), x, y, width, height, grid);
    }

    @Override
    public void update(MainClass game) {

    }
}
