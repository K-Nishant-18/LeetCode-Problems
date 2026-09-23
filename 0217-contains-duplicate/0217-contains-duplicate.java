class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set=new HashSet<>();
        for(Integer n:nums){
            if(!set.add(n)){
                return true;
            }else{
                set.add(n);
            }
        }
        return false;
    }
}