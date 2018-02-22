package tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import entities.DynamicEntity;
import entities.Pump;
import utilities.Clock;
import utilities.Maths;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public abstract class TileGrid {

    private Tile[][] map;
    private int tilesWide, tilesHigh;
    private float tileSize, worldWidth, worldHeight;
    private Vector2 gravitySource;

    public TileGrid(int tilesWide, int tilesHigh) {
        this.tilesWide = tilesWide;
        this.tilesHigh = tilesHigh;
        this.tileSize = 16;
        worldWidth = tilesWide * tileSize;
        worldHeight = tilesHigh * tileSize;
        map = new Tile[tilesWide][tilesHigh];
    }

    public void render(SpriteBatch spriteBatch) {
        Color colour = spriteBatch.getColor();
        float waterBlocks = 0;
        for (int i = 0; i < tilesWide; i++) {
            for (int j = 0; j < tilesHigh; j++) {
                //background[i][j].render(spriteBatch, mousePosition);
                Tile tile = map[i][j];
                spriteBatch.setColor(colour.r, colour.g, colour.b, tile.getType().getAlpha());
                if (tile.getType() == TileType.LAVA) {
                    if (j > 0 && map[i][j - 1].getType() == TileType.WATER) {
                        tile.setType(TileType.OBSIDIAN);
                    } else if (j < tilesHigh - 1 && map[i][j + 1].getType() == TileType.WATER) {
                        tile.setType(TileType.OBSIDIAN);
                    } else if (i > 0 && map[i - 1][j].getType() == TileType.WATER) {
                        tile.setType(TileType.OBSIDIAN);
                    } else if (i < tilesWide - 1 && map[i + 1][j].getType() == TileType.WATER) {
                        tile.setType(TileType.OBSIDIAN);
                    }
                }
                if (tile.getType().isFluid()) {
                    TileType type = tile.getType();
                    waterBlocks += tile.getDurability();
                    /*if (j > 0 && map[i][j - 1].getType() == TileType.AIR) {
                        tile.setType(TileType.AIR);
                        map[i][j - 1].setType(TileType.WATER);
                    } else*/
                    if (i > 0 && i < tilesWide - 1 && j > 0 && j < tilesHigh - 1) {
                        if (map[i][j - 1].getType() == TileType.AIR) {
                            map[i][j - 1].setType(type);
                            map[i][j - 1].setDurability(0);
                        }
                        if (map[i - 1][j].getType() == TileType.AIR) {
                            map[i - 1][j].setType(type);
                            map[i - 1][j].setDurability(0);
                        }
                        if (map[i + 1][j].getType() == TileType.AIR) {
                            map[i + 1][j].setType(type);
                            map[i + 1][j].setDurability(0);
                        }
                        if (map[i][j - 1].getType() == type && map[i][j - 1].getDurability() != type.getDurability()) {
                            float durability = tile.getDurability() + map[i][j - 1].getDurability();
                            map[i][j - 1].setDurability(Maths.clamp(durability, 0, type.getDurability()));
                            tile.setDurability(Maths.clamp(durability - type.getDurability(), 0, type.getDurability()));
                        }
                        float total = tile.getDurability();
                        int count = 1;
                        if (map[i - 1][j].getType() == type) {
                            total += map[i - 1][j].getDurability();
                            count++;
                        }
                        if (map[i + 1][j].getType() == type) {
                            total += map[i + 1][j].getDurability();
                            count++;
                        }
                        float durability = total / (float) count;
                        tile.setDurability(durability);
                        if (map[i - 1][j].getType() == type) {
                            map[i - 1][j].setDurability(durability);
                        }
                        if (map[i + 1][j].getType() == type) {
                            map[i + 1][j].setDurability(durability);
                        }
                    }
                    if (tile.getDurability() < 0.0001f) {
                        tile.setType(TileType.AIR);
                    }
                    if (map[i - 1][j].getDurability() < 0.0001f) {
                        map[i - 1][j].setType(TileType.AIR);
                    }
                    if (map[i + 1][j].getDurability() < 0.0001f) {
                        map[i + 1][j].setType(TileType.AIR);
                    }
                    tile.render(spriteBatch);
                } else {
                    tile.render(spriteBatch);
                }
            }
        }
        //System.out.println(waterBlocks / 10f);
    }

    public boolean detectTerrainCollisionX(DynamicEntity entity) {
        entity.setFlushLeft(false);
        entity.setFlushRight(false);
        float deltaX = entity.getxVelocity() * Clock.getDeltaTime();
        float xMin = entity.getX();
        float xMax = entity.getX() + entity.getWidth();
        if (deltaX < 0)
            xMin += deltaX;
        else if (deltaX > 0)
            xMax += deltaX;

        Rectangle boundingBox = new Rectangle(xMin, entity.getY(), xMax - xMin, entity.getHeight());

        int iMin = (int) Maths.clamp((float) Math.floor(xMin / tileSize), 0, tilesWide);
        int iMax = (int) Maths.clamp((xMax / tileSize) + 1, 0, tilesWide);

        int jMin = (int) Maths.clamp((float) Math.floor(entity.getY() / tileSize), 0, tilesHigh);
        int jMax = (int) Maths.clamp(((entity.getY() + entity.getHeight()) / tileSize) + 1, 0, tilesHigh);

        for (int i = iMin; i < iMax; i++) {
            for (int j = jMin; j < jMax; j++) {
                Tile tile = map[i][j];
                if (tile.getType().isCollideable() && boundingBox.overlaps(tile.getBoundingBox())) {
                    if (deltaX > 0) {
                        entity.setX(tile.getX() - entity.getWidth());
                        entity.setFlushRight(true);
                    } else if (deltaX < 0) {
                        entity.setX(tile.getX() + tile.getWidth());
                        entity.setFlushLeft(true);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean detectTerrainCollisionY(DynamicEntity entity) {
        entity.setGrounded(false);
        float deltaY = entity.getyVelocity() * Clock.getDeltaTime();
        float yMin = entity.getY();
        float yMax = entity.getY() + entity.getHeight();
        if (deltaY < 0)
            yMin += deltaY;
        else if (deltaY > 0)
            yMax += deltaY;

        Rectangle boundingBox = new Rectangle(entity.getX(), yMin, entity.getWidth(), yMax - yMin);

        int iMin = (int) Maths.clamp((float) Math.floor(entity.getX() / tileSize), 0, tilesWide);
        int iMax = (int) Maths.clamp(((entity.getX() + entity.getWidth()) / tileSize) + 1, 0, tilesWide);

        int jMin = (int) Maths.clamp((float) Math.floor(yMin / tileSize), 0, tilesHigh);
        int jMax = (int) Maths.clamp((yMax / tileSize) + 1, 0, tilesHigh);

        for (int i = iMin; i < iMax; i++) {
            for (int j = jMin; j < jMax; j++) {
                i = (int) Maths.clamp(i, 0, tilesWide - 1);
                j = (int) Maths.clamp(j, 0, tilesHigh - 1);
                Tile tile = map[i][j];
                if (tile.getType().isCollideable() && boundingBox.overlaps(tile.getBoundingBox())) {
                    if (deltaY > 0)
                        entity.setY(tile.getY() - entity.getHeight());
                    else if (deltaY < 0) {
                        entity.setY(tile.getY() + tile.getHeight());
                        entity.setGrounded(true);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void dispose() {
        for (int i = 0; i < tilesWide; i++) {
            for (int j = 0; j < tilesHigh; j++) {
                map[i][j].dispose();
            }
        }
    }

    public Tile getTile(float x, float y) {
        int i = (int) (x / tileSize);
        int j = (int) (y / tileSize);
        if (i < 0 || i > tilesWide - 1 || j < 0 || j > tilesHigh - 1)
            return null;
        return map[i][j];
    }



    public Tile[][] getMap() {
        return map;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }

    public int getTilesWide() {
        return tilesWide;
    }

    public void setTilesWide(int tilesWide) {
        this.tilesWide = tilesWide;
    }

    public int getTilesHigh() {
        return tilesHigh;
    }

    public void setTilesHigh(int tilesHigh) {
        this.tilesHigh = tilesHigh;
    }

    public float getTileSize() {
        return tileSize;
    }

    public void setTileSize(float tileSize) {
        this.tileSize = tileSize;
    }

    public float getWorldWidth() {
        return worldWidth;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public Vector2 getGravitySource() {
        return gravitySource;
    }

    public void setGravitySource(float x, float y) {
        this.gravitySource = new Vector2(x, y);
    }
}
