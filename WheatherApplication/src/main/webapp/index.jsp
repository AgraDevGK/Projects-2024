<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weather Application</title>
</head>
<body>
<h1> Date and Time= ${date}</h1>
<h1>City= ${city }</h1>
<h1>Temperature= ${temperature }</h1>
<h1>Wheather Condition= ${weatherCondition }</h1>
<h1>Humudity= ${humidity}</h1>
<h1>Wind Speed= ${windSpeed}</h1>
<p>REST OF THE DATA</p>
${weatherData}
</body>
</html>