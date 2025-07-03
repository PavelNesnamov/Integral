package ait.numbers;

import ait.numbers.model.ExecutorGroupSum;
import ait.numbers.model.GroupSum;
import ait.numbers.model.ParallelStreamGroupSum;
import ait.numbers.model.ThreadGroupSum;
import ait.numbers.test.GroupSumPerformanceTest;

import java.util.Random;

public class GroupSumAppl {
    private static final int N_GROUPS = 10_000;
    private static final int NUMBERS_PER_GROUP = 10_000;
    private static final int[][] numbers = new int[N_GROUPS][NUMBERS_PER_GROUP];
    private static Random random = new Random();

    public static void main(String[] args) {
        fillArray();
        GroupSum theadGroupSum = new ThreadGroupSum(numbers);
        GroupSum executorGroupSum = new ExecutorGroupSum(numbers);
        GroupSum streamGroupSum = new ParallelStreamGroupSum(numbers);
        new GroupSumPerformanceTest("ThreadGroupSum", theadGroupSum).runTest();
        new GroupSumPerformanceTest("ExecutorGroupSum", executorGroupSum).runTest();
        new GroupSumPerformanceTest("ParallelStreamGroupTest" , streamGroupSum).runTest();
    }
    private static void fillArray() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                numbers[i][j] = random.nextInt();
            }
        }
    }
}
