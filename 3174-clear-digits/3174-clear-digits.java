class Solution {
    public String clearDigits(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for(char c:s.toCharArray()){
            if(Character.isDigit(c) && !stack.isEmpty()){
                stack.pop();
            }else if(!Character.isDigit(c)){
                stack.push(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        Deque<Character> stack2 = new ArrayDeque<>();
        while(!stack.isEmpty()){
            stack2.push(stack.pop());
        }        
        while(!stack2.isEmpty()){
            sb.append(stack2.pop());
        }
        return sb.toString();
    }
}