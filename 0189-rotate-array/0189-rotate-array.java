class Solution {
       public void rotate(int[] nums, int k) {
        int l=nums.length;
        k=k%l;
        int[] temp=new int[l];
        for(int i=0; i<l; i++){
            int m= (i+k)%l;
            temp[m]=nums[i];
        }
        for(int j=0; j<l; j++){
            nums[j]=temp[j];
        }
    }
}