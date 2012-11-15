package entities;

import factories.WeaponFactory.WeaponType;
import game.Ressources;
import game.Variables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import listeners.CollisionListener;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.FrictionJointDef;

public abstract class Entity implements CollisionListener{	
	private Entities entities;
	private Body body;

	public Entity(Entities entities, Body body) {
		this.entities = entities;
		this.body = body;//entities.createBody(posX, posY, width, height);
		
		//TODO refactoriser
		//C'est pas bien mais une autre solution prendrai trop de temps a refactoriser (on a pas de thread donc theoriquement pas de problemes) (ps: c'est le seul endroit du programme ou cela est fait...)
		//entities.addEntity(this);
	}

	public abstract BufferedImage getImage();// The Image use for render -> for action like loop, ...
	public abstract void compute();
	
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
	
	public void addtoCollisionGroup(EntityType entityType){
		CollisionGroup.setCollisionMask(body, entityType);
	}

	public void setVelocity(float speedX, float speedY){
		/*Vec2 f = getBody().getWorldVector(new Vec2(300.0f, 300.0f));
		Vec2 p = getBody().getWorldCenter();
		getBody().applyForce(f, p);*/
		body.setLinearVelocity(new Vec2(toWorldSize(speedX)*Variables.WORLD_SCALE, toWorldSize(speedY)*Variables.WORLD_SCALE));
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
		
		public Body get(Entities entities, int posX, int posY, int width, int height){
			switch(this){
			case Square:
				return getSquareBody(entities, posX, posY, width, height);
			case Circle:
				return getCircleBody(entities, posX, posY, width, height);
			case Polygon:
				return getPolygonBody(entities, posX, posY, width, height);
			}
			return null;
		}
	}
	
	public static Body getSquareBody(Entities entities, int posX, int posY, int width, int height){		
		PolygonShape box = new PolygonShape();		
		box.setAsBox(Entity.toWorldSize(width/2), Entity.toWorldSize(height/2));
		return getBody(entities, box, posX, posY);
	}
	
	
	public static Body getCircleBody(Entities entities, int posX, int posY, int width, int height){		
		CircleShape circle = new CircleShape();
		circle.m_radius = Entity.toWorldSize(Math.min(width, height)/2);
		return getBody(entities, circle, posX, posY);
	}
	
	
	public static Body getPolygonBody(Entities entities, int posX, int posY, int width, int height){	
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
		return getBody(entities, polygon, posX, posY);
	}
	
	
	private static Body getBody(Entities entities, Shape shape, int posX, int posY){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.linearDamping=0.1f;
		bodyDef.angularDamping=0.5f;
		bodyDef.position.set(Entity.toWorldSize(posX), Entity.toWorldSize(posY));
		bodyDef.allowSleep = false;
		Body body = entities.getWorld().createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;
		
		body.createFixture(fixtureDef);
		
		return body;
	}

}
