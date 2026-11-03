class Solution {
    public double findMaxAverage(int[] nums, int k) {
        double sum=0;
        for(int i=0; i<k; i++){
            sum+=nums[i];
        }
        double maxSum=sum;
        for(int i=k; i<nums.length; i++){
            sum+=nums[i];
            sum-=nums[i-k];
            if(sum>=maxSum)maxSum=sum;
        }
        return maxSum/k;
    }
}