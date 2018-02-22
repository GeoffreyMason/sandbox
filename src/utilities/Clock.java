package utilities;

import com.badlogic.gdx.Gdx;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public class Clock {

    private static float deltaTime, timeSpeed = 1f, time, tickDuration = 1 / 60f ;
    private static boolean tick;

    public static void update() {
        tick = false;
        if (Gdx.graphics.getDeltaTime() < 0.5f) {
            deltaTime = Gdx.graphics.getDeltaTime() * timeSpeed;
            time += deltaTime;
            if (time >= tickDuration) {
                time %= tickDuration;
                tick = true;
            }
        }
    }

    public static float getDeltaTime() {
        return deltaTime;
    }

    public static float getTimeSpeed() {
        return timeSpeed;
    }

    public static void setTimeSpeed(float timeSpeed) {
        Clock.timeSpeed = timeSpeed;
    }

    public static boolean isTick() {
        return tick;
    }
}
