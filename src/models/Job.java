package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class Job {
	private int jobID;
	private int userID;
	private String pcID;
	private String jobStatus;

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
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

	public Job(int jobID, int userID, String pcID, String jobStatus) {
		super();
		this.jobID = jobID;
		this.userID = userID;
		this.pcID = pcID;
		this.jobStatus = jobStatus;
	}
	
	public Job() {
		
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
	
	public static void updateJobStatus(int jobID, String jobStatus) throws SQLException{
		Connect db = Connect.getConnection();
		PreparedStatement ps = db.prepareStatement("UPDATE job SET status = ? WHERE id = ?");
		ps.setString(1, jobStatus);
		ps.setInt(2, jobID);
		ps.executeUpdate();
	}
	
	public void completeJobStatus(String jobID, String jobStatus)throws SQLException {
		try {
			Connect db = Connect.getConnection();
			String query = "UPDATE job SET job_status = ? WHERE job_id = ?";
			try(PreparedStatement ps = db.prepareStatement(query)){
				ps.setString(1, jobStatus);
				ps.setString(2, jobID);
				
				ps.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
	}
	
	public void getPCOnWorkingList(String pcID) {
		
	}
		
	public static ArrayList<Job> getAllJobData() throws SQLException{
		ArrayList<Job> jobList = new ArrayList<Job>();
		Connect db = Connect.getConnection();
		
		PreparedStatement ps = db.prepareStatement("Select * FROM job");
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			jobList.add(new Job(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
		}
		
		return jobList;
		
	}
	public static ArrayList<Job> getTechnicianJobs(int userID) throws SQLException {
        ArrayList<Job> technicianJobs = new ArrayList<>();
        try {
            Connect db = Connect.getConnection();
            String query = "SELECT * FROM job WHERE staffId = ?";
            try (PreparedStatement ps = db.prepareStatement(query)) {
                ps.setInt(1, userID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Job job = new Job();
                        job.setJobID(rs.getInt("id"));
                        job.setUserID(rs.getInt("staffId"));
                        job.setPcID(rs.getString("pcId"));
                        job.setJobStatus(rs.getString("status"));

                        technicianJobs.add(job);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return technicianJobs;
    }
}
