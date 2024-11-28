package PrimerTrimestre.AE2;

import java.io.IOException;

import PrimerTrimestre.AE2.Controlador;
import PrimerTrimestre.AE2.Modelo;
import PrimerTrimestre.AE2.Vista;

public class Principal {

	public static void main(String[] args) throws IOException {

		Modelo modelo = new Modelo();
		Vista vista = new Vista();
		Controlador Controlador = new Controlador(modelo, vista);

	}

}
