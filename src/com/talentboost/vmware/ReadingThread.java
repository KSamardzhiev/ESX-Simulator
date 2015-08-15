package com.talentboost.vmware;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class implements Runnable interface and provide logic for reading some
 * file in particular thread.
 * 
 * @author KSamardzhiev
 *
 */
public class ReadingThread implements Runnable {

	private String args = null;

	public ReadingThread(String args) {
		this.args = args;
	}

	@Override
	public void run() {
		String fileName = String.format("%s.txt", this.args);
		String workingDirectory = System.getProperty("user.dir");
		String savedDirectory = "savedVMs";
		String absoluteFilePath = "";

		absoluteFilePath = workingDirectory + File.separator + savedDirectory + File.separator + fileName;

		File file = new File(absoluteFilePath);
		try {
			FileInputStream stream = new FileInputStream(file);
			if (ESXSimulator.VMsStorage.containsKey(args)) {
				ESXSimulator.broker.executeCommand("delete-vm", this.args);
			}
			ESXSimulator.processInput(stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
