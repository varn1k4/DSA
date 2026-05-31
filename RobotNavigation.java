import java.util.*;

public class RobotNavigation {

    static class Node {
        int x, y, dist;

        Node(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    static int bfs(int[][] grid, int sx, int sy, int dx, int dy) {

        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];

        int[] rowDir = {-1, 1, 0, 0};
        int[] colDir = {0, 0, -1, 1};

        Queue<Node> queue = new LinkedList<>();

        queue.add(new Node(sx, sy, 0));
        visited[sx][sy] = true;

        while (!queue.isEmpty()) {

            Node curr = queue.poll();

            if (curr.x == dx && curr.y == dy)
                return curr.dist;

            for (int i = 0; i < 4; i++) {

                int nx = curr.x + rowDir[i];
                int ny = curr.y + colDir[i];

                if (nx >= 0 && ny >= 0 &&
                    nx < rows && ny < cols &&
                    !visited[nx][ny] &&
                    grid[nx][ny] == 0) {

                    visited[nx][ny] = true;
                    queue.add(new Node(nx, ny, curr.dist + 1));
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();

        System.out.print("Enter number of columns: ");
        int cols = sc.nextInt();

        int[][] grid = new int[rows][cols];

        System.out.println("Enter grid (0 = Free Path, 1 = Obstacle):");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        System.out.print("Enter Start Position (row col): ");
        int sx = sc.nextInt();
        int sy = sc.nextInt();

        System.out.print("Enter Destination Position (row col): ");
        int dx = sc.nextInt();
        int dy = sc.nextInt();

        int result = bfs(grid, sx, sy, dx, dy);

        if (result != -1)
            System.out.println("Shortest Path Length = " + result);
        else
            System.out.println("Destination Not Reachable");

        sc.close();
    }
}
