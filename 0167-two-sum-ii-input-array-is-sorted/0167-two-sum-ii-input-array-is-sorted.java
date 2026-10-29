class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int[] arr = new int[2];
        int i=0;
        int j=numbers.length-1;
        while(i<j){
            if(numbers[i]+numbers[j]==target){
                arr[0]=i+1;
                arr[1]=j+1;
                return arr;
            }else if(numbers[i]+numbers[j]<target){
                i++;
            }else{
                j--;
            }
        }
        return arr;
    }
}