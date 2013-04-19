package by.epamlab.elevator.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.jsfml.system.Vector2i;

public class WStoreyArray extends WBase {
	private List<WStorey> storeys = new ArrayList<WStorey>();
	private int vSize = 10;
	
	public WStoreyArray(int storeyCount) {
		super();
		init(storeyCount);	
	}
	
	public WStoreyArray(Vector2i position, int storeyCount) {
		super(position);
		init(storeyCount);
	}
	
	public void init(int storeyCount) {
		
		for (int i = 0; i < storeyCount; i++) {
			storeys.add(new WStorey());
		}
		
		if (storeys.size() > 0 && storeys.get(0) != null) {
			Vector2i storeySize = storeys.get(0).getSize();
			vSize = storeySize.y;
			origin = new Vector2i(storeySize.x, storeys.size()*vSize);
		}

		this.setPosition(this.position);
	}
	
	@Override
	public void setPosition(Vector2i position) {
		super.setPosition(position);

		int i = 0;
		for(WStorey storey : storeys) {
			storey.setPosition(new Vector2i(position.x - origin.x,
					position.y - i++ * vSize));
		}
	}
	
	public void moveToStorey(int storey) {
		setPosition(new Vector2i(position.x, position.y + storey * vSize));
	}
	
	@Override
	public void draw(Graphics target) {
		super.draw(target);
		
		for(WStorey storey : storeys) {
			storey.draw(target);
		}
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		
		for(WStorey storey : storeys) {
			storey.update(dt);
		}
	}
}
