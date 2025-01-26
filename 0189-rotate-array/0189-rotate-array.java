/*
MY PREVIOUS SOLUTION

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

*/


// UPDATED SOLUTION WITH BETTER APPROACH

class Solution {
    public void rotate(int[] nums, int k) {
        int n=nums.length;
        k=k%n;
        
        reverse(nums, 0, n-k-1);
        reverse(nums, n-k, n-1);
        reverse(nums, 0, n-1);

    }

    void reverse(int[] nums, int i, int j){
            int temp;
            for(int k=i; k<j; k++){
                temp=nums[i];
                nums[i]=nums[j];
                nums[j]=temp;
                i++;
                j--;
            }
        }

}
