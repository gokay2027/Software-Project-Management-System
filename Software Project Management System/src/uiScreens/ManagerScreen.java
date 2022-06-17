package uiScreens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import keeptoo.KGradientPanel;
import softwareProjectManagement.Customer;
import softwareProjectManagement.Manager;
import softwareProjectManagement.Person;

import javax.swing.JLabel;
import javax.swing.JOptionPane;


import databaseProcesses.GeneralDB;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class ManagerScreen {

	private JFrame frame;
	private GeneralDB DB = GeneralDB.getObject();
	private String projectName;
	private int projectId;
	private int teamid;

	/**
	 * Launch the application.
	 */
	public static void openManagerScreen(Person person) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					ManagerScreen window = new ManagerScreen(person);
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
	public ManagerScreen(Person person) throws SQLException {
		initialize(person);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	private void initialize(Person person) throws SQLException {

		ResultSet rs = DB.selectData("select * from worker\r\n" + "where workerid = " + person.getId());
		rs.next();
		teamid = rs.getInt("Team_idTeam");

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 575, 728);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		KGradientPanel gradientPanel = new KGradientPanel();
		gradientPanel.kEndColor = Color.RED;
		gradientPanel.kStartColor = Color.BLUE;
		gradientPanel.setBounds(359, 0, 243, 761);
		frame.getContentPane().add(gradientPanel);
		gradientPanel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("MENU");
		lblNewLabel_4.setForeground(new Color(255, 255, 224));
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_4.setBounds(56, 84, 107, 48);
		gradientPanel.add(lblNewLabel_4);

		JButton btnNewButton = new JButton("Meets");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ManagerMeetsScreen.OpenManagerMeetsScreen(projectName, teamid + "");
			}
		});
		btnNewButton.setBounds(38, 232, 125, 32);
		gradientPanel.add(btnNewButton);

		JButton btnProjectTasks = new JButton("Project Tasks");
		btnProjectTasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ManagerTasksScreen.OpenManagerTasksScreen(person);

			}
		});
		btnProjectTasks.setBounds(38, 354, 125, 32);
		gradientPanel.add(btnProjectTasks);

		JButton btnTeam = new JButton("Team and Reports");
		btnTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				ManagerTeamMembers.OpenManagerTeamScreen(teamid, projectName);

			}
		});
		btnTeam.setBounds(38, 482, 125, 32);
		gradientPanel.add(btnTeam);

		JButton btnNewButton_1 = new JButton("Show message");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					ResultSet messageRS = DB.selectData(
							"SELECT customer.messageTitle , currentMessage FROM softwaremanagementsystem.project\r\n"
									+ "inner join customer on project.Customer_idCustomer=customer.idCustomer where project.idProject="
									+ projectId);

					messageRS.next();

					
					JOptionPane.showMessageDialog(frame, messageRS.getString("currentMessage"),

							messageRS.getString("messageTitle"), JOptionPane.DEFAULT_OPTION);

					messageRS.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNewButton_1.setBounds(63, 632, 127, 48);
		gradientPanel.add(btnNewButton_1);

		Manager manager = (Manager) person;

		JLabel lblNewLabel = new JLabel("Personal \r");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(10, 11, 339, 75);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Title:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 156, 103, 34);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\nInformation");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_2.setBounds(10, 75, 284, 47);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_1_1 = new JLabel("Name:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(10, 201, 103, 34);
		frame.getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Surname:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_2.setBounds(10, 246, 103, 34);
		frame.getContentPane().add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Current Project:");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_3.setBounds(10, 291, 169, 34);
		frame.getContentPane().add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_5 = new JLabel("Salary:");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_5.setBounds(10, 336, 103, 34);
		frame.getContentPane().add(lblNewLabel_1_5);

		JLabel lblNewLabel_1_6 = new JLabel("Phone Number:");
		lblNewLabel_1_6.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_6.setBounds(10, 381, 169, 34);
		frame.getContentPane().add(lblNewLabel_1_6);

		JLabel lblNewLabel_3 = new JLabel(person.getTitle());
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(67, 167, 112, 23);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_3_1 = new JLabel(person.getPersonName());
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_1.setBounds(80, 215, 99, 20);
		frame.getContentPane().add(lblNewLabel_3_1);

		JLabel lblNewLabel_3_2 = new JLabel(person.getPersonSurname());
		lblNewLabel_3_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_2.setBounds(114, 260, 147, 20);
		frame.getContentPane().add(lblNewLabel_3_2);

		try {
			ResultSet resultset = DB.selectData(
					"select idProject, projectname from worker inner join project on worker.Team_idTeam = project.Team_idTeam\r\n"
							+ "where workerid =+" + manager.getId());
			resultset.next();

			projectId = resultset.getInt(1);
			projectName = resultset.getString(2);
			JLabel lblNewLabel_3_3 = new JLabel(resultset.getString(2));
			lblNewLabel_3_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabel_3_3.setBounds(183, 292, 166, 34);
			frame.getContentPane().add(lblNewLabel_3_3);
			resultset.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JLabel lblNewLabel_3_5 = new JLabel("" + manager.getSalary() + "$");
		lblNewLabel_3_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_5.setBounds(90, 343, 132, 23);
		frame.getContentPane().add(lblNewLabel_3_5);

		JLabel lblNewLabel_3_6 = new JLabel(manager.getPersonPhone());
		lblNewLabel_3_6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_6.setBounds(178, 384, 171, 31);
		frame.getContentPane().add(lblNewLabel_3_6);

		KGradientPanel gradientPanel_1 = new KGradientPanel();
		gradientPanel_1.setLayout(null);
		gradientPanel_1.kStartColor = Color.BLUE;
		gradientPanel_1.setkStartColor(Color.BLUE);
		gradientPanel_1.kEndColor = Color.RED;
		gradientPanel_1.setkEndColor(Color.RED);
		gradientPanel_1.setBounds(0, 611, 365, 86);
		frame.getContentPane().add(gradientPanel_1);

		JButton btnNewButton_1_1 = new JButton("Cancel");
		btnNewButton_1_1.setForeground(new Color(255, 0, 0));
		btnNewButton_1_1.setBounds(201, 21, 139, 37);
		gradientPanel_1.add(btnNewButton_1_1);


		JLabel lblProjectInformation = new JLabel("Project Information");
		lblProjectInformation.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblProjectInformation.setBounds(10, 414, 339, 75);
		frame.getContentPane().add(lblProjectInformation);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 426, 339, 2);
		frame.getContentPane().add(separator);

		JLabel lblNewLabel_1_7 = new JLabel("Project Owner:");
		lblNewLabel_1_7.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_7.setBounds(10, 494, 169, 34);
		frame.getContentPane().add(lblNewLabel_1_7);

		ResultSet allTasksCountRs = DB.selectData("select count(task.idTask) from project inner join\r\n"
				+ "task on task.Project_idProject=project.idProject\r\n" + "where project.Team_idTeam=" + teamid);

		allTasksCountRs.next();

		double allTasksCount = allTasksCountRs.getInt(1);

		allTasksCountRs.close();

		ResultSet completedTasksCountRs = DB.selectData("select count(task.idTask) from project inner join\r\n"
				+ "task on task.Project_idProject=project.idProject\r\n"
				+ "where task.Taskstatus=1 and project.Team_idTeam=" + teamid);

		completedTasksCountRs.next();

		double completedTasksCount = completedTasksCountRs.getInt(1);
		completedTasksCountRs.close();


		double percentage = (completedTasksCount / allTasksCount) * 100;
		if (percentage==100.0) {
			DB.updateData("update project set projectStatus = 1 where idProject="+projectId);
		}else {
			//in the case of adding a new task after completing a project, needed to change its status
			DB.updateData("update project set projectStatus = 0 where idProject="+projectId);

		}

		ResultSet customerRS = DB.selectData(
				"select customer.idCustomer, customer.customerName, customer.customerSurname,customer.customerPhone\r\n"
						+ "from project inner join \r\n" + "customer where project.Team_idTeam=" + teamid);

		customerRS.next();


		Customer customer = new Customer(customerRS.getInt(1), "Customer", customerRS.getString(2),
				customerRS.getString(3), customerRS.getString(4));
		customerRS.close();

		ResultSet countWorkerRS = DB
				.selectData("select count(workerid) from worker \r\n" + "where worker.Team_idTeam=" + teamid);

		countWorkerRS.next();

		int workercount = countWorkerRS.getInt(1);

		countWorkerRS.close();

		
		JLabel lblNewLabel_3_7 = new JLabel(customer.getPersonName() + " " + customer.getPersonSurname());
		lblNewLabel_3_7.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_7.setBounds(167, 505, 153, 14);
		frame.getContentPane().add(lblNewLabel_3_7);

		JLabel lblNewLabel_1_7_1 = new JLabel("Project Status:");
		lblNewLabel_1_7_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_7_1.setBounds(10, 530, 169, 34);
		frame.getContentPane().add(lblNewLabel_1_7_1);

		JLabel lblNewLabel_3_7_1 = new JLabel(percentage + "%");
		if (percentage==100.0) lblNewLabel_3_7_1.setText(percentage+ "% - COMPLETED");
		lblNewLabel_3_7_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_7_1.setBounds(167, 541, 182, 14);
		frame.getContentPane().add(lblNewLabel_3_7_1);

		JLabel lblNewLabel_1_7_1_1 = new JLabel("Project Workers:");
		lblNewLabel_1_7_1_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_1_7_1_1.setBounds(10, 566, 187, 34);
		frame.getContentPane().add(lblNewLabel_1_7_1_1);

		JLabel lblNewLabel_3_7_1_1 = new JLabel(workercount + "");
		lblNewLabel_3_7_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_3_7_1_1.setBounds(167, 575, 83, 14);
		frame.getContentPane().add(lblNewLabel_3_7_1_1);
		

		JButton btnNewButton_1_1_1 = new JButton("Complete");
		btnNewButton_1_1_1.setBounds(35, 21, 139, 37);
		gradientPanel_1.add(btnNewButton_1_1_1);
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (percentage==100.0) {
					try {
						DB.updateData("update project set projectStatus = 1 where idProject="+projectId);
						lblNewLabel_3_7_1.setText(percentage+ "% - COMPLETED");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to complete although all tasks are not completed?", "Complete?",  JOptionPane.YES_NO_OPTION);
					if (reply == JOptionPane.YES_OPTION)
					{
						try {
							DB.updateData("update project set projectStatus = 1 where idProject="+projectId);
							lblNewLabel_3_7_1.setText(percentage+ "% - COMPLETED");					
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}
		);
	}
}
