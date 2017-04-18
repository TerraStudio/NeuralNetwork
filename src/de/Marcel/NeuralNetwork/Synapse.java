package de.Marcel.NeuralNetwork;

public class Synapse {
	private Neuron from, to;
	private float weight, previousDeltaWeight;
	
	public Synapse (Neuron from, Neuron to) {
		this.from = from;
		this.to = to;
		
		//set random weight between -1 and 1
		this.weight = (float) (Math.random() * 2 - 1);
		this.previousDeltaWeight = 0;
	}
	
	//GETTER
	public Neuron getFrom() {
		return from;
	}
	
	public Neuron getTo() {
		return to;
	}
	
	public float getPreviousDeltaWeight() {
		return previousDeltaWeight;
	}
	
	public float getWeight() {
		return weight;
	}
	
	//SETTER
	public void setFrom(Neuron from) {
		this.from = from;
	}
	
	public void setTo(Neuron to) {
		this.to = to;
	}
	
	public void setPreviousDeltaWeight(float previousDeltaWeight) {
		this.previousDeltaWeight = previousDeltaWeight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
}
