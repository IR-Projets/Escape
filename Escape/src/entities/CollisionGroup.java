package entities;

import org.jbox2d.dynamics.Body;

import listeners.CollisionListener.EntityType;

public class CollisionGroup {

	public final static int PLAYER_COLLISION = 1;
	public final static int ENEMY_COLLISION = 2;
	public final static int WEAPON_PLAYER_COLLISION = 4;
	public final static int WEAPON_ENEMY_COLLISION = 8;
	public final static int BOSS_COLLISION = 16;
	public final static int BONUS_COLLISION = 32;
	public final static int WORLD_LIMIT_COLLISION = 64;
	
	public static void setCollisionGroup(Body body, EntityType entityType){
		switch (entityType) {
		case Joueur:
			body.getFixtureList().getFilterData().categoryBits = PLAYER_COLLISION;
			body.getFixtureList().getFilterData().maskBits = ENEMY_COLLISION | BOSS_COLLISION | WEAPON_ENEMY_COLLISION | BONUS_COLLISION | WORLD_LIMIT_COLLISION;
			break;
		case Enemy:
			body.getFixtureList().getFilterData().categoryBits = ENEMY_COLLISION;
			body.getFixtureList().getFilterData().maskBits = PLAYER_COLLISION | WEAPON_PLAYER_COLLISION | WORLD_LIMIT_COLLISION;
			break;			
		case WeaponPlayer:
			body.getFixtureList().getFilterData().categoryBits = WEAPON_PLAYER_COLLISION;
			body.getFixtureList().getFilterData().maskBits = ENEMY_COLLISION | BOSS_COLLISION | WEAPON_ENEMY_COLLISION | WORLD_LIMIT_COLLISION;
			break;				
		case WeaponEnnemy:
			body.getFixtureList().getFilterData().categoryBits = WEAPON_ENEMY_COLLISION;
			body.getFixtureList().getFilterData().maskBits = PLAYER_COLLISION | WEAPON_PLAYER_COLLISION | WORLD_LIMIT_COLLISION;
			break;
		case Boss:
			body.getFixtureList().getFilterData().categoryBits = BOSS_COLLISION;
			body.getFixtureList().getFilterData().maskBits = PLAYER_COLLISION | WEAPON_PLAYER_COLLISION;
			break;
		case Bonus:
			body.getFixtureList().getFilterData().categoryBits = BONUS_COLLISION;
			body.getFixtureList().getFilterData().maskBits = PLAYER_COLLISION | WORLD_LIMIT_COLLISION;
			break;
		case WorldLimit:
			body.getFixtureList().getFilterData().categoryBits = WORLD_LIMIT_COLLISION;
			body.getFixtureList().getFilterData().maskBits = PLAYER_COLLISION | ENEMY_COLLISION | WEAPON_PLAYER_COLLISION | WEAPON_ENEMY_COLLISION | BONUS_COLLISION;
			break;
		default:
			break;
		}
	}
}
