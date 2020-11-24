import java.util.Iterator;

/**
 * 本文件包含一个图的接口及相关操作
 *
 * @param <T> 类型参数
 * @author Wei Bin
 * @date 2020/11/24
 */

public interface MyGraphInterface<T> {

    /**
     * 添加顶点
     * @param ele 存储在顶点中的元素
     */
    public abstract void addVertex(T ele);

    /**
     * 删除顶点
     * @param ele 需要删除的顶点的元素
     */
    public abstract void removeVertex(T ele);

    /**
     * 根据顶点添加边
     * @param ele1 边连接的顶点的元素1
     * @param ele2 边连接的顶点的元素2
     */
    public abstract void addEdge(T ele1, T ele2);

    /**
     * 根据顶点删除边
     * @param ele1 边连接的顶点的元素1
     * @param ele2 边连接的顶点的元素2
     */
    public abstract void removeEdge(T ele1, T ele2);

    /**
     * 广度优先搜索(breadth first search)
     * @param startVertex 起始顶点
     * @return 广度优先遍历器
     */
    public abstract Iterator iteratorBFS(T startVertex);

    /**
     * 深度优先搜索(breadth first search)
     * @param startVertex 起始顶点
     * @return 深度优先遍历器
     */
    public abstract Iterator iteratorDFS(T startVertex);

    /**
     * 两个顶点之间的最短路径遍历器
     * @param startVertex 起始顶点
     * @param targetVertex 目标顶点
     * @return 两个顶点之间最短路径遍历器
     */
    public Iterator iteratorShortestPath(T startVertex, T targetVertex);

    /**
     * 判断两个节点是否通过边相连
     * @return 布尔值
     */
    public abstract boolean isConnected();
    public abstract boolean isEmpty();
    public abstract int getSize();
    public String toString();
}