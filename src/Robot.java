
public class Robot {
	
	Field field;
	int worldSize;
	double x, y, orientation;
	double forwardNoise, turnNoise, senseNoise;
	int[][] landmarks;
	int[][] deadzones;
	
	public Robot(int _x, int _y, boolean randomPos)
	{
		field = new Field(100);
		deadzones = field.getDeadzones();
		worldSize = field.getSize();
		x = _x;
		y = _y;
		if(randomPos)
		{
			x = Math.random() * worldSize;
			y = Math.random() * worldSize;
		}
		orientation = Math.random() * 2.0 * Math.PI;
		landmarks = field.getLandmarks();
		forwardNoise = 0.0;
		turnNoise = 0.0;
		senseNoise = 0.0;
	}
}
