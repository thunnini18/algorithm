import java.util.Scanner;

class jaccard{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double standard = scanner.nextDouble();
        int n = scanner.nextInt();
        scanner.nextLine(); 

        String[][] sentence = new String[n][];

        for (int i = 0; i < n; i++) {
            sentence[i] = removeAndSort(scanner.nextLine().toLowerCase().split(" "));
        }
        scanner.close();

        int count = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (computeJaccard(sentence[i], sentence[j]) > standard) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    static String[] removeAndSort(String[] words) {
        if (words.length == 0) return new String[0];

        quicksort(words, 0, words.length - 1);

        int count = 1;
        for (int i = 1; i < words.length; i++) {
            if (!words[i].equals(words[i - 1])) {
                words[count++] = words[i];
            }
        }
        String[] uniqueWords = new String[count];
        System.arraycopy(words, 0, uniqueWords, 0, count);
        return uniqueWords;
    }

    static double computeJaccard(String[] set1, String[] set2) {
        int i = 0, j = 0, intersection = 0, union = set1.length + set2.length;

        while (i < set1.length && j < set2.length) {
            int cmp = set1[i].compareTo(set2[j]);
            if (cmp < 0) {
                i++;
            } else if (cmp > 0) {
                j++;
            } else {
                i++;
                j++;
                intersection++;
            }
        }

        return (double) intersection / (union - intersection);
    }

    static void quicksort(String[] words, int left, int right) {
        while (right - left > 10) {  
            int pivotIndex = medianOfThree(words, left, right);
            pivotIndex = partition(words, left, right, pivotIndex);
            if (pivotIndex - left < right - pivotIndex) {
                quicksort(words, left, pivotIndex - 1);
                left = pivotIndex + 1;
            } else {
                quicksort(words, pivotIndex + 1, right);
                right = pivotIndex - 1;
            }
        }
        insertionSort(words, left, right);
    }

    static int partition(String[] words, int left, int right, int pivotIndex) {
        String pivot = words[pivotIndex];
        swap(words, pivotIndex, right); 
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (words[j].compareTo(pivot) < 0) {
                i++;
                swap(words, i, j);
            }
        }
        swap(words, i + 1, right);
        return i + 1;
    }

    static int medianOfThree(String[] words, int left, int right) {
        int mid = left + (right - left) / 2;
        if (words[left].compareTo(words[mid]) > 0) swap(words, left, mid);
        if (words[left].compareTo(words[right]) > 0) swap(words, left, right);
        if (words[mid].compareTo(words[right]) > 0) swap(words, mid, right);
        return mid; 
    }

    static void insertionSort(String[] words, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            String key = words[i];
            int j = i - 1;
            while (j >= left && words[j].compareTo(key) > 0) {
                words[j + 1] = words[j];
                j--;
            }
            words[j + 1] = key;
        }
    }

    static void swap(String[] words, int i, int j) {
        String temp = words[i];
        words[i] = words[j];
        words[j] = temp;
    }
}