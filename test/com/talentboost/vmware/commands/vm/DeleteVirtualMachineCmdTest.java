package com.talentboost.vmware.commands.vm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.commands.vm.DeleteVirtualMachineCmd;

public class DeleteVirtualMachineCmdTest {

	DeleteVirtualMachineCmd delete;
	VirtualMachine vm = new VirtualMachine("id2", "testVM", 4096,(byte)4);
	VirtualMachine vm1 = new VirtualMachine("id12", "testVM1", 1024,(byte)4);
	VirtualMachine vm2 = new VirtualMachine("id225", "testVM2", 120000,(byte)4);

	@Before
	public void setUp() throws Exception {

		delete = new DeleteVirtualMachineCmd();
		ESXSimulator.VMsStorage.clear();
		ESXSimulator.VMsStorage.put(vm.getID(), vm);
		ESXSimulator.VMsStorage.put(vm1.getID(), vm1);

	}

	@Test
	public void testExecution() {
		assertEquals(2, ESXSimulator.VMsStorage.size());
		delete.execute(vm.getID());
		assertEquals(1, ESXSimulator.VMsStorage.size());
		assertEquals(false, ESXSimulator.VMsStorage.containsKey(vm.getID()));
	}

	@Test
	public void testExecutionNegative() {
		String result = delete.execute(vm2.getID());
		assertEquals(
				"There is no virtual machine with this ID.", result);
	}

	@Test
	public void testExecutionReturnResult() {

		String result = delete.execute(vm.getID());
		assertEquals("Virtual machine is deleted.",
				result);
	}

	@Test
	public void testExecutionDeleteFileToo() {

		String result = delete.execute(vm.getID());
		assertEquals("Virtual machine is deleted.",
				result);
	}
}
