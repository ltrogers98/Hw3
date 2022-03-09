package edu.cmich.cps542;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

public class StronglyConnectedComponents {
	public static int[] visited;
	public static Stack<Integer> visitedOrder = new Stack<Integer>();
	public static int componentCount = 1;
	public static Graph displayOpposites(Graph g) {
		String edges = "";
		for (int i = 0; i<g.size(); i++) {
			for (int j = 0; j<g.size(); j++) {
				if (g.getAdjMatrix()[i][j]==1) {
					edges=edges+j+"-"+i+",";
				}
			}
		}
		if (edges.length()!=0) {
			edges=edges.substring(0,edges.length()-1);
		}
		return new Graph(g.size(),edges,true);
	}
	public static void main(String[] arg) {
		Graph g = new Graph(6, "0-1,1-2,2-0,2-3,1-4,2-5,4-5,5-4", true);
		Graph g2 = new Graph(8, "0-1,1-2,1-3,2-6,3-4,4-1,4-7,6-2,7-6", true);
		Graph g3 = new Graph(5, "1-2,2-0,3-0,4-0", true);
		findComponents(g);
		findComponents(g2);
		findComponents(g3);
		
	}
	public static void firstDFS(Graph g, int node) {
		visited[node]=1;
		Integer[] adjNodes = g.getAdjVector(node);
		for (int i = 0; i<adjNodes.length; i++) {
			if (adjNodes[i]==1&&visited[i]==0) {
				visited[i]=1;
				firstDFS(g,i);
			}
		}
		visitedOrder.push(node);
	}
	public static void firstDFS(Graph g) {
		visited = new int[g.size()];
		boolean done = false;
		while (!done) {
			done=true;
			for (int j = 0; j<g.size(); j++) {
				if (visited[j]==0) {
					visited[j]=1;
					firstDFS(g,j);
					done=false;
					break;
				}
			}
			
			
		}
	}
	public static void secondDFS(Graph g) {
		visited = new int[g.size()];
		componentCount=1;
		while (!visitedOrder.isEmpty()) {
			int newStart = visitedOrder.pop();
			if (visited[newStart]==0) {
				secondDFS(g,newStart);
				componentCount++;
			}
			
		}
	}
	public static void secondDFS(Graph g,int node) {
		
		visited[node]=componentCount;
		Integer[] adjNodes = g.getAdjVector(node);
		for (int i = 0; i<adjNodes.length; i++) {
			if (adjNodes[i]==1&&visited[i]==0) {
				visited[i]=componentCount;
				secondDFS(g,i);
			}
		}
	}
	public static void findComponents(Graph g) {
		Graph c = displayOpposites(g);
		firstDFS(g);
		secondDFS(c);
		System.out.println("Components:");
		int ComponentNum = 1;
		for (int i = 0; i<visited.length; i++) {
			if(visited[i]>ComponentNum) {
				ComponentNum=visited[i];
			}
		}
		HashMap<Integer,ArrayList<Integer>> components = new HashMap<Integer,ArrayList<Integer>>();
		for (int i = 0; i<ComponentNum; i++) {
			components.put(i+1, new ArrayList<Integer>());
			for (int j = 0; j<visited.length; j++) {
				if (visited[j]==i+1) {
					components.get(i+1).add(j);
				}
			}
				
		}
		for(Integer componentNumber : components.keySet() ){
		    System.out.print("Component "+componentNumber+": ");
		    ArrayList<Integer> componentNodes = components.get(componentNumber);
		    for(int i = 0; i<componentNodes.size(); i++) {
		    	System.out.print(componentNodes.get(i)+", ");
		    }
		    System.out.println();
		}
		System.out.println("\n\nAdjacency Lists");
		Integer[][] adjacentMat = g.getAdjMatrix();
		for(Integer componentNumber:components.keySet() ) {
			 System.out.print("Component "+componentNumber+"'s List: ");
			 ArrayList<Integer> componentNodes = components.get(componentNumber);
			 HashSet<Integer> adjacent = new HashSet<Integer>();
			 for(Integer currNode : componentNodes) {
				 for (int i = 0; i<g.size(); i++) {
					 if(adjacentMat[currNode][i] == 1&&visited[currNode]!=visited[i]&&!adjacent.contains(visited[i])) {
						 System.out.print(visited[i]+", ");
						 adjacent.add(visited[i]);
					 }
				 }
			 }
			 System.out.println();
		}
		System.out.println();
	}
}
