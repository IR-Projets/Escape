package listeners;

import entities.Entity;


public interface CollisionListener {	
	public enum EntityType{
		Enemy,
		Boss,
		Joueur,
		Item,
		WeaponPlayer,
		WeaponEnnemy, 
		WorldLimit;
		
	}
	
	
	public EntityType getEntityType();
	public int getDamage();
	public void collision(Entity entity, EntityType type);
}
