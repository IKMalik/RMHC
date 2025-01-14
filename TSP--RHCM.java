import java.util.*;
import java.io.*;

public class testing
{
    public static void main(String[] args)
    {
        Double[][] dist = { {0.0, 3.0, 4.0, 5.0},{3.0, 0.0, 2.0, 2.5}, {4.0, 2.0, 0.0, 1.5}, {5.0, 2.5, 1.5, 0.0} };
        
        System.out.println(Arrays.toString(SolutionTSP(dist, 50)));
   
    }
    public static int[] SolutionTSP(Double[][] distances, int iter)
    {
        TSP sol = new TSP(distances);
        
        sol = sol.RMHC(distances, iter);
        
        ArrayList<Integer> res = sol.GetTour();
        
        int[] result = new int[res.size()];
        
        for (int i = 0; i < res.size(); i++) {
            result[i] = res.get(i);
        }
        
        return result;
    }
    
}

class TSP
{
    private  ArrayList<Integer> tour;
    
    public TSP(Double[][] distances)
    {
        int cities = distances[0].length;
        tour = RandPerm(cities);
        
    }

    public static TSP RMHC(Double[][] distances, int iter)
	{
	    TSP sol = new TSP(distances);
	    
	    for(int i=0;i<iter;i++)
	    {
	        
	        int cities = distances[0].length;
	        ArrayList<Integer> RandTour = RandPerm(cities);
	        
	        Double oldFit = sol.fitness(cities, distances);
	        ArrayList<Integer> oldTour = sol.tour;
	        
	        sol.tour = RandTour;
	        
	        Double newFit = sol.fitness(cities, distances);
	        
	        if (newFit > oldFit)
	        {
	            sol.tour = oldTour;
	        }
	 
	    }
	    return sol;
	}
    
    public Double fitness(int cities, Double[][] dist)
    {
        Double s = 0.0;

        for (int i=0; i<cities-1; i++)
        {
            int a = tour.get(i);
            int b = tour.get(i+1);
            s += dist[a-1][b-1];
        }
        
        int end = tour.get(tour.size()-1);
        int start = tour.get(0);
        
        s += dist[end-1][start-1];
        
        return s;
    }
    
    public static ArrayList<Integer> RandPerm(int cities)
    {
        ArrayList<Integer> p = new ArrayList<Integer>();
        ArrayList<Integer> t = new ArrayList<Integer>();
        
        for (int i=1; i<cities+1; i++)
        {
            p.add(i);
        }
        

        while (p.size() > 0)
        {
            int i = CS2004.UI(0, p.size()-1);
            t.add(p.get(i));
            p.remove(i);
        }
        
        return t;
        
    }
    
    public static ArrayList<Integer> swap(ArrayList<Integer> t)
    {
        int i = 0;
        int j = 0;
        
        while (i == j)
        {
            i = CS2004.UI(0, t.size()-1);
            j = CS2004.UI(0, t.size()-1);
        }
        
        int temp = t.get(i);
        t.set(i, t.get(j));
        t.set(j, temp);
        
        return t;
    }
    
    public ArrayList<Integer> GetTour()
    {
        return tour;
    }
    
}

class CS2004 
{
	//Shared random object
	static private Random rand;
	//Create a uniformly distributed random integer between aa and bb inclusive
	static public int UI(int aa,int bb)
	{
		int a = Math.min(aa,bb);
		int b = Math.max(aa,bb);
		if (rand == null) 
		{
			rand = new Random();
			rand.setSeed(System.nanoTime());
		}
		int d = b - a + 1;
		int x = rand.nextInt(d) + a;
		return(x);
	}
	//Create a uniformly distributed random double between a and b inclusive
	static public double UR(double a,double b)
	{
		if (rand == null) 
		{
			rand = new Random();
			rand.setSeed(System.nanoTime());
		}
		return((b-a)*rand.nextDouble()+a);
	}
	//This method reads in a text file and parses all of the numbers in it
	//This code is not very good and can be improved!
	//But it should work!!!
	//It takes in as input a string filename and returns an array list of Doubles
	static public ArrayList<Double> ReadNumberFile(String filename)
	{
		ArrayList<Double> res = new ArrayList<Double>();
		Reader r;
		try
		{
			r = new BufferedReader(new FileReader(filename));
			StreamTokenizer stok = new StreamTokenizer(r);
			stok.parseNumbers();
			stok.nextToken();
			while (stok.ttype != StreamTokenizer.TT_EOF) 
			{
				if (stok.ttype == StreamTokenizer.TT_NUMBER)
				{
					res.add(stok.nval);
				}
				stok.nextToken();
			}
		}
		catch(Exception E)
		{
			System.out.println("+++ReadFile: "+E.getMessage());
		}
	    return(res);
	}
}
