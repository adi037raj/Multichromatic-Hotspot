package multichromatic;

import java.util.*;
public class Monte_Carlo extends Thread{
	static int grid[][][][];
	int currColor;
	
	@Override
	public void run() {
		
		
		
		Main.findPattern(Constants.total_no_of_points, currColor, 1);

		//System.out.println("curr color is "+currColor);
		//System.out.println("siz of daily monte "+Periodicity.Monte_Daily_pattern[currColor].size());
		if(Periodicity.Monte_Daily_pattern[currColor].isEmpty()==false)
         Collections.sort(Periodicity.Monte_Daily_pattern[currColor], new PatternComperator());
		if(Periodicity.Monte_weekdays[currColor].isEmpty()==false)
		Collections.sort(Periodicity.Monte_weekdays[currColor], new PatternComperator());
		if(Periodicity.Monte_weekends[currColor].isEmpty()==false)
		Collections.sort(Periodicity.Monte_weekends[currColor], new PatternComperator());
		if(Periodicity.Monte_weekly_patterns[currColor].isEmpty()==false)
		Collections.sort(Periodicity.Monte_weekly_patterns[currColor], new PatternComperator());
		
		// Daily pattern
		
		
		
		for(int i=0;i<Periodicity.Daily_pattern[currColor].size();i++)
		{
			//System.out.println(Periodicity.Monte_Daily_pattern[currColor].isEmpty() + "daily empty monte");
			
			
			for(int j=0;j<Periodicity.Monte_Daily_pattern[currColor].size();j++)
			{
				//System.out.println(Periodicity.Monte_Daily_pattern[currColor].get(j).llr + "this is daily monte carlo");
				if(Periodicity.Daily_pattern[currColor].get(i).llr >= Periodicity.Monte_Daily_pattern[currColor].get(j).llr)
				{
					double p = (double)(j+1)/(Constants.monte_carlo_simus+1);
				//	System.out.println("p value is "+p);
					if(p > Constants.p_value_threshold)
					{
						Periodicity.Daily_pattern[currColor].remove(i);
						i--;
					}
					else {
						Periodicity.Daily_pattern[currColor].get(i).p_value = p;
					}
					
					
				}
			}
		}
		
		
		
		
		// Weekdays
		
		for(int i=0;i<Periodicity.weekdays[currColor].size();i++)
		{
			//System.out.println(Periodicity.Monte_weekdays[currColor].isEmpty() + "weekdays empty monte");

			
			for(int j=0;j<Periodicity.Monte_weekdays[currColor].size();j++)
			{
				if(Periodicity.weekdays[currColor].get(i).llr >= Periodicity.Monte_weekdays[currColor].get(j).llr)
				{
					double p = (double)(j+1)/(Constants.monte_carlo_simus+1);
					
					if(p > Constants.p_value_threshold)
					{
						Periodicity.weekdays[currColor].remove(i);
						i--;
					}
					else {
						Periodicity.weekdays[currColor].get(i).p_value = p;
					}
					
					
				}
			}
		}
		
		
		// Weekends
		
		
		for(int i=0;i<Periodicity.weekends[currColor].size();i++)
		{
			//System.out.println(Periodicity.Monte_weekends[currColor].isEmpty() + "weekends monte carlo empty");
			

			for(int j=0;j<Periodicity.Monte_weekends[currColor].size();j++)
			{
				if(Periodicity.weekends[currColor].get(i).llr >= Periodicity.Monte_weekends[currColor].get(j).llr)
				{
					double p = (double)(j+1)/(Constants.monte_carlo_simus+1);
					
					if(p > Constants.p_value_threshold)
					{
						Periodicity.weekends[currColor].remove(i);
						i--;
					}
					else {
						Periodicity.weekends[currColor].get(i).p_value = p;
					}
					
					
				}
			}
		}
		
		
		// weekly
		
		for(int i=0;i<Periodicity.weekly_patterns[currColor].size();i++)
		{
			//System.out.println(Periodicity.Monte_weekly_patterns[currColor].isEmpty() + "weekly monte carlo empty");
			for(int j=0;j<Periodicity.Monte_weekly_patterns[currColor].size();j++)
			{
				if(Periodicity.weekly_patterns[currColor].get(i).llr >= Periodicity.Monte_weekly_patterns[currColor].get(j).llr)
				{
					double p = (double)(j+1)/(Constants.monte_carlo_simus+1);
					
					if(p > Constants.p_value_threshold)
					{
						Periodicity.weekly_patterns[currColor].remove(i);
						i--;
					}
					else {

						Periodicity.weekly_patterns[currColor].get(i).p_value = p;
					}
					
					
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	public static int getRandomNumberUsingInts(int min, int max) {
	    Random random = new Random();
	    return random.ints(min, max)
	      .findFirst()
	      .getAsInt();
	}
	
	
	
	static void StatSignificance() {
	
	for(int i=0;i<Constants.monte_carlo_simus;i++)
	{
		if(i%20==0)
		System.out.println("current monte is "+i);

		int grid[][][][]= new int[Constants.numberOfColors][Constants.a][Constants.b][Constants.c];
		
		for(int k=0;k<Constants.numberOfColors;k++)
		{
			int points = Constants.totalPointsbyColor[k];
			for(int j=0;j<points;j++)
		{
			int x=getRandomNumberUsingInts(0,Constants.a);
			int y=getRandomNumberUsingInts(0,Constants.b);
			int z=getRandomNumberUsingInts(0,Constants.c);
			
			
			grid[k][x][y][z]++;
			
		}

		}



		
		
		
		
		Constants.cummulativeGridList.clear();
		Constants.cummulativeGridList=new ArrayList<>(Constants.dayCount+1);
		Main.addPointsToCummulativeGrid(grid);
		Monte_Carlo thObj[]=new Monte_Carlo[Constants.numberOfColors];
		for(int color = 0; color<Constants.numberOfColors;color++)
		{
			thObj[color]=new Monte_Carlo();
			thObj[color].currColor=color;
			thObj[color].start();
		}
		
		for(int color = 0; color<Constants.numberOfColors;color++)
		{
			
			try {
				thObj[color].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
	}
	
	
	
	
	}

}