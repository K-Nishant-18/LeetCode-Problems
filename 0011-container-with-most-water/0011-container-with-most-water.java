class Solution {
    public int maxArea(int[] height) {
        int n=height.length;
        int area=0;
        int left=0;
        int right=n-1;

        while(left<right){
            int tempArea = Math.min(height[left], height[right])*(right-left);
            if(tempArea>area){
                area=tempArea;
            }
            if(height[left]<height[right]){
                left++;
            }else{
                right--;
            }

        }
        return area;
    }
}