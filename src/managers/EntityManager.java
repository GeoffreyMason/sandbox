package managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.geoxpm.sandboxgame.MainClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entities.DynamicEntity;
import items.Item;
import particles.ParticleSystem;
import tiles.TileGrid;

/**
 * Created by Geoffrey Mason on 25/01/2018.
 */

public class EntityManager {
    private List<DynamicEntity> entityList;
    private List<Item> itemList;
    private List<ParticleSystem> particleList;
    private TileGrid grid;

    public EntityManager(TileGrid grid) {
        this.grid = grid;
        entityList = new ArrayList<DynamicEntity>();
        itemList = new ArrayList<Item>();
        particleList = new ArrayList<ParticleSystem>();
    }

    public void update(MainClass game) {
        //System.out.println(entityList.size());
        Iterator<DynamicEntity> entityIterator = entityList.iterator();
        while (entityIterator.hasNext()) {
            DynamicEntity entity = entityIterator.next();
            if (entity.isAlive()) {
                entity.render(game);
            } else {
                entityIterator.remove();
            }
        }

        Iterator<Item> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (item.isAlive()) {
                item.render(game);
            } else {
                itemIterator.remove();
            }
        }

        Iterator<ParticleSystem> particleIterator = particleList.iterator();
        while (particleIterator.hasNext()) {
            ParticleSystem particleSystem = particleIterator.next();
            if (!particleSystem.isComplete()) {
                particleSystem.render(game);
            } else {
                particleIterator.remove();
            }
        }
    }

    public void addEntity(DynamicEntity entity) {
        entityList.add(entity);
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void addItem(Texture texture, float x, float y) {
        itemList.add(new Item(texture, x, y, grid));
    }

    public void addParticle(ParticleSystem particleSystem) {
        particleList.add(particleSystem);
    }

    public List<DynamicEntity> getEntityList() {
        return entityList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<ParticleSystem> getParticleList() {
        return particleList;
    }
}
