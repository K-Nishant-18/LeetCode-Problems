class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] arr = new int[temperatures.length];
        for(int i=0; i<temperatures.length; i++){
            if(stack.isEmpty()){
                stack.push(i);
            }else{
                if(temperatures[stack.peek()]>temperatures[i]){
                    stack.push(i);
                }else if(temperatures[stack.peek()]<=temperatures[i]){
                    while(!stack.isEmpty() &&
       temperatures[i] > temperatures[stack.peek()]){
                        int temp = stack.pop();
                        int wait = i-temp;
                        arr[temp]=wait;
                        wait=0;
                    }
                    stack.push(i);
                }
            }
        }
        return arr;
    }
}