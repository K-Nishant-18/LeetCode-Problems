class Solution {
    public boolean isPalindrome(String s) {
        List<Character> list = new ArrayList<>();
        char[] c =  s.toCharArray();
        for(Character ele:c){
            if(Character.isLetterOrDigit(ele)){
                char temp = Character.toLowerCase(ele);
                list.add(temp);
            }
        }
        int left = 0;
        int right = list.size()-1;
        while(left<=right){
            if(list.get(left)!=list.get(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}