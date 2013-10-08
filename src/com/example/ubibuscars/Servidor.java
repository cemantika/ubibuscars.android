package com.example.ubibuscars;

public class Servidor {

	//para testar no simulador   
	//private static final String servidor="http://10.0.2.2/ubibuscars.ws";
	
	private static final String servidor="http://192.168.1.2/ubibuscars.ws";
	
	//private static final String servidor="http://200.128.51.48/cemantika/ubibuscars";
	
	

	public static String getServidor() {
		return servidor;
	}
}
