import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/*
 *  SimpleReflex class
 *  
 *  The class has percept2action() method that can take in the percept
 *  and return an action
 * 	
 */


public class SimpleReflex {
	// Return an action based on the percept sensed by the agent
	int percept2action(boolean bump, boolean glitter, 
			boolean breeze, boolean stench, boolean scream) {
		return this.getAction(this.actionCaculator(bump, glitter, breeze, stench, scream));
	}
	
	// Return an action based on the probability of each action
	private int getAction(double[] probs) {
		// create the range of value for each action(six ranges in total)
		double[] partitions = new double[] {0,0,0,0,0,0,0};
		double sum = 0;
		for(int i = 0; i < probs.length; i++) {
			sum += probs[i];
			partitions[i+1] = sum;
		}
		
		// generate a random number and check which range of values it falls in
		double randomNumber = Math.random();
		for(int i = 0; i < partitions.length - 1; i++) {
			if(randomNumber > partitions[i] && randomNumber < partitions[i+1]) {
				return i+1;
			}
		}
		
		return probs.length - 1; // return NO_OP if there is no action is selected
	}
	
	// Add values to each action in an array of actions
	// (forward, right, left, grab, shoot, no_op)
	// based on the percept that the agent senses
	// then normalize the array to get the probabilities for the actions
	private double[] actionCaculator(boolean bump, boolean glitter, 
			boolean breeze, boolean stench, boolean scream) {
		double[] actionArray = new double[]{8,1,1,0,0,0};
		if(bump == true) {
			// agent senses bump, so it might go left or right or no action
			actionArray = this.add(actionArray, new double[] {0,8000,4000,0,0,1000});
		}
		if(scream == true) {
			// agent senses scream, so it might go forward
			actionArray = this.add(actionArray, new double[] {10,0,0,0,0,0});
		}
		if(stench == true) {
			// agent senses stench, increase the probability that the agent 
			// will turn left, right, shoot and no action,
			// suppress the probability that the agent will go forward
			actionArray = this.add(actionArray, new double[] {0,8000,4000,0,2000,1000});
		}
		if(breeze == true) {
			// agent senses breeze, increase the probability that the agent 
			// will turn left, rightand no action
			// suppress the probability that the agent will go forward
			actionArray = this.add(actionArray, new double[] {0,8000,4000,0,0,1000});
		}
		
		return this.normalize(actionArray);
	}
	
	// add array a to b element wise
	private double[] add(double[] a, double[] b) {
		double[] sum = new double[a.length];
		for(int i = 0; i < a.length; i++) {
			sum[i] = a[i] + b[i];
		}
		return sum;
	}
	
	// divide each elements in the array to their sum
	private double[] normalize(double[] arr) {
		double sum = Arrays.stream(arr).sum();
		for(int i = 0; i < arr.length; i++) {
			arr[i] = arr[i]/sum;
		}
		
		return arr;
	}
}
