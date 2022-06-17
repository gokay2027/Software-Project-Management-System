package softwareProjectManagement;

public class Manager extends Person {
	
	private int salary;
	
	public Manager(int id,String title, String personName, String personSurname, String personPhone, int salary) {
		super(id,title, personName, personSurname, personPhone);
		this.salary= salary;
		// TODO Auto-generated constructor stub
	}

	public int getSalary() {
		return salary;
	}




	
	
	
}