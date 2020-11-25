import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 *本类实现了图这个数据结构以及其主要方法。
 *
 * @param <T> 类型参数
 * @author Wei Bin
 * @date 2020/11/24
 */

@SuppressWarnings("all")
public class MyGraph<T> implements MyGraphInterface<T> {
    protected final int DEFAULT_CAPACITY = 8;
    protected int num; //顶点数量
    protected int adjMat[][]; //邻接矩阵
    protected T[] vertices; //顶点

    //默认构造方法
    public MyGraph(){
        num = 0;
        adjMat = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    //带参构造方法
    public MyGraph(int numVer){
        adjMat = new int[numVer][numVer];
        vertices = (T[])(new Object[numVer]);
    }

    /**
     * 向图中添加顶点
     * @param ele 存储在顶点中的元素
     */
    @Override
    public void addVertex(T ele) {
        if(num == 0){
            vertices[0] = ele;
            num++;
        }
        else{
            vertices[num] = ele;
            num++;
        }
    }

    /**
     * 从图中删除顶点
     * @param ele 需要删除的顶点的元素
     */
    @Override
    public void removeVertex(T ele) {
        if(num == 0){
            throw new RuntimeException("Empty graph exception.");
        }
        else{
            int index = getIndex(ele); //获取要删除顶点的下标
            if(index == -1){
                throw new RuntimeException("Vertex not found exception.");
            }
            else {
                //删除顶点，并将与该顶点相连接的边都移除
                vertices[index] = null;
                for(int i = index; i < num - 1; i++){
                    vertices[i] = vertices[i + 1];
                }
                vertices[num - 1] = null;
                adjMat = removeVertexEdge(index);
                num--;
            }
        }
    }

    /**
     * 本方法删除与某个被删除顶点相连接的边
     * @param index 被删除元素的下标
     * @return 新的邻接矩阵
     */
    private int[][] removeVertexEdge(int index){
        int[][] temp = new int[num - 1][num - 1];
        if(index < 0){
            throw new RuntimeException("Vertex not found exception.");
        }
        else {
            for(int m = 0; m < num; m++){
                if(m < index) {
                    int[] array = new int[num - 1];
                    int[] vec = adjMat[m];
                    for (int k = 0; k < index; k++) {
                        array[k] = vec[k];
                    }
                    for (int k = index; k < num - 1; k++) {
                        array[k] = vec[k + 1];
                    }
                    temp[m] = array;
                }

                else if(m > index){
                    int[] array = new int[num - 1];
                    int[] vec = adjMat[m];
                    for (int k = 0; k < index; k++) {
                        array[k] = vec[k];
                    }
                    for (int k = index; k < num - 1; k++) {
                        array[k] = vec[k + 1];
                    }
                    temp[m - 1] = array;
                }
            }
        }
        return temp;
    }

    /**
     * 添加两个顶点之间的一条边
     * @param ele1 边连接的顶点的元素1
     * @param ele2 边连接的顶点的元素2
     */
    @Override
    public void addEdge(T ele1, T ele2) {
        int ind1 = getIndex(ele1);
        int ind2 = getIndex(ele2);
        if(ind1 < 0 || ind2 < 0){
            throw new RuntimeException("Illegal vertex exception.");
        }
        else{
            adjMat[ind1][ind2] = 1;
            adjMat[ind2][ind1] = 1;
        }
    }

    /**
     * 删除两个顶点之间的边
     * @param ele1 边连接的顶点的元素1
     * @param ele2 边连接的顶点的元素2
     */
    @Override
    public void removeEdge(T ele1, T ele2) {
        int ind1 = getIndex(ele1);
        int ind2 = getIndex(ele2);
        if(ind1 < 0 || ind2 < 0){
            throw new RuntimeException("Illegal vertex exception.");
        }
        else{
            adjMat[ind1][ind2] = 0;
            adjMat[ind2][ind1] = 0;
        }
    }

    @Override
    public ArrayList<T> breadthFirstSearch(int start) {
        Integer index;
        ArrayList<T> list = new ArrayList<>(num);
        //创建一个queue来存储每个顶点的顺序
        MyLinkedQueue<Integer> queue = new MyLinkedQueue<>();
        //创建一个布尔类型的数组用来表示每个顶点是否被遍历过并将
        //其中的每一个值设为false
        boolean[] visited = new boolean[num];
        for(int i = 0; i < num; i++){
            visited[i] = false;
        }

        //将开始顶点的下标放入队列中，并声明该顶点已被遍历过
        queue.enqueue(start);
        visited[start] = true;

        while(!queue.isEmpty()) {
            index = queue.dequeue();
            list.add(vertices[index]);
            //遍历每一个与下标为index的顶点相邻的顶点，如果该顶点还没有被遍历过，
            //将其入列
            for (int i = 0; i < num; i++) {
                if (adjMat[index][i] == 1 && !visited[i]) {
                    queue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return list;
    }

    @Override
    public ArrayList<T> depthFirstSearch(int start) {
        Integer index;
        boolean found = false;
        ArrayList<T> list = new ArrayList<>(num);
        //创建一个stack来存储每个顶点的顺序
        Stack<Integer> stack = new Stack<>();
        //创建一个布尔类型的数组用来表示每个顶点是否被遍历过并将
        //其中的每一个值设为false
        boolean[] visited = new boolean[num];
        for(int i = 0; i < num; i++){
            visited[i] = false;
        }

        //将开始顶点的下标存入栈中，并声明该顶点已被遍历过
        stack.push(start);
        list.add(vertices[start]);
        visited[start] = true;

        while(!stack.isEmpty()){
            index = stack.peek();
            found = false;
            //遍历每一个与下标为index的顶点相邻的顶点，如果该顶点还没有被遍历过，
            //将其入栈
            for(int i = 0; (i < num) && !found; i++){
                if(adjMat[index][i] == 1 && !visited[i]){
                    stack.push(i);
                    list.add(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if(!found && !stack.isEmpty()) {
                stack.pop();
            }
        }
        return list;
    }

    /**
     * 获取某个元素的下标
     * @param ele 元素
     * @return 该元素的下标
     */
    private int getIndex(T ele){
        int index = -1;
        for(int ind = 0; ind < num; ind++){
            if(vertices[ind] == ele){
                index = ind;
                break;
            }
        }
        return index;
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        return null;
    }

    /**
     * 测试图的连通性，当且仅当广度优先搜索中的顶点数目等于图中的顶点数目时，该图才是连通的
     * @return
     */
    @Override
    public boolean isConnected() {
        return breadthFirstSearch(0).size() == num;
    }

    @Override
    public boolean isEmpty() {
        return num == 0;
    }

    @Override
    public int getSize() {
        return num;
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            builder.append(vertices[i] + " ");
        }
        return builder.toString();
    }
}

class MyLinkedQueue<T>{
    private Node<T> head;
    private int size;

    public MyLinkedQueue(){
        head = new Node();
    }

    public boolean enqueue(T data){
        if(this.size == 0){
            head = new Node<>(data);
            size++;
            return true;
        }

        else{
            Node<T> current = head;
            while(current.next != null){
                current = current.next;
            }
            current.next = new Node<>(data);
            this.size++;
            return true;
        }
    }

    public T dequeue(){
        if(this.size == 0){
            return null;
        }
        else{
            T result = head.data;
            head = head.next;
            this.size--;
            return result;
        }
    }

    public int getSize(){
        return this.size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    class Node<T>{
        T data;
        Node<T> next;

        public Node(){}
        public Node(T data){
            this.data = data;
        }
    }
}
