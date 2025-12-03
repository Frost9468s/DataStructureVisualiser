import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


public class Tree {

    
    static class Node {
        int value;
        Node left, right;
        int xPos;
        int yPos;

        Node(int value) {
            this.value = value;
            this.xPos = -1;
            this.yPos = -1;
        }
    }

    
    Node root;

    

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node node, int value) {
        if (node == null) return new Node(value);

        if (value < node.value) node.left = insertRec(node.left, value);
        else if (value > node.value) node.right = insertRec(node.right, value);

        return node;
    }

    

    public boolean search(int value) {
        return searchRec(root, value);
    }

    private boolean searchRec(Node node, int value) {
        if (node == null) return false;
        if (value == node.value) return true;

        return value < node.value
                ? searchRec(node.left, value)
                : searchRec(node.right, value);
    }

    

    public void calculatePositions() {
        int[] counter = {1};
        calculateXPositions(root, counter);
        calculateYPositions(root, 1);
    }

    private void calculateXPositions(Node node, int[] counter) {
        if (node == null) return;

        calculateXPositions(node.left, counter);
        node.xPos = counter[0]++;
        calculateXPositions(node.right, counter);
    }

    private void calculateYPositions(Node node, int depth) {
        if (node == null) return;

        node.yPos = depth;
        calculateYPositions(node.left, depth + 1);
        calculateYPositions(node.right, depth + 1);
    }

    

    public Map<String, Integer> createGrid() {
        Map<String, Integer> grid = new HashMap<>();
        if (root != null) fillGrid(root, grid);
        return grid;
    }

    private void fillGrid(Node node, Map<String, Integer> grid) {
        if (node == null) return;

        String key = node.yPos + "," + node.xPos;
        grid.put(key, node.value);

        fillGrid(node.left, grid);
        fillGrid(node.right, grid);
    }

    private int getMaxDepth(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getMaxDepth(node.left), getMaxDepth(node.right));
    }

    private int getMaxX(Node node) {
        if (node == null) return 0;
        return Math.max(node.xPos, Math.max(getMaxX(node.left), getMaxX(node.right)));
    }

    

    public void printGridVisualization(Integer highlightValue) {
    if (root == null) {
        System.out.println("Tree is empty");
        return;
    }

    calculatePositions();
    Map<String, Integer> grid = createGrid();

    int maxDepth = getMaxDepth(root);
    int maxX = getMaxX(root);

    for (int y = 1; y <= maxDepth; y++) {
        System.out.print("[L" + y + "] ");

        for (int x = 1; x <= maxX; x++) {
            String key = y + "," + x;

            if (grid.containsKey(key)) {
                int val = grid.get(key);

                if (highlightValue != null && highlightValue == val) {
                    // print marker + number
                    System.out.print("> " + val);
                } else {
                    System.out.print("  " + val);
                }

            } else {
                System.out.print("    "); 
            }

            if (x < maxX) System.out.print("  ");
        }
        System.out.println();
    }
}


    public void inOrderTraversal() {
        try {
            inorderTraversalWithAnimation();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Traversal interrupted");
        }
    }

    public void preOrderTraversal() {
        try {
            preorderTraversalWithAnimation();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Traversal interrupted");
        }
    }

    public void postOrderTraversal() {
        try {
            postorderTraversalWithAnimation();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Traversal interrupted");
        }
    }

    public void inorderTraversalWithAnimation() throws InterruptedException {
        System.out.println("Starting inorder traversal...\n");
        animateInorder(root);
    }

    private void animateInorder(Node node) throws InterruptedException {
        if (node == null) return;

        animateInorder(node.left);
        animateTraversalStep(node.value);
        Thread.sleep(600);
        animateInorder(node.right);
    }

    public void preorderTraversalWithAnimation() throws InterruptedException {
        System.out.println("Starting preorder traversal...\n");
        animatePreorder(root);
    }

    private void animatePreorder(Node node) throws InterruptedException {
        if (node == null) return;

        animateTraversalStep(node.value);
        Thread.sleep(600);
        animatePreorder(node.left);
        animatePreorder(node.right);
    }

    public void postorderTraversalWithAnimation() throws InterruptedException {
        System.out.println("Starting postorder traversal...\n");
        animatePostorder(root);
    }

    private void animatePostorder(Node node) throws InterruptedException {
        if (node == null) return;

        animatePostorder(node.left);
        animatePostorder(node.right);
        animateTraversalStep(node.value);
        Thread.sleep(600);
    }

    private List<Integer> traversalList = new ArrayList<>();

    private void animateTraversalStep(int currentValue) throws InterruptedException {
    clear();

    printGridVisualization(currentValue);

    traversalList.add(currentValue);

    System.out.println("\nTraversal progress: " + traversalList);

    Thread.sleep(600);
}
     public void printGridVisualization() {
        clear();
        printGridVisualization(null);
    }

    private void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
