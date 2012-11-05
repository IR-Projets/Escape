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

public abstract class Entitie {

	private final Body body;
	private final BufferedImage image;
	private final World world;

	public Entitie(World world, String nameImage, float x, float y) {
		this.image = Ressources.getImage(nameImage);
		this.world=world;
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(toWorldSize(image.getWidth()/2), toWorldSize(image.getHeight()/2));
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(x / Variables.WORLD_SCALE, y / Variables.WORLD_SCALE);
		bodyDef.allowSleep = false;
		body = world.createBody(bodyDef);
		body.createFixture(polygonShape, 1.0f);
	}

	public abstract BufferedImage getImageRender();// The Image use for render -> for action like loop, ...
	public abstract void compute();
	
	
	public void render(Graphics2D graphics){
		AffineTransform tx = new AffineTransform();
		tx.rotate(getRotate(), getImageRender().getWidth()/2, getImageRender().getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
		
		Vec2 pos = getScreenPostion();

		graphics.drawImage(op.filter(getImageRender(), null),
				(int)pos.x,
				(int)pos.y,
				getImageRender().getWidth(), 
				getImageRender().getHeight(),
				null );
	}

	public BufferedImage getImage() {
		return image;
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
		return new Vec2(toScreenSize(body.getPosition().x) - getImageRender().getWidth()/2, 
				Variables.SCREEN_HEIGHT - (toScreenSize(body.getPosition().y) + getImageRender().getHeight()/2));
	}

	public Vec2 getPositionNormalized(){
		return new Vec2(toScreenSize(body.getPosition().x) - getImageRender().getWidth()/2, 
				toScreenSize(body.getPosition().y) + getImageRender().getHeight()/2);
	}

	public boolean isOnSprite(Vec2 point){
		Vec2 position = getScreenPostion();
		return point.x>position.x && point.x<position.x+getImageRender().getWidth() &&
				point.y>position.y && point.y<position.y+getImageRender().getHeight();
	}

	public void setVelocity(float speedX, float speedY){
		body.setLinearVelocity(new Vec2(toWorldSize(speedX), toWorldSize(speedY)));
	}	

	public void stop(){
		setVelocity(0,0);
	}

	/*
	 * Les mettre a par ??*/
	public int toScreenSize(float val){
		return (int) (val * Variables.WORLD_SCALE);
	}

	public float toWorldSize(float speedX){
		return speedX / Variables.WORLD_SCALE;
	}

	
	public World getWorld() {
		return world;
	}


}
