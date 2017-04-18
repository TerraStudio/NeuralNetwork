package de.Marcel.NeuralNetwork;

import java.util.ArrayList;

public class NeuralNetwork {
	private ArrayList<Neuron> inputLayer, hiddenLayer, outputLayer;
	private ArrayList<Synapse> ihSynpases, hoSynapses;

	private float learnParameter, momentum;

	public NeuralNetwork(int inputSize, int hiddenSize, int outputSize, float learnParameter, float momentum) {
		// create layers
		createLayers(inputSize, hiddenSize, outputSize);

		// connect layers
		connectLayers();

		// apply learn values
		this.learnParameter = learnParameter;
		this.momentum = momentum;
	}

	// feedforward data
	public void feedForward(int[] input) throws NeuralNetworkException {
		// determine wether input is as big as inputlayer
		if (inputLayer.size() == input.length) {
			// set input as input and output of input neurons
			for (int i = 0; i < input.length; i++) {
				this.inputLayer.get(i).setInput(input[i]);
				this.inputLayer.get(i).setOutput(input[i]);
			}

			// propagate to hiddenLayer via ihSynapses
			for (Synapse synapse : ihSynpases) {
				// add output of from neuron and add it to input of to neuron,
				// multiplied by the weight between
				synapse.getTo()
						.setInput(synapse.getTo().getInput() + (synapse.getFrom().getOutput() * synapse.getWeight()));
			}

			// calculate output of hiddenNeurons
			for (Neuron n : hiddenLayer) {
				// calculate output by applying sigmoid to the neurons input
				n.setOutput(sigmoid(n.getInput()));
				n.setInput(0);
			}

			// propagate to outputLayer via hoSynapses
			for (Synapse synapse : hoSynapses) {
				// add output of from neuron and add it to input of to neuron,
				// multiplied by the weight between
				synapse.getTo()
						.setInput(synapse.getTo().getInput() + (synapse.getFrom().getOutput() * synapse.getWeight()));
			}

			// calculate output of hiddenNeurons
			for (Neuron n : outputLayer) {
				// calculate output by applying sigmoid to the neurons input
				n.setOutput(sigmoid(n.getInput()));
				n.setInput(0);
			}
		} else {
			throw new NeuralNetworkException("Input data does not have the right size");
		}
	}

	// backpropagate data / returns global error
	public float backpropagate(int[] input, int[] output) throws NeuralNetworkException {
		float globalError = 0;
		
		// determine wether output and input arrays are big enough
		if (input.length == inputLayer.size() && output.length == outputLayer.size()) {
			// perform feedforward with input data to calculate network output
			feedForward(input);

			// calculate error for each output neuron
			for (int i = 0; i < output.length; i++) {
				Neuron n = outputLayer.get(i);

				float errorSignal = (output[i] - n.getOutput()) * n.getOutput() * (1 - n.getOutput());

				n.setError(errorSignal);
			}

			// calculate error for each hidden neuron
			for (int i = 0; i < hiddenLayer.size(); i++) {
				Neuron n = hiddenLayer.get(i);

				// determine sum of output of next neurons, multiplied with the
				// weight between
				float sum = 0;
				for (Synapse s : hoSynapses) {
					sum += s.getWeight() * s.getTo().getOutput();
				}

				float errorSignal = n.getOutput() * (1 - n.getOutput()) * sum;
				n.setError(errorSignal);
			}

			// change weight
			// between hidden and output layer
			for (Synapse s : hoSynapses) {
				s.setWeight(s.getWeight() + learnParameter * s.getTo().getError() * s.getFrom().getOutput() + s.getPreviousDeltaWeight() * momentum);
				s.setPreviousDeltaWeight(learnParameter * s.getTo().getError() * s.getFrom().getOutput() + s.getPreviousDeltaWeight() * momentum);
			}

			// between input and hidden layer
			for (Synapse s : ihSynpases) {
				s.setWeight(s.getWeight() + learnParameter * s.getTo().getError() * s.getFrom().getOutput() + s.getPreviousDeltaWeight() * momentum);
				s.setPreviousDeltaWeight(learnParameter * s.getTo().getError() * s.getFrom().getOutput() + s.getPreviousDeltaWeight() * momentum);
			}
			
			//calculate global error
			for (int i = 0; i < output.length; i++) {
				Neuron n = outputLayer.get(i);
				
				globalError += Math.pow(output[i] - n.getOutput(), 2);
			}
			
			return (float) (0.5 * globalError);
		} else {
			throw new NeuralNetworkException("Input / Output data does not have the right size");
		}
	}

	// create different layers of neural network
	private void createLayers(int inputSize, int hiddenSize, int outputSize) {
		// create inputLayer
		this.inputLayer = new ArrayList<Neuron>();

		for (int i = 0; i < inputSize; i++) {
			this.inputLayer.add(new Neuron());
		}

		// create hiddenLayer
		this.hiddenLayer = new ArrayList<Neuron>();

		for (int i = 0; i < hiddenSize; i++) {
			this.hiddenLayer.add(new Neuron());
		}

		// create outputLayer
		this.outputLayer = new ArrayList<Neuron>();

		for (int i = 0; i < outputSize; i++) {
			this.outputLayer.add(new Neuron());
		}
	}

	// connect all neurons of layers
	private void connectLayers() {
		// create synapses
		this.ihSynpases = new ArrayList<>();
		this.hoSynapses = new ArrayList<>();

		// connect input with hidden layer (ihSynpases)
		for (Neuron hNeuron : hiddenLayer) {
			for (Neuron iNeuron : inputLayer) {
				// create synapse
				this.ihSynpases.add(new Synapse(iNeuron, hNeuron));
			}
		}

		// connect hidden with output layer (hoSynpases)
		for (Neuron oNeuron : outputLayer) {
			for (Neuron hNeuron : hiddenLayer) {
				// create synapse
				this.hoSynapses.add(new Synapse(hNeuron, oNeuron));
			}
		}
	}

	// apply sigmoid function to input value
	private float sigmoid(float input) {
		return (float) (1 / (1 + Math.exp(-input)));
	}

	// NeuralNetworkException
	public class NeuralNetworkException extends Exception {
		private static final long serialVersionUID = 1L;

		public NeuralNetworkException(String exception) {
			super(exception);
		}
	}
}
