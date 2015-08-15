package com.talentboost.vmware.commands;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.commands.vm.SaveVirtualMachineCmd;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class implements ICommand interface and provide main logic of command
 * "stop" in ESXSimulator. The main goal of this class is to provide ability to
 * stop the ESXSimulator and to save it state. So if stop command is invoked the
 * state of ESXSimulator should be save. Each virtual machine which is in the
 * map whit created virtual machines have to be saved in file.
 * 
 * @author KSamardzhiev
 *
 */
public class StopESXSimulatorCmd implements ICommand {
	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "stop";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = "stop - This command provide ability to stop the ESXSimulator and save its state in files (save available virtual machines).";

	/**
	 * @return String name of command "stop".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * @param args
	 *            String arguments after the command stop. In this case args
	 *            param is not used because after command "stop" there are no
	 *            arguments.
	 * @return String result of execution of command "stop".
	 */
	@Override
	public String execute(String args) {

		// implement exit of simulator and save everything && read everything at
		// start
		SaveVirtualMachineCmd saving = new SaveVirtualMachineCmd();

		for (VirtualMachine vm : ESXSimulator.VMsStorage.values()) {
			String result = saving.execute(vm.getID());
			if (!result.equals("The virtual machine is saved.")) {
				return result;
			}
		}
		return "All virtual machines are saved.";
	}

	/**
	 * @return String information about command "stop".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
