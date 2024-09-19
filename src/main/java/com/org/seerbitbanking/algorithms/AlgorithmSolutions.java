package com.org.seerbitbanking.algorithms;

import java.util.ArrayList;
import java.util.List;
public class AlgorithmSolutions {

    // Problem 1: Merge overlapping intervals
    /**
     * Merges overlapping intervals from a given list of intervals.
     * Assumes that the input intervals are sorted by their start times.
     *
     * @param intervals List of intervals represented as int[] where intervals[i][0] is the start and intervals[i][1] is the end.
     * @return A merged list of intervals.
     */
    public List<int[]> mergeIntervals(int[][] intervals) {
        List<int[]> merged = new ArrayList<>();

        // Edge case: If the interval list is empty, return an empty list
        if (intervals.length == 0) {
            return merged;
        }

        // Initialize the first interval as the current interval to compare with
        int[] currentInterval = intervals[0];
        merged.add(currentInterval);

        // Iterate through the rest of the intervals
        for (int[] interval : intervals) {
            // If the current interval overlaps with the next, merge them
            if (currentInterval[1] >= interval[0]) {
                currentInterval[1] = Math.max(currentInterval[1], interval[1]);
            } else {
                // If no overlap, move to the next interval
                currentInterval = interval;
                merged.add(currentInterval);
            }
        }

        return merged;
    }

    // Problem 2: Subarray with maximum XOR
    /**
     * Finds the subarray with the maximum XOR value in a given array.
     *
     * @param arr The input array of integers.
     * @return The maximum XOR value of any subarray.
     */
    public int maxXorSubarray(int[] arr) {
        int max = 0;
        int sum = 0;
        // Initialize a list to keep track of prefixes of XORs
        List<Integer> prefix = new ArrayList<>();
        prefix.add(0); // Initialize with 0 to handle single element subarrays

        for (int num : arr) {
            // Calculate the cumulative XOR sum for the current element
            sum ^= num;
            prefix.add(sum);

            // Now find the maximum XOR by comparing with previous prefix XORs
            for (int value : prefix) {
                max = Math.max(max, sum ^ value);
            }
        }

        return max;
    }

    public static void main(String[] args) {
        AlgorithmSolutions algorithmSolutions = new AlgorithmSolutions();

        // Test for merging intervals
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        List<int[]> mergedIntervals = algorithmSolutions.mergeIntervals(intervals);
        System.out.println("<====== Merged Intervals: ======>");
        for (int[] interval : mergedIntervals) {
            System.out.println("[" + interval[0] + ", " + interval[1] + "]");
        }

        // Test for maximum XOR subarray
        int[] arr = {1, 2, 3, 4};
        int maxXor = algorithmSolutions.maxXorSubarray(arr);
        System.out.println("Maximum XOR Subarray Value: ====> " + maxXor);
    }
}
