package listeners;

import entities.Entity;


public interface EntityListener {	
	public enum EntityType{
		Enemy,
		Boss,
		Joueur,
		Item,
		WeaponPlayer,
		WeaponEnnemy, 
		WorldLimit
	}
	
	
	public EntityType getType();
	public void collision(Entity entity, EntityType type);
}
