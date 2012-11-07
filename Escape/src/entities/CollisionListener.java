package entities;


public interface CollisionListener {
	public enum EntityType{
		Enemy,
		Boss,
		Joueur,
		Item,
		Weapon
	}
	
	public EntityType getType();
	public void collision(Entity entity, EntityType type);
}
