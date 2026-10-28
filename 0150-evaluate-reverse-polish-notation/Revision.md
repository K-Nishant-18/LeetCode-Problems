
# LeetCode 150: Evaluate Reverse Polish Notation

- **Difficulty:** Medium
- **Topics:** Stack, Array, Math
- **Language:** Java
- **LeetCode:** https://leetcode.com/problems/evaluate-reverse-polish-notation/
- **Approach:** Stack using `ArrayDeque`
- **Status:** Solved

---

## 1. Problem Statement

You are given an array of strings `tokens` representing an arithmetic expression in Reverse Polish Notation (RPN).

Evaluate the expression and return its integer result.

Valid operators are:

- `+` Addition
- `-` Subtraction
- `*` Multiplication
- `/` Division

Division between integers truncates toward zero.

Each expression is valid, and division by zero will not occur.

### Example 1

**Input:**
```text
tokens = ["2", "1", "+", "3", "*"]
```

**Output:**
```text
9
```

**Explanation:**

The expression is:

```text
(2 + 1) * 3 = 9
```

### Example 2

**Input:**
```text
tokens = ["4", "13", "5", "/", "+"]
```

**Output:**
```text
6
```

**Explanation:**

```text
4 + (13 / 5)
= 4 + 2
= 6
```

Integer division truncates `13 / 5` to `2`.

### Example 3

**Input:**
```text
tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
```

**Output:**
```text
22
```

---

## 2. What Is Reverse Polish Notation?

In normal mathematical notation, operators appear between operands.

This is called infix notation.

```text
2 + 3
```

In Reverse Polish Notation, the operator appears after its operands.

```text
2 3 +
```

Both expressions mean:

```text
2 + 3 = 5
```

### Another example

Normal notation:

```text
(2 + 3) * 4
```

Reverse Polish Notation:

```text
2 3 + 4 *
```

The advantage of RPN is that we can evaluate the expression using a stack without needing parentheses or operator-precedence rules.

---

## 3. Core Intuition: Why a Stack?

A stack follows **LIFO: Last In, First Out**.

When reading an RPN expression:

1. If the token is a number, push it onto the stack.
2. If the token is an operator, pop the top two numbers.
3. Apply the operator in the correct order.
4. Push the result back onto the stack.
5. Continue until every token has been processed.

At the end, the stack contains the final result.

### Example

```text
Tokens: ["2", "1", "+"]

Read 2  -> Push 2
Read 1  -> Push 1
Read +  -> Pop 1 and 2
           Calculate 2 + 1 = 3
           Push 3

Final stack: [3]
Answer: 3
```

---

## 4. My Final Java Solution

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String str : tokens) {
            if (str.equals("+") ||
                str.equals("-") ||
                str.equals("*") ||
                str.equals("/")) {

                int temp1 = stack.pop();
                int temp2 = stack.pop();

                if (str.equals("+")) {
                    stack.push(temp1 + temp2);
                } else if (str.equals("-")) {
                    stack.push(temp2 - temp1);
                } else if (str.equals("*")) {
                    stack.push(temp1 * temp2);
                } else {
                    stack.push(temp2 / temp1);
                }
            } else {
                stack.push(Integer.parseInt(str));
            }
        }

        return stack.pop();
    }
}
```

### How the Code Works

**Step 1: Create the stack**

```java
Deque<Integer> stack = new ArrayDeque<>();
```

The stack stores numbers and intermediate results.

`ArrayDeque` supports efficient push and pop operations.

**Step 2: Process every token**

```java
for (String str : tokens)
```

We evaluate the expression from left to right.

**Step 3: Identify operators**

```java
if (str.equals("+") ||
    str.equals("-") ||
    str.equals("*") ||
    str.equals("/"))
```

We use `.equals()` to compare string contents.

**Important Java lesson:** Do not use `==` to compare string contents. The `==` operator compares object references, whereas `.equals()` compares the string values.

**Step 4: Pop two operands**

```java
int temp1 = stack.pop();
int temp2 = stack.pop();
```

The first popped value is the right operand.

The second popped value is the left operand.

**Step 5: Perform the operation**

```java
if (str.equals("+")) {
    stack.push(temp1 + temp2);
} else if (str.equals("-")) {
    stack.push(temp2 - temp1);
} else if (str.equals("*")) {
    stack.push(temp1 * temp2);
} else {
    stack.push(temp2 / temp1);
}
```

Addition and multiplication work in either order, but subtraction and division do not.

**Step 6: Push numbers**

```java
stack.push(Integer.parseInt(str));
```

If the token is not an operator, parse it as an integer and push it onto the stack.

This also handles negative numbers such as `"-11"`.

**Step 7: Return the final result**

```java
return stack.pop();
```

A valid RPN expression leaves exactly one result on the stack.

---

## 5. Critical Concept: Operand Order

This is the most important detail in this problem.

Suppose the stack contains:

```text
Bottom -> [10, 3] <- Top
```

When we pop:

```java
int temp1 = stack.pop(); // 3
int temp2 = stack.pop(); // 10
```

Therefore:

| Operator | Correct calculation | Result |
|---|---|---:|
| `+` | `10 + 3` | 13 |
| `-` | `10 - 3` | 7 |
| `*` | `10 * 3` | 30 |
| `/` | `10 / 3` | 3 |

Integer division truncates toward zero.

### Memory Trick

```text
First pop  = RIGHT operand
Second pop = LEFT operand
```

Or remember:

```java
int right = stack.pop();
int left = stack.pop();

