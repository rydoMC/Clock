package Clock;


/**
 * This class simply takes a hand size and gives back the corresponding coordinates.
 *
 * @author Ryan McNulty
 * @project Graphics
 * @package Clock
 * @date Aug 17, 2012
 */
public class Coordinates {

	public int[][] coordinates(HandSize s){
		int[][] coor;
		
		switch(s){
			case StopWatch: 
				coor = new int[4][1];
				coor[0] = new int[] {497,250};
				coor[1] = new int[] {497,380};
				coor[2] = new int[] {503,380};
				coor[3] = new int[] {503,250};

				break;
			
			case StopWatchSmall:				
				coor = new int[4][1];
				coor[0] = new int[] {497,250};
				coor[1] = new int[] {497,360};
				coor[2] = new int[] {503,360};
				coor[3] = new int[] {503,250}; ;
			
				break;
				
			case BigHand:
				coor = new int[7][1];
				coor[0] = new int[] {495,498};
				coor[1] = new int[] {495,920};
				coor[2] = new int[] {485,920};
				coor[3] = new int[] {500,950}; 
				coor[4] = new int[] {520,920}; 
				coor[5] = new int[] {507,920}; 
				coor[6] = new int[] {507,498}; 
			
				break;
			
			// Small hand
			default:
				coor = new int[4][1];
				coor[0] = new int[] {492,500};
				coor[1] = new int[] {492,800};
				coor[2] = new int[] {508,800};
				coor[3] = new int[] {508,500}; 
				
				break;
		}
		
		return coor;
	}
	
	
	public int[][] coordinates(IntervalSize s){
		int[][] coor;
		
		switch(s){
			case Big: 
				coor = new int[4][1];
				coor[0] = new int[] {490,1000};
				coor[1] = new int[] {490,980};
				coor[2] = new int[] {510,980};
				coor[3] = new int[] {510,1000};

				break;
			
			case Normal:	
				coor = new int[10][1];
				coor[0] = new int[] {498, 998};
				coor[1] = new int[] {499, 999};
				coor[2] = new int[] {500, 1000};
				coor[3] = new int[] {501, 1000}; 
				coor[4] = new int[] {502, 999}; 
				coor[5] = new int[] {503, 998}; 
				coor[6] = new int[] {502, 997}; 
				coor[7] = new int[] {501, 996}; 
				coor[8] = new int[] {500, 996}; 
				coor[9] = new int[] {499, 997}; 
				
				break;
				
			// Mini
			default:
				coor = new int[4][1];
				coor[0] = new int[] {498,400};
				coor[1] = new int[] {498,380};
				coor[2] = new int[] {502,380};
				coor[3] = new int[] {502,400}; 
				
				break;
		}
		
		return coor;
	}
}
