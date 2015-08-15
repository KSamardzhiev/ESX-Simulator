package com.talentboost.vmware;

/**
 * This class is responsible for returning ESXSimulator class instance.
 * 
 * @author KSamardzhiev
 *
 */
public class ESXSimulatorFactory {
	/**
	 * 
	 * @return ESXSimulator instance
	 */
	public ESXSimulator getESX() {
		ESXSimulator simulator = new ESXSimulator();
		ESXSimulator.broker.executeCommand("start", null);
		return simulator;
	}
}
