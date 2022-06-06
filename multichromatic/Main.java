package multichromatic;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.lang.Math;
import java.time.Period;

/**
 * main
 */
public class Main extends Thread{
	static String fileName = Constants.fileName;
	static int a = Constants.a;    // max x
	static int b = Constants.b;    // max y
	static int c = Constants.c;    // max time
	static int centerThreshold = Constants.centerThreshold;       // 
	static int htMin = Constants.htMin; 
	static int htMax = Constants.htMax;
	static int rMin = Constants.rMin;
	static int rMax = Constants.rMax;
	
	static long totalPoints = 0;
	int currColorThread;
	// initializing the grid to 0

	@Override
	public void run() {
		
		 
		  int colorsGrid[][][] = Constants.grid[currColorThread];
		  findPattern(Constants.totalPointsbyColor[currColorThread], currColorThread, 0);
		
		
	}
	
	
	

	static void addPointsToCummulativeGrid(int grid[][][][])
	{
		
		int day,x,y,t,color;
		int tempGrid[][][][];
		for(day=0;day<Constants.dayCount;day++)
		{
			//totalPoints = 0;
			tempGrid=new int[Constants.numberOfColors][Constants.a][Constants.b][Constants.dayTimeSlot];
			
			for(color=0;color<Constants.numberOfColors;color++)
			{
				
			
			for(x=0;x<Constants.a;x++)
			{
				for(y=0;y<Constants.b;y++)
				{
					for(t=0;t<96;t++)
					{
						int z = t+day*(Constants.dayTimeSlot);
						if(x==0 && y==0 && t==0)
						{
							tempGrid[color][x][y][t] = grid[color][x][y][z];
						}
						else
						{
							long var1 = x > 0 ? tempGrid[color][x-1][y][t]: 0;
							long var2 = y > 0 ? tempGrid[color][x][y-1][t]: 0;
							long var3 = t > 0 ? tempGrid[color][x][y][t-1]: 0;
							long var4 = (x > 0 && y > 0) ? tempGrid[color][x-1][y-1][t] : 0;
							long var5 = (x > 0 && t > 0) ? tempGrid[color][x-1][y][t-1] : 0;
							long var6 = (y > 0 && t > 0) ? tempGrid[color][x][y-1][t-1] : 0;
							long var7 = (x > 0 && y > 0 && t > 0) ? tempGrid[color][x-1][y-1][t-1] : 0;
							long var8 = grid[color][x][y][z];
							tempGrid[color][x][y][t] = (int) (var1 + var2 + var3 - var4 - var5 - var6 + var7 + var8);
						}
					}
				}
			}
			Constants.totalPointsbyColor[color]+=tempGrid[color][Constants.a-1][Constants.b-1][96- 1];
			}
			
			Constants.cummulativeGridList.add(day, new Cummulative_Grid(tempGrid));
		}
	}

	
	
	static void gridReset(int grid[][][], int x_axis_length, int y_axis_length, int z_axis_length) {        
		int i, j, k;
		for (i = 0; i < x_axis_length; i++) {
			for (j = 0; j < y_axis_length; j++) {
				for (k = 0; k < z_axis_length; k++) {
					grid[i][j][k] = 0;
				}
			}
		}
	}
	// storing the points in the grid
	static void addPointsToGrid(int grid[][][][], ArrayList<Point> pointList) {
		for (Point p : pointList) {
			int x = p.x;
			int y = p.y;
			int z = p.z;
			int c=p.color;
			// p.printer();
			grid[c][x][y][z]++;
		}
	}




//	static void statCollection(ArrayList<Patterns> candidatePatterns, HashMap<Integer, Integer> colorFrequency,
//			HashMap<Integer, Double> maxLLR, int color) {
//		for (Patterns cy : candidatePatterns) {
//
//			// pt.printer(); // todo remove
//			if (colorFrequency.get(color) == null) {
//				colorFrequency.put(color, 1);
//			} else {
//				int prevFreq = colorFrequency.get(color);
//				colorFrequency.put(color, prevFreq + 1);
//			}
//			if (maxLLR.get(color) == null) {
//				maxLLR.put(color, cy.llr);
//			} else {
//				Double prevLLR = maxLLR.get(color);
//				Double nextMaxLLR = Math.max(prevLLR, cy.llr);
//				maxLLR.put(color, nextMaxLLR);
//			}
//		}
//	}

