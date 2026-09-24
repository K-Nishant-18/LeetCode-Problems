class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        List<Integer> list1 = new ArrayList<>();
        for (int num : nums1) {
            list1.add(num);
        }

        List<Integer> list2 = new ArrayList<>();
        for (int num : nums2) {
            list2.add(num);
        }

        list1.retainAll(list2);
        Set<Integer> set = new HashSet<>(list1);

        int[] result = new int[set.size()];

        int i = 0;
        for (int num : set) {
            result[i++] = num;
        }

        return result;
    }
}