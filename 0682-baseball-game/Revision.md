
# LeetCode 682 — Baseball Game

- **Difficulty:** Easy
- **Topic:** Stack, Array, Simulation
- **Pattern:** Stack-Based Operation Simulation
- **Problem Link:** https://leetcode.com/problems/baseball-game/

---

## 1. Problem Statement

You are keeping track of scores for a baseball game with unusual rules.

You are given a string array `operations`, where each element represents an operation:

- An integer `x`: Record a new score of `x`.
- `"+"`: Record a new score equal to the sum of the previous two valid scores.
- `"D"`: Record a new score equal to twice the previous valid score.
- `"C"`: Invalidate and remove the previous valid score.

Return the sum of all valid scores after processing every operation.

Each operation is guaranteed to be valid, so operations such as `"C"`, `"D"`, and `"+"` will have enough previous scores to work with.

### Example

**Input:**

```java
operations = {"5", "2", "C", "D", "+"};
```

**Output:**

```text
30
```

**Explanation:**

1. `"5"`: Add 5. Scores: `[5]`
2. `"2"`: Add 2. Scores: `[5, 2]`
3. `"C"`: Remove the last score, 2. Scores: `[5]`
4. `"D"`: Add double the previous score, 10. Scores: `[5, 10]`
5. `"+"`: Add the sum of the previous two scores, 15. Scores: `[5, 10, 15]`

Final sum:

```text
5 + 10 + 15 = 30
```

---

## 2. My Original Attempt

```java
class Solution {
    public int calPoints(String[] operations) {
        Deque<Integer> stack = new ArrayDeque<>();
        int sum = 0;

        for (String str : operations) {
            if (str == "C") {
                stack.pop();
            } else if (str == "D") {
                stack.push(stack.peek() * 2);
            } else if (str == "+") {
                String temp = stack.pop();
                String temp2 = stack.pop();

                stack.push(temp2);
                stack.push(temp);
                stack.push(temp + temp2);
            } else {
                stack.push(str);
            }
        }

        while (stack.isEmpty == false) {
            sum += stack.pop();
        }

        return sum;
    }
}
```

### Problems in the Original Attempt

#### Mistake 1: Comparing strings with `==`

```java
if (str == "C")
```

In Java, `==` compares object references, not string contents.

Use:

```java
str.equals("C")
```

Apply the same change to `"D"` and `"+"`.

#### Mistake 2: Using `String` instead of `int`

```java
String temp = stack.pop();
```

The stack is declared as `Deque<Integer>`, so `stack.pop()` returns an `Integer`.

Use:

```java
int temp = stack.pop();
```

Java automatically converts the `Integer` to an `int` through auto-unboxing.

#### Mistake 3: Pushing a string into an integer stack

```java
stack.push(str);
```

The stack accepts integers, but `str` is a string.

Convert numeric strings using:

```java
stack.push(Integer.parseInt(str));
```

#### Mistake 4: Incorrect `isEmpty` syntax

```java
while (stack.isEmpty == false)
```

`isEmpty()` is a method, so parentheses are required.

Correct:

```java
while (!stack.isEmpty())
```

---

## 3. Corrected Solution — My Approach

This is the final solution I developed.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int calPoints(String[] operations) {
        Deque<Integer> stack = new ArrayDeque<>();
        int sum = 0;

        for (String str : operations) {
            if (str.equals("C")) {
                stack.pop();

            } else if (str.equals("D")) {
                stack.push(stack.peek() * 2);

            } else if (str.equals("+")) {
                int temp = stack.pop();
                int temp2 = stack.pop();

                stack.push(temp2);
                stack.push(temp);
                stack.push(temp + temp2);

            } else {
                stack.push(Integer.parseInt(str));
            }
        }

        while (!stack.isEmpty()) {
            sum += stack.pop();
        }

        return sum;
    }
}
```

### How It Works

We use a stack to maintain the valid scores in the order they were recorded.

**Operation 1: Integer**

```java
stack.push(Integer.parseInt(str));
```

Convert the numeric string to an integer and push it onto the stack.

**Operation 2: `"C"`**

```java
stack.pop();
```

Remove the most recent valid score.

**Operation 3: `"D"`**

```java
stack.push(stack.peek() * 2);
```

Read the most recent score and push its double. `peek()` does not remove the original score.

**Operation 4: `"+"`**

```java
int temp = stack.pop();
int temp2 = stack.pop();

