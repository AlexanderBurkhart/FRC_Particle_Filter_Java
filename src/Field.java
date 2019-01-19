
public class Field {
	
	public int fieldSize;
	public int[][] dzSquare;
	public int[][] landmarks;
	
	public Field(int _fieldSize)
	{
		fieldSize = _fieldSize;
		int[] dz_tl = {40, 60};
		int[] dz_tr = {60, 60};
		int[] dz_bl = {40, 40};
		int[] dz_br = {60, 40};
		dzSquare = new int[][] {dz_tl, dz_tr,dz_bl, dz_br};
		
		landmarks = new int[][] {{0, 0}, {50, 39}, {60, 39}, 
								{65, 0}, {0, 40}, {99, 99}, 
								{0,99}, {99,0}, {40, 61}, 
								{50, 61}, {50, 99}};
		
	}
	
	public int[][] getDeadzones()
	{
		return dzSquare;
	}
	
	public int getSize()
	{
		return fieldSize;
	}
	
	public int[][] getLandmarks()
	{
		return landmarks;
	}
	
}
