package uiScreens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;

import databaseProcesses.GeneralDB;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import keeptoo.KGradientPanel;
import softwareProjectManagement.Customer;

public class CustomerMessageScreen {

	private JFrame frame;
	private JTextField textField_1;
	private static GeneralDB DB = GeneralDB.getObject();

	/**
	 * Launch the application.
	 * 
	 * @param customer
	 */
	public static void OpenCustomerOrderScreen(Customer customer) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerMessageScreen window = new CustomerMessageScreen(customer);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @param customer
	 */
	public CustomerMessageScreen(Customer customer) {
		initialize(customer);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param customer
	 */
	private void initialize(Customer customer) {

		

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 791, 574);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Message to Manager");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(20, -17, 335, 72);
		frame.getContentPane().add(lblNewLabel);

		KGradientPanel gradientPanel = new KGradientPanel();
		gradientPanel.setBounds(0, 139, 789, 407);
		frame.getContentPane().add(gradientPanel);
		gradientPanel.setLayout(null);

		JButton btnNewButton = new JButton("Send Message");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(285, 345, 189, 38);
		gradientPanel.add(btnNewButton);

		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 17));
		textArea.setBounds(10, 54, 755, 280);
		gradientPanel.add(textArea);

		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(10, -11, 204, 72);
		gradientPanel.add(lblMessage);
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 19));

		JLabel lblProjectDescription = new JLabel("Message Title:");
		lblProjectDescription.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblProjectDescription.setBounds(20, 24, 204, 72);
		frame.getContentPane().add(lblProjectDescription);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setColumns(10);
		textField_1.setBounds(20, 80, 364, 33);
		frame.getContentPane().add(textField_1);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					String updateQuery = String.format(
							"UPDATE customer \r\n"
									+ "SET customer.messageTitle = \"%s\",customer.currentMessage = \"%s\"\r\n"
									+ "WHERE customer.idCustomer=%s",
							textField_1.getText(), textArea.getText(), customer.getId());

					DB.updateData(updateQuery);

					JOptionPane.showMessageDialog(frame, "Your message has been sent succesfully",
							"Message has been sent", JOptionPane.WARNING_MESSAGE);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
	}
}
