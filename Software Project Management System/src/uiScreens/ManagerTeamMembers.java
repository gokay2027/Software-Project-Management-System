package uiScreens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import keeptoo.KGradientPanel;
import softwareProjectManagement.ITWorker;
import softwareProjectManagement.Team;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


import databaseProcesses.GeneralDB;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagerTeamMembers {

	private String selectedId;

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private GeneralDB DB = GeneralDB.getObject();

	/**
	 * Launch the application.
	 */
	public static void OpenManagerTeamScreen(int teamId, String projectName) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerTeamMembers window = new ManagerTeamMembers(teamId, projectName);
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
	public ManagerTeamMembers(int teamid, String projectName) throws SQLException {
		initialize(teamid, projectName);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	private void initialize(int teamid, String projectName) throws SQLException {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 844, 711);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		KGradientPanel gradientPanel = new KGradientPanel();
		gradientPanel.setBounds(245, -19, 631, 754);
		frame.getContentPane().add(gradientPanel);
		gradientPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(10, 28, 564, 656);
		gradientPanel.add(scrollPane);

		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				selectedId = (String) table.getValueAt(table.getSelectedRow(), 0);

				String name = (String) table.getValueAt(table.getSelectedRow(), 1);
				String surname = (String) table.getValueAt(table.getSelectedRow(), 2);
				String title = (String) table.getValueAt(table.getSelectedRow(), 3);
				String experience = (String) table.getValueAt(table.getSelectedRow(), 4);

				textField.setText(name);
				textField_1.setText(surname);
				textField_2.setText(title);
				textField_3.setText(experience);

			}
		});

		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Worker Id", "Name", "Surname", "Title", "Experience" }));
		scrollPane.setViewportView(table);

		ArrayList<ITWorker> teamWorkers = new ArrayList<ITWorker>();

		ResultSet rs = DB.selectData("select * from worker inner join title\r\n" + "on Title_idTitle=title.idTitle\r\n"
				+ "where Team_idTeam=" + teamid);

		while (rs.next()) {

			ITWorker worker = new ITWorker(rs.getInt("workerid"), rs.getString("titleName"), rs.getString("workerName"),
					rs.getString("workerSurname"), rs.getString("workerPhoneNumber"), rs.getInt("workerSalary"),
					rs.getInt("experience"), rs.getInt("Team_idTeam"));

			teamWorkers.add(worker);


		}

		Team team = new Team(teamid, teamWorkers);

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		for (int i = 0; i < team.getMembers().size(); i++) {

			String[] row = { team.getMembers().get(i).getId() + "", team.getMembers().get(i).getPersonName(),
					team.getMembers().get(i).getPersonSurname(), team.getMembers().get(i).getTitle(),
					"" + team.getMembers().get(i).getExperience() + " years" };

			model.addRow(row);

		}

		JLabel lblNewLabel = new JLabel("Team Members");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(10, 11, 204, 97);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 102, 133, 47);
		frame.getContentPane().add(lblNewLabel_1);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 143, 183, 27);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Surname:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(10, 179, 133, 47);
		frame.getContentPane().add(lblNewLabel_1_1);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(10, 220, 183, 27);
		frame.getContentPane().add(textField_1);

		JLabel lblNewLabel_1_2 = new JLabel("Title:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(10, 258, 133, 47);
		frame.getContentPane().add(lblNewLabel_1_2);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(10, 299, 183, 27);
		frame.getContentPane().add(textField_2);

		JLabel lblNewLabel_1_3 = new JLabel("Experience");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_3.setBounds(10, 337, 133, 47);
		frame.getContentPane().add(lblNewLabel_1_3);

		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(10, 378, 183, 27);
		frame.getContentPane().add(textField_3);

		JLabel lblNewLabel_1_3_1 = new JLabel("Project Name:");
		lblNewLabel_1_3_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_3_1.setBounds(10, 416, 133, 47);
		frame.getContentPane().add(lblNewLabel_1_3_1);

		JLabel lblNewLabel_1_3_1_1 = new JLabel("<Project Name>");
		lblNewLabel_1_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_3_1_1.setBounds(31, 435, 204, 81);
		lblNewLabel_1_3_1_1.setText(projectName);

		frame.getContentPane().add(lblNewLabel_1_3_1_1);

		JButton btnNewButton = new JButton("Show Reports");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "You should choose a worker First",
							"You have to choose worker first", JOptionPane.WARNING_MESSAGE);
				} else {

					for (int i = 0; i < team.getMembers().size(); i++) {

						if (Integer.toString(team.getMembers().get(i).getId()).equals(selectedId)) {

							ManagerReportsList.OpenManagerReportsScreen(team.getMembers().get(i));
							break;
						}

					}

				}

			}
		});
		btnNewButton.setBounds(10, 546, 192, 47);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Delete Member");
		btnNewButton_1.setForeground(Color.RED);
		btnNewButton_1.setBounds(10, 604, 192, 47);
		frame.getContentPane().add(btnNewButton_1);

	}
}
