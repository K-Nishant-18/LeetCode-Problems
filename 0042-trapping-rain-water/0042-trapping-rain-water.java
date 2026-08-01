class Solution {
    public int trap(int[] height) {
        int n=height.length;

        int trapped=0;
        int total=0;

        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        for (int i = 0; i < n; i++) {

            trapped=Math.max(0,(Math.min(leftMax[i], rightMax[i])-height[i])); 

            total=total+trapped; 


        }
        return total;
    }
}