import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SimpleReflex {
	public HashMap<String, double[]> actionTable;
	SimpleReflex(){
		this.actionTable = new HashMap<>();
		this.createActionTable();
	}
	
	int percept2action(boolean bump, boolean glitter, 
			boolean breeze, boolean stench, boolean scream) {
		String percept = this.perceptBoolean2String(bump, glitter, breeze, stench, scream);
		
		return this.getAction(actionTable.get(percept));
	}
	
	private String perceptBoolean2String(boolean bump, boolean glitter, 
			boolean breeze, boolean stench, boolean scream) {
		String temp = this.boolean2binary(bump) + this.boolean2binary(glitter) + this.boolean2binary(breeze) + 
				this.boolean2binary(stench) + this.boolean2binary(scream);
		return temp;
	}
	
	private String boolean2binary(boolean b) {
		return b ? "1" : "0";
	}
	
	private int getAction(double[] probs) {

		Random rand = new Random();
		double randomNumber = Math.random() + 0.1;
		
		double sum = 0;
		for(int i = 0; i < probs.length; i++) {
			if(sum + probs[i] > randomNumber) {
				return i + 1;
			}
			sum += probs[i];
		}

		return probs.length - 1;
	}
	
	private void createActionTable() {
		// Key: {glitter, stench, scream, bump, breeze}
		// Value: {^, >, < , grab, shoot, no_op} (probability of each action)
		this.actionTable.put("00000", new double[]{0.6, 0.2, 0.2, 0.0, 0.0, 0.0});
		this.actionTable.put("00001", new double[]{0.1, 0.3, 0.3, 0, 0, 0.3});
		this.actionTable.put("00010", new double[]{0, 0.3, 0.3, 0, 0, 0.4});
		this.actionTable.put("00011", new double[]{0, 0.3, 0.3, 0, 0, 0.4});
		this.actionTable.put("00100", new double[]{0.6, 0.2, 0.2, 0, 0, 0});
		
		this.actionTable.put("00101", new double[]{0.1, 0.4, 0.5, 0, 0, 0});
		this.actionTable.put("00110", new double[]{0, 0.3, 0.3, 0, 0, 0.4});
		this.actionTable.put("00111", new double[]{0, 0.3, 0.3, 0.0, 0, 0.4});
		this.actionTable.put("01000", new double[]{0.1, 0.2, 0.3, 0.0, 0.2, 0.2});
		this.actionTable.put("01001", new double[]{0.1, 0.3, 0.3, 0, 0.2, 0.1});
		
		this.actionTable.put("01010", new double[]{0, 0.5, 0.5, 0.0, 0.0, 0});
		this.actionTable.put("01011", new double[]{0, 0.5, 0.5, 0, 0, 0});
		this.actionTable.put("01100", new double[]{0.8, 0.1, 0.1, 0.0, 0.0, 0.0});
		this.actionTable.put("01101", new double[]{0.1, 0.2, 0.2, 0, 0.1, 0.4});
		this.actionTable.put("01110", new double[]{0, 0.3, 0.3, 0, 0, 0.4});
		this.actionTable.put("01111", new double[]{0, 0.3, 0.3, 0, 0, 0.4});
	}
}
