package com.desaco.fundroid.test_folder;

/**
 * @author dengwen
 * @date 2020/5/21.
 */
public class TestFolder {

    /**
     * 斐波那契数列
     *
     * @param n, 输入整数n项
     * @return
     */
    public long fibonacci(int n) {
        int[] a = new int[2];
        a[0] = 0; // 数组的第一个元素
        a[1] = 1; // 数组的第二个元素
        if (n < 2) {
            return a[n];
        }
        int fiboOne = 1;
        int fiboTwo = 0;
        int fiboN = 0;
        for (int i = 2; i <= n; i++) {
            fiboN = fiboOne + fiboTwo;
            fiboTwo = fiboOne;
            fiboOne = fiboN;
        }
        return fiboN;
    }
}
