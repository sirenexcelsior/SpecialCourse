import java.util.*;
import java.util.ArrayList;
import java.util.List;

class Board {
    private int[][] tiles; // 棋盘瓦片
    private int size;      // 棋盘大小

    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, size);
        }
    }

    // 判断是否达到目标状态
    public boolean isGoal() {
        int count = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1) {
                    return tiles[i][j] == 0; // 最后一个瓦片应该是空的
                }
                if (tiles[i][j] != count++) {
                    return false; // 如果任何瓦片不在其正确位置上，则不是目标状态
                }
            }
        }
        return true; // 所有瓦片都在正确位置上
    }

    // 计算曼哈顿距离
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = tiles[i][j];
                if (value != 0) {
                    int targetX = (value - 1) / size;
                    int targetY = (value - 1) % size;
                    distance += Math.abs(i - targetX) + Math.abs(j - targetY);
                }
            }
        }
        return distance;
    }

    public List<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int zeroPosition = findZero();
        int zeroX = zeroPosition / size;
        int zeroY = zeroPosition % size;

        // 定义移动方向：上下左右
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newX = zeroX + dir[0];
            int newY = zeroY + dir[1];
            if (isValidPosition(newX, newY)) {
                int[][] newTiles = copyTiles();
                // 交换空格和相邻瓦片的位置
                newTiles[zeroX][zeroY] = newTiles[newX][newY];
                newTiles[newX][newY] = 0;
                neighbors.add(new Board(newTiles));
            }
        }

        return neighbors;
    }

    private int findZero() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    return i * size + j;
                }
            }
        }
        throw new IllegalStateException("No zero tile on board");
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private int[][] copyTiles() {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%2d ", tiles[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

}

class Solver {
    // 优先级队列比较器，基于 A* 算法的公式
    private static class NodeComparator implements Comparator<Node> {
        public int compare(Node n1, Node n2) {
            return Double.compare(n1.priority(), n2.priority());
        }
    }

    public List<Board> solve(Board initial, double a, double b) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        pq.add(new Node(initial, null, 0, a, b));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 检查是否达到目标状态
            if (current.board.isGoal()) {
                return reconstructPath(current);
            }

            // 将邻居节点加入队列
            for (Board neighbor : current.board.neighbors()) {
                pq.add(new Node(neighbor, current, current.moves + 1, a, b));
            }
        }

        return new ArrayList<>(); // 未找到解决方案
    }

    private List<Board> reconstructPath(Node node) {
        LinkedList<Board> path = new LinkedList<>();
        while (node != null) {
            path.addFirst(node.board);
            node = node.previous;
        }
        return path;
    }
}

class Node {
    public final Board board;
    public final Node previous;
    public final int moves;
    private final double a, b;

    public Node(Board board, Node previous, int moves, double a, double b) {
        this.board = board;
        this.previous = previous;
        this.moves = moves;
        this.a = a;
        this.b = b;
    }

    public double priority() {
        return a * board.manhattan() + b * moves;
    }
}

public class Main {
    public static void main(String[] args) {
        int[][] tiles = {{1, 3, 2},
                         {6, 5, 4},
                         {0, 7, 8}};
        Board initialBoard = new Board(tiles);
        Solver solver = new Solver();

        // 解决 3x3 棋盘
        List<Board> solution = solver.solve(initialBoard, 1.0, 1.0);

        // 打印解决方案
        for (Board board : solution) {
            board.printBoard(); // 打印每一步的棋盘状态
        }
    }
}
