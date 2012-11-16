package game;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import listeners.EnvironnementListener;

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

import factories.EnvironnementFactory;
import factories.EnvironnementFactory.Level;
import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;



public class MainTest extends TestbedTest implements EnvironnementListener{

	private static final String SKIP_TICKS = "Skip ticks";
	private static final String MAX_FRAMESKIP = "Max frameSkip";
	private static final String LAG = "Lag";
	
	
	private Environnement env = null;
	//reflexion pour avoir accès au constructeur protected de MotionEvent
	private Constructor eventConstructor;
	Level level;

	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("#### TEST #####");

		level = Level.Moon;
		env = EnvironnementFactory.factory(getWorld(), level);
		env.addListener(this);
		this.setCamera(new Vec2( Variables.SCREEN_WIDTH/2, Variables.SCREEN_HEIGHT/2), 0.5f);	


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

	long currentTime, next_game_tick=-1;
	@Override
	public void step(TestbedSettings settings) {	
		super.step(settings); // make sure we update the engine!
		
		TestbedSetting skipTicks = settings.getSetting(SKIP_TICKS); // grab our setting
		TestbedSetting maxFrameSkip = settings.getSetting(MAX_FRAMESKIP); // grab our setting
		TestbedSetting lag = settings.getSetting(LAG); // grab our setting

		if(next_game_tick==-1)
			next_game_tick = System.currentTimeMillis();

		int loops = 0;
		long currentTime = System.currentTimeMillis();
		while(currentTime > next_game_tick && loops < maxFrameSkip.value) {
			next_game_tick += skipTicks.value;
			loops++;
			env.compute();
		}	
		//Simule the lag (simulate Environnement.render() time)
		try {
			Thread.sleep(lag.value);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void event(int x, int y, Kind kind){		
		if(x<0 || y<0)
			return;
		y = Variables.SCREEN_HEIGHT - y;

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

		MainTest test = new MainTest();

		// add tests
		model.addCategory("Tests personnels");             // add a category
		model.addTest(test);                		   // add our test
		TestList.populateModel(model);                   // populate the provided testbed tests (Si on veut tout les test décommenter)

		// add our custom setting "My Range Setting", with a default value of X, between Y and Z
		model.getSettings().addSetting(new TestbedSetting(SKIP_TICKS, SettingType.ENGINE, Variables.SKIP_TICKS, 1, 50));
		model.getSettings().addSetting(new TestbedSetting(MAX_FRAMESKIP, SettingType.ENGINE, Variables.MAX_FRAMESKIP, 1, 60));
		model.getSettings().addSetting(new TestbedSetting(LAG , SettingType.ENGINE, Variables.TEST_LAG, 0, 100));


		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

		JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame

		// etc
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


