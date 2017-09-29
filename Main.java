/*
 * Bryan Tran
 * bryanmichaeltran@gmail.com
 * CS 657 - Intelligent Systems and Control
 * Assignment 5
 * 
 * Main.java:
 * Driver program for Assignment 5. Operates on an instance of the particle swarm optimization, PSO, and writes the
 * output/test data as described below.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	static File file;
	static FileWriter writer;
	static double errorCriterion = 0.001;
	static int maxIterations = 10000;
	
	public static void main(String[] args) throws IOException 
	{
		prompt();
		sampleOutput();
		cZeroTest();
		populationSizeTest();
		maxVelocityTest();
		System.out.println("File generation completed.");
	}
	
	private static void prompt()
	{
		System.out.println("-----Particle Swarm Optimization (PSO) to minimize the Rosenbrock function-----");
		System.out.println("See output files for output/test results.");
		System.out.println("sampleOutput.txt: Sample output - 20 runs with the same parameters.");
		System.out.println("c0Test.txt: Error and outputs as the C0 constant is increased.");
		System.out.println("populationSizeTest.txt: Error and outputs as population size increases from 1 to 100.");
		System.out.println("maxVelocityTest.txt: Error and outputs as max velocity is decreased from 0.1 to 0.01");
		System.out.println("File generation may take a few seconds. Standby...");
	}
	
	//Sample output - 20 runs with the same parameters.
	private static void sampleOutput() throws IOException
	{
		int iterations = 20;
		int size = 20;
		double maxVelocity = 0.01;
		
		file = new File("sampleOutput.txt");
		writer = new FileWriter(file, false);
		
		writer.write(String.format("Sample Output - %d runs with size = %d, max velocity = %f \n", iterations, size, maxVelocity));
		writer.write(String.format("%-10s %-10s %-10s %-10s \n", "Status","SSE", "X", "Y"));
		for (int i=0; i<iterations; i++)
		{
			PSO pso = new PSO(size,maxVelocity);
			//Execute PSO with error criterion of 0.001 and max iterations of 10000.
			pso.execute(errorCriterion, maxIterations);
			writer.write(String.format("%-10s %-10f %-10f %-10f \n", pso.result, pso.error(), pso.gBest.x(), pso.gBest.y()));
		}
		writer.flush();
		writer.close();
	}
	
	//Error and outputs as the C0 constant is increased.
	private static void cZeroTest() throws IOException
	{
		file = new File("c0Test.txt");
		writer = new FileWriter(file, false);

		writer.write(String.format("%-10s \n", "Error and Outputs as C0 increases."));
		writer.write(String.format("%-10s %-10s %-10s %-10s \n", "C0","SSE", "X", "Y"));
		for (double i=0; i<1; i = i + 0.01)
		{
			PSO pso = new PSO(i);
			//Execute PSO with error criterion of 0.001 and max iterations of 10000.
			pso.execute(errorCriterion, maxIterations);
			writer.write(String.format("%-10s %-10f %-10f %-10f \n", Math.floor(i*100)/100, pso.error(), pso.gBest.x(), pso.gBest.y()));
		}
		writer.flush();
		writer.close();
	}
	
	//Error and outputs as population size increases from 1 to 100.
	private static void populationSizeTest() throws IOException
	{
		file = new File("populationSizeTest.txt");
		writer = new FileWriter(file, false);
		
		writer.write(String.format("%-10s \n", "Error and Outputs as population size increases."));
		writer.write(String.format("%-10s %-10s %-10s %-10s \n","Size", "SSE", "X", "Y"));
		for (int i=1; i<=100; i++)
		{
			// C0 defaults to 0.2 and max velocity here is 0.01
			PSO pso = new PSO(i,0.01);
			pso.execute(errorCriterion, maxIterations);
			writer.write(String.format("%-10d %-10f %-10f %-10f \n", i, pso.error(), pso.gBest.x(), pso.gBest.y()));
		}
		writer.flush();
		writer.close();
	}
	
	//Error and outputs as max velocity is decreased from 0.1 to 0.01
	private static void maxVelocityTest() throws IOException
	{
		file = new File("maxVelocityTest.txt");
		writer = new FileWriter(file, false);
		
		writer.write(String.format("%-10s \n", "Error and Outputs as max velocity decreases."));
		writer.write(String.format("%-10s %-10s %-10s %-10s \n","Max Velocity", "SSE", "X", "Y"));
		for (double i=0.1; i>=0.010; i = i-0.001)
		{
			// C0 defaults to 0.2 and number of particles is 20.
			PSO pso = new PSO(20,i);
			pso.execute(errorCriterion, maxIterations);
			writer.write(String.format("%-10f %-10f %-10f %-10f \n", i, pso.error(), pso.gBest.x(), pso.gBest.y()));
		}
		writer.flush();
		writer.close();
	}

}
