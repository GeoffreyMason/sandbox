package utilities;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public class Timer {

    private float time, duration;
    private boolean completed, swing, reverse;

    public Timer(float duration) {
        this.duration = duration;
    }

    public void update() {
        completed = false;
        if (reverse) {
            if (time - Clock.getDeltaTime() <= 0) {
                completed = true;
                reverse = false;
            } else {
                time -= Clock.getDeltaTime();
            }
        } else {
            if (time + Clock.getDeltaTime() >= duration) {
                completed = true;
                if (swing) {
                    reverse = true;
                } else {
                    time %= duration;
                    time += Clock.getDeltaTime();
                }
            } else {
                time += Clock.getDeltaTime();
            }
        }
        /*
        if (time + Clock.getDeltaTime() >= duration) {
            time %= duration;
            completed = true;
        }
        time += Clock.getDeltaTime();
        */
    }

    public float getProgress() {
        return Maths.clamp(time / duration, 0, 1);
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isSwing() {
        return swing;
    }

    public void swing() {
        swing = true;
    }
}
