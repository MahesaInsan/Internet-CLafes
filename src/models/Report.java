package models;

import main.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Report {
    private int reportID;
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

    public int getReportID() {
		return reportID;
	}

	public void setReportID(int reportID) {
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
            String query = "SELECT r.id, u.role, r.pcId, r.reportNote, r.reportDate FROM report r INNER JOIN Users u ON r.userId = u.id";
            try (PreparedStatement ps = db.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Report report = new Report();
                    report.setReportID(rs.getInt(1));
                    report.setUserRole(rs.getString(2));
                    report.setPcID(rs.getString(3));
                    report.setReportNote(rs.getString(4));
                    report.setReportDate(rs.getString(5));

                    reports.add(report);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }
}
