package de.Marcel.NeuralNetwork;

public class Neuron {
	private float input, output, error;
	
	public Neuron () {
		this.input = 0;
		this.output = 0;
		this.error = 0;
	}
	
	//GETTER
	public float getInput() {
		return input;
	}
	
	public float getOutput() {
		return output;
	}
	
	public float getError() {
		return error;
	}
	
	//SETTER
	public void setInput(float input) {
		this.input = input;
	}
	
	public void setOutput(float output) {
		this.output = output;
	}
	
	public void setError(float error) {
		this.error = error;
	}
}
