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

    public T removeMinimum(){
        if(root == null){
            throw new RuntimeException("Empty collection exception.");
        }
        T value = root.getElement();
        if(getSize() == 1){
            root = null;
            last = null;
        }
        else{
            MyHeapNode<T> newLast = getNewLastNode();
            if(last.getParent().getLeftNode() == last){
                last.getParent().setLeftNode(null);
            }
            else{
                last.getParent().setRightNode(null);
            }
            ((MyHeapNode<T>)root).setElement(last.getElement());
            last = newLast;
            alterHeap();
        }

        return value;
    }

    private MyHeapNode<T> getNewLastNode(){
        if(root == null){
            throw new RuntimeException("Empty collection exception.");
        }
        while(last != root && last == last.getParent().getLeftNode()){
            last = last.getParent();
        }
        if(last != root){
            last = (MyHeapNode<T>)last.getParent().getLeftNode();
        }
        while(last.getRightNode() != null){
            last = (MyHeapNode<T>)last.getRightNode();
        }
        return last;
    }

    private void alterHeap(){
        T temp;
        MyHeapNode<T> node = root;
        MyHeapNode<T> left = (MyHeapNode<T>)node.getLeftNode();
        MyHeapNode<T> right = (MyHeapNode<T>)node.getRightNode();
        MyHeapNode<T> next;

        if((left == null) && (right == null))
            next = null;
        else if(right == null)
            next = left;
        else if(((Comparable)left.getElement()).compareTo(right.getElement()) < 0)
            next = left;
        else
            next = right;
        temp = node.getElement();
        while((next != null) && ((Comparable)next.getElement()).compareTo(temp) < 0){
            node.setElement(next.getElement());
            node = next;
            left = (MyHeapNode<T>) node.getLeftNode();
            right = (MyHeapNode<T>) node.getRightNode();
            if((left == null) && (right == null))
                next = null;
            else if(right == null)
                next = left;
            else if(((Comparable)left.getElement()).compareTo(right.getElement()) < 0)
                next = left;
            else
                next = right;
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
