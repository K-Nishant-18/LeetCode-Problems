class Solution {
    public int lengthOfLastWord(String s) {
        String[] strArr = s.trim().split(" ");
        int n = strArr.length;
        return strArr[n - 1].length();
    }
}