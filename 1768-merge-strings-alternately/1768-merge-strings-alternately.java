class Solution {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int i=0;
        int j=0;
        int count=0;

        char[] w1=word1.toCharArray();
        char[] w2=word2.toCharArray();

        while(i<word1.length() && j<word2.length()){
            if(count%2==0){
                sb.append(word1.charAt(i));
                i++;
                count++;
            }else{
                sb.append(word2.charAt(j));
                j++;
                count++;
            }
        }

        while(j<word2.length()){
            sb.append(word2.charAt(j));
            j++;
        }
        while(i<word1.length()){
            sb.append(word1.charAt(i));
            i++;
        }

        return sb.toString();
    }
}