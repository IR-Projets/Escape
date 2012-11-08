package entities;


public interface CollisionListener {
	public enum EntityType{
		Enemy,
		Boss,
		Joueur,
		Item,
		WeaponAllied,
		WeaponEnnemy
	}
	
	public EntityType getType();
	public void collision(Entity entity, EntityType type);
}
