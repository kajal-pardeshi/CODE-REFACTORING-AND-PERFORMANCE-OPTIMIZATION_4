import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CodeRefactoringExample {

    public static void main(String[] args) {
        // Original Inefficient Code
        List<Integer> numbers = generateLargeList(1_000_000);
        long startTime = System.currentTimeMillis();
        List<Integer> evenNumbers = findEvenNumbersInefficient(numbers);
        long endTime = System.currentTimeMillis();
        long originalTime = endTime - startTime;

        // Refactored Optimized Code
        startTime = System.currentTimeMillis();
        List<Integer> optimizedEvenNumbers = findEvenNumbersOptimized(numbers);
        endTime = System.currentTimeMillis();
        long optimizedTime = endTime - startTime;

        // Generate Report
        generatePerformanceReport(originalTime, optimizedTime, evenNumbers, optimizedEvenNumbers);
    }

    // Generate a large list of integers
    private static List<Integer> generateLargeList(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        return list;
    }

    // Inefficient method to find even numbers
    private static List<Integer> findEvenNumbersInefficient(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        for (Integer num : numbers) {
            if (num % 2 == 0) {
                result.add(num);
            }
        }
        return result;
    }

    // Optimized method to find even numbers using Streams
    private static List<Integer> findEvenNumbersOptimized(List<Integer> numbers) {
        return numbers.stream()
                .parallel()
                .filter(num -> num % 2 == 0)
                .collect(Collectors.toList());
    }

    // Generate a performance report
    private static void generatePerformanceReport(long originalTime, long optimizedTime,
                                                  List<Integer> originalOutput, List<Integer> optimizedOutput) {
        StringBuilder report = new StringBuilder();
        report.append("Code Refactoring and Performance Optimization Report\n");
        report.append("=====================================================\n");
        report.append("1. Original Method:\n");
        report.append("   - Time Taken: ").append(originalTime).append(" ms\n");
        report.append("   - Output Size: ").append(originalOutput.size()).append("\n");
        report.append("\n2. Refactored Optimized Method:\n");
        report.append("   - Time Taken: ").append(optimizedTime).append(" ms\n");
        report.append("   - Output Size: ").append(optimizedOutput.size()).append("\n");
        report.append("\n3. Performance Improvement:\n");
        long improvement = originalTime - optimizedTime;
        double percentage = ((double) improvement / originalTime) * 100;
        report.append("   - Improvement: ").append(improvement).append(" ms (").append(String.format("%.2f", percentage)).append("%)\n");

        // Save report to a file
        try (FileWriter writer = new FileWriter("PerformanceReport.txt")) {
            writer.write(report.toString());
            System.out.println("Performance report generated: PerformanceReport.txt");
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }
}
