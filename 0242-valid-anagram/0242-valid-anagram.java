class Solution {
    public boolean isAnagram(String s, String t) {
        
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