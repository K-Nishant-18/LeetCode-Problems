
# 🔗 LeetCode 141 — Linked List Cycle

> **Milestone:** My third Linked List problem!

## 📝 Problem Statement

Given the `head` of a singly linked list, determine whether the linked list contains a cycle.

A cycle exists if some node can be reached again by continuously following the `next` pointer.

Return `true` if a cycle exists; otherwise, return `false`.

**Important:** A cycle is about revisiting the same node, not merely encountering the same value.

## 🧪 Examples

### Example 1 — Cycle Exists

```text
3 → 2 → 0 → -4
    ↑         |
    └─────────┘
```

The last node points back to the node containing `2`.

**Output:**
```java
true
```

### Example 2 — No Cycle

```text
1 → 2 → null
```

**Output:**
```java
false
```

### Example 3 — Empty List

```text
head = null
```

**Output:**
```java
false
```

---

## 💡 Approach: HashSet of Visited Nodes

### Core Idea

Traverse the linked list and store every visited node in a `HashSet`.

- If the current node is already in the set, we have revisited a node. A cycle exists.
- If we reach `null`, the list ends normally and has no cycle.

We store **node references**, not node values.

### Why store node references?

Consider two different nodes:

```text
Node A: value = 5
Node B: value = 5
```

These are different nodes even though their values are equal.

A cycle is detected when we encounter the same node reference again, not just the same integer value.

---

## 1. My Original Attempt

```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        boolean flag = false;
        ListNode temp = head;
        Set<ListNode> set = new HashSet<>();

        while (temp != null) {
            if (!set.add(temp)) {
                flag = true;
                return flag;
            }
        }

        return flag;
    }
}
```

### What was wrong?

The pointer `temp` was never advanced inside the loop.

```java
while (temp != null) {
    if (!set.add(temp)) {
        flag = true;
        return flag;
    }
}
```

If the list had no cycle, `temp` would keep pointing to the same node forever, causing an infinite loop.

### Correction

Advance to the next node after confirming that the current node hasn't already been visited:

```java
temp = temp.next;
```

---

## 2. My Corrected Solution

This is the solution I completed successfully.

```java
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public boolean hasCycle(ListNode head) {
        boolean flag = false;
        ListNode temp = head;
        Set<ListNode> set = new HashSet<>();

        while (temp != null) {
            if (!set.add(temp)) {
                flag = true;
                return flag;
            }

            temp = temp.next;
        }

        return flag;
    }
}
```

### Step-by-Step Explanation

**Step 1 — Initialize the pointer and set**

```java
ListNode temp = head;
Set<ListNode> set = new HashSet<>();
```

`temp` traverses the list. The set remembers every node we have visited.

**Step 2 — Continue while a node exists**

```java
while (temp != null)
```

If `temp` becomes `null`, we have reached the end of the list without finding a cycle.

**Step 3 — Check whether the node was visited**

```java
if (!set.add(temp)) {
    flag = true;
    return flag;
}
```

The `HashSet.add()` method returns:

- `true` if the node was not already present.
- `false` if the node was already present.

Therefore, `!set.add(temp)` means that the node has been visited before, proving that a cycle exists.

**Step 4 — Move to the next node**

```java
temp = temp.next;
```

This ensures the traversal progresses instead of repeatedly checking the same node.

**Step 5 — Return false if the traversal ends**

```java
return flag;
```

Since `flag` starts as `false` and the method returns immediately when a cycle is detected, reaching this line means no cycle exists.

---

## 3. Cleaner Version of My Solution

The `flag` variable is unnecessary because we can return `true` or `false` directly.

```java
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        ListNode temp = head;

        while (temp != null) {
            if (!set.add(temp)) {
                return true;
            }

            temp = temp.next;
        }

        return false;
    }
}
```

This version has the same algorithm and complexity as my corrected solution, but fewer variables and less code.

---

## 4. Dry Run — Cycle Exists

Consider this linked list:

```text
3 → 2 → 0 → -4
    ↑         |
    └─────────┘
```

The last node points back to the node containing `2`.

| Step | Current node value | Already visited? | Set after processing |
|---:|---:|---|---|
| 1 | 3 | No | `{3}` |
| 2 | 2 | No | `{3, 2}` |
| 3 | 0 | No | `{3, 2, 0}` |
| 4 | -4 | No | `{3, 2, 0, -4}` |
| 5 | 2 | Yes | Cycle detected |

At step 5, the traversal reaches the exact same node containing `2` that was visited earlier.

**Output:**
```java
true
```

## 5. Dry Run — No Cycle

Input:

```text
1 → 2 → 3 → null
```

| Step | Current node | Action |
|---:|---:|---|
| 1 | 1 | Add to set |
| 2 | 2 | Add to set |
| 3 | 3 | Add to set |
| 4 | `null` | Stop |

The pointer reaches `null` without revisiting any node.

**Output:**
```java
false
```

---

## 6. Complexity Analysis

Let `n` be the number of distinct nodes visited before termination.

| Complexity | Cost | Explanation |
|---|---|---|
| Time | `O(n)` expected | Each node is inserted into or checked against the set |
| Auxiliary space | `O(n)` | The set stores visited node references |

A `HashSet` generally provides expected constant-time insertion and lookup, so processing `n` nodes takes expected `O(n)` time.

The extra memory is used to remember visited nodes.

---

## 7. Edge Cases

| Case | Expected result | Reason |
|---|---|---|
| Empty list | `false` | `head` is `null` |
| One node pointing to `null` | `false` | No cycle exists |
| One node pointing to itself | `true` | The same node is revisited |
| Multiple nodes, no cycle | `false` | Traversal reaches `null` |
| Cycle begins at the head | `true` | The head node is eventually revisited |
| Cycle begins in the middle | `true` | A previously visited node is encountered again |

---

## 8. Interview Takeaways

1. A linked-list cycle is detected by revisiting the same node reference.
2. Use `HashSet<ListNode>` when you want to remember visited nodes.
3. Advance the pointer with `temp = temp.next` during traversal.
4. `HashSet.add()` returns `false` when the same element is already present.
5. Always consider empty lists and self-referencing nodes.
6. A visited-node set uses `O(n)` auxiliary space.
7. A more space-efficient approach exists using fast and slow pointers; I will study it separately.

## 🧠 Quick Revision

**Pattern:** Traverse → Check visited → Detect cycle or advance.

```java
while (temp != null) {
    if (!set.add(temp)) {
        return true;
    }

    temp = temp.next;
}

return false;
```

**Time complexity:** `O(n)` expected.

**Auxiliary space:** `O(n)`.

**Key lesson from my third Linked List problem:** Track node references rather than values, and ensure every traversal loop either advances its pointer or returns a result.
