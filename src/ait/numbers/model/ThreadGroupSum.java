package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

import java.util.ArrayList;
import java.util.List;

public class ThreadGroupSum extends GroupSum {
    public ThreadGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        List<OneGroupSum> tasks = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        // Create and start threads
        for (int[] group : numberGroups) {
            OneGroupSum task = new OneGroupSum(group);
            tasks.add(task);
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        // Calculate total sum
        int totalSum = 0;
        for (OneGroupSum task : tasks) {
            totalSum += task.getSum();
        }

        return totalSum;
    }
}
