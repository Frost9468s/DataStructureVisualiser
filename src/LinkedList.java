public class LinkedList {
    private class Node {
        int value;
        Node next;
        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;

    public LinkedList() {
        head = null;
    }

    public void insert(int value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            Node cur = head;
            while (cur.next != null)
                cur = cur.next;
            cur.next = newNode;
        }
    }

    public void insertFirst(int value) {
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public void insertAt(int position, int value) {
        if (position < 0)
            throw new IllegalArgumentException("Position cannot be negative");
        if (position == 0) {
            insertFirst(value);
            return;
        }
        Node newNode = new Node(value);
        Node current = head;
        for (int i = 0; i < position - 1 && current != null; i++)
            current = current.next;
        if (current == null)
            throw new IndexOutOfBoundsException("Position out of bounds");
        newNode.next = current.next;
        current.next = newNode;
    }

    public void insertAtHead(int data) {
        System.out.println("Inserting " + data + " at HEAD...");
        insertFirst(data);
        animate("Inserted " + data + " at HEAD");
    }

    public void insertAtEnd(int data) {
        System.out.println("Inserting " + data + " at END...");
        if (head == null) {
            head = new Node(data);
            animate("Inserted " + data + " at HEAD (List was empty)");
            return;
        }
        Node current = head;
        int index = 0;
        while (current.next != null) {
            animatePointer(index, "Traversing to end...");
            current = current.next;
            index++;
        }
        animatePointer(index, "Found end. Inserting...");
        current.next = new Node(data);
        animate("Inserted " + data + " at END");
    }

    public boolean delete(int value) {
        System.out.println("Deleting " + value + "...");
        if (head == null) {
            System.out.println("List is empty!");
            return false;
        }
        if (head.value == value) {
            animatePointer(0, "Found " + value + " at HEAD. Deleting...");
            head = head.next;
            animate("Deleted " + value);
            return true;
        }
        Node current = head;
        int index = 0;
        while (current.next != null && current.next.value != value) {
            animatePointer(index, "Searching for " + value + "...");
            current = current.next;
            index++;
        }
        if (current.next != null) {
            animatePointer(index + 1, "Found " + value + ". Deleting...");
            current.next = current.next.next;
            animate("Deleted " + value);
            return true;
        } else {
            System.out.println(value + " not found!");
            return false;
        }
    }

    public boolean deleteAt(int position) {
        if (position < 0 || head == null)
            return false;
        if (position == 0) {
            head = head.next;
            return true;
        }
        Node cur = head;
        for (int i = 0; i < position - 1 && cur != null; i++)
            cur = cur.next;
        if (cur == null || cur.next == null)
            return false;
        cur.next = cur.next.next;
        return true;
    }

    public boolean search(int value) {
        System.out.println("Searching for " + value + "...");
        Node current = head;
        int index = 0;
        while (current != null) {
            animatePointer(index, "Checking node...");
            if (current.value == value) {
                System.out.println(ConsoleUtils.ANSI_GREEN + "Found " + value + "!" + ConsoleUtils.ANSI_RESET);
                return true;
            }
            current = current.next;
            index++;
        }
        System.out.println(ConsoleUtils.ANSI_RED + value + " not found." + ConsoleUtils.ANSI_RESET);
        return false;
    }

    public int size() {
        int c = 0;
        Node cur = head;
        while (cur != null) {
            c++;
            cur = cur.next;
        }
        return c;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void display() {
        printList();
    }

    public void reverse() {
        Node prev = null;
        Node cur = head;
        while (cur != null) {
            Node nt = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nt;
        }
        head = prev;
    }

    private void animate(String message) {
        ConsoleUtils.clearScreen();
        printList();
        System.out.println("\n" + message);
        ConsoleUtils.sleep(800);
    }

    private void animatePointer(int index, String message) {
        ConsoleUtils.clearScreen();
        printList();

        StringBuilder spaces = new StringBuilder();
        spaces.append("        ");

        Node current = head;
        for (int i = 0; i < index; i++) {
            if (current == null)
                break;
            String numStr = String.valueOf(current.value);
            spaces.append(" ".repeat(Math.max(0, numStr.length() + 6)));
            current = current.next;
        }

        if (current != null)
            spaces.append(" ");

        System.out.println(spaces.toString() + "^");
        System.out.println(spaces.toString() + "(current)");
        System.out.println("\n" + message);
        ConsoleUtils.sleep(600);
    }

    private void printList() {
        System.out.print("HEAD -> ");
        Node current = head;
        while (current != null) {
            System.out.print("[" + ConsoleUtils.ANSI_YELLOW + current.value + ConsoleUtils.ANSI_RESET + "] -> ");
            current = current.next;
        }
        System.out.println("NULL");
    }
}
