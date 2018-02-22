package tiles;

import utilities.Maths;

/**
 * Created by geoxp on 02/02/2018.
 */

public class RoundWorld extends TileGrid{

    public RoundWorld(int radius) {
        super(radius * 2, radius * 2);
        setGravitySource(getWorldWidth() / 2f, getWorldHeight() / 2f);
        for (int i = 0; i < getTilesWide(); i++) {
            for (int j = 0; j < getTilesHigh(); j++) {
                float x = i * getTileSize();
                float y = j * getTileSize();
                if (i == 0 || i == getTilesWide() - 1 || j == 0 || j == getTilesHigh() - 1) {
                    getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.BEDROCK);
                } else if (Maths.distance(x + getTileSize() / 2f, y + getTileSize() / 2f, getWorldWidth() / 2f, getWorldHeight() / 2f) <= (radius - 5) * getTileSize()){
                    getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.GRASS);
                } else {
                    getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.AIR);
                }
            }
        }
    }
}
