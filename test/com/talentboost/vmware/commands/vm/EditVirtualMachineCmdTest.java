package com.talentboost.vmware.commands.vm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.commands.vm.EditVirtualMachineCmd;

public class EditVirtualMachineCmdTest {

	EditVirtualMachineCmd edit;
	VirtualMachine vm = new VirtualMachine("id1", "testVM", 4096, (byte) 4);
	VirtualMachine vm1 = new VirtualMachine("id2", "testVM2", 1024, (byte) 4);
	VirtualMachine vm2 = new VirtualMachine("id3", "testVM3", 120000, (byte) 4);

	@Before
	public void setUp() throws Exception {
		edit = new EditVirtualMachineCmd();
		ESXSimulator.VMsStorage.put(vm.getID(), vm);
		ESXSimulator.VMsStorage.put(vm1.getID(), vm1);
		ESXSimulator.VMsStorage.put(vm2.getID(), vm2);

	}

	@Test
	public void testExecutionValidInput() {
		edit.execute("id1 'TESTVM' 1024 4");
		assertEquals("TESTVM", ESXSimulator.VMsStorage.get("id1").getName());
		assertEquals(1024, ESXSimulator.VMsStorage.get("id1").getMemory());
	}

	@Test
	public void testExecutionValidInputMessage() {
		String result = edit.execute("id1 'TESTVM' 1024 4");
		assertEquals("Virtual machine is edited.", result);
	}

	@Test
	public void testExecutionInvalidInputName() {
		edit.execute("id1 'TEST@VM' 1024 4");
		assertEquals("testVM", ESXSimulator.VMsStorage.get("id1").getName());
		assertEquals(4096, ESXSimulator.VMsStorage.get("id1").getMemory());
	}

	@Test
	public void testExecutionInvalidInputMemory() {
		edit.execute("id1 'TESTVM' -1024 4");
		assertEquals("testVM", ESXSimulator.VMsStorage.get("id1").getName());
		assertEquals(4096, ESXSimulator.VMsStorage.get("id1").getMemory());
	}
	
	@Test
	public void testExecutionInvalidInputCPUs() {
		edit.execute("id1 'testVM' 4096 -12");
		assertEquals("testVM", ESXSimulator.VMsStorage.get("id1").getName());
		assertEquals(4096, ESXSimulator.VMsStorage.get("id1").getMemory());
		assertEquals(4, ESXSimulator.VMsStorage.get("id1").getCPUs());
	}


}
