package ships.enemies;

import java.io.IOException;

import ships.Ship;

public class Enemy extends Ship{

	public Enemy() throws IOException {
		super();
	}

	@Override
	protected String getImageURL() {
		return "images\\dirtyDick.png";
	}

	@Override
	public void compute() {
		// TODO Auto-generated method stub
		
	}

}
