/**
 * 本类实现了一个红黑树及其插入删除操作。
 * @param <K> 插入的数据的键的类型
 * @param <V> 插入的数据值的类型
 * @author Wei Bin 魏斌
 * @date 2020/11/19
 */
public class MyRedBlackTree<K extends Comparable<K>, V> {
    private static boolean RED = false;
    private static boolean BLACK = true;
    private RBNode root;

    public MyRedBlackTree(){}

    /**
     * 围绕某个结点p旋转
     *       pf                    pf
     *      /                     /
     *     p                     pr(r)
     *    / \          ==>      / \
     *  pl  pr(r)              p   rr
     *     / \                / \
     *    rl  rr             pl  rl
     * @param p 结点
     */
    private void leftRotate(RBNode p){
        if(p != null){
            //给p的右子结点一个应用
            RBNode r = p.right;
            //旋转之后p的右子结点指向现在的右子结点的左结点
            p.right = r.left;
            //假如现在右子结点的左子结点不为空
            if(r.left != null){
                //把右子结点的父节点设为p
                r.left.parent = p;
            }
            //把r的父节点设置为原来p结点的父节点
            r.parent = p.parent;
            //如原来p结点的父节点为空，则原来p结点是根结点
            if(p.parent == null){
                //经过旋转，r结点变成了新的根节点
                root = r;
            }
            //假如p是其父节点的左子结点，将其父节点的左子结点设为r
            else if(p.parent.left == p){
                p.parent.left = r;
            }
            //否则将右子结点设为r
            else{
                p.parent.right = r;
            }
            //在一起完成之后，把r的左子结点设为p，p的父节点设为r
            r.left = p;
            p.parent = r;
        }
    }

    /**
     * 围绕着结点p进行右旋转
     * @param p 结点
     */
    private void rightRotate(RBNode p){
        if(p != null){
            //赋予p的左子结点一个引用
            RBNode l = p.left;
            //p的左子节点指向l的右子结点
            p.left = l.right;
            //假如l的右子结点不为空，则将l的右子结点的父节点设为p
            if(l.right != null){
                l.right.parent = p;
            }
            //将l的父节点设置为p的父节点
            l.parent = p.parent;
            //假如o的父节点为空，则p为根结点，右旋转将l设为根结点
            if(p.parent == null){
                root = l;
            }
            //如果p是p的父节点的右子结点，则将p的父节点的右子结点设为l
            else if(p.parent.right == p){
                p.parent.right = l;
            }
            //反之将p的父节点的左子结点设为l
            else{
                p.parent.right = l;
            }
            //l的右子结点设为p，p的父节点设为l
            l.right = p;
            p.parent = l;
        }
    }

    /**
     * 向红黑树中插入键值对
     * @param key 键
     * @param value 值
     */
    public void put(K key, V value){
        RBNode t = this.root;
        //如果根结点为空，将插入的结点作为根结点
        if(t == null){
            t = new RBNode<>(key, value == null ? key : value, null);
        }
        int cmp; //比较结果
        RBNode parent; //父节点的指针
        if(key == null){
            throw new NullPointerException();
        }
        do{
            //从根节点开始比较
            parent = t;
            cmp = key.compareTo((K)t.key);
            //如果比0小，在根结点的左子树中查找
            if(cmp < 0){
                t = t.left;
            }
            //否则再右子树中查找
            else if(cmp > 0){
                t = t.right;
            }
            //如果找到一样的键，则将值更新
            else{
                t.value = value;
            }
        } while(t != null);
        //新建一个新的插入结点，父节点是当前的叶子结点
        RBNode node = new RBNode<>(key, value == null ? key : value, parent);
        if(cmp < 0){
            //如果cmp<0，则将新节点设为那个叶子结点的左子结点
            parent.left = node;
        }
        else{
            //否则设为右子结点
            parent.right = node;
        }
    }

