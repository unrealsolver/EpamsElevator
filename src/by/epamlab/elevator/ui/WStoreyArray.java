package by.epamlab.elevator.ui;

import java.awt.Graphics;

import org.jsfml.system.Vector2i;

public class WStoreyArray extends WBase {
	ObjectManager storeys = new ObjectManager();
	
	public WStoreyArray(int storeyCount) {
		super();
		init(storeyCount);	
	}
	
	public WStoreyArray(Vector2i position, int storeyCount) {
		super(position);
		init(storeyCount);
	}
	
	public void init(int storeyCount) {
		Vector2i storeyPosition;
		
		for (int i = 0; i < storeyCount; i++) {
			storeyPosition = new Vector2i(position.x, position.y + i*150); //FIXME by image size
			storeys.add(new WStorey(storeyPosition));
		}
	}
	
	@Override
	public void draw(Graphics target) {
		super.draw(target);
		storeys.drawAll(target);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		storeys.updateAll(dt);
	}
}
