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

    private void rightRotate(RBNode p){
        if(p != null){
            RBNode l = p.left;
            p.left = l.right;
            if(l.right != null){
                l.right.parent = p;
            }
            l.parent = p.parent;
            if(p.parent == null){
                root = l;
            }
            else if(p.parent.right == p){
                p.parent.right = l;
            }
            else{
                p.parent.right = l;
            }
            l.right = p;
            p.parent = l;
        }
    }

    public void put(K key, V value){
        RBNode t = this.root;
        //如果根结点为空，将插入的结点作为根结点
        if(t == null){
            t = new RBNode<>(key, value == null ? key : value, null);
        }

        int cmp;
        RBNode parent;
        if(key == null){
            throw new NullPointerException();
        }
        do{
            parent = t;
            cmp = key.compareTo((K)t.key);
            if(cmp < 0){
                t = t.left;
            }
            else if(cmp > 0){
                t = t.right;
            }
            else{
                t.value = value;
            }
        } while(t != null);

        RBNode node = new RBNode<>(key, value == null ? key : value, parent);
        if(cmp < 0){
            parent.left = node;
        }
        else{
            parent.right = node;
        }
    }

    private void fixAferPut(RBNode x){
        x.color = RED;

        while(x != null && x != root && x.parent.color == RED){
            if(parentOf(x) == leftOf(parentOf(parentOf(x)))){
                 RBNode y = rightOf(parentOf(parentOf(x)));
                 if(colorOf(y) == RED){
                     setColor(parentOf(x), BLACK);
                     setColor(y, BLACK);
                     setColor(parentOf(parentOf(x)), RED);
                 }
                else{
                    if(rightOf(parentOf(x)) ==  x){
                        x = parentOf(x);
                        leftRotate(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rightRotate(parentOf(parentOf(x)));
                    x = parentOf(parentOf(x));
                }
            }

            else{
                RBNode y = leftOf(parentOf(parentOf(x)));
                if(colorOf(y) == RED){
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                }
                else{
                    if(leftOf(parentOf(x)) ==  x){
                        x = parentOf(x);
                        rightRotate(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    leftRotate(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
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

    class RBNode<K extends Comparable<K>, V>{
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
}