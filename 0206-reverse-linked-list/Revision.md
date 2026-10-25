
# LeetCode 206 — Reverse Linked List

- **Difficulty:** Easy
- **Topic:** Linked List, Pointers
- **Pattern:** Iterative Pointer Reversal
- **Problem Link:** https://leetcode.com/problems/reverse-linked-list/

---

## 1. Problem Statement

Given the `head` of a singly linked list, reverse the list and return the reversed list's new head.

### Example 1

**Input:**
```text
head = [1, 2, 3, 4, 5]
```

**Output:**
```text
[5, 4, 3, 2, 1]
```

### Example 2

**Input:**
```text
head = [1, 2]
```

**Output:**
```text
[2, 1]
```

### Example 3

**Input:**
```text
head = []
```

**Output:**
```text
[]
```

### Constraints

- The number of nodes is in the range `[0, 5000]`.
- `-5000 <= Node.val <= 5000`.

---

## 2. Understanding the Problem

A singly linked list consists of nodes where each node points to the next node.

Initially:

```text
1 → 2 → 3 → 4 → 5 → null
```

After reversing:

```text
5 → 4 → 3 → 2 → 1 → null
```

We must reverse the direction of every link without creating a new list of nodes.

For example, the original link:

```text
1 → 2
```

must become:

```text
1 ← 2
```

The challenge is to change each node's `next` reference without losing access to the remaining nodes.

---

## 3. My Original Attempt

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode previous = new ListNode(-1, head);
        ListNode current = head;

        while (current != null) {
            ListNode temp = current;
            previous.next = temp;
            current = current.next;
        }
    }
}
```

### Problems in the Original Attempt

**1. Incorrect initialization**

```java
ListNode previous = new ListNode(-1, head);
```

A dummy node is useful in some linked-list problems, but it does not help us reverse the links here.

We need `previous` to represent the previously processed node. Initially, no node has been processed, so:

```java
ListNode previous = null;
```

**2. Saving the wrong reference**

```java
ListNode temp = current;
```

This only stores another reference to the current node.

Instead, we need to save the next node:

```java
ListNode temp = current.next;
```

**3. The link is never reversed**

```java
previous.next = temp;
```

This does not reverse the current node's link. We need:

```java
current.next = previous;
```

**4. The method does not return a value**

The method returns a `ListNode`, so it must return the new head after reversing the list.

---

## 4. Corrected Iterative Solution

This is the solution I developed by learning how the pointers move.

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode previous = null;
        ListNode current = head;

        while (current != null) {
            ListNode temp = current.next;
            current.next = previous;
            previous = current;
            current = temp;
        }

        return previous;
    }
}
```

### How It Works

We use three references:

| Reference | Responsibility |
|---|---|
| `previous` | Points to the already-reversed portion |
| `current` | Points to the node being processed |
| `temp` | Saves the next node before changing the current link |

Initially:

```java
ListNode previous = null;
ListNode current = head;
```

For each node, perform these four operations in order.

**Step 1: Save the next node**

```java
ListNode temp = current.next;
```

Save the remaining list so we don't lose access to it.

**Step 2: Reverse the link**

```java
current.next = previous;
```

Make the current node point to the previous node.

**Step 3: Advance `previous`**

```java
previous = current;
```

The current node becomes the beginning of the reversed portion.

**Step 4: Advance `current`**

```java
current = temp;
```

Move to the next node in the original list.

Repeat until `current == null`.

Finally:

```java
return previous;
```

`previous` points to the new head of the reversed list.

---

## 5. Dry Run

Consider:

```text
1 → 2 → 3 → null
```

### Initial State

```text
previous = null
current  = 1 → 2 → 3 → null
```

### Iteration 1

Save the next node:

```java
temp = current.next;  // Node 2
```

Reverse the link:

```java
current.next = previous;
```

Node `1` now points to `null`.

Advance the pointers:

```java
previous = current;  // Node 1
current = temp;      // Node 2
```

State:

