class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        int[] result = new int[k];
        Map<Integer, Integer> map = new HashMap<>();
        for(Integer n:nums){
            map.put(n, map.getOrDefault(n,0)+1);
        }

        List<Integer> list = new ArrayList<>(map.keySet());

        list.sort((a,b)->Integer.compare(map.get(b), map.get(a)));
        for(int i=0; i<k; i++){
            result[i]=list.get(i);
        }
        return result;
    }
}