/**
 * 本类实现了一些主流的排序方法
 * @author Wei Bin
 * 2020/11/26
 */

public class JavaSortExample {
    public static void main(String[] args){
        int[] array = new int[]{1, 8, 3, 6, 7, 5};
//        bubbleSort(array);
        insertionSort(array);
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
}
