package entities;

import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public abstract class Entity {

	private final Body body;

	public Entity(Entities entities, int posX, int posY, int width, int height) {
		body = createBody(entities.getWorld(), posX, posY, width, height);
		entities.addEntity(this);
	}

	public abstract BufferedImage getImage();// The Image use for render -> for action like loop, ...
	public abstract void compute();
	
	
	public void render(Graphics2D graphics){
		AffineTransform tx = new AffineTransform();
		tx.rotate(getRotate(), getImage().getWidth()/2, getImage().getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
		
		Vec2 pos = getScreenPostion();

		graphics.drawImage(op.filter(getImage(), null),
				(int)pos.x,
				(int)pos.y,
				getImage().getWidth(), 
				getImage().getHeight(),
				null );
	}
	
	public float getRotate(){
		return body.getAngle();
	}	

	public Body getBody(){
		return body;
	}

	public Vec2 getVelocity(){
		return body.getLinearVelocity();
	}
	
	public Vec2 getScreenPostion(){
		return new Vec2(toScreenSize(body.getPosition().x) - getImage().getWidth()/2, 
				Variables.SCREEN_HEIGHT - (toScreenSize(body.getPosition().y) + getImage().getHeight()/2));
	}

	public Vec2 getPositionNormalized(){
		return new Vec2(toScreenSize(body.getPosition().x) - getImage().getWidth()/2, 
				toScreenSize(body.getPosition().y) + getImage().getHeight()/2);
	}

	public boolean isOnSprite(Vec2 point){
		Vec2 position = getScreenPostion();
		return point.x>position.x && point.x<position.x+getImage().getWidth() &&
				point.y>position.y && point.y<position.y+getImage().getHeight();
	}

	public void setVelocity(float speedX, float speedY){
		body.setLinearVelocity(new Vec2(toWorldSize(speedX), toWorldSize(speedY)));
	}	

	public void stop(){
		setVelocity(0,0);
	}
	
	
	
	
	/*
	 * Methodes utiles statiques
	 */
	public static int toScreenSize(float val){
		return (int) (val * Variables.WORLD_SCALE);
	}

	public static float toWorldSize(float val){
		return val / Variables.WORLD_SCALE;
	}
	
	protected static Body createBody(World world, int posX, int posY, int width, int height){
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(Entity.toWorldSize(width), Entity.toWorldSize(height));
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(Entity.toWorldSize(posX), Entity.toWorldSize(posY));
		bodyDef.allowSleep = false;
		Body body = world.createBody(bodyDef);
		body.createFixture(polygonShape, 1.0f);
		return body;
	}
	

}
