package entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import utilities.Maths;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public abstract class Entity {

    private float x, y, width, height;
    private Rectangle boundingBox;
    private boolean useable;

    public Entity(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.boundingBox = new Rectangle(x, y, width, height);
    }

    public boolean isHoveredOver(Vector2 mousePosition) {
        if (boundingBox.contains(mousePosition)) {
            return true;
        }
        return false;
    }

    public boolean isJustTouched(Vector2 mousePosition) {
        if (Gdx.input.justTouched() && isHoveredOver(mousePosition)) {
            return true;
        }
        return false;
    }

    public boolean isTouched(Vector2 mousePosition) {
        if (Gdx.input.isTouched() && isHoveredOver(mousePosition)) {
            return true;
        }
        return false;
    }

    public float distance(Entity entity) {
        return Maths.distance(getX() + getWidth() / 2f, getY() + getHeight() / 2f, entity.getX() + entity.getWidth() / 2f, entity.getY() + entity.getHeight() / 2f);
    }

    public float getX() {
        return x;
    }

    public float getXCentre() {
        return x + width / 2f;
    }

    public void setX(float x) {
        this.x = x;
        boundingBox.setX(x);
    }

    public void setXCentre(float xCentre) {
        x = xCentre - width / 2;
        boundingBox.setX(x);
    }

    public float getY() {
        return y;
    }

    public float getYCentre() {
        return y + height / 2f;
    }

    public void setY(float y) {
        this.y = y;
        boundingBox.setY(y);
    }

    public void setYCentre(float yCentre) {
        y = yCentre - height / 2;
        boundingBox.setY(y);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        boundingBox.setWidth(width);
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        boundingBox.setHeight(height);
    }

    public void set(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        boundingBox.set(x, y, width, height);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public boolean isUseable() {
        return useable;
    }

    public void setUseable(boolean useable) {
        this.useable = useable;
    }
}
