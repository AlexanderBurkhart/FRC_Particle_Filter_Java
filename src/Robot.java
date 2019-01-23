import java.util.ArrayList;
import java.util.Random;

public class Robot {
	
	Field _field;
	int _worldSize;
	double _posX, _posY, _orientation;
	double _forwardNoise, _turnNoise, _senseNoise;
	int[][] _landmarks;
	double[][] _deadzones;
	Random rand = new Random();
	
	public Robot(double x, double y)
	{
		_field = new Field(100);
		_deadzones = _field.getDeadzones();
		_worldSize = _field.getSize();
		_posX = x;
		_posY = y;
		_orientation = Math.random() * 2.0 * Math.PI;
		_landmarks = _field.getLandmarks();
		_forwardNoise = 0.0;
		_turnNoise = 0.0;
		_senseNoise = 0.0;
	}
	
	public Robot(boolean randomPos)
	{
		this(-1, -1);
		if(randomPos)
		{
			_posX = Math.random() * _worldSize;
			_posY = Math.random() * _worldSize;
		}
	}

	public Robot()
	{
		_field = new Field(100);
		_deadzones = _field.getDeadzones();
		_worldSize = _field.getSize();
		_landmarks = _field.getLandmarks();
		_posX = 0;
		_posY = 0;
		_orientation = 0;
		_forwardNoise = 0.0;
		_turnNoise = 0.0;
		_senseNoise = 0.0;
	}
	
	public Robot move(double fwd, double heading)
	{
		_orientation += heading + rand.nextGaussian()*_turnNoise;
		_orientation %= 2*Math.PI;
		
		double xFwd = Math.sin(_orientation)*fwd + rand.nextGaussian()*_forwardNoise;
		double yFwd = Math.cos(_orientation)*fwd + rand.nextGaussian()*_forwardNoise;
		
		_posX += xFwd;
		_posY += yFwd;
		_posX = PositiveMod(_posX, _worldSize);
		_posY = PositiveMod(_posY, _worldSize);
		
		Robot rob = new Robot();
		rob.set(_posX, _posY, _orientation);
		rob.setNoise(_forwardNoise, _turnNoise, _senseNoise);
		return rob;
	}
	
	private double PositiveMod(double value, double mod)
	{
	    return (value % mod + mod) % mod;
	}
	
	public void set(double x, double y, double orientation)
	{
		_posX = x;
		_posY = y;
		_orientation = orientation;
	}
	
	public void setNoise(double forwardNoise, double turnNoise, double senseNoise)
	{
		_forwardNoise = forwardNoise;
		_turnNoise = turnNoise;
		_senseNoise = senseNoise;
	}
	
	public double[] getPos()
	{
		return new double[] {_posX, _posY};
	}
	
	public ArrayList<Double> sense()
	{
		ArrayList<Double> dists = new ArrayList<Double>();
		for(int i = 0; i < _landmarks.length; i++)
		{
			if(canSense(_landmarks[i]))
			{
				double dist = Math.sqrt(Math.pow(_posX-_landmarks[i][0], 2) + Math.pow(_posY-_landmarks[i][1], 2));
				dist += rand.nextGaussian()*_senseNoise;
				dists.add(dist);
			}
			else
			{
				dists.add(-1.0);
			}
		}
		return dists;
	}
	
	private boolean canSense(int[] landmark)
	{
		double[] bl = _deadzones[2];
		double[] tr = _deadzones[1];
		return !isColliding(landmark[0], landmark[1], _posX, _posY, tr[0], tr[1], bl[0], bl[1]);
	}
	
	private boolean isColliding(double x1, double y1, double x2, double y2, double rx1, double ry1, double rx2, double ry2)
	{
		boolean left, right, top, bottom;
		left = lineLine(x1, y1, x2, y2, rx1, ry2, rx1, ry1);
		right = lineLine(x1, y1, x2, y2, rx2, ry2, rx2, ry1);
		top = lineLine(x1, y1, x2, y2 , rx2, ry2, rx1, ry2);
		bottom = lineLine(x1, y1, x2, y2, rx2, ry1, rx1, ry1);
			
		if(left || right || top || bottom)
			return true;
		return false;
	}
	
	private boolean lineLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4)
	{
		double uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
		double uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

		if(uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) 
			return true;
		return false;
	}
	
	private double gaussian(double mu, double sigma, double x)
	{
		return Math.exp(-Math.pow(mu-x, 2) / Math.pow(sigma, 2)/2.0) / Math.sqrt(2.0 * Math.PI * Math.pow(sigma, 2));
	}
	
	public double measurement_prob(ArrayList<Double> measurement)
	{
		double prob = 1.0;
		for(int i = 0; i < _landmarks.length; i++)
		{
			if(measurement.get(i) == -1)
				continue;
			int[] landmark = _landmarks[i];
			double dist = Math.sqrt(Math.pow(_posX - landmark[0], 2) + Math.pow(_posY - landmark[1], 2));
			prob *= gaussian(dist, _senseNoise, measurement.get(i));
		}
		return prob;
	}
}