    /**
     * 1、2-3-4树：新增元素+2节点合并（节点中只有1个元素）=3节点（节点中有2个元素）
     *    红黑树：新增一个红色节点+黑色父亲节点=上黑下红（2节点）--------------------不要调整
     *
     * 2、2-3-4树：新增元素+3节点合并（节点中有2个元素）=4节点（节点中有3个元素）
     *    这里有4种小情况（左3，右3，还有2个左中右不需要调整）------左3，右3需要调整，其余2个不需要调整
     *    红黑树：新增红色节点+上黑下红=排序后中间节点是黑色，两边节点都是红色（3节点）
     *
     * 3、2-3-4树：新增一个元素+4节点合并=原来的4节点分裂，中间元素升级为父节点，新增元素与剩下的其中一个合并
     *    红黑树：新增红色节点+爷爷节点黑，父节点和叔叔节点都是红色=爷爷节点变红，父亲和叔叔变黑，如果爷爷是根节点，则再变黑
     *
     *
     * @param x
     */
    private void fixAferPut(RBNode x){
        //将被添加结点的颜色设为红色
        x.color = RED;
        //如果父节点为黑色，则不需要调整，直接添加即可
        //如果x不是黑色结点并且x不是根结点，同时x的父节点的颜色为红色
        while(x != null && x != root && x.parent.color == RED){
            //如果x的父节点是爷爷结点的左子结点
            if(parentOf(x) == leftOf(parentOf(parentOf(x)))){
                //给叔叔结点一个引用
                RBNode y = rightOf(parentOf(parentOf(x)));
                //如果叔叔结点的颜色为红色，这对应第三种情形
                if(colorOf(y) == RED){
                    //将x的父亲结点和叔叔节点的颜色设为黑色，爷爷结点设为红色
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    //爷爷节点向上递归
                    x=parentOf(parentOf(x));
                }
                //第二种情形
                else{
                    //如果x是x的父节点的右子结点
                    if(rightOf(parentOf(x)) ==  x){
                        //围绕x的父节点进行左旋转
                        x = parentOf(x);
                        leftRotate(x);
                    }
                    //如果是左3，即添加的结点是原来叶子节点的左子结点，
                    // 原来叶子结点是是其父节点的左子结点，则将x的父节点设为红色，
                    // 爷爷结点设为黑色，并且围绕爷爷结点进行右旋转
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rightRotate(parentOf(parentOf(x)));
                    //爷爷结点向上递归
                    x = parentOf(parentOf(x));
                }
            }

            //如果x的父节点是爷爷结点的右子结点
            else{
                //给叔叔结点一个引用
                RBNode y = leftOf(parentOf(parentOf(x)));
                //第二种情形
                if(colorOf(y) == RED){
                    //父节点和叔叔结点设为黑色，爷爷结点设为红色
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    //爷爷节点向上递归
                    x=parentOf(parentOf(x));
                }
                //第三种情形
                else{
                    //如果x是x的父节点的左子结点，围绕x的父节点进行右旋转
                    if(leftOf(parentOf(x)) == x){
                        x = parentOf(x);
                        rightRotate(x);
                    }
                    //如果是右3，即x是其父节点的右子结点，
                    //将父节点设为黑色，爷爷结点设为红色，并且围绕
                    //爷爷结点进行左旋转
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    leftRotate(parentOf(parentOf(x)));
                }
            }
        }
        //最后地轨道根结点，将根节点设为黑色
        root.color = BLACK;
    }

    /**
     * 找后继节点，即大于节点的最小值
     * @param node
     * @return
     */
    private RBNode getNext(RBNode node){
        if(node == null){
            return null;
        }
        else if(node.right != null){
            RBNode r = node.right;
            while(r.left != null){
                r = r.left;
            }
            return r;
        }
        else{
            RBNode pa = node.parent;
            RBNode ch = node;
            while(pa != null && ch == pa.right){
                ch = pa;
                pa = pa.parent;
            }
            return pa;
        }
    }

    /**
     * 找到指定节点的前驱节点，即找小于node节点的最大值
     * @param node
     */
    private RBNode getPre(RBNode node){
        if(node == null){
            return null;
        }
        else if(node.left != null){
            RBNode l = node.left;
            while(l.right != null){
                l = l.right;
            }
            return l;
        }
        else{
            RBNode pa = node.parent;
            RBNode ch = pa;
            while(pa != null && ch == pa.left){
                ch = pa;
                pa = pa.parent;
            }
            return pa;
        }
    }

    private RBNode getNode(K key){
        RBNode node=this.root;
        while(node!=null){
            int cmp = key.compareTo((K) node.key);
            if(cmp<0){
                node=node.left;
            }
            else if(cmp>0){
                node=node.right;
            }
            else
                return node;
        }
        return null;
    }

    public V remove(K key){
        RBNode node=getNode(key);
        if(node==null){
            return null;
        }
        V oldValue = (V) node.value;
        deleteNode(node);
        return oldValue;
    }

    /**
     * 删除操作：
     * 1、删除叶子节点，直接删除
     * 2、删除的节点有一个子节点，那么用子节点来替代
     * 3、如果删除的节点有2个子节点，此时需要找到前驱节点或者后继节点来替代
     *
     * @param node
     */
    private void deleteNode(RBNode node){
        //第三种情况：如果删除的节点有两个子结点
        if(node.left != null && node.right != null){
            RBNode next = getNext(node);
            node.key = next.key;
            node.value = next.value;
            node = next;
        }
        //使用替代结点的左子结点或者右子结点来替代
        RBNode replacement = node.left == null ? node.right : node.left;
        //子节点为空则说明本结点是叶子结点
        if(replacement != null){
            //替代者的父指针指向的原来node的父亲
            replacement.parent=node.parent;
            if(node.parent == null){
                root = replacement;
            }
            else if(node == leftOf(parentOf(node))){
                node.parent.left = replacement;
            }
            else{
                node.parent.right = replacement;
            }
            //将node有关的指针都设置为空，就是断开node的所有指针，等待gc回收
            node.left = node.right = node.parent = null;

            if(node.color == BLACK) {
                //删除之后进行一定调整
                fixAfterDelete(replacement);
            }
        }
        //node此时为根结点
        else if(node.parent == null){
            root = null;
        }

        //删除的节点是叶子结点，即node的replacement为空
        else{
            if(node.color == BLACK){
                fixAfterDelete(node);
            }

            if(node.parent != null){
                if(node == leftOf(parentOf(node))){
                    node.parent.left = null;
                }
                else{
                    node.parent.right = null;
                }
                node.parent = null;
            }
        }
    }

