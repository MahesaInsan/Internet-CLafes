package controllers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;
import models.Job;

public class JobController {
	private Job job;
	
	
	public JobController() {
		this.job = new Job();
	}

	public void addNewJob(String userID, String pcID) {
		
	}
	public void updateJobStatus(String jobID, String jobStatus) throws SQLException{
		try {
			job.updateJobStatus(jobID, jobStatus);
			System.out.println("Job Status Updated Succesfully");
		} catch (Exception e) {
			System.out.println("Error Updating Job Status: " + e.getMessage());
			// TODO: handle exception
		}
	}
	public void getAllJobData() {
		
	}
	public void getPCOnWorkingList(String pcID) {
		
	}
	public ArrayList<Job> getTechnicianJob(int userID) throws SQLException{
		try {
			ArrayList<Job> technicianJobs = job.getTechnicianJobs(userID);
			if(technicianJobs.isEmpty()) {
				System.out.println("No Jobs Assigned to the Technician yet");
			}
			return technicianJobs;
		} catch (Exception e) {
			System.out.println("" + e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
		
	}
}
