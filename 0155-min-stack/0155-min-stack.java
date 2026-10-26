class MinStack {

    Deque<Integer> stack = new ArrayDeque<>();
    Deque<Integer> minStack = new ArrayDeque<>();

    public MinStack() {
        
    }
    
    public void push(int value) {
        stack.push(value);
        if(minStack.isEmpty() || value<=(minStack.peek())){
            minStack.push(value);
        }
    }
    
    public void pop() {
        int temp = stack.pop();
        if(minStack.peek()==temp){
            minStack.pop();
        }  
    }
    
    public int top() {
       return  stack.peek();
    }
    
    public int getMin() {
         return minStack.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(value);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */