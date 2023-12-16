package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class Job {
	private String jobID;
	private String userID;
	private String pcID;
	private String jobStatus;

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPcID() {
		return pcID;
	}

	public void setPcID(String pcID) {
		this.pcID = pcID;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Job(String jobID, String userID, String pcID, String jobStatus) {
		super();
		this.jobID = jobID;
		this.userID = userID;
		this.pcID = pcID;
		this.jobStatus = jobStatus;
	}

	public static void addNewJob(String userID, String pcID) throws SQLException{
		Connect db = Connect.getConnection();
		int id = Integer.parseInt(userID);
		PreparedStatement ps = db.prepareStatement("INSERT INTO job (staffId, pcId, status) VALUES (?, ?, ?)");
		ps.setInt(1, id);
		ps.setString(2, pcID);
		ps.setString(3, "UnComplete");
		ps.executeUpdate();
	}
	
	public static void updateJobStatus(String jobID, String jobStatus) throws SQLException{
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("UPDATE job SET status = ? WHERE id = ?");
		ps.setString(1, jobStatus);
		ps.setString(2, jobID);
		ps.executeUpdate();
	}
	
	public void getPCOnWorkingList(String pcID) {
		
	}
	
	public void getTechnicianJob(String userID) {
		
	}
	
	public static ArrayList<Job> getAllJobData() throws SQLException{
		ArrayList<Job> jobList = new ArrayList<Job>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("Select * FROM job");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			jobList.add(new Job(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		
		return jobList;
		
	}
}
