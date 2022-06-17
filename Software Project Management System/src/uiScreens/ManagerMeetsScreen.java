package uiScreens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import keeptoo.KGradientPanel;
import softwareProjectManagement.Meet;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import databaseProcesses.GeneralDB;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingConstants;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class ManagerMeetsScreen {

	private JFrame frame;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private GeneralDB DB = GeneralDB.getObject();
	private String selectedMeetId;

	/**
	 * Launch the application.
	 */
	public static void OpenManagerMeetsScreen(String projectName, String teamid) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerMeetsScreen window = new ManagerMeetsScreen(projectName, teamid);
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
	 * @throws SQLException
	 */
	public ManagerMeetsScreen(String projectName, String teamid) throws SQLException {
		initialize(projectName, teamid);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private void initialize(String projectName, String teamid) throws SQLException {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 717, 576);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		KGradientPanel gradientPanel = new KGradientPanel();
		gradientPanel.setBounds(198, 0, 519, 538);
		frame.getContentPane().add(gradientPanel);
		gradientPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 482, 516);
		gradientPanel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				selectedMeetId = (String) table.getValueAt(table.getSelectedRow(), 0);

			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "MeetId", "Meet Name", "Description", "Time" }));

		refreshTable(teamid);

		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Meets");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(29, 19, 129, 62);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Meet Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 92, 129, 24);
		frame.getContentPane().add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(10, 120, 168, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Description:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(10, 161, 129, 24);
		frame.getContentPane().add(lblNewLabel_1_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 196, 168, 30);
		frame.getContentPane().add(textField_1);

		JLabel lblNewLabel_1_2 = new JLabel("Time:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(10, 237, 181, 24);
		frame.getContentPane().add(lblNewLabel_1_2);

		JLabel lblNewLabel_2 = new JLabel("Project:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel_2.setBounds(10, 354, 129, 38);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Project Name");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(90, 364, 98, 22);
		frame.getContentPane().add(lblNewLabel_3);

		JButton btnNewButton = new JButton("Arrange Meet");
		btnNewButton.setBounds(10, 422, 168, 38);
		frame.getContentPane().add(btnNewButton);

		JButton btnCancelMeet = new JButton("Cancel Meet");
		btnCancelMeet.setForeground(new Color(255, 0, 0));
		btnCancelMeet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					DB.deleteData("DELETE FROM attendence WHERE attendence.meet_idMeet=" + selectedMeetId);
					DB.deleteData("DELETE FROM meet WHERE meet.idMeet=" + selectedMeetId);
					refreshTable(teamid);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnCancelMeet.setBounds(10, 471, 168, 38);
		frame.getContentPane().add(btnCancelMeet);

		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setModel(new DefaultComboBoxModel<Object>(
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
						"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
		comboBox.setBounds(10, 272, 51, 30);
		frame.getContentPane().add(comboBox);

		JComboBox<Object> comboBox_1 = new JComboBox<Object>();
		comboBox_1.setModel(new DefaultComboBoxModel<Object>(
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		comboBox_1.setBounds(67, 272, 51, 30);
		frame.getContentPane().add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox();
		String[] years = new String[100];
		for (int i = 2022; i < 2122; i++) {

			years[i - 2022] = i + "";

		}

		comboBox_2.setModel(new DefaultComboBoxModel(years));
		comboBox_2.setBounds(127, 272, 61, 30);
		frame.getContentPane().add(comboBox_2);

		JComboBox comboBox_3 = new JComboBox();

		String[] hours = new String[24];

		for (int i = 0; i < 24; i++) {

			if (i < 10) {

				hours[i] = "0" + i;

			} else {
				hours[i] = i + "";
			}

		}

		comboBox_3.setModel(new DefaultComboBoxModel(hours));
		comboBox_3.setBounds(29, 313, 51, 30);
		frame.getContentPane().add(comboBox_3);

		String[] minutes = new String[60];

		for (int i = 0; i < 60; i++) {
			minutes[i] = i + "";
		}

		JComboBox<Object> comboBox_3_1 = new JComboBox<Object>();
		comboBox_3_1.setModel(new DefaultComboBoxModel<Object>(minutes));
		comboBox_3_1.setBounds(88, 313, 51, 31);
		frame.getContentPane().add(comboBox_3_1);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isEmpty() || textField_1.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "At least one area is empty! Try Again", "EMPTY AREA",
							JOptionPane.WARNING_MESSAGE);

				} else {

					String meetname = textField.getText();
					String meetdesc = textField_1.getText();

					String meetTime = comboBox.getSelectedItem() + "/" + comboBox_1.getSelectedItem() + "/"
							+ comboBox_2.getSelectedItem() + "-" + comboBox_3.getSelectedItem() + ":"
							+ comboBox_3_1.getSelectedItem();


					String insertQueryMeet = String.format(
							"INSERT INTO meet (meet.meetName, meet.meetDescription, meet.meetTime,meet.Team_idTeam)\r\n"
									+ "VALUES (\"%s\",\"%s\",\"%s\",\"%s\");",
							meetname, meetdesc, meetTime, teamid);

					String getWorkers = "SELECT * FROM softwaremanagementsystem.worker\r\n" + "where Team_idTeam="
							+ teamid;

					String lastMeetId = null;

					try {
						DB.insertData(insertQueryMeet);

						ResultSet lastAddedId = DB
								.selectData("SELECT * FROM meet ORDER BY meet.idMeet\r\n" + " DESC LIMIT 1");
						lastAddedId.next();
						lastMeetId = lastAddedId.getString(1);
						lastAddedId.close();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ArrayList<String> workerids = new ArrayList<String>();
					try {
						ResultSet rs = DB.selectData(getWorkers);

						while (rs.next()) {

							String workerid = rs.getString(1);
							workerids.add(workerid);
						}

					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					for (int i = 0; i < workerids.size(); i++) {

						String insertAttendence = String.format(
								"INSERT INTO attendence (attendence.isAttended, attendence.worker_workerid, attendence.meet_idMeet)\r\n"
										+ "VALUES (%s,\"%s\",\"%s\");",
								"0", workerids.get(i), lastMeetId);


						try {
							DB.insertData(insertAttendence);
							refreshTable(teamid);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}

				}

			}
		});
	}

	private void refreshTable(String teamid) throws SQLException {

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
		ResultSet rs = DB.selectData("SELECT * FROM meet\r\n" + "where Team_idTeam=" + teamid);

		while (rs.next()) {

			Meet meet = new Meet(rs.getInt("idMeet"), Integer.parseInt(teamid), rs.getString("meetName"),
					rs.getString("meetDescription"), rs.getString("meetTime"));

			String[] row = { Integer.toString(meet.getMeetingId()), meet.getName(), meet.getDescription(),
					meet.getMeetingTime() };

			tableModel.addRow(row);

		}
		rs.close();

	}

}
