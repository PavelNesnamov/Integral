package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorGroupSum extends GroupSum {
    public ExecutorGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<OneGroupSum> tasks = new ArrayList<>();

        // Create and submit tasks
        for (int[] group : numberGroups) {
            OneGroupSum task = new OneGroupSum(group);
            tasks.add(task);
            executorService.execute(task);
        }

        // Shutdown executor and wait for all tasks to complete
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        // Calculate total sum
        int totalSum = 0;
        for (OneGroupSum task : tasks) {
            totalSum += task.getSum();
        }

        return totalSum;
    }
}
