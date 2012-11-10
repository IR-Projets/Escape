package entities;

import entities.weapons.WeaponFactory.WeaponType;
import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public abstract class Entity implements CollisionListener{	
	private Body body;
	private Entities entities;

	public Entity(Entities entities, Body body) {
		this.entities = entities;
		this.body = body;//entities.createBody(posX, posY, width, height);
		
		//TODO refactoriser
		//C'est pas bien mais une autre solution prendrai trop de temps a refactoriser (on a pas de thread donc theoriquement pas de problemes) (ps: c'est le seul endroit du programme ou cela est fait...)
		//entities.addEntity(this);
	}

	public abstract BufferedImage getImage();// The Image use for render -> for action like loop, ...
	public abstract void compute();
	public abstract EntityType getType();
	
	public Entities getEntities(){
		return entities;
	}
	  
	
	public void render(Graphics2D graphics){
		AffineTransform tx = new AffineTransform();
		tx.rotate(getRotate(), getImage().getWidth()/2, getImage().getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
		BufferedImage imageRotated = op.filter(getImage(), null);
		
		Vec2 pos = getScreenPostion();
		
		
		
		graphics.drawImage(imageRotated,
				(int)pos.x,
				(int)pos.y,
				imageRotated.getWidth(), 
				imageRotated.getHeight(),
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
		body.setLinearVelocity(new Vec2(toWorldSize(speedX)*Variables.WORLD_SCALE, toWorldSize(speedY)*Variables.WORLD_SCALE));
	}	

	public void stop(){
		setVelocity(0,0);
	}
	
	public void setSensor(boolean isSensor){
		body.getFixtureList().setSensor(isSensor);
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
	
	
	/*
	 * Les body:
	 */
	public enum EntityShape{
		Square,
		Circle,
		Polygon;
		
		public Body get(World world, int posX, int posY, int width, int height){
			switch(this){
			case Square:
				return getSquareBody(world, posX, posY, width, height);
			case Circle:
				return getCircleBody(world, posX, posY, width, height);
			case Polygon:
				return getPolygonBody(world, posX, posY, width, height);
			}
			return null;
		}
	}
	
	public static Body getSquareBody(World world, int posX, int posY, int width, int height){		
		PolygonShape box = new PolygonShape();		
		box.setAsBox(Entity.toWorldSize(width/2), Entity.toWorldSize(height/2));
		return getBody(world, box, posX, posY);
	}
	
	
	public static Body getCircleBody(World world, int posX, int posY, int width, int height){		
		CircleShape circle = new CircleShape();
		circle.m_radius = Entity.toWorldSize(Math.min(width, height)/2);
		return getBody(world, circle, posX, posY);
	}
	
	
	public static Body getPolygonBody(World world, int posX, int posY, int width, int height){	
		int w = (int) Entity.toWorldSize(width)/2;
		int h = (int) Entity.toWorldSize(height)/2;
		
		Vec2[] vertices = new Vec2[]
		{
			new Vec2(-w, 0),		//Gauche
			new Vec2(0, -h),	//Bas		
			new Vec2(w, 0),		//Droite
			new Vec2(0, h)		//Haut					
		};
		
		PolygonShape polygon = new PolygonShape();	
		polygon.set(vertices, 4);
		return getBody(world, polygon, posX, posY);
	}
	
	
	private static Body getBody(World world, Shape shape, int posX, int posY){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(Entity.toWorldSize(posX), Entity.toWorldSize(posY));
		bodyDef.allowSleep = false;
		Body body = world.createBody(bodyDef);
		body.createFixture(shape, 1.0f);
		return body;
	}

}
