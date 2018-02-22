package particles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.geoxpm.sandboxgame.MainClass;

import entities.DynamicEntity;
import tiles.TileGrid;
import utilities.Maths;
import utilities.Timer;

/**
 * Created by Geoffrey Mason on 21/01/2018.
 */

public class ParticleSystem {

    private List<Particle> particleList;
    private TileGrid grid;
    private Timer timer;
    private boolean emitting, alive;
    private float x, y;
    private int intensity, particleRadius, particleSpeed;

    public ParticleSystem(TileGrid grid, int intensity, int particleRadius, int particleSpeed) {
        this.grid = grid;
        this.intensity = intensity;
        this.particleRadius = particleRadius;
        this.particleSpeed = particleSpeed;
        particleList = new ArrayList<Particle>();
        timer = new Timer(1);
        alive = true;
    }

    public void emit(float x, float y) {
        for (int i = 0; i < intensity; i++) {
            Particle particle = new Particle(x, y, particleRadius, 1, Maths.random(0.5f, 1), Maths.random(0.5f, 0.6f), 1.2f, grid);
            float xImpulse = Maths.random(-particleSpeed, particleSpeed);
            float factor = Maths.random(-1, 1);
            float yImpulse = (float) Math.sqrt(Math.pow(particleSpeed, 2) - Math.pow(xImpulse, 2)) * factor * Math.abs(factor);
            particle.impulse(xImpulse, yImpulse);
            particleList.add(particle);
        }
        alive = false;
    }

    public void emit(float x, float y, float duration) {
        this.x = x;
        this.y = y;
        emitting = true;
        timer.setDuration(duration);
        timer.setTime(0);
        alive = false;
    }

    public void render(MainClass game) {
        timer.update();
        if (timer.isCompleted())
            emitting = false;
        else if (emitting) {
            emit(x, y);
        }
        Iterator<Particle> iterator = particleList.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            if (particle.isAlive()) {
                particle.render(game);
            } else {
                iterator.remove();
            }
        }
    }

    public boolean isComplete() {
        if (particleList.size() == 0)
            return true;
        return false;
    }

    public boolean isAlive() {
        if (isComplete() && alive)
            return true;
        return false;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
