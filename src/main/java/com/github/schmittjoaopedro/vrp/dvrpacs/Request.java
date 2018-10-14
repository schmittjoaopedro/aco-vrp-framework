package com.github.schmittjoaopedro.vrp.dvrpacs;

/**
 * Models client requests that contains certain information. For e.g. demand, coordinates, startTime, endTime, serviceTime, etc...
 * It implements comparable to allow native sorting using ArrayList methods (using the natural order from objects).
 * <p>
 * Original version: https://github.com/ralucanecula/DVRPTW_ACS
 */
public class Request implements Comparable<Request> {

    // Id of the client request
    private int id;

    private int xCoordinate;

    private int yCoordinate;

    // Demand (delivery quantity) of the request
    private int demand;

    // Ready time of the request; Earliest arrival time/lower limit/beginning of the time window
    private double startWindow;

    // Due date of the request; Latest arrival time/upper bound/end of the time window
    private double endWindow;

    /**
     * Service time/duration (necessary for unloading/loading of the goods) corresponding to the client request.
     * Service time involves pickup and/or delivery of goods or services for s_i units of time.
     * A service time s_i is associated with each customer. It represents the time required to service him/her or the
     * time spend by the vehicle at the customer once it arrived at its location before moving/traveling to the next customer.
     */
    private double serviceTime;

    /**
     * It represents the moment in time when the customer request is known to the system and becomes
     * available as node in the optimization task of ACS as part of the DVRPTW instance being solved.
     * Basically, for dynamic instances, it represents the time moment that the request will enter in the problem.
     */
    private double availableTime;

    public Request(int id, int xCoordinate, int yCoordinate, int demand, int startWindow, int endWindow, int serviceTime, int availableTime) {
        setId(id);
        setxCoordinate(xCoordinate);
        setyCoordinate(yCoordinate);
        setDemand(demand);
        setStartWindow(startWindow);
        setEndWindow(endWindow);
        setServiceTime(serviceTime);
        setAvailableTime(availableTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public double getStartWindow() {
        return startWindow;
    }

    public void setStartWindow(double startWindow) {
        this.startWindow = startWindow;
    }

    public double getEndWindow() {
        return endWindow;
    }

    public void setEndWindow(double endWindow) {
        this.endWindow = endWindow;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public double getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(double availableTime) {
        this.availableTime = availableTime;
    }

    @Override
    public int compareTo(Request comparable) {
        Double thisTime = getAvailableTime();
        Double comparableTime = comparable.getAvailableTime();
        return thisTime.compareTo(comparableTime);
    }
}
