class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int[] result=new int[2];
        int j=0;
        int i=numbers.length-1;
        while(i<numbers.length && j<numbers.length){
            if(numbers[i]+numbers[j]==target){
                result[0]=j+1;
                result[1]=i+1;
                return result;
            }else if(numbers[i]+numbers[j]<target){
                j++;
            }else if(numbers[i]+numbers[j]>target){
                i--;
            }
        }
        return result;
    }
}