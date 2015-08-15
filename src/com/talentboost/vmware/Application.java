package com.talentboost.vmware;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

	public static Logger logger = LoggerFactory.getLogger("log4j.properties");

	public static void main(String[] args) throws FileNotFoundException {
		ESXSimulatorFactory esx = new ESXSimulatorFactory();
		InputStream stream = System.in;
		//FileInputStream fileStream = new FileInputStream("command.txt");
		esx.getESX().run(stream);
	}

}