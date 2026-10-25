
# 🔗 LeetCode 83 — Remove Duplicates from Sorted List

> **Milestone:** My first Linked List problem!

## 📝 Problem Statement

Given the `head` of a sorted linked list, delete all duplicate values so that each element appears only once.

Return the head of the modified linked list.

The list is sorted in non-decreasing order, so duplicate values are always adjacent.

### Example 1

**Input:**
```text
1 → 1 → 2
```

**Output:**
```text
1 → 2
```

### Example 2

**Input:**
```text
1 → 1 → 2 → 3 → 3
```

**Output:**
```text
1 → 2 → 3
```

### Example 3

**Input:**
```text
[]
```

**Output:**
```text
[]
```

---

## 💡 Approach: Iterative Traversal

### Core Idea

Since the linked list is sorted, we only need to compare each node with the node immediately after it.

- If both nodes have the same value, skip the next node.
- If their values differ, move to the next node.
- Continue until there is no next node to compare.

We do not need an additional data structure such as a `HashSet`.

## 1. My Original Attempt

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode tempNode = head;

        while (tempNode.next.next != null) {
            if (tempNode.next == tempNode.next.next) {
                tempNode.next = tempNode.next.next;
            }
        }

        return ListNode;
    }
}
```

### What needed fixing?

**1. Unsafe null handling**

```java
while (tempNode.next.next != null)
```

This can throw a `NullPointerException` if the list is empty or too short.

**2. Comparing node references instead of values**

```java
tempNode.next == tempNode.next.next
```

This checks whether two references point to the same node. We need to compare their values instead:

```java
tempNode.val == tempNode.next.val
```

**3. Missing pointer movement**

When two values differ, the pointer must move forward. Otherwise, the loop can run indefinitely.

**4. Incorrect return statement**

```java
return ListNode;
```

`ListNode` is a type, not the variable holding the list's head. We should return `head`.

---

## 2. My Corrected Solution

This is the solution I completed successfully.

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode tempNode = head;

        while (tempNode != null && tempNode.next != null) {
            if (tempNode.val == tempNode.next.val) {
                tempNode.next = tempNode.next.next;
            } else {
                tempNode = tempNode.next;
            }
        }

        return head;
    }
}
```

### Step-by-Step Explanation

**Step 1 — Initialize the pointer**

```java
ListNode tempNode = head;
```

`tempNode` points to the first node. We use it to traverse the list without changing the original `head` reference.

**Step 2 — Check the current and next nodes**

```java
while (tempNode != null && tempNode.next != null)
```

Both conditions must be true:

- `tempNode != null`: the current node exists.
- `tempNode.next != null`: a next node exists to compare.

The `&&` operator short-circuits, so Java does not evaluate `tempNode.next` when `tempNode` is `null`.

**Step 3 — Remove a duplicate**

```java
if (tempNode.val == tempNode.next.val) {
    tempNode.next = tempNode.next.next;
}
```

Suppose the list is:

```text
1 → 1 → 2
```

The first node's `next` initially points to the second node. We redirect it to the third node:

```text
1 ─────────→ 2
```

The duplicate is no longer reachable from the list.

**Step 4 — Move forward when values differ**

```java
else {
    tempNode = tempNode.next;
}
```

When adjacent values differ, the current node is not a duplicate of its next node, so we advance.

**Step 5 — Return the head**

```java
return head;
```

We return the beginning of the modified list, not the temporary pointer.

---

## 3. Dry Run

Input:

```text
1 → 1 → 1 → 2 → 3 → 3
```

Initially, `tempNode` points to the first node.

| Step | Current value | Next value | Action |
|---:|---:|---:|---|
| 1 | 1 | 1 | Skip the next node |
| 2 | 1 | 1 | Skip the next node again |
| 3 | 1 | 2 | Move forward |
| 4 | 2 | 3 | Move forward |
| 5 | 3 | 3 | Skip the next node |
| 6 | 3 | `null` | Stop |

Final list:

```text
1 → 2 → 3
```

### Why don't we move the pointer after deleting a duplicate?

Consider:

```text
1 → 1 → 1 → 2
```

After the first deletion, the list becomes:

```text
1 → 1 → 2
```

The current node still has the value `1`, and its new next node also has the value `1`.

If we advanced immediately, we could miss another duplicate. Therefore, we check the same current node again after deletion.

---

## 4. Understanding Linked List Pointers

A singly linked list node contains two main pieces of information:

```java
class ListNode {
    int val;
    ListNode next;
}
```

- `val` stores the node's value.
- `next` stores a reference to the next node.

### Important distinction

```java
tempNode.next
```

Refers to the next node.

```java
tempNode.next.val
```

Accesses the value stored in the next node.

```java
tempNode.next = tempNode.next.next;
```

Changes the link to skip the next node.

This is the fundamental operation used in this problem.

---

## 5. Edge Cases

| Input | Expected output | Reason |
|---|---|---|
| `[]` | `[]` | Empty list |
| `[1]` | `[1]` | Only one node |
| `[1, 1]` | `[1]` | Two identical values |
| `[1, 2, 3]` | `[1, 2, 3]` | No duplicates |
| `[1, 1, 1]` | `[1]` | All values identical |
| `[1, 1, 2, 3, 3]` | `[1, 2, 3]` | Duplicates at different positions |

The corrected solution handles all these cases.

---

## 6. Complexity Analysis

Let `n` be the number of nodes in the linked list.

| Complexity | Result | Explanation |
|---|---|---|
| Time | `O(n)` | Each node is visited a constant number of times |
| Auxiliary space | `O(1)` | Only one traversal pointer is used |

The algorithm modifies the links in place. It does not create a new linked list or use additional collections.

---

## 7. Interview Takeaways

- Sorted lists make duplicate detection easier because equal values are adjacent.
- Compare node values using `.val`, not node references.
- Always guard pointer access against `null`.
- When deleting a node, update the link using `current.next = current.next.next`.
- Do not advance the pointer after deleting a duplicate; check the same node again.
- Preserve and return the original `head` reference.
- In-place linked-list operations can achieve `O(1)` auxiliary space.

## 🧠 Quick Revision

**Pattern:** Compare adjacent nodes → skip duplicates → advance only when values differ.

```java
while (current != null && current.next != null) {
    if (current.val == current.next.val) {
        current.next = current.next.next;
    } else {
        current = current.next;
    }
}
```

**Time:** `O(n)`

**Auxiliary space:** `O(1)`

**Key lesson from my first Linked List problem:** Changing a node's `next` reference changes the structure of the list. Correct pointer movement and null checks are essential.