int result = left - right;
```

For subtraction and division, always calculate:

```text
left operator right
```

Never reverse the operands.

---

## 6. Dry Run: Example 1

**Input:**

```text
tokens = ["2", "1", "+", "3", "*"]
```

The stack is shown from bottom to top.

| Token | Action | Stack after processing |
|---|---|---|
| `"2"` | Push 2 | `[2]` |
| `"1"` | Push 1 | `[2, 1]` |
| `"+"` | `2 + 1 = 3` | `[3]` |
| `"3"` | Push 3 | `[3, 3]` |
| `"*"` | `3 * 3 = 9` | `[9]` |

Final result:

```text
9
```

---

## 7. Dry Run: Example 2

**Input:**

```text
tokens = ["4", "13", "5", "/", "+"]
```

| Token | Action | Stack after processing |
|---|---|---|
| `"4"` | Push 4 | `[4]` |
| `"13"` | Push 13 | `[4, 13]` |
| `"5"` | Push 5 | `[4, 13, 5]` |
| `"/"` | `13 / 5 = 2` | `[4, 2]` |
| `"+"` | `4 + 2 = 6` | `[6]` |

Final result:

```text
6
```

Notice that `13 / 5` produces `2`, not `2.6`, because the operands are integers.

---

## 8. Dry Run: Negative Division

Consider:

```text
tokens = ["7", "-3", "/"]
```

The stack becomes:

```text
Push 7  -> [7]
Push -3 -> [7, -3]
```

At `/`:

```java
int temp1 = stack.pop(); // -3
int temp2 = stack.pop(); // 7
```

Calculation:

```text
7 / -3 = -2
```

The result is `-2` because Java integer division truncates toward zero, rather than rounding down to negative infinity.

For example:

```text
 7 /  3 =  2
-7 /  3 = -2
 7 / -3 = -2
-7 / -3 =  2
```

---

## 9. Common Mistakes and Lessons Learned

### Mistake 1: Comparing strings with `==`

Incorrect:

```java
if (str == "+")
```

Correct:

```java
if (str.equals("+"))
```

**Lesson:** Use `.equals()` to compare Java string contents.

### Mistake 2: Reversing subtraction operands

Incorrect:

```java
stack.push(temp1 - temp2);
```

Correct:

```java
stack.push(temp2 - temp1);
```

The first popped value is the right operand.

### Mistake 3: Reversing division operands

Incorrect:

```java
stack.push(temp1 / temp2);
```

Correct:

```java
stack.push(temp2 / temp1);
```

Operand order matters because integer division is not commutative.

### Mistake 4: Treating negative numbers as operators

The token `"-11"` is a number, not the subtraction operator.

This condition handles the distinction correctly:

```java
str.equals("-")
```

The entire token must equal `"-"` to be treated as an operator.

Otherwise, `Integer.parseInt(str)` parses the negative integer.

### Mistake 5: Using a queue instead of a stack

A queue removes the oldest element first, while RPN evaluation requires the most recently pushed operands.

**Lesson:** Choose the data structure based on the order in which values must be accessed.

---

## 10. Complexity Analysis

Let `n` be the number of tokens.

### Time Complexity: O(n)

Every token is processed exactly once.

Stack operations such as `push()` and `pop()` take O(1) time under normal conditions.

Therefore:

```text
Time Complexity = O(n)
```

### Auxiliary Space Complexity: O(n)

In the worst case, many tokens can be numbers before an operator is encountered.

The stack may therefore hold O(n) values.

```text
Auxiliary Space = O(n)
```

---

## 11. Alternative: Switch Statement

The same logic can be written using a `switch` statement.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();

        for (String token : tokens) {
            switch (token) {
                case "+":
                case "-":
                case "*":
                case "/":
                    int right = stack.pop();
                    int left = stack.pop();

                    int result;

                    switch (token) {
                        case "+":
                            result = left + right;
                            break;
                        case "-":
                            result = left - right;
                            break;
                        case "*":
                            result = left * right;
                            break;
                        default:
                            result = left / right;
                    }

                    stack.push(result);
                    break;

                default:
                    stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }
}
```

This version uses descriptive variable names and separates operand order from the arithmetic operation.

Both implementations have the same time and space complexity.

---

## 12. Interview Explanation

If asked to explain the solution in an interview, say:

"I use a stack because Reverse Polish Notation places each operator after its operands. I iterate through the tokens from left to right. When a token is a number, I push it onto the stack. When it is an operator, I pop the right operand first and then the left operand, perform the operation, and push the result back. After processing all tokens, the stack contains the final result. Each token is processed once, giving O(n) time complexity and O(n) auxiliary space complexity."

---

## 13. Quick Revision Questions

Try answering these without looking at the solutions.

1. What is Reverse Polish Notation?
2. Why is a stack suitable for evaluating RPN?
3. Why do we pop two operands when an operator appears?
4. Which operand is popped first?
5. Why does subtraction use `temp2 - temp1`?
6. Why do we use `.equals()` instead of `==` for strings?
7. How does Java handle integer division with negative values?
8. Why does the algorithm take O(n) time?
9. What is the maximum auxiliary space complexity?
10. Why is `"-11"` a number rather than the subtraction operator?

---

## 14. Key Takeaways

- RPN operators appear after their operands.
- Use a stack to evaluate RPN expressions.
- Push numbers and evaluate operators when encountered.
- Pop the right operand first, then the left operand.
- Use `.equals()` to compare Java strings.
- Java integer division truncates toward zero.
- The final stack contains one result for a valid expression.

### Final Summary

**Problem:** LeetCode 150 — Evaluate Reverse Polish Notation

**Data Structure:** Stack (`ArrayDeque`)

**Time Complexity:** O(n)

**Auxiliary Space Complexity:** O(n)

**Most important rule:**

```java
int right = stack.pop();
int left = stack.pop();

// Perform left operator right.
```
