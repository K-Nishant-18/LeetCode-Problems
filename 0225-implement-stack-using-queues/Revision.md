
# LeetCode 225 — Implement Stack using Queues

- **Difficulty:** Easy
- **Topics:** Stack, Queue, Design
- **Pattern:** Queue Rotation to Simulate LIFO
- **Problem:** https://leetcode.com/problems/implement-stack-using-queues/

---

## 1. Problem Statement

Implement a last-in-first-out (LIFO) stack using one or two queues.

Implement the following operations:

- `push(int x)`: Push element `x` onto the stack.
- `pop()`: Remove the top element and return it.
- `top()`: Return the top element without removing it.
- `empty()`: Return `true` if the stack is empty, otherwise `false`.

### Example

```text
push(10)
push(20)
push(30)
top()
pop()
empty()

Results:
null
null
null
30
30
false
```

The most recently inserted element must be the first one removed.

---

## 2. Core Concept

A stack follows **LIFO**.

A queue follows **FIFO**.

A queue normally removes the oldest element first, but a stack must remove the newest element first.

Suppose we insert:

```text
push(10)
push(20)
push(30)
```

We want the queue to contain:

```text
Front → 30, 20, 10 ← Rear
```

Then `queue.poll()` returns `30`, which correctly simulates stack behavior.

### Key Insight

Use two queues:

- `queue1`: holds the elements in stack order.
- `queue2`: temporarily helps rearrange elements when pushing a new value.

**After each push, place the newest element at the front of the active queue.**

This allows `pop()` and `top()` to operate on the front in constant time.

---

## 3. My Original Attempt

```java
Queue<Integer> queue1 = new ArrayDeque<>();
Queue<Integer> queue2 = new ArrayDeque<>();

public void push(int x) {
    queue1.offer(x);
}

public int pop() {
    if (queue2.isEmpty()) {
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }
    }

    return queue2.poll();
}

public int top() {
    if (queue2.isEmpty()) {
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }
    }

    return queue2.peek();
}
```

### Problem

Transferring elements from one queue to another preserves their order.

For example:

```text
queue1: Front → 10, 20, 30 ← Rear
queue2: Front → 10, 20, 30 ← Rear
```

Calling `queue2.poll()` returns `10`, but a stack must return `30`.

The solution is to rotate the elements during `push()`, not during `pop()`.

---

## 4. Corrected Solution — My Approach

```java
import java.util.ArrayDeque;
import java.util.Queue;

class MyStack {
    Queue<Integer> queue1 = new ArrayDeque<>();
    Queue<Integer> queue2 = new ArrayDeque<>();

    public MyStack() {
    }

    public void push(int x) {
        while (!queue1.isEmpty()) {
            queue2.offer(queue1.poll());
        }

        queue1.offer(x);

        while (!queue2.isEmpty()) {
            queue1.offer(queue2.poll());
        }
    }

    public int pop() {
        return queue1.poll();
    }

    public int top() {
        return queue1.peek();
    }

    public boolean empty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }
}
```

### How It Works

**Step 1: Move the old elements to `queue2`**

```java
while (!queue1.isEmpty()) {
    queue2.offer(queue1.poll());
}
```

This temporarily empties `queue1`.

**Step 2: Insert the new element into `queue1`**

```java
queue1.offer(x);
```

Now the new element is at the front and rear because it is the only element.

**Step 3: Move the older elements behind the new element**

```java
while (!queue2.isEmpty()) {
    queue1.offer(queue2.poll());
}
```

The new element stays at the front, and the older elements follow it.

**Step 4: Implement the remaining operations**

```java
public int pop() {
    return queue1.poll();
}

public int top() {
    return queue1.peek();
}
```

Because the newest element is always at the front, `pop()` and `top()` need no transfer.

---

## 5. Optimized Solution

The original two-queue approach is correct, but we can simplify it using a single queue.

