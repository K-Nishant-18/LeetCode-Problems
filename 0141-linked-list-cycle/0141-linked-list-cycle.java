/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        boolean flag = false;
        ListNode temp=head;
        Set<ListNode> set = new HashSet<>();

        while(temp!=null){
            if(!set.add(temp)){
                flag=true;
                return flag;
            }
            temp = temp.next;
        }
        return flag;
    }
}