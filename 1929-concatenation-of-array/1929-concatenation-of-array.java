class Solution {
    public int[] getConcatenation(int[] nums) {
     int[] ans = new int[nums.length*2];
     for(int i=1; i<=nums.length; i++){
        ans[i-1]=nums[i-1];
        ans[i+nums.length-1]=nums[i-1];
     }   
     return ans;
    }
}