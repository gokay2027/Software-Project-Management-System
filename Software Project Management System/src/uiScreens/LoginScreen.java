package uiScreens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;

import databaseProcesses.GeneralDB;

import javax.swing.JButton;
import javax.swing.ButtonGroup;

import java.awt.Color;
import keeptoo.KGradientPanel;
import softwareProjectManagement.Customer;
import softwareProjectManagement.ITWorker;
import softwareProjectManagement.Manager;
import softwareProjectManagement.Person;
import softwareProjectManagement.Project;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;

public class LoginScreen {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private GeneralDB DB = GeneralDB.getObject();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					LoginScreen window = new LoginScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 552, 374);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ButtonGroup buttonGroup = new ButtonGroup();
		KGradientPanel gradientPanel = new KGradientPanel();
		frame.getContentPane().add(gradientPanel, BorderLayout.CENTER);
		gradientPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(148, 112, 131, 31);
		gradientPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(148, 174, 114, 31);
		gradientPanel.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(148, 136, 237, 27);
		gradientPanel.add(textField);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				try {
					String selection = buttonGroup.getSelection().getActionCommand();
					if (selection.isEmpty()) {
						JOptionPane.showMessageDialog(frame, "NO BUTTON SELECTED",
								"CHOOSE CUSTOMER OR WORKER BUTTON!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (passwordField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(frame, "PASSWORD EMPTY",
								"PASSWORD CANNOT BE EMPTY!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (textField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(frame, "PASSWORD EMPTY",
								"PASSWORD CANNOT BE EMPTY!", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (selection.equalsIgnoreCase("Customer")) {

						
						ResultSet persondata = DB
								.selectData("SELECT * FROM softwaremanagementsystem.customer where customerusername = "
										+ "\"" + textField.getText() + "\"" + " and" + " customerpassword = " + "\""
										+ passwordField.getText() + "\"" + ";");
						persondata.next();


						Customer customer = new Customer(Integer.parseInt(persondata.getString(1)), "Customer",
								persondata.getString(2), persondata.getString(3), persondata.getString(4));
						persondata.close();
						ResultSet projectOfCustomer = DB
								.selectData("select * FROM softwaremanagementsystem.project where Customer_idCustomer="
										+ customer.getId() + ";");
						if (projectOfCustomer.next()) {
							customer.setProject(new Project(projectOfCustomer.getInt(1), customer.getId(),
									projectOfCustomer.getString(2), projectOfCustomer.getString(3)));
						}
						CustomerScreen.OpenCustomerScreen(customer);

					} else {

						ResultSet persondata = DB.selectData(
								"select worker.workerid, workername, workersurname, experience, workersalary, workerPhoneNumber, title.titleName, Team_idTeam from softwaremanagementsystem.worker \r\n"
										+ "inner join title on worker.Title_idTitle = title.idTitle \r\n"
										+ "where workerUsername = \"" + textField.getText()
										+ "\" and workerPassword =\"" + passwordField.getText() + "\";");
						persondata.next();

						if (persondata.getString(7).equalsIgnoreCase("manager")) {


							Person person = new Manager(Integer.parseInt(persondata.getString(1)),
									persondata.getString(7), persondata.getString(2), persondata.getString(3),
									persondata.getString(6), Integer.parseInt(persondata.getString(5)));
							ManagerScreen.openManagerScreen(person);

						} else {
							Person person = new ITWorker(Integer.parseInt(persondata.getString(1)),
									persondata.getString(7), persondata.getString(2), persondata.getString(3),
									persondata.getString(6), Integer.parseInt(persondata.getString(5)),
									Integer.parseInt(persondata.getString(4)),
									Integer.parseInt(persondata.getString(8)));
							ITWorkerScreen.OpenITWorkerScreen(person);

						}

					}
				} catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(frame, "Wrong data was entered try again",
							"Some data was given incorrect", JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		btnNewButton.setBounds(177, 241, 163, 31);
		gradientPanel.add(btnNewButton);

		JLabel lblNewLabel_2 = new JLabel("Software Project");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(133, 11, 414, 68);
		gradientPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel(" Management System");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_3.setBounds(143, 59, 252, 31);
		gradientPanel.add(lblNewLabel_3);

		passwordField = new JPasswordField();
		passwordField.setBounds(149, 203, 236, 27);
		gradientPanel.add(passwordField);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Customer");
		rdbtnNewRadioButton.setBounds(111, 292, 109, 23);
		gradientPanel.add(rdbtnNewRadioButton);

		JRadioButton rdbtnWorker = new JRadioButton("Worker");
		rdbtnWorker.setBounds(250, 292, 109, 23);
		gradientPanel.add(rdbtnWorker);

		rdbtnWorker.setActionCommand("Worker");
		rdbtnNewRadioButton.setActionCommand("Customer");

		buttonGroup.add(rdbtnNewRadioButton);
		buttonGroup.add(rdbtnWorker);
	}

}
