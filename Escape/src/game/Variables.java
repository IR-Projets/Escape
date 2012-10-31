package game;

import java.awt.Color;



public class Variables {

	public final static boolean DEBUG = true;
	
	public final static String APPLICATION_NAME = "Escape IR";
	public final static int SCREEN_WIDTH = 500;
	public final static int SCREEN_HEIGHT = 800;
	
	public final static Color GREEN = new Color(0,125,0);
	public final static Color RED = new Color(255,0,0);
	public final static Color BLUE = new Color(0,0,255);
	public final static Color ORANGE = new Color(240,80,40);
	
	public final static int SPEED_MAIN_SHIP = 100;/* Speed of the main ship */
	
	public final static int TRACE_DELETE_RATE = 5;/*If we increase the value, the delete time of Trace become lower*/
	public final static double TRACE_VARIATION_MAX = 30;/* The limit intervalle to accept movement -> Angle Bornes */
	public static final int TRACE_LENGTH_MIN = 4;
	
	
	public final static float WORLD_SCALE = 20;
	public static final float WORLD_GRAVITY_X = 0;
	public static final float WORLD_GRAVITY_Y = 0;

	public static final float WORLD_TIME_STEP = 1.0f / 60.f;
	public static final int WORLD_VELOCITY_ITERATION = 10;
	public static final int WORLD_POSITION_ITERATION = 8;
	
	
	public static float LINK_FREQUENCY = .005f;	//Duret?e plus c'est petit, plus c'est dur
	public static float LINK_DAMPING = 0.001f;	//Ressort plus c'est petit, moins ia de ressort
	
	public final static int MAX_LIFE = 100;
	/*
	 * 
	 */
	
	
}
