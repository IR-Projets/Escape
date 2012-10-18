import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;
import fr.umlv.zen2.MotionEvent;

public class MotionMain {
	public static void main(String[] args) {
		final int WIDTH = 800;
		final int HEIGHT = 600;

		Application.run("Detect motions", WIDTH, HEIGHT, new ApplicationCode() {
			@Override
			public void run(final ApplicationContext context) {
				final Random random = new Random(0);
				final Font font = new Font("arial", Font.BOLD, 30);

				for(;;) {
					final MotionEvent event = context.waitMotion();
					if (event == null) {
						return;
					}
					context.render(new ApplicationRenderCode() {
						@Override
						public void render(Graphics2D graphics) {
							Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
							graphics.setPaint(color);
							graphics.setFont(font);
							graphics.drawString(event.toString(), event.getX(), event.getY());
						}
					});
				}
			}
		});
	}
}
