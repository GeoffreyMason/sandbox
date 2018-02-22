package uis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.geoxpm.sandboxgame.MainClass;

import entities.Bar;
import items.Item;
import screens.TestScreen;

/**
 * Created by Geoffrey Mason on 21/01/2018.
 */

public class UIOverlay {

    private MainClass game;
    private Button button;
    private Bar bar;
    private Hotbar hotbar;
    private Inventory inventory;

    public UIOverlay(MainClass game) {
        this.game = game;
        button = new Button(MainClass.WIDTH - 20 - 10, MainClass.HEIGHT - 20 - 10, 20, 20);
        bar = new Bar(MainClass.WIDTH - 100 - 10, MainClass.HEIGHT - 20 - 10, 80, 20, 100);
        inventory = new Inventory(MainClass.WIDTH / 2f, MainClass.HEIGHT / 2f, 32, 32, 12, 6);
        hotbar = new Hotbar(MainClass.WIDTH / 2f, 8, 32, 32);
    }

    public void render(SpriteBatch spriteBatch) {
        button.render(game.spriteBatch);
        bar.render(game.spriteBatch);
        hotbar.render(game);
        if (Gdx.input.isKeyPressed(Input.Keys.E))
            inventory.render(game);
    }

    public Hotbar getHotbar() {
        return hotbar;
    }
}
