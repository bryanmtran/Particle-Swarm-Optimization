// A 2 element vector used to represent both position and velocity.
// Basically the same as a tuple.
public class Vector2 
{
	private double x, y;
	
	public Vector2()
	{
		this(0,0);
	}
	
	public Vector2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public void translate(double deltaX, double deltaY)
	{
		x = x + deltaX;
		y = y + deltaY;
	}
		
	public double x()
	{
		return x;
	}
	
	public double y()
	{
		return y;
	}
	
}
