class Solution {
    public int removeElement(int[] nums, int val) {
        int n=nums.length;
        int i=0;
        int temp;
        while(i<n){
            if(nums[i]==val){
                temp=nums[n-1]; 
                nums[n-1]=nums[i];
                nums[i]=temp;
                n--;
            }else{
                i++;
            }
        }
        return n;
    }
}