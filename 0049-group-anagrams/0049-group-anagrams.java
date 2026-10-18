class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str:strs){
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String temp = String.valueOf(chars);
            List<String> list = new ArrayList<>();
            if(!map.containsKey(temp)){
                list.add(str);
                map.put(temp, list);
            }else{
                List<String> currentList = map.get(temp);
                currentList.add(str);
            }
        }
        return new ArrayList<>(map.values());
    }
}