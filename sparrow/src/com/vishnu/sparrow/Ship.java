package com.vishnu.sparrow;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ship extends Actor {
	private TextureRegion sprite;
	private float speed;
	private float angle;
	public MassiveObject orbitingPlanet;
	private int clockwise;
	private float orbitRadius;
	private double t;
	private float flipX;
	private float flipY;

	public Ship(int x, int y, float speed, float angle) {
		super();
		this.setX(x);
		this.setY(y);
		this.sprite = new TextureRegion(Sparrow.assetManager.get("data/arrow.png", Texture.class));
		this.speed = speed;
		this.angle = angle;
		this.setOrigin(getWidth()/2, getHeight()/2);
		this.clockwise = -1;
		this.flipX = 1;
		this.flipY = 1;
		this.t = angle * Math.PI/180;
	}

	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		batch.draw(sprite, getX() - 25 / 2, getY() - 25 / 2, 0f, 0f, 25f, 25f, 1f, 1f, angle, clockwise == -1);
	}
	
	public Vector2 getCoords() {
		return new Vector2(this.getX() - 25/2 , this.getY() - 25/2 );
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if(GameScreen.attracting) {
			flipX = 1;
			flipY = 1;
			if(GameScreen.justTapped) {
				t = Math.atan2(getY() - orbitingPlanet.getY(), getX() - orbitingPlanet.getX());
				clockwise = getY() > orbitingPlanet.getY() ? -1 : 1;
				orbitRadius = distanceTo(orbitingPlanet);
				GameScreen.justTapped = false;
			}
			
			t += speed*delta*clockwise / orbitRadius;
			
			float newX = (float) (orbitRadius * Math.cos(t)) + orbitingPlanet.getX();
			float newY = (float) (orbitRadius * Math.sin(t)) + orbitingPlanet.getY();
			if(newX > GameScreen.level.width || newX*newY < 0 || newY > GameScreen.level.height) { 
				clockwise = -1 * clockwise;
			} else {
				this.setX((float) (orbitRadius * Math.cos(t)) + orbitingPlanet.getX());
				this.setY((float) (orbitRadius * Math.sin(t)) + orbitingPlanet.getY());
				this.angle = (float) (t * 180/Math.PI);
			}
		} else {
			orbitingPlanet = GameScreen.level.objects[0];
			for(int o = 1; o < GameScreen.level.objects.length; o++) {
				if(distanceTo(GameScreen.level.objects[o]) < distanceTo(orbitingPlanet)) {
					orbitingPlanet = GameScreen.level.objects[o];
				}
			}
			float newX = this.getX() + (float)(flipX*clockwise*speed*delta*Math.cos(t+(Math.PI/2)));
			float newY = this.getY() + (float)(flipY*clockwise*speed*delta*Math.sin(t+(Math.PI/2)));
			
			if (newX < 0 || newX > GameScreen.level.width || newY < 0 || newY > GameScreen.level.height) {
				if(newX < 0 || newX > GameScreen.level.width) {
					flipX = -1 * flipX;
				}
				if(newY < 0 || newY > GameScreen.level.height) {
					flipY = -1 * flipY;
				}
			} else {
				this.setX(newX);
				this.setY(newY);
			}
		}
	}
	
	public float t() {
		
		double val = t % (Math.PI*2);
		return (float) (val < 0 ? 2*Math.PI + val : val);
	}
	public float myTan(double t) {
		
		if((t % (Math.PI/2)) == 0.0) {
			return 0; 
		} else {
			return (float) Math.tan(t);
		}
	}
	
	public float distanceTo(MassiveObject planet) {
		return (float) Math.sqrt(Math.pow(getY() - planet.getY(), 2) + Math.pow(getX() - planet.getX(), 2));
	}
	
	public double slopeTo(MassiveObject planet) {
		return (planet.getY() - getY()) / (planet.getX() - getX()); 
	}
	
	public float camCenterX() {
		return GameScreen.attracting && !GameScreen.justTapped ? orbitingPlanet.getX() : getX();
	}
	
	public float camCenterY() {
		return GameScreen.attracting && !GameScreen.justTapped ? orbitingPlanet.getY() : getY();
	}

}
