class Solution {
    Map<Integer, Integer> map = new HashMap<>();
    public int minCostClimbingStairs(int[] cost) {
        int n=cost.length-1;

        return Math.min(minCost(cost, n),minCost(cost, n-1));
    }
    public int minCost(int[] cost, int n){
        if(map.containsKey(n)){
            return map.get(n);
        }
        if(n==0) return cost[0];
        if(n==1) return cost[1];

        int k=cost[n] + Math.min(minCost(cost, n-1), minCost(cost, n-2));

        map.put(n,k);
        
        return k;
    }
}