class Solution {
    public void rotate(int[] nums, int k) {
    int n=nums.length;
    k=k%n;
    int[] rotated=new int[n];
    for (int i=0; i<n; i++){
        int key=(i+k)%n;
        rotated[key]=nums[i];
    } 
    for(int i=0; i<n; i++){
        nums[i]=rotated[i];
    } 
    }
}