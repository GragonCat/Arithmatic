package com.red_black_tree;

import java.util.Scanner;

/***
 * (1) 每个节点或者是黑色，或者是红色。
 * (2) 根节点是黑色。
 * (3) 每个叶子节点是黑色。 [注意：这里叶子节点，是指为空的叶子节点！]
 * (4) 如果一个节点是红色的，则它的子节点必须是黑色的。
 * (5) 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
 */
public class RBTree<K extends Comparable<K>,V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private static RBNode root;

    public static RBNode getRoot() {
        return root;
    }

    public static void setRoot(RBNode root) {
        RBTree.root = root;
    }

    public static void main(String[] args) {
    insertOps();
    }

    public static void insertOps(){
        Scanner scanner = new Scanner(System.in);
        RBTree<String,Object> rbTree = new RBTree<>();
        while(true){
            System.out.println("请输入要插入的节点：");
            String key = scanner.next();
            System.out.println();
            if(key.length() == 1){
                key = "0"+key;
            }
            rbTree.put(key,null);
            TreeOperation.show(RBTree.getRoot());
        }

    }
    public void put(K key,V value){
        if(key == null){
            throw new RuntimeException("key不能为空！");
        }
        RBNode rbNode = new RBNode(key,value == null?key:value);
        if(root == null){
            //根节点为黑色
            rbNode.color = BLACK;
            root = rbNode;
            return;
        }

        RBNode t = root;
        RBNode parent = root;
        int cmp ;

        //查找插入的位置
        do{
            parent = t;
             cmp = key.compareTo((K) parent.key);
            if(cmp > 0){
                t = t.right;
            }else{
                t = t.left;
            }
        }while (t != null);

        rbNode.parent = parent;

        if(cmp > 0){
            parent.right = rbNode;
        }else{
            parent.left = rbNode;
        }
        //在插入节点后，平衡红黑树，进行旋转和变色操作
        fixTreeAfterPutVal(rbNode);

    }

    /***
     * 需要重新调整的情况右8种：
     *
     *
     *         BLACK            BLACK           BLACK          BLACK
     *          / \             /   \           /   \          /   \
     *       RED   RED        RED    RED      RED    RED     RED    RED
     *        /                 \                    /                 \
     *     RED                   RED              RED                  RED
     *
     *
     *
    *          BLACK            BLACK             BLACK                BLACK
    *          /                /                      \                   \
    *        RED             RED                       RED                  RED
    *        /                  \                      /                      \
    *     RED                    RED                RED                        RED
     * */
    public void fixTreeAfterPutVal(RBNode rbNode) {
        setColor(rbNode,RED);

        while(rbNode != null && rbNode != root && parentOf(rbNode).color == RED){
            //如果 父节点为爷爷节点的左子节点
            if(parentOf(rbNode) == parentOf(parentOf(rbNode)).left){
                RBNode uncle = rightOf(parentOf(parentOf(rbNode)));

                /*
                 *         BLACK            BLACK                          RED               RED
                 *          / \             /   \                         /   \             /   \
                 *       RED   RED        RED    RED     ====》        BLACK  BLACK       BLACK  BLACK
                 *        /                 \                          /                     \
                 *     RED                   RED                      RED                    RED
                 * */
                //红色说明有叔叔节点并且是红色(特性3和5，任意路径的黑色节点数量相同，不能是黑色)，只需要变色，不需要旋转
                if(colorOf(uncle) == RED){
                    setColor(parentOf(parentOf(rbNode)),RED);
                    setColor(parentOf(rbNode),BLACK);
                    setColor(uncle,BLACK);
                    //可以将当前的祖孙三代树看做一个红色节点，进行向上递归变色,借助循环向上迭代
                    rbNode = parentOf(parentOf(rbNode));
                }else{
                    /* 或者没有叔叔节点(特性3和5，任意路径的黑色节点数量相同，不能是黑色)，需要变色，旋转
                     *          BLACK                    BLACK
                     *          /                       /
                     *（1）    RED  爷爷节点（右旋）   （2）RED  父节点左旋，爷爷节点再右旋           ====》       BLACK
                     *        /                          \                                              /     \
                     *     RED                            RED                                          RED     RED
                     *
                     * */
                    //若为左子节点则只需要爷爷节点右旋然后变色，如情况（1）
                    if(rbNode == leftOf(parentOf(rbNode))){
                        setColor(parentOf(parentOf(rbNode)),RED);
                        setColor(parentOf(rbNode),BLACK);
                        rightRotate(parentOf(parentOf(rbNode)));
                    }else{
                        //若为右子节点则只需要父亲节点先左旋，然后爷爷节点右旋然后变色，如情况（2）
                        RBNode parent = parentOf(rbNode);
                        RBNode grandpa = parentOf(parent);
                        setColor(parentOf(parentOf(rbNode)),RED);
                        setColor(rbNode,BLACK);
                        leftRotate(parent);
                        rightRotate(grandpa);
                    }
                    // 已经稳定 退出循环
                   break;
                }
            }else{//如果 父节点为爷爷节点的右子节点
                RBNode uncle = leftOf(parentOf(parentOf(rbNode)));

                /* 红色说明有叔叔节点并且只能是红色(特性3和5，任意路径的黑色节点数量相同，不能是黑色)，只需要变色，不需要旋转
                 *         BLACK            BLACK                          RED               RED
                 *          / \             /   \                         /   \             /   \
                 *       RED   RED        RED    RED     ====》        BLACK  BLACK       BLACK  BLACK
                 *            /                    \                          /                     \
                 *           RED                   RED                      RED                    RED
                 * */
                if(colorOf(uncle) == RED){
                    setColor(parentOf(parentOf(rbNode)),RED);
                    setColor(parentOf(rbNode),BLACK);
                    setColor(uncle,BLACK);
                    //可以将当前的祖孙三代树看做一个红色节点，进行向上递归变色,借助循环向上迭代
                    rbNode = parentOf(parentOf(rbNode));
                }else{
                    /* 没有叔叔节点(特性3和5,任意路径的黑色节点数量相同，所以只能是空节点)，旋转，变色
                     *          BLACK                             BLACK
                     *              \                                 \
                     *（2）         RED 父节点右旋，爷爷节点再左旋    （1） RED  爷爷节点左旋           ====》       BLACK
                     *                \                               /                                     /     \
                     *                RED                           RED                                   RED     RED
                     *
                     * */
                    //若为右子节点则只需要爷爷节点左旋然后变色，如情况（1）
                    if(rbNode == rightOf(parentOf(rbNode))){
                        setColor(parentOf(parentOf(rbNode)),RED);
                        setColor(parentOf(rbNode),BLACK);
                        leftRotate(parentOf(parentOf(rbNode)));
                    }else{
                        //若为左子节点则只需要父亲节点先右旋，然后爷爷节点左旋然后变色，如情况（2）
                        RBNode parent = parentOf(rbNode);
                        RBNode grandpa = parentOf(parent);
                        setColor(parentOf(parentOf(rbNode)),RED);
                        setColor(rbNode,BLACK);
                        rightRotate(parent);
                        leftRotate(grandpa);
                    }
                    // 已经稳定 退出循环
                    break;
                }

            }


        }
        setColor(RBTree.root,BLACK);
    }

    public RBNode parentOf(RBNode rbNode){
        return rbNode == null?null:rbNode.parent;
    }

    public RBNode leftOf(RBNode rbNode){
        return rbNode == null ? null : rbNode.left;
    }

    public RBNode rightOf(RBNode rbNode){
        return rbNode == null? null:rbNode.right;
    }

    public boolean colorOf(RBNode rbNode){
        return rbNode == null ? BLACK :rbNode.color;
    }

    public void setColor(RBNode rbNode,boolean color){
        if(rbNode!= null){
            rbNode.color = color;
        }
    }


    /***
     * 节点左旋
     * 1.保存相关节点引用
     * 2.将当前节点的右子节点（rbNode.right）的左子节点（rbNode.right.left）赋值作为当前节点的右子节点(rbNode.right)，
     *      并且将右子节点（rbNode.right）的左子节点（rbNode.right.left）的parent指向当前节点.
     * 3.将当前节点(rbNode)和右子节点(rbNode.right)的父子关系互相交换
     * 4.如果当前节点有父节点:
     *      将当前节点的右子节点的Parent(rbNode.right.parent)指向当前节点的Parent(rbNode.parent)，将当前节点的Parent(rbNode.parent)的子节点改成当前节点的右子节点(rbNode.right)：
     *          （1）如果当前节点是父节点(rbNode.parent)的左子节点（parent.left），将当前节点的父节点的左子节点(rbNode.parent.left) 指向 当前节点的右子节点(rbNode.right)
     *          （2）如果当前节点是父节点(rbNode.parent)的右子节点（parent.right），将当前节点的父节点的右子节点(rbNode.parent.right) 指向 当前节点的右子节点(rbNode.right)
     *   如果当前节点没有父节点，则当前节点为root，设置root为当前节点的右子节点
     *
     */
    public void leftRotate(RBNode rbNode){
        if(rbNode != null){
            //1
            RBNode right = rbNode.getRight();
            RBNode parent = rbNode.getParent();
            //2
            rbNode.setRight(right.getLeft());
            if(right.getLeft() != null){
                right.getLeft().setParent(rbNode);
            }
            //3
            rbNode.setParent(right);
            right.setLeft(rbNode);
            //4
            right.setParent(parent);
            if(parent != null){
                if(parent.getLeft() == rbNode){
                    parent.setLeft(right);
                }else{
                    parent.setRight(right);
                }
            }else{
                root = right;
            }
        }
    }

    /***
     * 右旋反过来，同理
     *
     */
    public void rightRotate(RBNode rbNode){
        if(rbNode != null){
            //1

            RBNode left = rbNode.getLeft();
            RBNode parent = rbNode.getParent();
            //2
            rbNode.setLeft(left.getRight());
            if(left.getRight() != null){
                left.getRight().setParent(rbNode);
            }
            //3
            rbNode.setParent(left);
            left.setRight(rbNode);
            //4
            left.setParent(parent);
            if(parent != null){
                if(parent.getLeft() == rbNode){
                    parent.setLeft(left);
                }else{
                    parent.setRight(left);
                }
            }else{
                root = left;
            }
        }
    }

    static class RBNode<K extends Comparable<K>,V>{
        private RBNode parent;
        private RBNode left;
        private RBNode right;

        private boolean color;
        private K key;
        private V v;

        public RBNode(K key, V v) {
            this.key = key;
            this.v = v;
        }

        public RBNode() {}

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getV() {
            return v;
        }

        public void setV(V v) {
            this.v = v;
        }
    }


}
