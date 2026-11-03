class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> tempStack = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();


        for(int i=0; i<asteroids.length; i++){
            boolean alive = true;
            if(asteroids[i]<0 && !stack.isEmpty() && stack.peek()>0){
                if(stack.peek() + asteroids[i]>0){
                    continue;
                }else if(stack.peek()+asteroids[i]<0){
                    while(alive && !stack.isEmpty() && stack.peek() > 0 && asteroids[i] < 0){
                        int top = stack.peek();
                        int current = asteroids[i];
                        if(top+current<0){
                            stack.pop();
                        }else if(top+current==0){
                            stack.pop();
                            alive=false;
                        }else{
                            alive=false;
                        }
                    }
                    if(alive==true){
                        stack.push(asteroids[i]);
                    }
                }else{
                    stack.pop();
                }
            }else{
                stack.push(asteroids[i]);
            }
        }

        while(!stack.isEmpty()){
            tempStack.push(stack.pop());
        }

        while(!tempStack.isEmpty()){
            result.add(tempStack.pop());
        }

        int[] arr = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            arr[i] = result.get(i);
        }

        return arr;
    }
}