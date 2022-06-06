package multichromatic;

import java.util.ArrayList;

public class Periodicity {
	
	static ArrayList<Patterns> []Daily_pattern=new ArrayList[Constants.numberOfColors];
	static ArrayList<Patterns> []weekdays=new ArrayList[Constants.numberOfColors];;
	static ArrayList<Patterns> []weekends=new ArrayList[Constants.numberOfColors];;
	static ArrayList<Patterns> []weekly_patterns=new ArrayList[Constants.numberOfColors];
	
	static ArrayList<Patterns> []Monte_Daily_pattern = new ArrayList[Constants.numberOfColors];
	static ArrayList<Patterns> []Monte_weekdays= new ArrayList[Constants.numberOfColors];
	static ArrayList<Patterns> []Monte_weekends =new ArrayList[Constants.numberOfColors];
	static ArrayList<Patterns> []Monte_weekly_patterns =new ArrayList[Constants.numberOfColors];
	static ArrayList<ChangeDetection> Daily_pattern_CD=new ArrayList<>();
	static ArrayList<DSChangeDetection> count_everything=new ArrayList<>();

}