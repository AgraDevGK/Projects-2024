package mypackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
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
//		API Setup 
		String ApiKey="e540944fa0177955d05bf1f0a988b15f";
//		taking city name from the user(API Integration)
		String CityName=request.getParameter("CityName");
//		Creating url from the OpenWheatherAPI(Data Received in Jason format)
		String apiUrl="https://api.openweathermap.org/data/2.5/weather?q="+CityName+"&appid="+ApiKey;
//		API Integration
		 try {
	            URL url = new URL(apiUrl);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");

	                InputStream inputStream = connection.getInputStream();
	                InputStreamReader reader = new InputStreamReader(inputStream);
	               // System.out.println(reader);
	                
	                Scanner scanner = new Scanner(reader);
	                StringBuilder responseContent = new StringBuilder();

	                while (scanner.hasNext()) {
	                    responseContent.append(scanner.nextLine());
	                }
	                
	               // System.out.println(responseContent);
	                scanner.close();
	                
	                // Parse the JSON response to extract temperature, date, and humidity
	                Gson gson = new Gson();
	                JsonObject jsonObject = gson.fromJson(responseContent.toString(), JsonObject.class);
	                
	                //Date & Time
	                long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000;
	                String date = new Date(dateTimestamp).toString();
	                
	                //Temperature
	                double temperatureKelvin = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
	                int temperatureCelsius = (int) (temperatureKelvin - 273.15);
	               
	                //Humidity
	                int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
	                
	                //Wind Speed
	                double windSpeed = jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
	                
	                //Weather Condition
	                String weatherCondition = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
	                
	                // Set the data as request attributes (for sending to the jsp page)
	                request.setAttribute("date", date);
	                request.setAttribute("city", CityName);
	                request.setAttribute("temperature", temperatureCelsius);
	                request.setAttribute("weatherCondition", weatherCondition); 
	                request.setAttribute("humidity", humidity);    
	                request.setAttribute("windSpeed", windSpeed);
	                request.setAttribute("weatherData", responseContent.toString());
	                
	                connection.disconnect();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        // Forward the request to the weather.jsp page for rendering
	        request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}

}
