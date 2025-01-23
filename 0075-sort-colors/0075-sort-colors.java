class Solution {
    public void sortColors(int[] nums) {
       int temp;
        for(int i=0; i<nums.length; i++){
            int flag=0;
            for(int j=0; j<nums.length-1-i; j++){
                if(nums[j]>nums[j+1]){
                    temp=nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;
                    flag=1;
                }
            }
            if(flag==0)
            break;
        } 
    }
}