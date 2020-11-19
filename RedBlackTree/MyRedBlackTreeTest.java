import java.util.Scanner;

public class MyRedBlackTreeTest {
    public static void main(String[] args) {
        insertOpt();
    }

    /**
     * 插入操作
     */
    public static void insertOpt(){
        Scanner scanner=new Scanner(System.in);
        MyRedBlackTree<String,Object> rbt = new MyRedBlackTree<>();
        while (true){
            System.out.println("请输入你要插入的节点:");
            String key=scanner.next();
            System.out.println();
            rbt.put(key.length() == 1 ? ("0"+key) : key,null);
            MyRedBlackTreeOperation.show(rbt.getRoot());
        }
    }
}
