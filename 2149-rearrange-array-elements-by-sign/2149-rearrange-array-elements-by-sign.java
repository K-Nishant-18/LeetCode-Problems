class Solution {
    public int[] rearrangeArray(int[] nums) {
        int[] arr = new int[nums.length];

        int i=0;
        while(i<nums.length){
            for(int j=0; j<nums.length; j++){
                if(nums[j]>=0){
                    arr[i]=nums[j];
                    i+=2;
                }
            }
        }

        int k=1;
        while(k<nums.length){
            for(int j=0; j<nums.length; j++){
                if(nums[j]<0){
                    arr[k]=nums[j];
                    k+=2;
                }
            }
        }

        return arr;
    }
}