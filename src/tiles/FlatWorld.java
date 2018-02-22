package tiles;

import utilities.Maths;

/**
 * Created by geoxp on 02/02/2018.
 */

public class FlatWorld extends TileGrid {

    private NoiseGenerator terrainGenerator;
    private NoiseGenerator caveGenerator;
    private float[] heights;

    public FlatWorld(int tilesWide, int tilesHigh) {
        super(tilesWide, tilesHigh);
        heights = new float[tilesWide];
        terrainGenerator = new NoiseGenerator((int) Maths.clamp(tilesHigh - 64, 0, tilesHigh - 6), tilesHigh - 6, 5);
        caveGenerator = new NoiseGenerator(0, 1, 4);
        heights = new float[tilesWide];
        terrainGenerator = new NoiseGenerator((int) Maths.clamp(tilesHigh - 64, 0, tilesHigh - 6), tilesHigh - 6, 5);
        caveGenerator = new NoiseGenerator(0, 1, 4);
        //background = new Tile[tilesWide][tilesHigh];
        for (int i = 0; i < tilesWide; i++) {
            heights[i] = terrainGenerator.generateHeight(i);
            for (int j = 0; j < tilesHigh; j++) {
                if (i == 0 || i == tilesWide - 1 || j == 0 || j == tilesHigh - 1) {
                    getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.BEDROCK);
                    //background[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSize, TileType.BEDROCK);
                } else if (caveGenerator.generateValue(i, j) < 0.35f && j <= heights[i] - 32) {
                    float random = Maths.random(0, 1);
                    if (random < 0.2f) {
                        if (j < tilesHigh / 3f) {
                            getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.LAVA);
                        } else {
                            getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.WATER);
                        }
                    }
                    else
                        getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.AIR);
                    //background[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSize, TileType.DIRT);
                } else if (j <= heights[i]) {
                    getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.GRASS);
                    ////background[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSize, TileType.DIRT);
                } else if (j < tilesHigh - 32) {
                    getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.WATER);
                    //background[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSize, TileType.AIR);
                } else {
                    getMap()[i][j] = new Tile(i * getTileSize(), j * getTileSize(), getTileSize(), getTileSize(), TileType.AIR);
                    //background[i][j] = new Tile(i * tileSize, j * tileSize, tileSize, tileSize, TileType.AIR);
                }
            }
        }
    }
}
