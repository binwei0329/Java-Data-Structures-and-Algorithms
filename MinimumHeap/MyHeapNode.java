/**
 * 本类实现了最小堆(minimum heap)的结点。
 * @param <T> 类型参数
 * @author Wei Bin
 * @date 2020/11/23
 */
public class MyHeapNode<T> extends MyBinaryTreeNode<T> {
    public MyHeapNode<T> parent;
    public MyHeapNode(T data) {
        super(data);
        parent = null;
    }

    public MyHeapNode<T> getParent(){
        return parent;
    }

    public void setParent(MyHeapNode<T> node){
        parent = node;
    }

    @Override
    public void setElement(T ele) {
        super.setElement(ele);
    }
}
