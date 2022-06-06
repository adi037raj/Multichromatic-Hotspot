package multichromatic;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.print.DocFlavor.INPUT_STREAM;

public class AssociationRuleMining {
	static String outFileName = Constants.outputFile;
	static ArrayList<ArrayList<Integer>> possibleAssociation;
	static ArrayList<Patterns> daily_Association;
	static ArrayList<Patterns> weekly_Association;
	
	static ArrayList<Patterns> weekdays_Association;
	static ArrayList<Patterns> weekends_Association;
	
	

	static int getAvailableColorSize(){
		int c=0;
		for(int i=0;i<Constants.numberOfColors;i++)
			if(Constants.colorUnused[i])
			c++;
			return c;
	}

	static void findAssociation() throws Exception {

		int k;
		k=2;
	
		while(true)
		{
			possibleAssociation=new ArrayList<ArrayList<Integer>>();
			int n=getAvailableColorSize();
			if(n==0)return ;
			if(k>n)return ;
			int j=0;
			int []colorListAvailable=new int[n];
			for(int i=0;i<Constants.numberOfColors;i++)
			if(Constants.colorUnused[i])
			colorListAvailable[j++]=i;
			printCombination(colorListAvailable,n,k);
			k++;
			System.out.println("This is the possoble associaiton color list "+possibleAssociation);
			for(ArrayList<Integer> p:possibleAssociation) {
				
				daily_Association = new ArrayList<Patterns>();
				weekly_Association=new ArrayList<Patterns>();
				weekdays_Association = new ArrayList<Patterns>();
				weekends_Association=new ArrayList<Patterns>();
				int totalPoints=0;
				for(int color:p)
				{
					totalPoints+=Constants.totalPointsbyColor[color];
				}


				Main.findPatternAssociation(totalPoints, p);
				
				if(!daily_Association.isEmpty() || !weekly_Association.isEmpty() || ! weekdays_Association.isEmpty() || !weekends_Association.isEmpty())
				{
					
					String fileName="";
					System.out.println(p + "this is the color assocuition ");
					for(int color:p)
					{
						Constants.colorUnused[color]=false;
						fileName += String.valueOf(color)+" ";
						
					}

					//Monte_Carlo.monte_carlo_Association(p);
					FileOutput.writeFileforAssociation(fileName, p);
					
					
					
						
					
					
				}
				
				
				
				
				
			}
			
		
			

		}




	}

	// #######################################



	static void combinationUtil(int arr[], int n, int r,
                          int index, int data[], int i)
    {
        
        if (index == r) {
			ArrayList<Integer> temp=new ArrayList<>();
            for (int j = 0; j < r; j++)
				temp.add(data[j]);
               possibleAssociation.add(temp);
            return;
        }
 
        
        if (i >= n)
            return;
 
       
        data[index] = arr[i];
        combinationUtil(arr, n, r, index + 1,
                               data, i + 1);
 
       
        combinationUtil(arr, n, r, index, data, i + 1);
    }
 
   
    static void printCombination(int arr[], int n, int r)
    {
       
        int data[] = new int[r];
 
       
        combinationUtil(arr, n, r, 0, data, 0);
    }
	// ###############################














}
