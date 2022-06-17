package softwareProjectManagement;

import java.util.ArrayList;

public class Team {
	private int teamId;
	private ArrayList<ITWorker> members;

	public Team(int teamId,ArrayList<ITWorker> members) {
		this.members = members;
		this.teamId=teamId;
	}







	public ArrayList<ITWorker> getMembers() {
		return members;
	}



}
