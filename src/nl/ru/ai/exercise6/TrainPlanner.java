package nl.ru.ai.exercise6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TrainPlanner 
{
	public static void main(String[] args) 
	{
		String[] stations = readStations();
		int noStations = stations.length;
		int[] [] table = new int [noStations] [noStations];
		initTable(table);
		int[] [] via = new int [noStations] [noStations];
		initVia(via);
		table = addDistances(table,stations);
		floydWarshall (table, via);
		findBiggestDistance(table, stations);
		String stationFrom=biggestDistanceFrom(table,stations);
		String stationTo=biggestDistanceTo(table,stations);
//		printTable(table);
		showPath(via,stationNumber(stationFrom,stations),stationNumber(stationTo,stations),stations);

	}
	
	

	/** Initiates the via table with only -1
	 * @param via
	 */
	private static void initVia(int[][] via) 
	{
		assert via!=null:"via table must be initialised";
		for(int i = 0;i<via.length;i++)
		{
			for(int j = 0;j<via[0].length;j++)
			{
				via[i][j]=-1;
			}
		}
	}

	/** Initiates the distances table with 0 from station to itself, and 999 elsewhere
	 * @param table
	 */
	private static void initTable(int[][] table) 
	{
		assert table!=null:"table must be initialised";
		for(int i=0; i<table.length; i++)
		{
			for(int j=0; j<table[0].length; j++)
			{
				if(i==j)
					table[i][j]=0;
				else
					table[i][j]=999;
			}
		}
	}

	/** Reads the the stations from stations.txt
	 * @return string[]stations
	 */
	static String[] readStations()
	{
		assert true;
		String line;
		String[] stations = null;
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader("stations.txt"));
			while((line=reader.readLine())!=null)
			{
				stations = line.split(",");
			}
			reader.close();
			System.out.println("Stations: " + stations.length);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return stations;
	}
	
	/**
	 * @param table where distances need to be added
	 * @param stations
	 * @return table filled with some distances as specified in connections.txt
	 */
	static int[][] addDistances(int[][] table, String[] stations)
	{
		assert table!=null:"table must be initialised";
		assert stations!=null:"stations must be initialised";
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader("connections.txt"));
			String line;
			String[] words;
			while((line=reader.readLine())!=null)
			{
				words = line.split(",");
				int pos1 = stationNumber(words[0],stations);
				int pos2 = stationNumber(words[1],stations);
				int distance = Integer.parseInt(words[2]);
				table[pos1][pos2] = distance;
			}
			reader.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return table;
	}
	
	/** Finds the index of a station name in the stations array
	 * @param name of the station to be found
	 * @param stations
	 * @return the index number of the station
	 */
	static int stationNumber(String name, String [] stations)
	{
		assert name!=null:"name must be specified";
		assert stations!=null:"stations must be initialised";
		int pos=0;
		while(!stations[pos].equals(name))
			pos++;
		return pos;
	}
	/**
	 * result is the shortest distance of path from A to B where intermediate places can only be member of {0..k}
	 * @param A
	 * @param B
	 * @param k
	 * @return shortest distance
	 */
	static void floydWarshall (int [ ] [ ] table, int [ ] [ ] via)
	{ 
		for (int k = 0 ; k < table.length ; k++)
		{
			for (int a = 0 ; a < table.length ; a++)
			{
					for (int b = 0 ; b < table.length ; b++)
					{ 
						int alternative = table[a][k] + table[k][b];
						if (alternative < table[a][b] && alternative > 0)
						{
							table[a][b] = alternative;
							via[a][b] = k;
						}
					}
			}
		}
	}
	
	/** shows the shortest path from station a to station b
	 * @param via table
	 * @param a from place (stationnumber)
	 * @param b to place (stationnumber)
	 * @param stations
	 */
	static void showPath (int [ ] [ ] via, int a, int b, String[]stations)
	{
		assert via!=null:"via must be initialised";
		assert a>0 && b>0 &&a<stations.length && b<stations.length: "Not a valid train station";
		assert stations!=null:"stations must be initialised";
		if (via[a][b] == -1)
			System.out.println(stations[a] + " " + stations[b]);
		else
		{
		showPath (via, a, via[a][b],stations);
		showPath (via, via[a][b], b, stations);
		}
	}
	
	private static void drawTable(int[][] table, String[] stations)
	  {
	    assert table!=null : "Uninitialized table";
	    assert stations!=null : "Uninitialized stations array";

	    int N = table.length;

	    // Every 'cell' of the table is 5 characters long: at most the station names are 4 characters long, plus one space added.

	    // print the first line, which is a line with all the to-station names
	    System.out.print("     ");
	    for(int i=0;i<N;i++)
	    {
	      System.out.printf("%s ",stations[i]);
	      for(int j=4-stations[i].length();j>0;j--)
	        System.out.print(" ");
	    }

	    // print the rest of the table, spaced out properly by checking the length of the integers.
	    for(int i=0;i<N;i++)
	    {
	      System.out.println("");
	      for(int j=0;j<N;j++)
	      {
	        // add the from-station
	        if(j==0)
	        {
	          System.out.printf("%s ",stations[i]);
	          for(int k=4-stations[i].length();k>0;k--)
	            System.out.print(" ");
	        }
	        System.out.printf("%d ",table[i][j]);
	        for(int k=4-String.valueOf(table[i][j]).length();k>0;k--)
	          System.out.print(" ");
	      }
	    }
	  }
	/** Prints the given table on the screen
	 * @param table
	 */
	static void printTable(int[][] table)
    {
		assert table!=null:"table must be initialised";
        for (int i=0; i<table.length; ++i)
        {
            for (int j=0; j<table[0].length; ++j)
            {
            	System.out.print(table[i][j] + "\t");
            }
            System.out.println();
        }
    }
	
	/** finds the biggest distance in the distance table and prints it on the screen
	 * @param table with distances
	 * @param stations
	 * @return
	 */
	private static int findBiggestDistance(int[][] table, String[]stations) 
	{
		assert table!=null:"table must be initialised";
		assert stations!=null:"stations must be initialised";
		int biggestDistance=0;
		String stationFrom="";
		String stationTo="";
		for (int i=0; i<table.length; ++i)
        {
            for (int j=0; j<table[0].length; ++j)
            {
            	if(table[i][j]>biggestDistance)
            	{
            		biggestDistance=table[i][j];
            		stationFrom=stations[i];
            		stationTo=stations[j];
            	}
            }
        }
		System.out.println("The biggest distance is "+biggestDistance+"km from "+stationFrom+" to "+stationTo);
		return biggestDistance;
	}
	
	/** Finds the name of the farthest station(1)
	 * @param table
	 * @param stations
	 * @return
	 */
	private static String biggestDistanceFrom(int[][] table, String[]stations) 
	{
		assert table!=null:"table must be initialised";
		assert stations!=null:"stations must be initialised";
		int biggestDistance=0;
		String stationFrom="";
		String stationTo="";
		for (int i=0; i<table.length; ++i)
        {
            for (int j=0; j<table[0].length; ++j)
            {
            	if(table[i][j]>biggestDistance)
            	{
            		biggestDistance=table[i][j];
            		stationFrom=stations[i];
            		stationTo=stations[j];
            	}
            }
        }
		return stationFrom;
	}
	/** Finds the name of the farthest station (2)
	 * @param table
	 * @param stations
	 * @return
	 */
	private static String biggestDistanceTo(int[][] table, String[]stations) 
	{
		assert table!=null:"table must be initialised";
		assert stations!=null:"stations must be initialised";
		int biggestDistance=0;
		String stationFrom="";
		String stationTo="";
		for (int i=0; i<table.length; ++i)
        {
            for (int j=0; j<table[0].length; ++j)
            {
            	if(table[i][j]>biggestDistance)
            	{
            		biggestDistance=table[i][j];
            		stationFrom=stations[i];
            		stationTo=stations[j];
            	}
            }
        }
		return stationTo;
	}
}
