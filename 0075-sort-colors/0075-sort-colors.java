class Solution {
    public void sortColors(int[] nums) {
      int n=nums.length;
      int i=0;
      int j=0;
      int k=n-1;
      int temp;

      while(i<=k){
        if(nums[i]==0){
            temp=nums[j];
            nums[j]=nums[i];
            nums[i]=temp;
            j++;
            i++;
        }
        else if(nums[i]==2){
            temp=nums[k];
            nums[k]=nums[i];
            nums[i]=temp;
            k--;
        }
        else{
            i++;
        }

      }
    }
}