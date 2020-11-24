import java.util.Iterator;

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
    protected boolean adjMat[][]; //邻接矩阵
    protected T[] vertices; //顶点

    public MyGraph(){
        num = 0;
        adjMat = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public MyGraph(int numVer){
        adjMat = new boolean[numVer][numVer];
        vertices = (T[])(new Object[numVer]);
    }

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

    @Override
    public void removeVertex(T ele) {
        if(num == 0){
            throw new RuntimeException("Empty graph exception.");
        }
        else{
            int index = getIndex(ele);
            if(index == -1){
                throw new RuntimeException("Vertex not found exception.");
            }
            else {
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

    private boolean[][] removeVertexEdge(int index){
        boolean[][] temp = new boolean[num - 1][num - 1];
        if(index < 0){
            throw new RuntimeException("Vertex not found exception.");
        }
        else {
            for(int m = 0; m < num; m++){
                if(m < index) {
                    boolean[] array = new boolean[num - 1];
                    boolean[] vec = adjMat[m];
                    for (int k = 0; k < index; k++) {
                        array[k] = vec[k];
                    }
                    for (int k = index; k < num - 1; k++) {
                        array[k] = vec[k + 1];
                    }
                    temp[m] = array;
                }

                else if(m > index){
                    boolean[] array = new boolean[num - 1];
                    boolean[] vec = adjMat[m];
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

    @Override
    public void addEdge(T ele1, T ele2) {
        int ind1 = getIndex(ele1);
        int ind2 = getIndex(ele2);
        if(ind1 < 0 || ind2 < 0){
            throw new RuntimeException("Illegal vertex exception.");
        }
        else{
            adjMat[ind1][ind2] = true;
            adjMat[ind2][ind1] = true;
        }
    }

    @Override
    public void removeEdge(T ele1, T ele2) {
        int ind1 = getIndex(ele1);
        int ind2 = getIndex(ele2);
        if(ind1 < 0 || ind2 < 0){
            throw new RuntimeException("Illegal vertex exception.");
        }
        else{
            adjMat[ind1][ind2] = false;
            adjMat[ind2][ind1] = false;
        }
    }

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
    public Iterator iteratorBFS(T startVertex) {
        return null;
    }

    @Override
    public Iterator iteratorDFS(T startVertex) {
        return null;
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
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
