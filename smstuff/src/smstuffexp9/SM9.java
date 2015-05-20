package smstuffexp9;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class SM9 
{	
	static double[] rand;
	static double N;
	static double n1=0,n2=0,n=0;
	static int ch=0;
	static Scanner s=new Scanner(System.in);
	public static void main(String args[]) throws Exception
	{
		Scanner s=new Scanner(System.in);
		System.out.println("Enter the Number of Random Nos:");
		N=s.nextInt();
		System.out.println();
		rand=new double[(int)N];
		double m = 1000,a=571,c=79;
		rand[0] = 787;
		double temp = rand[0];
		for(int i=1;i<N;i++)
		{
			temp=((a*temp+c))%m;
			rand[i]=temp;
			rand[i]=rand[i]/m;			
		}
		rand[0]=rand[0]/m;	
		System.out.println("\nRandom Numbers");		
		for(int i=0;i<N;i++)
		{
			System.out.print(rand[i]+" ");
			if(i%10==9)
			System.out.println();	
		}
		System.out.println("\nSelect the test type: \n1.Runs Up and Runs Down \n2.Runs Above and Runs Below Mean \n3.Length of Runs Test(Up-Down)  \n4.Length of Runs Test(Above Below) \n5.Auto Correlation");
		ch = s.nextInt();
		switch(ch)
		{
			case 1:	System.out.println("RUNS UP AND RUNS DOWN");
					updown(rand);				
				break;

			case 2:	System.out.println("RUNS ABOVE AND RUNS BELOW MEAN");
					abovebelow(rand);
				break;

			case 3:	System.out.println("LENGTH OF RUNS TEST(UP-DOWN)");
					updown(rand);
				break;

			case 4:	System.out.println("LENGTH OF RUNS TEST(ABOVE BELOW)");
					abovebelow(rand);
				break;

			case 5:	System.out.println("AUTO CORRELATION");
					autoCo();
				break;

			default: System.out.println("Choose from Options");	
		}
	}
	public static void updown(double[] rand)
	{
		int x[] = new int[(int)N-1];
		for (int i=0;i<N-1;i++)
		{
			if(rand[i+1]>rand[i])
			x[i] = 1;
			else
			x[i] = 0;
		}
		System.out.println("\nSequence of Runs up and runs down");
		double[] o = abc(x);
		int a1 = o.length;
		double as = o.length;
		double[] e = new double[a1];
		double sum=0;
		for (int i=0;i<a1;i++)
		n=n+o[i];

		for(int j=0;j<a1-1;j++)
		{
			if(j==a1-2)
			e[j] = roundTwoDecimals(2/fact(as));
			else
			e[j] = roundTwoDecimals(2*((N*((Math.pow(j+1,2))+(3*(j+1))+1))-((Math.pow(j+1,3))+(3*(Math.pow(j+1,2)))-j-5))/fact(j+4.0));
			sum=sum+e[j];
		}
		e[a1-1] = roundTwoDecimals((((2*N)-1)/3)-sum);
		if(ch==3)
		chisquare(o,e);
		if(ch==1)
		{	
			System.out.println("No. of runs:"+n);
			double mean = roundTwoDecimals((((2*N)-1)/3));
			double variance = roundTwoDecimals((((16*N)-29)/90));
			double z0 = roundTwoDecimals((n-mean)/Math.sqrt(variance));		
			System.out.println("Mean: "+mean+"\nVariance: "+variance+"\nZ0: "+z0 + "\nCritical Value : +-1.96");
			if(z0>= -1.96 && z0<=1.96)
				System.out.println("The Random Numbers Are Independent");
			else
				System.out.println("The Random Numbers Are Not Independent");
			
		}
	}
	
	public static void abovebelow(double[] rand)
	{	
		double sumr=0,avg=0.4995;
		int x[] = new int[(int)N];
		for (int i=0;i<N;i++)
		{
			if(rand[i]>avg)
			{
				x[i] = 1; 
				n1=n1+1;
			}
			else
			{
				x[i] = 0;  
				n2=n2+1;
			}
		}
		System.out.println("\n Sequence of Runs above and runs below");
		double[] o = abc(x);
		int a1 = o.length;
		
		for (int i=0;i<a1;i++)
		n=n+o[i];
		
		double ei = (n1/n2)+(n2/n1);
		double as = o.length;
		double[] e = new double[a1];
		double sum=0;
		for(int j=0;j<a1-1;j++)
		{
			e[j] = roundTwoDecimals((Math.pow(n1/N,j+1)*(n2/N)+Math.pow(n2/N,j+1)*(n1/N))*N/ei);
			sum=sum + e[j];
		}
		e[a1-1] = roundTwoDecimals((N/ei)-sum);
		System.out.println("No. of runs above mean :"+n1);
		System.out.println("No. of runs above mean :"+n2);
		if(ch==4)
		chisquare(o,e);
		if(ch==2)
		{	
			System.out.println("No. of runs:"+n);
			double mean = roundTwoDecimals((2*n1*n2/N)+0.5);
			double variance = roundTwoDecimals((2*n1*n2*(2*n1*n2 - N))/(N*N*(N-1)));
			double z0 = roundTwoDecimals((n-mean)/Math.sqrt(variance));		
			System.out.println("Mean: "+mean+"\nVariance: "+variance+"\nZ0: "+z0 + "\nCritical Value : +-1.96");
			if(z0>= -1.96 && z0<=1.96)
				System.out.println("The Random Numbers Are Independent");
			else
				System.out.println("The Random Numbers Are Not Independent");
		}
	}
	
	public static double[] abc(int[] x)
	{
		int[] co = new int[x.length];
		for(int i=0;i<x.length;i++)
		{
			if(x[i]==1)	
			System.out.print("+ ");
			else
			System.out.print("- ");
			if(i%10==9)
			System.out.println();
		}
		System.out.println();
		for (int i=0;i<x.length;i++)
		co[i]=0;
		int j=1,i=0;
		co[0]=1;
		while(j<x.length)
		{
			if(x[j-1]==1 && x[j]==0)
			i++;
			else if(x[j-1]==0 && x[j]==1)
			i++;
			
			co[i]++;
			j++;
		}
		int max=0;
		System.out.println("\nRuns :");
		for(i=0;i<x.length;i++)
		{	
			if(co[i] != 0)
			System.out.print(co[i]+" ");
			if(i%10==9)
			System.out.println();
			if(co[i]>max)
			max=co[i];
		}
		double[] count = new double[max+1];
		for (i=0;i<=max;i++)
		count[i]=0;
		
		for(i=0;i<x.length;i++)
		{
		if(co[i]!=0)
		count[co[i]-1]=count[co[i]-1]+1;
		}
		return count;
	}
	
	public static void chisquare(double[] o , double[] e)
	{
		int l = e.length;
		System.out.println("\nRun Length  Oi   Ei \n");
		for(int k=0;k<l;k++)
		{
			if(k==l-1)
			System.out.println("   >="+(k+1)+"    "+o[k]+"  "+e[k]);
			else
			System.out.println("     "+(k+1)+"    "+o[k]+"  "+e[k]);

		}
		System.out.println();
		for(int i = l-1;i>0;i--)
		{
			if(e[i]<=5)
			{	
				System.out.println("Expected value of class "+(i+1)+"= "+roundTwoDecimals(e[i])+" which is less than 5");
				System.out.println("Therefore adding class "+(i+1)+" to class "+i+"\n");
				e[i-1] += e[i];
				o[i-1] += o[i];
				e[i] = 0;
				o[i] = 0;
			}	
		}
		int clas = 0;
		for(int i = 0;i<l;i++)
		{
			if(e[i]>0)
			clas++;
		}
		System.out.println("No of Classes: "+clas);
		double temp =0,sum=0;
		System.out.println("\nRun Length  Oi   Ei  (Oi-Ei)^2/Ei \n");
		for(int i=0;i<clas;i++)
		{
			temp = (Math.pow((o[i]-e[i]),2))/e[i];
			temp = roundTwoDecimals(temp);	
			sum = sum + temp;
			if(i==clas-1)
			System.out.println("   >="+(i+1)+"    "+o[i]+"  "+e[i]+"    "+temp);
			else
			System.out.println("     "+(i+1)+"    "+o[i]+"  "+e[i]+"    "+temp);
		}
		System.out.println("\nChi Square Zero: "+roundTwoDecimals(sum));
		
		double[] crit = {3.841,5.991,7.815, 9.488, 11.070 ,12.592 ,14.067 ,15.507 ,16.919, 18.307, 19.675 ,21.026 ,22.362, 	23.685, 24.996, 26.296, 27.587, 28.869, 30.144, 31.410};
		int crt = clas-2;
		
		System.out.println("Critical Value: "+crit[crt]);
		
		if(crit[crt]>sum)
			System.out.println("The Random Numbers Are Independent");
		else
			System.out.println("The Random Numbers Are Not Independent");
	}
	public static void autoCo()
	{
		int start=0;
		do {
			System.out.println("Enter the Starting position :");
			start=s.nextInt();
		}while(start>N);
		System.out.println("Enter the lag :");
		int lag=s.nextInt();
		int M=(int)((((int)N-start)/lag)-1);
		System.out.println("i= "+start+"\t M= "+M+"\t lag= "+lag);
		double sum=0;
		for(int k=0;k<=M;k++){
			if(start+(k+1)*lag<N)
				 sum=sum+rand[start+k*lag]*rand[start+(k+1)*lag];
		}
		sum=sum/(M+1)-0.25;
		sum=roundTwoDecimals(sum);
		System.out.println("Value of Estimator : "+sum);
		double sd=Math.sqrt(13*M+7)/(12*(M+1));
		sd=roundTwoDecimals(sd);
		System.out.println("Standard Deviation : "+sd);
		double z=roundTwoDecimals(sum/sd);
		System.out.println("Test Statistics Z: "+z +"\t Critical value: +/- 1.96");
		if(z>=-1.96 && z<= 1.96)
			System.out.println("Random Nos are Independent");
		else
			System.out.println("Random Nos are Dependent");
	}
	
	public static double fact(double n) 
	{
        		double fact = 1;
        		for (int i = 1; i <= (int)n; i++) 
		{
            			fact *= i;
        		}
       	 	return fact;
	}
	
	public static double roundTwoDecimals(double d) 
	{
    	DecimalFormat twoDForm = new DecimalFormat("#.##");
    	return Double.valueOf(twoDForm.format(d));
	}
		
}