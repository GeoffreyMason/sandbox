package utilities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public class Maths {

    public static float normaliseValue(float value, float min, float max) {
        return (value - min) / (max - min);
    }

    public static float clamp(float value, float min, float max) {
        if (value < min)
            value = min;
        else if (value > max)
            value = max;
        return value;
    }

    public static float random(float min, float max) {
        return (float) (Math.random()) * (max - min) + min;
    }

    public static float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static Vector2 normalisedDirection(float x1, float y1, float x2, float y2) {
        Vector2 direction = new Vector2(x1 - x2, y1 - y2);
        return direction.nor();
    }

    public static float cosineInterpolate(float x1, float x2, float mu) {
        float mu2;
        mu2 = (float) (1 - Math.cos(mu * Math.PI)) / 2f;
        return (x1 * (1 - mu2) + x2 * mu2);
    }
}
