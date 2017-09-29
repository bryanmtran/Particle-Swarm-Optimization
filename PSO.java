/*
 * Particle Swarm Optimizaiton (PSO). This implementation uses a global definition
 * of neighbors, where each particle's neighbors is the entire set of particles.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PSO 
{	
	// Constants used in calculating updated particle velocities.
	private double C0 = 0.2;
	private double C1 = 1;
	private double C2 = 3;
	
	private int numParticles;
	private double maxVelocity;
	private List<Particle> swarm;
	private Random random;
	String result;
	
	// Global best position. (Position with the highest fitness value).
	Vector2 gBest;
	// Global best fitness value.
	double gBestValue;
	
	public PSO()
	{
		this(20, 0.01);
	}
	
	public PSO(double cZero)
	{
		this(20, 0.01);
		this.C0 = cZero;
	}
	
	public PSO(int numParticles, double maxVelocity)
	{
		this.numParticles = numParticles;
		this.maxVelocity = maxVelocity;
		swarm = new ArrayList<Particle>();
		random = new Random();
		gBest = null;
		gBestValue = Double.NEGATIVE_INFINITY;
		result = null;
	}
	
	// Execute PSO until error falls below error criterion, or the max number of iterations
	// has been reached.
	public void execute(double errorCriterion, int maxIterations)
	{
		initialize();
		int i = 0;
		do
		{
			updatePersonalBests();
			updateGlobalBest();
			updateValues();
			i++;
		}
		while (error() > errorCriterion && (i < maxIterations));
		// Set result string.
		if (i >= maxIterations)
		{
			result = "Failure.";
		}
		else
		{
			result = "Success.";
		}
	}
	
	public void initialize()
	{
		// Initialize particles with random positions and velocities.
		for (int i=0; i<numParticles; i++)
		{	
			Particle particle = new Particle(randomPosition(), randomVelocity());
			swarm.add(particle);
		}
	}
		
	private void updatePersonalBests()
	{
		for (Particle particle: swarm)
		{	
			double fitnessValue = fitness(particle.position);
			if (fitnessValue > particle.pBestValue)
			{
				particle.pBest = particle.position;
				particle.pBestValue = fitnessValue;
			}
		}
	}
	
	// Find the new global best position, if any.
	private void updateGlobalBest()
	{
		for (Particle particle: swarm)
		{	
			if (particle.pBestValue > this.gBestValue)
			{
				this.gBestValue = particle.pBestValue;
				this.gBest = particle.pBest;
			}
		}
	}
	
	// Update velocity and position values for each particle.
	private void updateValues()
	{
		for (Particle particle: swarm)
		{	
			particle.updateValues(C0, C1, C2, gBest, maxVelocity);
		}
	}
	
	private Vector2 randomPosition()
	{
		double randomX = (random.nextDouble() * (Rosenbrock.MAX_X - Rosenbrock.MIN_X)) + Rosenbrock.MIN_X;
		double randomY = (random.nextDouble() * (Rosenbrock.MAX_Y - Rosenbrock.MIN_Y)) + Rosenbrock.MIN_Y;
		return new Vector2(randomX,randomY);
	}
	
	private Vector2 randomVelocity()
	{
		double randomX = (random.nextDouble() * (maxVelocity * 2)) - maxVelocity;
		double randomY = (random.nextDouble() * (maxVelocity * 2)) - maxVelocity;
		return new Vector2(randomX,randomY);
	}
	
	// Fitness function is defined as the inverse of the output of the Rosenbrock function.
	// A one is added in the denominator to avoid division by zero.
	public static double fitness(Vector2 position)
	{
		return 1/(Rosenbrock.evaluate(position) + 1);
	}
	
	private double sumSquaredError(Vector2 position)
	{
		double x = position.x();
		double y = position.y();
		
		// It is known that (1,1) is the global minimum for the Rosenbrock function.
		// So those values are used in calculating SSE.
		return Math.pow(1-x, 2) + Math.pow(1-y, 2);
	}
	
	public double error()
	{
		return sumSquaredError(this.gBest);
	}
}
