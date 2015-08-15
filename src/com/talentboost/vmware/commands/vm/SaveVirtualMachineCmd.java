package com.talentboost.vmware.commands.vm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.devices.HardDiskDevice;
import com.talentboost.vmware.interfaces.ICommand;
import com.talentboost.vmware.interfaces.IDevice;
import com.talentboost.vmware.interfaces.IHDController;

/**
 * This class provide logic of command save-vm and implements ICommand
 * interface. It provide ability to save particular virtual machine.
 *
 * @author KSamardzhiev
 */
public class SaveVirtualMachineCmd implements ICommand {

	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "save-vm";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = "save-vm - This command provide ability to save virtual machine in the file system\n\n";

	/**
	 * @return String name of command "save-vm".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * This method invoke authorizeInput method where is the logic of execution.
	 * The main goal is to save particular virtual machine. All needed
	 * parameters are provided after the command.
	 * 
	 * @param args
	 *            String of arguments after the command "save-vm"
	 * @return String result of execution of the command "save-vm".
	 */
	@Override
	public String execute(String args) {
		return this.authorizeInput(args);
	}

	/**
	 * This method authorize the input and execute the command in case of
	 * everything with the input is correct. Otherwise it return concrete String
	 * with the problem. In case the execution is successful it return String
	 * with the result.
	 * 
	 * @param args
	 *            String of arguments after the command "save-vm".
	 * @return String result of execution of the command "save-vm".
	 */
	private String authorizeInput(String args) {
		if (!ESXSimulator.VMsStorage.containsKey(args)) {
			return "There is no vm with this id!";
		}
		VirtualMachine vm = ESXSimulator.VMsStorage.get(args);

		String fileName = String.format("%s.txt", vm.getID());
		String workingDirectory = System.getProperty("user.dir");
		String savedDirectory = "savedVMs";
		String absoluteFilePath = "";

		absoluteFilePath = workingDirectory + File.separator + savedDirectory + File.separator + fileName;

		File file = new File(absoluteFilePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			return "Failed to make new file!";
		}

		try (FileWriter writer = new FileWriter(file)) {

			StringBuffer result = new StringBuffer();
			result.append(String.format("create-vm %s '%s' %s %s\n", vm.getID(), vm.getName(), vm.getMemory(),
					String.valueOf(vm.getCPUs())));
			if (!vm.getDevices().isEmpty()) {
				for (IDevice device : vm.getDevices().values()) {
					result.append(chechDeviceType(device, vm));
				}
			}

			writer.write(result.toString());
		} catch (IOException e) {
			return "Problem with writing in the file.";
		}

		return "The virtual machine is saved.";
	}

	/**
	 * This method check the type of device and parse its configuration in way
	 * that they are correct to be saved in file.
	 * 
	 * @param device
	 *            Instance of class that implements IDevice interface.
	 * @param vm
	 *            Instance of VitualMachine class.
	 * @return String result which to be added to file.
	 */
	private String chechDeviceType(IDevice device, VirtualMachine vm) {
		StringBuffer result = new StringBuffer();
		if (device.getType().equals("HardDisk_Controller")) {

			IHDController controller = (IHDController) device;
			result.append(String.format("add-dev %s %s %s %s\n", vm.getID(), "HardDisk_Controller", controller.getID(),
					controller.getControllerType()));
			for (HardDiskDevice hardDisk : controller.getHardDisks()) {
				result.append(String.format("add-dev %s %s %s %s %s\n", vm.getID(), hardDisk.getType(),
						hardDisk.getID(), hardDisk.getSize(), hardDisk.getIDController()));
			}
			return result.toString();
		}

		result.append(String.format("add-dev %s %s\n", vm.getID(), device.toString()));
		return result.toString();
	}

	/**
	 * @return String information about command "save-vm".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