After adding the new element, rotate the queue until the new element reaches the front.

```java
import java.util.ArrayDeque;
import java.util.Queue;

class MyStack {
    private final Queue<Integer> queue = new ArrayDeque<>();

    public MyStack() {
    }

    public void push(int x) {
        queue.offer(x);

        int size = queue.size();

        for (int i = 0; i < size - 1; i++) {
            queue.offer(queue.poll());
        }
    }

    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.element();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
```

### Why Does the Rotation Work?

Suppose the queue contains:

```text
Front → 10, 20, 30 ← Rear
```

We call:

```java
push(40);
```

First, insert `40`:

```text
Front → 10, 20, 30, 40 ← Rear
```

The original queue size before rotation was four.

Rotate the first three elements to the rear:

1. Move `10` to the rear.
2. Move `20` to the rear.
3. Move `30` to the rear.

Result:

```text
Front → 40, 10, 20, 30 ← Rear
```

Now the newest element is at the front.

### Why Is This Version Better?

- Uses one queue instead of two.
- Reduces the amount of state that must be managed.
- Keeps `pop()`, `top()`, and `empty()` simple.
- Maintains the same time complexity as the two-queue solution.

This is an optimization in auxiliary data structure usage, not in asymptotic time complexity.

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
top()
```

Queue is shown from front to rear.

| Operation | Queue | Result |
|---|---|---|
| `push(10)` | `[10]` | — |
| `push(20)` | `[20, 10]` | — |
| `push(30)` | `[30, 20, 10]` | — |
| `pop()` | `[20, 10]` | `30` |
| `push(40)` | `[40, 20, 10]` | — |
| `pop()` | `[20, 10]` | `40` |
| `top()` | `[20, 10]` | `20` |

The newest element is always at the front.

---

## 7. Complexity Analysis

Let `n` be the number of elements in the stack.

| Operation | Two-queue solution | One-queue solution |
|---|---|---|
| `push()` | `O(n)` | `O(n)` |
| `pop()` | `O(1)` | `O(1)` |
| `top()` | `O(1)` | `O(1)` |
| `empty()` | `O(1)` | `O(1)` |
| Auxiliary space | `O(n)` | `O(n)` |

The two-queue version temporarily stores older elements in the second queue.

The one-queue version rotates the same queue instead.

Both solutions are efficient in terms of their asymptotic complexities.

---

## 8. Important Edge Cases

| Scenario | Expected behavior |
|---|---|
| Push one element | `top()` returns that element |
| Push several elements | Most recently pushed element is on top |
| Pop once | Removes the newest element |
| Call `top()` repeatedly | Returns the same element without removing it |
| Pop until empty | `empty()` returns `true` |
| Push after popping | New element becomes the top |

LeetCode guarantees that `pop()` and `top()` are called only when the stack is non-empty.

---

## 9. Common Mistakes

- Assuming a queue automatically reverses the order of elements.
- Transferring elements without rotating them.
- Rotating the wrong number of elements.
- Forgetting that `top()` must not remove an element.
- Confusing the front of a queue with the rear.
- Using two queues when a single queue is sufficient.

---

## 10. Interview Takeaways

- A queue can simulate a stack by rearranging elements.
- Queue rotation makes the newest element available at the front.
- Moving elements during `push()` allows `pop()` and `top()` to remain `O(1)`.
- A single queue is enough if we rotate the elements after each insertion.
- Be able to explain why `push()` is `O(n)` and the other operations are `O(1)`.

### Revision Checklist

- [ ] Explain the difference between FIFO and LIFO.
- [ ] Explain why simple queue insertion is insufficient.
- [ ] Implement the two-queue solution.
- [ ] Implement the one-queue optimized solution.
- [ ] Explain the rotation process.
- [ ] Analyze time and auxiliary space complexity.

**Core lesson:** To simulate a stack with a queue, rotate the queue after each insertion so the newest element always becomes the front element.
