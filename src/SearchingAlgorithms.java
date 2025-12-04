public class SearchingAlgorithms {

    private static final int STEP_DELAY_MS = 500; 
    private static final int COL_WIDTH = 4; 

    
    public static int linearSearchNoClear(int[] arr, int target) {
        printFullArray(arr);

        
        String caret = buildCaretLine(arr.length, -1);
        System.out.print(caret);
        System.out.flush();

        for (int i = 0; i < arr.length; i++) {
            caret = buildCaretLine(arr.length, i);
            System.out.print('\r' + caret);
            System.out.flush();
            ConsoleUtils.sleep(STEP_DELAY_MS);

            if (arr[i] == target) {
                System.out.println(); 
                System.out.println("Found at index: " + i);
                return i;
            }
        }

        
        System.out.print('\r' + buildCaretLine(arr.length, -1));
        System.out.println();
        System.out.println("Element " + target + " not found in array");
        return -1;
    }

    private static String buildCaretLine(int len, int caretIndex) {
        int total = len * COL_WIDTH;
        char[] line = new char[total];
        for (int i = 0; i < total; i++) line[i] = ' ';
        if (caretIndex >= 0 && caretIndex < len) {
            int pos = caretIndex * COL_WIDTH + (COL_WIDTH / 2);
            if (pos >= total) pos = total - 1;
            line[pos] = '^';
        }
        return new String(line);
    }

    
    public static int binarySearchNoClear(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        printFullArray(arr);

        String active = buildActiveHalfLine(arr, -1, -2);
        System.out.print(active);
        System.out.flush();
        ConsoleUtils.sleep(STEP_DELAY_MS);

        while (left <= right) {
            int mid = left + (right - left) / 2;

            active = buildActiveHalfLine(arr, left, right);
            System.out.print('\r' + active);
            System.out.flush();
            ConsoleUtils.sleep(STEP_DELAY_MS);

            if (arr[mid] == target) {
                System.out.println();
                System.out.println("Found at index: " + mid);
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        
        System.out.print('\r' + buildActiveHalfLine(arr, -1, -2));
        System.out.println();
        System.out.println("Element " + target + " not found in array");
        return -1;
    }

    private static String buildActiveHalfLine(int[] arr, int left, int right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i >= left && i <= right) sb.append(String.format("%" + COL_WIDTH + "d", arr[i]));
            else sb.append(String.format("%" + COL_WIDTH + "s", ""));
        }
        return sb.toString();
    }
    private static void printFullArray(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int v : arr) sb.append(String.format("%" + COL_WIDTH + "d", v));
        System.out.println(sb.toString());
    }
}
