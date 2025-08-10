class Solution {
    public int[] runningSum(int[] nums) {
       int[] sum = {0};
    return Arrays.stream(nums)
                 .map(x -> sum[0] += x)
                 .toArray(); 
    }
}