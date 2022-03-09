package edu.cmich.cps542;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

/**
* Finds and displays strongly connected components along with their adjency list. It utilizes
* DFS (depth first search) to ****INSERT HERE**** before ****INSERT HERE****.
*
* @author brock1hj
* @author jagre1d
* @author roger1lt
* @author stron1le
*
* @since 03/09/2022
*/
public class StronglyConnectedComponents {
	public static int[] visited;
	public static Stack<Integer> visitedOrder = new Stack<Integer>();
	public static int componentCount = 1;
	
	/**
	* TODO
	*
	* @param currentGraph TODO
	*/
	public static Graph displayOpposites(Graph currentGraph) {
		String edges = "";
		for (int i = 0; i < currentGraph.size(); i++) {
			for (int j = 0; j < currentGraph.size(); j++) {
				if (currentGraph.getAdjMatrix()[i][j] == 1) {
					edges = edges + j + "-" + i + ",";
				}
			}
		}
		if (edges.length() != 0) {
			edges = edges.substring(0, edges.length() - 1);
		}
		return new Graph(currentGraph.size(), edges, true);
	}
	
	/**
	* Identifies and displays the strongly connected components of 3 required graphs.
	*
	* @param arg any command line arguments; expecting null.
	*/
	public static void main(String[] arg) {
		Graph graph1 = new Graph(6, "0-1,1-2,2-0,2-3,1-4,2-5,4-5,5-4", true);
		Graph graph2 = new Graph(8, "0-1,1-2,1-3,2-6,3-4,4-1,4-7,6-2,7-6", true);
		Graph graph3 = new Graph(5, "1-2,2-0,3-0,4-0", true);
		findComponents(graph1);
		findComponents(graph2);
		findComponents(graph3);
	}
	
	/**
	* TODO
	*
	* @param currentGraph TODO
	* @param node TODO
	*/
	public static void firstDFS(Graph currentGraph, int node) {
		visited[node] = 1;
		Integer[] adjNodes = currentGraph.getAdjVector(node);
		for (int i = 0; i < adjNodes.length; i++) {
			if (adjNodes[i] == 1 && visited[i] == 0) {
				visited[i] = 1;
				firstDFS(currentGraph, i);
			}
		}
		visitedOrder.push(node);
	}
	
	/**
	* TODO
	*
	* @param currentGraph TODO
	*/
	public static void firstDFS(Graph currentGraph) {
		visited = new int[currentGraph.size()];
		boolean done = false;
		while (!done) {
			done=true;
			for (int j = 0; j < currentGraph.size(); j++) {
				if (visited[j] == 0) {
					visited[j] = 1;
					firstDFS(currentGraph, j);
					done = false;
					break;
				}
			}
		}
	}
	
	/**
	* TODO
	*
	* @param currentGraph TODO
	*/
	public static void secondDFS(Graph currentGraph) {
		visited = new int[currentGraph.size()];
		componentCount = 1;
		while (!visitedOrder.isEmpty()) {
			int newStart = visitedOrder.pop();
			if (visited[newStart] == 0) {
				secondDFS(currentGraph, newStart);
				componentCount++;
			}
		}
	}
	
	/**
	* TODO
	*
	* @param currentGraph TODO
	* @param node TODO
	*/
	public static void secondDFS(Graph currentGraph, int node) {
		visited[node] = componentCount;
		Integer[] adjNodes = currentGraph.getAdjVector(node);
		for (int i = 0; i < adjNodes.length; i++) {
			if (adjNodes[i] == 1 && visited[i] == 0) {
				visited[i] = componentCount;
				secondDFS(currentGraph, i);
			}
		}
	}
	
	/**
	* TODO
	*
	* @param currentGraph TODO
	*/
	public static void findComponents(Graph currentGraph) {
		Graph oppositeGraph = displayOpposites(currentGraph);
		firstDFS(currentGraph);
		secondDFS(oppositeGraph);
		System.out.println("Components:");
		int componentNum = 1;
		for (int i = 0; i < visited.length; i++) {
			if(visited[i] > componentNum) {
				componentNum = visited[i];
			}
		}
		HashMap<Integer,ArrayList<Integer>> components = new HashMap<Integer,ArrayList<Integer>>();
		for (int i = 0; i < componentNum; i++) {
			components.put(i + 1, new ArrayList<Integer>());
			for (int j = 0; j < visited.length; j++) {
				if (visited[j] == i + 1) {
					components.get(i + 1).add(j);
				}
			}
		}
		for(Integer componentNumber : components.keySet() ){
		    System.out.print("Component " + componentNumber + ": ");
		    ArrayList<Integer> componentNodes = components.get(componentNumber);
		    for(int i = 0; i < componentNodes.size(); i++) {
		    	System.out.print(componentNodes.get(i) + ", ");
		    }
		    System.out.println();
		}
		System.out.println("\n\nAdjacency Lists:");
		Integer[][] adjacentMat = currentGraph.getAdjMatrix();
		for(Integer componentNumber: components.keySet() ) {
			 System.out.print("Component " + componentNumber + "'s List: ");
			 ArrayList<Integer> componentNodes = components.get(componentNumber);
			 HashSet<Integer> adjacent = new HashSet<Integer>();
			 for(Integer currNode : componentNodes) {
				 for (int i = 0; i < currentGraph.size(); i++) {
					 if(adjacentMat[currNode][i] == 1 && visited[currNode] != visited[i] && !adjacent.contains(visited[i])) {
						 System.out.print(visited[i] + ", ");
						 adjacent.add(visited[i]);
					 }
				 }
			 }
			 System.out.println();
		}
		System.out.println();
	}
}
