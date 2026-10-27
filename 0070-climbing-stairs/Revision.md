
# LeetCode 70: Climbing Stairs

- **Problem Number:** 70
- **Difficulty:** Easy
- **Topic:** Recursion, Dynamic Programming, Memoization
- **LeetCode:** https://leetcode.com/problems/climbing-stairs/
- **Status:** Solved using Recursion + Memoization

---

## 1. Problem Statement

You are climbing a staircase with `n` steps.

Each time, you can climb either **1 step or 2 steps**.

Return the number of distinct ways to reach the top.

### Example 1

**Input:**
```text
n = 2
```

**Output:**
```text
2
```

**Explanation:**

There are two distinct ways to reach the top:

1. `1 + 1`
2. `2`

### Example 2

**Input:**
```text
n = 3
```

**Output:**
```text
3
```

**Explanation:**

There are three distinct ways:

1. `1 + 1 + 1`
2. `1 + 2`
3. `2 + 1`

---

## 2. Core Intuition

To reach stair `n`, the last move must be one of two possibilities:

- Climb 1 step from stair `n - 1`.
- Climb 2 steps from stair `n - 2`.

Therefore:

```text
ways(n) = ways(n - 1) + ways(n - 2)
```

We add the two counts because these represent different final moves and therefore distinct routes.

### Base Cases

```java
if (n == 0) return 1;
if (n == 1) return 1;
```

- `n == 0`: There is one way to complete the journey when no steps remain: do nothing.
- `n == 1`: There is only one way to reach the first stair: take one step.

**Important:** Returning `1` for `n == 0` makes the recurrence work correctly.

---

## 3. Approach 1: Basic Recursion

### Code

```java
class Solution {
    public int climbStairs(int n) {
        if (n == 0) return 1;
        if (n == 1) return 1;

        return climbStairs(n - 1) + climbStairs(n - 2);
    }
}
```

### How It Works

The method recursively calculates the number of ways to reach the previous two stair positions and adds their results.

### Dry Run: `n = 4`

```text
climbStairs(4)
= climbStairs(3) + climbStairs(2)
= 3 + 2
= 5
```

The five possible routes are:

```text
1 + 1 + 1 + 1
1 + 1 + 2
1 + 2 + 1
2 + 1 + 1
2 + 2
```

### Complexity

- **Time:** O(2^n), an upper bound for the recursive approach.
- **Auxiliary Space:** O(n) due to the recursion stack.

### Limitation

The same subproblems are calculated repeatedly. For example, `climbStairs(2)` may be evaluated multiple times.

This repeated work can cause **Time Limit Exceeded (TLE)** for larger inputs.

---

## 4. Approach 2: Recursion + Memoization

### Idea

Memoization means storing the result of a subproblem so that we can reuse it instead of recalculating it.

We use a `HashMap<Integer, Integer>`:

- **Key:** Number of stairs.
- **Value:** Number of distinct ways to climb those stairs.

