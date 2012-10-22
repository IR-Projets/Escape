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
	
	
	public void init(World world, float x, float y){
		this.world = world;
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(getWidth(), getHeight());

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x, y);
		bodyDef.allowSleep = false;
		body = world.createBody(bodyDef);
		body.createFixture(polygonShape, 1.0f);
	}
	
	public int getX(){
		return (int) body.getPosition().x - getWidth();
	}
	
	public int getY(){
		return (int) body.getPosition().y;
	}
	
	public float getRotate(){
		return body.getAngle();
	}
	
	public void move(float x, float y){
		body.setLinearVelocity(new Vec2(x,y));
	}	
	
	
	/*
	 * Methodes abstraites
	 */
	public abstract int getHeight();
	public abstract int getWidth();
	
	public abstract void render(Graphics2D graphics);
	public abstract void compute();
	
	
	
	public void debug(){
	     //world.step(TIME_STEP, VELOCITY_ITERATION, POSITION_ITERATION);
	     Vec2 position = body.getPosition();
	     float angle = body.getAngle();
	     System.out.printf("X:%4.2f, Y:%4.2f, Angle:%4.2f\n", position.x, position.y, angle);
	     //System.out.printf("X:%4.2f, Y:%4.2f, Angle:%4.2f\n", getX(), getY(), angle);
	}
}
