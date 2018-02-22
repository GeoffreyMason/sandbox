package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.geoxpm.sandboxgame.MainClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import items.Item;
import managers.EntityManager;
import player.Player;
import tiles.Tile;
import tiles.TileGrid;

/**
 * Created by Geoffrey Mason on 17/01/2018.
 */

public class PlayerInput {

    private TileGrid grid;
    private Player player;
    private MainClass game;
    private Tile currentTile;
    private EntityManager entityManager;
    private int pickUpDistance;
    private float miningSpeed;
    private Item heldItem, equippedItem;

    public PlayerInput(TileGrid grid, Player player, MainClass game, EntityManager entityManager) {
        this.grid = grid;
        this.player = player;
        this.game = game;
        this.entityManager = entityManager;
        pickUpDistance = (int) (10 * grid.getTileSize());
        miningSpeed = 20;
        heldItem = null;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (player.getxVelocity() - player.getAngle() > -player.getSpeed()) {
                player.setxVelocity(player.getxVelocity() - player.getAcceleration());
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (player.getxVelocity() + player.getAcceleration() <= player.getSpeed()) {
                player.setxVelocity(player.getxVelocity() + player.getAcceleration());
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.jump(player.getJumpPower());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(2);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(3);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(4);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(5);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(6);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(7);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
            game.testScreen.uiOverlay.getHotbar().setSelectedSlotNumber(8);
        }

        if (Gdx.input.justTouched()) {
            Item item = game.testScreen.uiOverlay.getHotbar().getSelectedSlot().getItem();
            /*if (item != null && item.isUseable()) {
                DynamicEntity entity = item.getEntity()
                        ;
                entity.setX(player.getXCentre());
                entity.setY(player.getYCentre());
                launch(entity, 300);
                item.setAmount(item.getAmount() - 1);
            }*/
        }
    }

    public void pickUpItem() {
        Iterator<Item> iterator = entityManager.getItemList().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (player.distance(item) <= pickUpDistance && !item.isImmune()) {
                //player.attract(item);
                if (player.getBoundingBox().overlaps(item.getBoundingBox())) {
                    game.testScreen.uiOverlay.getHotbar().addItem(item);
                    iterator.remove();
                }
            }
        }
    }

    public void launch(DynamicEntity entity, float speed) {
        float angle = (float) Math.atan2(game.mousePosition.y - player.getYCentre(), game.mousePosition.x - player.getXCentre());
        entity.impulseAngle(speed, angle);
        entityManager.addEntity(entity);
    }

    public void dropItem(Item item) {
        item.setXCentre(player.getXCentre());
        item.setYCentre(player.getYCentre());
        item.setxVelocity(0);
        item.setyVelocity(0);
        item.setImmune(true);
        float angle = (float) Math.atan2(game.mousePosition.y - player.getYCentre(), game.mousePosition.x - player.getXCentre());
        item.impulseAngle(200, angle);
        entityManager.addItem(item);
    }

    public void render(MainClass game) {
        update();
        if (heldItem != null) {
            game.spriteBatch.draw(heldItem.getTexture(), game.testScreen.staticMousePosition.x - 8, game.testScreen.staticMousePosition.y - 8, 16, 16);
            if (Gdx.input.justTouched()) {
                dropItem(heldItem);
                heldItem = null;
            }
        }
    }

    private void update() {
        handleInput();
        pickUpItem();
        currentTile = grid.getTile(game.mousePosition.x, game.mousePosition.y);
        game.testScreen.pickaxe.setRendering(false);
        if (Gdx.input.isTouched()) {
            game.testScreen.pickaxe.setRendering(true);
            if (currentTile != null && currentTile.getType().isMineable()) {
                currentTile.mine(miningSpeed, entityManager);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            player.getExplosionComponent().explode(entityManager, 4, 10);
        }
    }

    public Item getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
    }
}
