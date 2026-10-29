
# LeetCode 739: Daily Temperatures

- **Difficulty:** Medium
- **Pattern:** Monotonic Stack
- **Language:** Java
- **Problem:** [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/)

---

## 1. Problem Statement

Given an array `temperatures`, return an array `answer` where `answer[i]` is the number of days you must wait after day `i` to get a **strictly warmer temperature**.

If there is no future day with a warmer temperature, `answer[i] = 0`.

### Example

**Input:**
```text
temperatures = [73,74,75,71,69,72,76,73]
```

**Output:**
```text
[1,1,4,2,1,1,0,0]
```

**Explanation:**

- Day 0: `73` → `74` after 1 day.
- Day 1: `74` → `75` after 1 day.
- Day 2: `75` → `76` after 4 days.
- Day 3: `71` → `72` after 2 days.
- Day 4: `69` → `72` after 1 day.
- Day 5: `72` → `76` after 1 day.
- Days 6 and 7 have no warmer future day, so their answers remain `0`.

---

## 2. Core Intuition

Imagine every day is waiting for a warmer day.

Some days get resolved immediately when a warmer temperature arrives. Other days must continue waiting.

A **stack of indices** stores the days whose warmer future temperature has not been found yet.

When a warmer day arrives:
1. Check the most recent unresolved day.
2. If today's temperature is strictly warmer, pop that day's index.
3. Calculate the waiting time: `currentDay - previousDay`.
4. Store the result in the answer array.
5. Repeat while today's temperature resolves more previous days.
6. Push today's index because it may need a warmer day later.

### Why store indices instead of temperatures?

We need both:
- The temperature: `temperatures[index]`
- The number of days waited: `currentIndex - previousIndex`

An index gives us access to both.

---

## 3. Monotonic Stack Rule

The stack stores **indices of unresolved days**.

From the bottom to the top, their temperatures are in **non-increasing order**. This means each temperature is less than or equal to the temperature beneath it.

For every current day `i`, use this condition:

```java
while (!stack.isEmpty()
        && temperatures[i] > temperatures[stack.peek()]) {
```

The condition means:
- `!stack.isEmpty()` ensures that `peek()` is safe.
- `temperatures[i] > temperatures[stack.peek()]` checks whether today is strictly warmer than the most recent unresolved day.

**Important:** Equal temperatures do not count as warmer. That's why we use `>` and not `>=`.

---

## 4. Final Java Solution

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] answer = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {

            while (!stack.isEmpty()
                    && temperatures[i] > temperatures[stack.peek()]) {

                int prevDay = stack.pop();
                answer[prevDay] = i - prevDay;
            }

            stack.push(i);
        }

        return answer;
    }
}
```

---

## 5. Dry Run

Use a smaller example:

```text
temperatures = [30, 40, 35, 45]
answer       = [ 0,  0,  0,  0]
```

The stack contains indices, not temperature values.

| Current day | Temperature | Action | Stack (bottom → top) | Answer |
|---:|---:|---|---|---|
| 0 | 30 | Push index 0 | `[0]` | `[0,0,0,0]` |
| 1 | 40 | Pop 0; answer[0] = 1. Push 1 | `[1]` | `[1,0,0,0]` |
| 2 | 35 | Push index 2 | `[1,2]` | `[1,0,0,0]` |
| 3 | 45 | Pop 2; answer[2] = 1. Pop 1; answer[1] = 2. Push 3 | `[3]` | `[1,2,1,0]` |

**Final answer:**

```text
[1, 2, 1, 0]
```

Notice that day 0 is resolved immediately by day 1. Once an index is popped, its answer is finalized and it is removed from the stack.

---

## 6. Why Does the Algorithm Work?

When today's temperature is warmer than the temperature at the top index, today is the **first warmer day** for that index.

Why the first?

- We process days from left to right.
- If an earlier day had already found a warmer day, it would have been popped.
- Any index remaining in the stack has not yet encountered a warmer day.
- Therefore, the current day is the first warmer day for every index it pops.

The stack only needs to examine unresolved days. Each day is pushed once and popped at most once.

---

## 7. Common Mistakes

### Mistake 1: Popping every index without checking temperature

Incorrect:

```java
while (!stack.isEmpty()) {
    stack.pop();
}
```

This incorrectly resolves colder, equal, and warmer days alike.

Correct:

```java
while (!stack.isEmpty()
        && temperatures[i] > temperatures[stack.peek()]) {
    // Resolve only days with colder temperatures
}
```

### Mistake 2: Using `>=` instead of `>`

Incorrect:

```java
temperatures[i] >= temperatures[stack.peek()]
```

Equal temperatures do not satisfy the problem's requirement of a strictly warmer day.

### Mistake 3: Forgetting to push today's index

After resolving previous days, always execute:

```java
stack.push(i);
```

Today's index might be resolved by a warmer day in the future.

### Mistake 4: Storing temperatures instead of indices

If you store only temperatures, calculating the exact number of days between two temperatures becomes difficult because you lose their positions.

### Mistake 5: Using unnecessary `if-else` branches

You do not need separate cases for an empty stack, colder temperatures, or equal temperatures. A single `while` loop followed by `stack.push(i)` handles all of them.

---

## 8. Complexity Analysis

Let `n` be the length of the temperature array.

- **Time Complexity:** `O(n)`
- **Auxiliary Space Complexity:** `O(n)`
- **Output Array Space:** `O(n)`

Why is the time complexity `O(n)` even though there is a `while` loop inside a `for` loop?

Every index is pushed exactly once and popped at most once. Across the entire algorithm, there can be at most `n` pushes and `n` pops.

Therefore, the total stack operations are linear, giving **O(n)** time complexity.

---

## 9. Interview Explanation

"I use a monotonic stack to keep indices of days that haven't found a warmer future temperature yet. I traverse the array from left to right. Whenever the current temperature is strictly greater than the temperature at the top index, I pop that index and calculate the waiting time using the difference between the current and previous indices. I continue resolving indices while the current temperature is warmer, then push the current index. Since every index is pushed once and popped at most once, the algorithm runs in O(n) time with O(n) auxiliary space."

---

## 10. Revision Questions

Try answering these without looking at the solution.

1. Why does the stack store indices instead of temperatures?
2. Why must the condition use `>` instead of `>=`?
3. Why do we use a `while` loop rather than a single `if`?
4. Why is `answer[prevDay] = i - prevDay` the correct calculation?
5. Why do unresolved days automatically have answer `0`?
6. Why is the total time complexity `O(n)` despite the nested loop?
7. What does the stack represent at the end of the algorithm?

### Quick Practice

Predict the output for each input before running the code.

**A.**
```text
temperatures = [30, 40, 50]
```

**B.**
```text
temperatures = [50, 40, 30]
```

**C.**
```text
temperatures = [30, 30, 40]
```

**D.**
```text
temperatures = [70, 70, 71, 69, 72]
```

---

## 11. Key Takeaways

- Daily Temperatures is a classic **Next Greater Element** problem.
- Use a monotonic stack when each element needs to find the next greater element to its right.
- Store indices when the answer depends on positions or distances.
- Pop an index only when the current value satisfies the problem's required comparison.
- Push every current index after resolving previous indices.
- Each index is pushed once and popped at most once, making the algorithm `O(n)`.

**Pattern to remember:**

> Current element resolves previous unresolved elements while it satisfies the required comparison; then push the current index.
