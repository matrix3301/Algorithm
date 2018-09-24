package com.Algorithm.Sort;

import org.junit.Test;

/**
 * @author Rex Tan
 * @version 1.0
 * @since 2018/9/11
 */
public class sort {
    public static void main(String[] args) {
        int[] list = {23, 64, 223, 346, 27, 92, 46, 10, 78, 86};
        long startTime = System.currentTimeMillis();
        System.out.println("排序前");
        for (int i : list) {
            System.out.print(i + ",");
        }
        System.out.println();

        //冒泡排序
//        bubbleSort(list);

        //选择排序法，等价于下述的Arrays工具包里的sort功能。 Arrays.sort(list);
//        selectSort(list, "ASC");

        //快速排序调用
//        quickSort(list, 0, list.length - 1);

        //堆排序调用
        heapSort(list);

        System.out.println("排序后：" + (System.currentTimeMillis() - startTime));
        for (int i : list) {
            System.out.print(i + ",");
        }
        System.out.println();
    }

    //冒泡排序
    /*
    冒泡排序算法的原理如下：
    1、比较相邻的元素。如果第一个比第二个大，就交换他们两个。
    2、对每一对相邻元素做同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
    3、针对所有的元素重复以上的步骤，除了最后一个。
    4、持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     */
    static void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    arr[j] ^= arr[j + 1];
                    arr[j + 1] ^= arr[j];
                    arr[j] ^= arr[j + 1];
                }
            }
        }
    }

    //选择排序法，将数组中的每个元素与其后面的元素逐一比较，如果未满足排序规则，就立刻更换位置，重复执行。再次循环时，从第二位开始往复。
    static void selectSort(int[] arr, String orderby) {
        switch (orderby) {
            case "ASC":
                for (int i = 0; i < arr.length - 1; i++) {
                    for (int j = i + 1; j < arr.length; j++) {
                        if (arr[i] > arr[j]) {
                            //通过位异或赋值进行变量值互换
                            arr[i] ^= arr[j];
                            arr[j] ^= arr[i];
                            arr[i] ^= arr[j];
                        }
                    }
                }
                break;
            case "DESC":
                for (int i = 0; i < arr.length - 1; i++) {
                    for (int j = i + 1; j < arr.length; j++) {
                        if (arr[i] < arr[j]) {
                            //通过位异或赋值进行变量值互换
                            arr[i] ^= arr[j];
                            arr[j] ^= arr[i];
                            arr[i] ^= arr[j];
                        }
                    }
                }
                break;
        }
    }

    //快速排序算法
    /*
    基本思想是：通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小，然后再按此方法对这两部
    分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
     */
    static void quickSort(int[] arr, int low, int high) {
        int l = low;
        int h = high;
        int key = arr[l];

        while (l < h) {
            //从右往左查找
            while (l < h) {
                if (key > arr[h]) {
                    //执行一次交换
                    arr[l] ^= arr[h];
                    arr[h] ^= arr[l];
                    arr[l] ^= arr[h];
                    l++;
                    break;
                }
                h--;
            }
            //从左往右查找
            while (l < h) {
                if (key < arr[l]) {
                    //执行一次交换
                    arr[l] ^= arr[h];
                    arr[h] ^= arr[l];
                    arr[l] ^= arr[h];
                    h--;
                    break;
                }
                l++;
            }
        }
        //如果中心点不在最左侧，则需要对中心点左侧继续进行快速排序
        if (l > low) {
            quickSort(arr, low, l - 1);
        }
        //如果中心点不在最右侧，则需要对中心点右侧继续进行快速排序
        if (l < high) {
            quickSort(arr, l + 1, high);
        }
    }

    //堆排序
    /*
     原理：
     */
    static void heapSort(int[] arr) {
        //将数组首先进行一次标准的大根堆构建
        buildMaxHeapify(arr);

        //每次取出大根堆根节点的数值与当前堆深度末端的数值互换，然后进行堆深度-1的重复操作。
        for (int i = arr.length - 1; i > 0; i--) {
            arr[0] ^= arr[i];
            arr[i] ^= arr[0];
            arr[0] ^= arr[i];
            maxHeapify(arr, i, 0);
        }


    }

    //构建大根堆
    private static void buildMaxHeapify(int[] data) {
        //从最后一个父节点开始往上构建大根堆
        int startIndex = getParentIndex(data.length - 1);
        //从尾端开始构建，循环一次后将得到一个完整准确的大根堆
        for (int i = startIndex; i >= 0; i--) {
            maxHeapify(data, data.length, i);
        }

    }

    //大根堆排序
    /*思想：
      传入一个数组、堆的最大高度（深度）和一个父节点。
      1、判断其子节点是否有比父节点更大的值，如果有则进行更换。
      2、如果有过更换操作，则需要将更换过的子节点，作为一个新的父节点继续检查其后续子节点是否满足同样的大根堆条件（递归）
     */
    private static void maxHeapify(int[] data, int heapsize, int index) {
        int left = getLeftChildIndex(index);
        int right = getRightChildIndex(index);
        int largest = index;
        //分别将左右子节点与父节点进行比较，而堆深度用于判断其子节点是否已经超越边界，如果已经超越则没有继续比较的意义。
        if (left < heapsize && data[left] > data[largest]) {
            largest = left;
        }
        if (right < heapsize && data[right] > data[largest]) {
            largest = right;
        }
        //如果最大值的位置需要变动，则变动后，要继续检查新子节点位置上的大根堆是否满足条件，也就是递归调用大根堆排序。
        if (largest != index) {
            data[index] ^= data[largest];
            data[largest] ^= data[index];
            data[index] ^= data[largest];
            maxHeapify(data, heapsize, largest);
        }
    }

    //获得父节点的下标
    private static int getParentIndex(int currIndex) {
        return (currIndex - 1) >> 1;    //当前节点-1后整除2，便是其二叉树的父节点下标位置
    }

    //获得左叶子节点的下标
    private static int getLeftChildIndex(int currIndex) {
        return (currIndex << 1) + 1;    //当前节点乘2后+1，便是其二叉树子节点的左叶
    }

    //获得右叶子节点的下标
    private static int getRightChildIndex(int currIndex) {
        return (currIndex << 1) + 2;    //当前节点乘2后+1，便是其二叉树子节点的右叶
    }

    @Test
    public void testCode() {

    }
}
