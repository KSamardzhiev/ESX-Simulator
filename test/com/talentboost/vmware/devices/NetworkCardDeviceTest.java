package com.talentboost.vmware.devices;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.talentboost.vmware.devices.NetworkCardDevice;

public class NetworkCardDeviceTest {

	private NetworkCardDevice networkCard;
	private NetworkCardDevice otherNetworkCard;

	@Before
	public void setUp() throws Exception {
		networkCard = new NetworkCardDevice("id112", "FF-FF-FF-FF-FF-FF", "192.168.15.181");
		otherNetworkCard = new NetworkCardDevice("id111","AA-AA-AA-AA-AA-AA");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidID() {
		new NetworkCardDevice("id-231/", "FF-FF-FF-FF-FF-FF");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidMACAddr() {
		new NetworkCardDevice("id212", "FF-FF-hh-FF:123-FF");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidIPAddr() {
		new NetworkCardDevice("id212", "FF-FF-AA-FF-12-FF", "192.168.15.300");
	}

	@Test
	public void testConstructorWithTwoParameters() {
		NetworkCardDevice testCard = new NetworkCardDevice("id212", "FF-FF-AA-FF-12-FF");
		assertEquals(null, testCard.getIPAddr());
	}

	@Test
	public void testConstructorWithThreeParameters() {
		new NetworkCardDevice("id212", "FF-FF-AA-FF-12-FF", "192.168.15.181");
	}

	@Test
	public void testGetID() {
		assertEquals("id112", networkCard.getID());
	}

	@Test
	public void testGetType() {
		assertEquals("NETWORK_CARD", networkCard.getType());
	}

	@Test
	public void testGetMACAddr() {
		assertEquals("FF-FF-FF-FF-FF-FF", networkCard.getMACAddr());
	}

	@Test
	public void testGetIPAddr() {
		assertEquals("192.168.15.181", networkCard.getIPAddr());
	}
	
	@Test
	public void testToString(){
		assertEquals("NETWORK_CARD id111 AA-AA-AA-AA-AA-AA", otherNetworkCard.toString());
	}
	
	@Test
	public void testToStringWithIPAddr(){
		assertEquals("NETWORK_CARD id112 FF-FF-FF-FF-FF-FF 192.168.15.181", networkCard.toString());
	}

}
