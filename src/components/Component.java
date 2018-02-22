package components;

import com.geoxpm.sandboxgame.MainClass;

/**
 * Created by Geoffrey Mason on 23/01/2018.
 */

public abstract class Component {

    private boolean alive;

    public Component() {
        alive = true;
    }

    public abstract void update(MainClass game);

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
