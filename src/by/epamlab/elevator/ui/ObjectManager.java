package by.epamlab.elevator.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
	List<DynamicWidget> objects = new ArrayList<DynamicWidget>();
	
	public void add(DynamicWidget object) {
		objects.add(object);
	}
	
	public void drawAll(Graphics target) {
		for (DynamicWidget object : objects) {
			object.draw(target);
		}
	}
	
	public void updateAll(float dt) {
		for (DynamicWidget object : objects) {
			object.update(dt);
		}
	}
}
