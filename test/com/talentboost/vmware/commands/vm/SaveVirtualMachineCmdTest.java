package com.talentboost.vmware.commands.vm;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;

public class SaveVirtualMachineCmdTest {

	SaveVirtualMachineCmd save;
	@Before
	public void setUp() throws Exception {
		save = new SaveVirtualMachineCmd();
	}

	@Test
	public void testExecuteSavingVirtualMachine() {
		VirtualMachine vm = new VirtualMachine("id2", "testSaveVM", 1024, (byte) 4);
		ESXSimulator.VMsStorage.put(vm.getID(), vm);
		save.execute(vm.getID());
		String fileName = String.format("%s.txt", vm.getID());
		String workingDirectory = System.getProperty("user.dir");
		String savedDirectory = "savedVMs";
		String absoluteFilePath = "";

		absoluteFilePath = workingDirectory + File.separator + savedDirectory
				+ File.separator + fileName;
		
		File savedFile = new File(absoluteFilePath);
		assertTrue(savedFile.exists());
		savedFile.delete();
		
	}

}
