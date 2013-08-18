package com.vishnu.sparrow;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MassiveObject extends Actor {
	public int r;
	public int mass;
	private Texture sprite;
	
	public MassiveObject(int x, int y, int r) {
		super();
		this.setX(x);
		this.setY(y);
		this.r = r;
		this.sprite = Sparrow.assetManager.get("data/planet.png", Texture.class);
	}
	
	public void draw (SpriteBatch batch, float parentAlpha) {
         batch.draw(sprite, getX() - r, getY() - r, r*2, r*2);
	}
	
	public Vector2 getCoords() {
		return new Vector2(this.getX(), this.getY());
	}

}
