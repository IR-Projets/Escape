package game;

import java.awt.Color;



public class Variables {

	public final static boolean DEBUG = true;
	
	/*
	 * Application variables
	 */
	public static final String APPLICATION_NAME = "Escape IR";
	public static final int SCREEN_WIDTH = 500;
	public static final int SCREEN_HEIGHT = 700;
	
	
	/*
	 * Render variables
	 */
	public static final int TICKS_PER_SECOND = 25;
	public static final int SKIP_TICKS = 300 / TICKS_PER_SECOND;
	public static final int MAX_FRAMESKIP = 5;
	
	
	/*
	 * Some static colors
	 */
	public final static Color GREEN = new Color(0,125,0);
	public final static Color RED = new Color(255,0,0);
	public final static Color BLUE = new Color(0,0,255);
	public final static Color WHITE = new Color(255,255,255);
	public final static Color BLACK = new Color(0,0,0);
	
	
	/*
	 * Main ship
	 */
	public final static int SPEED_MAIN_SHIP = 150;/* Speed of the main ship */
	public final static int SPEED_WEAPON = 200;/* Speed of the main ship */
	public final static int MAX_LIFE = 100;
	
	
	/*
	 * Gesture variables
	 */
	public final static int TRACE_DELETE_RATE = 3;/*If we increase the value, the delete time of Trace become lower*/
	public final static double TRACE_VARIATION_MAX = 30;/* The limit intervalle to accept movement -> Angle Bornes */
	public static final int TRACE_LENGTH_MIN = 4;
	
	
	/*
	 * World variables
	 */
	public final static float WORLD_SCALE = 1;
	public static final float WORLD_GRAVITY_X = 0;
	public static final float WORLD_GRAVITY_Y = 0;

	public static final float WORLD_TIME_STEP = 1.0f / 60.f;
	public static final int WORLD_VELOCITY_ITERATION = 10;
	public static final int WORLD_POSITION_ITERATION = 8;

	public static final float WORLD_BORDER = 50;
	
	
	
}
