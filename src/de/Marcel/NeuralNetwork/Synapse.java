package de.Marcel.NeuralNetwork;

public class Synapse {
	private Neuron from, to;
	private float weight, previousWeight;
	
	public Synapse (Neuron from, Neuron to) {
		this.from = from;
		this.to = to;
		
		//set random weight between -1 and 1
		this.weight = (float) (Math.random() * 2 - 1);
		this.previousWeight = 0;
	}
	
	//GETTER
	public Neuron getFrom() {
		return from;
	}
	
	public Neuron getTo() {
		return to;
	}
	
	public float getPreviousWeight() {
		return previousWeight;
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
	
	public void setPreviousWeight(float previousWeight) {
		this.previousWeight = previousWeight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
}
