package com.talentboost.vmware.interfaces;

public interface IDevice {

	public String getType();
	public String getID();
	public String toString();
	//Override toString() so you can resolve the problem with the map in VirtualMachine class
}
