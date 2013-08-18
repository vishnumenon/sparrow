package com.vishnu.sparrow;

public class LevelDetails {
	public int levelNumber;
	public int height;
	public int width;
	public MassiveObject[] objects;
	
	public LevelDetails(int levelNumber, int width, int height, MassiveObject[] objects) {
		this.levelNumber = levelNumber;
		this.width = width;
		this.height = height;
		this.objects = objects;
	}
	
	public LevelDetails(int levelNumber) {
		this.levelNumber = levelNumber;
		this.width = 0;
		this.height = 0;
		this.objects = null;
	}
	
}
