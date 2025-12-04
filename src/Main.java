import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean running = true;
        while (running) {
            clearConsole();
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    demoLinkedList();
                    break;
                case 2:
                    demoStack();
                    break;
                case 3:
                    demoQueue();
                    break;
                case 4:
                    demoTree();
                    break;
                case 5:
                    demoGraph();
                    break;
                case 6:
                    demoSorting();
                    break;
                case 7:
                    demoSearching();
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    pause();
            }
        }
        scanner.close();
    }

    // ==================== MENU ====================
    static void displayMainMenu() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                Data Structure Visualizer - Main Menu          ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Linked List Demo                                          ║");
        System.out.println("║  2. Stack Demo                                                ║");
        System.out.println("║  3. Queue Demo                                                ║");
        System.out.println("║  4. Binary Search Tree Demo                                   ║");
        System.out.println("║  5. Graph Demo (BFS/DFS)                                      ║");
        System.out.println("║  6. Sorting Algorithms Demo                                   ║");
        System.out.println("║  7. Searching Algorithms Demo                                 ║");
        System.out.println("║  0. Exit                                                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }

    // ==================== LINKED LIST DEMO ====================
    static void demoLinkedList() throws Exception {
        ConsoleUtils.clearScreen();
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║         Linked List Demonstration           ║");
        System.out.println("╚═════════════════════════════════════════════╝\n");

        LinkedList list = new LinkedList();

        System.out.println(" Inserting elements at END: 10, 20, 30, 40, 50\n");
        int[] values = {10, 20, 30, 40, 50};
        for (int val : values) {
            list.insertAtEnd(val);
            Thread.sleep(300);
        }

        System.out.println("Inserting 5 at the HEAD\n");
        list.insertAtHead(5);
        Thread.sleep(300);

        System.out.println("Searching for 30\n");
        list.search(30);
        Thread.sleep(300);

        System.out.println("Deleting 30\n");
        list.delete(30);
        pause();
    }

    // ==================== STACK DEMO ====================
    static void demoStack() throws Exception {
        ConsoleUtils.clearScreen();
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("║            Stack Demonstration              ║");
        System.out.println("╚═════════════════════════════════════════════╝\n");

        stack st = new stack(6);

        System.out.println("Pushing elements: 10, 20, 30, 40, 50\n");
        int[] values = {10, 20, 30, 40, 50};
        for (int val : values) {
            st.push(val);
            Thread.sleep(300);
        }

        System.out.println("Peeking at top\n");
        st.peek();
        Thread.sleep(300);

        System.out.println(" Popping 3 elements\n");
        for (int i = 0; i < 3; i++) {
            st.pop();
            Thread.sleep(300);
        }

        pause();
    }

    // ==================== QUEUE DEMO ====================
    static void demoQueue() throws Exception {
        clearConsole();
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                      Queue Demonstration                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        Scanner sc = new Scanner(System.in);
        while (true) {
            QueueDemo.Animator.clear();
            System.out.println("=== QueueDemo - Pick a Queue ===\n");
            System.out.println("1. Simple Array Queue");
            System.out.println("2. Circular Queue");
            System.out.println("3. Linked-List Queue");
            System.out.println("4. Deque (double-ended)");
            System.out.println("0. Exit");
            System.out.print("\nChoice: ");
            String choice = sc.nextLine().trim();
            if (choice.equals("0")) break;

            QueueDemo.VisualizableQueue active = null;
            QueueDemo.Deque activeDeque = null; // special case for deque operations
            switch (choice) {
                case "1": active = new QueueDemo.SimpleQueue(10); break;
                case "2": active = new QueueDemo.CircularQueue(10); break;
                case "3": active = new QueueDemo.LinkedQueue(); break;
                case "4": activeDeque = new QueueDemo.Deque(0); break; // 0 means unbounded
                default: continue;
            }

            
            if (active != null) active.printState(); else activeDeque.printState();
            QueueDemo.Animator.sleep(300);

            
            while (true) {
                System.out.println();
                System.out.print("queue -> ");
                String line = sc.nextLine().trim();
                if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("back")) break;
                if (line.equalsIgnoreCase("help")) { printHelp(); continue; }
                if (line.equalsIgnoreCase("state")) { if (active!=null) active.printState(); else activeDeque.printState(); continue; }

                // command parsing: enqueue [1,2,3] or enqueue 5 or dequeue or peek or pushfront 7 or poprear
                String lower = line.toLowerCase();
                try {
                     if (lower.startsWith("enqueue")) {

                     List<Integer> vals = parseValues(line);
                     if (vals.isEmpty()) {
                     System.out.println("No values parsed.");
                     continue;
                 }

                    if (active != null) {
                    active.enqueueMultiple(vals);
                    } else {
                    for (int v : vals) activeDeque.enqueueOne(v);
                    }

                    } else if (lower.startsWith("dequeue")) {

                    Integer out = (active != null)
                      ? active.dequeueOne()
                      : activeDeque.dequeueOne();

                    if (out != null) System.out.println("Dequeued: " + out);

                    } else if (lower.startsWith("peek")) {

                     Integer p = (active != null)
                    ? active.peek()
                    : activeDeque.peek();

                    System.out.println("Peek: " + (p == null ? "<empty>" : p));

    } else if (lower.startsWith("pushfront")) {

        if (activeDeque == null) {
            System.out.println("pushfront only valid for deque.");
            continue;
        }

        List<Integer> vs =
            parseValues(line.replaceFirst("(i)pushfront", "enqueue"));

        for (int v : vs) activeDeque.pushFront(v);

    } else if (lower.startsWith("poprear")) {

        if (activeDeque == null) {
            System.out.println("poprear only valid for deque.");
            continue;
        }

        Integer v = activeDeque.popRear();
        if (v != null) System.out.println("Popped rear: " + v);

    } else if (lower.startsWith("clear")) {

        
            if (active instanceof QueueDemo.SimpleQueue) {
                active = new QueueDemo.SimpleQueue(10);
            } else if (active instanceof QueueDemo.CircularQueue) {
                active = new QueueDemo.CircularQueue(10);
            } else if (active instanceof QueueDemo.LinkedQueue) {
                active = new QueueDemo.LinkedQueue();
            } else if (activeDeque != null) {
                activeDeque = new QueueDemo.Deque(0);
            }

            } else {

             System.out.println("Unknown command. Type 'help' for commands.");
             QueueDemo.Animator.sleep(1500);
            }
            } 
            catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            }


                // print current state after each command
                QueueDemo.Animator.sleep(120);
                if (active != null) active.printState(); else activeDeque.printState();
            }
        }
        sc.close();
    }

        static List<Integer> parseValues(String s) {
        // extracts integers in a bracket or space separated after the command
        List<Integer> out = new ArrayList<>();
        int i1 = s.indexOf('[');
        int i2 = s.indexOf(']');
        String numbers = null;
        if (i1 >= 0 && i2 > i1) numbers = s.substring(i1+1, i2);
        else {
            // try space-separated tokens after command
            String[] parts = s.split("\\s+");
            if (parts.length >= 2) numbers = String.join(",", Arrays.copyOfRange(parts,1,parts.length));
        }
        if (numbers == null) return out;
        for (String tok : numbers.split(",")) {
            tok = tok.trim();
            if (tok.length()==0) continue;
            try { out.add(Integer.parseInt(tok)); } catch (NumberFormatException ignored) {}
        }
        return out;

    }

    static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  enqueue [a,b,c]   -> enqueue multiple values, or 'enqueue 5' for single");
        System.out.println("  dequeue            — remove front element");
        System.out.println("  peek               — show front");
        System.out.println("  pushfront [v]      — (deque only) push value at front");
        System.out.println("  poprear            — (deque only) pop value from rear");
        System.out.println("  state              — reprint current queue state");
        System.out.println("  clear              — reset the active structure");
        System.out.println("  exit / back        — go back to queue type menu or quit");
    
    }

    // ==================== BINARY SEARCH TREE DEMO ====================
    static void demoTree() throws Exception {
        clearConsole();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      Binary Search Tree                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        Tree tree = new Tree(); 

        int[] values = {50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 65};
        System.out.println(" Inserting elements: 50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 65\n");
        for (int val : values) {
            System.out.println("   Inserting: " + val);
            tree.insert(val);
            clearConsole();
            System.out.println("Current Tree Structure:\n");
            tree.printGridVisualization();
            Thread.sleep(1000);
        }

        System.out.println("\n Searching for elements:\n");
        int[] searchValues = {40, 60, 100, 25};
        for (int val : searchValues) {
            boolean found = tree.search(val);
            System.out.println("  " + val + ": " + (found ? "✓ Found" : "✗ Not Found"));
            Thread.sleep(400);
        }

        tree.printGridVisualization();
        System.out.println("\nChoose traversal:");
        System.out.println("1. In-order");
        System.out.println("2. Pre-order");
        System.out.println("3. Post-order");

        int choice = getIntInput("Choose traversal (1-3): ");

        switch (choice) {
            case 1 -> tree.inOrderTraversal();
            case 2 -> tree.preOrderTraversal();
            case 3 -> tree.postOrderTraversal();
        }

        pause();    
    }

    // ==================== GRAPH DEMO ====================
    static void demoGraph() throws Exception {
        ConsoleUtils.clearScreen();
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║                Graph Demo             ║");
        System.out.println("╚═══════════════════════════════════════╝\n");

        Graph graph = new Graph(6);
        System.out.println(" Building graph with edges:\n");
        int[][] edges = {
                {0, 1}, {0, 2}, {1, 2},
                {1, 3}, {2, 4}, {3, 4}, {4, 5}
        };
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1]);
            System.out.println("  Added edge: " + edge[0] + " - " + edge[1]);
            Thread.sleep(200);
        }

        System.out.println("\n Breadth-First Search (BFS) starting from vertex 0:\n");
        graph.bfs(0);
        Thread.sleep(500);

        System.out.println("\n Depth-First Search (DFS) starting from vertex 0:\n");
        graph.dfs(0);

        pause();
    }

    // ==================== SORTING DEMO ====================
    static void demoSorting() throws Exception {
        clearConsole();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 Sorting Algorithms Demonstration              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        int[] original = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("Original Array:");
        SortingAlgorithms.printArray(original);
        Thread.sleep(500);

        int[] arr;

        arr = original.clone();
        System.out.println("\nBubble Sort:");
        SortingAlgorithms.bubbleSort(arr);
        SortingAlgorithms.printArray(arr);
        Thread.sleep(500);

        arr = original.clone();
        System.out.println("\nSelection Sort:");
        SortingAlgorithms.selectionSort(arr);
        SortingAlgorithms.printArray(arr);
        Thread.sleep(500);

        arr = original.clone();
        System.out.println("\nInsertion Sort:");
        SortingAlgorithms.insertionSort(arr);
        SortingAlgorithms.printArray(arr);
        Thread.sleep(500);

        arr = original.clone();
        System.out.println("\nMerge Sort:");
        SortingAlgorithms.mergeSort(arr);
        SortingAlgorithms.printArray(arr);
        Thread.sleep(500);

        arr = original.clone();
        System.out.println("\nQuick Sort:");
        SortingAlgorithms.quickSort(arr);
        SortingAlgorithms.printArray(arr);

        pause();
    }

    // ==================== SEARCHING DEMO ====================
    static void demoSearching() throws Exception {
        clearConsole();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                 Searching Algorithms Demonstration            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");

        int[] arr = {10, 45, 23, 51, 19, 8, 34};
        int[] sortedArr = arr.clone();
        java.util.Arrays.sort(sortedArr);

        System.out.println("Unsorted Array:");
        SortingAlgorithms.printArray(arr);
        Thread.sleep(500);

        // Use the cursor-based demos for visible step-by-step searching
        System.out.println("\nLinear Search (cursor demo) for 19:");
        SearchingAlgorithms.linearSearchNoClear(arr, 19);
        Thread.sleep(500);

        System.out.println("\nBinary Search (cursor demo) for 51 on sorted array:");
        SearchingAlgorithms.binarySearchNoClear(sortedArr, 51);
        Thread.sleep(500);

        System.out.println("\nSorted Array:");
        SortingAlgorithms.printArray(sortedArr);
        Thread.sleep(400);

        pause();
    }

    

    // ==================== UTILITIES ====================

    static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void pause() throws Exception {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}
