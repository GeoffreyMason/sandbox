package tiles;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public enum TileType {
    GRASS("grass1", 10, 1, 1, true, true, false),
    DIRT("dirt", 10, 1, 1, true, true, false),
    WATER("water", 10, 0.85f, 0.6f, false, false, true),
    LAVA("lava", 10, 0.85f, 0.8f, false, false, true),
    OBSIDIAN("tileObsidian", 100, 1, 1, true, true, false),
    BEDROCK("bedrock", 10000, 1, 1, true, false, false),
    AIR("blank", 0, 0.98f, 0, false, false, false);

    private Texture texture;
    private int durability;
    private float frictionCoefficient, alpha;
    private boolean collideable, mineable, fluid;

    TileType(String textureName, int durability, float frictionCoefficient, float alpha, boolean collideable, boolean mineable, boolean fluid) {
        this.texture = new Texture(textureName + ".png");
        this.durability = durability;
        this.frictionCoefficient = frictionCoefficient;
        this.alpha = alpha;
        this.collideable = collideable;
        this.mineable = mineable;
        this.fluid = fluid;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getDurability() {
        return durability;
    }

    public boolean isCollideable() {
        return collideable;
    }

    public boolean isMineable() {
        return mineable;
    }

    public boolean isFluid() {
        return fluid;
    }

    public float getFrictionCoefficient() {
        return frictionCoefficient;
    }

    public float getAlpha() {
        return alpha;
    }
}
