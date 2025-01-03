class Solution {
    public int minimumSum(int num) {
        char[] ch=(num+"").toCharArray();
        Arrays.sort(ch);
        int n=Integer.parseInt(""+ch[0]+ch[2]);
        int m=Integer.parseInt(""+ch[1]+ch[3]);
        return n+m;
    }        
}