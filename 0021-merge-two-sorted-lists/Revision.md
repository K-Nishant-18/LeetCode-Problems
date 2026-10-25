
# 🔗 LeetCode 21 — Merge Two Sorted Lists

> **Milestone:** My second Linked List problem!

## 📝 Problem Statement

You are given the heads of two sorted linked lists, `list1` and `list2`.

Merge both lists into one sorted linked list by reusing their existing nodes, and return the head of the merged list.

The resulting list must also be sorted in non-decreasing order.

## 🧪 Examples

### Example 1

**Input:**
```text
list1 = [1,2,4]
list2 = [1,3,4]
```

**Output:**
```text
[1,1,2,3,4,4]
```

### Example 2

**Input:**
```text
list1 = []
list2 = []
```

**Output:**
```text
[]
```

### Example 3

**Input:**
```text
list1 = []
list2 = [0]
```

**Output:**
```text
[0]
```

---

## 💡 Approach: Iterative Merge Using a Dummy Node

### Core Idea

Both linked lists are already sorted. Therefore, we can repeatedly compare their current nodes and attach the smaller one to the merged list.

We use three pointers:

- `list1`: points to the current node in the first list.
- `list2`: points to the current node in the second list.
- `current`: points to the last node in the merged list.

We also create a **dummy node** to simplify building the result.

### Why use a dummy node?

Without a dummy node, we would need special handling to determine the head of the result when attaching the first node.

With a dummy node:

```java
ListNode dummy = new ListNode(-1);
ListNode current = dummy;
```

We can attach every selected node using the same logic. After merging, `dummy.next` points to the actual first node.

The dummy value `-1` is just a placeholder and is not included in the returned list.

---

## 1. My Original Attempt

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = null;
        ListNode finalNode = dummy.next;

        while (list1.next != null && list2.next != null) {
            if (list2.val <= list1.val) {
                dummy.next = list2;
                list2 = list2.next;
                dummy = dummy.next;
            }

            if (list1.val <= list2.val) {
                dummy.next = list1;
                list1 = list1.next;
                dummy = dummy.next;
            }
        }

        return finalNode;
    }
}
```

### What went wrong?

**1. Initializing the dummy node with `null`**

```java
ListNode dummy = null;
ListNode finalNode = dummy.next;
```

Accessing `.next` on a `null` reference causes a `NullPointerException`.

**Correction:**
```java
ListNode dummy = new ListNode(-1);
```

**2. Using `.next` in the loop condition**

```java
while (list1.next != null && list2.next != null)
```

This fails for empty lists and stops before processing the final node of either list.

**Correction:**
```java
while (list1 != null && list2 != null)
```

**3. Using two independent `if` statements**

Advancing `list2` in the first condition could change the value used by the second condition in the same iteration. This can lead to incorrect merging.

**Correction:** Use `if ... else` so that only one node is selected per iteration.

**4. Incorrect handling of remaining nodes**

When one list is exhausted, the other list may still contain nodes, including a single remaining node whose `.next` is `null`.

Attach the remaining list itself instead of checking whether its `.next` exists.

---

## 2. My Corrected Solution

This is the solution I developed and completed successfully.

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode finalNode = dummy;

        while (list1 != null && list2 != null) {
            if (list2.val <= list1.val) {
                dummy.next = list2;
                list2 = list2.next;
                dummy = dummy.next;
            } else if (list1.val <= list2.val) {
                dummy.next = list1;
                list1 = list1.next;
                dummy = dummy.next;
            }
        }

        if (list1 != null) {
            dummy.next = list1;
        } else {
            dummy.next = list2;
        }

        return finalNode.next;
    }
}
```

### Step-by-Step Explanation

**Step 1 — Create the dummy node**

```java
ListNode dummy = new ListNode(-1);
ListNode finalNode = dummy;
```

`finalNode` preserves the original dummy-node reference. `dummy` moves forward as nodes are attached.

**Step 2 — Compare the current values**

```java
while (list1 != null && list2 != null)
```

Continue while both lists have a current node.

**Step 3 — Attach the smaller node**

```java
if (list2.val <= list1.val) {
    dummy.next = list2;
    list2 = list2.next;
    dummy = dummy.next;
}
```

If `list2` contains the smaller value, attach that node to the result and advance `list2`.

Otherwise, attach the node from `list1` and advance `list1`.

After either branch, advance the tail pointer so it points to the newly attached node.

**Step 4 — Attach the remaining list**

```java
if (list1 != null) {
    dummy.next = list1;
} else {
    dummy.next = list2;
}
```