	static void statPrint(HashMap<Integer, ArrayList<Point>> colorPoint, HashMap<Integer, Integer> colorFrequency,
			HashMap<Integer, Double> maxLLR) {
		for (HashMap.Entry<Integer, ArrayList<Point>> entry : colorPoint.entrySet()) { // iterate over all the entries
			// in colorPoint hashmap
			int color = entry.getKey();
			int freq = (colorFrequency.get(color) == null) ? 0 : colorFrequency.get(color);
			Double llr = (maxLLR.get(color) == null) ? 0 : maxLLR.get(color);

			System.out.printf("color: %d, freq: %d, maxLLR: %f\n", color, freq, llr);
			System.out.printf("points of above color: %d\n", entry.getValue().size());
		}
	}

	// this is the heart of the algorithm ---monochromatic 

	static void findPatternAssociation(int size,ArrayList<Integer> colorList)
	{
		int i,j,k,r,period=1,start,end;
		int times=0;
		Utils ut = new Utils();
		ArrayList<Patterns> Daily_pattern=new ArrayList<Patterns>();
		ArrayList<Patterns> weekdays=new ArrayList<Patterns>();
		ArrayList<Patterns> weekends=new ArrayList<Patterns>();
		ArrayList<Patterns> weekly_patterns=new ArrayList<Patterns>();
		Double max_llr=(double) 0;
		for(i=0;i<a;i++)       // iterating for every possible x 
		{  			
			for(j=0;j<b;j++)      // iterating for every possible y
			{
				for(r=rMin;r<=rMax;r+=1)         //  iterating for every possible radius
				{
		
					// radius checking
					if(i-r < 0 || i+r > a || j-r <0 || j+r > b) break;
					
					// daily patterns
					for(start=0; start<Constants.dayTimeSlot; start++)
					{
						for(end = start+1; end<Constants.dayTimeSlot; end++)
						{

							int inside_points=0;
							int outsidePoints=0;
							for(int day=0; day<Constants.dayCount; day+=1)
							{
								for(int color:colorList) {
								inside_points += ut.points_inside_cylinder(i, j, start, end, r,day,color);
								outsidePoints+= ut.points_inside_cylinder(i, j, end, Constants.dayTimeSlot, r,day,color);}
								
								if(day+1<Constants.dayCount)
									for(int color:colorList)
								outsidePoints+= ut.points_inside_cylinder(i, j, 0, start, r,day+1,color);
							}
							
							
							//System.out.println("inside is "+inside_points);
							Double LR0 = ut.llr(inside_points, end-start, r, size,Constants.dayCount);
							Double LR2 = ut.llr(outsidePoints, Constants.dayTimeSlot-(end-start), r,size,Constants.dayCount);
							Double llrVal=LR2!=0?LR0/LR2:LR0;  // eliminating the elephant pattern
							
							
							max_llr=Math.max(max_llr, llrVal);
							//System.out.println("Llr "+max_llr);
							if (llrVal > Constants.logThreshold_daily) {
								
								Hotspot_Point currPoint = new Hotspot_Point(i, j, start,end, 100); // where color 100 implies general color
								Daily_pattern.add(new Patterns(currPoint, r, end-start, llrVal));

							}
						}
					}
					
					//System.out.println("Daily pattern complete");
					
					
					// weekdays
					for(start=0; start<Constants.dayTimeSlot; start++)

					{
						for(end = start+1; end<Constants.dayTimeSlot; end++)             // considering only the 3 hours patterns
						{
							//Patterns maxLLRPattern = new Patterns(new Point(), 0, 0, -1);

							int inside_points=0;
							int outsidePoints=0;
							for(int day=0; day<Constants.dayCount; day+=1) // consider day 0 means monday,1 -> tuesday , so on
							{
								if(day%7==5 || day%7==6)continue;
								for(int color:colorList) {
								inside_points += ut.points_inside_cylinder(i, j, start, end, r,day,color);
								outsidePoints+= ut.points_inside_cylinder(i, j, end, Constants.dayTimeSlot, r,day,color);}
								
								if(day+1<Constants.dayCount)
									for(int color:colorList)
								outsidePoints+= ut.points_inside_cylinder(i, j, 0, start, r,day+1,color);
							}

							Double LR0 = ut.llr(inside_points, end-start, r, size,5*Constants.weeks);
							Double LR2 = ut.llr(outsidePoints, Constants.dayTimeSlot-(end-start), r, size,5*Constants.weeks);
							Double llrVal=LR2!=0?LR0/LR2:LR0;
							max_llr=Math.max(max_llr, llrVal);
							if (llrVal > Constants.logThreshold_weekdays) {
								Hotspot_Point currPoint = new Hotspot_Point(i, j, start,end, 100);
								weekdays.add(new Patterns(currPoint, r, end-start, llrVal));

							}
						}	
					}
					//System.out.println("weekdays pattern complete");
					
					
					
					
					
					// weekends
					for(start=0; start<Constants.dayTimeSlot; start++)
					{
						for(end = start+1; end<Constants.dayTimeSlot; end++)             // considering only the 3 hours patterns
						{
							//Patterns maxLLRPattern = new Patterns(new Point(), 0, 0, -1);

							int inside_points=0;
							int outsidePoints=0;
							for(int day=0; day<Constants.dayCount; day+=1) // consider day 0 means monday,1 -> tuesday , so on
							{
								if(day%7==5 || day%7==6)
								{
									for(int color:colorList) {
									inside_points += ut.points_inside_cylinder(i, j, start, end, r,day,color);
									outsidePoints+= ut.points_inside_cylinder(i, j, end, Constants.dayTimeSlot, r,day,color);}
								}
									
								
								if(day+1<Constants.dayCount)
									for(int color:colorList)
								outsidePoints+= ut.points_inside_cylinder(i, j, 0, start, r,day+1,color);
							}

							Double LR0 = ut.llr(inside_points, end-start, r,size,2*Constants.weeks);
							Double LR2 = ut.llr(outsidePoints, Constants.dayTimeSlot-(end-start), r, size,2*Constants.weeks);
							Double llrVal=LR2!=0?LR0/LR2:LR0;
							max_llr=Math.max(max_llr, llrVal);
							if (llrVal > Constants.logThreshold_weekends) {
								Hotspot_Point currPoint = new Hotspot_Point(i, j, start,end, 100);
								weekends.add(new Patterns(currPoint, r, end-start, llrVal));
							}
						}	
					}
					//System.out.println("weekends pattern complete");
					
					
					
					// weekly patterns
					
					for(start=0; start<Constants.dayTimeSlot; start++)
					{
						for(end = start+1; end<Constants.dayTimeSlot; end++)                    // considering only the 3 hours patterns
						{
							//Patterns maxLLRPattern = new Patterns(new Point(), 0, 0, -1);

							for(period=1;period<=7;period++)
							{
								int inside_points=0;
								int outsidePoints=0;
								for(int day=period-1; day<Constants.dayCount; day+=7)
								{
									for(int color:colorList) {
									
									inside_points += ut.points_inside_cylinder(i, j, start, end, r,day,color);
									outsidePoints+= ut.points_inside_cylinder(i, j, end, Constants.dayTimeSlot, r,day,color);}
									
									if(day+1<Constants.dayCount)
										for(int color:colorList)
									outsidePoints+= ut.points_inside_cylinder(i, j, 0, start, r,day+1,color);
								}

								Double LR0 = ut.llr(inside_points, end-start, r, size,1*Constants.weeks);
								Double LR2 = ut.llr(outsidePoints, Constants.dayTimeSlot-(end-start), r, size,1*Constants.weeks);
								Double llrVal=LR2!=0?LR0/LR2:LR0;
								max_llr=Math.max(max_llr, llrVal);
								if (llrVal > Constants.logThreshold_weekly) {
									
									Hotspot_Point currPoint = new Hotspot_Point(i, j, start,end, 100);
									weekly_patterns.add(new Patterns(currPoint, r, end-start, llrVal));

								}
							}
						}
					}
					//System.out.println("weekly patten complete");
				}
			}
		}
		AssociationRuleMining.daily_Association=Daily_pattern;
		AssociationRuleMining.weekdays_Association=weekdays;
		AssociationRuleMining.weekends_Association=weekends;
		AssociationRuleMining.weekdays_Association=weekdays;
		
		
	}
	
	
	static void findPattern(int size, int  color,int isMonte) { // Here No parameters are required need to remove

		int i,j,k,r,period=1,start,end;
		int times=0;
		Utils ut = new Utils();
		ArrayList<Patterns> Daily_pattern=new ArrayList<Patterns>();
		ArrayList<Patterns> weekdays=new ArrayList<Patterns>();
		ArrayList<Patterns> weekends=new ArrayList<Patterns>();
		ArrayList<Patterns> weekly_patterns=new ArrayList<Patterns>();
		Double max_llr=(double) 0;
		for(i=0;i<a;i++)       // iterating for every possible x 
		{  			
			for(j=0;j<b;j++)      // iterating for every possible y
			{
				for(r=rMin;r<=rMax;r+=1)         //  iterating for every possible radius
				{
		
					// radius checking
					if(i-r < 0 || i+r > a || j-r <0 || j+r > b) break;
					
					// daily patterns
					for(start=0; start<Constants.dayTimeSlot; start++)
					{
						for(end = start+1; end<Constants.dayTimeSlot; end++)
						{

							int inside_points=0;
							int outsidePoints=0;
							for(int day=0; day<Constants.dayCount; day+=1)
							{
								
								inside_points += ut.points_inside_cylinder(i, j, start, end, r,day,color);
								outsidePoints+= ut.points_inside_cylinder(i, j, end, Constants.dayTimeSlot, r,day,color);
								
								if(day+1<Constants.dayCount)
									
								outsidePoints+= ut.points_inside_cylinder(i, j, 0, start, r,day+1,color);
							}
							
							
							//System.out.println("inside is "+inside_points);
							Double LR0 = ut.llr(inside_points, end-start, r, size,Constants.dayCount);
							Double LR2 = ut.llr(outsidePoints, Constants.dayTimeSlot-(end-start), r,size,Constants.dayCount);
							Double llrVal=LR2!=0?LR0/LR2:LR0;  // eliminating the elephant pattern
							
							
							max_llr=Math.max(max_llr, llrVal);
							//System.out.println("Llr "+max_llr);
							if (llrVal > Constants.logThreshold_daily) {

								Hotspot_Point currPoint = new Hotspot_Point(i, j, start,end, color);
								Daily_pattern.add(new Patterns(currPoint, r, end-start, llrVal));

							}
						}
					}
					
					//System.out.println("Daily pattern complete");
					
					
					// weekdays
					for(start=0; start<Constants.dayTimeSlot; start++)

					{
						for(end = start+1; end<Constants.dayTimeSlot; end++)             // considering only the 3 hours patterns
						{
							//Patterns maxLLRPattern = new Patterns(new Point(), 0, 0, -1);

							int inside_points=0;
							int outsidePoints=0;
							for(int day=0; day<Constants.dayCount; day+=1) // consider day 0 means monday,1 -> tuesday , so on
							{
								if(day%7==5 || day%7==6)continue;
								
								inside_points += ut.points_inside_cylinder(i, j, start, end, r,day,color);
								outsidePoints+= ut.points_inside_cylinder(i, j, end, Constants.dayTimeSlot, r,day,color);
								
								if(day+1<Constants.dayCount)
								
								outsidePoints+= ut.points_inside_cylinder(i, j, 0, start, r,day+1,color);
							}

							Double LR0 = ut.llr(inside_points, end-start, r, size,5*Constants.weeks);
							Double LR2 = ut.llr(outsidePoints, Constants.dayTimeSlot-(end-start), r, size,5*Constants.weeks);
							Double llrVal=LR2!=0?LR0/LR2:LR0;
							max_llr=Math.max(max_llr, llrVal);
							if (llrVal > Constants.logThreshold_weekdays) {
								Hotspot_Point currPoint = new Hotspot_Point(i, j, start,end, color);
								weekdays.add(new Patterns(currPoint, r, end-start, llrVal));

							}
						}	
					}
					//System.out.println("weekdays pattern complete");
					
					
					
					
					
					// weekends
					for(start=0; start<Constants.dayTimeSlot; start++)
					{
						for(end = start+1; end<Constants.dayTimeSlot; end++)             // considering only the 3 hours patterns
						{
							//Patterns maxLLRPattern = new Patterns(new Point(), 0, 0, -1);

							int inside_points=0;
							int outsidePoints=0;
							for(int day=0; day<Constants.dayCount; day+=1) // consider day 0 means monday,1 -> tuesday , so on
							{
								if(day%7==5 || day%7==6)
								{
									
									inside_points += ut.points_inside_cylinder(i, j, start, end, r,day,color);
									outsidePoints+= ut.points_inside_cylinder(i, j, end, Constants.dayTimeSlot, r,day,color);
								}
									
								
								if(day+1<Constants.dayCount)
									
								outsidePoints+= ut.points_inside_cylinder(i, j, 0, start, r,day+1,color);
							}

							Double LR0 = ut.llr(inside_points, end-start, r,size,2*Constants.weeks);
							Double LR2 = ut.llr(outsidePoints, Constants.dayTimeSlot-(end-start), r, size,2*Constants.weeks);
							Double llrVal=LR2!=0?LR0/LR2:LR0;
							max_llr=Math.max(max_llr, llrVal);
							if (llrVal > Constants.logThreshold_weekends) {
								Hotspot_Point currPoint = new Hotspot_Point(i, j, start,end, color);
								weekends.add(new Patterns(currPoint, r, end-start, llrVal));
							}
						}	
					}
					//System.out.println("weekends pattern complete");
					
					
					
					// weekly patterns
					
					for(start=0; start<Constants.dayTimeSlot; start++)
					{
						for(end = start+1; end<Constants.dayTimeSlot; end++)                    // considering only the 3 hours patterns
						{
							//Patterns maxLLRPattern = new Patterns(new Point(), 0, 0, -1);

							for(period=1;period<=7;period++)
							{
								int inside_points=0;
								int outsidePoints=0;
								for(int day=period-1; day<Constants.dayCount; day+=7)
								{
									
									inside_points += ut.points_inside_cylinder(i, j, start, end, r,day,color);
									outsidePoints+= ut.points_inside_cylinder(i, j, end, Constants.dayTimeSlot, r,day,color);
									
									if(day+1<Constants.dayCount)
										
									outsidePoints+= ut.points_inside_cylinder(i, j, 0, start, r,day+1,color);
								}

								Double LR0 = ut.llr(inside_points, end-start, r, size,1*Constants.weeks);
								Double LR2 = ut.llr(outsidePoints, Constants.dayTimeSlot-(end-start), r, size,1*Constants.weeks);
								Double llrVal=LR2!=0?LR0/LR2:LR0;
								max_llr=Math.max(max_llr, llrVal);
								if (llrVal > Constants.logThreshold_weekly) {
									Hotspot_Point currPoint = new Hotspot_Point(i, j, start,end, color);
									weekly_patterns.add(new Patterns(currPoint, r, end-start, llrVal));

								}
							}
						}
					}
					//System.out.println("weekly patten complete");
				}
			}
		}
		if(isMonte==0)
		{
			System.out.println("Hotspot geenrated ");
			Daily_pattern=MergeOverlapCylinders.mergeOverlap(Daily_pattern);
			
			weekdays=MergeOverlapCylinders.mergeOverlap(weekdays);
			weekends=MergeOverlapCylinders.mergeOverlap(weekends);
			weekly_patterns=MergeOverlapCylinders.mergeOverlap(weekly_patterns);
			System.out.println("Overlapped done");
			System.out.println(Daily_pattern.size());
			System.out.println(weekdays.size());
			System.out.println(weekends.size());
			System.out.println(weekly_patterns.size());
			Periodicity.Daily_pattern[color]=Daily_pattern;
			Periodicity.weekdays[color]=weekdays;
			Periodicity.weekends[color]=weekends;
			Periodicity.weekly_patterns[color]=weekly_patterns;
			
			
			
			// true denotes that every color are unused and are eligible candidate for association rule 
			
			if(Daily_pattern.isEmpty())
				Constants.colorUnused[color]=true;
			else
				Constants.colorUnused[color]=false;
			
			
			System.out.println(max_llr);
		}
		else
		{
			
			
			//System.out.println("Monte Carlo");
			if(Daily_pattern.isEmpty()==false){
				Collections.sort(Daily_pattern, new PatternComperator());
			
			Periodicity.Monte_Daily_pattern[color].add(Daily_pattern.get(0));
			}
			
			if(weekdays.isEmpty()==false){

				Collections.sort(weekdays, new PatternComperator());
			
			Periodicity.Monte_weekdays[color].add(weekdays.get(0));
			}

			if(weekends.isEmpty()==false){

				Collections.sort(weekends, new PatternComperator());
			
				Periodicity.Monte_weekends[color].add(weekends.get(0));

			}
			
			if(weekly_patterns.isEmpty()==true)
			return;
		
			
			Collections.sort(weekly_patterns, new PatternComperator());
			
			Periodicity.Monte_weekly_patterns[color].add(weekly_patterns.get(0));
			
			
			
		}
		
		
	}

