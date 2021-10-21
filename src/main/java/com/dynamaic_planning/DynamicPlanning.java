package com.dynamaic_planning;

public class DynamicPlanning {

    public static void main(String[] args) {

        int A[] = {2,5,7};

        int result = coinChange(A,27);

        System.out.println(result);
    }
     public static int coinChange(int A[],int M){
        //f(x) = Min{f(x-2)+1,f(x-5)+1,f(x-7)+1 ......}
         int len = A.length;
         int[] x = new int[M+1];
         x[0] = 0;
         int i,j;
         for(i=1;i<M+1;++i){
             x[i] = Integer.MAX_VALUE;
             for(j = 0;j<len;++j){
                 if(i-A[j] >= 0 && x[i-A[j]] != Integer.MAX_VALUE){
                     x[i] = Math.min(x[i-A[j]]+1,x[i]);
                 }
             }
         }

         if(x[M] == Integer.MAX_VALUE ){
            return -1;
         }

        return x[M];
     }


}
