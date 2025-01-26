class Solution {
    public int missingNumber(int[] nums) {
        int n=nums.length;
        int rangeSum=n*(n+1)/2;
        int actualSum=0;
        for(int num:nums){
            actualSum += num;
        }
        return (rangeSum-actualSum);
    }
}