package com.talentboost.vmware.commands.devices;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.ESXSimulator;
import com.talentboost.vmware.commands.devices.DeleteDeviceCmd;
import com.talentboost.vmware.commands.vm.CreateVirtualMachineCmd;
import com.talentboost.vmware.devices.HDControllerIDEDevice;
import com.talentboost.vmware.devices.HDControllerSCSIDevice;
import com.talentboost.vmware.devices.NetworkCardDevice;
import com.talentboost.vmware.devices.VideoCardDevice;

public class DeleteDeviceCmdTest {

	private NetworkCardDevice networkCard;
	private VideoCardDevice videoCard;
	private HDControllerIDEDevice hdIDE;
	private HDControllerSCSIDevice hdSCSI;
	private DeleteDeviceCmd delete;
	private CreateVirtualMachineCmd create = new CreateVirtualMachineCmd();

	@Before
	public void setUp() throws Exception {
		ESXSimulator.VMsStorage.clear();
		create.execute("id1 'OSX 10' 4096 4");
		networkCard = new NetworkCardDevice("id12", "FF-FF-FF-FF-FF-FF");
		videoCard = new VideoCardDevice("id13", 1024, (byte) 2);
		hdIDE = new HDControllerIDEDevice("idHDIDE");
		hdSCSI = new HDControllerSCSIDevice("idHDSCSI");
		delete = new DeleteDeviceCmd();
		ESXSimulator.VMsStorage.get("id1").addDevice(networkCard);
		ESXSimulator.VMsStorage.get("id1").addDevice(videoCard);
		ESXSimulator.VMsStorage.get("id1").addDevice(hdIDE);
		ESXSimulator.VMsStorage.get("id1").addDevice(hdSCSI);
	}

	@Test
	public void testExecuteDeleteNetworkCardDevice() {
		String result = delete.execute("id1 id12");
		assertEquals("The device is deleted", result);
	}

	@Test
	public void testGetName() {
		assertEquals("delete-dev", delete.getName());
	}

	@Test
	public void testGetInfo() {
		assertEquals(
				"delete-dev - This command provide ability to delete particular device on particular virtual machine\n\n",
				delete.info());
	}

	@Test
	public void testExecuteDeleteNetworkCardDeviceNegativeTest() {
		String failResult = delete.execute("id1 id123");
		assertNotEquals("The device is deleted", failResult);
	}

	@Test
	public void testExecuteDeleteVideoCardDevice() {
		String result = delete.execute("id1 id13");
		assertEquals("The device is deleted", result);
	}

	@Test
	public void testExecuteDeleteVideoCardDeviceNegativeTest() {
		String failResult = delete.execute("id1 id123");
		assertNotEquals("The device is deleted", failResult);
	}

	@Test
	public void testExecuteDeleteHDControllerIDEDevice() {
		String result = delete.execute("id1 idHDIDE");
		assertEquals("The device is deleted", result);
	}

	@Test
	public void testExecuteDeleteHDContorellerIDENegativeTest() {
		String result = delete.execute("id1 idHDIDE1");
		assertNotEquals("The device is deleted", result);
	}

	@Test
	public void testExecuteDeleteHDControllerSCSIDevice() {
		String result = delete.execute("id1 idHDSCSI");
		assertEquals("The device is deleted", result);
	}

	@Test
	public void testExecuteDeleteHDControllerSCSIDeviceNegativeTest() {
		String result = delete.execute("id1 idHDSCSI1");
		assertNotEquals("The device is deleted", result);
	}

}
