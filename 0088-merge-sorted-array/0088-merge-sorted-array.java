class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for(int i=m; i<nums1.length; i++){
            nums1[i]=nums2[i-m];
        }
        for(int i=nums1.length-1; i>=0; i--){
            for(int j=0; j<i; j++){
                if(nums1[j]>nums1[j+1]){
                    int temp = nums1[j];
                    nums1[j] = nums1[j+1];
                    nums1[j+1] = temp;
                }
            }
        }
    }
}