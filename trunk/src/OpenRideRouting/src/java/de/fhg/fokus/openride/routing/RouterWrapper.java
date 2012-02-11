package de.fhg.fokus.openride.routing;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mapsforge.core.GeoCoordinate;
import org.mapsforge.server.routing.IEdge;
import org.mapsforge.server.routing.IRouter;
import org.mapsforge.server.routing.IVertex;
import org.mapsforge.server.routing.highwayHierarchies.RouterImpl;

/**
 *
 * @author fvi
 */
public class RouterWrapper {

    private static final Logger logger = Logger.getLogger(RouterWrapper.class.getName());

    private static final File F_ROUTING_GRAPH = new File("routingGraph.hh").getAbsoluteFile();
    private static final long FILE_SYSTEM_CHECK_INTERVALL = 30000;
    private static RouterWrapper instance = null;

    private volatile IRouter router;
    private volatile long lastModified;

    public static RouterWrapper getInstance() {
        synchronized(RouterWrapper.class) {
            if(instance == null) {
                instance = new RouterWrapper();
            }
        }
        return instance;
    }

    private RouterWrapper() {
        loadRouter();

        // check for changes of the routing graph file (and reload if changed)
        Thread listenerThread = new Thread(){
            @Override
            public void run() {
                // polling loop
                while(true) {
                    try {
                        Thread.sleep(FILE_SYSTEM_CHECK_INTERVALL);
                    } catch (InterruptedException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                    if(F_ROUTING_GRAPH.exists() && lastModified != F_ROUTING_GRAPH.lastModified()) {
                        loadRouter();
                    }
                }
            }
        };
        listenerThread.start();
    }

    private synchronized boolean loadRouter() {
        logger.info("load routingGraph '" + F_ROUTING_GRAPH.getAbsolutePath() + "' ...");
        try {
            FileInputStream fis = new FileInputStream(F_ROUTING_GRAPH);
            IRouter tmp = RouterImpl.deserialize(fis);
            fis.close();

            this.router = tmp;
            this.lastModified = F_ROUTING_GRAPH.lastModified();
            logger.info("load routingGraph successfull!");
            return true;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        logger.info("load routingGraph not successfull!");
        return false;
    }
    
    public GeoCoordinate[] getMapCoordinates(GeoCoordinate[] coords) {
        // we need to use it as a variable since it might
        //be that the router gets reloaded from file system during computation.
        IRouter myRouter = this.router;
        if(coords != null && myRouter != null) {
            IVertex[] vertices = getVertices(myRouter, coords);
            GeoCoordinate[] result = new GeoCoordinate[vertices.length];
            for(int i=0;i<result.length;i++) {
                result[i] = vertices[i].getCoordinate();
            }
            return result;
        }
        return null;
    }

    public LinkedList<GeoCoordinate> getShortestPath(GeoCoordinate[] coords) {
        // we need to use it as a variable since it might
        //be that the router gets reloaded from file system during computation.
        IRouter myRouter = this.router;
        if(coords != null && myRouter != null) {
            IVertex[] vertices = getVertices(myRouter, coords);
            LinkedList<IEdge> route = getRoute(myRouter, vertices);
            if(route == null || vertices == null) {
                return null;
            }
            LinkedList<GeoCoordinate> result = new LinkedList<GeoCoordinate>();
            if(route.size() == 0 && vertices.length > 0) {
                //source == target
                result.add(vertices[0].getCoordinate());
            } else {
                for(IEdge e : route) {
                    int start;
                    if(e.equals(route.getFirst())) {
                        start = 0;
                    } else {
                        start = 1;
                    }
                    GeoCoordinate[] waypoints = e.getAllWaypoints();
                    for(int i=start;i<waypoints.length;i++) {
                        result.add(waypoints[i]);
                    }
                }
            }
            return result;
        } else if(myRouter == null) {
            String msg = "Could not compute shortest path."
                + "Probably the router could not be loaded from file system.";
            logger.info(msg);
        }
        return null;
    }

    public void getShortestPathAndTravelTime(GeoCoordinate[] coords, LinkedList<GeoCoordinate> buffCoords, LinkedList<Integer> buffTime) {
        // we need to use it as a variable since it might
        //be that the router gets reloaded from file system during computation.
        IRouter myRouter = this.router;
        if(coords != null && myRouter != null) {
            IVertex[] vertices = getVertices(myRouter, coords);
            LinkedList<IEdge> route = getRoute(myRouter, vertices);
            if(route == null) {
                return;
            }
            if(route.size() == 0 && vertices.length > 0) {
                //source == target
                buffCoords.add(vertices[0].getCoordinate());
                buffTime.add(0);
            } else {
                double timeOffset = 0d;
                for(IEdge e : route) {
                    GeoCoordinate[] waypoints = e.getAllWaypoints();
                    double edgeLength = getEdgeLength(waypoints);
                    if(e.equals(route.getFirst())) {
                        buffCoords.addLast(waypoints[0]);
                        buffTime.addLast((int)timeOffset);
                    }
                    for(int i=1;i<waypoints.length;i++) {
                        double sectionLength = GeoCoordinate.sphericalDistance(
                            waypoints[i-1].getLongitude(),
                            waypoints[i-1].getLatitude(),
                            waypoints[i].getLongitude(),
                            waypoints[i].getLatitude()
                        );
                        double sectionTime = (sectionLength / edgeLength) * ((double)e.getWeight());
                        timeOffset += (sectionTime * 100);
                        buffCoords.addLast(waypoints[i]);
                        buffTime.addLast((int)timeOffset);
                    }
                }
            }
        } else if(myRouter == null) {
            String msg = "Could not compute shortest path."
                + "Probably the router could not be loaded from file system.";
            logger.info(msg);
        }
    }
    
    private static double getEdgeLength(GeoCoordinate[] waypoints) {
        double length = 0;
        for(int i=1;i<waypoints.length;i++) {
            length += GeoCoordinate.sphericalDistance(
                    waypoints[i-1].getLongitude(),
                    waypoints[i-1].getLatitude(),
                    waypoints[i].getLongitude(),
                    waypoints[i].getLatitude()
            );
        }
        return length;
    }

    private static LinkedList<IEdge> getRoute(IRouter router, IVertex[] vertices) {
        LinkedList<IEdge> route = new LinkedList<IEdge>();
        for(int i=1;i<vertices.length;i++) {
            IEdge[] edges = router.getShortestPath(vertices[i-1].getId(), vertices[i].getId());
            if(route == null) {
                return null;
            }
            for(int j=0;j<edges.length;j++) {
                route.addLast(edges[j]);
            }
        }
        return route;
    }

    private static IVertex[] getVertices(IRouter router, GeoCoordinate[] coords) {
        if(coords == null) {
            return new IVertex[0];
        }
        IVertex[] vertices = new IVertex[coords.length];
        for(int i=0;i<coords.length;i++) {
            vertices[i] = router.getNearestVertex(coords[i]);
        }
        return vertices;
    }

}