### My Submitted Solution

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    Map<Integer, Integer> map = new HashMap<>();

    public int climbStairs(int n) {
        if (map.containsKey(n)) {
            return map.get(n);
        }

        if (n == 0) return 1;
        if (n == 1) return 1;

        int k = climbStairs(n - 1) + climbStairs(n - 2);
        map.put(n, k);

        return k;
    }
}
```

### How It Works

**Step 1: Check the cache**

```java
if (map.containsKey(n)) {
    return map.get(n);
}
```

If the answer for `n` already exists, return it immediately.

**Step 2: Handle base cases**

```java
if (n == 0) return 1;
if (n == 1) return 1;
```

These cases stop the recursion.

**Step 3: Calculate the answer**

```java
int k = climbStairs(n - 1) + climbStairs(n - 2);
```

Use the recurrence to calculate the number of ways.

**Step 4: Store the result**

```java
map.put(n, k);
```

Save the result for future calls.

**Step 5: Return the result**

```java
return k;
```

### Dry Run: `n = 4`

The calculations proceed as follows:

| Call | Calculation | Result |
|---|---|---:|
| `climbStairs(0)` | Base case | 1 |
| `climbStairs(1)` | Base case | 1 |
| `climbStairs(2)` | `1 + 1` | 2 |
| `climbStairs(3)` | `2 + 1` | 3 |
| `climbStairs(4)` | `3 + 2` | 5 |

After calculating the answers, the map contains:

```text
{
    2 = 2,
    3 = 3,
    4 = 5
}
```

The base cases are returned directly and are not stored in the map.

If a subproblem is requested again, the cached answer is returned without further recursive calculation.

### Complexity

- **Time:** O(n), because each non-base subproblem is calculated once.
- **Auxiliary Space:** O(n) for the map and O(n) for the recursion stack. Together, these remain O(n).

### Small Improvement

The instance-level map persists between calls to the same `Solution` object. A helper method with a cache created inside `climbStairs` can keep the cache specific to each invocation.

---

## 5. Approach 3: Bottom-Up Dynamic Programming

### Idea

Instead of recursively solving smaller problems, calculate the answers from the smallest cases upward.

We already know:

```text
ways(0) = 1
ways(1) = 1
```

Then:

```text
ways(2) = ways(1) + ways(0) = 2
ways(3) = ways(2) + ways(1) = 3
ways(4) = ways(3) + ways(2) = 5
```

### Code

```java
class Solution {
    public int climbStairs(int n) {
        if (n <= 1) return 1;

        int prev2 = 1;
        int prev1 = 1;

        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}
```

### How It Works

- `prev2` represents the answer for the stair two positions behind the current position.
- `prev1` represents the answer for the previous position.
- `current` calculates the answer for the current position.
- Update the two variables and continue.

Only two previous answers are needed, so an array or map is unnecessary.

### Dry Run: `n = 5`

Initial values:

```text
prev2 = 1
prev1 = 1
```

| `i` | `current = prev1 + prev2` | Updated `prev2` | Updated `prev1` |
|---:|---:|---:|---:|
| 2 | 2 | 1 | 2 |
| 3 | 3 | 2 | 3 |
| 4 | 5 | 3 | 5 |
| 5 | 8 | 5 | 8 |

**Answer:** `8`

### Complexity

- **Time:** O(n)
- **Auxiliary Space:** O(1)

This is the most space-efficient of the three approaches.

---

## 6. Approach Comparison

| Approach | Time Complexity | Auxiliary Space | Main Idea |
|---|---|---|---|
| Basic recursion | O(2^n) | O(n) | Repeated recursive calculations |
| Memoization | O(n) | O(n) | Cache previously calculated answers |
| Bottom-up DP | O(n) | O(1) | Build answers using two variables |

---

## 7. Mistakes and Lessons Learned

### Mistake 1: Ignoring repeated calculations

Basic recursion solves the same subproblems multiple times.

**Lesson:** When a recursive solution recalculates the same inputs, consider memoization.

### Mistake 2: Confusing the final move with the total number of routes

There are two possible final moves, but each move can be preceded by multiple different routes.

**Lesson:** Add the number of routes from the two distinct preceding positions:

```java
ways(n - 1) + ways(n - 2)
```

### Lesson 3: Understand the base case `n == 0`

Returning `1` represents one completed route with no remaining moves. It allows the recurrence to count routes correctly.

### Lesson 4: Cache only after calculating the answer

```java
int k = climbStairs(n - 1) + climbStairs(n - 2);
map.put(n, k);
```

The result must be calculated before it can be stored.

### Lesson 5: Choose the right optimization

- Use recursion to understand the recurrence.
- Use memoization to eliminate repeated work while retaining recursion.
- Use bottom-up DP to eliminate recursion and reduce extra space.

---

## 8. Key Takeaways

- Climbing Stairs is a classic introduction to dynamic programming.
- The recurrence is `ways(n) = ways(n - 1) + ways(n - 2)`.
- Memoization is a top-down dynamic programming technique.
- Tabulation is a bottom-up dynamic programming technique.
- The recurrence resembles the Fibonacci sequence.
- You can reduce O(n) auxiliary space to O(1) by tracking only the previous two values.

---

## 9. Quick Revision Questions

1. Why do we add `ways(n - 1)` and `ways(n - 2)`?
2. Why does the base case `n == 0` return `1`?
3. Why does basic recursion cause TLE?
4. How does memoization prevent repeated calculations?
5. What is the difference between memoization and tabulation?
6. Why can the bottom-up solution use O(1) extra space?
7. What are the time and space complexities of each approach?

### Final Summary

**Recurrence:**

```text
ways(n) = ways(n - 1) + ways(n - 2)
```

**Best space-optimized approach:** Bottom-up DP

**Time Complexity:** O(n)

**Auxiliary Space:** O(1)
