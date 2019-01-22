//
// SHORTESTPATHS.JAVA
// Compute shortest paths in a weighted, directed graph.
//

package spath;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.nio.file.Path;
import java.util.HashMap;

// heap-related structures from Lab 3
import heaps.Decreaser;
import heaps.MinHeap;

// directed graph structure
import spath.graphs.DirectedGraph;
import spath.graphs.Edge;
import spath.graphs.Vertex;

// vertex/dist pair for priority queue
import spath.VertexAndDist;



import timing.Ticker;


public class ShortestPaths {

    // "infinity" value for path lengths
    private final static Integer inf = Integer.MAX_VALUE;
    
    // a directed graph, and a weighting function on its edges
    private final DirectedGraph g;
    private HashMap<Edge, Integer> weights;	
    
    // starting vertex for shortest path computation
    private Vertex startVertex;
    
    // map from vertices to their handles into the priority queue
    
    private HashMap<Vertex, Decreaser<VertexAndDist>> handles;
//    private Decreaser<VertexAndDist> [] handles;

    
    
    
    // map from vertices to their parent edges in the shortest-path tree
    private HashMap<Vertex, Edge> parentEdges;
    
    
    
    
    //
    // constructor
    //
    public ShortestPaths(DirectedGraph g, HashMap<Edge,Integer> weights, 
			 Vertex startVertex) {
    	this.g           = g;
    	this.weights     = weights;

    	this.startVertex = startVertex;	
    	
    	this.handles     = new HashMap<Vertex, Decreaser<VertexAndDist>>();
    	
    	
//    	Decreaser<VertexAndDist>[] handle =new Decreaser<VertexAndDist>() [2];
//    	Ticker ticker = new Ticker(); // heap requires a ticker
//    	MinHeap<VertexAndDist> pq = new MinHeap<VertexAndDist>(g.getNumVertices(), ticker);    	
    	
    	
//    	Decreaser<VertexAndDist> [] arr = pq.toArray();

    	this.parentEdges = new HashMap<Vertex, Edge>();
    }

    
    //
    // run() 
    //
    // Given a weighted digraph stored in g/weights, compute a
    // shortest-path tree of parent edges back to a given starting
    // vertex.
    //
    public void run() {
    	Ticker ticker = new Ticker(); // heap requires a ticker
//    	unknown
    	MinHeap<VertexAndDist> pq = 
    			new MinHeap<VertexAndDist>(g.getNumVertices(), ticker);
    	
    	//
    	// Put all vertices into the heap, infinitely far from start.
    	// Record handle to each inserted vertex, and initialize
    	// parent edge of each to null (since we have as yet found 
    	// no path to it.)
    	//
    	for (Vertex v : g.vertices()) {
    		Decreaser<VertexAndDist> d = pq.insert(new VertexAndDist(v, inf));
    		handles.put(v, d);
//    		or store in an array vdArr
    		parentEdges.put(v, null);
    	}
    	
    	//
    	// Relax the starting vertex's distance to 0.
    	//   - get the handle to the vertex from the heap
    	//   - extract the vertex + distance object from the handle
    	//   - create a *new* vertex + distance object with a reduced 
    	//      distance
    	//   - update the heap through the vertex's handle
    	//
    	Decreaser<VertexAndDist> startHandle = handles.get(startVertex);
//    	vdArray[0]
    	VertexAndDist vd = startHandle.getValue();
    	startHandle.decrease(new VertexAndDist(vd.vertex, 0));
    	
//    	startHandle.decrease(vd.vertex, 0);

//notes	
//pq cant not be null
//do not call function in print()
    	
    	while (!pq.isEmpty()) {
//    		System.out.println("------------------ ");
//    		System.out.println("pq "+pq);
//    		At each step, find next vertex to explore by pq.extractMin()
    		VertexAndDist u = pq.extractMin();
//			System.out.println("u " + u );

//    		get edges outgoing from u
    		for (Edge v: u.vertex.edgesFrom()) {
    			
    			int weight = weights.get(v);
//    			System.out.println("weights " + weight + "   +u " + u.distance);

//    			find alternative path
//    			if this path is faster than dist[v], update it
    			int alternative = u.distance + weight;

//    			get the handle to the vertex of u's neighbor vD
				Decreaser<VertexAndDist> handle = handles.get(v.to);
		    	VertexAndDist vD = handle.getValue();
		    	
    			if (alternative < vD.distance) {
//    				System.out.println(handle);
    				
//    				u.dist <- alternative
    				handle.decrease(new VertexAndDist(vD.vertex, alternative));
//    				System.out.println(handle);
//    				System.out.println(pq.size());

    				parentEdges.put(vD.vertex, v);
//    				System.out.println(vD.distance);
    			}
    	
    		}//end of finding edges, we have a null left 		
//    		System.out.println(parentEdges);
    	}//end of while
//		System.out.println("out of while ");

    }
    
    
    //
    // returnPath()
    //
    // Given an ending vertex v, compute a linked list containing every
    // edge on a shortest path from the starting vertex (stored) to v.
    // The edges should be ordered starting from the start vertex.
    //
    public LinkedList<Edge> returnPath(Vertex endVertex) {
    	LinkedList<Edge> path = new LinkedList<Edge>();
    	//
    	// FIXME: implement this using the parent edges computed in run()
    	//
//    	System.out.println("PE " +this.parentEdges);
        HashMap<Vertex, Edge> clone = this.parentEdges;
    	Vertex v;
    	Edge e;
    	e = clone.get(endVertex);    
//    	System.out.println("e " +e);

    	int count = 0;
		while(count < parentEdges.size()) {
	    	count++;
	    	if (e != null) {
//				add first edge to path
		    	path.addFirst(e);
//		    	path.add(e);

//		    	get next vertex v
		    	v = e.from;
//		    	if we are there we end w subpath
	    		if (v == startVertex) {
//			    	System.out.println("start vertex ");
	    			break;
	    		}
//	    		else we move on
//		    	get edge of v
		    	e =parentEdges.get(v);
	    	}
	    	else {
	    		break;
	    	}
//	    	System.out.println("path " +path);
		}        
		
//    	System.out.println("end result "+ path );

    	return path;
    }
    
    ////////////////////////////////////////////////////////////////
    
    //
    // returnLength()
    // Compute the total weight of a putative shortest path
    // from the start vertex to the specified end vertex.
    // No user-serviceable parts inside.
    //
    public int returnLength(Vertex endVertex) {
    	LinkedList<Edge> path = returnPath(endVertex);
	
    	int pathLength = 0;
    	for(Edge e : path) {
    		pathLength += weights.get(e);
    	}
	
    	return pathLength;
    }
}
