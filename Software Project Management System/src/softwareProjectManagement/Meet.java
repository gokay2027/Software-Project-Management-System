package softwareProjectManagement;

public class Meet {
	private int meetingId;
	private String name;
	private String description;
	private String meetingTime;
	private int teamID;
	public Meet(int meetingId, int teamID,String name, String description, String meetingTime) {
		this.meetingId = meetingId;
		this.teamID=teamID;
		this.name = name;
		this.description = description;
		this.meetingTime = meetingTime;

	}


	public int getMeetingId() {
		return meetingId;
	}






	public String getMeetingTime() {
		return meetingTime;
	}



	public String getName() {
		return name;
	}



	public String getDescription() {
		return description;
	}



}
