package by.epamlab.elevator.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import by.epamlab.elevator.ui.widgets.DynamicWidget;

public class ObjectManager {
	private final List<DynamicWidget> objects = new LinkedList<DynamicWidget>();
	
	public void add(DynamicWidget object) {
		objects.add(object);
	}
	
	public void addLast(DynamicWidget object) {
		((LinkedList<DynamicWidget>) objects).addFirst(object);
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
