class Solution {
    public boolean isAnagram(String s, String t) {
        List<String> list1 = new ArrayList(Arrays.asList(s.toCharArray()));
        List<String> list2 = new ArrayList(Arrays.asList(t.toCharArray()));
        Map<Character, Integer> freq1=new HashMap<>();
        Map<Character, Integer> freq2=new HashMap<>();

        for(char ch:s.toCharArray()){
            freq1.put(ch, freq1.getOrDefault(ch, 0)+1);
        }

         for(char ch:t.toCharArray()){
            freq2.put(ch, freq2.getOrDefault(ch, 0)+1);
        }  

        if(freq1.equals(freq2)){
            return true;
        }
        return false;
    }
}