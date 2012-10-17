import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;

public class Main {
  static int saturate(int value) {
    return (value < 0)? 0: (value > 255)? 255: value;
  }
  
  public static void main(String[] args) {
    final int WIDTH = 400;
    final int HEIGHT = 600;
    
    Application.run("ScrollIt", WIDTH, HEIGHT, new ApplicationCode() {
      @Override
      public void run(ApplicationContext context) {   
        final Random random = new Random(100);
        
        context.render(new ApplicationRenderCode() {  
          @Override
          public void render(Graphics2D graphics) {
            graphics.setColor(Color.GREEN);
            graphics.fill(new Rectangle(0, 0, WIDTH, HEIGHT));
          }
        });
        
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        
        for(;;) {
          try {
            Thread.sleep(25);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          
          red = saturate(red - random.nextInt(8) + 4);
          green = saturate(green + random.nextInt(8) - 4);
          blue = saturate(blue - random.nextInt(8) + 4);
          
          final int r = red;
          final int g = green;
          final int b = blue;
          
          context.render(new ApplicationRenderCode() {
            @Override
            public void render(Graphics2D graphics) {
              graphics.copyArea(0, 5, WIDTH, HEIGHT - 5, 0, -5);
              graphics.setPaint(new Color(r, g, b));
              graphics.fill(new Rectangle(0, HEIGHT - 5, WIDTH, 5));
            }
          });
        }
      }
    });
  }
}
