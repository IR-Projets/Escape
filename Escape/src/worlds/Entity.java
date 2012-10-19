package worlds;

import java.awt.Graphics2D;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public abstract class Entity {

	World world;
	Body body;
	
	public Entity() {
	}
	
	public void setWorld(World world){
		this.world = world;
	}
	
	public void init(float x, float y, float w, float h){
		if(world==null)
			throw new IllegalStateException("Add this entity to an environnement first!");
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(w, h);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x, y);
		bodyDef.allowSleep = false;
		body = world.createBody(bodyDef);
		body.createFixture(polygonShape, 5.0f);

		//body.applyForce(new Vec2(-10000 * (- 1), 0), new Vec2());
	}
	
	public int getX(){
		return (int) body.getLocalCenter().x;
	}
	
	public int getY(){
		return (int) body.getLocalCenter().y;
	}
	
	public void move(float x, float y){
		body.setLinearVelocity(new Vec2(x,y));
	}	
	
	public abstract void render(Graphics2D graphics);
}
