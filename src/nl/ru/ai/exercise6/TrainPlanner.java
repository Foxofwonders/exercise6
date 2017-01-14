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
		for(int i = 0;i<noStations;i++)
		{
			for(int j = 0;j<noStations;j++)
			{
				via[i][j]=-1;
			}
		}
		table = addDistances(table,stations);
		table = floydWarshall (table, via);
//		printTable(table);
		

	}
	
	private static void initTable(int[][] table) 
	{
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

	static String[] readStations()
	{
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
	
	static int[][] addDistances(int[][] table, String[] stations)
	{
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
	
	static int stationNumber(String name, String [] stations)
	{
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
	static int[][] floydWarshall (int [ ] [ ] table, int [ ] [ ] via)
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
							System.out.println(alternative);
							table[a][b] = alternative;
							via[a][b] = k;
						}
					}
			}
		}
		printTable(table);
		return table;
	}
	
	static void showPath (int [ ] [ ] via, int a, int b)
	{
		if (via[a][b] == -1)
			System.out.println(a + " " + b);
		else
		{
		showPath (via, a, via[a][b]);
		showPath (via, via[a][b], b);
		}
	}
	
	static void printTable(int[][] table)
    {
        for (int i=0; i<table.length; ++i)
        {
            for (int j=0; j<table[0].length; ++j)
            {
                if (table[i][j]==0)
                   System.out.print(" - ");
                else
                    System.out.print(table[i][j]+"   ");
            }
            System.out.println();
        }
    }
}
