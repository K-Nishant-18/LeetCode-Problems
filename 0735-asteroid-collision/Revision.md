
# LeetCode 735 — Asteroid Collision

## 1. Problem Statement

We are given an integer array `asteroids`.

- The absolute value represents the asteroid's size.
- A positive value means the asteroid moves to the right.
- A negative value means the asteroid moves to the left.
- All asteroids move at the same speed.

When two asteroids collide:
- The smaller asteroid explodes.
- If both have the same size, both explode.
- Asteroids moving in the same direction never collide.

Return the state of the asteroids after all collisions.

## 2. Examples

### Example 1

**Input:**
```java
asteroids = [5, 10, -5]
```

**Output:**
```java
[5, 10]
```

Explanation:
- `10` and `-5` collide.
- `-5` explodes because `10` is larger.
- `5` and `10` survive.

### Example 2

**Input:**
```java
asteroids = [8, -8]
```

**Output:**
```java
[]
```

Explanation:
Both asteroids have equal size, so both explode.

### Example 3

**Input:**
```java
asteroids = [10, 2, -5]
```

**Output:**
```java
[10]
```

Explanation:
- `2` collides with `-5` and explodes.
- `-5` then collides with `10` and explodes.
- `10` survives.

---

## 3. Pattern Used

**Stack — Collision Simulation**

The stack stores asteroids that have survived so far.

Why a stack?

A newly incoming asteroid can only collide with the most recently surviving asteroid on its left. If that asteroid explodes, the incoming asteroid may collide with the next surviving asteroid.

This naturally creates a Last In, First Out (LIFO) process.

> Important: This is a stack-based simulation, not a strictly monotonic stack. The values are not maintained in sorted or monotonic order.

---

## 4. Core Intuition

Not every pair of asteroids can collide.

A collision is possible only when:

```java
stack.peek() > 0 && current < 0
```

Why?

- The asteroid on the left must move right.
- The incoming asteroid on the right must move left.
- They are moving toward each other.

Other combinations:

| Stack top | Current asteroid | Collision? |
|---|---|---|
| Positive | Positive | No |
| Negative | Negative | No |
| Negative | Positive | No |
| Positive | Negative | Yes |

### Three collision outcomes

Let:
- `top` = asteroid at the top of the stack.
- `current` = incoming asteroid.

Compare their sizes using `top + current`.

| Condition | Result |
|---|---|
| `top + current < 0` | Top asteroid explodes; current survives |
| `top + current == 0` | Both explode |
| `top + current > 0` | Current asteroid explodes |

Why does addition work?

During a possible collision, `top` is positive and `current` is negative. Their sum compares their magnitudes without needing `Math.abs()`.

---

## 5. Final Java Solution

```java
import java.util.*;

class Solution {
    public int[] asteroidCollision(int[] asteroids) {

        Deque<Integer> stack = new ArrayDeque<>();

        for (int current : asteroids) {

            boolean alive = true;

            while (alive &&
                   !stack.isEmpty() &&
                   stack.peek() > 0 &&
                   current < 0) {

                int top = stack.peek();

                if (top + current < 0) {
                    // Top asteroid is smaller.
                    // Remove it and check for another collision.
                    stack.pop();

                } else if (top + current == 0) {
                    // Both asteroids have equal size.
                    stack.pop();
                    alive = false;

                } else {
                    // Incoming asteroid is smaller.
                    alive = false;
                }
            }

            // Push only if the incoming asteroid survives.
            if (alive) {
                stack.push(current);
            }
        }

        // Convert stack to array while preserving original order.
        int[] result = new int[stack.size()];

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }
}
```

---

## 6. Step-by-Step Dry Run

Input:

```java
asteroids = [10, 2, -5]
```

Initially:

```text
Stack = []
```

### Step 1: current = 10

No collision is possible because the stack is empty.

Push `10`.

```text
Stack: [10]
```

### Step 2: current = 2

Stack top is positive and current is positive.

They move in the same direction. No collision.

Push `2`.

```text
Stack: [10, 2]
```

### Step 3: current = -5

Stack top = `2`

Current = `-5`

Both move toward each other.

Calculate:

```text
2 + (-5) = -3
```

Since the result is negative, the stack's top asteroid is smaller.

Pop `2`.

```text
Stack: [10]
```

The incoming asteroid `-5` is still alive.

Now check again.

Stack top = `10`

Current = `-5`

Calculate:

```text
10 + (-5) = 5
```

Since the result is positive, the incoming asteroid is smaller.

It explodes.

```text
Stack: [10]
```

Final output:

```java
[10]
```

### Dry-run table

