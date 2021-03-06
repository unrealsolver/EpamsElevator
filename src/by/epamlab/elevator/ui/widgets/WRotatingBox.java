package by.epamlab.elevator.ui.widgets;

import java.util.Random;

import org.jsfml.system.Vector2i;

/**
 * Just for fun
 * @author Ruslan_Panasiuk
 *
 */
public class WRotatingBox extends WBase {
		private Vector2i rotationCenter = new Vector2i(125, 125);
		private float rotation = (new Random()).nextFloat()*20;
		private int radius = 100;
		
		public WRotatingBox() {
			super();
		}
		
		public WRotatingBox(Vector2i position) {
			super(position);
		}
		
		@Override
		public void update(float dt) {
			rotation += dt/200000;
			position = new Vector2i((int) (rotationCenter.x + Math.cos(rotation)*radius),
									(int) (rotationCenter.y + Math.sin(rotation)*radius));
		}
}