stack.push(temp2);
stack.push(temp);
stack.push(temp + temp2);
```

First, retrieve the two most recent scores.

- `temp` stores the most recent score.
- `temp2` stores the second-most-recent score.

Restore both scores, then push their sum as a new score.

Restoring the original scores is essential because `"+"` creates an additional score rather than replacing either previous score.

**Final step:**

```java
while (!stack.isEmpty()) {
    sum += stack.pop();
}
```

Add all remaining valid scores and return their total.

---

## 4. Optimized and Cleaner Solution

We can simplify the `"+"` operation by using `peek()` and temporarily storing the sum.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int calPoints(String[] operations) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String operation : operations) {
            switch (operation) {
                case "C":
                    stack.pop();
                    break;

                case "D":
                    stack.push(stack.peek() * 2);
                    break;

                case "+":
                    int last = stack.pop();
                    int previous = stack.peek();

                    stack.push(last);
                    stack.push(last + previous);
                    break;

                default:
                    stack.push(Integer.parseInt(operation));
            }
        }

        int sum = 0;

        for (int score : stack) {
            sum += score;
        }

        return sum;
    }
}
```

### Why Is This Version Cleaner?

For `"+"`, we only need to temporarily remove the most recent score.

After removing it:

- `last` stores the most recent score.
- `stack.peek()` gives the second-most-recent score.
- We restore `last`.
- We push the sum of the two scores.

This avoids removing and restoring both previous scores.

The `switch` statement also makes each operation easier to read.

### Complexity

Let `n` be the number of operations.

- **Time Complexity:** `O(n)` — each operation takes constant time, and the final sum visits each remaining score once.
- **Auxiliary Space Complexity:** `O(n)` — the stack can contain up to `n` scores.

Both solutions have optimal asymptotic complexity.

---

## 5. Dry Run

Input:

```java
operations = {"5", "2", "C", "D", "+"};
```

Stack is shown from bottom to top.

| Step | Operation | Action | Stack |
|---|---|---|---|
| 1 | `"5"` | Push 5 | `[5]` |
| 2 | `"2"` | Push 2 | `[5, 2]` |
| 3 | `"C"` | Remove 2 | `[5]` |
| 4 | `"D"` | Push 5 × 2 = 10 | `[5, 10]` |
| 5 | `"+"` | Push 10 + 5 = 15 | `[5, 10, 15]` |

Final sum:

```text
5 + 10 + 15 = 30
```

Output:

```text
30
```

---

## 6. Important Edge Cases

| Input | Expected Output | Explanation |
|---|---:|---|
| `{"5"}` | `5` | Only one score |
| `{"5", "-2"}` | `3` | Positive and negative scores |
| `{"5", "C"}` | `0` | Previous score is invalidated |
| `{"3", "D"}` | `9` | Scores are 3 and 6 |
| `{"3", "4", "+"}` | `14` | Scores are 3, 4, and 7 |
| `{"1", "2", "C", "D"}` | `5` | Remove 2, then double 1 |

The problem guarantees valid operations, so you do not need to add extra checks for insufficient previous scores.

---

## 7. Common Mistakes to Avoid

### Mistake 1: Using `==` for string comparisons

Use `.equals()` or a `switch` statement.

### Mistake 2: Removing previous scores permanently during `"+"`

The two previous scores remain valid. The operation adds a new score; it does not replace the old ones.

### Mistake 3: Confusing `peek()` and `pop()`

- `peek()` reads the top score without removing it.
- `pop()` removes and returns the top score.

### Mistake 4: Forgetting negative numbers

A numeric operation can contain a negative integer, such as `"-2"`. `Integer.parseInt()` handles this correctly.

### Mistake 5: Summing scores before all operations finish

A score may later be invalidated by `"C"`. Calculate the final sum only after processing all operations, unless you deliberately maintain a running total and update it for every operation.

---

## 8. Interview Takeaways

- Recognize that operations depend on previously recorded values.
- Use a stack to maintain the history of valid scores.
- Understand how `push()`, `pop()`, and `peek()` work.
- Restore previous scores after temporarily removing them for `"+"`.
- Use `Integer.parseInt()` to convert numeric strings into integers.
- Use `.equals()` for string comparison in Java.
- Explain why the algorithm runs in `O(n)` time and uses `O(n)` auxiliary space.

### Operation Summary

| Operation | Stack Action |
|---|---|
| Integer `x` | Push `x` |
| `"C"` | Pop the most recent score |
| `"D"` | Push twice the top score |
| `"+"` | Push the sum of the top two scores |

---

## 9. Final Revision Checklist

- [ ] I understand why a stack is suitable for this problem.
- [ ] I can explain all four operations.
- [ ] I know why `"+"` must preserve the two previous scores.
- [ ] I can convert numeric strings using `Integer.parseInt()`.
- [ ] I understand `peek()` versus `pop()`.
- [ ] I can dry-run the stack after every operation.
- [ ] I can explain the time and auxiliary space complexity.
- [ ] I can implement the solution without looking at my notes.

**Core lesson:** A stack is useful when the current operation depends on the most recent values. Baseball Game is a great example of simulating a set of rules using `push`, `pop`, and `peek`.
