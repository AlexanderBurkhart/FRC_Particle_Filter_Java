import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Robot robot = new Robot(0, 20);
		robot.setNoise(1.0, 0.1, 1.0);
		
		int T = 100;
		int N = 1000;
		ArrayList<Robot> p = new ArrayList<Robot>();
		
		for(int i = 0; i < N; i++)
		{
			Robot x = new Robot(true);
			x.setNoise(0.05, 0.05, 5.0);
			p.add(x);
		}
		
		for(int t = 0; t < T; t++)
		{
			//System.out.print("_posX(" + robot._posX + ") % _worldSize(" + robot._worldSize + ")= ");
			robot = robot.move(2, 0);
			//System.out.println(robot._posX);
			ArrayList<Double> dists = robot.sense();
			
			ArrayList<Robot> p2 = new ArrayList<Robot>();
			for(int i = 0; i < N; i++)
			{
				p2.add(p.get(i).move(2,0));
			}
			p = p2;
			
			ArrayList<Double> w = new ArrayList<Double>();
			for(int i = 0; i < N; i++)
			{
				w.add(p.get(i).measurement_prob(dists));
			}
			
			ArrayList<Robot> p3 = new ArrayList<Robot>();
			int index = (int)(Math.random()*N);
			double beta = 0.0;
			double mw = max(w);
			for(int i = 0; i < N; i++)
			{
				beta += Math.random() * 2.0 * mw;
				while(beta > w.get(index))
				{
					beta -= w.get(index);
					index = (index+1) % N;
				}
				p3.add(p.get(index));
			}
			p = p3;
			
			int num_p = p.size();
			Robot best_p = new Robot();
			double highest_weight = -1.0;
			
			for(int i = 0; i < num_p; i++)
			{
				double p_weight = p.get(i).measurement_prob(dists);
				if(p_weight > highest_weight)
				{
					highest_weight = p_weight;
					best_p = p.get(i);
				}
			}
			System.out.printf("Actual Pos:[X: %f Y: %f] Particle Pos:[X: %f Y: %f]", robot._posX, robot._posY, best_p.getPos()[0], best_p.getPos()[1]);
			System.out.println();
		}
	}
	
	public static double max(ArrayList<Double> w)
	{
		double max = -1;
		for(double weight : w)
		{
			if(max < weight)
				max = weight;
		}
		return max;
	}

}
