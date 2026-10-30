class Solution {
    public int maxArea(int[] height) {
        int i=0; 
        int j=height.length-1;
        int maxVol=0;

        while(i<j){
           int volume = Math.min(height[i], height[j])*(j-i);
           if(volume>=maxVol){
            maxVol = volume;
           }
           if(height[i]<height[j]){
            i++;
           }else{
            j--;
           }
        }
        return maxVol;
    }
}