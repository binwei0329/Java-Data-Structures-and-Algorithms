/**
 * 本类实现了一些主流的排序方法
 * @author Wei Bin
 * 2020/11/26
 */

public class JavaSortExample {
    public static void main(String[] args){
        int[] array = new int[]{1, 8, 3, 6, 7, 5};
//        bubbleSort(array);
//        insertionSort(array);
//        selectionSort(array);
//        quickSort(array, 0, array.length - 1);
        mergeSort(array, 0, array.length - 1);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();

    }

    /**
     * 本方法对传入的数据进行冒泡排序
     * @param array 被排序的数组
     */
    public static void bubbleSort(int[] array){
        for(int m = 0; m < array.length - 1; m++){
            for(int n = m + 1; n < array.length; n++){
                if(array[m] > array[n]){
                    int temp = array[m];
                    array[m] = array[n];
                    array[n] = temp;
                }
            }
        }
    }

    /**
     * 本方法对数据进行插入排序
     * @param array 数据
     */
    public static void insertionSort(int[] array){
        //从第二个元素开始
        for(int j = 1; j < array.length; j++){
            //如果当前数字比前一个小
            if(array[j] < array[j - 1]){
                //将当前比较小的元素另外存储起来
                int temp = array[j];
                int k;
                //对之前排列好的数据中的元素进行注意比较，并且将
                //比当前元素大的元素集体向右移
                for(k = j - 1; k >= 0 && temp < array[k]; k--){
                    array[k + 1] = array[k];
                }
                //找到合适的位置插入元素
                array[k + 1] = temp;
            }
        }
    }

    /**
     * 选择排序
     * @param array 数据
     */
    public static void selectionSort(int[] array){
        for(int i = 0; i < array.length; i++){
            int minIndex = i;
            //将当前的数和之后的每个数进行比较，如果后面的数小，就交换
            for(int j = i + 1; j< array.length; j++){
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

    public static void quickSort(int[] array, int left, int right){
        if(left > right)
            return;
        //将一个数组中的最左面的的元素当做基准数
        int base = array[left];
        int i = left;
        int j = right;

        //当i j 不相等时，分别从数组的左面和右面遍历
        while(i != j) {
            while (array[j] >= base && i < j) {
                j--;
            }
            while (array[i] <= base && i < j) {
                i++;
            }

            //在i和j交汇时，交换两个元素
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        //当数组中的第i个元素大于基准数时，和基准数进行交换
        array[left] = array[i];
        array[i] = base;

        //再1️⃣相同的方法对数组中剩余的元素进行处理
        quickSort(array, left, i - 1);
        quickSort(array, i + 1, right);
    }

    public static void mergeSort(int[] array, int low, int high){
        if(low < high){
            //递归调用排序方法，最后合并
            int mid = (low + high) / 2;
            mergeSort(array, low, mid);
            mergeSort(array, mid + 1 , high);
            merge(array, low, high);
        }
    }

    /**
     * 本方法将归并排序中的合并操作实现了
     * @param array 待合并数组
     * @param low 数组起点
     * @param high 数组终点
     */
    private static void merge(int[] array, int low, int high){
        //临时数组用来储存数据
        int[] temp = new int[high - low + 1];
        //找到中间位置的数
        int mid = (high + low) / 2;
        //分别从开始位置和中间位置进行处理
        int i = low;
        int j = mid + 1;
        //临时数组中的起始位置
        int index = 0;
        //从数组的起始位置和中间位置开始检索
        while(i <= mid && j <= high) {
            //分别将前半部分的数据和后半部分的数据进行比较
            //如果前面的小于后面的，就将该数据放入临时数组中，
            //并继续在前半部分中向后遍历，直到前半部分里的某个数据
            //大于后半部分的某个数据
            if (array[i] <= array[j]) {
                temp[index] = array[i];
                i++;
            }
            //如果是另外一种情况的话，将后半部分的数据放入临时数组当中
            else {
                temp[index] = array[j];
                j++;
            }
            index++;
        }

        //如果前半部分的数据已经全部放入临时数组中了，
        //而后半部分还有多余数据，则将剩下的数据依次放入临时数组中
        while(j <= high){
            temp[index] = array[j];
            j++;
            index++;
        }
        //反之如果前面部分多余的数据放入临时数组中
        while(i <= mid){
            temp[index] = array[i];
            i++;
            index++;
        }
        //将已经排好顺序的数据从low的位置开始依次放入原数组中
        for(int k = 0; k < temp.length; k++){
            array[k + low] = temp[k];
        }
    }
}
