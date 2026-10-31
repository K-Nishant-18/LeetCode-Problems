class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxOne = 0;
        int count=0;
        for(int n:nums){
            if(n!=1)count=0;
            if(n==1){
                count++;
                if(count>=maxOne)maxOne=count;
            }
            
        }
        return maxOne;
    }
}