    private void fixAfterDelete(RBNode x){
        //如果x不是根结点并且x是一个黑色结点
        if(x != root && colorOf(x) == BLACK){
            //如果x是左节点
            if(x == leftOf(parentOf(x))){
                //找到兄弟结点
                RBNode rnode = rightOf(parentOf(x));
                //判断此时兄弟结点是否是x的真正的兄弟结点
                if(colorOf(rnode) == RED){
                    setColor(rnode, BLACK);
                    setColor(parentOf(rnode), RED);
                    leftRotate(x);
                    rnode = rightOf(parentOf(x));
                }

                //情况三：本节点搞不定，只能找兄弟借一个结点，兄弟没得借
                if(colorOf(leftOf(rnode)) == BLACK && colorOf(rightOf(rnode)) == BLACK){
                    setColor(rnode, RED);
                    x = parentOf(x);
                }
                //情况二：本节点搞不定，只能找兄弟结点,兄弟借给结点
                else{
                    //分两种情形：兄弟是三节点或者四结点
                    //首先是三节点的情况
                    if(colorOf(rightOf(rnode)) == BLACK){
                        setColor(leftOf(rnode), BLACK);
                        setColor(rnode, RED);
                        rightRotate(rnode);
                        rnode = rightOf(parentOf(x));
                    }
                    //然后是四结点的情况
                    setColor(rnode, colorOf(parentOf(rnode)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf((rnode)), BLACK);
                    leftRotate(parentOf(x));
                    x = root;
                }
            }
            //如果被删除的结点是右子结点
            else{
                RBNode lnode = leftOf(parentOf(x));
                if(colorOf(lnode) == RED){
                    setColor(lnode, BLACK);
                    setColor(parentOf(x), RED);
                    rightRotate(parentOf(x));
                    lnode = leftOf(parentOf(x));
                }
                if(colorOf(rightOf(lnode)) == BLACK && colorOf(leftOf(lnode)) == BLACK){
                    setColor(lnode, RED);
                    x = parentOf(x);
                }
                else{
                    if(colorOf(leftOf(lnode)) == BLACK){
                        setColor(rightOf(lnode), BLACK);
                        setColor(lnode, RED);
                        leftRotate(lnode);
                        lnode = leftOf(parentOf(x));
                    }
                    setColor(lnode, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(lnode), BLACK);
                    rightRotate(parentOf(x));
                    x = root;
                }
            }
        }
        //如果被删除的结点x是红色结点
        //情形一：替代结点为红色结点，则将替代结点设为黑色，补偿被删除的黑色结点，使红黑树保持黑色平衡
        setColor(x, BLACK);
    }

    private void setColor(RBNode node, boolean color){
        node.color = color;
    }

    public boolean colorOf(RBNode node){
        return node == null ? BLACK : node.color;
    }
    private RBNode parentOf(RBNode node){
        return node == null ? null : node.parent;
    }

    private RBNode leftOf(RBNode node){
        return node == null ? null : node.left;
    }

    private RBNode rightOf(RBNode node){
        return node == null ? null : node.right;
    }

    public void getNodeColor(){
        if(root != null){
            getColor(root);
        }
    }

    private void getColor(RBNode node){
        if(node.left != null){
            getColor(node.left);
        }
        System.out.println(node.color);
        if(node.right != null){
            getColor(node.right);
        }
    }

    static class RBNode<K extends Comparable<K>, V>{
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private K key;
        private V value;

        public RBNode(){}

        public RBNode(K key, V value, RBNode parent){
            this.parent = parent;
            this.key = key;
            this.value = value;
            this.color = BLACK;
        }

        public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K key, V value){
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = key;
            this.value = value;
        }
        public boolean isColor() {
            return color;
        }
        public RBNode getLeft() {
            return left;
        }
        public RBNode getRight() {
            return right;
        }
        public V getValue() {
            return value;
        }
    }

    public void inorderTraverse(){
        if(root == null){
            throw new NullPointerException();
        }
        inorder(root);
    }

    private void inorder(RBNode node){
        if(node == null){
            throw new NullPointerException();
        }
        inorder(node.left);
        System.out.print(node.color + " ");
        inorder(node.right);
    }

    public RBNode getRoot(){
        return root;
    }
}