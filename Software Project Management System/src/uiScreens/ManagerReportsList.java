package uiScreens;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import databaseProcesses.GeneralDB;

import javax.swing.JLabel;
import java.awt.Font;
import keeptoo.KGradientPanel;
import softwareProjectManagement.Person;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ManagerReportsList {

	private JFrame frame;
	private JTable table;
	private GeneralDB DB = GeneralDB.getObject();
	private String reportDescription;

	/**
	 * Launch the application.
	 */
	public static void OpenManagerReportsScreen(Person person) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerReportsList window = new ManagerReportsList(person);
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
	public ManagerReportsList(Person person) throws SQLException {
		initialize(person);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 */
	private void initialize(Person person) throws SQLException {

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1048, 713);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 11, 151, 38);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Surname:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(10, 60, 151, 38);
		frame.getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Title:");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_2.setBounds(10, 109, 151, 38);
		frame.getContentPane().add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel(person.getPersonName());
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_3.setBounds(84, 11, 151, 38);
		frame.getContentPane().add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel(person.getPersonSurname());
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_4.setBounds(113, 60, 151, 38);
		frame.getContentPane().add(lblNewLabel_1_4);

		JLabel lblNewLabel_1_5 = new JLabel(person.getTitle());
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1_5.setBounds(66, 109, 151, 38);
		frame.getContentPane().add(lblNewLabel_1_5);

		KGradientPanel gradientPanel = new KGradientPanel();
		gradientPanel.setBounds(433, 0, 609, 696);
		frame.getContentPane().add(gradientPanel);
		gradientPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 578, 654);
		gradientPanel.add(scrollPane);

		table = new JTable();
		table.setDefaultEditor(Object.class, null);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				reportDescription = (String) table.getValueAt(table.getSelectedRow(), 2);

			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Project Name", "Task Description", "Report Description", "Task Deadline" }));
		scrollPane.setViewportView(table);

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		ResultSet rs = DB.selectData(
				"select * from report inner join\r\n" + "worker on worker.workerid=report.Worker_workerid\r\n"
						+ "inner join task on task.idTask=report.Task_idTask\r\n"
						+ "inner join project on project.idProject=task.Project_idProject\r\n"
						+ "where worker.workerid=" + person.getId());

		while (rs.next()) {

			String projectName = rs.getString("ProjectName");
			String taskDescription = rs.getString("TaskDescription");
			String reportDescription = rs.getString("reportDescription");
			String deadLine = rs.getString("deadline");

			String[] row = { projectName, taskDescription, reportDescription, deadLine };

			tableModel.addRow(row);

		}

		JTextArea reportDescriptionField = new JTextArea();
		reportDescriptionField.setFont(new Font("Tahoma", Font.BOLD, 16));
		reportDescriptionField.setEditable(false);
		reportDescriptionField.setForeground(UIManager.getColor("Button.darkShadow"));
		reportDescriptionField.setDisabledTextColor(Color.BLACK);
		reportDescriptionField.setText("Task\u0131\u0131n raporunun a\u00E7\u0131klamas\u0131 gelecek ");
		reportDescriptionField.setBounds(10, 197, 415, 466);
		frame.getContentPane().add(reportDescriptionField);

		JButton btnNewButton = new JButton("See Report");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				reportDescriptionField.setText(reportDescription);

			}
		});
		btnNewButton.setBounds(246, 148, 177, 38);
		frame.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("Export as PDF");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					exportPDF(person);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(10, 148, 151, 38);
		frame.getContentPane().add(button);
	}
	private void exportPDF(Person person) throws IOException {
		Document doc = new Document();  
		try {
			File file=new File("PDFs");
			if (!file.exists()) {
				file.mkdirs();
			}
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("PDFs\\"+person.getPersonName()+"_"+person.getPersonSurname()+"_report.pdf"));
			doc.open();  
			doc.add(new Paragraph(reportDescription)); 
			doc.close();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  


	}
}