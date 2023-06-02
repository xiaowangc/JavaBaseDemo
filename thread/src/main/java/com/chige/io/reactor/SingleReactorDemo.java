package com.chige.io.reactor;

/**
 * 单线程反应器Reactor
 *
 * @author wangyc
 * @date 2022/4/19
 */
public class SingleReactorDemo {

    public static void serverReactor() {
        Thread.currentThread().isDaemon();
    }

    private static void two(int[] nums1, int m, int[] nums2, int n) {
        int left = m - 1;
        int rigth = n - 1;
        int len = m + n - 1;
        while(left >= 0 && rigth >= 0) {
            if(nums1[left] >= nums2[rigth]) {
                nums1[len--] = nums1[left--];
                continue;
            }
            nums1[len--] = nums2[rigth--];
        }
        if(left <= 0) {
            for(int i = 0; i <= rigth; i++) {
                nums1[i] = nums2[i];
            }
        }
    }

    public static void main(String[] args) {
        int[] nums1 = {0};
        int m = 0;
        int[] nums2 = {1};
        int n = 1;
        two(nums1, m, nums2, n);
        for (int i:nums1) {
            System.out.println(i);
        }
    }
}
