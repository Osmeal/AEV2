package PrimerTrimestre.AE2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JPanel panelLogin;
	private JPanel panelGestion;
	private JButton btnLogOut;
	private JButton btnLogIn;
	private JButton btnAddUser;
	private JButton btnBusqueda;
	private JTextPane txtInfo;
	private JScrollPane scrollPaneInfo;
	private JTextPane txtColumnas;
	private JScrollPane scrollPane;
	private JPanel panelCrearUsuario;
	private JPanel panelInfo;
	private JTextField txtNouUsuari;
	private JPasswordField txtNouPassword;
	private JPasswordField txtNouConfirmar;
	private JButton btnCrearUsuario;
	private JButton btnCargarXML;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JButton btnCancelar;
	private JTextField txtSelect;
	private JTextField txtFrom;
	private JLabel lblNewLabel_7;
	private JTextField txtWhere;
	private JButton btnExportarCSV;
	private JTextPane txtXML;
	private JScrollPane scrollPane_1;

	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 930, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelGestion = new JPanel();
		panelGestion.setBounds(0, 0, 0, 0);
		contentPane.add(panelGestion);
		panelGestion.setLayout(null);

		panelCrearUsuario = new JPanel();
		panelCrearUsuario.setLayout(null);
		panelCrearUsuario.setBounds(0, 0, 0, 0);
		panelGestion.add(panelCrearUsuario);

		txtNouUsuari = new JTextField();
		txtNouUsuari.setColumns(10);
		txtNouUsuari.setBounds(33, 46, 347, 25);
		panelCrearUsuario.add(txtNouUsuari);

		txtNouPassword = new JPasswordField();
		txtNouPassword.setBounds(33, 107, 347, 25);
		panelCrearUsuario.add(txtNouPassword);

		txtNouConfirmar = new JPasswordField();
		txtNouConfirmar.setBounds(33, 168, 347, 25);
		panelCrearUsuario.add(txtNouConfirmar);

		btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.setBounds(291, 211, 120, 25);
		panelCrearUsuario.add(btnCrearUsuario);

		lblNewLabel_2 = new JLabel("Nombre");
		lblNewLabel_2.setBounds(33, 21, 46, 14);
		panelCrearUsuario.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Contraseña");
		lblNewLabel_3.setBounds(33, 82, 78, 14);
		panelCrearUsuario.add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("Confirmar Contraseña");
		lblNewLabel_4.setBounds(33, 143, 126, 14);
		panelCrearUsuario.add(lblNewLabel_4);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(10, 212, 89, 23);
		panelCrearUsuario.add(btnCancelar);

		panelInfo = new JPanel();
		panelInfo.setBounds(38, 11, 824, 430);
		panelGestion.add(panelInfo);
		panelInfo.setLayout(null);

		scrollPaneInfo = new JScrollPane();
		scrollPaneInfo.setBounds(32, 220, 743, 143);
		panelInfo.add(scrollPaneInfo);

		txtInfo = new JTextPane();
		txtInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtInfo.setEditable(false);
		scrollPaneInfo.setViewportView(txtInfo);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 185, 743, 34);
		panelInfo.add(scrollPane);

		txtColumnas = new JTextPane();
		txtColumnas.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(txtColumnas);
		txtColumnas.setEditable(false);
		
		btnBusqueda = new JButton("Buscar");
		btnBusqueda.setBounds(730, 374, 89, 23);
		panelInfo.add(btnBusqueda);
		
		txtFrom = new JTextField();
		txtFrom.setBounds(614, 375, 106, 20);
		panelInfo.add(txtFrom);
		txtFrom.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("FROM  population ");
		lblNewLabel_6.setBounds(575, 378, 124, 14);
		panelInfo.add(lblNewLabel_6);
		
		txtSelect = new JTextField();
		txtSelect.setBounds(66, 375, 499, 20);
		panelInfo.add(txtSelect);
		txtSelect.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("SELECT");
		lblNewLabel_5.setBounds(10, 378, 46, 14);
		panelInfo.add(lblNewLabel_5);
		
		lblNewLabel_7 = new JLabel("WHERE");
		lblNewLabel_7.setBounds(10, 409, 46, 14);
		panelInfo.add(lblNewLabel_7);
		
		txtWhere = new JTextField();
		txtWhere.setColumns(10);
		txtWhere.setBounds(66, 406, 499, 20);
		panelInfo.add(txtWhere);
		
		btnExportarCSV = new JButton("Exportar a CSV");
		btnExportarCSV.setBounds(695, 406, 124, 23);
		panelInfo.add(btnExportarCSV);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 11, 743, 154);
		panelInfo.add(scrollPane_1);
		
		txtXML = new JTextPane();
		txtXML.setEditable(false);
		scrollPane_1.setViewportView(txtXML);

		btnLogOut = new JButton("LogOut");
		btnLogOut.setBounds(788, 453, 96, 23);
		panelGestion.add(btnLogOut);

		btnAddUser = new JButton("Añadir Usuario");
		btnAddUser.setBounds(10, 453, 137, 23);
		panelGestion.add(btnAddUser);
		
		btnCargarXML = new JButton("Cargar XML");
		btnCargarXML.setBounds(157, 452, 137, 23);
		panelGestion.add(btnCargarXML);

		panelLogin = new JPanel();
		panelLogin.setBounds(308, 70, 310, 254);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);

		btnLogIn = new JButton("Iniciar Sesión");
		btnLogIn.setBounds(86, 214, 119, 29);
		panelLogin.add(btnLogIn);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(10, 125, 265, 29);
		panelLogin.add(txtPassword);

		JLabel lblNewLabel_1 = new JLabel("Contraseña");
		lblNewLabel_1.setBounds(10, 100, 96, 14);
		panelLogin.add(lblNewLabel_1);

		txtUser = new JTextField();
		txtUser.setBounds(10, 51, 265, 29);
		panelLogin.add(txtUser);
		txtUser.setColumns(10);

		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(10, 26, 46, 14);
		panelLogin.add(lblNewLabel);

		this.setVisible(true);
	}


	public JTextPane getTxtXML() {
		return txtXML;
	}
	
	public JTextPane getTxtInfo() {
		return txtInfo;
	}

	public JTextPane getTxtColumnas() {
		return txtColumnas;
	}
	
	public JTextField getTxtUser() {
		return txtUser;
	}
	
	public JTextField getTxtPassword() {
		return txtPassword;
	}
	
	public JTextField getTxtNouUsuari() {
		return txtNouUsuari;
	}

	public JTextField getTxtNouPassword() {
		return txtNouPassword;
	}

	public JTextField getTxtNouConfirmar() {
		return txtNouConfirmar;
	}
	
	public JTextField getTxtWhere() {
		return txtWhere;
	}
	
	public JTextField getTxtFrom() {
		return txtFrom;
	}
	
	public JTextField getTxtSelect() {
		return txtSelect;
	}

	public JButton getBtnAddUser() {
		return btnAddUser;
	}

	public JButton getBtnLogIn() {
		return btnLogIn;
	}

	public JButton getBtnCrearUsuario() {
		return btnCrearUsuario;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnBusqueda() {
		return btnBusqueda;
	}
	
	public JButton getBtnCargarXML() {
		return btnCargarXML;
	}
	
	public JButton getBtnExportarCSV() {
		return btnExportarCSV;
	}
	
	public JPanel getPanelApp() {
		return panelGestion;
	}

	public JPanel getPanelLogIn() {
		return panelLogin;
	}

	public JPanel getPanelCrearUsuario() {
		return panelCrearUsuario;
	}

	public JPanel getPanelInfo() {
		return panelInfo;
	}

	public JButton getBtnLogOut() {
		return btnLogOut;
	}
}
