package controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Job;
import models.PC;
import models.User;
import views.IErrorMessage;

public class JobController {
	private Job job;

	//Method controller untuk validasi dari logika add new job
	public boolean addNewJob(IErrorMessage error, String userID, String pcID) throws SQLException{
		
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

	//Method controller untuk validasi pada logika update job status
	public boolean updateJobStatus(int jobID, String jobStatus, String pcID) throws SQLException{
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
	
	public void completeJobStatus(String jobID, String jobStatus) throws SQLException{
		try {
			job.completeJobStatus(jobID, jobStatus);
			System.out.println("Job Status Updated Succesfully");
		} catch (Exception e) {
			System.out.println("Error Updating Job Status: " + e.getMessage());
			// TODO: handle exception
		}
	}

	//Metode untuk mengambil data pada getAllJobData dan memberitahu error menggunakan interface
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

	//Metode untuk mencek data TechnicianJob dan memberi feedback mengenai retrieval status
	public ArrayList<Job> getTechnicianJob(int userID) throws SQLException{
		try {
			ArrayList<Job> technicianJobs = Job.getTechnicianJobs(userID);
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

	//Method untuk memeriksa apakah PCid exist
	private boolean checkPCID(String pcID) throws SQLException {
		if(PC.getPCDetail(pcID) == null) {
			return true;
		}else return false;
	}

	//Method untuk memeriksa role user apakah computer technician
	private boolean checkUserRole(String userID) throws SQLException{
		User staff = User.checkStaffRole(userID);
		String Role = staff.getRole();
		if(Role.equals("Computer Technician")) {
			return false;
		}else return true;
	}

	//Method untuk memeriksa apakah staff tersebut exist
	private boolean checkStaff(String userID) throws SQLException{
		User staff = User.checkStaffRole(userID);
		if(staff == null) {
			return true;
		}else return false;
	}

	//method untuk memeriksa status dari PC
	private boolean checkPCStatus(String pcID) throws SQLException{
		PC pc = PC.getPCDetail(pcID);
		if(pc.getPcCondition().equals("Maintenance")) {
			return true;
		}else return false;
	}
}
