package main;

import java.util.Date;
import java.util.Random;

public class Application 
{
	private static final int n = 8000; // dim of array
	public static int numcores = Runtime.getRuntime().availableProcessors();
	private static int matrix[][] = new int [n][n];
	public static boolean isNeutral;
	public static int global_count = 0;
		
public static void main(String[] args) 
{
	int[] counts;
	int i,j;
	int sign = 1;
	counts = new int[numcores];
	// initialize matrix
	Random random = new Random();

	for (i=0; i<n; i++)
		for (j=0; j<n; j++) 
		{
		   matrix[i][j] = sign * (100 + random.nextInt(100));
		   sign = -sign;
		}


	//new Application().printArray();
	/*
	 * Parallel Part
	 */
	
	Thread[] threads = new Thread[numcores];
	int portion = n / numcores;
	for(i = 0; i < numcores - 1 ; i++)
	{
		int start = i * portion;
		int end = start + portion - 1;
		threads[i] = new Thread(new Worker(i, matrix, start, end, counts));
	}
	// Last thread gets the remain of the matrix.
	int last_start = (numcores - 1 )*portion;
	int last_end = matrix.length - 1;
	threads[numcores - 1] = new Thread(new Worker(i, matrix,last_start,last_end,counts));
	
	Date t = new Date();
	for(i = 0; i < numcores; i++)
	{
		threads[i].start();
	}
	
	for(i = 0; i < numcores; i++)
	{
		try
		{
			threads[i].join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	

	Date s = new Date();

	long time = s.getTime()-t.getTime();
	System.out.print("\n*Execution Time:  ");
	System.out.println(time + " milliseconds.");

	if(global_count == 0)
	{
		isNeutral = true;
	}
	else
	{
		isNeutral = false;
	}
	if (isNeutral)
		System.out.println("**Array is Neutral**");
	else System.out.println("**Array is not Neutral**");
			
	}
	public void printArray()
	{
		int i,j;
		for (i=0; i<n; i++)
		{
			System.out.println();
			for (j=0; j<n; j++) 
			{
				System.out.print(matrix[i][j] + " ");
			}
		}
	}
}
