package by.epamlab.elevator.ui;

import java.util.Random;

import org.jsfml.system.Vector2i;

public class WRotatingBox extends BaseWidget {
		private Vector2i rotationCenter = new Vector2i(125, 125);
		private float rotation = (new Random()).nextFloat()*20;
		private int radius = 100;
		
		@Override
		public void update(float dt) {
			rotation += dt/200000;
			position = new Vector2i((int) (rotationCenter.x + Math.cos(rotation)*radius),
									(int) (rotationCenter.y + Math.sin(rotation)*radius));
		}
}
