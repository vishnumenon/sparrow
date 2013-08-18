package com.vishnu.sparrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class LevelSelectionScreen implements Screen {
	final Sparrow game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture levelsBGtexture;
	private Music levelsBGmusic;
	private Stage stage;
	
	
	public MassiveObject[] m = {new MassiveObject(100, 100, 30), new MassiveObject(1000, 500, 200), new MassiveObject(500, 800, 150)};
	private LevelDetails[] levels = {
		new LevelDetails(1, 2000, 2000, m), 
		new LevelDetails(2), 
		new LevelDetails(3), 
		new LevelDetails(4), 
		new LevelDetails(5), 
		new LevelDetails(6), 
		new LevelDetails(7), 
		new LevelDetails(8), 
		new LevelDetails(9), 
		new LevelDetails(10), 
		new LevelDetails(11), 
		new LevelDetails(12), 
		new LevelDetails(13), 
		new LevelDetails(14), 
		new LevelDetails(15), 
		new LevelDetails(16), 
		new LevelDetails(17), 
		new LevelDetails(18), 
		new LevelDetails(19), 
		new LevelDetails(20), 
		new LevelDetails(21), 
		new LevelDetails(22), 
		new LevelDetails(23), 
		new LevelDetails(24), 
		new LevelDetails(25), 
		new LevelDetails(26), 
		new LevelDetails(27)};
	
	public LevelSelectionScreen(Sparrow game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(levelsBGtexture,0,0,800,480);
		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		if (Gdx.input.isKeyPressed(Keys.BACK)){
			  game.setScreen(new StartScreen(game));
		}

	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(800, 480, false);

	}

	@Override
	public void show() {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		Texture.setEnforcePotImages(false);
		
		levelsBGtexture = Sparrow.assetManager.get("data/galaxies.jpg", Texture.class);
		
		levelsBGmusic = Sparrow.assetManager.get("data/title_bg.mp3", Music.class);
		levelsBGmusic.setLooping(true);
		if(game.getSoundOn()) {
			levelsBGmusic.play();
		}
		
		stage = new Stage();
		stage.setViewport(800, 480, false);
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);

		
		Texture buttonUp = Sparrow.assetManager.get("data/outlinebutton.9.png", Texture.class);
		buttonUp.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		NinePatch buttonUpBG = new NinePatch(buttonUp, 20, 20, 20, 20);
		NinePatch buttonDownBG = new NinePatch(buttonUp, 20, 20, 20, 20);
		TextButtonStyle style = new TextButtonStyle();
		style.fontColor = Color.WHITE;
				
		style.font = game.mediumFont;
		style.up = new NinePatchDrawable(buttonUpBG);
		style.down = new NinePatchDrawable(buttonDownBG);
		
		Table levelButtons = new Table();
		levelButtons.setWidth(800);
		
		TextButton currentLevelButton;
		int c = 0;
		for (LevelDetails l : levels) {
			c++;
			currentLevelButton = new TextButton(String.format("%03d",l.levelNumber), style);
			currentLevelButton.addListener(new LevelLauncher(l));
			
			levelButtons.add(currentLevelButton).expandX().pad(10);
			if(c == 4) {
				levelButtons.row();
				c = 0;
			}
		}
		
		ScrollPane levelButtonScroll = new ScrollPane(levelButtons);
		levelButtonScroll.setPosition(0, 0);
		levelButtonScroll.setWidth(800);
		levelButtonScroll.setHeight(480);
		stage.addActor(levelButtonScroll);
		
	}

	@Override
	public void hide() {
	}

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

	private class LevelLauncher extends ClickListener {
		private LevelDetails level;
		public LevelLauncher(LevelDetails level) { 
			this.level = level;
		}
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			event.getListenerActor().setColor(Color.RED);
            return true;  // must return true for touchUp event to occur
        }
		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            game.setScreen(new GameScreen(game, level));
        }
	}

}
