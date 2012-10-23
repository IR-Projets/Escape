package worlds;

import game.Variables;

import java.awt.Graphics2D;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.lang.model.element.VariableElement;
import javax.swing.JFrame;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedModel.TestChangedListener;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import ships.Ship;


public class Test extends TestbedTest {

	private static final String TIME_STEP = "Time step";
	private static final String VELOCITY_OP = "Velocity operation";
	private static final String POSITION_IT = "Position Iteration";
	
	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("#### TEST #####");

		final Environnement env = EnvironnementFactory.factory(getWorld());
		this.setCamera(new Vec2( (Variables.SCREEN_WIDTH/2)/Variables.WORLD_SCALE, (Variables.SCREEN_HEIGHT/2)/Variables.WORLD_SCALE), Variables.WORLD_SCALE*0.5f);
		
		Runnable run = new Runnable(){
			@Override
			public void run() {
				for(;;){
					env.compute();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};
		Thread thread = new Thread(run);
		thread.start();
		
		
	}
	
	  @Override
	  public void step(TestbedSettings settings) {
	    super.step(settings); // make sure we update the engine!
	    TestbedSetting timeStep = settings.getSetting(TIME_STEP); // grab our setting
	    TestbedSetting velocityIterations = settings.getSetting(VELOCITY_OP); // grab our setting
	    TestbedSetting positionIterations = settings.getSetting(POSITION_IT); // grab our setting
	    
	    
	    getWorld().step(timeStep.value/60.f, velocityIterations.value, positionIterations.value);
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
		model.getSettings().addSetting(new TestbedSetting("Time step", SettingType.ENGINE, 0, 0, 100));
		model.getSettings().addSetting(new TestbedSetting("Velocity operation", SettingType.ENGINE, 0, 0, 100));
		model.getSettings().addSetting(new TestbedSetting("Position Iteration", SettingType.ENGINE, 0, 0, 100));

		
		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

		JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
		
		// etc
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


