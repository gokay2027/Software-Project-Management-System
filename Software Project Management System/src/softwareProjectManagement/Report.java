package softwareProjectManagement;

import softwareProjectManagement.Project.Task;

public class Report {

	private ITWorker owner;
	private String description;
	private int taskid;

	public Report(ITWorker owner, String description, int taskid) {

		this.taskid = taskid;
		this.owner = owner;
		this.description = description;

	}




	public String getDescription() {
		return description;
	}



}
