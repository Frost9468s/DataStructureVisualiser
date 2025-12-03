import java.util.*;

public class QueueDemo {

    static class Animator {
        static void clear() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        static void sleep(int ms) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ignored) {}
        }

        static String pad(String s, int w) {
            if (s.length() >= w) return s;
            StringBuilder sb = new StringBuilder(s);
            while (sb.length() < w) sb.append(' ');
            return sb.toString();
        }
    }

    interface VisualizableQueue {
        void enqueueMultiple(List<Integer> values);
        void enqueueOne(int value);
        Integer dequeueOne();
        Integer peek();
        boolean isEmpty();
        void printState(Integer highlight);
        void printState();
    }

    static class SimpleQueue implements VisualizableQueue {
        private final int[] arr;
        private int front = 0;
        private int rear = -1;
        private int size = 0;
        private final int capacity;

        public SimpleQueue(int capacity) {
            this.capacity = capacity;
            this.arr = new int[capacity];
        }

        public void enqueueMultiple(List<Integer> values) {
            for (int v : values) enqueueOne(v);
        }

        public void enqueueOne(int value) {
            if (size == capacity) {
                Animator.clear();
                System.out.println("[SimpleQueue] Overflow — capacity full.");
                Animator.sleep(700);
                return;
            }
            rear++;
            arr[rear] = value;
            size++;
            for (int i = 0; i < 3; i++) {
                printState(value);
                Animator.sleep(160);
            }
        }

        public Integer dequeueOne() {
            if (size == 0) {
                Animator.clear();
                System.out.println("[SimpleQueue] Underflow — queue empty.");
                Animator.sleep(700);
                return null;
            }
            int value = arr[front];
            front++;
            size--;
            for (int i = 0; i < 3; i++) {
                printState(value);
                Animator.sleep(160);
            }
            return value;
        }

        public Integer peek() {
            return size == 0 ? null : arr[front];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void printState() {
            printState(null);
        }

        public void printState(Integer highlight) {
            Animator.clear();
            System.out.println("Simple Queue (array semantics) — capacity=" + capacity + "\n");

            for (int i = 0; i < capacity; i++) {
                if (i >= front && i <= rear) {
                    int val = arr[i];
                    String cell = (highlight != null && highlight == val)
                            ? ">" + padInt(val)
                            : " " + padInt(val);
                    System.out.print("[" + cell + "] ");
                } else {
                    System.out.print("[    ] ");
                }
            }
            System.out.println("\n");

            for (int i = 0; i < capacity; i++) {
                if (i == front && i == rear) System.out.print("  ^FR ");
                else if (i == front) System.out.print("  ^F  ");
                else if (i == rear) System.out.print("  ^R  ");
                else System.out.print("      ");
            }
            System.out.println();
        }

        private String padInt(int v) {
            return Animator.pad(Integer.toString(v), 3);
        }
    }

    static class CircularQueue implements VisualizableQueue {
        private final int[] buffer;
        private int front = 0;
        private int rear = -1;
        private int size = 0;
        private final int capacity;

        public CircularQueue(int capacity) {
            this.capacity = capacity;
            buffer = new int[capacity];
        }

        public void enqueueMultiple(List<Integer> values) {
            for (int v : values) enqueueOne(v);
        }

        public void enqueueOne(int value) {
            if (size == capacity) {
                Animator.clear();
                System.out.println("[CircularQueue] Overflow");
                Animator.sleep(600);
                return;
            }
            rear = (rear + 1) % capacity;
            buffer[rear] = value;
            size++;
            for (int f = 0; f < 4; f++) {
                printState(value);
                Animator.sleep(140);
            }
        }

        public Integer dequeueOne() {
            if (size == 0) {
                Animator.clear();
                System.out.println("[CircularQueue] Underflow");
                Animator.sleep(600);
                return null;
            }
            int val = buffer[front];
            front = (front + 1) % capacity;
            size--;
            for (int f = 0; f < 4; f++) {
                printState(val);
                Animator.sleep(140);
            }
            return val;
        }

        public Integer peek() {
            return size == 0 ? null : buffer[front];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void printState() {
            printState(null);
        }

        public void printState(Integer highlight) {
            Animator.clear();
            System.out.println("Circular Queue (ring buffer) — capacity=" + capacity + "\n");

            for (int i = 0; i < capacity; i++) {
                String content;
                boolean occupied = false;

                if (size > 0) {
                    if (front <= rear) occupied = (i >= front && i <= rear);
                    else occupied = (i >= front || i <= rear);
                }

                if (occupied) {
                    int val = buffer[i];
                    content = (highlight != null && highlight == val)
                            ? ">" + Animator.pad(Integer.toString(val), 3)
                            : " " + Animator.pad(Integer.toString(val), 3);
                } else {
                    content = "    ";
                }

                System.out.print("[" + content + "] ");
            }
            System.out.println("\n");

            for (int i = 0; i < capacity; i++) {
                if (size == 0) System.out.print("      ");
                else if (i == front && i == rear) System.out.print("  ^FR ");
                else if (i == front) System.out.print("  ^F  ");
                else if (i == rear) System.out.print("  ^R  ");
                else System.out.print("      ");
            }
            System.out.println();
        }
    }

    static class LinkedQueue implements VisualizableQueue {
        static class Node {
            int val;
            Node next;
            Node(int v) { val = v; }
        }

        private Node front = null, rear = null;
        private int size = 0;

        public void enqueueMultiple(List<Integer> values) {
            for (int v : values) enqueueOne(v);
        }

        public void enqueueOne(int value) {
            Node n = new Node(value);
            if (rear == null) {
                front = rear = n;
            } else {
                rear.next = n;
                rear = n;
            }
            size++;
            for (int i = 0; i < 3; i++) {
                printState(value);
                Animator.sleep(180);
            }
        }

        public Integer dequeueOne() {
            if (front == null) {
                Animator.clear();
                System.out.println("[LinkedQueue] Underflow");
                Animator.sleep(600);
                return null;
            }
            int v = front.val;
            front = front.next;
            if (front == null) rear = null;
            size--;
            for (int i = 0; i < 3; i++) {
                printState(v);
                Animator.sleep(180);
            }
            return v;
        }

        public Integer peek() {
            return front == null ? null : front.val;
        }

        public boolean isEmpty() {
            return front == null;
        }

        public void printState() {
            printState(null);
        }

        public void printState(Integer highlight) {
            Animator.clear();
            System.out.println("Linked Queue (nodes) — size=" + size + "\n");

            Node cur = front;
            while (cur != null) {
                String s = (highlight != null && highlight == cur.val)
                        ? ">" + Animator.pad(Integer.toString(cur.val), 3)
                        : " " + Animator.pad(Integer.toString(cur.val), 3);
                System.out.print("[" + s + "] -> ");
                cur = cur.next;
            }
            System.out.println("NULL\n");

            if (front != null) {
                System.out.print("front\n");
            }
        }
    }

    static class Deque implements VisualizableQueue {
        static class Node {
            int val;
            Node prev, next;
            Node(int v) { val = v; }
        }

        private Node head = null, tail = null;
        private int size = 0;
        private final int capacity;

        public Deque(int capacity) {
            this.capacity = capacity;
        }

        public void enqueueOne(int value) {
            if (capacity > 0 && size == capacity) {
                Animator.clear();
                System.out.println("[Deque] Overflow");
                Animator.sleep(600);
                return;
            }
            Node n = new Node(value);
            if (tail == null) {
                head = tail = n;
            } else {
                tail.next = n;
                n.prev = tail;
                tail = n;
            }
            size++;
            for (int i = 0; i < 3; i++) {
                printState(value);
                Animator.sleep(140);
            }
        }

        public void pushFront(int value) {
            if (capacity > 0 && size == capacity) {
                Animator.clear();
                System.out.println("[Deque] Overflow");
                Animator.sleep(600);
                return;
            }
            Node n = new Node(value);
            if (head == null) {
                head = tail = n;
            } else {
                n.next = head;
                head.prev = n;
                head = n;
            }
            size++;
            for (int i = 0; i < 3; i++) {
                printState(value);
                Animator.sleep(140);
            }
        }

        public Integer dequeueOne() {
            if (head == null) {
                Animator.clear();
                System.out.println("[Deque] Underflow");
                Animator.sleep(600);
                return null;
            }
            int v = head.val;
            head = head.next;
            if (head != null) head.prev = null;
            else tail = null;
            size--;
            for (int i = 0; i < 3; i++) {
                printState(v);
                Animator.sleep(140);
            }
            return v;
        }

        public Integer popRear() {
            if (tail == null) {
                Animator.clear();
                System.out.println("[Deque] Underflow");
                Animator.sleep(600);
                return null;
            }
            int v = tail.val;
            tail = tail.prev;
            if (tail != null) tail.next = null;
            else head = null;
            size--;
            for (int i = 0; i < 3; i++) {
                printState(v);
                Animator.sleep(140);
            }
            return v;
        }

        public Integer peek() {
            return head == null ? null : head.val;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public void printState() {
            printState(null);
        }

        public void printState(Integer highlight) {
            Animator.clear();
            System.out.println("Deque (double-ended) — size=" + size + "\n");

            Node cur = head;
            while (cur != null) {
                String s = (highlight != null && highlight == cur.val)
                        ? ">" + Animator.pad(Integer.toString(cur.val), 3)
                        : " " + Animator.pad(Integer.toString(cur.val), 3);
                System.out.print("[" + s + "] ");
                cur = cur.next;
            }
            System.out.println("\n");
        }

        public void enqueueMultiple(List<Integer> values) {
            for (int v : values) enqueueOne(v);
        }
    }

    static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  enqueue [a,b,c]");
        System.out.println("  dequeue");
        System.out.println("  peek");
        System.out.println("  pushfront [v]");
        System.out.println("  poprear");
        System.out.println("  state");
        System.out.println("  clear");
        System.out.println("  exit / back");
    }

    static List<Integer> parseValues(String s) {
        List<Integer> out = new ArrayList<>();
        int i1 = s.indexOf('[');
        int i2 = s.indexOf(']');
        String numbers = null;

        if (i1 >= 0 && i2 > i1) {
            numbers = s.substring(i1 + 1, i2);
        } else {
            String[] parts = s.split("\\s+");
            if (parts.length >= 2)
                numbers = String.join(",", Arrays.copyOfRange(parts, 1, parts.length));
        }

        if (numbers == null) return out;

        for (String tok : numbers.split(",")) {
            tok = tok.trim();
            if (tok.length() == 0) continue;
            try {
                out.add(Integer.parseInt(tok));
            } catch (NumberFormatException ignored) {}
        }

        return out;
    }
}
