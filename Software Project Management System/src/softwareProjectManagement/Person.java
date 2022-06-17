package softwareProjectManagement;

public abstract class Person {
	private int id;
	private String title;
	private String personName;
	private String personSurname;
	private String personPhone;
	
	public Person(int id,String title,String personName,String personSurname,String personPhone) {
		this.id=id;
		this.title=title;
		this.personName=personName;
		this.personSurname=personSurname;
		this.personPhone=personPhone;
		
	}

	public String getTitle() {
		return title;
	}



	public int getId() {
		return id;
	}



	public String getPersonName() {
		return personName;
	}



	public String getPersonSurname() {
		return personSurname;
	}



	public String getPersonPhone() {
		return personPhone;
	}


	
	
}
