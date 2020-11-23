/**
 * 本类实现了二叉树中的结点。
 * @param <T> 类型参数
 * @author Wei Bin
 * @date 2020/11/23
 */
public class MyBinaryTreeNode<T> {
    private T element;
    private MyBinaryTreeNode<T> leftNode;
    private MyBinaryTreeNode<T> rightNode;

    public MyBinaryTreeNode(T data) {
        element = data;
        leftNode = null;
        rightNode = null;
    }

    public MyBinaryTreeNode(T data, MyBinaryTreeNode<T> left, MyBinaryTreeNode<T> right) {
        element = data;
        leftNode = left;
        rightNode = right;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T ele){
        element = ele;
    }

    public void setLeftNode(MyBinaryTreeNode<T> left) {
        leftNode = left;
    }

    public void setRightNode(MyBinaryTreeNode<T>right) {
        rightNode = right;
    }

    public MyBinaryTreeNode<T> getRightNode() {
        return rightNode;
    }

    public MyBinaryTreeNode<T> getLeftNode() {
        return leftNode;
    }
}
