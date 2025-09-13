class Solution {
    public boolean isPalindrome(String s) {
        String[] words=s.split("[^a-zA-Z0-9]+");
        StringBuilder sb=new StringBuilder();
        for(int i=0; i<words.length; i++){
            words[i]=words[i].toLowerCase();
            sb.append(words[i]);
        }
        //Make copy before reversing
        String org=sb.toString();

        StringBuilder sb2=new StringBuilder();
        sb2=sb.reverse();
        
        String rev=sb2.toString();
        

        return org.equals(rev);
    }
}