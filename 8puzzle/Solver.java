/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {
    private boolean isSolvable;
    private MinPQ<SearchNode> nodes;
    private SearchNode solutionNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        solutionNode = null;
        nodes = new MinPQ<>();
        nodes.insert(new SearchNode(0, null, initial));

        // perform A* search algorithm
        while (true) {
            SearchNode currNode = nodes.delMin();
            Board currBoard = currNode.getBoard();

            // check if solved
            if (currBoard.isGoal()) {
                isSolvable = true;
                solutionNode = currNode;
                break;
            }
            // check if unsolvable
            if (currBoard.hamming() == 2 && currBoard.twin().isGoal()) {
                isSolvable = false;
                break;
            }

            // insert onto pq all neighboring search nodes except for deleted
            for (Board board : currBoard.neighbors()) {
                SearchNode newNode =
                        new SearchNode(currNode.getMoves() + 1, currNode, board);
                if (currNode.getPrev() != null &&
                        board.equals(currNode.getPrev().getBoard())) {
                    continue;
                }
                nodes.insert(newNode);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? solutionNode.getMoves() : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) {
            return null;
        }
        LinkedList<Board> solution = new LinkedList<>();
        SearchNode node = solutionNode;
        while (node != null) {
            solution.addFirst(node.getBoard());
            node = node.getPrev();
        }

        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private int moves;
        private SearchNode prev;
        private Board board;
        private int priority;

        SearchNode(int moves, SearchNode prev, Board board) {
            this.moves = moves;
            this.prev = prev;
            this.board = board;
            this.priority = priority();
        }

        public int priority() {
            return board.manhattan() + moves;
        }

        public int getPriority() {
            return priority;
        }


        public int compareTo(SearchNode that) {
            return this.getPriority() - that.getPriority();
        }

        public Board getBoard() {
            return this.board;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrev() {
            return prev;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

