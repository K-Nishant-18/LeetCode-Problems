class Solution {
    public int firstMissingPositive(int[] nums) {
        for(int i=0; i<nums.length; i++){
            while(nums[i]>0 && nums[i]<=nums.length && nums[i]!=nums[nums[i]-1]){
                int correctIndex = nums[i]-1;
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }
        for(int i=0; i<nums.length; i++){
            if(nums[i]!=i+1)
            return i+1;
        }
        return nums.length + 1;
    }
}