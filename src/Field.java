
public class Field {
	
	public int fieldSize;
	public double[][] dzSquare;
	public int[][] landmarks;
	
	public Field(int _fieldSize)
	{
		fieldSize = _fieldSize;
		double[] dz_tl = {40, 60};
		double[] dz_tr = {60, 60};
		double[] dz_bl = {40, 40};
		double[] dz_br = {60, 40};
		dzSquare = new double[][] {dz_tl, dz_tr,dz_bl, dz_br};
		
		landmarks = new int[][] {{0, 0}, {50, 39}, {60, 39}, 
								{65, 0}, {0, 40}, {99, 99}, 
								{0,99}, {99,0}, {40, 61}, 
								{50, 61}, {50, 99}};
		
	}
	
	public double[][] getDeadzones()
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
