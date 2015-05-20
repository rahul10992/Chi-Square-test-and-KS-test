package smstuffexp8;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class Experiment8 
{	
	static double[] rand;
	static double n;
	static Scanner s=new Scanner(System.in);
	public static void main(String args[]) throws Exception
	{
		Scanner s=new Scanner(System.in);
		System.out.println("Enter the Number of Random Nos:");
		n=s.nextInt();
		rand=new double[(int)n];
		double m = 3571,a=571,c=719;
		rand[0] = 787;
		double temp = rand[0];
		for(int i=1;i<n;i++)
		{
			temp=((a*temp+c))%m;
			rand[i]=temp;
			rand[i]=rand[i]/m;			
			rand[i] = roundTwoDecimals(rand[i]);
		}
		rand[0]=rand[0]/m;
		Arrays.sort(rand);
		if(n<=20)
			KSTest(rand);
		else
			chisquare(rand);
	}
	
	public static void KSTest(double[] random_digit)
	{
		System.out.println("-----K-S TEST-----");
		double rd1=random_digit[0];
		
		double dp=(1/n)-random_digit[0];
		double dm=random_digit[0];
		int i=0;
		
		System.out.println("i \t Ri \t i/N \t (i/N)-Ri \t Ri-((i-1)/N)");
		System.out.println((i+1)+" \t "+roundTwoDecimals(random_digit[i])+" \t "+roundTwoDecimals((i+1)/n)+" \t "+roundTwoDecimals(((i+1)/n)-random_digit[i])+" \t \t "+roundTwoDecimals(random_digit[i]-(i/n)));
		for(i=1;i<n;i++){
			
			if((((i+1)/n)-random_digit[i])>dp)
				dp=((i+1)/n)-random_digit[i];
			if((random_digit[i]-(i/n))>dm)
				dm=(random_digit[i]-(i/n));
			System.out.println((i+1)+" \t "+roundTwoDecimals(random_digit[i])+" \t "+roundTwoDecimals((i+1)/n)+" \t "+roundTwoDecimals(((i+1)/n)-random_digit[i])+" \t \t "+roundTwoDecimals(random_digit[i]-(i/n)));
		}		
		System.out.println("\n D+ :"+dp+ "\t D- :"+roundTwoDecimals(dm));
		double d=0;
		if(dp>dm)
			d=dp;
		else
			d=dm;
			
		double los_ks[]={0.975,0.842,0.708,0.624,0.565,0.521,0.486,0.457,0.432,0.410,0.391,0.375,0.361,0.349,0.338,0.328,0.318,0.309,0.301,0.294,0.27};	
		System.out.println("Level of Significance = 0.05");			
		double cv=los_ks[(int)n-1];
		System.out.println("D :"+d+"\n Critical Value: "+cv);
		if(d<cv)
			System.out.println("RANDOM NOS ARE UNIFORMLY DISTRIBUTED");
		else
			System.out.println("RANDOM NOS ARE NOT UNIFORMLY DISTRIBUTED");
	}

	
	
	
	public static void chisquare(double[] rand)
	{
		System.out.println("-----CHI SQUARE TEST----- \n");
		
		System.out.println("The max no of classes are:" + (int)(n/5));
		double cl = (n/5)+1;
		while(cl>n/5)
		{
			System.out.println("Enter No of Classes");
			cl = s.nextDouble();
		}
		double inter = 1/cl;
		double ll = 0.0;
		double ul = ll+inter;
		int j=0 , i=0;
	
		int count = 0;
		double exp = n/cl;
		double chi = 0,x=0;
		
		ll = roundTwoDecimals(ll);
		ul = roundTwoDecimals(ul);
		inter = roundTwoDecimals(inter);
		exp = roundTwoDecimals(exp);

		System.out.println("\nLL  UL  Oi   Ei  (Oi-Ei)^2/Ei \n");
		while(j<(int)cl)
		{	
			while(i<(int)n && rand[i]<ul)
			{	
				count = count+1;
				i++;	
			}
			while(i<(int)n && rand[i]==1)
			{
				count=count+1;
				i++;
			}	
			x = (Math.pow((count-exp),2))/exp;
			x = roundTwoDecimals(x);	
			chi = chi + x;
			if(count<10)
			System.out.print(ll+" "+ul+" 0"+count+"  "+exp+"    "+x);
			else
			System.out.print(ll+" "+ul+" "+count+"  "+exp+"    "+x);
			System.out.println();			
			ll+=inter;
			ul+=inter;
			ll = roundTwoDecimals(ll);
			ul = roundTwoDecimals(ul);
			count=0;
			j++;
		}	
		System.out.println("\nChi Square Zero: "+roundTwoDecimals(chi));
		
		double[] crit = {3.841,5.991,7.815, 9.488, 11.070 ,12.592 ,14.067 ,15.507 ,16.919, 18.307, 19.675 ,21.026 ,22.362, 	23.685, 24.996, 26.296, 27.587, 28.869, 30.144, 31.410};
		int crt = (int)cl-2;
		System.out.println("Level of Significance = 0.05");
		System.out.println("Critical Value: "+crit[crt]);
		
		if(crit[(int)cl-2]>chi)
			System.out.println("The Random Numbers Are Uniformly Distributed");
		else
			System.out.println("The Random Numbers Are Not Uniformly Distributed");
	}	
	
	public static double roundTwoDecimals(double d) 
	{
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return Double.valueOf(twoDForm.format(d));
	}
	
}