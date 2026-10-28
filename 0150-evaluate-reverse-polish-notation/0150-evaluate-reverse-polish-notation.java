class Solution {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for(String str:tokens){
            if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")){
                int temp1=stack.pop();
                int temp2=stack.pop();
                if(str.equals("+")){
                    stack.push(temp1 + temp2);
                }else if(str.equals("-")){
                    stack.push(temp2 - temp1);
                }else if(str.equals("*")){
                    stack.push(temp1 * temp2);
                }else{
                    stack.push(temp2 / temp1);
                }
            }else{
                stack.push(Integer.parseInt(str));
            }
        }
        return stack.pop();
    }
}