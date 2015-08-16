package com.talentboost.vmware.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.ReturnMessage;
import com.talentboost.vmware.interfaces.ICommand;

/**
 * This class implements ICommand interface and provide main logic of command
 * "start" in ESXSimulator. The main goal of this class is to provide ability
 * when the ESXSimulator is started, all saved virtual machine to be build. Each
 * virtual machine which is in the file system will be created (build).
 * 
 * @author KSamardzhiev
 *
 */
public class StartESXSimulatorCmd implements ICommand {

	/**
	 * String variable that stores the name of the command.
	 */
	private final String NAME = "start";
	/**
	 * String variable that stores information about the command.
	 */
	private final String INFO = ReturnMessage.getMessage("MSG_START_ESX_INFO");

	/**
	 * @return String name of command "start".
	 */
	@Override
	public String getName() {
		return this.NAME;
	}

	/**
	 * @param args
	 *            String arguments after the command start. In this case args
	 *            param is not used because after command "start" there are no
	 *            arguments.
	 * @return String result of execution of command "start".
	 */
	@Override
	public String execute(String args) {

		String workingDirectory = System.getProperty("user.dir");
		String savedDirectory = "savedVMs";

		try {
			Files.walk(Paths.get(workingDirectory + "/" + savedDirectory)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {

					FileInputStream stream;
					try {
						stream = new FileInputStream(filePath.toFile());
						ESXSimulator.processInput(stream);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
	}

	/**
	 * @return String information about command "stop".
	 */
	@Override
	public String info() {
		return this.INFO;
	}

}
