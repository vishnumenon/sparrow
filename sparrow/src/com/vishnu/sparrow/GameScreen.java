package com.vishnu.sparrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen {
	Sparrow game;
	private Stage stage;
	public static OrthographicCamera camera;
	private Texture bgTexture;
	private Ship ship;
	public static boolean attracting;
	public static ShapeRenderer sr;
	public static LevelDetails level;
	public static boolean justTapped;
	public static boolean paused;



	public GameScreen(Sparrow game, LevelDetails level) {
		this.game = game;
		GameScreen.level = level;
		GameScreen.attracting = false;
		GameScreen.paused = false;
		GameScreen.justTapped = true;
		Gdx.app.log("Loaded Level: ", level.levelNumber + "");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.getSpriteBatch().begin();
		stage.getSpriteBatch().draw(bgTexture, 0, 0, level.width, level.height);
		stage.getSpriteBatch().end();
		
		if(!paused) {
			stage.act(Gdx.graphics.getDeltaTime());
			float newCamX = ship.getX();
			float newCamY = ship.getY();
			if(! (newCamX < camera.viewportWidth / 2 ||  newCamX > level.width - camera.viewportWidth / 2)) {
				camera.position.x = newCamX;
			}
			if(! (newCamY < camera.viewportHeight / 2 || newCamY > level.height - camera.viewportHeight / 2)) {
				camera.position.y = newCamY;
			}
			
			Vector2 shipReal = stage.stageToScreenCoordinates(ship.getCoords());
			Vector2 planetReal = stage.stageToScreenCoordinates(ship.orbitingPlanet.getCoords());

			sr.begin(ShapeType.Line);
			sr.setColor(1, attracting ? 1 : 0, 0, 0.1f);
			sr.line(shipReal.x, shipReal.y, planetReal.x, planetReal.y);
			sr.end();
		}
		
		camera.update();
		stage.draw();

		if (Gdx.input.isKeyPressed(Keys.BACK)){
			game.setScreen(new LevelSelectionScreen(game));
		}

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(800, 480, false);

	}

	@Override
	public void show() {
		stage = new Stage(level.width, level.height, false);
		camera = new OrthographicCamera(800, 480);
		camera.position.x = camera.viewportWidth / 2;
		camera.position.y = level.height / 2;
		stage.setCamera(camera);
		sr = new ShapeRenderer();

		Texture.setEnforcePotImages(false);

		bgTexture = Sparrow.assetManager.get("data/grass.jpg", Texture.class);

		for(MassiveObject o : level.objects) {
			stage.addActor(o);
		}

		ship = new Ship(15, 15, 200, 90);
		stage.addActor(ship);

		
		GestureDetector gd = new GestureDetector(new SparrowGestureListener());
		InputMultiplexer im = new InputMultiplexer(gd, stage);

		Gdx.input.setInputProcessor(im);
		Gdx.input.setCatchBackKey(true);


	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	private class SparrowGestureListener implements GestureListener {

		@Override
		public boolean zoom (float originalDistance, float currentDistance) {
			
			float scale = 0.02f*(originalDistance/currentDistance) + 0.98f;
			float newVPW = camera.viewportWidth * scale;
			float newVPH = camera.viewportHeight * scale;
			
			if(!(Math.min(newVPW, newVPH) > Math.max(level.width, level.height) || newVPW < 200 || newVPH < 120)){
				camera.viewportHeight = newVPH;
				camera.viewportWidth = newVPW;
			}
			
			return false;
		}

		@Override
		public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
			return false;
		}

		@Override
		public boolean touchDown(float x, float y, int pointer, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean tap(float x, float y, int count, int button) {
			// TODO Auto-generated method stub
			if(!paused) {
				attracting = !attracting;
				justTapped = true;
			} else {
				paused = false;
			}
			return false;
		}

		@Override
		public boolean longPress(float x, float y) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY) {
			// TODO Auto-generated method stub
				paused = true;
				if(! ((deltaX < 0 && camera.position.x > level.width) || (deltaX > 0 && camera.position.x < 0))) {
					camera.position.x = camera.position.x - deltaX;
				}
				if(! ((deltaY > 0 && camera.position.y > level.height) || (deltaY < 0 && camera.position.y < 0))) {
					camera.position.y = camera.position.y + deltaY;
				}
			return false;
		}
	}

}
