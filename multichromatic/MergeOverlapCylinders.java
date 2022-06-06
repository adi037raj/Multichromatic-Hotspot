package multichromatic;



import java.util.ArrayList;
import java.util.Collections;

public class MergeOverlapCylinders {

	static ArrayList<Patterns>  mergeOverlap(ArrayList<Patterns> pattern) {

		Collections.sort(pattern, new PatternComperator());
		ArrayList<Patterns> temp=new ArrayList<Patterns>();

		if(pattern.isEmpty())
			return temp;
		temp.add(pattern.get(0));
		for(int i=1;i<pattern.size();i++)
		{
			int flag=1;
			for(int j=0;j<temp.size();j++)
			{

				if(isOveralapped(pattern.get(i), temp.get(j)))
				{
					flag=0;
					break;
				}



			}
			if(flag==1)
			{
				temp.add(pattern.get(i));
			}


		}











		return temp;






	}



	static boolean isOveralapped(Patterns p,Patterns q)
	{

		double volume_p=p.radius*p.radius*p.height;
		double volume_q=q.radius*q.radius*q.height;
		int x1=p.center.x;
		int y1=p.center.y;
		int r1=p.radius;
		int x2=q.center.x;
		int y2=q.center.y;
		int r2=q.radius;
		double area=0.0;
		area=intersectionArea(x1, y1, r1, x2, y2, r2);

		int s1=p.center.start,e1=p.center.end;
		int s2=q.center.start,e2=q.center.end;
		if(s1>e2 || s2>e1)
		{
			return false;
		}
		int start=Math.max(s1, s2);
		int end=Math.min(e1, e2);
		
		double volume=(area*Math.abs(start-end))/(Math.min(volume_p, volume_q));


		if(volume*100<Constants.overlappingPercentage)
			return false;
		return true;

	}









	static double   intersectionArea(int X1, int Y1,int R1, int X2,int Y2, int R2) {

		double Pi = Math.PI;
		double d, alpha, beta, a1, a2;
		double ans;
		d = Math.sqrt((X2 - X1) * (X2 - X1) + (Y2 - Y1) * (Y2 - Y1));

		if (d > R1 + R2) ans = 0;
		else if (d <= Math.abs(R2 - R1) && R1 >= R2)
			ans = (Pi * R2 * R2);

		else if (d <= Math.abs(R2 - R1) && R2 >= R1)
			ans = (Pi * R1 * R1);

		else {
			alpha = Math.acos((R1 * R1 + d * d - R2 * R2) / (2 * R1 * d)) * 2;
			beta = Math.acos((R2 * R2 + d * d - R1 * R1) / (2 * R2 * d)) * 2;
			a1 = 0.5 * beta * R2 * R2 - 0.5 * R2 * R2 * Math.sin(beta);
			a2 = 0.5 * alpha * R1 * R1 - 0.5 * R1 * R1 * Math.sin(alpha);
			ans = (a1 + a2);
		}
		return ans;












		// code here
	}












}