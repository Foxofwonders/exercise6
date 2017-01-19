/**
 * @author Denise van Baalen (s4708237)
 * @author Anna Gansen (s4753755)
 */

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
		int[] biggestDistance = findBiggestDistance(table);
//		printTable(table);
		System.out.println("The bigges distance of " + biggestDistance[2] + "km is between: " + stations[biggestDistance[0]] + " and "
				+ stations[biggestDistance[1]] + ".");
		showPath(via,biggestDistance[0],biggestDistance[1],stations);

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
		assert via!=null:"via must be initialised";
		assert table!=null:"table must be initialised";
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
			System.out.println(stations[a] + " -> " + stations[b]);
		else
		{
			showPath (via, a, via[a][b],stations);
			showPath (via, via[a][b], b, stations);
		}
	}
	
	
	/** finds the biggest distance with the two stations in the distance table
	 * @param table with distances
	 * @return array with biggest distance and the 2 station numbers
	 */
	private static int[] findBiggestDistance(int[][] table) 
	{
		assert table!=null:"table must be initialised";
		int[] biggestDistance = { 0, 0, 0 };
		for (int r = 0; r < table.length; r++)
			for (int c = 0; c < table[r].length; c++)
				if (table[r][c] > biggestDistance[2]) 
				{
					biggestDistance[0] = r;
					biggestDistance[1] = c;
					biggestDistance[2] = table[r][c];
				}
		return biggestDistance;
		}
}

