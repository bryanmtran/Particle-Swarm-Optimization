import java.util.Random;

public class Particle 
{
	Vector2 position;
	Vector2 velocity;
	
	Vector2 pBest;
	double pBestValue;

	
	public Particle()
	{
		this(new Vector2(0,0), new Vector2(0,0));
	}
	
	public Particle(Vector2 position, Vector2 velocity)
	{
		this.position = position;
		this.velocity = velocity;
		pBest = position;
		pBestValue = PSO.fitness(pBest);
	}
	
	// Calculate new velocity value. Update velocity and position of particle.
	public void updateValues(double C0, double C1, double C2, Vector2 gBest, double maxVelocity)
	{
		double velX = velocity.x();
		double velY = velocity.y();
		
		//Inertia influence
		velX = velX * C0;
		velY = velY * C0;
		
		//Personal influence
		Random random = new Random();
		double r1 = random.nextDouble();
		double pX = C1 * r1 * (pBest.x() - position.x());
		double pY = C1 * r1 * (pBest.y() - position.y());
		
		//Global influence
		double r2 = random.nextDouble();
		double gX = C2 * r2 * (gBest.x() - position.x());
		double gY = C2 * r2 * (gBest.y() - position.y());
		
		velX = velX + pX + gX;
		velY = velY + pY + gY;
		
		//Clamp velocity if needed
		if (velX > maxVelocity) { velX = maxVelocity; }
		if (velY > maxVelocity) { velY = maxVelocity; }
		if (velX < -maxVelocity) { velX = -maxVelocity; }
		if (velY < -maxVelocity) { velY = -maxVelocity; }
		
		//Update velocity
		velocity.set(velX, velY);
		
		//Update position
		position.translate(velX, velY);
		// Keep position in bounds of Rosenbrock problem.
		if (position.x() > Rosenbrock.MAX_X) { position.setX(Rosenbrock.MAX_X); }
		if (position.x() < Rosenbrock.MIN_X) { position.setX(Rosenbrock.MIN_X); }
		if (position.y() > Rosenbrock.MAX_Y) { position.setY(Rosenbrock.MAX_Y); }
		if (position.y() < Rosenbrock.MIN_Y) { position.setY(Rosenbrock.MIN_Y); }
	}
	
}
