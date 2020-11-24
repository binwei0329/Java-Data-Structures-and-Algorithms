import java.util.Iterator;

public class MyGraph<T> implements MyGraphInterface<T> {
    protected final int DEFAULT_CAPACITY = 8;
    protected int numVertices; //顶点数量
    protected boolean adjMat[][]; //邻接矩阵
    protected T[] vertices; //顶点

    public MyGraph(){
        numVertices = 0;
        adjMat = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public MyGraph(int numVer){
        adjMat = new boolean[numVer][numVer];
        vertices = (T[])(new Object[numVer]);
    }

    @Override
    public void addVertex(T ele) {
        if(numVertices == 0){
            vertices[0] = ele;
            numVertices++;
        }
        else{
            vertices[numVertices] = ele;
            numVertices++;
        }
    }

    @Override
    public void removeVertex(T ele) {
        if(numVertices == 0){
            throw new RuntimeException("Empty graph exception.");
        }
        else{
            int index = getIndex(ele);
            if(index == -1){
                throw new RuntimeException("Vertex not found exception.");
            }
            else {
                vertices[index] = null;

                for(int i = index; i < numVertices - 1; i++){
                    vertices[i] = vertices[i + 1];
                }
            }
        }
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
        for(int ind = 0; ind < numVertices; ind++){
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
        return false;
    }

    @Override
    public int getSize() {
        return numVertices;
    }
}
