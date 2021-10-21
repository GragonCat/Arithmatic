package com.heap_sort;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class HeapSort {
    public static void main(String[] args) {
//        int[] arr = new int[]{2,6,4,8,100,-26,50,1,-2,5,3,20,-1};
//        Heap heap = new Heap();
//        heap.heapSort(arr);
//
//        for(int i : arr){
//            System.out.print(i+",");
//        }

        Hashtable hashTable = new Hashtable<Object,Object>();
        ListNode l1 = new ListNode(-1,null);
        ListNode cur = l1;
        int k = 20;
        for(int i = 0 ;i< k;i++){
            cur.next = new ListNode(i,null);
            cur = cur.next;
        }
//
//        ListNode l2 = new ListNode(1,null);
//        l2.next = new ListNode(3,null);
//        l2.next.next = new ListNode(4,null);
//
//        ListNode l3 = new ListNode(2,null);
//        l3.next = new ListNode(6,null);
//
//
//        ListNode[] lists = new ListNode[]{l1,l2,l3};
        NodeUtils nodeUtils = new NodeUtils();
        ListNode listNode = nodeUtils.reverseKGroup(l1.next,3);
        Queue<String> dq = new LinkedList<>();

        while(listNode != null){
            System.out.print(listNode.val+",");
            listNode = listNode.next;
        }


    }
}

class ListNode{
    public int val;
    public ListNode next;
    public ListNode(){}
    public ListNode(int val,ListNode listNode){
        this.val = val;
        this.next = listNode;
    }
}

class NodeUtils{
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || k == 1 ){
            return head;
        }
        ListNode preHead = new ListNode();
        preHead.next = head;
        //前后指针，前指针（preCur）用来链接链表，后指针（cur）用来标记入栈节点
        ListNode preCur = preHead;
        ListNode cur = head;
        //tail用来保存一组最后一个节点的next指针，防止链表断裂
        ListNode tail = head;
        Stack<ListNode> stack = new Stack<>();
        while(cur != null){
            int count = 0;
            //入栈操作
            for(int i = 0; i < k;i++){
                if(cur != null){
                    stack.push(cur);
                    ++count;
                    cur = cur.next;
                }else{
                    break;
                }
            }
            //判断入栈的个数是否等于K，如果小于K说明，剩余的元素小于一组（K个） 所以 不能继续反转
            if(count != k){break;}
            //出栈操作
            for(int i = 0; i < k;i++){
                ListNode node = stack.pop();
                if(i == 0){
                    //反转前的最后一个节点，保存最后一个节点的next指针，防止链表断裂
                    tail = node.next;
                }
                //前指针 链接出栈的节点
                preCur.next = node;
                //后移前指针
                preCur = preCur.next;
                //反转到最后一个节点时，需要将 最后一个节点 指向 反转前的 最后一个节点的 next 节点，避免链表断裂
                if(i == k-1){
                    preCur.next = tail;
                }
            }
        }
        return preHead.next;
    }


    public ListNode mergeKLists(ListNode[] lists) {
        int len  = lists.length;
        ListNode newList =  new ListNode();
        ListNode cur = newList;
        if(len == 0){
            return newList;
        }else if(len == 1){
            return lists[0];
        }
        while(true){
            int minIndex = 999999;
            int min = 999999;
            for(int i = 0; i< lists.length;i++){
                if(lists[i] == null){
                    continue;
                }else{
                    if( lists[i].val < min){
                        minIndex = i;
                        min = lists[i].val;
                    }
                }
            }
            if(min == 999999){
                break;
            }
            cur.next = lists[minIndex];
            cur = cur.next;
            lists[minIndex] = lists[minIndex].next;
        }
        return newList.next;

    }
    public ListNode swapPairs(ListNode head) {
        if(head == null){
            return null;
        }
        if(head.next == null){
            return head;
        }
        ListNode preHead = new ListNode();
        preHead.next = head;
        ListNode left = preHead;
        ListNode right = head;

        while(right != null && right.next!= null){
            left.next = right.next;
            right.next = right.next.next;
            left.next.next = right;
            left = right;
            right = right.next;
        }
        return preHead.next;

    }
}

class Heap{

    public void heapSort(int[] arr){
        int len = arr.length;
        for(int i = len/2 -1;i>=0;i--){
            heapify(arr,len,i);
        }

        for(int i : arr){
            System.out.print(i+",");
        }
        System.out.println();
        for(int i = len-1;i>=0;i--){
            swap(arr,i,0);
            heapify(arr,i,0);
        }


    }

    public void heapify(int[] arr,int len,int index){
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int maxIndex = index;
        if(left < len && arr[left] > arr[maxIndex]){
            maxIndex = left;
        }
        if(right < len && arr[right] > arr[maxIndex]){
            maxIndex = right;
        }
        if(maxIndex != index){
            swap(arr,maxIndex,index);
            heapify(arr,len,maxIndex);
        }
    }
    public void swap(int[] arr,int i,int j){
        int temp =arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}


class Heap1{
    public void sort(int[] arr ){
        int len = arr.length;
        for(int i = len/2 -1;i >= 0;i--){
            heapBuilder(arr,len,i);
        }
        for(int lastIndex = len -1;lastIndex >=0;lastIndex--){
            swap(arr,0,lastIndex);
            heapBuilder(arr,lastIndex,0);
        }
    }

    public void heapBuilder(int[] arr,int len,int curNodeIndex){
        int left = 2 * curNodeIndex + 1;
        int right = 2 * curNodeIndex + 2;
        int maxIndex = curNodeIndex;
        if(left < len && arr[left] > arr[maxIndex]){
           maxIndex = left;
        }
        if(right < len && arr[right] > arr[maxIndex]){
            maxIndex = right;
        }
        if(maxIndex != curNodeIndex){
            swap(arr,maxIndex,curNodeIndex);
            heapBuilder(arr,len,maxIndex);
        }
    }

    public void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}