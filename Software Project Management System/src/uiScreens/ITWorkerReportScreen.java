package uiScreens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import keeptoo.KGradientPanel;
import softwareProjectManagement.ITWorker;
import softwareProjectManagement.Person;
import softwareProjectManagement.Report;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatDarkLaf;

import databaseProcesses.GeneralDB;

import java.awt.Color;

public class ITWorkerReportScreen {

	private JFrame frame;
	private GeneralDB DB=GeneralDB.getObject();
	/**
	 * Launch the application.
	 * @param selectedTaskId 
	 * @param person 
	 */
	public static void OpenITWorkerReportsScreen(int selectedTaskId, Person person) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ITWorkerReportScreen window = new ITWorkerReportScreen(selectedTaskId,person);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param selectedTaskId 
	 * @param person 
	 * @throws SQLException 
	 */
	public ITWorkerReportScreen(int selectedTaskId, Person person) throws SQLException {
		initialize(selectedTaskId,person);
	}

	/**
	 * Initialize the contents of the frame.
	 * @param selectedTaskId 
	 * @param person 
	 * @throws SQLException 
	 */
	private void initialize(int selectedTaskId, Person person) throws SQLException {
		
		
		ResultSet task=getTask(selectedTaskId);
		task.next();
		String projectId = task.getString("Project_idProject");

		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(new Rectangle(0, 0, 1000, 1000));
		frame.setBounds(100, 100, 861, 721);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Title:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblNewLabel.setBounds(10, 0, 149, 53);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblItworker = new JLabel(person.getTitle());
		lblItworker.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblItworker.setBounds(69, 0, 149, 53);
		frame.getContentPane().add(lblItworker);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblName.setBounds(10, 44, 149, 53);
		frame.getContentPane().add(lblName);
		
		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblSurname.setBounds(10, 92, 149, 53);
		frame.getContentPane().add(lblSurname);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblPhoneNumber.setBounds(10, 138, 257, 53);
		frame.getContentPane().add(lblPhoneNumber);
		
		JLabel lblTaskDescription = new JLabel("Task Description:");
		lblTaskDescription.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblTaskDescription.setBounds(10, 187, 228, 53);
		frame.getContentPane().add(lblTaskDescription);
		
		JLabel lblItworker_1 = new JLabel(person.getPersonName());
		lblItworker_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblItworker_1.setBounds(79, 44, 149, 53);
		frame.getContentPane().add(lblItworker_1);
		
		JLabel lblItworker_2 = new JLabel(person.getPersonSurname());
		lblItworker_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblItworker_2.setBounds(118, 92, 149, 53);
		frame.getContentPane().add(lblItworker_2);
		
		JLabel lblItworker_3 = new JLabel(person.getPersonPhone());
		lblItworker_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblItworker_3.setBounds(179, 138, 149, 53);
		frame.getContentPane().add(lblItworker_3);
		
		KGradientPanel gradientPanel = new KGradientPanel();
		gradientPanel.setBounds(10, 820, 984, -453);
		frame.getContentPane().add(gradientPanel);
		
		KGradientPanel gradientPanel_1 = new KGradientPanel();
		gradientPanel_1.kStartColor = Color.RED;
		gradientPanel_1.setBounds(0, 237, 872, 462);
		frame.getContentPane().add(gradientPanel_1);
		gradientPanel_1.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.BOLD, 15));
		textArea.setBounds(10, 11, 823, 376);
		gradientPanel_1.add(textArea);
		
		JButton btnNewButton = new JButton("Send Report");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(276, 398, 244, 34);
		gradientPanel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "A REPORT CANNOT BE EMPTY",
							"EMPTY TEXT AREA", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
//				Report report=new Report(1, (ITWorker) person, textArea.getText(),null);
				
				
				Report report = new Report((ITWorker) person,textArea.getText(),selectedTaskId);
				String insertQueryReport = String.format(
						"INSERT INTO  report(reportDescription, Worker_workerid,Task_idTask,Task_Project_idProject)\r\n"
								+ "VALUES (\"%s\",\"%s\",\"%s\",\"%s\");",
						report.getDescription(), person.getId(),selectedTaskId, projectId);
				
				try {
					DB.insertData(insertQueryReport);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JLabel lblItworker_3_1 = new JLabel(task.getString(2));
		lblItworker_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblItworker_3_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblItworker_3_1.setBounds(201, 165, 594, 98);
		frame.getContentPane().add(lblItworker_3_1);
	}
	private ResultSet getTask(int taskId) {
		try {
			return DB.selectData("select * from task where idTask="+taskId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
