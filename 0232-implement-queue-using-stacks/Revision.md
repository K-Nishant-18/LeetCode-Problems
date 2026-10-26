
# LeetCode 232 — Implement Queue using Stacks

- **Difficulty:** Easy
- **Topics:** Stack, Queue, Design
- **Pattern:** Two Stacks to Implement FIFO
- **Problem:** https://leetcode.com/problems/implement-queue-using-stacks/

---

## 1. Problem Statement

Implement a first-in-first-out (FIFO) queue using only two stacks.

Implement the following operations:

- `push(int x)`: Push element `x` to the back of the queue.
- `pop()`: Remove the element from the front of the queue and return it.
- `peek()`: Return the front element without removing it.
- `empty()`: Return `true` if the queue is empty, otherwise `false`.

### Example

```text
Operations:
push(1)
push(2)
peek()
pop()
empty()

Results:
null
null
1
1
false
```

The first element inserted must be the first element removed.

---

## 2. Core Concept

A stack follows **LIFO** — Last In, First Out.

A queue follows **FIFO** — First In, First Out.

Our challenge is to use stacks to simulate queue behavior.

Suppose we insert:

```text
push(10)
push(20)
push(30)
```

The first stack contains:

```text
stack1 (top)
    30
    20
    10
```

If we transfer all elements to a second stack, their order reverses:

```text
stack2 (top)
    10
    20
    30
```

Now `stack2.pop()` returns `10`, which is the correct queue behavior.

### Key Insight

Use two stacks:

- `stack1`: stores newly inserted elements.
- `stack2`: provides the next element to remove or inspect.

**Transfer elements from `stack1` to `stack2` only when `stack2` is empty.**

This is important because elements already in `stack2` are older and must leave the queue first.

---

## 3. My Original Attempt

```java
Deque<Integer> stack1 = new ArrayDeque<>();
Deque<Integer> stack2 = new ArrayDeque<>();

public void push(int x) {
    while (stack2 != isEmpty()) {
        stack1.push(stack2.pop());
    }
    stack1.push(x);
}

public int pop() {
    while (stack1 != isEmpty()) {
        stack2.push(stack1.pop());
    }
    return stack2.pop();
}
```

### Problems

1. The `while` condition is invalid Java syntax. Use `!stack2.isEmpty()`.
2. `push()` should not transfer existing elements unnecessarily.
3. `pop()` and `peek()` should transfer elements only if `stack2` is empty.
4. Java uses `{}` for loop bodies, not `()`.

---

## 4. Corrected Solution — My Approach

```java
import java.util.ArrayDeque;
import java.util.Deque;

class MyQueue {
    Deque<Integer> stack1 = new ArrayDeque<>();
    Deque<Integer> stack2 = new ArrayDeque<>();

    public MyQueue() {
    }

    public void push(int x) {
        stack1.push(x);
    }

    public int pop() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }

        return stack2.pop();
    }

    public int peek() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }

        return stack2.peek();
    }

    public boolean empty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }
}
```

### Explanation

**`push(x)`**

```java
stack1.push(x);
```

New elements are stored in `stack1`. No transfer is needed.

**`pop()`**

```java
if (stack2.isEmpty()) {
    while (!stack1.isEmpty()) {
        stack2.push(stack1.pop());
    }
}
return stack2.pop();
```

If `stack2` has elements, its top is already the oldest element. Otherwise, transfer all elements from `stack1`.

**`peek()`**

Uses the same transfer rule as `pop()`, but reads the top without removing it.

**`empty()`**

The queue is empty only when both stacks are empty.

---

## 5. Optimized Solution

The corrected solution above is already optimal in amortized time complexity and auxiliary space.

A cleaner implementation can use helper methods to avoid duplicating transfer logic.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class MyQueue {
    private final Deque<Integer> input = new ArrayDeque<>();
    private final Deque<Integer> output = new ArrayDeque<>();

    public MyQueue() {
    }

    public void push(int x) {
        input.push(x);
    }

    private void moveIfNeeded() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
        }
    }

    public int pop() {
        moveIfNeeded();
        return output.pop();
    }

    public int peek() {
        moveIfNeeded();
        return output.peek();
    }

    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }
}
```

### Why Is This Version Cleaner?

- `moveIfNeeded()` contains the transfer logic in one place.
- `pop()` and `peek()` remain short and easy to understand.
- `private` prevents external code from accessing the internal stacks directly.
- `final` ensures the stack references cannot be reassigned after initialization.

The algorithm itself is unchanged.

---

## 6. Dry Run

Operations:

```text
push(10)
push(20)
push(30)
pop()
push(40)
pop()
```

Stack contents are shown from bottom to top.

| Operation | stack1 | stack2 | Result |
|---|---|---|---|
| `push(10)` | `[10]` | `[]` | — |
| `push(20)` | `[10, 20]` | `[]` | — |
| `push(30)` | `[10, 20, 30]` | `[]` | — |
| `pop()` | `[]` | `[30, 20]` | `10` |
| `push(40)` | `[40]` | `[30, 20]` | — |
| `pop()` | `[40]` | `[30]` | `20` |

The next element returned is always the oldest remaining element.

Notice that `push(40)` does not disturb the older elements in `stack2`.

---

## 7. Complexity Analysis

Let `n` be the number of elements in the queue.

| Operation | Complexity |
|---|---|
| `push()` | `O(1)` |
| `pop()` | `O(1)` amortized; `O(n)` worst case |
| `peek()` | `O(1)` amortized; `O(n)` worst case |
| `empty()` | `O(1)` |
| Auxiliary space | `O(n)` |

### Why Are `pop()` and `peek()` Amortized O(1)?

Every element:

1. Is pushed into `stack1` once.
2. Is transferred to `stack2` at most once.
3. Is removed from `stack2` at most once.

Therefore, a sequence of `n` operations performs only `O(n)` total stack work, even though an individual transfer may take `O(n)`.

---

## 8. Important Edge Cases

| Scenario | Expected behavior |
|---|---|
| Pop after one push | Returns the inserted value |
| Peek multiple times | Returns the same front value each time |
| Push after popping | New values are added behind older queued values |
| Both stacks empty | `empty()` returns `true` |
| `stack2` contains elements | Do not transfer from `stack1` |

LeetCode guarantees that `pop()` and `peek()` are called only when the queue is non-empty.

---

## 9. Common Mistakes

- Transferring elements on every `pop()` even when `stack2` is not empty.
- Moving elements in the wrong direction.
- Assuming `push()` needs to rearrange existing elements.
- Forgetting that `peek()` must not remove an element.
- Checking only one stack in `empty()`.

---

## 10. Interview Takeaways

- A stack can simulate a queue using two stacks.
- Reversing the order twice restores FIFO behavior.
- Lazy transfer avoids unnecessary work.
- Amortized analysis explains why expensive transfers do not make the overall algorithm quadratic.
- `Deque` with `ArrayDeque` is a good Java choice for stack operations.

### Revision Checklist

- [ ] Explain LIFO versus FIFO.
- [ ] Explain why transferring reverses the order.
- [ ] Implement `push()`, `pop()`, `peek()`, and `empty()`.
- [ ] Explain why transfer occurs only when the output stack is empty.
- [ ] Derive the amortized `O(1)` complexity.

**Core lesson:** Keep new elements in one stack and reverse their order only when the queue needs its oldest element.
