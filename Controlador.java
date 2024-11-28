package PrimerTrimestre.AE2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import PrimerTrimestre.AE2.Modelo;
import PrimerTrimestre.AE2.Vista;

public class Controlador {
	public String usuario;
	public String contraseña;
	private Modelo modelo;
	private Vista vista;
	private ActionListener actionListener_btnLogOut, actionListener_btnLogIn, actionListener_btnCrearUsuario,
			actionListener_btnAddUser, actionListener_btnCancelar, actionListener_btnBusqueda,
			actionListener_btnCargarXML, actionListener_btnPasarCSV;

	public Controlador(Modelo modelo, Vista vista) {
		// TODO Auto-generated constructor stub
		this.modelo = modelo;
		this.vista = vista;

		control();

	}

	public void control() {

		// **********LOGOUT**********
		actionListener_btnLogOut = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vista.getPanelApp().setBounds(0, 0, 0, 0);
				vista.getPanelLogIn().setBounds(308, 70, 310, 254);
				reset();
				usuario = "";
				contraseña = "";
			}
		};
		vista.getBtnLogOut().addActionListener(actionListener_btnLogOut);

		// **********LOGIN**********
		actionListener_btnLogIn = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (vista.getTxtUser().getText().equals("") || vista.getTxtPassword().getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos", "Iniciar sesion",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (Modelo.iniciarSesion(vista.getTxtUser().getText(), vista.getTxtPassword().getText())
							.equals("admin")) {

						vista.getPanelLogIn().setBounds(0, 0, 0, 0);
						vista.getPanelApp().setBounds(10, 11, 894, 476);

						vista.getBtnAddUser().setBounds(10, 453, 137, 23);
						vista.getTxtFrom().setBounds(614, 375, 106, 20);
						vista.getBtnCargarXML().setBounds(157, 452, 137, 23);
						usuario = vista.getTxtUser().getText();
						contraseña = Modelo.hashMD5(vista.getTxtPassword().getText());
					}

					else if (Modelo.iniciarSesion(vista.getTxtUser().getText(), vista.getTxtPassword().getText())
							.equals("client")) {

						vista.getPanelLogIn().setBounds(0, 0, 0, 0);
						vista.getPanelApp().setBounds(10, 11, 894, 476);

						vista.getBtnAddUser().setBounds(0, 0, 0, 0);
						vista.getTxtFrom().setBounds(0, 0, 0, 0);
						vista.getBtnCargarXML().setBounds(0, 0, 0, 0);
						usuario = vista.getTxtUser().getText();
						contraseña = Modelo.hashMD5(vista.getTxtPassword().getText());
					}

					else {
						JOptionPane.showMessageDialog(null,
								"Las credenciales no corresponden con un usuario de la base de datos", "Iniciar sesion",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		};
		vista.getBtnLogIn().addActionListener(actionListener_btnLogIn);

		// **********CREAR USUARIO**********
		actionListener_btnCrearUsuario = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (vista.getTxtNouPassword().getText().equals("") || vista.getTxtNouConfirmar().getText().equals("")
						|| vista.getTxtNouUsuari().getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Rellena todos los campos", "Crear usuario",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					
					if (vista.getTxtNouPassword().getText().equals(vista.getTxtNouConfirmar().getText())) {
						
						if (Modelo.crearUsuario(usuario, contraseña, vista.getTxtNouUsuari().getText(),
								vista.getTxtNouPassword().getText())) {
							JOptionPane.showMessageDialog(null, "Usuario creado", "Crear usuario",
									JOptionPane.INFORMATION_MESSAGE);
							limpiarNuevoUsuario();
						} else {
							JOptionPane.showMessageDialog(null, "El usuario ya existe", "Crear usuario",
									JOptionPane.INFORMATION_MESSAGE);
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Crear usuario",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		};
		vista.getBtnCrearUsuario().addActionListener(actionListener_btnCrearUsuario);

		// **********AÑADIR USUARIO**********
		actionListener_btnAddUser = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vista.getPanelCrearUsuario().setBounds(228, 55, 421, 247);
				vista.getPanelInfo().setBounds(0, 0, 0, 0);
			}
		};
		vista.getBtnAddUser().addActionListener(actionListener_btnAddUser);

		// **********CANCELAR CREAR USUARIO**********
		actionListener_btnCancelar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				reset();
			}
		};
		vista.getBtnCancelar().addActionListener(actionListener_btnCancelar);

		// **********BUSQUEDA**********
		actionListener_btnBusqueda = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				vista.getTxtInfo().setText(Modelo.consultaSelect(usuario, contraseña, vista.getTxtSelect().getText(),
						vista.getTxtFrom().getText(), vista.getTxtWhere().getText()));

				vista.getTxtColumnas().setText(Modelo.getColumnasBaseDatos(usuario, contraseña,
						vista.getTxtSelect().getText(), vista.getTxtFrom().getText(), vista.getTxtWhere().getText()));

			}
		};
		vista.getBtnBusqueda().addActionListener(actionListener_btnBusqueda);

		// **********CARGAR XML**********
		actionListener_btnCargarXML = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Modelo.CSVaXML();
				vista.getTxtXML().setText(Modelo.XMLaString());
				Modelo.CrearLlenarBaseDatos(usuario, contraseña);
			}
		};
		vista.getBtnCargarXML().addActionListener(actionListener_btnCargarXML);

		// **********PASAR CONSULTA A CSV**********
		actionListener_btnPasarCSV = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (vista.getTxtInfo().getText().equals("")) {
					JOptionPane.showMessageDialog(null,
							"Haz una consulta SQL antes de exportar el resultado a un archivo CSV", "Exportar consulta",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.showOpenDialog(vista);
					File archivo = fc.getSelectedFile();

					if (archivo != null) {
						Modelo.pasarConsultaCSV(vista.getTxtColumnas().getText(), vista.getTxtInfo().getText(),
								archivo);
					}
				}

			}
		};
		vista.getBtnExportarCSV().addActionListener(actionListener_btnPasarCSV);

	}

	private void reset() {
		vista.getPanelCrearUsuario().setBounds(0, 0, 0, 0);
		vista.getPanelInfo().setBounds(38, 11, 824, 430);

		vista.getTxtFrom().setText("");
		vista.getTxtSelect().setText("");
		vista.getTxtWhere().setText("");

		vista.getTxtInfo().setText("");
		vista.getTxtColumnas().setText("");

		vista.getTxtUser().setText("");
		vista.getTxtPassword().setText("");
		limpiarNuevoUsuario();
	}

	private void limpiarNuevoUsuario() {
		vista.getTxtNouUsuari().setText("");
		vista.getTxtNouPassword().setText("");
		vista.getTxtNouConfirmar().setText("");
	}
}
