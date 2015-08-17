package com.talentboost.vmware.devices;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.devices.HDControllerIDEDevice;
import com.talentboost.vmware.devices.HDControllerSCSIDevice;
import com.talentboost.vmware.devices.HardDiskDevice;

public class HDControllerSCSIDeviceTest {

	HDControllerSCSIDevice hdController;

	@Before
	public void setUp() throws Exception {
		hdController = new HDControllerSCSIDevice("1id1");
	}

	@Test
	public void testGetID() {
		assertEquals("1id1", hdController.getID());
	}

	@Test
	public void testGetType() {
		assertEquals("HardDisk_Controller", hdController.getType());
	}

	@Test
	public void testGetTypeController() {
		assertEquals("SCSI", hdController.getControllerType());
	}

	@Test
	public void testToString() {
		assertEquals("HardDisk_Controller 1id1 SCSI", hdController.toString());
	}

	@Test
	public void testGetHardDisks() {
		assertTrue(hdController.getHardDisks() instanceof Collection<?>);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeSetUp() {
		new HDControllerIDEDevice("1i@d1");
	}

	@Test
	public void testAddHardDiskValidInput() {
		HardDiskDevice hardDisk = new HardDiskDevice("1id1", "id3", 1234l);
		String result = hdController.addHardDisk(hardDisk);
		assertTrue(hdController.getHardDisks().contains(hardDisk));
		assertEquals("Hard disk is added.", result);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddHardDiskNegativeExistingID() {
		hdController.addHardDisk(new HardDiskDevice("1id1", "id3", 1234l));
		hdController.addHardDisk(new HardDiskDevice("1id1", "id3", 1234l));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddHardDiskNegativeTooManyHardDisks() {
		for (int i = 0; i < 17; i++) {
			hdController.addHardDisk(new HardDiskDevice(String.valueOf(i),
					"id3", 1234l));
		}
	}
}
