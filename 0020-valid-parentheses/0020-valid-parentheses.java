class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)=='(' || s.charAt(i)=='{' || s.charAt(i)=='['){
                stack.push(s.charAt(i));
            }
            else if(s.charAt(i)==')' && stack.isEmpty()==false){
                if(stack.peek()=='('){
                    stack.pop();
                }else{
                    return false;
                }
            }else if(s.charAt(i)=='}' && stack.isEmpty()==false){
                if(stack.peek()=='{'){
                    stack.pop();
                }else{
                    return false;
                }
            }
            else if(s.charAt(i)==']' && stack.isEmpty()==false){
                if(stack.peek()=='['){
                    stack.pop();
                }else{
                    return false;
                }
            }else{
                return false;
            }
        
        }
        return stack.isEmpty();
    }
}