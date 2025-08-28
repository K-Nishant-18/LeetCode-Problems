class Solution {
    public int removeDuplicates(int[] nums) {
        int i=0;
        int k=0;

        while(i<nums.length){
            if(nums[i]!=nums[k]){
                nums[k+1]=nums[i];
                i++;
                k++;
            }else{
                i++;
            }
        }
        return k+1;
    }
}