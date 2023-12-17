package controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Report;

public class ReportController{
	private Report report;
	
	public ReportController() {
		this.report = new Report();
	}
	
	public void addNewReport(String userRole, String pcID, String reportNote)throws SQLException{
		try {
			report.addNewReport(0, userRole, pcID, reportNote);
			System.out.println("Report added succesfully");
		}catch(SQLException e) {
			System.out.println("Error adding a new report : " + e.getMessage());
		}
	}
	public ArrayList<Report> getAllReportData(){
		try {
			ArrayList<Report> reports = report.getAllReportData();
			if(reports.isEmpty()) {
				System.out.println("No Reports Available");
			}
			return reports;
		} catch (Exception e) {
			System.out.println("Error retrieving reports : " + e.getMessage());
			e.printStackTrace();// TODO: handle exception
			return null;
		}
		
	}
}
