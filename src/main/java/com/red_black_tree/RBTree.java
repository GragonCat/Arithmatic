package com.red_black_tree;

import java.util.Scanner;

/***
 * (1) ÿ���ڵ�����Ǻ�ɫ�������Ǻ�ɫ��
 * (2) ���ڵ��Ǻ�ɫ��
 * (3) ÿ��Ҷ�ӽڵ��Ǻ�ɫ�� [ע�⣺����Ҷ�ӽڵ㣬��ָΪ�յ�Ҷ�ӽڵ㣡]
 * (4) ���һ���ڵ��Ǻ�ɫ�ģ��������ӽڵ�����Ǻ�ɫ�ġ�
 * (5) ��һ���ڵ㵽�ýڵ������ڵ������·���ϰ�����ͬ��Ŀ�ĺڽڵ㡣
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
            System.out.println("������Ҫ����Ľڵ㣺");
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
            throw new RuntimeException("key����Ϊ�գ�");
        }
        RBNode rbNode = new RBNode(key,value == null?key:value);
        if(root == null){
            //���ڵ�Ϊ��ɫ
            rbNode.color = BLACK;
            root = rbNode;
            return;
        }

        RBNode t = root;
        RBNode parent = root;
        int cmp ;

        //���Ҳ����λ��
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
        //�ڲ���ڵ��ƽ��������������ת�ͱ�ɫ����
        fixTreeAfterPutVal(rbNode);

    }

    /***
     * ��Ҫ���µ����������8�֣�
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
            //��� ���ڵ�Ϊүү�ڵ�����ӽڵ�
            if(parentOf(rbNode) == parentOf(parentOf(rbNode)).left){
                RBNode uncle = rightOf(parentOf(parentOf(rbNode)));

                /*
                 *         BLACK            BLACK                          RED               RED
                 *          / \             /   \                         /   \             /   \
                 *       RED   RED        RED    RED     ====��        BLACK  BLACK       BLACK  BLACK
                 *        /                 \                          /                     \
                 *     RED                   RED                      RED                    RED
                 * */
                //��ɫ˵��������ڵ㲢���Ǻ�ɫ(����3��5������·���ĺ�ɫ�ڵ�������ͬ�������Ǻ�ɫ)��ֻ��Ҫ��ɫ������Ҫ��ת
                if(colorOf(uncle) == RED){
                    setColor(parentOf(parentOf(rbNode)),RED);
                    setColor(parentOf(rbNode),BLACK);
                    setColor(uncle,BLACK);
                    //���Խ���ǰ����������������һ����ɫ�ڵ㣬�������ϵݹ��ɫ,����ѭ�����ϵ���
                    rbNode = parentOf(parentOf(rbNode));
                }else{
                    /* ����û������ڵ�(����3��5������·���ĺ�ɫ�ڵ�������ͬ�������Ǻ�ɫ)����Ҫ��ɫ����ת
                     *          BLACK                    BLACK
                     *          /                       /
                     *��1��    RED  үү�ڵ㣨������   ��2��RED  ���ڵ�������үү�ڵ�������           ====��       BLACK
                     *        /                          \                                              /     \
                     *     RED                            RED                                          RED     RED
                     *
                     * */
                    //��Ϊ���ӽڵ���ֻ��Ҫүү�ڵ�����Ȼ���ɫ���������1��
                    if(rbNode == leftOf(parentOf(rbNode))){
                        setColor(parentOf(parentOf(rbNode)),RED);
                        setColor(parentOf(rbNode),BLACK);
                        rightRotate(parentOf(parentOf(rbNode)));
                    }else{
                        //��Ϊ���ӽڵ���ֻ��Ҫ���׽ڵ���������Ȼ��үү�ڵ�����Ȼ���ɫ���������2��
                        RBNode parent = parentOf(rbNode);
                        RBNode grandpa = parentOf(parent);
                        setColor(parentOf(parentOf(rbNode)),RED);
                        setColor(rbNode,BLACK);
                        leftRotate(parent);
                        rightRotate(grandpa);
                    }
                    // �Ѿ��ȶ� �˳�ѭ��
                   break;
                }
            }else{//��� ���ڵ�Ϊүү�ڵ�����ӽڵ�
                RBNode uncle = leftOf(parentOf(parentOf(rbNode)));

                /* ��ɫ˵��������ڵ㲢��ֻ���Ǻ�ɫ(����3��5������·���ĺ�ɫ�ڵ�������ͬ�������Ǻ�ɫ)��ֻ��Ҫ��ɫ������Ҫ��ת
                 *         BLACK            BLACK                          RED               RED
                 *          / \             /   \                         /   \             /   \
                 *       RED   RED        RED    RED     ====��        BLACK  BLACK       BLACK  BLACK
                 *            /                    \                          /                     \
                 *           RED                   RED                      RED                    RED
                 * */
                if(colorOf(uncle) == RED){
                    setColor(parentOf(parentOf(rbNode)),RED);
                    setColor(parentOf(rbNode),BLACK);
                    setColor(uncle,BLACK);
                    //���Խ���ǰ����������������һ����ɫ�ڵ㣬�������ϵݹ��ɫ,����ѭ�����ϵ���
                    rbNode = parentOf(parentOf(rbNode));
                }else{
                    /* û������ڵ�(����3��5,����·���ĺ�ɫ�ڵ�������ͬ������ֻ���ǿսڵ�)����ת����ɫ
                     *          BLACK                             BLACK
                     *              \                                 \
                     *��2��         RED ���ڵ�������үү�ڵ�������    ��1�� RED  үү�ڵ�����           ====��       BLACK
                     *                \                               /                                     /     \
                     *                RED                           RED                                   RED     RED
                     *
                     * */
                    //��Ϊ���ӽڵ���ֻ��Ҫүү�ڵ�����Ȼ���ɫ���������1��
                    if(rbNode == rightOf(parentOf(rbNode))){
                        setColor(parentOf(parentOf(rbNode)),RED);
                        setColor(parentOf(rbNode),BLACK);
                        leftRotate(parentOf(parentOf(rbNode)));
                    }else{
                        //��Ϊ���ӽڵ���ֻ��Ҫ���׽ڵ���������Ȼ��үү�ڵ�����Ȼ���ɫ���������2��
                        RBNode parent = parentOf(rbNode);
                        RBNode grandpa = parentOf(parent);
                        setColor(parentOf(parentOf(rbNode)),RED);
                        setColor(rbNode,BLACK);
                        rightRotate(parent);
                        leftRotate(grandpa);
                    }
                    // �Ѿ��ȶ� �˳�ѭ��
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
     * �ڵ�����
     * 1.������ؽڵ�����
     * 2.����ǰ�ڵ�����ӽڵ㣨rbNode.right�������ӽڵ㣨rbNode.right.left����ֵ��Ϊ��ǰ�ڵ�����ӽڵ�(rbNode.right)��
     *      ���ҽ����ӽڵ㣨rbNode.right�������ӽڵ㣨rbNode.right.left����parentָ��ǰ�ڵ�.
     * 3.����ǰ�ڵ�(rbNode)�����ӽڵ�(rbNode.right)�ĸ��ӹ�ϵ���ཻ��
     * 4.�����ǰ�ڵ��и��ڵ�:
     *      ����ǰ�ڵ�����ӽڵ��Parent(rbNode.right.parent)ָ��ǰ�ڵ��Parent(rbNode.parent)������ǰ�ڵ��Parent(rbNode.parent)���ӽڵ�ĳɵ�ǰ�ڵ�����ӽڵ�(rbNode.right)��
     *          ��1�������ǰ�ڵ��Ǹ��ڵ�(rbNode.parent)�����ӽڵ㣨parent.left��������ǰ�ڵ�ĸ��ڵ�����ӽڵ�(rbNode.parent.left) ָ�� ��ǰ�ڵ�����ӽڵ�(rbNode.right)
     *          ��2�������ǰ�ڵ��Ǹ��ڵ�(rbNode.parent)�����ӽڵ㣨parent.right��������ǰ�ڵ�ĸ��ڵ�����ӽڵ�(rbNode.parent.right) ָ�� ��ǰ�ڵ�����ӽڵ�(rbNode.right)
     *   �����ǰ�ڵ�û�и��ڵ㣬��ǰ�ڵ�Ϊroot������rootΪ��ǰ�ڵ�����ӽڵ�
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
     * ������������ͬ��
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
