class Solution {
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int j=people.length-1;
        int i=0;
        int count=0;

        while(i<=j){
            if(people[j]+people[i]<=limit){
                count++;
                i++;
                j--;
            }else{
                count++;
                j--;
            }
        }
        return count;
    }
}