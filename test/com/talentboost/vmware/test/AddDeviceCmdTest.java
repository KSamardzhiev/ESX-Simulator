package com.talentboost.vmware.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.VirtualMachine;
import com.talentboost.vmware.commands.devices.AddDeviceCmd;
import com.talentboost.vmware.interfaces.IHDController;

public class AddDeviceCmdTest {

	private VirtualMachine vm;
	private AddDeviceCmd addDevice;


	@Before
	public void setUp() throws Exception {
		vm = new VirtualMachine("id1", "OSX 10", 4096, (byte) 8);
		ESXSimulator.VMsStorage.put(vm.getID(), vm);
		addDevice = new AddDeviceCmd();
	}

	@Test
	public void testExecuteStringResult() {
		String result = addDevice
				.execute("id1 NETWORK_CARD net1 FF-FF-FF-FF-FF-FF");
		assertEquals("This device is added", result);

	}
	
	@Test
	public void testExecuteAddNetworkCardDevice() {
		String result = addDevice
				.execute("id1 NETWORK_CARD net1 FF-FF-FF-FF-FF-FF");
		assertEquals("This device is added", result);
	}
	
	@Test
	public void tesExecuteAddVideoCardDevice(){
		String result = addDevice
				.execute("id1 VIDEO_CARD vid1 512 2");
		assertEquals("This device is added", result);
	}

	@Test
	public void testExecuteAddingIDEHardDiskController() {
		String result = addDevice
				.execute("id1 HardDisk_Controller id12345 IDE");
		assertEquals("This device is added", result);
		IHDController hd = (IHDController) ESXSimulator.VMsStorage.get("id1")
				.getDevices().get("id12345");
		assertEquals("IDE", hd.getControllerType());
	}

	@Test
	public void testExecuteNegativeTestAddingIDEHardDiskController() {
		addDevice.execute("id1 HardDisk_Controller id12345 IDE");
		String failResult = addDevice
				.execute("id1 HardDisk_Controller id1234 IDE");
		assertEquals(
				"This virtual machine has max number of provided hard disk controllers of this type",
				failResult);
	}

	@Test
	public void testExecuteAddingSCSIHardDiskController() {
		String result = addDevice
				.execute("id1 HardDisk_Controller id12345 SCSI");
		assertEquals("This device is added", result);
		IHDController hd = (IHDController) ESXSimulator.VMsStorage.get("id1")
				.getDevices().get("id12345");

		assertEquals("SCSI", hd.getControllerType());

	}

	@Test
	public void testExecuteNegativeTestAddingSCSIHardDiskController() {
		addDevice.execute("id1 HardDisk_Controller 1id SCSI");
		addDevice.execute("id1 HardDisk_Controller 2id SCSI");
		addDevice.execute("id1 HardDisk_Controller 3id SCSI");
		addDevice.execute("id1 HardDisk_Controller 4id SCSI");
		String failResult = addDevice
				.execute("id1 HardDisk_Controller 5id SCSI");

		assertEquals(
				"This virtual machine has max number of provided hard disk controllers of this type",
				failResult);
	}

	@Test
	public void testExecuteNegativeTestAddingExistingSCSIHardDiskController() {
		addDevice.execute("id1 HardDisk_Controller 1id SCSI");
		String failResult = addDevice
				.execute("id1 HardDisk_Controller 1id SCSI");

		assertEquals(
				"This virtual machine contain device with the same ID! Try again with different device.",
				failResult);
	}

	@Test
	public void testAddingHardDiskToIDEHardDiskControllerValidInput() {
		addDevice.execute("id1 HardDisk_Controller id12345 IDE");
		String test = addDevice.execute("id1 HardDisk id1 1234 id12345");
		assertEquals("Hard disk is added.", test);

	}

	@Test
	public void testAddingHardDiskToIDEHardDiskControllerNegativeTestValidInput() {
		addDevice.execute("id1 HardDisk_Controller id12345 IDE");
		addDevice.execute("id1 HardDisk id1 1234 id12345");
		addDevice.execute("id1 HardDisk id2 1234 id12345");
		addDevice.execute("id1 HardDisk id3 1234 id12345");
		addDevice.execute("id1 HardDisk id4 1234 id12345");
		String failResult = addDevice.execute("id1 HardDisk id5 1234 id12345");
		assertEquals(
				"Hard disk controller have max number of hard disk attached",
				failResult);
	}

}
