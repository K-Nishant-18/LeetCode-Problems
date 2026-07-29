class Solution {
    public void moveZeroes(int[] nums) {
        int i=0;
        int j=0;
        for(i=0; i<nums.length; i++ ){
            if(nums[i]!=0){
                nums[j]=nums[i];
                j++;
            }
        }
        for(j=j; j<nums.length; j++){
            nums[j]=0;
        }
    }
}


//Single Pass - Best Interview Solution
class Solution {
    public void moveZeroes(int[] nums) {

        int j = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                j++;
            }
        }
    }
}
