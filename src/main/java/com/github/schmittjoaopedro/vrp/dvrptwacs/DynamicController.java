package com.github.schmittjoaopedro.vrp.dvrptwacs;

import java.util.ArrayList;

public class DynamicController {

    // length of a working day in seconds
    public int workingDay = 100;

    //number of time slices
    public int noTimeSlices = 50;

    //dynamic level, which gives the proportion of the dynamic requests (available time > 0) from the DVRPTW instance
    public double dynamicLevel = 0.1;  //0.0  //0.1  //0.5  //1.0

    public double scalingValue;

    public int idLastAvailableNode = 0;

    //it indicates that node at position/index i is committed if the value at position i is true; the
    //depot node is considered to be committed by default and it's not included in this array
    public boolean[] committedNodes;

    private LoggerOutput loggerOutput;

    public DynamicController(LoggerOutput loggerOutput) {
        this.loggerOutput = loggerOutput;
    }

    public double getScalingValue() {
        return scalingValue;
    }

    public void setScalingValue(double scalingValue) {
        this.scalingValue = scalingValue;
    }

    public int getIdLastAvailableNode() {
        return idLastAvailableNode;
    }

    public void setIdLastAvailableNode(int idLastAvailableNode) {
        this.idLastAvailableNode = idLastAvailableNode;
    }

    public void allocateStructures(VRPTW vrptw) {
        committedNodes = new boolean[vrptw.n];
        for (int i = 0; i < vrptw.n; i++) {
            committedNodes[i] = false;
        }
    }

    //get a list of new available (known) nodes at the given time moment
    public ArrayList<Integer> countNoAvailableNodes(ArrayList<Request> dynamicRequests, double time) {
        int i, id;
        int pos = getIdLastAvailableNode();
        ArrayList<Integer> nodesList = new ArrayList<Integer>();
        for (i = pos; i < dynamicRequests.size(); i++) {
            if (time >= dynamicRequests.get(i).getAvailableTime()) {
                id = dynamicRequests.get(i).getId() - 1;
                nodesList.add(id);
            } else {
                break;
            }
        }
        setIdLastAvailableNode(i);
        return nodesList;
    }

    //get the position of the last committed node in the tour designated by indexTour from the best so far ant
    public int getLastCommittedPos(Ant bestSoFarAnt, int indexTour) {
        int pos = 0;
        int node;
        int tourLength;
        if (indexTour < bestSoFarAnt.usedVehicles) {
            tourLength = bestSoFarAnt.tours.get(indexTour).size();
            for (int i = 1; i < tourLength - 1; i++) {
                node = bestSoFarAnt.tours.get(indexTour).get(i);
                if (committedNodes[node]) {
                    pos++;
                } else {
                    break;
                }
            }
        }
        return pos;
    }

    //check if there are any nodes in the tours of the best so far solution that should be marked as committed
    public boolean checkNewCommittedNodes(Ant bestSoFarAnt, int indexTimeSlice, double lengthTimeSlice) {
        boolean result = false;
        int indexTour = 0;
        int tourLength = 0;
        int node, startPos, count = 0;
        while (indexTour < bestSoFarAnt.usedVehicles) {
            if (count >= 50) {
                loggerOutput.log("Index tour=" + indexTour + ", used vehicles=" + bestSoFarAnt.usedVehicles + ", tour length=" + tourLength);
            }
            //skip for already committed nodes
            tourLength = bestSoFarAnt.tours.get(indexTour).size();
            startPos = getLastCommittedPos(bestSoFarAnt, indexTour);
            for (int i = startPos + 1; i < tourLength - 1; i++) {
                node = bestSoFarAnt.tours.get(indexTour).get(i);
                //check condition for a node to be committed
                if (bestSoFarAnt.beginService[node + 1] <= indexTimeSlice * lengthTimeSlice) {
                    if (!committedNodes[node]) {
                        return true;
                    } else {
                        continue;
                    }
                } else {
                    indexTour++;
                    break;
                }
            }
            //if all the nodes from this tour are committed, move to the next tour for checking if it
            //contains nodes that must be committed
            if (indexTour < bestSoFarAnt.usedVehicles) {
                startPos = getLastCommittedPos(bestSoFarAnt, indexTour);
                tourLength = bestSoFarAnt.tours.get(indexTour).size();
                if (startPos == tourLength - 2) {
                    indexTour++;
                }
            }
            count++;

        }
        return result;
    }

    //check if there are any committed tours (i.e. tours that contain at least one committed node that
    //should be included in the ant's solution)
    public boolean checkCommittedTours(Ant bestSoFarAnt) {
        int lastPos;
        ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
        for (int index = 0; index < bestSoFarAnt.usedVehicles; index++) {
            lastPos = getLastCommittedPos(bestSoFarAnt, index);
            lastCommittedIndexes.add(lastPos);
        }
        for (int index : lastCommittedIndexes) {
            if (index > 0) {
                return true;
            }
        }
        return false;
    }

