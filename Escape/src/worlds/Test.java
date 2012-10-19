package worlds;

import java.awt.Graphics2D;

import javax.swing.JFrame;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.TestbedTest;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;


public class Test extends TestbedTest {

	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("#### TEST #####");
		//getWorld().setGravity(new Vec2());

		Environnement.get(getWorld());
		Entity entity = new Entity();
		entity.init(0, 0, 5, 5);
		Environnement.get().addEntity(entity);
	}

	@Override
	public String getTestName() {
		return "#### TEST #####";
	}
	

	public static void main(String args[]){
		TestbedModel model = new TestbedModel();         	// create our model

		// add tests
		TestList.populateModel(model);                   // populate the provided testbed tests (Si on veut tout les test décommenter)
		model.addCategory("Tests personnels");             // add a category
		model.addTest(new Test());                		   // add our test

		// add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
		model.getSettings().addSetting(new TestbedSetting("Test1", SettingType.ENGINE, 10, 0, 20));

		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

		JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
		// etc
		testbed.setVisible(true);
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


