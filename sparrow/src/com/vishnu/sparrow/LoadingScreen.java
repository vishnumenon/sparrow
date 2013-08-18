package com.vishnu.sparrow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen {
	private Sparrow game;
	private BitmapFont font;
	private SpriteBatch batch;
	
	public LoadingScreen(Sparrow game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if(Sparrow.assetManager.update()) {
			game.setScreen(new StartScreen(game));
	     }
		
		batch.begin();
		font.draw(batch, "Loading: " + Sparrow.assetManager.getProgress() / 1.0 + "%", 60, 60);
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
	
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		Texture.setEnforcePotImages(false);
		

		TextureParameter linearFilter = new TextureParameter();
		linearFilter.minFilter = TextureFilter.Linear;
		linearFilter.magFilter = TextureFilter.Linear;
		
		Sparrow.assetManager.load("data/grass.jpg", Texture.class);
		Sparrow.assetManager.load("data/galaxies.jpg", Texture.class);
		Sparrow.assetManager.load("data/title_bg.mp3", Music.class);
		Sparrow.assetManager.load("data/outlinebutton.9.png", Texture.class, linearFilter);
		Sparrow.assetManager.load("data/Ubuntu100Solid.png", Texture.class, linearFilter);
//		game.assetManager.load("data/Ubuntu100Solid.fnt", BitmapFont.class);
		Sparrow.assetManager.load("data/planet.png", Texture.class);
		Sparrow.assetManager.load("data/arrow.png", Texture.class);
				

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

}
