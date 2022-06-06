package multichromatic;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {
	static int a = Constants.a;
	static int b = Constants.b;
	static int c = Constants.dayTimeSlot;
	// static int dayCount = Constants.dayCount;

	synchronized  long points_inside_cylinder(int x, int y, int hight_minimum, int height_maximum, int r, int day,int color){

		int xMin = Math.max(0, x - r);
		int xMax = Math.min(a - 1, x + r);
		int yMin = Math.max(0, y - r);
		int yMax = Math.min(b - 1, y + r);
		int zMin = hight_minimum;
		int zMax = Math.min(hight_minimum + height_maximum, c - 1);

		long ans=0;
		int currGrid[][][]=Constants.cummulativeGridList.get(day).cGrid[color];

		long var1 =  xMin>0?currGrid[xMin-1][yMax][zMax]:0;
		long var2 =  yMin>0?currGrid[xMax][yMin-1][zMax]:0;
		long var3 =  zMin>0?currGrid[xMax][yMax][zMin-1]:0;
		long var4 = (xMin>0 && yMin>0)?currGrid[xMin-1][yMin-1][zMax]:0;
		long var5 = (xMin>0 && zMin>0)? currGrid[xMin-1][yMax][zMin-1]:0 ;
		long var6 = (yMin>0 && zMin>0)?currGrid[xMax][yMin-1][zMin-1]:0 ;
		long var7 = (yMin>0 && zMin>0 && xMin>0)?currGrid[xMin-1][yMin-1][zMin-1]:0;

		long count=currGrid[xMax][yMax][zMax]- var1-var2-var3 +var4 + var5 + var6- var7;
		return count;


	}



//	public int pointsInCylinder(int grid[][][], int x, int y, int hight_minimum, int height_maximum, int r) {
//		// todo : find an efficient algorithm if available
//		int i, j, k;
//		int xMin = Math.max(0, x - r);
//		int xMax = Math.min(a - 1, x + r);
//		int yMin = Math.max(0, y - r);
//		int yMax = Math.min(b - 1, y + r);
//		int zMin = hight_minimum;
//		int zMax = Math.min(hight_minimum + height_maximum, c - 1);
//		int count = 0;
//		for (i = xMin; i <= xMax; i++) {
//			for (j = yMin; j <= yMax; j++) {
//				for (k = zMin; k <= zMax; k++) {
//					if ((i - x) * (i - x) + (j - y) * (j - y) <= r * r) {
//						count += grid[i][j][k];
//					}
//				}
//			}
//		}
//		return count;
//	}

	synchronized  Double llr(int insidePoints, int ht, int r, int totalPoints,int dayCount) { // Log likelihood ratio
		Double ret = 0.0;
		int outsidePoints = totalPoints - insidePoints;
		float cylinderVolume = 3.14f * r * r * ht * dayCount;
		int expectedPointsIn = Math.round((float) totalPoints / (a * b * c) * cylinderVolume);

		int expectedPointsOut = totalPoints - expectedPointsIn;

		if (insidePoints <= expectedPointsIn) {
			return 0.0;
		}
		if (expectedPointsIn == 0) {
			return (double) 1;
		}
		ret += insidePoints * Math.log((double) insidePoints / (double) expectedPointsIn);
		ret += (outsidePoints) * Math.log((double) outsidePoints / (double) expectedPointsOut);

		// System.out.printf("inside: %d, insideexpected: %d, outside: %d,
		// outsideExpected: %d\n", insidePoints,
		// expectedPointsIn, outsidePoints, expectedPointsOut);

		return ret;
	}
	// leave this part


	/*
    ArrayList<Patterns> mergeCylinders(ArrayList<Patterns> candidatePatterns) {
        ArrayList<Patterns> mergedPatterns = new ArrayList<Patterns>(); // todo make this linked list

        while (candidatePatterns.size() > 0) {
            Patterns parentPattern = candidatePatterns.get(candidatePatterns.size() - 1);

            for (int i = candidatePatterns.size() - 2; i >= 0; i--) {

                Patterns currPattern = candidatePatterns.get(i);
                boolean eliminateByCenter = ((currPattern.center.x - parentPattern.center.x)
	 * (currPattern.center.x - parentPattern.center.x))
                        + ((currPattern.center.y - parentPattern.center.y)
	 * (currPattern.center.y - parentPattern.center.y)) < ((currPattern.radius
                                        + parentPattern.radius) * (currPattern.radius + parentPattern.radius));

                boolean eliminateByHeight = false;

                if (currPattern.center.z < parentPattern.center.z) {
                    eliminateByHeight = (parentPattern.center.z <= currPattern.center.z + currPattern.height);
                } else {
                    eliminateByHeight = (currPattern.center.z <= parentPattern.center.z + parentPattern.height);
                }

                if (eliminateByCenter && eliminateByHeight) {
                    // delete the currPattern
                    candidatePatterns.remove(i);
                }
            }
            mergedPatterns.add(parentPattern);
            candidatePatterns.remove(candidatePatterns.size() - 1);
        }
        return mergedPatterns;
    }
	 */
	public synchronized void addToList(int color, Point p, HashMap<Integer, ArrayList<Point>> colorPoint) {
		ArrayList<Point> pointList = colorPoint.get(color);

		// if list does not exist create it
		if (pointList == null) {
			pointList = new ArrayList<Point>();
			pointList.add(p);
			colorPoint.put(color, pointList);
		} else {
			// add if item is not already in list
			pointList.add(p);
		}
	}
}