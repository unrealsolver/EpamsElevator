package by.epamlab.elevator.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.jsfml.system.Vector2i;

import by.epamlab.elevator.core.ElevationTask;


public class WStoreyArray extends WBase {
	private List<WStorey> storeys = new ArrayList<WStorey>();
	private int[] untransportedDistribution;
	private int[] transportedDistribution;
	private Vector2i offset; //Storey offset
	private int vSize = 10; //Just default value for storey height
	private int targetStorey = -1;
	
	public WStoreyArray(int storeyCount) {
		super();
		init(storeyCount);	
	}
	
	public WStoreyArray(Vector2i position, int storeyCount) {
		super(position);
		init(storeyCount);
	}
	
	public void init(int storeyCount) {
		WStorey storey;
		untransportedDistribution= new int[storeyCount];
		transportedDistribution= new int[storeyCount];
		
		for (int i = 0; i < storeyCount; i++) {
			storey = new WStorey();
			storey.setStoreyNo(i);
			storeys.add(storey);
		}
		
		if (storeys.size() > 0 && storeys.get(0) != null) {
			Vector2i storeySize = storeys.get(0).getSize();
			vSize = storeySize.y;
			origin = new Vector2i(storeySize.x, storeySize.y);
		}

		super.setColor(Color.RED);
		super.setSize(new Vector2i(5,5));
		super.setLineWidth(4);
	}
	
	public void setUntransportedDistribution(int[] distribution) {
		untransportedDistribution = distribution;
	}
	
	public void setTransportedDistribution(int[] distribution) {
		transportedDistribution = distribution;
	}
	
	public void setTargetStorey(int storey) {
		targetStorey = storey;
	}
	
	public void moveToStorey(int level) {
		//Some optimization
		if (targetStorey != level) {
			targetStorey = level;
			
			offset = new Vector2i(0, level*vSize);
			
			int i = 0;
			for(WStorey storey : storeys) {
				storey.setUntransportedCount(untransportedDistribution[i]);
				storey.setTransportedCount(transportedDistribution[i]);
				storey.setPosition(new Vector2i(position.x + offset.x,
						position.y + offset.y - i * vSize));
				i++;
			}
		}
	}
	
	@Override
	public void draw(Graphics target) {
		for(WStorey storey : storeys) {
			storey.draw(target);
		}
		
		//super.draw(target);
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		
		for(WStorey storey : storeys) {
			storey.update(dt);
		}
	}
}
