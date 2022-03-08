package edu.cmich.cps542;

import java.util.ArrayList;

// TODO: Throw exceptions if an vertex specified in an edge is out of range.

/** 
 * An implementation of an adjacency list representation of a graph.  Both 
 * directed and undirected graphs are supported.  The adjacency matrix and adjacency 
 * vectors can be generated and provided as needed.  
 * 
 * The vertices in the graph are labeled as integers starting from 0.
 * 
 * @author J Eickholt (eickh1jl)
 * @since 2016-02-10
 *
 */
public class Graph {

	int size;
	ArrayList<Integer>[] adjList;
	
	/**
	 * Constructs a graph with a specified number of vertices.  No 
	 *   edges are present in the graph.
	 * 
	 * @param size the number of vertices in the graph
	 */
	public Graph(int size) {
		this.size = size;
		this.adjList = (ArrayList<Integer>[]) new ArrayList[this.size];
		
		for(int i = 0; i < this.size; i++) {
			this.adjList[i] = new ArrayList<Integer>();
		}
		
	}
	
	/**
	 * Constructs an undirected graph of specified size and
	 *   set edges in the graph.  
	 * @param size the number of vertices in the graph
	 * @param edgeSpecifier a list of edges in the graph, separated by commas 
	 *    with each pair hyphenated (e.g., 2-3).
	 */
	public Graph(int size, String edgeSpecifier) {
		this(size, edgeSpecifier, false);
	}
	
	/**
	 * Constructs a graph of specified size and
	 *   set edges in the graph.  
	 * @param size the number of vertices in the graph
	 * @param edgeSpecifier a list of edges in the graph, separated by commas 
	 *    with each pair hyphenated (e.g., 2-3).
	 * @param directed is true if the edges should be interpreted as directed
	 */
	public Graph(int size, String edgeSpecifier, boolean directed) {
		
		this(size);		
		
		int a = -1;
		int b = -1;
		
		String[] edges = edgeSpecifier.split(",");
		for(int i = 0; i < edges.length; i++) {
			String[] endPoints = edges[i].split("-");
			a = new Integer(endPoints[0].trim());
			b = new Integer(endPoints[1].trim());
			
			this.adjList[a].add(b);
			if(!directed) {
				this.adjList[b].add(a);
			}
			
		}
		
	}
	
	/**
	 * Removes all edges from the graph.
	 */
	public void clearEdges() {
		for(int i = 0; i < this.size; i++) {
			this.adjList[i].clear();
		}
	
	}

	/**
	 * Returns the number of vertices in the graph.
	 * 
	 * @return the number of vertices in the graph.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Sets a directed edge in the graph.
	 * 
	 * @param a the source vertex for the edge
	 * @param b the destination vertex for the edge
	 */
	public void setDirectedEdge(int a, int b) {
		this.adjList[a].add(b);
	}

	/**
	 * Sets an undirected edge in the graph.
	 * 
	 * @param a 
	 * @param b
	 */
	public void setUndirectedEdge(int a, int b) {
		this.adjList[a].add(b);
		this.adjList[b].add(a);
		
	}

	/**
	 * Gets the adjacency list for a specified vertex in the graph.
	 * 
	 * @param a the index of the vertex of which the adjacency list will
	 *   be returned 
	 * @return the adjacency list for vertex indexed by a
	 */
	public ArrayList<Integer> getAdjList(int a) {
		
		return this.adjList[a];
	}

	 /**
	 * Gets the adjacency vector for a specified vertex in the graph.
	 * 
	 * @param a the index of the vertex of which the adjacency vector will
	 *   be returned 
	 * @return the adjacency vector for vertex indexed by a
	 */
	public Integer[] getAdjVector(int a) {
		Integer[] adjVector = new Integer[this.size];
		
		for(int i = 0; i < this.size; i++) {
			adjVector[i] = 0;
		}
		
		for(int i = 0; i < this.adjList[a].size(); i++) {
			adjVector[this.adjList[a].get(i)] = 1;
		}
		
		return adjVector;
	}

	/**
	 * Gets the adjacency matrix for the graph.
	 * 
	 * @return the adjacency matrix
	 */
	public Integer[][] getAdjMatrix() {
		Integer[][] adjMatrix = new Integer[this.size][this.size];
		
		for(int i = 0; i < this.size; i++) {
			
			for(int j = 0; j < this.size; j++) {
				adjMatrix[i][j] = 0;
			}
			
			for(int j = 0; j < this.adjList[i].size(); j++) {
				adjMatrix[i][this.adjList[i].get(j)] = 1;
			}
		}
		
		return adjMatrix;
	}

}