class Solution {
    public List<Integer> getRow(int rowIndex) {
        List<List<Integer>> result = new ArrayList<>();
        for(int i=0; i<=rowIndex; i++){
            List<Integer> list = new ArrayList<>();
            if(i==0){
                list.add(1);
            }else{
                List<Integer> previousRow = result.get(i - 1);
                list.add(1);
                int rowSize = i+1;
                for(int j=1; j<rowSize-1; j++){
                    list.add(previousRow.get(j - 1) + previousRow.get(j));
                }
                list.add(1);
            }
            result.add(list);
        }
        return result.get(rowIndex);
    }
}