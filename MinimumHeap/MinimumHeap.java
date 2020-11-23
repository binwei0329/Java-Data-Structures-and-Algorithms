import java.util.ArrayList;

public class MinimumHeap<T>  {
    MyHeapNode<T> root;
    MyHeapNode<T> last;
    public MinimumHeap(){}

    public void addElement(T ele){
        MyHeapNode<T> node = new MyHeapNode<>(ele);
        if(root == null){
            root = node;
        }
        else{
            MyHeapNode<T> parent =  getParentNode();
            if(parent.getLeftNode() == null){
                parent.setLeftNode(node);
            }
            else{
                parent.setRightNode(node);
            }
            node.setParent(parent);

        }
        last = node;
        if(getSize() > 1){
            swap();
        }
    }

    private MyHeapNode<T> getParentNode(){
        MyHeapNode<T> target = last;
        while((target != root) && target != target.getParent().getLeftNode()){
            target = target.getParent();
        }

        if(target != root){
            if(target.getParent().getRightNode() == null){
                target = target.getParent();
            }
            else{
                target = (MyHeapNode<T>) target.getParent().getRightNode();
                while(target.getLeftNode() != null){
                    target = (MyHeapNode<T>)target.getLeftNode();
                }
            }
        }
        else{
            while(target.getLeftNode() != null){
                target = (MyHeapNode<T>) target.getLeftNode();
            }
        }
        return target;
    }

    private void swap(){
        MyHeapNode<T> node = last;
        T temp = node.getElement();
        while((node != null) && ((Comparable) temp).compareTo(node.getParent().getElement()) < 0){
            node.setElement(node.getParent().getElement());
            node = node.getParent();
        }
        node.setElement(temp);
    }

    public int getSize(){
        return root == null ? 0 : preorderTraversal().size();
    }

    public ArrayList<T> preorderTraversal(){
        if(root == null){
            return null;
        }
        else{
            ArrayList<T> list = new ArrayList<>();
            return preorder(list, root);
        }
    }

    private ArrayList<T> preorder(ArrayList<T> list, MyHeapNode node){
        if(node != null){
            list.add((T)node.getElement());
            preorder(list, (MyHeapNode<T>)node.getLeftNode());
            preorder(list, (MyHeapNode<T>)node.getRightNode());
        }
        return list;
    }
}
