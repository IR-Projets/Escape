package entities;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import listeners.CollisionListener;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

/** This class represents an entity, which is a Body for Jbox.
 * Contains all methods uses for a body to interact with Jbox, for our entity.
 * 
 *  * @author Quentin Bernard et Ludovic Feltz
 */

/* <This program is an Shoot Them up space game, called Escape-IR, made by IR students.>
*  Copyright (C) <2012>  <BERNARD Quentin & FELTZ Ludovic>

*  This program is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.

*  This program is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.

*  You should have received a copy of the GNU General Public License
*  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
*/

public abstract class Entity implements CollisionListener{	
	
	/**
	 * Our Entities.
	 */
	private Entities entities;
	
	/**
	 * The Jbox body associated with our entity.
	 */
	private Body body;

	/**
	 * Initialize an entity, with an Entities and the body of the entity. 
	 * @param entities - class which represents our world
	 * @param body - the Jbox body of our entity
	 */
	public Entity(Entities entities, Body body) {
		this.entities = entities;
		this.body = body;
	}

	/**
	 *  Return the BufferedImage associated with this entity.
	 *  @return the BufferedImage associated with this entity
	 */
	public abstract BufferedImage getImage();
	
	/**
	 * Compute method, uses for launch compute for an entity.
	 */
	public abstract void compute();
	
	/**
	 * Return the class which represents our world, entities.
	 * @return the class which represents our world, entities
	 */
	public Entities getEntities(){
		return entities;
	}
	
	/**
	 * Do the render of a entity.
	 * @param graphics - the graphics2D to print on
	 */
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
	
	/**
	 * Return the angle of the body, in radians.
	 * @return the angle of the body, in radians.
	 */
	public float getRotate(){
		return body.getAngle();
	}	

	/**
	 * Return the Jbox body associated with this entity.
	 * @return the Jbox body associated with this entity.
	 */
	public Body getBody(){
		return body;
	}

	/**
	 * Return the velocity of the entity.
	 * @return the velocity of the entity
	 */
	public Vec2 getVelocity(){
		return body.getLinearVelocity();
	}
	
	/**
	 * Set the collisionGroup of an entity, using CollisionGroup.
	 * @param entityType - the entityType corresponding to the good collisionGroup
	 * @see CollisionGroup
	 */
	public void setCollisionGroup(EntityType entityType){
		CollisionGroup.setCollisionGroup(body, entityType);
	}

	/**
	 * Set the velocity of our entity.
	 * @param speedX - the speed associated with x position
	 * @param speedY - the speed associated with y position
	 */
	public void setVelocity(float speedX, float speedY){
		body.setLinearVelocity(new Vec2(speedX, speedY));
	}
	
	/**
	 * Set the linear damping of an entity.
	 * @param linearDamping - the linear damping to set
	 */
	public void setDamping(float linearDamping){
		body.setLinearDamping(linearDamping);
	}
	
	/**
	 * 
	 * @return
	 */
	public Vec2 getScreenPostion(){
		return new Vec2(body.getPosition().x - getImage().getWidth()/2, 
				Variables.SCREEN_HEIGHT - (body.getPosition().y + getImage().getHeight()/2));
	}

	/**
	 * 
	 * @return
	 */
	public Vec2 getPositionNormalized(){
		return new Vec2(body.getPosition().x - getImage().getWidth()/2, 
				body.getPosition().y + getImage().getHeight()/2);
	}
	
	/**
	 * 
	 * @return
	 */
	public Vec2 getWorldPosition(){
		return body.getPosition();
	}

	/**
	 * 
	 * @param point
	 * @return
	 */
	public boolean isOnSprite(Vec2 point){
		Vec2 position = getScreenPostion();
		return point.x>position.x && point.x<position.x+getImage().getWidth() &&
				point.y>position.y && point.y<position.y+getImage().getHeight();
	}	

	/**
	 * 
	 */
	public void stop(){
		setVelocity(0,0);
	}
	
	/**
	 * 
	 * @param isSensor
	 */
	public void setSensor(boolean isSensor){
		body.getFixtureList().setSensor(isSensor);
	}
	
	
	/*
	 * Les body:
	 */
	
	/**
	 * 
	 * @author kiouby
	 *
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
	
	/**
	 * 
	 * @param entities
	 * @param posX
	 * @param posY
	 * @param width
	 * @param height
	 * @return
	 */
	private static Body getSquareBody(Entities entities, int posX, int posY, int width, int height){		
		PolygonShape box = new PolygonShape();		
		box.setAsBox(width/2, height/2);
		return getBody(entities, box, posX, posY);
	}
	
	/**
	 * 
	 * @param entities
	 * @param posX
	 * @param posY
	 * @param width
	 * @param height
	 * @return
	 */
	private static Body getCircleBody(Entities entities, int posX, int posY, int width, int height){		
		CircleShape circle = new CircleShape();
		circle.m_radius = Math.min(width, height)/2;
		return getBody(entities, circle, posX, posY);
	}
	
	/**
	 * 
	 * @param entities
	 * @param posX
	 * @param posY
	 * @param width
	 * @param height
	 * @return
	 */
	private static Body getPolygonBody(Entities entities, int posX, int posY, int width, int height){	
		int w = (int) width/2;
		int h = (int) height/2;
		
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
	
	/**
	 * 
	 * @param entities
	 * @param shape
	 * @param posX
	 * @param posY
	 * @return
	 */
	private static Body getBody(Entities entities, Shape shape, int posX, int posY){
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.linearDamping=0.0f;
		bodyDef.angularDamping=0.5f;
		bodyDef.allowSleep = false;
		bodyDef.position.set(posX, posY);
		Body body = entities.getWorld().createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.3f;		
		body.createFixture(fixtureDef);
		
		return body;
	}

}
