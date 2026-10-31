class Solution {
    public int missingNumber(int[] nums) {
        // Arrays.sort(nums);
        // for(int i=0; i<nums.length; i++){
        //     if(nums[i]!=i)return i;
        // }
        // return nums.length;

        int n=nums.length;
        int rangeSum=n*(n+1)/2;
        int sum=0;

        for(int m:nums){
            sum+=m;
        }
        return rangeSum-sum;
    }
}