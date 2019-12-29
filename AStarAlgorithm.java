package AStarAlgorithmPackage;

import AStarAlgorithmPackage.NodeComparator;
import AStarAlgorithmPackage.Node;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarAlgorithm {
	
	public AStarAlgorithm() {

	}
	
	
	/*
	OPEN = priority queue containing START
	CLOSED = empty set
	while lowest rank in OPEN is not the GOAL:
		current = remove lowest rank item from OPEN
		add current to CLOSED
		for neighbors of current:
		cost = g(current) + movement cost(current, neighbor)
		if neighbor in OPEN and cost less than g(neighbor):
  			remove neighbor from OPEN, because new path is better
		if neighbor in CLOSED and cost less than g(neighbor): ⁽²⁾
  			remove neighbor from CLOSED
		if neighbor not in OPEN and neighbor not in CLOSED:
  			set g(neighbor) to cost
  			add neighbor to OPEN
  			set priority queue rank to g(neighbor) + h(neighbor)
  			set neighbor's parent to current

			reconstruct reverse path from goal to start
			by following parent pointers
	 */
	public Path findShortestPath(Node startNode, Node goalNode, int[][] gameBoard, int ownColor) {
		
		int gameBoardRows = gameBoard.length;
		int gameBoardColumns = gameBoard[0].length;
		
		Comparator<Node> comp = new NodeComparator();	//sortiert nach f kosten
		PriorityQueue<Node> open = new PriorityQueue<Node>(comp);
		ArrayList<Node> closed = new ArrayList<Node>();
		
		startNode.setfCost(0);
		open.add(startNode);

		Node possibleGoal = open.poll();
		
		
		while( (possibleGoal.getNodeID() != goalNode.getNodeID()) || (open.size() != 0) ) {		//while wird verlassen wenn entweder das Ziel gefunden wurde oder es keine Knoten zum durchsuchen mehr gibt
			Node current = open.poll();		//current mit kleinstem f wird aus open entfernt
			closed.add(current);			//current wird closed hinzugefügt
			
			
		}
		
		return null;
	}
	
	
	public int calculateHeuristic(Node start, Node end, int gameRowsNumber, int gameColumnsNumber) {
		int heuristicValue = 0;
		int absoluteHorizontalValue;
		int absoluteVerticalValue;
		
		
		absoluteHorizontalValue = Math.abs(start.getColumnPosition() - end.getColumnPosition());
		absoluteVerticalValue = Math.abs(start.getRowPosition() - end.getRowPosition() );
		
		if(absoluteHorizontalValue > (Math.floor( (gameColumnsNumber) / 2))) {
			if(start.getColumnPosition() <= end.getColumnPosition() ) {
				absoluteHorizontalValue = (start.getColumnPosition() - 0) + ((gameColumnsNumber) - end.getColumnPosition() ) ;
			}
			else if(start.getColumnPosition() >= end.getColumnPosition() ) {
				absoluteHorizontalValue = (end.getColumnPosition() - 0) + ((gameColumnsNumber) - start.getColumnPosition() ) ;
			}
		}
		if(absoluteVerticalValue > (Math.floor(gameRowsNumber / 2)) ) {
			if(start.getRowPosition() <= end.getRowPosition()) {
				absoluteVerticalValue = (start.getRowPosition() - 0) + ((gameRowsNumber) - end.getRowPosition()) ;
			}
			else if(start.getRowPosition() >= end.getRowPosition()) {
				absoluteVerticalValue = (end.getRowPosition() - 0) + ((gameRowsNumber) - start.getRowPosition()) ;
			}
		}
		heuristicValue = absoluteHorizontalValue + absoluteVerticalValue;
		return heuristicValue;
	}
	
	//Methode um die Nachbarn einer Node zu bekommen
		public ArrayList<Node> findAllNeighbours(Node currentNode, int[/*maxRows*/][/*maxCols*/] board, int gameRowNumber, int gameColumnNumber, int ownColor, int moveCost){
			
			int rowNumber;
			int columnNumber;
			int rowIndex = gameRowNumber - 1;
			int columnIndex = gameColumnNumber -1;
			
			ArrayList<Node> neighbours = new ArrayList<Node>();

			
			//========== Upper Neighbour ==========
			columnNumber = currentNode.getColumnPosition();
			rowNumber = ((currentNode.getRowPosition() -1));
			
			if(rowNumber == -1)
				rowNumber = rowIndex;

			if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {
				Node upperNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor, Direction.UP);
				//upperNeighbour.setgCost(currentNode.getgCost() + moveCost);
				//upperNeighbour.setParent(currentNode);
				neighbours.add(upperNeighbour);
			}
			

			//========== Right neighbour ==========
			columnNumber = ((currentNode.getColumnPosition()  + 1));
			rowNumber = currentNode.getRowPosition(); 
			
			if(columnNumber > columnIndex)
				columnNumber = 0;
			
			if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {
				Node rightNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor, Direction.RIGHT);
				//rightNeighbour.setgCost(currentNode.getgCost() + moveCost);
				//rightNeighbour.setParent(currentNode);
				neighbours.add(rightNeighbour);
			}
			
			
			
			//========== Lower neighbour ==========
			columnNumber = currentNode.getColumnPosition();
			rowNumber = ((currentNode.getRowPosition() + 1));

			if(rowNumber > rowIndex)
				rowNumber = 0;

			if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {
				Node lowerNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor, Direction.DOWN);
				//lowerNeighbour.setgCost(currentNode.getgCost() + moveCost);
				//lowerNeighbour.setParent(currentNode);
				neighbours.add(lowerNeighbour);
			}
			
			
			//========== Left neighbour ==========
			columnNumber = ((currentNode.getColumnPosition()  -1));
			rowNumber = currentNode.getRowPosition(); 

			if(columnNumber == -1)
				columnNumber = columnIndex;

			if( fieldIsBlocked(rowNumber, columnNumber, board, ownColor) == false ) {
				Node leftNeighbour = createNewNode(rowNumber, columnNumber, board, ownColor, Direction.LEFT);
				//leftNeighbour.setgCost(currentNode.getgCost() + moveCost);
				//leftNeighbour.setParent(currentNode);
				neighbours.add(leftNeighbour);
			}
			
			
			return neighbours;
		}
		
		
		//Methode um ein Feld zu überprüfen, ob es geblockt ist
		public boolean fieldIsBlocked(int rowNumber, int columnNumber, int[/*maxRows*/][/*maxCols*/] board, int ownColor) {
			
			
			//alles außer 0 und -ownColor ist eine Blockade
			if( board[rowNumber][columnNumber] != 0 && board[rowNumber][columnNumber] != -ownColor  ) {
				return true;
			}
			else {
				return false;
			}
		}
		
		
		public void calculateNodeID(Node node, int ColumnNumbers /*Nicht der Index, sondern die tatsächliche Anzahl an Columns*/) {
			int ID = node.getColumnPosition() + ( node.getRowPosition() * ColumnNumbers );
			node.setNodeID(ID);
		}
		
		
		
		//Methode um eine neue Node zu erstellen
		public Node createNewNode(int rowNumber, int columnNumber, int [/*maxRows*/][/*maxCols*/] board, int ownColor, Direction direction ) {
			
			boolean isBlocked;
			
			//wenn es nicht 0 ist und nicht -ownColor ist, dann ist es eine Blockade
			if( board[rowNumber][columnNumber] != 0 && board[rowNumber][columnNumber] != -ownColor ) {
				isBlocked = true;
			}
			else {
				isBlocked = false;
			}
			
			Node node = new Node(/*id*/,rowNumber, columnNumber, isBlocked, direction);
			return node;
		}
	
	

}
