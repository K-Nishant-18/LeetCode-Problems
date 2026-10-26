
# LeetCode 155 — Min Stack

- **Difficulty:** Medium
- **Topic:** Stack, Design
- **Approach:** Two Stacks
- **Time Complexity:** O(1) for every operation
- **Space Complexity:** O(n)

---

## 1. Problem Statement

Design a stack that supports the following operations in constant time, O(1):

- `MinStack()` — Initialize the stack.
- `push(int val)` — Push an element onto the stack.
- `pop()` — Remove the top element.
- `top()` — Return the top element.
- `getMin()` — Return the minimum element currently in the stack.

### Example

**Input:**
```text
push(-2)
push(0)
push(-3)
getMin()
pop()
top()
getMin()
```

**Output:**
```text
-3
0
-2
```

**Explanation:**
- After pushing `-2`, `0`, and `-3`, the minimum is `-3`.
- `pop()` removes `-3`.
- `top()` returns `0`.
- `getMin()` returns `-2`.

---

## 2. My Original Solution

My initial approach used a main stack and a list. To find the minimum, it moved all elements into the list, found the minimum, and restored the stack.

```java
class MinStack {

    Deque<Integer> stack = new ArrayDeque<>();
    List<Integer> list = new ArrayList<>();

    public MinStack() {}

    public void push(int value) {
        stack.push(value);
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }

        int minNumber = Collections.min(list);
        int n = list.size();

        while (n > 0) {
            stack.push(list.get(n - 1));
            n--;
        }

        list.clear();
        return minNumber;
    }
}
```

### Why this approach is inefficient

- `push()` takes O(1).
- `pop()` takes O(1).
- `top()` takes O(1).
- `getMin()` takes O(n), because it traverses all elements.

The problem requires `getMin()` to run in O(1).

**Lesson:** Finding the minimum by scanning the entire stack is unnecessary if we maintain the minimum while pushing and popping.

---

## 3. Optimized Approach — Two Stacks

Use two stacks:

1. **Main stack:** Stores every element.
2. **Minimum stack:** Tracks values that become the current minimum.

### Core idea

Whenever an element is pushed:

- Push it onto the main stack.
- Push it onto the minimum stack only if the minimum stack is empty or the new value is less than or equal to its top.

Whenever an element is popped:

- Remove the top element from the main stack.
- If the removed value equals the top of the minimum stack, pop the minimum stack too.

To get the minimum, simply return `minStack.peek()`.

### Why use `<=` instead of `<`?

Duplicate minimum values must be tracked separately.

For example, if we push `2, 2, 3`, the minimum stack stores `2, 2`.

When one `2` is removed, the other `2` remains as the minimum.

---

## 4. My Optimized Solution

```java
import java.util.ArrayDeque;
import java.util.Deque;

class MinStack {

    Deque<Integer> stack = new ArrayDeque<>();
    Deque<Integer> minStack = new ArrayDeque<>();

    public MinStack() {}

    public void push(int value) {
        stack.push(value);

        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
        }
    }

    public void pop() {
        int temp = stack.pop();

        if (temp == minStack.peek()) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

**Important:** This implementation assumes the operations are valid, as guaranteed by the problem constraints.

---

## 5. Dry Run

Operations:

```text
push(5)
push(2)
push(7)
getMin()
pop()
getMin()
pop()
getMin()
```

Stacks are shown from **bottom to top**, with the rightmost element being the top.

| Operation | Main Stack | Minimum Stack | Result |
|---|---|---|---|
| `push(5)` | `[5]` | `[5]` | — |
| `push(2)` | `[5, 2]` | `[5, 2]` | — |
| `push(7)` | `[5, 2, 7]` | `[5, 2]` | — |
| `getMin()` | `[5, 2, 7]` | `[5, 2]` | `2` |
| `pop()` | `[5, 2]` | `[5, 2]` | — |
| `getMin()` | `[5, 2]` | `[5, 2]` | `2` |
| `pop()` | `[5]` | `[5]` | — |
| `getMin()` | `[5]` | `[5]` | `5` |

### What happened?

- `5` became the first minimum.
- `2` became the new minimum.
- `7` did not affect the minimum.
- Removing `7` did not affect the minimum stack.
- Removing `2` removed it from both stacks, revealing `5` as the minimum.

---

## 6. Edge Cases

### Case 1: A single element

```java
push(10);
getMin(); // 10
pop();
```

Both stacks contain `10` before the pop.

### Case 2: Duplicate minimum values

```java
push(2);
push(2);
push(3);

pop();    // Removes 3
pop();    // Removes one occurrence of 2
getMin(); // 2
```

The minimum remains `2` because both occurrences were tracked.

### Case 3: Increasing values

```java
push(1);
push(2);
push(3);
```

Main stack: `[1, 2, 3]`

Minimum stack: `[1]`

Only `1` is needed to track the minimum.

### Case 4: Decreasing values

```java
push(5);
push(3);
push(1);
```

Main stack: `[5, 3, 1]`

Minimum stack: `[5, 3, 1]`

Every pushed value becomes a new minimum.

### Case 5: Negative numbers

```java
push(-2);
push(0);
push(-3);
getMin(); // -3
```

Negative numbers work normally; no special handling is required.

---

## 7. Complexity Analysis

Let `n` be the number of elements in the main stack.

| Operation | Time Complexity | Reason |
|---|---|---|
| `push()` | O(1) | Constant number of stack operations |
| `pop()` | O(1) | Constant number of stack operations |
| `top()` | O(1) | Reads the top element |
| `getMin()` | O(1) | Reads the top of the minimum stack |

**Auxiliary space:** O(n)

In the worst case, every new element is smaller than or equal to the current minimum, so the minimum stack can contain all `n` elements.

---

## 8. Interview Takeaways

1. A stack alone cannot provide the minimum in O(1) unless additional information is maintained.
2. An auxiliary stack can track the current minimum efficiently.
3. Use `<=` when pushing minimum values to correctly handle duplicates.
4. During `pop()`, update the minimum stack only when the removed value matches its top.
5. Think about how the data structure changes after every operation, not just how to calculate the answer once.
6. Prefer `Deque<Integer>` with `ArrayDeque<>` for stack operations in modern Java.

---

## 9. Quick Revision

**Question:** Why don't we store every element in the minimum stack?

**Answer:** Elements that are greater than the current minimum cannot affect `getMin()` while that minimum remains in the stack. We store only values that become a new minimum, including duplicates.

**Question:** Why do we remove an element from the minimum stack during `pop()`?

**Answer:** If the removed element is the current minimum, the minimum stack must discard that occurrence so the previous minimum becomes available.

**Question:** What makes `getMin()` O(1)?

**Answer:** The current minimum is always available at the top of the minimum stack.

**Question:** What is the most important edge case?

**Answer:** Duplicate minimum values, which are handled by using `<=` during `push()`.

---

## Final Summary

The key optimization is to **maintain the minimum as the stack changes instead of searching for it when requested**.

- Original approach: `getMin()` is O(n).
- Optimized approach: every operation is O(1).
- Extra space: O(n).

**Pattern to remember:** When a stack must return additional information quickly, consider maintaining an auxiliary stack that tracks that information.
