/* 
 * The function to be minimized by PSO.
 * Rosenbrock function: f(x,y) = (1-x)^2 + 100(y-x^2)^2
 */

public class Rosenbrock 
{
	public static final double MIN_X = -1.5;
	public static final double MAX_X = 2.0;
	public static final double MIN_Y = -0.5;
	public static final double MAX_Y = 3.0;
	
	public Rosenbrock(){}
	
	public static double evaluate(Vector2 position)
	{
		return Math.pow(1-position.x(), 2) + 100*Math.pow(position.y() - Math.pow(position.x(), 2),2);
	}
}
