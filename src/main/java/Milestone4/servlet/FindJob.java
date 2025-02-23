package Milestone4.servlet;

import Milestone4.dal.*;
import Milestone4.model.*;
import Milestone4.model.Character;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/findjob")
public class FindJob extends HttpServlet{
	protected JobRecordDao jobRecordDao;
	protected CharacterDao characterDao;
	protected JobDao jobDao;
	
	@Override
	public void init() throws ServletException {
		jobRecordDao = JobRecordDao.getInstance();
		jobDao = JobDao.getInstance();
		characterDao = CharacterDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Map<String, String> messages = new HashMap<String, String>();
		Map<String, String> results = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        
        String characterID = req.getParameter("characterId");
        if(characterID == null || characterID.trim().isEmpty()) {
        	messages.put("title", "Invalid character ID.");
        } else {
        	try {
        	
	        Character character =  characterDao.getCharacterByID(Integer.parseInt(characterID));
	        messages.put("title", "Jobs for character " + character.getFirstName() + " " + character.getLastName());
	       
	        List<Integer> jobIDs = new ArrayList<Integer>();
	        jobIDs = jobRecordDao.getJobRecordByCharacterID(characterID);
	        
	        List<JobRecord> jobRecordList = new ArrayList<JobRecord>();
	        Job job = null;
	        JobRecord jobRecord = null;
	        
	        for (Integer jobID : jobIDs) {
	        	 job = jobDao.getJobByJobID(jobID);
	 	         jobRecord = jobRecordDao.getJobRecordByCharacterIDAndJobID(character, job);
	 	         jobRecordList.add(jobRecord);
	        }
	        req.setAttribute("jobRecords", jobRecordList);
	        
        } catch  (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        }
        req.getRequestDispatcher("/FindJob.jsp").forward(req, resp);
	}
}