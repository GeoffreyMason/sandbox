package components;

import com.geoxpm.sandboxgame.MainClass;

import entities.DynamicEntity;
import managers.EntityManager;
import particles.ParticleSystem;
import tiles.Tile;
import tiles.TileGrid;
import utilities.Maths;

/**
 * Created by Geoffrey Mason on 23/01/2018.
 */

public class ExplosionComponent extends Component {

    private ParticleSystem explosionParticles;
    private DynamicEntity entity;
    private TileGrid grid;
    private boolean triggered;

    public ExplosionComponent(TileGrid grid, DynamicEntity entity) {
        this.grid = grid;
        this.entity = entity;
        explosionParticles = new ParticleSystem(grid, 16, 6, 300);
    }

    @Override
    public void update(MainClass game) {
        explosionParticles.render(game);
        if (triggered && explosionParticles.isComplete()) {
            setAlive(false);
        }
    }

    public void explode(EntityManager entityManager, int radius, int explosionPower) {
        triggered = true;
        explosionParticles.emit(entity.getXCentre(), entity.getYCentre(), 0.1f);
        int iMin = (int) Maths.clamp((entity.getXCentre() / grid.getTileSize()) - radius, 0, grid.getTilesWide());
        int iMax = (int) Maths.clamp((entity.getXCentre() / grid.getTileSize()) + radius + 1, 0, grid.getTilesWide());
        int jMin = (int) Maths.clamp((entity.getYCentre() / grid.getTileSize()) - radius, 0, grid.getTilesHigh());
        int jMax = (int) Maths.clamp((entity.getYCentre() / grid.getTileSize()) + radius + 1, 0, grid.getTilesHigh());
        for (int i = iMin; i < iMax; i++) {
            for (int j = jMin; j < jMax; j++) {
                Tile tile = grid.getMap()[i][j];
                if (entity.distance(tile) <= radius * grid.getTileSize() && tile.getType().isMineable() && tile.getDurability() <= explosionPower)
                    tile.breakTile(entityManager);
            }
        }
    }
}
