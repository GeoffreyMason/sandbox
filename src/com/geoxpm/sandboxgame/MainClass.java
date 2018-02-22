package com.geoxpm.sandboxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import screens.TestScreen;
import utilities.Clock;


public class MainClass extends Game {

	public static final int WIDTH = 800, HEIGHT = 400;

	public SpriteBatch spriteBatch;
	public ShapeRenderer shapeRenderer;
	public BitmapFont font;
	public OrthographicCamera camera;
	public Viewport viewport;
	public Vector2 mousePosition;
	public TestScreen testScreen;
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(800, 400);
		mousePosition = new Vector2(0, 0);
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
		viewport.apply(true);
		testScreen = new TestScreen(this);
		setScreen(testScreen);
	}

	@Override
	public void render () {
		//System.out.println(Gdx.graphics.getFramesPerSecond());
		camera.update();
		Vector3 screenPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(screenPosition, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
		mousePosition.set(screenPosition.x, screenPosition.y);
		Clock.update();
		Gdx.gl.glClearColor(250 / 255f, 214 / 255f, 165 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		super.render();
		spriteBatch.end();
	}
	
	@Override
	public void dispose () {
		spriteBatch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
