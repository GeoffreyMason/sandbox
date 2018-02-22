package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.geoxpm.sandboxgame.MainClass;

import entities.Chest;
import entities.Dynamite;
import entities.Pickaxe;
import entities.Pump;
import items.Item;
import player.Player;
import entities.PlayerInput;
import managers.EntityManager;
import tiles.FlatWorld;
import tiles.RoundWorld;
import tiles.SquareWorld;
import tiles.TileGrid;
import uis.UIOverlay;

/**
 * Created by Geoffrey Mason on 16/01/2018.
 */

public class TestScreen implements Screen {

    public MainClass game;
    public TileGrid grid;
    public Player player;
    public EntityManager entityManager;
    public PlayerInput playerInput;
    public UIOverlay uiOverlay;
    private Pump pump;
    public Pickaxe pickaxe;
    public Vector2 staticMousePosition;

    private OrthographicCamera staticCamera;
    public Viewport staticViewport;

    public TestScreen(MainClass game) {
        this.game = game;

        staticCamera = new OrthographicCamera(MainClass.WIDTH, MainClass.HEIGHT);
        staticViewport = new FitViewport(MainClass.WIDTH, MainClass.HEIGHT, staticCamera);
        staticViewport.apply(true);

        grid = new FlatWorld(256, 64);
        entityManager = new EntityManager(grid);
        player = new Player(grid);
        pickaxe = new Pickaxe(player, grid);
        game.camera.position.set(player.getX(), player.getY(), 0);
        playerInput = new PlayerInput(grid, player, game, entityManager);
        pump = new Pump(new Texture("pumpBase.png"),player.getXCentre() - 32, player.getYCentre(), grid.getTileSize() / 2f, grid.getTileSize() / 2f, grid);
        //entityManager.addEntity(new Chest(Chest.CHEST_TYPE.IRON, player.getX() + 64, player.getYCentre(), 16, 16, grid));
        entityManager.addEntity(pump);
        entityManager.addEntity(pickaxe);
        entityManager.addEntity(player);
        uiOverlay = new UIOverlay(game);
        uiOverlay.getHotbar().addItem(new Item(pickaxe.getTexture(), 0, 0, grid));
        //uiOverlay.getHotbar().addEntity(new Dynamite(0, 0, grid));
        staticMousePosition = new Vector2();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //game.staticCamera.position.set(player.getX() + player.getWidth() / 2f, player.getY() + player.getHeight() / 2f, 0);
        //game.staticCamera.position.set(Maths.cosineInterpolate(game.staticCamera.position.x, player.getX() + player.getWidth() / 2f, 1), Maths.cosineInterpolate(game.staticCamera.position.y, player.getY() + player.getHeight() / 2f, 1), 0);
        //game.camera.rotate();
        //game.camera.position.set(player.getXCentre(), player.getYCentre(), 0);
        entityManager.update(game);
        grid.render(game.spriteBatch);

        staticCamera.update();
        Vector3 screenPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        staticCamera.unproject(screenPosition, staticViewport.getScreenX(), staticViewport.getScreenY(), staticViewport.getScreenWidth(), staticViewport.getScreenHeight());
        staticMousePosition.set(screenPosition.x, screenPosition.y);
        game.spriteBatch.setProjectionMatrix(staticCamera.combined);
        playerInput.render(game);
        uiOverlay.render(game.spriteBatch);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
        staticViewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        grid.dispose();
    }
}
