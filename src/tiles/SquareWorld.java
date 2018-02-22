package tiles;

/**
 * Created by geoxp on 02/02/2018.
 */

public class SquareWorld extends TileGrid {

    public SquareWorld(int tilesWide, int tilesHigh) {
        super(tilesWide, tilesHigh);
            for (int i = 0; i < tilesWide; i++) {
                for (int j = 0; j < tilesHigh; j++) {
                    if (i == 0 || i == tilesWide - 1 || j == 0 || j == tilesHigh - 1) {
                        getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.BEDROCK);
                    } else if (j <= tilesHigh - 10){
                        getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.GRASS);
                    } else {
                        getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.AIR);
                }
            }
        }
    }
}
