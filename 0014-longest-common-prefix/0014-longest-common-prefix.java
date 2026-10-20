class Solution {
    public String longestCommonPrefix(String[] strs) {
        int minLength = strs[0].length();
        for (String s : strs) {
            minLength = Math.min(minLength, s.length());
        }

        if(strs.length==1){
            return strs[0];
        }

        for (int j = 0; j < minLength; j++) {
            for (int i = 1; i < strs.length; i++) {
                if (strs[i].charAt(j) != strs[0].charAt(j)) {
                    return strs[0].substring(0, j);
                }
            }
        }

        return strs[0].substring(0, minLength);
    }
}