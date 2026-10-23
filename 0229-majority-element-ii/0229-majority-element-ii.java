class Solution {
    public List<Integer> majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        for(int n:nums){
            map.put(n, map.getOrDefault(n, 0) +1);
        }

        map.forEach((key, value)->{
            if(value>nums.length/3){
                list.add(key);
            }
        });

        return list;

    }
}