package game;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import org.jbox2d.common.Vec2;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;
import game.EnvironnementFactory.Level;



public class Test extends TestbedTest implements GameStateListener{

	private static final String COMPUTE_RATE = "Compute rate";
	private static final String TIME_STEP = "Time step";
	private static final String VELOCITY_OP = "Velocity operation";
	private static final String POSITION_IT = "Position Iteration";
	private Environnement env = null;
	//reflexion pour avoir accès au constructeur protected de MotionEvent
	Constructor eventConstructor;
	Level level;
	
	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("#### TEST #####");

		level = Level.Moon;
		env = EnvironnementFactory.factory(getWorld(), level);
		env.addListener(this);
		this.setCamera(new Vec2( (Variables.SCREEN_WIDTH/2)/Variables.WORLD_SCALE, (Variables.SCREEN_HEIGHT/2)/Variables.WORLD_SCALE), Variables.WORLD_SCALE*0.5f);	
		
		
		try {
			eventConstructor = MotionEvent.class.getDeclaredConstructor(int.class, int.class, Kind.class);
			eventConstructor.setAccessible(true);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}		
	}
	

	@Override
	public void stateChanged(GameState state) {
		switch(state){
		case Loose:
			System.out.println("Perdu !!");
			System.exit(0);
			break;
		case Win:
			System.out.println("Gagné !!");
			//le niveau que l'on vient de gagner
			switch(level){
			case Jupiter:
				env = EnvironnementFactory.factory(Level.Moon);
				break;
			case Moon:
				env = EnvironnementFactory.factory(Level.Earth);
				break;
			case Earth:
				System.out.println("Jeu fini!! pas encore implémenté");
				break;
			}			
			env.addListener(this);
			break;
		}		
	}
	
	int count=0;
	  @Override
	  public void step(TestbedSettings settings) {
	    super.step(settings); // make sure we update the engine!
	    
	    TestbedSetting computeRate = settings.getSetting(COMPUTE_RATE); // grab our setting
	    TestbedSetting timeStep = settings.getSetting(TIME_STEP); // grab our setting
	    TestbedSetting velocityIterations = settings.getSetting(VELOCITY_OP); // grab our setting
	    TestbedSetting positionIterations = settings.getSetting(POSITION_IT); // grab our setting
	    
	    count++;
	    if(count>computeRate.value){
	    	count=0;
		    env.compute(timeStep.value/60.f, velocityIterations.value, positionIterations.value);
	    }	  
	    
	  }
	 
	public void event(int x, int y, Kind kind){		
		if(x<0 || y<0)
			return;
		
		x = (int) (x * Variables.WORLD_SCALE);
		y = Variables.SCREEN_HEIGHT - (int)(y * Variables.WORLD_SCALE);

		MotionEvent event = null;
		try {
			event = (MotionEvent) eventConstructor.newInstance(x, y, kind);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		env.event(event);
	}
	
	  
	boolean clicked = false;
	@Override
	public void mouseUp(Vec2 p){	
		clicked=false;
		event((int)p.x, (int)p.y, Kind.ACTION_UP);
	}
	@Override
	public void mouseDown(Vec2 p){
		clicked=true;
		event((int)p.x, (int)p.y, Kind.ACTION_DOWN);
	}
	@Override
	public void mouseMove(Vec2 p){
		if(clicked)
			event((int)p.x, (int)p.y, Kind.ACTION_MOVE);
	}
	  
	
	
	
	
	
	@Override
	public String getTestName() {
		return "#### TEST #####";
	}
	

	public static void main(String args[]){
		final TestbedModel model = new TestbedModel();         	// create our model

		Test test = new Test();
		
		// add tests
		model.addCategory("Tests personnels");             // add a category
		model.addTest(test);                		   // add our test
		TestList.populateModel(model);                   // populate the provided testbed tests (Si on veut tout les test décommenter)
		
		// add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
		model.getSettings().addSetting(new TestbedSetting(COMPUTE_RATE, SettingType.ENGINE, 1, 1, 50));
		model.getSettings().addSetting(new TestbedSetting(TIME_STEP, SettingType.ENGINE, 1, 1, 60));
		model.getSettings().addSetting(new TestbedSetting(VELOCITY_OP, SettingType.ENGINE, 0, 0, 10));
		model.getSettings().addSetting(new TestbedSetting(POSITION_IT, SettingType.ENGINE, 0, 0, 10));

		
		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

		JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
		
		// etc
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


