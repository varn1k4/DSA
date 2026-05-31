import java.util.Scanner;

public class GamingLeaderboard {

    static int[] tree;
    static int[] scores;

    static void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = scores[start];
        } else {
            int mid = (start + end) / 2;
            build(2 * node, start, mid);
            build(2 * node + 1, mid + 1, end);
            tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
        }
    }

    static int query(int node, int start, int end, int l, int r) {
        if (r < start || end < l)
            return Integer.MIN_VALUE;

        if (l <= start && end <= r)
            return tree[node];

        int mid = (start + end) / 2;

        return Math.max(
                query(2 * node, start, mid, l, r),
                query(2 * node + 1, mid + 1, end, l, r));
    }

    static void update(int node, int start, int end, int idx, int value) {
        if (start == end) {
            scores[idx] = value;
            tree[node] = value;
        } else {
            int mid = (start + end) / 2;

            if (idx <= mid)
                update(2 * node, start, mid, idx, value);
            else
                update(2 * node + 1, mid + 1, end, idx, value);

            tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of players: ");
        int n = sc.nextInt();

        scores = new int[n];
        tree = new int[4 * n];

        System.out.println("Enter player scores:");
        for (int i = 0; i < n; i++) {
            scores[i] = sc.nextInt();
        }

        build(1, 0, n - 1);

        System.out.print("Enter range (start rank end rank): ");
        int l = sc.nextInt();
        int r = sc.nextInt();

        System.out.println("Highest Score: " +
                query(1, 0, n - 1, l - 1, r - 1));

        System.out.print("Enter player rank to update: ");
        int index = sc.nextInt();

        System.out.print("Enter new score: ");
        int newScore = sc.nextInt();

        update(1, 0, n - 1, index - 1, newScore);

        System.out.println("Updated Highest Score: " +
                query(1, 0, n - 1, l - 1, r - 1));

        sc.close();
    }
}
