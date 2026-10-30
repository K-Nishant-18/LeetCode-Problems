class Solution {
    public void rotate(int[] nums, int k) {
        k=k%nums.length;
        int i=0;
        int start=i;
        int j=nums.length-1-k;
        int m=j+1;
        int n=nums.length-1;
        int end=n;

        while(i<=j){
            int temp=nums[i];
            nums[i]=nums[j];
            nums[j]=temp;
            i++;
            j--;
        }
        while(m<=n){
            int temp=nums[m];
            nums[m]=nums[n];
            nums[n]=temp;
            m++;
            n--;
        }
        while(start<=end){
            int temp=nums[start];
            nums[start]=nums[end];
            nums[end]=temp;
            start++;
            end--;
        }

    }
}