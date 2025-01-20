
/*
We can solve the Two Sum Problem in O(n) using a HashMap. 
The idea is to store the difference between the target and the 
current number in the map as we iterate through the array. 
This allows quick lookup to check if the required number already exists.
*/



class Solution {
    public int[] twoSum(int[] nums, int target) {
       int[] arr= new int[2];
       int sum=0;
       int n=nums.length;
       outer:
       for(int i=0; i<n; i++){
        for(int j=i+1; j<n; j++){
            sum= nums[i]+nums[j];
            if(target==sum){
                
                arr[0]=i;
                arr[1]=j;
                break outer;
            }
            sum=0;
        }
       } 
       return arr;
    }
}
