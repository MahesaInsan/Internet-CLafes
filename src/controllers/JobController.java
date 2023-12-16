package controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Job;
import models.PC;
import models.User;
import views.IErrorMessage;

public class JobController {
	public boolean addNewJob(IErrorMessage error, String userID, String pcID) throws SQLException{
		if(userID.equals("")) {
			error.displayErrorMessage("All fields must be filled");
			return false;
		}
		
		if(pcID.equals("")) {
			error.displayErrorMessage("All fields must be filled");
			return false;
		}
		
		if(checkStaff(userID)) {
			error.displayErrorMessage("Staff didn't exist!");
			return false;
		}
		
		if(checkUserRole(userID)) {
			error.displayErrorMessage("Staff role is not Computer Technician!");
			return false;
		}
		
		if(checkPCID(pcID)) {
			error.displayErrorMessage("PC ID is not exist!");
			return false;
		}
		
		if(checkPCStatus(pcID)) {
			error.displayErrorMessage("There is already technician doing this job!");
			return false;
		}
		
		Job.addNewJob(userID, pcID);
		PC.updatePCCondition(pcID, "Maintenance");
		return true;
	}
	public boolean updateJobStatus(String jobID, String jobStatus, String pcID) throws SQLException{
		if(jobStatus.equals("Complete")) {
			Job.updateJobStatus(jobID, jobStatus);
			PC.updatePCCondition(pcID, "Usable");
			return true;
		}
		
		if(jobStatus.equals("UnComplete")) {
			Job.updateJobStatus(jobID, jobStatus);
			PC.updatePCCondition(pcID, "Maintenance");
			return true;
		}
		
		return false;
	}
	public ArrayList<Job> getAllJobData(IErrorMessage error) {
		try {
			return Job.getAllJobData();
		} catch (Exception e) {
			error.displayErrorMessage("Error fetching job data");
			e.printStackTrace();
			return null;
		}
	}
	public void getPCOnWorkingList(String pcID) {
		
	}
	public void getTechnicianJob(String userID) {
		
	}
	
	private boolean checkPCID(String pcID) throws SQLException {
		if(PC.getPCDetail(pcID) == null) {
			return true;
		}else return false;
	}
	
	private boolean checkUserRole(String userID) throws SQLException{
		User staff = User.checkStaffRole(userID);
		String Role = staff.getRole();
		if(Role.equals("Computer Technician")) {
			return false;
		}else return true;
	}
	
	private boolean checkStaff(String userID) throws SQLException{
		User staff = User.checkStaffRole(userID);
		if(staff == null) {
			return true;
		}else return false;
	}
	
	private boolean checkPCStatus(String pcID) throws SQLException{
		PC pc = PC.getPCDetail(pcID);
		if(pc.getPcCondition().equals("Maintenance")) {
			return true;
		}else return false;
	}
}
