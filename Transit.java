package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {

		int counttrain = 0;
       int countbus = 0;
       int countwalk = 0;
       TNode current = new TNode(0);
       TNode child = new TNode(0);
       trainZero = new TNode(0, null, null);
       current = trainZero;
     
       while(counttrain < trainStations.length){
           current.setNext(new TNode(trainStations[counttrain]));
           current = current.getNext();
           counttrain ++;
       }
     
     
       TNode busZero = new TNode(0, null, null);
       current = busZero;
       while(countbus < busStops.length){
           current.setNext(new TNode(busStops[countbus]));
           current = current.getNext();
           countbus++;
       }
     
       TNode locationzero = new TNode(0, null, null);
       current = locationzero;
       while(countwalk < locations.length){
           current.setNext(new TNode(locations[countwalk]));
           current = current.getNext();
           countwalk ++;
       }
      
       current = busZero;
       child = locationzero;
       while(current != null){
           while(child != null){
           if(current.getLocation() == child.getLocation()){
               current.setDown(child);
           }
           child = child.getNext();
       }
       child = locationzero;
       current = current.getNext();
       }
      
       current = trainZero;
       child = busZero;
       while(current != null){
           while(child != null){
           if(current.getLocation() == child.getLocation()){
               current.setDown(child);
           }
           child = child.getNext();
       }
       child = busZero;
       current = current.getNext();
       }
	 
	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
	    
		TNode current = trainZero;
		while(current != null){
			if( current.getNext().getLocation() == station){
				current.setNext(current.getNext().getNext());
				break;
			}
			current = current.getNext();

		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    
		TNode current = trainZero.getDown();
       TNode walkStop = new TNode();
       while(current != null){
           if(current.getLocation() < busStop && (current.getNext() == null || current.getNext().getLocation() > busStop)){
               TNode nextBusStop = current.getNext();
               current.setNext(new TNode(busStop));
               current.getNext().setNext(nextBusStop);
               walkStop = current.getNext();
           }
           current = current.getNext();
       }
       current = trainZero.getDown().getDown();
       while(current != null){
           if(current.getLocation() == busStop){
               walkStop.setDown(current);
           }
           current = current.getNext();
       }
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {

	    ArrayList<TNode> bestPath = new ArrayList<TNode>();
       TNode current = trainZero;
       int counter = -1;
       while(current != null){
           if(current.getLocation() <= destination){
               bestPath.add(current);
               counter ++;
           }
           current = current.getNext();
       }
       current = trainZero.getDown();
       while(current != null){
           if(current.getLocation() <= destination && current.getLocation() >= bestPath.get(counter).getLocation()){
               bestPath.add(current);
               counter ++;
           }
           current = current.getNext();
       }
       current = trainZero.getDown().getDown();
       while(current != null){
           if(current.getLocation() <= destination && current.getLocation() >= bestPath.get(counter).getLocation()){
               bestPath.add(current);
           }
           current = current.getNext();
       }
       return bestPath;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		
		TNode count = new TNode();
		count = trainZero;
		int trainstops = 0;
		ArrayList<Integer> trainlocations = new ArrayList<Integer>();
	    
		while(count.getNext() != null){
			trainstops++;
			trainlocations.add(count.getNext().getLocation());
			count = count.getNext();
		}

		count = trainZero.getDown();
		int busStops = 0;
		ArrayList<Integer> buslocations = new ArrayList<Integer>();
		while(count.getNext() != null){
			busStops++;
			buslocations.add(count.getNext().getLocation());
			count = count.getNext();
		}

		count = trainZero.getDown().getDown();
		int locations = 0;
		ArrayList<Integer> walklocations = new ArrayList<Integer>();
		while(count.getNext() != null){
			locations++;
			walklocations.add(count.getNext().getLocation());
			count = count.getNext();
		}
		int Titterate = 0;
		int Bitterate = 0;
		int Litterate = 0;
		TNode Dcurrent = new TNode(0);
		TNode Dchild = new TNode(0);
		TNode DtrainZero = new TNode (0, null , null);
		Dcurrent = DtrainZero;

		while(Titterate < trainstops){
			Dcurrent.setNext(new TNode(trainlocations.get(Titterate)));
			Dcurrent = Dcurrent.getNext();
			Titterate++;
		}

		TNode DbusZero = new TNode(0 , null , null);
		Dcurrent = DbusZero;

		while(Bitterate < busStops){
			Dcurrent.setNext(new TNode(buslocations.get(Bitterate)));
			Dcurrent = Dcurrent.getNext();
			Bitterate++;
		}

		TNode Dlocationzero = new TNode(0, null, null);
		Dcurrent = Dlocationzero;

		while(Litterate < locations){
			Dcurrent.setNext(new TNode(walklocations.get(Litterate)));
			Dcurrent = Dcurrent.getNext();
			Litterate++;
		}

		Dcurrent = DbusZero;
		Dchild = Dlocationzero;

		while(Dcurrent != null){
			while(Dchild != null){
				if(Dcurrent.getLocation() == Dchild.getLocation()){
					Dcurrent.setDown(Dchild);
				}
				Dchild = Dchild.getNext();
			}
			Dchild = Dlocationzero;
			Dcurrent = Dcurrent.getNext();
		}

		Dcurrent = DtrainZero;
		Dchild = DbusZero;
		while(Dcurrent != null){
			while(Dchild != null){
				if(Dcurrent.getLocation() == Dchild.getLocation()){
					Dcurrent.setDown(Dchild);
				}
				Dchild = Dchild.getNext();
			}
			Dchild = DbusZero;
			Dcurrent = Dcurrent.getNext();
		}


		TNode Dreturn = new TNode(0, DtrainZero.getNext(), DtrainZero.getDown());

	    return Dreturn;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

	    // UPDATE THIS METHOD
		
		TNode scurrent = new TNode();
		int countscooter = 0;
		TNode scooterZero = new TNode(0, null, null);
		scurrent = scooterZero;
	
	

		while(countscooter < scooterStops.length){
			scurrent.setNext(new TNode(scooterStops[countscooter]));
			scurrent = scurrent.getNext();
			countscooter++;
		}

		TNode buszero = new TNode(0);
		TNode locationzero = new TNode(0);
		buszero = trainZero.getDown();
		locationzero = buszero.getDown();

		
		
		buszero.setDown(scooterZero);
		scooterZero = buszero.getDown();

		scooterZero.setDown(locationzero);
		locationzero = scooterZero.getDown();

		TNode schild = new TNode();
		scurrent = scooterZero;
		schild = locationzero;
		while(scurrent != null){
			while(schild != null){
				if(scurrent.getLocation() == schild.getLocation()){
					scurrent.setDown(schild);
				}
				schild = schild.getNext();
			}
			schild = locationzero;
			scurrent = scurrent.getNext();
		}

		scurrent = buszero;
		schild = scooterZero;
		while(scurrent != null){
			while(schild != null){
				if(scurrent.getLocation() == schild.getLocation()){
					scurrent.setDown(schild);
				}
				schild = schild.getNext();
			}
			schild = scooterZero;
			scurrent = scurrent.getNext();
		}
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
