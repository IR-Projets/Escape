package fr.umlv.zen2;

/**
 * Provided by the application framework, this object allows
 * to render a graphic code into the drawing area of the application
 * and to wait/ask for motion events (mouse or touch events).
 */
public interface ApplicationContext {
  /** Returns the first mouse event since or the application was started or
   * {@link #pollMotion()} or {@link #waitMotion()} was called
   * or null if the user didn't use the mouse.
   *  
   * @return a mouse event or null otherwise.
   */
  public MotionEvent pollMotion();
  
  /** Returns the first mouse event since or the application was started or
   * {@link #pollMotion()} or {@link #waitMotion()} was called
   * or if the user didn't press a key, this call wait until a key is pressed
   * or the application thread is interrupted.
   *  
   * @return a mouse event or null if the application thread is interrupted.
   */
  public MotionEvent waitMotion();
  
  /** Used to ask the application to render a specific graphic code into the drawing area.
   *  In fact, the drawing code doesn't draw directly into the drawing area
   *  but draw into a back buffer that will be drawn to the screen.
   *  
   * @param rendererCode user object specifying the code that should be executed to drawn
   *                     to the drawing area.
   */
  public void render(ApplicationRenderCode rendererCode);
}