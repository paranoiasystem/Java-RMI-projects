package com.marcoferraioli.thread;

public class PrimeNumber {

	public static void main(String[] args) throws InterruptedException {
		Prime p1 = new Prime(1, 500000);
		Prime p2 = new Prime(500001, 1000000);
		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		long startTime = System.currentTimeMillis();
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		long endTime = System.currentTimeMillis();
		System.out.println("Total execution time of: " + (endTime-startTime) + "ms");
	}


}

class Prime implements Runnable {
	
	int start, stop;
	
	public Prime(int start, int stop){
		this.start = start;
		this.stop = stop;
	}
	
	@Override
	public void run() {
		for (Integer i = this.start; i <= this.stop; i++)
			if (isPrime(i)){
				//System.out.println("THREAD FINO A " + this.stop + ": " + i.toString());
			}
	}

	boolean isPrime(int n) {
		for(int i=2;i<n;i++) {
			if(n%i==0)
				return false;
		}
		return true;
	}

}
