class Solution {
    Map<Integer, Integer> map = new HashMap<>();
    public int climbStairs(int n) {
        if(map.containsKey(n)){
            return map.get(n);
        }
        if(n==0) return 1;
        if(n==1) return 1;

        int k=climbStairs(n-1)+climbStairs(n-2);
        map.put(n,k);

        return k;        
    }

}