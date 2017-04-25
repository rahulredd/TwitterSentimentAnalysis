package com.spatial.challenge;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProcessGrid
 */
@WebServlet("/ProcessGrid")
public class ProcessGrid extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String input = "/Users/Rahul/Documents/workspace/Spatial/test.csv";
	private static HashMap<Integer, Integer> sentimentCount = new HashMap<>();
	private static HashMap<Integer, Set<String>> box = new HashMap<>();
	/**
	 * Default constructor. 
	 */
	public ProcessGrid() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<List<String>> coordinates = new ArrayList<>();
		for (int i = 0; i < 180; i++) {
			List<String> list = new ArrayList<>();
			String data[] = request.getParameterValues("json["+ i +"][]");
			list.add(data[0]);
			list.add(data[1]);
			list.add(data[2]);
			list.add(data[3]);
			coordinates.add(list);
		}
		System.out.println("Total number of grids " + coordinates.size());
		long startTime = System.currentTimeMillis();
		GenerateSentiment.init();
		ProcessCsv.csvToMap(input, sentimentCount, coordinates, box);
		int index  = RetrieveResults.runSentiment(sentimentCount, box);
		
		System.out.println("Area with maximum average sentiment " + coordinates.get(index));
		System.out.println(box.get(index).size() + " " +  sentimentCount.get(index) + "  "+ box.get(index));
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		
		doGet(request, response);
	}

}
