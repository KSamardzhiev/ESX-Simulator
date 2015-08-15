package com.talentboost.vmware.interfaces;

/**
 * This Interface provide the abilities of each class that implements it. It is
 * implemented by CreateVirtualMachineCmd class, DeleteVirtualMachineCmd class,
 * EditVirtualMachineCmd class, PrintVirtualMachineCmd class, AddDeviceCmd
 * class, DeleteDeviceCmd class and HelpCmd class.
 * 
 * @author KSamardzhiev
 *
 */
public interface ICommand {

	final String PATTERN_WITH_NAME_ARG = "\\s+'|'\\s+";
	final String PATTERN_WITHOUT_NAME_ARG = "\\s+";

	/**
	 * 
	 * @return String - the name of particular command (e.g create-vm,
	 *         delete-vm, edit-vm, print-vms, add-dev, delete-dev, help).
	 */
	public String getName();

	/**
	 * 
	 * @param args
	 *            String - arguments after particular command.
	 * @return String - the result of the execution.
	 */
	public String execute(String args);

	/**
	 * 
	 * @return String - information about particular command (short summary).
	 */
	public String info();
}
