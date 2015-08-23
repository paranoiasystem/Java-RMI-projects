package com.marcoferraioli.thread;

public class PrimeNoThread {
	
	static int start = 1;
	static int stop = 1000000;
	
	static boolean isPrime(int n) {
		for(int i=2;i<n;i++) {
			if(n%i==0)
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for (Integer i = start; i <= stop; i++)
			if (isPrime(i)){
				//System.out.println(i.toString());
			}
		 long endTime = System.currentTimeMillis();
		 System.out.println("Total execution time: " + (endTime-startTime) + "ms");
	}

}
