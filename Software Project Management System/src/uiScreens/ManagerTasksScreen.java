package uiScreens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import softwareProjectManagement.Manager;
import softwareProjectManagement.Person;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatDarkLaf;

import databaseProcesses.GeneralDB;

import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagerTasksScreen {

	private JFrame frame;
	private JTable table;
	private String selectedid = "";
	private String projectid = null;
	GeneralDB DB = GeneralDB.getObject();
	private JComboBox comboBox_3;

	/**
	 * Launch the application.
	 */
	public static void OpenManagerTasksScreen(Person person) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ManagerTasksScreen window = new ManagerTasksScreen(person);
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
	public ManagerTasksScreen(Person person) {
		initialize(person);
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Person person) {
		Manager manager = (Manager) person;

		

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1025, 785);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Project Tasks");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(10, 11, 206, 61);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblProjectName = new JLabel("Project Name:");
		lblProjectName.setForeground(Color.WHITE);
		lblProjectName.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblProjectName.setBounds(10, 59, 206, 61);
		frame.getContentPane().add(lblProjectName);

		JLabel lblProjectName_1 = new JLabel("<Project Name>");

		try {
			ResultSet projectResultSet = DB.selectData("select project.idProject, project.ProjectName from worker\r\n"
					+ "inner join project\r\n" + "on project.Team_idTeam=worker.Team_idTeam\r\n"
					+ "where project.projectStatus=0 and worker.workerid=" + manager.getId());
			projectResultSet.next();

			lblProjectName_1.setText(projectResultSet.getString("ProjectName"));
			projectid = projectResultSet.getString("idProject");

			projectResultSet.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

		lblProjectName_1.setForeground(Color.WHITE);
		lblProjectName_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblProjectName_1.setBounds(10, 83, 295, 94);
		frame.getContentPane().add(lblProjectName_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(278, 11, 721, 723);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				String selectedCellValue = (String) table.getValueAt(table.getSelectedRow(), 0);
				selectedid = selectedCellValue;

			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Task Id", "Task Description", "Deadline", "Worker", "Task Status" }));

		String taskQuery = "select * from task\r\n" + "inner join project \r\n"
				+ "on project.idProject=task.Project_idProject\r\n" + "inner join worker\r\n"
				+ "on worker.workerid=task.Worker_workerid\r\n" + "where project.idProject=" + projectid;

		try {

			DefaultTableModel tblmodel = (DefaultTableModel) table.getModel();
			tblmodel.setRowCount(0);
			String[] taskData = new String[5];
			ResultSet taskResult = DB.selectData(taskQuery);

			while (taskResult.next()) {
				taskData[0] = taskResult.getString("idTask");
				taskData[1] = taskResult.getString("TaskDescription");
				taskData[2] = taskResult.getString("deadline");
				taskData[3] = taskResult.getString("workerName") + " " + taskResult.getString("workerSurname");

				if (taskResult.getString("Taskstatus").equals("0")) {
					taskData[4] = "Not Completed";
				} else {
					taskData[4] = "Completed";
				}

				tblmodel.addRow(taskData);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Accept Task");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String acceptQuery = "UPDATE task\r\n" + "SET Taskstatus=1\r\n" + "WHERE task.idTask=" + selectedid;

				try {
					DB.updateData(acceptQuery);

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				refreshTable(projectid);

			}
		});
		btnNewButton.setBounds(10, 202, 230, 40);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Delete Task");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String deleteQuery = "DELETE FROM task WHERE task.idTask=" + selectedid;
					DB.deleteData(deleteQuery);
					refreshTable(projectid);
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(frame, "Task which has report can not be deleted", "Task has report",
							JOptionPane.WARNING_MESSAGE);

				}
			}
		});
		btnNewButton_1.setForeground(Color.RED);
		btnNewButton_1.setBounds(10, 304, 230, 40);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Decline Task");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String acceptQuery = "UPDATE task\r\n" + "SET Taskstatus=0\r\n" + "WHERE task.idTask=" + selectedid;
				try {
					DB.updateData(acceptQuery);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				refreshTable(projectid);

			}
		});
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.setBounds(10, 253, 230, 40);
		frame.getContentPane().add(btnNewButton_2);

		JLabel lblTaskDescription = new JLabel("Task Description:");
		lblTaskDescription.setForeground(Color.WHITE);
		lblTaskDescription.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblTaskDescription.setBounds(10, 339, 206, 61);
		frame.getContentPane().add(lblTaskDescription);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 388, 259, 143);
		frame.getContentPane().add(textArea);

		JLabel lblDeadLine = new JLabel("Deadline:");
		lblDeadLine.setForeground(Color.WHITE);
		lblDeadLine.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblDeadLine.setBounds(10, 519, 206, 61);
		frame.getContentPane().add(lblDeadLine);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
						"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
		comboBox.setBounds(10, 571, 56, 31);
		frame.getContentPane().add(comboBox);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(
				new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		comboBox_1.setBounds(76, 571, 56, 31);
		frame.getContentPane().add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox();

		String[] years = new String[100];

		for (int i = 0; i < years.length; i++) {

			years[i] = 2022 + i + "";

		}

		comboBox_2.setModel(new DefaultComboBoxModel(years));
		comboBox_2.setBounds(142, 571, 98, 31);
		frame.getContentPane().add(comboBox_2);

		JButton btnAddTask = new JButton("Add Task");
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String taskDescription = textArea.getText();
				String time = comboBox.getSelectedItem() + "-" + comboBox_1.getSelectedItem() + "-"
						+ comboBox_2.getSelectedItem();
				String selectedWorkerId = ("" + comboBox_3.getSelectedItem()).split(" ")[0];


				String insertQuery = "INSERT INTO task (task.TaskDescription, task.Taskstatus, task.deadline, task.Project_idProject, task.Worker_workerid)\r\n"
						+ "VALUES (\"%s\",\"0\",\"%s\",\"%s\",\"%s\");";
				String query = String.format(insertQuery, taskDescription, time, projectid, selectedWorkerId);

				try {
					DB.insertData(query);
					refreshTable(projectid);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnAddTask.setBounds(10, 694, 230, 40);
		frame.getContentPane().add(btnAddTask);

		JLabel lblWorkerId = new JLabel("Worker Id:");
		lblWorkerId.setForeground(Color.WHITE);
		lblWorkerId.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblWorkerId.setBounds(10, 601, 206, 61);
		frame.getContentPane().add(lblWorkerId);

		comboBox_3 = new JComboBox();

		String workerResultQuery = "select * from worker inner join\r\n"
				+ "project on worker.Team_idTeam=project.Team_idTeam\r\n" + "where project.idProject=" + projectid;

		try {
			ResultSet workersList = DB.selectData(workerResultQuery);

			ArrayList<String> workerInfo = new ArrayList<String>();

			while (workersList.next()) {

				workerInfo.add(workersList.getString("workerid") + " " + workersList.getString("workerName") + " "
						+ workersList.getString("workerSurname"));

			}

			comboBox_3.setModel(new DefaultComboBoxModel(workerInfo.toArray()));
			workersList.close();
			comboBox_3.setBounds(10, 648, 230, 31);
			frame.getContentPane().add(comboBox_3);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String[] combobox2model = new String[2100 - 2022];
		for (int i = 0; i < combobox2model.length; i++) {

			combobox2model[i] = "" + (2022 + i) + "";

		}
	}

	private void refreshTable(String projectid) {

		String taskQuery = "select * from task\r\n" + "inner join project \r\n"
				+ "on project.idProject=task.Project_idProject\r\n" + "inner join worker\r\n"
				+ "on worker.workerid=task.Worker_workerid\r\n" + "where project.idProject=" + projectid;

		try {

			DefaultTableModel tblmodel = (DefaultTableModel) table.getModel();
			tblmodel.setRowCount(0);
			String[] taskData = new String[5];
			ResultSet taskResult = DB.selectData(taskQuery);

			while (taskResult.next()) {
				taskData[0] = taskResult.getString("idTask");
				taskData[1] = taskResult.getString("TaskDescription");
				taskData[2] = taskResult.getString("deadline");
				taskData[3] = taskResult.getString("workerName") + " " + taskResult.getString("workerSurname");

				if (taskResult.getString("Taskstatus").equals("0")) {
					taskData[4] = "Not Completed";
				} else {
					taskData[4] = "Completed";
				}

				tblmodel.addRow(taskData);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
