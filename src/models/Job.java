package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Connect;

public class Job {
	private int jobID;
	private int userID;
	private int pcID;
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

	public int getPcID() {
		return pcID;
	}

	public void setPcID(int pcID) {
		this.pcID = pcID;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public void addNewJob(String userID, String pcID) {
		
	}
	
	public void updateJobStatus(String jobID, String jobStatus)throws SQLException {
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
	
	public ArrayList<Job> getTechnicianJobs(int userID) throws SQLException {
        ArrayList<Job> technicianJobs = new ArrayList<>();
        try {
            Connect db = Connect.getConnection();
            String query = "SELECT * FROM job WHERE userID = ?";
            try (PreparedStatement ps = db.prepareStatement(query)) {
                ps.setInt(1, userID);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Job job = new Job();
                        job.setJobID(rs.getInt("jobID"));
                        job.setUserID(rs.getInt("userID"));
                        job.setPcID(rs.getInt("pcID"));
                        job.setJobStatus(rs.getString("jobStatus"));

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
