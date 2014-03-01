package main;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Worker implements Runnable
{ 
	private int threadID;

	private int[] counts;
	private int matrix[][];
	private int start_row;
	private int end_row;
	Lock lock = new ReentrantLock();
	int k = 0;
	
	
	public Worker(int threadID,int[][] matrix,int start,int end,int[] counts)
	{
		this.threadID = threadID;
		this.matrix = matrix;
		this.start_row = start;
		this.end_row = end;
		this.counts = counts;
	}
	
	@Override
	public void run() 
	{
		int num = 0;
	    System.out.println("\nThread " + Thread.currentThread().getName()+ " is working.");
	    
		for(int i = start_row; i <= end_row; i++)
		{
			for(int j = 0; j < matrix.length; j++)
			{
				if(matrix[i][j] == 0)
				{
					continue;
				}
				if(matrix[i][j] < 0)
				{
					num--;
				}
				else
				{
					num++;
					
				}
				counts[threadID] = num;
			}	
		}
		
	}

}
