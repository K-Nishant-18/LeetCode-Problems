class Solution {
    public boolean isValid(String s) {
        // mapping: closing bracket -> required opening bracket
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');

        Stack<Character> stack=new Stack<>();
        int length=s.length();
        for(int i=0; i<=length-1; i++){
            if(pairs.containsKey(s.charAt(i))){
                if(stack.isEmpty() || stack.peek() != pairs.get(s.charAt(i))){
                    return false;
                }else{
                    stack.pop();
                }
            }else{
                stack.push(s.charAt(i));
            }
        }
        return stack.isEmpty();
    }
}