	static double customLog(double base, double logNumber) {
		return Math.log(logNumber) / Math.log(base);
	}
	static double findEntropy(ArrayList<Integer> a)
	{

		int total=0;
		for(int i:a)
		total+=i;

		int sz=a.size();
		if(sz<2)return 0;
		double ans=0;
		
		for(int i:a){
			double p=(double) i/total;
			ans += p*customLog(sz, p);

		}


		return -ans;
	}

	static void findPattern_changeDetection()
	{
		



		int i,j,r,start,end,day;


		Utils ut = new Utils();

		

		


		// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4


		for(i=0;i<a;i++)       // iterating for every possible x 
		{  			
			for(j=0;j<b;j++)      // iterating for every possible y
			{
				for(r=rMin;r<=rMax;r+=1)         //  iterating for every possible radius
				{
		
					// radius checking
					if(i-r < 0 || i+r > a || j-r <0 || j+r > b) break;
					
					// daily patterns
					for(start=0; start<Constants.dayTimeSlot; start++)
					{
					for(end = start+1; end<Constants.dayTimeSlot; end++)
					{
					int inside_points=0;
					int tp=0;
					
					ArrayList<Integer> dominance=new ArrayList<>();
					ArrayList<Integer> countList=new ArrayList<>();
					Map<Integer,Integer> colorlist =new HashMap<>();
					for(day=0;day<Constants.slidingWindowCD;day++)
					{	
						
						// first find the total number of points in this range (all color)
					int totalPoints=0;
					for(int color=0;color<Constants.numberOfColors;color++)
					{
						totalPoints+=ut.points_inside_cylinder(i, j, start, end, r,day,color);
					}
					 

					// only conisder that colors whose percentage is atleast 20% so that it will help in calculating A
					
					int candidatePoints=0;
					int lastS=dominance.size();
					for(int color=0;color<Constants.numberOfColors;color++)
					{
						int curr_points=(int)ut.points_inside_cylinder(i, j, start, end, r,day,color);
						double percentage =totalPoints==0?0:(double) curr_points/totalPoints;
					//	System.out.println("percentage is "+percentage);
						if(percentage >Constants.colorChangePercentage){
							dominance.add(color);
							candidatePoints+=curr_points;
							if(!colorlist.containsKey(color))
							{
								colorlist.put(color,curr_points);
								tp+=Constants.totalPointsbyColor[color];
							}
							else
							{
								colorlist.put(color,colorlist.get(color)+curr_points);
							}

							
							countList.add(curr_points);
							
						}
						else if(percentage>Constants.colorPercentage)
						{
							candidatePoints+=curr_points;
							countList.add(curr_points);
							

							if(!colorlist.containsKey(color))
							{
								colorlist.put(color,curr_points);
								tp+=Constants.totalPointsbyColor[color];
							}
							else
							{
								colorlist.put(color,colorlist.get(color)+curr_points);
							}

							
						}
						else{
							
						}





					}

					if(dominance.size()>lastS)
					{
						inside_points+=candidatePoints;
					}


				}

	double llr=ut.llr(inside_points, end-start, r, tp, Constants.dayCount);
	//System.out.println("inside points is +" + inside_points);
	//System.out.println("tp  "+ tp);
	if(llr>Constants.changeDetectionThreshold)
	{
		double entropy=findEntropy(countList);
		//System.out.println("entropy is +" + entropy);
		ArrayList<Integer> temp=new ArrayList<>();
		for(int clr:colorlist.keySet())
		{
			temp.add(clr);
		}
		


		ChangeDetection c=new ChangeDetection(i, j, start, end, r, end-start, llr, temp, entropy, 0, Constants.slidingWindowCD-1);
		Periodicity.Daily_pattern_CD.add(c);
	}					

				for(day=Constants.slidingWindowCD; day<Constants.dayCount; day+=1)
				{
						totalPoints=0;
					// subtract the inital only
					for(int color=0;color<Constants.numberOfColors;color++)
					{
						totalPoints+=ut.points_inside_cylinder(i, j, start, end, r,day-Constants.slidingWindowCD,color);
					}
					for(int color=0;color<Constants.numberOfColors;color++)
					{
						int curr_points=(int)ut.points_inside_cylinder(i, j, start, end, r,day-Constants.slidingWindowCD,color);
						double percentage =(double) curr_points/totalPoints;

						if(percentage >Constants.colorChangePercentage){
							dominance.remove(new Integer(color));
							inside_points-=curr_points;
							

							colorlist.put(color, colorlist.get(color) - curr_points);
							if(colorlist.get(color)==0)
							{
								colorlist.remove(color);
								tp-=Constants.totalPointsbyColor[color];
							}
							countList.remove(new Integer(curr_points));
							
						}
						else if(percentage>Constants.colorPercentage)
						{
							inside_points-=curr_points;
							colorlist.put(color, colorlist.get(color) - curr_points);
							if(colorlist.get(color)==0)
							{
								colorlist.remove(color);
								tp-=Constants.totalPointsbyColor[color];
							}
							
							countList.remove(new Integer(curr_points));
							
						}
						else{
						
						}





					}



					// Now add for cuurent day
					int totalPoints=0;
					for(int color=0;color<Constants.numberOfColors;color++)
					{
						totalPoints+=ut.points_inside_cylinder(i, j, start, end, r,day,color);
					}
					 
					int candidatePoints=0;
					int sz=dominance.size();
					for(int color=0;color<Constants.numberOfColors;color++)
					{
						int curr_points=(int)ut.points_inside_cylinder(i, j, start, end, r,day,color);
						double percentage =(double) curr_points/totalPoints;

						if(percentage >Constants.colorChangePercentage){
							dominance.add(color);
							candidatePoints+=curr_points;
							
							countList.add(curr_points);
							if(!colorlist.containsKey(color))
							{
								colorlist.put(color,curr_points);
								tp+=Constants.totalPointsbyColor[color];
							}
							else
							{
								colorlist.put(color,colorlist.get(color)+curr_points);
							}
						}
						else if(percentage>Constants.colorPercentage)
						{
							candidatePoints+=curr_points;	
							countList.add(curr_points);
							if(!colorlist.containsKey(color))
							{
								colorlist.put(color,curr_points);
								tp+=Constants.totalPointsbyColor[color];
							}
							else
							{
								colorlist.put(color,colorlist.get(color)+curr_points);
							}


						}
						else{
							
						}





					}
					if(dominance.size()>sz)
					inside_points+=candidatePoints;

								
					llr=ut.llr(inside_points, end-start, r, tp, Constants.dayCount);
					//System.out.println("tp  "+ tp);
					if(llr>Constants.changeDetectionThreshold)
			     	{
					double entropy=findEntropy(countList);
					ArrayList<Integer> temp=new ArrayList<>();
		for(int clr:colorlist.keySet())
		{
			temp.add(clr);
		}

		
		
					ChangeDetection c=new ChangeDetection(i, j, start, end, r, end-start, llr, temp, entropy, day, day+Constants.slidingWindowCD-1);
					Periodicity.Daily_pattern_CD.add(c);
					}					


								
				}



				

							

							}
						}
					}
					
					//System.out.println("Daily pattern complete");
					
					
					// weekdays
					
					//System.out.println("weekdays pattern complete");
					
					
					
					
					
					
					//System.out.println("weekly patten complete");
				}
			}









		// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	}
	

	public static void main(String[] args) throws Exception {
		
		//FileOutput.clearOutputFile();
		    


		for(int i=0;i<Constants.numberOfColors;i++)
		  {
			  //System.out.println(Periodicity.Monte_Daily_pattern[i].size());
			  Periodicity.Monte_Daily_pattern[i]=new ArrayList<>();
			  Periodicity.Monte_weekdays[i]=new ArrayList<>();
			  Periodicity.Monte_weekends[i]=new ArrayList<>();
			  Periodicity.Monte_weekly_patterns[i]=new ArrayList<>();
		  }   
		CSVScanner sc = new CSVScanner();
		Utils ut = new Utils();
		ArrayList<Point> points = sc.readCSV(fileName);
		Constants.total_no_of_points = points.size();
		
		System.out.println("Input done");
		System.out.println("Total No. of points are "+Constants.total_no_of_points);
		
		HashMap<Integer, ArrayList<Point>> colorPoint = new HashMap<Integer, ArrayList<Point>>();
		for (Point p : points) {
			// p.printer();
			ut.addToList(p.color, p, colorPoint);         // distinguish points with respect to colors and store in colorpoints
		}

		int x_axis_length = a;
		int y_axis_length = b;
		int z_axis_length = c;
		
		Constants.grid = new int[Constants.numberOfColors][x_axis_length][y_axis_length][z_axis_length];
		
	
		long start=System.currentTimeMillis();
		
		
		
		addPointsToGrid(Constants.grid, points);
		addPointsToCummulativeGrid(Constants.grid);
		



		System.out.println("total points " + Constants.total_no_of_points);
		

		for(int i=0;i<Constants.numberOfColors;i++)
		{
			System.out.println(Constants.totalPointsbyColor[i]+ " this is total color points in "+i);
		}
		

		System.out.println("before findpattern");
		Main threadObj[]=new Main[Constants.numberOfColors];
		  for (Map.Entry<Integer,ArrayList<Point>> entry : colorPoint.entrySet())
		  {
			  int color=entry.getKey();
			  threadObj[color]=new Main();
			  threadObj[color].currColorThread=color;
			  threadObj[color].start();
			  System.out.println("cuurent color thread is "+color);
	
		  }
		  for (Map.Entry<Integer,ArrayList<Point>> entry : colorPoint.entrySet())
		  {
			  int color=entry.getKey();
			  threadObj[color].join();
		  }
		
		
		
		System.out.println("patterns findpattern completed");
		
        
		System.out.println("Change detetction started ");


	 	findPattern_changeDetection();
	 FileOutput.ChangeDetectionOutputFile();

		System.out.println("Change detetction eneed");


		System.out.println("Association Rule started");
		AssociationRuleMining.findAssociation();
		
		System.out.println("Association Rule ended");
		System.out.println("Monte Carlo Simulatio Started");
	Monte_Carlo.StatSignificance();
		System.out.println("Monte Carlo Simulatio finished");
		FileOutput.appendOutput(); 
		
		
		
		
		long end=System.currentTimeMillis();
		System.out.println("The total time taken is"+(end-start));
		
		
		
		
		
	}
}