
# 🔗 LeetCode 203 — Remove Linked List Elements

> **Milestone:** My fourth Linked List problem!

## 📝 Problem Statement

Given the head of a linked list and an integer `val`, remove all nodes from the list that have `Node.val == val`.

Return the head of the modified linked list.

## 🧪 Examples

### Example 1

**Input:**
```text
head = [1,2,6,3,4,5,6]
val = 6
```

**Output:**
```text
[1,2,3,4,5]
```

### Example 2

**Input:**
```text
head = []
val = 1
```

**Output:**
```text
[]
```

### Example 3

**Input:**
```text
head = [7,7,7,7]
val = 7
```

**Output:**
```text
[]
```

---

## 💡 Approach: Dummy Node + Pointer Traversal

### Core Idea

Traverse the linked list and remove every node whose value matches `val`.

A **dummy node** is placed before the original head. This makes it easier to remove the head itself, because every node—including the original first node—has a predecessor.

For each node:

- If `current.next.val == val`, skip the next node.
- Otherwise, advance `current`.
- If a node is removed, keep `current` in place so the new next node can be checked.

---

## 1. My Original Attempt

```java
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(-1, head);
        ListNode current = dummy;

        while (current.next != null) {
            if (current.next.val == val) {
                current.next = current.next.next;
                current = current.next;
            } else {
                current = current.next;
            }
        }

        return dummy.next;
    }
}
```

### What was wrong?

The issue was this line inside the `if` block:

```java
current = current.next;
```

After removing a node, the current node's `next` reference already points to the next candidate.

Advancing `current` immediately can skip checking that candidate, which may also contain `val`.

### Example of the bug

Input:

```text
1 → 6 → 6 → 2
val = 6
```

After removing the first `6`:

```text
1 → 6 → 2
```

If `current` advances immediately, the second `6` may be skipped.

**Lesson:** After removing a node, do not advance `current`. Check its new next node first.

---

## 2. My Corrected Solution

This is the solution I completed successfully.

```java
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(-1, head);
        ListNode current = dummy;

        while (current.next != null) {
            if (current.next.val == val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }

        return dummy.next;
    }
}
```

### Step-by-Step Explanation

**Step 1 — Create the dummy node**

```java
ListNode dummy = new ListNode(-1, head);
ListNode current = dummy;
```

The dummy node points to the original head.

This handles cases where the original head itself must be removed.

**Step 2 — Check that a next node exists**

```java
while (current.next != null)
```

We inspect `current.next`, so we must ensure it exists before accessing its value.

**Step 3 — Remove a matching node**

```java
if (current.next.val == val) {
    current.next = current.next.next;
}
```

This skips the matching node by redirecting the current node's link.

For example:

```text
Before: 1 → 6 → 2
After:  1 ─────→ 2
```

The node containing `6` is no longer reachable from the list.

**Step 4 — Advance only when keeping the next node**

```java
else {
    current = current.next;
}
```

When the next node does not match `val`, move forward.

When a node is removed, stay at the same `current` node so consecutive matches are also removed.

**Step 5 — Return the updated head**

```java
return dummy.next;
```

The dummy node is not part of the answer. Its `next` reference points to the actual first node of the modified list, or `null` if the list becomes empty.

---

## 3. Dry Run

Input:

```text
head = [1,6,6,2,6]
val = 6
```

Initial list:

```text
Dummy → 1 → 6 → 6 → 2 → 6
```

| Step | `current` | `current.next` | Action |
|---:|---|---|---|
| 1 | Dummy | 1 | Keep 1; advance |
| 2 | 1 | 6 | Remove 6 |
| 3 | 1 | 6 | Remove 6 again |
| 4 | 1 | 2 | Keep 2; advance |
| 5 | 2 | 6 | Remove 6 |
| 6 | 2 | `null` | Stop |

Final list:

```text
1 → 2
```

Output:

```text
[1,2]
```

---

## 4. Why Is the Dummy Node Useful?

Without a dummy node, removing the original head requires special handling:

```text
Before: 6 → 6 → 1
val = 6
```

The head itself must change twice before the result becomes:

```text
1
```

With a dummy node:

```text
Dummy → 6 → 6 → 1
```

The same removal logic works for every node, including the original head. No separate head-removal loop is necessary.

### Pattern to remember

```java
ListNode dummy = new ListNode(-1, head);
ListNode current = dummy;
```

After processing:

```java
return dummy.next;
```

This is a common technique for linked-list insertion, deletion, and merging problems.

---

## 5. Edge Cases

| Input | `val` | Expected output |
|---|---:|---|
| `[]` | 1 | `[]` |
| `[1]` | 1 | `[]` |
| `[1]` | 2 | `[1]` |
| `[6,6,6]` | 6 | `[]` |
| `[1,2,3]` | 2 | `[1,3]` |
| `[1,6,6,2]` | 6 | `[1,2]` |

The solution handles empty lists, head removal, consecutive matches, and lists containing no matching values.

---

## 6. Complexity Analysis

Let `n` be the number of nodes in the list.

| Complexity | Cost | Explanation |
|---|---|---|
| Time | `O(n)` | Each node is examined at most a constant number of times |
| Auxiliary space | `O(1)` | Only a dummy node and traversal pointer are used |

The list is modified in place. No additional collection or replacement list is created.

---

## 7. Interview Takeaways

1. A dummy node simplifies deletion when the head may change.
2. Check `current.next` before accessing its value.
3. Remove a node by redirecting the previous node's `next` reference.
4. Do not advance the current pointer immediately after deletion.
5. `return dummy.next` returns the updated head.
6. Reusing nodes allows the solution to use constant auxiliary space.

## 🧠 Quick Revision

**Pattern:** Create dummy → Inspect next node → Remove or advance → Return `dummy.next`.

```java
while (current.next != null) {
    if (current.next.val == val) {
        current.next = current.next.next;
    } else {
        current = current.next;
    }
}

return dummy.next;
```

**Time complexity:** `O(n)`

**Auxiliary space:** `O(1)`

**Key lesson:** When deleting nodes from a linked list, carefully control pointer movement. After removing a node, the next candidate may also need to be removed.
