class Solution {
    public int calPoints(String[] operations) {
        Deque<Integer> stack = new ArrayDeque<>();
        int sum = 0;
        for(String str:operations){
            if(str.equals("C")){
                stack.pop();
            }else if(str.equals("D")){
                stack.push(stack.peek()*2);
            }else if(str.equals("+")){
                int temp = stack.pop();
                int temp2 = stack.pop();
                stack.push(temp2);
                stack.push(temp);
                stack.push(temp+temp2);
            }else{
                stack.push(Integer.parseInt(str));;
            }
        }
        while(!stack.isEmpty()){
            sum+=stack.pop();
        }
        return sum;
    }
}