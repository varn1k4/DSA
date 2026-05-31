import java.util.Scanner;

class AVLNode {
    String word;
    int height;
    AVLNode left, right;

    AVLNode(String word) {
        this.word = word;
        this.height = 1;
    }
}

public class DictionaryAVL {

    AVLNode root;

    int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, String word) {

        if (node == null)
            return new AVLNode(word);

        if (word.compareToIgnoreCase(node.word) < 0)
            node.left = insert(node.left, word);
        else if (word.compareToIgnoreCase(node.word) > 0)
            node.right = insert(node.right, word);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL Case
        if (balance > 1 &&
                word.compareToIgnoreCase(node.left.word) < 0)
            return rightRotate(node);

        // RR Case
        if (balance < -1 &&
                word.compareToIgnoreCase(node.right.word) > 0)
            return leftRotate(node);

        // LR Case
        if (balance > 1 &&
                word.compareToIgnoreCase(node.left.word) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL Case
        if (balance < -1 &&
                word.compareToIgnoreCase(node.right.word) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    boolean search(AVLNode node, String word) {

        if (node == null)
            return false;

        if (word.equalsIgnoreCase(node.word))
            return true;

        if (word.compareToIgnoreCase(node.word) < 0)
            return search(node.left, word);

        return search(node.right, word);
    }

    void inorder(AVLNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.println(node.word);
            inorder(node.right);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DictionaryAVL dictionary = new DictionaryAVL();

        System.out.print("Enter number of words: ");
        int n = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter words:");

        for (int i = 0; i < n; i++) {
            String word = sc.nextLine();
            dictionary.root = dictionary.insert(dictionary.root, word);
        }

        System.out.println("\nWords in Dictionary (Alphabetical Order):");
        dictionary.inorder(dictionary.root);

        System.out.print("\nEnter word to search: ");
        String searchWord = sc.nextLine();

        if (dictionary.search(dictionary.root, searchWord))
            System.out.println("'" + searchWord + "' exists in the dictionary.");
        else
            System.out.println("'" + searchWord + "' does not exist in the dictionary.");

        sc.close();
    }
}
