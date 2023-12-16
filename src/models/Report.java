package models;

import main.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Report {
    private String reportID;
    private String userRole;
    private String pcID;
    private String reportNote;
    private String reportDate;

    // Getter and setter methods
    
    public void addNewReport(int userId, String pcId, String reportNote, String userRole) throws SQLException {
        try {
            if ("Customer".equals(userRole) || "Operator".equals(userRole)) {
                Connect db = Connect.getConnection();
                String query = "INSERT INTO Report (userId, pcId, reportNote) VALUES (?, ?, ?)";
                try (PreparedStatement ps = db.prepareStatement(query)) {
                    ps.setInt(1, userId);
                    ps.setString(2, pcId);
                    ps.setString(3, reportNote);

                    ps.executeUpdate();
                }
                System.out.println("Report added successfully");
            } else {
                System.out.println("User with role " + userRole + " is not allowed to make a report.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getPcID() {
		return pcID;
	}

	public void setPcID(String pcID) {
		this.pcID = pcID;
	}

	public String getReportNote() {
		return reportNote;
	}

	public void setReportNote(String reportNote) {
		this.reportNote = reportNote;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public ArrayList<Report> getAllReportData() throws SQLException {
        ArrayList<Report> reports = new ArrayList<>();
        try {
            Connect db = Connect.getConnection();
            String query = "SELECT * FROM report";
            try (PreparedStatement ps = db.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Report report = new Report();
                    report.setReportID(rs.getString("report_id"));
                    report.setUserRole(rs.getString("user_role"));
                    report.setPcID(rs.getString("pc_id"));
                    report.setReportNote(rs.getString("report_note"));
                    report.setReportDate(rs.getString("report_date"));

                    reports.add(report);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }
}
