package AStarAlgorithmPackage;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Path {
	
	private NodeComparator nodeComparator;
	
	private ArrayList<Node> pathInNodes;
	private PriorityQueue<Node> pathInQueue;
	
	public Path(ArrayList<Node> pathInNodes) {
		this.setPathInNodes(pathInNodes);
		this.nodeComparator = new NodeComparator();
		this.pathInQueue = new PriorityQueue(nodeComparator);
	}

	public ArrayList<Node> getPathInNodes() {
		return pathInNodes;
	}

	public void setPathInNodes(ArrayList<Node> pathInNodes) {
		this.pathInNodes = pathInNodes;
	}

	public PriorityQueue<Node> getPathInQueue() {
		return pathInQueue;
	}

	public void setPathInQueue(PriorityQueue<Node> pathInQueue) {
		this.pathInQueue = pathInQueue;
	}

}