```text
1 → null

previous = 1
current  = 2 → 3 → null
```

### Iteration 2

Save the next node:

```java
temp = current.next;  // Node 3
```

Reverse the link:

```java
current.next = previous;
```

Node `2` now points to node `1`.

Advance the pointers:

```java
previous = current;  // Node 2
current = temp;      // Node 3
```

State:

```text
2 → 1 → null

previous = 2
current  = 3 → null
```

### Iteration 3

Save the next node:

```java
temp = current.next;  // null
```

Reverse the link:

```java
current.next = previous;
```

Node `3` now points to node `2`.

Advance the pointers:

```java
previous = current;  // Node 3
current = temp;      // null
```

Final state:

```text
3 → 2 → 1 → null

previous = 3
current  = null
```

The loop terminates because `current == null`.

Therefore:

```java
return previous;
```

returns node `3`, the new head.

---

## 6. Optimized Solution

The iterative solution above is already optimal in time and auxiliary space.

```java
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }
}
```

This version uses shorter variable names while keeping the same logic.

### Complexity Analysis

**Time Complexity: `O(n)`**

Each node is visited exactly once, where `n` is the number of nodes.

**Auxiliary Space Complexity: `O(1)`**

Only a constant number of references are used. The existing nodes are rearranged in place.

No additional collection or list of nodes is created.

---

## 7. Important Edge Cases

### Empty List

Input:

```text
head = null
```

The loop never executes, and `previous` remains `null`.

Output:

```text
null
```

### Single Node

Input:

```text
1 → null
```

The node already points to `null`. The algorithm returns the same node.

Output:

```text
1 → null
```

### Two Nodes

Input:

```text
1 → 2 → null
```

Output:

```text
2 → 1 → null
```

### Multiple Nodes

Input:

```text
1 → 2 → 3 → 4 → 5 → null
```

Output:

```text
5 → 4 → 3 → 2 → 1 → null
```

---

## 8. Common Mistakes to Avoid

### Mistake 1: Changing the link before saving the next node

Incorrect order:

```java
current.next = previous;
ListNode next = current.next;
```

After the first line, `current.next` points backward. The original next node is no longer accessible through that reference.

Correct order:

```java
ListNode next = current.next;
current.next = previous;
```

### Mistake 2: Moving `current` too early

If you move `current` before reversing its link, you may skip the node that needs to be processed.

### Mistake 3: Returning the original head

```java
return head;
```

The original head becomes the tail after reversal. It is not the new head.

Correct:

```java
return previous;
```

### Mistake 4: Using a dummy node unnecessarily

A dummy node is helpful when operations may remove or insert the head, but reversing a list with this three-pointer technique does not require one.

---

## 9. Interview Takeaways

- Understand that a linked list is reversed by changing its `next` references.
- Save the next node before overwriting the current link.
- Use `previous`, `current`, and `next` to manage pointer movement.
- Initialize `previous` to `null` because the new tail must point to `null`.
- Return `previous`, which becomes the new head.
- The iterative solution achieves `O(n)` time and `O(1)` auxiliary space.
- Do not confuse a reference to a node with a copy of the node.

### The Four-Step Pattern

Memorize this order:

```java
ListNode next = current.next;
current.next = previous;
previous = current;
current = next;
```

**Save → Reverse → Advance Previous → Advance Current**

This sequence is the core of iterative linked-list reversal.

---

## 10. Final Revision Checklist

Before an interview, make sure you can explain:

- [ ] Why `previous` starts as `null`.
- [ ] Why the next node must be saved before reversing a link.
- [ ] Why `current.next = previous` reverses the link.
- [ ] Why both `previous` and `current` must advance.
- [ ] Why the answer is `return previous`, not `return head`.
- [ ] Why the algorithm is `O(n)` time and `O(1)` auxiliary space.
- [ ] How the algorithm handles an empty list and a single-node list.

**Core lesson:** Reverse the links one at a time, preserving access to the unprocessed portion of the list. Once you understand the pointer sequence, the entire algorithm becomes straightforward.
