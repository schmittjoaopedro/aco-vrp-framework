package com.github.schmittjoaopedro.vrp.mpdptw.aco;

import com.github.schmittjoaopedro.vrp.mpdptw.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequentialInfeasible implements SolutionBuilder {

    private ProblemInstance instance;

    private Random random;

    private MMAS mmas;

    private List<Solution> antPopulation;

    public final double weight1 = 0.3;

    public final double weight2 = 0.3;

    public final double weight3 = 0.3;

    public final double weight4 = 0.1;

    @Override
    public void init(ProblemInstance instance, Random random, MMAS mmas) {
        this.instance = instance;
        this.random = random;
        this.mmas = mmas;
        this.antPopulation = mmas.getAntPopulation();
    }

    @Override
    public void constructSolutions() {
        int vehicle;
        Request request;
        double vehicleCapacity;
        NextClient nextVisit;
        int currentPosition;
        double departureTimes[] = new double[instance.getNumNodes()];
        double demands[] = new double[instance.getNumNodes()];
        ArrayList<Double> capacity = new ArrayList<>();
        for (Solution ant : antPopulation) {
            SolutionUtils.clearSolution(ant, instance);
        }
        for (int k = 0; k < antPopulation.size(); k++) {// For each ant
            vehicle = 0;
            Solution ant = antPopulation.get(k);
            ant.toVisit--; // The depot is visited
            ant.tours.add(vehicle, new ArrayList<>());
            ant.tours.get(vehicle).add(instance.getDepot().nodeId);
            ant.requests.add(vehicle, new ArrayList<>());
            ant.visited[instance.getDepot().nodeId] = true;
            capacity.add(vehicle, instance.getVehicleCapacity());
            currentPosition = 0;
            while (ant.toVisit > 0) {
                nextVisit = selectNextClient(ant, vehicle, currentPosition, capacity, departureTimes, demands);
                if (nextVisit.nextClient != -1) { // a next visit node was found
                    currentPosition++;
                    ant.tours.get(vehicle).add(currentPosition, nextVisit.nextClient);
                    ant.visited[nextVisit.nextClient] = true;
                    ant.feasible &= nextVisit.feasible; // If in one move is infeasible, the full solution will be infeasible
                    departureTimes[nextVisit.nextClient] = nextVisit.departureTime;
                    demands[nextVisit.nextClient] = nextVisit.demand;
                    ant.toVisit--;
                    request = instance.getRequest(nextVisit.nextClient);
                    if (request.isDeliver) {
                        vehicleCapacity = capacity.get(vehicle) - request.demand; // Delivery demand is negative
                        capacity.set(vehicle, vehicleCapacity);
                    }
                    if (!ant.requests.get(vehicle).contains(request.requestId)) {
                        ant.requests.get(vehicle).add(request.requestId);
                        vehicleCapacity = capacity.get(vehicle) + instance.getDelivery(request.requestId).demand; // Delivery demand is negative
                        capacity.set(vehicle, vehicleCapacity);
                    }
                } else {
                    ant.tours.get(vehicle).add(instance.getDepot().nodeId); // Finish previous route
                    // Starts a new route
                    vehicle++;
                    currentPosition = 0;
                    ant.tours.add(vehicle, new ArrayList<>());
                    ant.tours.get(vehicle).add(instance.getDepot().nodeId);
                    ant.requests.add(vehicle, new ArrayList<>());
                    capacity.add(vehicle, instance.getVehicleCapacity());
                }
            }
            ant.tours.get(vehicle).add(instance.getDepot().nodeId); // Finish last route
        }
        // Calculate fitness
        for (Solution ant : antPopulation) {
            instance.solutionEvaluation(ant);
            if (ant.capacityPenalty > 0) {
                throw new RuntimeException("Invalid capacity penaly!!");
            }
        }
    }

    public NextClient selectNextClient(Solution ant, int vehicle, int currIdx, ArrayList<Double> capacities, double[] departureTimes, double[] demands) {
        Request request;
        int currentCity = ant.tours.get(vehicle).get(currIdx);
        NextClient nextClientHeuristic;
        double sum = 0.0;
        boolean hasRequest, valid;
        ArrayList<NextClient> newRequestProbs = new ArrayList<>();
        ArrayList<NextClient> currRequestProbs = new ArrayList<>();
        ArrayList<NextClient> probs = new ArrayList<>();
        for (int i = 0; i < instance.getNumNodes(); i++) {
            if (!ant.visited[i] && isNextClientSupported(ant, capacities, vehicle, currentCity, i)) {
                // Calculate heuristic value for the next client and its feasibility
                nextClientHeuristic = HEURISTIC(ant, departureTimes, demands, vehicle, currentCity, i);
                // If the vehicle has request, validate if the deliver can be executed after all pickups were made
                request = instance.getRequest(i);
                hasRequest = ant.requests.get(vehicle).contains(request.requestId);
                valid = request.isPickup; // We assume that all pickups requests are valid a-priori
                if (hasRequest && request.isDeliver) { // If the request is a delivery request we must to test if all pickups were made previously
                    valid = true;
                    for (Request req : instance.getPickups(request.requestId)) {
                        if (!ant.visited[req.nodeId]) {
                            valid = false;
                            break;
                        }
                    }
                }
                // If the next client belongs to the current vehicle requests, add to new request probs
                if (hasRequest && valid) {
                    // [nextClient, heuristic, departureTime, demand, feasible, cumulativeCost]
                    currRequestProbs.add(nextClientHeuristic);
                } else if (nextClientHeuristic.feasible && valid) { // Else add to new request probs, but allow only new feasible requests
                    newRequestProbs.add(nextClientHeuristic);
                }
            }
        }

        // Check if there is at least one unfeasible node in the request list
        boolean currReqFeasible = true;
        for (NextClient nextClient : currRequestProbs) {
            if (!nextClient.feasible) { //unfeasible
                currReqFeasible = false;
                break;
            }
        }
        // If are all feasible nodes in current request
        if (currReqFeasible) {
            // Select only feasible moves
            for (NextClient nextClient : currRequestProbs) {
                sum += nextClient.heuristic; //heuristic
                nextClient.cumulativeCost = sum;
                probs.add(nextClient);
            }
            for (NextClient nextClient : newRequestProbs) {
                if (nextClient.feasible) {
                    sum += nextClient.heuristic;
                    nextClient.cumulativeCost = sum;
                    probs.add(nextClient);
                }
            }
        } else {
            for (NextClient nextClient : currRequestProbs) {
                sum += nextClient.heuristic;
                nextClient.cumulativeCost = sum;
                probs.add(nextClient);
            }
        }
        // If this route is done, returns a flag indicating no more clients to add
        if (probs.isEmpty()) {
            NextClient nextClient = new NextClient();
            nextClient.nextClient = -1;
            return nextClient;
        }
        for (NextClient nextClient : probs) {
            nextClient.cumulativeCost = (nextClient.cumulativeCost / sum) * 100.0;
        }
        // Roulette wheel selection
        double rand = random.nextDouble() * 100.0;
        int selected = 0;
        while (selected != probs.size() - 1) {
            if (rand <= probs.get(selected).cumulativeCost) {
                break;
            }
            selected++;
        }
        return probs.get(selected);
    }

    public boolean isNextClientSupported(Solution ant, ArrayList<Double> capacities, int vehicle, int currentCity, int nextCity) {
        boolean supported;
        int requestId = instance.getRequestId(nextCity);
        // Check if the request is already allocated in the vehicle and must be attended
        if (ant.requests.get(vehicle).contains(requestId) || nextCity == instance.getDepot().nodeId) {
            supported = true;
        } else {
            // Check if the vehicle supports the demand of the new request
            supported = capacities.get(vehicle) + instance.getDelivery(requestId).demand > 0;
            // Check if all requests from the nextCity request are feasbile from the current point
            for (Request req : instance.getPickups(requestId)) {
                supported &= instance.isFeasible(currentCity, req.nodeId);
            }
            if (currentCity != 0) { // Depot never will be feasibly with delivery points
                supported &= instance.isFeasible(currentCity, instance.getDelivery(requestId).nodeId);
            }
        }
        return supported;
    }

    public NextClient HEURISTIC(Solution ant, double[] departureTimes, double[] demands, int vehicle, int currentCity, int nextCity) {
        NextClient nextClient = new NextClient();
        nextClient.nextClient = nextCity;
        nextClient.feasible = true;
        Request nextReq = instance.getRequest(nextCity);
        double demand;
        double departureTime;
        double timeCostDelivery;
        double timeCostDepot;
        // Calculate cost to next client
        departureTime = departureTimes[currentCity] + instance.dist(currentCity, nextCity);
        departureTime = Math.max(departureTime, nextReq.twStart);
        demand = demands[currentCity] + nextReq.demand;
        if (departureTime > nextReq.twEnd || demand > instance.getVehicleCapacity()) {
            nextClient.feasible = false;
        }
        departureTime += nextReq.serviceTime;
        nextClient.departureTime = departureTime;
        nextClient.demand = demand;
        // Is fair to calculate the cost to the delivery before to calculate the cost to depot
        if (nextReq.isPickup) {
            Request depotReq = instance.getDelivery(nextReq.requestId);
            timeCostDelivery = departureTime + instance.dist(nextCity, depotReq.nodeId);
            timeCostDelivery = Math.max(depotReq.twStart, timeCostDelivery);
            if (timeCostDelivery > depotReq.twEnd) {
                nextClient.feasible = false;
            }
            timeCostDelivery += depotReq.serviceTime;
            // Calculate cost to depot
            timeCostDepot = timeCostDelivery + instance.dist(depotReq.nodeId, instance.getDepot().nodeId);
            timeCostDepot = Math.max(timeCostDepot, instance.getDepot().twStart);
            if (timeCostDepot > instance.getDepot().twEnd) {
                nextClient.feasible = false;
            }
        } else { // Our current node is the delivery point
            // Calculate cost to depot
            timeCostDepot = departureTime + instance.dist(nextCity, instance.getDepot().nodeId);
            timeCostDepot = Math.max(timeCostDepot, instance.getDepot().twStart);
            if (timeCostDepot > instance.getDepot().twEnd) {
                nextClient.feasible = false;
            }
        }
        // Calculate heuristic Racula
        double deliveryUrgency = nextReq.twEnd - departureTime;
        double timeDifference = departureTime - departureTimes[currentCity];
        double futureCost = calculateFutureCost(ant, vehicle, currentCity, nextCity);
        double cost = 1.0 / (weight1 * instance.dist(currentCity, nextCity) + weight2 * timeDifference + weight3 * futureCost + weight4 * deliveryUrgency);

        //double cost = 1.0 / (0.3 * instance.distances[currentCity][nextCity] + 0.3 * futureCost + 0.3 * deliveryUrgency);

        // Original heuristic transition rule from Dorigo
        nextClient.heuristic = Math.pow(cost, mmas.getBeta()) * Math.pow(mmas.getPheromoneNodes()[currentCity][nextCity], mmas.getAlpha());

        // Transition rule proposed by Afshar
        //nextClient.heuristic = (beta * cost) + (alpha * pheromoneNodes[currentCity][nextCity]);

        // Only pheromone
        //nextClient.heuristic = Math.pow(pheromoneNodes[currentCity][nextCity], alpha);

        return nextClient;
    }

    public double calculateFutureCost(Solution ant, int vehicle, int currentCity, int nextCity) {
        double cost = 0.0;
        for (int reqId : ant.requests.get(vehicle)) {
            for (Request req : instance.getPickups(reqId)) {
                if (!ant.visited[req.nodeId]) {
                    cost += instance.dist(currentCity, req.nodeId);
                }
            }
            if (!ant.visited[instance.getDelivery(reqId).nodeId]) {
                cost += instance.dist(currentCity, instance.getDelivery(reqId).nodeId);
            }
        }
        int reqId = instance.getRequestId(nextCity);
        if (!ant.visitedRequests[reqId]) {
            for (Request req : instance.getPickups(reqId)) {
                cost += instance.dist(currentCity, req.nodeId);
            }
            cost += instance.dist(currentCity, instance.getDelivery(reqId).nodeId);
        }
        return cost;
    }

    @Override
    public void onSearchControlExecute() {
    }

    private class NextClient {
        int nextClient = 0;
        double heuristic = 0.0;
        double departureTime = 0.0;
        double demand = 0.0;
        boolean feasible = false;
        double cumulativeCost = 0.0;
    }
}