Once one list becomes `null`, all nodes in the other list are already sorted. We can attach the remainder in one operation.

**Step 5 — Return the actual head**

```java
return finalNode.next;
```

This skips the placeholder dummy node and returns the merged list.

---

## 3. Optimized and Cleaner Solution

Your solution is correct. We can simplify the comparison by using `if ... else`, because if `list1.val <= list2.val` is false, `list2` must contain the smaller value.

```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }

            current = current.next;
        }

        current.next = (list1 != null) ? list1 : list2;

        return dummy.next;
    }
}
```

### What's improved?

- Uses `current` to clearly represent the tail of the merged list.
- Removes the redundant second comparison.
- Uses a ternary expression to attach the remaining list.
- Preserves the same time and auxiliary-space complexity.

Both solutions reuse existing nodes rather than creating a new node for each input value.

---

## 4. Dry Run

Input:

```text
list1: 1 → 3 → 5
list2: 2 → 4 → 6
```

Initially:

```text
dummy → null
list1 → 1 → 3 → 5
list2 → 2 → 4 → 6
```

| Step | `list1.val` | `list2.val` | Selected node | Merged list |
|---:|---:|---:|---:|---|
| 1 | 1 | 2 | 1 | `1` |
| 2 | 3 | 2 | 2 | `1 → 2` |
| 3 | 3 | 4 | 3 | `1 → 2 → 3` |
| 4 | 5 | 4 | 4 | `1 → 2 → 3 → 4` |
| 5 | 5 | 6 | 5 | `1 → 2 → 3 → 4 → 5` |

At this point, `list1` is `null` and `list2` points to `6`.

Attach the remaining list:

```text
1 → 2 → 3 → 4 → 5 → 6
```

The final result is:

```text
[1,2,3,4,5,6]
```

---

## 5. Understanding the Pointer Operations

### Attaching a node

```java
current.next = list1;
```

This connects the last node of the merged list to the current node from `list1`.

### Advancing the input pointer

```java
list1 = list1.next;
```

This moves the `list1` pointer forward. It does not delete the node or create a copy.

### Advancing the result pointer

```java
current = current.next;
```

This moves the tail pointer to the node just attached.

### Why preserve the dummy node?

As `current` moves, the original dummy node remains unchanged. Returning `dummy.next` or `finalNode.next` gives us the beginning of the actual result.

---

## 6. Edge Cases

| Input list 1 | Input list 2 | Expected output |
|---|---|---|
| `[]` | `[]` | `[]` |
| `[]` | `[1]` | `[1]` |
| `[1]` | `[]` | `[1]` |
| `[1,2,3]` | `[4,5,6]` | `[1,2,3,4,5,6]` |
| `[4,5,6]` | `[1,2,3]` | `[1,2,3,4,5,6]` |
| `[1,2,4]` | `[1,3,4]` | `[1,1,2,3,4,4]` |

The algorithm handles empty lists, lists of unequal lengths, and equal values.

---

## 7. Complexity Analysis

Let:
- `n` = number of nodes in `list1`.
- `m` = number of nodes in `list2`.

| Complexity | Cost | Explanation |
|---|---|---|
| Time | `O(n + m)` | Each node is processed at most once |
| Auxiliary space | `O(1)` | Only a fixed number of pointers are used |

The dummy node adds only constant space. The input nodes are reused, so we do not need an additional list containing copies of all values.

---

## 8. Interview Takeaways

1. When merging sorted lists, always choose the smaller current value.
2. A dummy node simplifies linked-list construction.
3. Preserve the dummy node reference so you can return the result's head.
4. Advance the input pointer whose node you just selected.
5. Advance the result tail after attaching a node.
6. When one input list becomes `null`, attach the remaining list directly.
7. Returning `dummy.next` excludes the placeholder node.
8. Reusing existing nodes enables an `O(1)` auxiliary-space solution.

## 🧠 Quick Revision

**Pattern:** Compare → Attach smaller node → Advance input pointer → Advance result tail → Attach remainder.

```java
while (list1 != null && list2 != null) {
    if (list1.val <= list2.val) {
        current.next = list1;
        list1 = list1.next;
    } else {
        current.next = list2;
        list2 = list2.next;
    }

    current = current.next;
}

current.next = (list1 != null) ? list1 : list2;

return dummy.next;
```

**Time:** `O(n + m)`

**Auxiliary space:** `O(1)`

**Key lesson from my second Linked List problem:** A dummy node makes it easier to build a linked list, while separate input and tail pointers let us merge lists without losing track of their nodes.
