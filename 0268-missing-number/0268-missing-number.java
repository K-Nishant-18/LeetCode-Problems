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


/* 

====== Alternate Solution using XOR ======

class Solution {
    public int missingNumber(int[] nums) {
        int n=nums.length;
        int xor=0;
        for(int i=0; i<=n; i++){
            xor=xor^i;
        }
        for(int x:nums){
            xor=xor^x;
        }
        return xor;
    }
}
*/
