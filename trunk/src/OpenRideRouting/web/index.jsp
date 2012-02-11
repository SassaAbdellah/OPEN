<%-- 
    Document   : index
    Created on : 02.08.2010, 13:34:49
    Author     : fvi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<style>
        body{ width: 500px;
            margin-left: auto;
            margin-right: auto;
        }
</style>

<html>
  <head>
    <title>OpenRideRouting</title>
  </head>
  <body>
      <h2>Getting Started - Routing</h2>
      The routing functionality is realized as servelts based on the mapsforge.org highway hierarchies implementation.
      There exists two servlets, 'ShortestPath' and 'MapCoordinate'. The Shortest path servlet computes
      routes and returns coordinates, the distance or travel times. The MapCoordinate
      returns the nearest map coordinate for a given coordinate wheras the returned
      coordinate lies on the nearest edge. <br>
      <br>
      For updating map data, the ant build script located at the project root can be used. 
      This script downloads the OpenStreetMap file, extracts a region specified by a polygon, builds the 
      routing graph and at least builds a highway hierarchies binary file.
      In the following, a quick introduction is given.

      <h3>ShortestPath Servlet</h3>

      The servlet requires two parameters, whereas Mode must be equal to one of the three specified strings.
      It is used for switching the result type.<br>
      Parameters :<br>
      <b>coords</b>=lat1,lon1;lat2,lon2;...;latK,lonK<br>
      <b>mode</b>=time : returns a list of 3-tuples. Each line contains: lat,lon,time_millis<br>
      <b>mode</b>=coordinates : return a list of 2-tuples. Each line contains lat,lon.<br>
      <b>mode</b>=distance : returns the distance in meters. Integer.MaxValue if no route is found.<br>
      <br>
      If no route has been found the mode 'distance' returns Integer.MaxValue wheras
      the modes 'time' and 'coordinates' return an empty list. If the source vertex is
      equal to the target vertex, the mode 'distance' returns 0, the modes 'time'
      and 'coordinate' return only a single tuple representing the location of
      source and target (which are equal).<br>
      <br>
      <a href="ShortestPath?mode=coordinates&coords=52.5075125,13.4502837;52.5256779,13.3061963">sample route coordinates</a><br>
      <a href="ShortestPath?mode=time&coords=52.5075125,13.4502837;52.5256779,13.3061963">sample route travel times</a><br>
      <a href="ShortestPath?mode=distance&coords=52.5075125,13.4502837;52.5256779,13.3061963">sample route distance</a><br>

      <h3>MapCoordinate Servlet</h3>
      This servlet requires just a single parameter which represents a k-tuple of coordinates.
      As result a k-tuple of coordinates is returned whereas the i-th coordinate is the nearest
      map coordinate with regard to the i-th input coordinate. The nearest coordinate
      always lies on an edge of the routing graph.<br>
      Parameters :<br>
      <b>coords</b>=lat1,lon1;lat2,lon2;...;latK,lonK<br>
      <br>
      <a href="NearestMapCoordinate?coords=52.5075125,13.4502837;52.5256779,13.3061963">sample</a><br>

      <h3>Updating to new OpenStreetMaps Data</h3>
      Run the 'all' task of the ant script build-routing.xml located at the project root.
      A polygon to extract a region can be specified within the ant script. You can find
      a sample .poly file in 'res/conf/dach.poly'. If the polygon file is changed, you
      might want to change the osm file to be downloaded. Currently this is set to europe,
      within the ant script. After the script is finished, a binary file has been written to
      'data/binary'. This file has to be copied to the routing server. The routing graph binary
      of the routing server has to be replaced with the newly build binary. This change is
      detected by the routing server automagically and reloading of the routing graph is triggered.
      
  </body>
</html>

