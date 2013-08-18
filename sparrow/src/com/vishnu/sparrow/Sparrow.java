package com.vishnu.sparrow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Sparrow extends Game {
	private boolean soundOn = true;
	public static AssetManager assetManager;
	public BitmapFont largeFont;
	public BitmapFont mediumFont;
	
	
	@Override
	public void create() {	
		assetManager = new AssetManager();
		setScreen(new LoadingScreen(this));
	}
	
	public void toggleSound() {
		this.soundOn = !this.soundOn;
	}
	
	public boolean getSoundOn() {
		return this.soundOn;
	}
}
