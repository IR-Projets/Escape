package worlds;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Entity {

	World world;
	Body body;
	
	public Entity(World world) {
		this.world = world;
	}
	
	public Entity(){
		this(Environnement.get().getWorld());
	}
	
	public void init(float x, float y, float w, float h){
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
	
	public float getX(){
		return body.getLocalCenter().x;
	}
	
	public float getY(){
		return body.getLocalCenter().y;
	}
	
	public void move(float x, float y){
		body.setLinearVelocity(new Vec2(x,y));
	}	
}
