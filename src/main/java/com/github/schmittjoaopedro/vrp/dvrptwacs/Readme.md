# Thanks for the implementations

## ACO algorithms for the TSP

Purpose: implementation of procedures for ants' behaviour.

Version: 1.0

Author: Thomas Stuetzle

This code is based on the ACOTSP project of Thomas Stuetzle. 
It was initially ported from C to Java by Adrian Wilke.

Project website: http://adibaba.github.io/ACOTSPJava/

Source code: https://github.com/adibaba/ACOTSPJava/

The following classes are based on the implementation from Thomas Stuetzle an Adrian Wilke.
* Ants.java
* InOut.java
* Parse.java
* Timer.java
* Utilities.java
* VRPTW.java
* VRPTW_ACS.java

## ACO algorithm for the VRPTW with dynamic requests

File: VRPTW_ACS.java

This Eclipse project represents the ACS algorithm adapted from the mTSP_ACO MinMax global_new heuristic Eclipse project,
for solving the vehicle routing problem with time windows (VRPTW), but in which the number
of used vehicles (or salesmen) is fixed and is known a priori; this value is taken from the best
known solution reported in the literature for that particular VRPTW test instance
(see http://people.idsia.ch/~luca/macs-vrptw/solutions/welcome.htm and http://web.cba.neu.edu/~msolomon/heuristi.htm)

The quantity of the deposited pheromone and the best ant is taken according to the MinSum criterion
(the total sum of the length/cost of each travelled route/tour)
according to the mTSP_ACO MinMax global_new heuristic algorithm, the salesman and the city to
be visited and added in its tour are selected simultaneously during the same step from the
construction phase of solution, reflecting a jointly decision making.

Necula, R., Breaban, M., & Raschip, M. (2017). Tackling Dynamic Vehicle Routing Problem with Time Windows by means of ant colony system. 2017 IEEE Congress on Evolutionary Computation (CEC), 2480–2487. https://doi.org/10.1109/CEC.2017.7969606