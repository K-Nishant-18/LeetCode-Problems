
# LeetCode 509 — Fibonacci Number

- **Difficulty:** Easy
- **Topic:** Recursion, Dynamic Programming
- **Primary Pattern:** Base Case + Recursive Case
- **Problem Link:** https://leetcode.com/problems/fibonacci-number/

---

## 1. Problem Statement

The Fibonacci sequence is defined as:

- `F(0) = 0`
- `F(1) = 1`
- `F(n) = F(n - 1) + F(n - 2)` for `n >= 2`

Given an integer `n`, return `F(n)`.

### Examples

**Example 1**

Input:
```text
n = 2
```

Output:
```text
1
```

Explanation:
`F(2) = F(1) + F(0) = 1 + 0 = 1`

**Example 2**

Input:
```text
n = 3
```

Output:
```text
2
```

Explanation:
`F(3) = F(2) + F(1) = 1 + 1 = 2`

**Example 3**

Input:
```text
n = 4
```

Output:
```text
3
```

Explanation:
`F(4) = F(3) + F(2) = 2 + 1 = 3`

---

## 2. My Original Solution

```java
class Solution {
    public int fib(int n) {
        int sum = 0;

        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        sum = sum + fib(n - 1) + fib(n - 2);
        return sum;
    }
}
```

### Explanation

1. If `n == 0`, return `0`.
2. If `n == 1`, return `1`.
3. Otherwise, calculate the sum of the previous two Fibonacci numbers.
4. Return the result.

This solution correctly applies the mathematical definition of Fibonacci.

### Small improvement

The `sum` variable is unnecessary because the recursive calls already return the values we need.

---

## 3. Cleaner Recursive Solution

```java
class Solution {
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        return fib(n - 1) + fib(n - 2);
    }
}
```

### Why is this cleaner?

Instead of creating a variable and updating it, directly return the sum of the two recursive calls.

This is the same algorithm as my original solution, just written more clearly.

### Complexity Analysis

- **Time:** O(2^n), commonly used as a simple upper bound.
- **Auxiliary Space:** O(n), because the maximum recursive call depth is proportional to `n`.

The main inefficiency is that the same Fibonacci values are calculated repeatedly.

---

## 4. Dry Run — `fib(4)`

The recursive relationship is:

`fib(4) = fib(3) + fib(2)`

Expand the calls:

```text
fib(4)
├── fib(3)
│   ├── fib(2)
│   │   ├── fib(1) = 1
│   │   └── fib(0) = 0
│   │   Result = 1
│   └── fib(1) = 1
│   Result = 2
└── fib(2)
    ├── fib(1) = 1
    └── fib(0) = 0
    Result = 1

fib(4) = 2 + 1 = 3
```

Final answer:

```text
3
```

### Important observation

`fib(2)` is calculated twice.

As `n` increases, the number of repeated calculations grows quickly. This is why the simple recursive approach is inefficient for larger inputs.

---

## 5. Optimized Solution — Recursion with Memoization

Memoization means **saving previously calculated results** so we don't calculate the same subproblem repeatedly.

```java
class Solution {
    public int fib(int n) {
        int[] memo = new int[n + 1];
        return calculateFib(n, memo);
    }

    private int calculateFib(int n, int[] memo) {
        if (n <= 1) {
            return n;
        }

        if (memo[n] != 0) {
            return memo[n];
        }

        memo[n] = calculateFib(n - 1, memo)
                + calculateFib(n - 2, memo);

        return memo[n];
    }
}
```

### How it works

1. Create an array to store previously calculated Fibonacci numbers.
2. Return `n` immediately when `n` is `0` or `1`.
3. If `memo[n]` already contains a calculated result, reuse it.
4. Otherwise, calculate the result recursively and store it.
5. Return the stored result.

**Why does `memo[n] != 0` work here?**

For this problem, the only Fibonacci value equal to zero is `F(0)`. The recursive helper handles `n <= 1` before checking the memo array, so zero can safely represent an uncomputed entry for the remaining indices.

### Complexity Analysis

- **Time:** O(n), because each non-base Fibonacci value is calculated once.
- **Auxiliary Space:** O(n) for the memo array and recursive call stack.

This is much faster than repeatedly calculating the same subproblems.

---

## 6. Alternative Optimization — Iterative Approach

We can calculate Fibonacci without recursion by keeping track of the previous two values.

```java
class Solution {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }

        int previous = 0;
        int current = 1;

        for (int i = 2; i <= n; i++) {
            int next = previous + current;
            previous = current;
            current = next;
        }

        return current;
    }
}
```

### Complexity Analysis

- **Time:** O(n)
- **Auxiliary Space:** O(1)

This approach avoids both repeated calculations and the recursive call stack.

---

## 7. Comparison of Approaches

| Approach | Time | Auxiliary Space |
|---|---|---|
| Original recursion | O(2^n) upper bound | O(n) |
| Cleaner recursion | O(2^n) upper bound | O(n) |
| Memoization | O(n) | O(n) |
| Iterative | O(n) | O(1) |

---

## 8. Common Mistakes

### Mistake 1: Missing a base case

Without a base case, recursive calls may continue until a `StackOverflowError` occurs.

Always identify when the problem becomes small enough to answer directly.

### Mistake 2: Using only one recursive call

Incorrect:

```java
return fib(n - 1);
```

Correct:

```java
return fib(n - 1) + fib(n - 2);
```

The Fibonacci definition requires two previous values.

### Mistake 3: Forgetting the recurrence

Write down the mathematical relationship before coding:

`F(n) = F(n - 1) + F(n - 2)`

Then translate it into Java.

### Mistake 4: Ignoring repeated calculations

A recursive solution can be logically correct but inefficient. After getting the basic solution working, look for repeated subproblems.

---

## 9. Recursion Checklist

Whenever I solve a recursion problem, I should ask:

1. What is the base case?
2. What smaller problem should I solve?
3. How do the smaller answers combine?
4. Does every recursive call move toward the base case?
5. Am I calculating the same subproblem repeatedly?
6. Can memoization reduce the repeated work?

---

## 10. Interview Takeaways

- Recursion solves a problem by calling the same method on smaller inputs.
- A base case prevents infinite recursive calls.
- Fibonacci is a classic example of a recurrence involving two recursive calls.
- Simple recursion can repeat work exponentially.
- Memoization stores results to avoid repeated calculations.
- Iteration can often achieve O(n) time with O(1) auxiliary space.

---

## Final Summary

**The most important lesson:** First make the recursive relationship correct. Then analyze whether the same subproblems are being solved repeatedly.

My progression:

1. Started with an incomplete recursive solution.
2. Added the correct base cases for `n == 0` and `n == 1`.
3. Implemented `fib(n - 1) + fib(n - 2)`.
4. Learned how the recursive calls expand and return.
5. Explored memoization and iteration as optimizations.

**Pattern to remember:**

`Base Case → Smaller Recursive Calls → Combine Results → Return`