    //commit nodes from the tours of the best so far solution, that will have their position fixed when
    //they will be copied in the ants'solutions
    // block part of the best solution that is being/has been visited
    public void commitNodes(Ant bestSoFarAnt, int indexTimeSlice, double lengthTimeSlice) {
        int indexTour = 0;
        int tourLength;
        int node, startPos, count = 0;
        while (indexTour < bestSoFarAnt.usedVehicles) {
            //skip for already committed nodes
            tourLength = bestSoFarAnt.tours.get(indexTour).size();
            if (count >= 50) {
                loggerOutput.log("Index tour=" + indexTour + ", used vehicles=" + bestSoFarAnt.usedVehicles + ", tour length=" + tourLength);
            }
            startPos = getLastCommittedPos(bestSoFarAnt, indexTour);
            for (int i = startPos + 1; i < tourLength - 1; i++) {
                node = bestSoFarAnt.tours.get(indexTour).get(i);
                //check condition for a node to be committed
                if ((bestSoFarAnt.beginService[node + 1] <= indexTimeSlice * lengthTimeSlice) &&
                        (!committedNodes[node])) {
                    committedNodes[node] = true;
                } else {
                    indexTour++;
                    break;
                }
            }
            //if all the nodes from this tour were committed (the depot from the start and
            //end of a tour are assumed to be committed by default), move to the next tour
            if (indexTour < bestSoFarAnt.usedVehicles) {
                startPos = getLastCommittedPos(bestSoFarAnt, indexTour);
                tourLength = bestSoFarAnt.tours.get(indexTour).size();
                if (startPos == tourLength - 2) {
                    indexTour++;
                }
            }
            count++;
        }
    }

    //add to the ant's solution the committed nodes from each tour of the best so far solution
    public void addCommittedNodes(Ant bestSoFarAnt, Ant ant, VRPTW vrptw) {
        int index, city, currentCity;
        double distance, arrivalTime, beginService;
        ArrayList<Request> reqList = vrptw.getRequests();
        int startIndex = 1, pos;
        ArrayList<Integer> lastCommittedIndexes = new ArrayList<>();
        for (int indexTour = 0; indexTour < bestSoFarAnt.usedVehicles; indexTour++) {
            pos = getLastCommittedPos(bestSoFarAnt, indexTour);
            lastCommittedIndexes.add(pos);
        }
        for (int i = 0; i < lastCommittedIndexes.size(); i++) {
            //we have at least one committed node in the i-th tour (i.e. tour with index i)
            index = lastCommittedIndexes.get(i);
            if (index > 0) {
                //if the number of vehicles (tours) from the ant solution is less than the index of the
                //tour from the best so far solution, add new (empty) tours in the ant's solution
                if (ant.usedVehicles < (i + 1)) {
                    ant.usedVehicles = i + 1;
                    for (int l = startIndex; l < ant.usedVehicles; l++) {
                        ant.tours.add(l, new ArrayList<>());
                        ant.tours.get(l).add(-1);
                        ant.tourLengths.add(l, 0.0);
                        ant.currentQuantity.add(l, 0.0);
                        ant.currentTime.add(l, 0.0);
                    }
                    startIndex = i + 1;
                }
                int lastPos = ant.tours.get(i).size() - 1;
                currentCity = ant.tours.get(i).get(lastPos);
                currentCity++;
                //add in the ant's i-th tour all the committed nodes from the i-th tour of the best so far solution
                for (int j = 1; j <= index; j++) {
                    city = bestSoFarAnt.tours.get(i).get(j);
                    distance = vrptw.instance.distance[currentCity][city + 1];
                    arrivalTime = ant.currentTime.get(i) + reqList.get(currentCity).getServiceTime() + distance;
                    beginService = Math.max(arrivalTime, reqList.get(city + 1).getStartWindow());
                    if (beginService > reqList.get(city + 1).getEndWindow()) {
                        loggerOutput.log("Method addCommittedNodes: solution infeasible..");
                    }
                    //add committed node to the ant's tour
                    ant.tours.get(i).add(j, city);
                    ant.visited[city] = true;
                    ant.toVisit--;
                    ant.currentTime.set(i, beginService);
                    ant.beginService[city + 1] = beginService;
                    ant.currentQuantity.set(i, ant.currentQuantity.get(i) + reqList.get(city + 1).getDemand());
                    currentCity = city + 1;
                }
            }
        }
    }

    public void calculateScaleValue(VRPTW vrptw) {
        //compute the scaling value with which we can scale all time-related values
        Request depotReq = vrptw.getRequests().get(0);
        scalingValue = (double) workingDay / (depotReq.getEndWindow() - depotReq.getStartWindow());
    }

    public void scaleRequestTimes(VRPTW vrptw) {
        double newStartWindow, newEndWindow, newServiceTime, newAvailableTime;
        //adjust for each request, all the time related values according to the length of the working day we are simulating
        if (scalingValue != 0) {
            loggerOutput.log("Scalling value = " + scalingValue);
            for (Request req : vrptw.getRequests()) {
                newStartWindow = req.getStartWindow() * scalingValue;
                req.setStartWindow(newStartWindow);
                newEndWindow = req.getEndWindow() * scalingValue;
                req.setEndWindow(newEndWindow);
                newServiceTime = req.getServiceTime() * scalingValue;
                req.setServiceTime(newServiceTime);
                newAvailableTime = req.getAvailableTime() * scalingValue;
                req.setAvailableTime(newAvailableTime);
            }
        }
    }

}
