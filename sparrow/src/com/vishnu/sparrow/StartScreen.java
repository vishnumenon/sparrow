package com.vishnu.sparrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class StartScreen implements Screen {
	final Sparrow game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture titleBGtexture;
	private Music titleBGmusic = null;
	private Stage stage;
	private int pixelsRight = 0;
	
	public StartScreen(Sparrow game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		pixelsRight++;
		
		if(Math.round(pixelsRight) == 1920) {
			pixelsRight = 0;
			camera.position.x = 0;
		}
		
			
		camera.translate(1, 0);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(titleBGtexture,0,0,1920,1200);
		batch.draw(titleBGtexture,1920,0,1920,1200);
		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		if (Gdx.input.isKeyPressed(Keys.BACK)){
			
			  Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			  Sparrow.assetManager.clear();
			  Gdx.app.exit();
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
		
		titleBGtexture = Sparrow.assetManager.get("data/galaxies.jpg", Texture.class);
		
		titleBGmusic = Sparrow.assetManager.get("data/title_bg.mp3", Music.class);
		
		titleBGmusic.setLooping(true);
		if(game.getSoundOn()) {
			titleBGmusic.play();
		}
		
		stage = new Stage();
		stage.setViewport(800, 480, false);
		Gdx.input.setInputProcessor(stage);
		Gdx.input.setCatchBackKey(true);

		

		Texture buttonUp = Sparrow.assetManager.get("data/outlinebutton.9.png", Texture.class);
		
		NinePatch buttonUpBG = new NinePatch(buttonUp, 20, 20, 20, 20);
		NinePatch buttonDownBG = new NinePatch(buttonUp, 20, 20, 20, 20);
		TextButtonStyle style = new TextButtonStyle();
		style.fontColor = Color.WHITE;
		
		Texture fontImage = Sparrow.assetManager.get("data/Ubuntu100Solid.png", Texture.class);
		
		game.largeFont = new BitmapFont(Gdx.files.internal("data/Ubuntu100Solid.fnt"), new TextureRegion(fontImage), false); 
		game.largeFont.setScale(0.8f);
		
		game.mediumFont = new BitmapFont(Gdx.files.internal("data/Ubuntu100Solid.fnt"), new TextureRegion(fontImage), false);		
		game.mediumFont.setScale(0.4f);
		
		LabelStyle titleLabelStyle = new LabelStyle(game.largeFont, Color.WHITE);
		Label titleLogoText = new Label("Omega Orbiter", titleLabelStyle);
		titleLogoText.setPosition((800 - titleLogoText.getWidth()) / 2, 350);
		
		style.font = game.mediumFont;
		style.up = new NinePatchDrawable(buttonUpBG);
		style.down = new NinePatchDrawable(buttonDownBG);
		TextButton campaignButton = new TextButton("Campaign", style);
		TextButton arcadeButton = new TextButton("Arcade", style);
		campaignButton.setPosition(titleLogoText.getX(), 150);
		arcadeButton.setPosition(titleLogoText.getX() + titleLogoText.getWidth() - arcadeButton.getWidth(), 150);				
		
		campaignButton.addListener(new ClickListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.getListenerActor().setColor(Color.RED);
                return true;  // must return true for touchUp event to occur
            }
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LevelSelectionScreen(game));
            }
		});
		
		arcadeButton.addListener(new ClickListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				event.getListenerActor().setColor(Color.RED);
                return true;  // must return true for touchUp event to occur
            }
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LevelSelectionScreen(game));
            }
		});
		
		stage.addActor(titleLogoText);
		stage.addActor(campaignButton);
		stage.addActor(arcadeButton);
		
		
	}

	@Override
	public void hide() {
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

}