| Current | Stack before collision | Action | Stack after |
|---|---|---|---|
| `10` | `[]` | Push 10 | `[10]` |
| `2` | `[10]` | Push 2 | `[10, 2]` |
| `-5` | `[10, 2]` | Pop 2 | `[10]` |
| `-5` | `[10]` | -5 explodes | `[10]` |

---

## 7. Why Do We Need a While Loop?

This is the most important part of the problem.

Consider:

```java
asteroids = [10, 2, -10]
```

If we use only an `if` statement, we can process just one collision.

But the incoming `-10` can collide with multiple asteroids:

1. `-10` collides with `2`.
2. `2` explodes.
3. `-10` now collides with `10`.
4. Both explode.

Final result:

```java
[]
```

The `while` loop handles this chain reaction.

```java
while (alive &&
       !stack.isEmpty() &&
       stack.peek() > 0 &&
       current < 0) {
    // Resolve collision
}
```

The loop continues until:
- The incoming asteroid explodes.
- The stack becomes empty.
- No collision is possible.

---

## 8. Why Do We Need the `alive` Variable?

The `alive` variable tracks whether the incoming asteroid survives.

```java
boolean alive = true;
```

When the incoming asteroid loses:

```java
alive = false;
```

When both asteroids explode:

```java
stack.pop();
alive = false;
```

After collision processing:

```java
if (alive) {
    stack.push(current);
}
```

This prevents an exploded asteroid from being pushed into the stack.

---

## 9. Complexity Analysis

Let `n` be the number of asteroids.

### Time Complexity: O(n)

- Each asteroid is pushed onto the stack at most once.
- Each asteroid is popped from the stack at most once.
- Although there is a nested `while` loop, the total number of pops across the entire algorithm cannot exceed `n`.

Therefore, total time complexity is:

```text
O(n)
```

### Space Complexity: O(n)

In the worst case, all asteroids survive.

The stack can store all `n` asteroids.

```text
O(n)
```

The returned array also requires O(n) space.

---

## 10. Common Mistakes

### Mistake 1: Using only `if` instead of `while`

One collision may expose another asteroid that can collide with the incoming asteroid.

**Fix:** Use a `while` loop to handle chain reactions.

### Mistake 2: Checking every pair of asteroids

Not every pair can collide. Only the stack's top and the incoming asteroid need to be checked.

**Fix:** Use the stack to track surviving asteroids.

### Mistake 3: Pushing an exploded asteroid

An incoming asteroid may lose a collision.

**Fix:** Track its survival using `alive`.

### Mistake 4: Incorrect collision direction

Two asteroids collide only when the left asteroid moves right and the right asteroid moves left.

**Fix:**

```java
stack.peek() > 0 && current < 0
```

### Mistake 5: Reversing the result

Stack order is opposite to the original left-to-right asteroid order.

**Fix:** Fill the result array from right to left.

---

## 11. Interview Explanation

"I use a stack to simulate asteroid collisions. I process each asteroid from left to right.

For every incoming asteroid, I check whether it can collide with the asteroid at the top of the stack. A collision is possible only when the stack top is positive and the incoming asteroid is negative.

I compare their sizes using their sum:
- If the sum is negative, the stack's asteroid explodes, and I continue checking.
- If the sum is zero, both explode.
- If the sum is positive, the incoming asteroid explodes.

I use a while loop because one incoming asteroid can trigger multiple consecutive collisions. If the incoming asteroid survives all possible collisions, I push it onto the stack.

Finally, I convert the stack into an array in the correct order.

Each asteroid is pushed and popped at most once, giving O(n) time and O(n) space complexity."

---

## 12. Related Problems

| Problem | Pattern |
|---|---|
| 20. Valid Parentheses | Stack |
| 496. Next Greater Element I | Monotonic Stack |
| 503. Next Greater Element II | Monotonic Stack |
| 739. Daily Temperatures | Monotonic Stack |
| 901. Online Stock Span | Monotonic Stack |
| 150. Evaluate Reverse Polish Notation | Stack |

---

## 13. Quick Revision

Before an interview, remember these five points:

1. Use a stack to store surviving asteroids.
2. Collision happens only when `stack.peek() > 0 && current < 0`.
3. Use `while`, not `if`, to handle chain collisions.
4. Use `alive` to decide whether the incoming asteroid survives.
5. Every asteroid is pushed and popped at most once.

**Core condition to remember:**

```java
while (alive && !stack.isEmpty()
       && stack.peek() > 0 && current < 0)
```

**Pattern:** Stack-based collision simulation  
**Time:** O(n)  
**Space:** O(n)
