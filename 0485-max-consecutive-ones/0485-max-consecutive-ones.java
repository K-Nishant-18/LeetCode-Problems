class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
       int count=0;
       int max=0;
       int n=nums.length;
       for(int i=0; i<n; i++){
        if(nums[i]==1){
            count++;
            if(count>max){max=count;}
        }else{
            count=0;
        }
       }
       return max; 
    }
}