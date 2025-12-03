public class stack {
    private int[] elements;
    private int top;
    private int capacity;

    public stack() {
         this.capacity = 6; 
         this.elements = new int[capacity]; 
         this.top = -1; 
        }
    public stack(int capacity) {
         this.capacity = capacity; 
         this.elements = new int[capacity]; 
         this.top = -1; 
        }

    public void push(int value) {
        if (top == capacity - 1) {
            resize();
        }
        System.out.println("Pushing " + value + "...");
        elements[++top] = value;
        animate("Pushed " + value);
    }

    public int pop() {
        if (isEmpty()) {
            System.out.println(ConsoleUtils.ANSI_RED + "Stack Underflow!" + ConsoleUtils.ANSI_RESET);
            return -1;
        }
        System.out.println("Popping " + elements[top] + "...");
        int value = elements[top];
        elements[top] = 0; 
        top--;
        animate("Popped " + value);
        return value;
    }

    public int peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return -1;
        }
        System.out.println("Peeking: " + elements[top]);
        return elements[top];
    }

    public boolean isEmpty() { return top == -1; }
    public int size() { return top + 1; }

    private void resize() {
         capacity = capacity * 2; 
         int[] newElements = new int[capacity]; 
         for (int i = 0; i <= top; i++)
             newElements[i] = elements[i];
         elements = newElements; 
        System.out.println("Resized stack to capacity " + capacity);
    }

    private void animate(String message) {
        ConsoleUtils.clearScreen();
        printStack();
        System.out.println("\n" + message);
        ConsoleUtils.sleep(800);
    }

    public void printStack() {
        System.out.println("Stack Capacity: " + capacity);
        System.out.println();

        for (int i = capacity - 1; i >= 0; i--) {
            if (i > top) {
                System.out.println("   |    |");
            } else {
                System.out.printf("   | %-2d |", elements[i]);
                if (i == top) {
                    System.out.print(" <-- TOP");
                }
                System.out.println();
            }
        }
        System.out.println("   ------");
    }
}
