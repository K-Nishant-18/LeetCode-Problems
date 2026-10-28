
# LeetCode 3174: Clear Digits

- **Difficulty:** Easy
- **Topics:** String, Stack, Simulation
- **Language:** Java
- **LeetCode:** https://leetcode.com/problems/clear-digits/
- **Approach:** Stack
- **Status:** Solved

---

## 1. Problem Statement

You are given a string `s`.

Your task is to repeatedly remove the first digit from the string and the closest non-digit character to its left.

Continue until there are no digits left. Return the resulting string.

The problem guarantees that, whenever a digit is encountered, there is a non-digit character available to its left to remove.

### Example 1

**Input:**
```text
s = "abc"
```

**Output:**
```text
"abc"
```

**Explanation:**

There are no digits, so the string remains unchanged.

### Example 2

**Input:**
```text
s = "cb34"
```

**Output:**
```text
""
```

**Explanation:**

1. Digit `3` removes the closest character to its left, `b`.
2. Digit `4` removes the closest remaining character to its left, `c`.

The resulting string is empty.

### Example 3

**Input:**
```text
s = "abc3"
```

**Output:**
```text
"ab"
```

**Explanation:**

Digit `3` removes the closest character to its left, `c`.

The remaining string is `"ab"`.

---

## 2. Core Intuition

When a digit appears, we must remove the most recently encountered non-digit character that hasn't already been removed.

This is exactly the behavior of a **stack**.

A stack follows LIFO:

**Last In, First Out**

We can solve the problem by:

1. Creating a stack of characters.
2. Traversing the string from left to right.
3. If the current character is a digit, pop the most recent character from the stack.
4. Otherwise, push the current character onto the stack.
5. Build the final string in the original character order.

### Example

```text
Input: "abc3"

Read 'a' -> Push a
Stack: [a]

Read 'b' -> Push b
Stack: [a, b]

Read 'c' -> Push c
Stack: [a, b, c]

Read '3' -> Pop c
Stack: [a, b]

Output: "ab"
```

The digit itself is never pushed onto the stack.

---

## 3. My Final Stack Solution

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public String clearDigits(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c) && !stack.isEmpty()) {
                stack.pop();
            } else if (!Character.isDigit(c)) {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();
        Deque<Character> stack2 = new ArrayDeque<>();

        while (!stack.isEmpty()) {
            stack2.push(stack.pop());
        }

        while (!stack2.isEmpty()) {
            sb.append(stack2.pop());
        }

        return sb.toString();
    }
}
```

### How It Works

**Step 1: Create the stack**

```java
Deque<Character> stack = new ArrayDeque<>();
```

The stack stores non-digit characters that haven't been removed.

**Step 2: Traverse the string**

```java
for (char c : s.toCharArray())
```

Process every character from left to right.

**Step 3: Handle digits**

```java
if (Character.isDigit(c) && !stack.isEmpty()) {
    stack.pop();
}
```

When a digit appears, remove the most recently added character.

The `!stack.isEmpty()` check ensures we don't attempt to pop from an empty stack.

**Step 4: Handle non-digits**

```java
else if (!Character.isDigit(c)) {
    stack.push(c);
}
```

Push every non-digit character onto the stack.

**Step 5: Restore the original order**

```java
while (!stack.isEmpty()) {
    stack2.push(stack.pop());
}
```

A stack reverses the order when its elements are popped. Transferring them into a second stack reverses that order again.

**Step 6: Build the result**

```java
while (!stack2.isEmpty()) {
    sb.append(stack2.pop());
}
```

The characters are now appended in their original order.

---

## 4. Dry Run: `s = "abc3"`

### Initial state

```text
stack = []
```

### Process each character

| Character | Action | Stack, bottom to top |
|---|---|---|
| `a` | Push `a` | `[a]` |
| `b` | Push `b` | `[a, b]` |
| `c` | Push `c` | `[a, b, c]` |
| `3` | Pop `c` | `[a, b]` |

The remaining characters are `a` and `b`.

### Restore order

The first stack pops characters in reverse order:

```text
c, b, a
```

But after processing the digit, only `a` and `b` remain:

```text
Pop a -> stack2 = [a]
Pop b -> stack2 = [b, a]
```

When `stack2` is popped, the order is restored:

```text
a, b
```

**Final output:**

```text
"ab"
```

---

## 5. Why Does the Second Stack Work?

Consider the string `"abcd"`.

After processing the characters, the first stack contains:

```text
Bottom -> [d, c, b, a] <- Top
```

Popping the first stack produces:

```text
a, b, c, d
```

Pushing those characters onto a second stack reverses the order again.

Popping the second stack produces:

```text
d, c, b, a
```

Correction: with the first stack represented bottom-to-top as `[d, c, b, a]`, its pop order is `a, b, c, d`. Pushing in that order into the second stack makes its bottom-to-top contents `[a, b, c, d]`, so its pop order is `d, c, b, a`.

To restore the original string with two stacks, the transfer must be understood by the actual stack order. An easier and less error-prone approach is to append the first stack's pop order to a `StringBuilder`, then reverse the builder.

---

## 6. Optimized Solution

We can eliminate the second stack entirely.

After processing the string, pop the remaining characters into a `StringBuilder`. This produces the characters in reverse order, so call `reverse()` before returning the result.

### Optimized Code

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public String clearDigits(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.reverse().toString();
    }
}
```

### Why Can We Remove the Empty-Stack Check?

The problem guarantees that every digit has a non-digit character available to its left to remove. Therefore, when a digit is processed, the stack won't be empty.

For more defensive code outside this problem's guarantees, the empty-stack check can be retained.

### Why Is `reverse()` Correct?

Popping the stack produces the surviving characters in reverse order. Calling `sb.reverse()` restores the original order.

For example:

```text
Remaining stack: [c, b, a] (bottom to top)

Pop order: a, b, c
StringBuilder: "abc"
Reverse: "cba"
```

Be careful: the actual stack's bottom-to-top order depends on the push operations. For input `"abc"`, pushing each character makes the bottom-to-top order `[c, b, a]`, so popping yields `"abc"`, not `"cba"`. Therefore, the optimized code's `reverse()` would incorrectly return `"cba"` for `"abc"`.

**Correct optimized approach:** Use the stack to remove characters, then construct the output from bottom to top, or use a `StringBuilder` as the stack directly.

### Recommended O(n) Solution Using StringBuilder as a Stack

```java
class Solution {
    public String clearDigits(String s) {
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
```

This works because the last character in the `StringBuilder` is the closest remaining non-digit character to the left. Deleting it simulates a stack pop.

---

## 7. Complexity Analysis

Let `n` be the length of the input string.

### Time Complexity: O(n)

Each character is processed once. Stack operations take O(1) time, and building the final result takes O(n) time.

### Auxiliary Space Complexity: O(n)

The stack can contain up to `n` characters in the worst case, such as when the input contains no digits.

The `StringBuilder` also requires O(n) space for the output.

---

## 8. Common Mistakes and Lessons Learned

### Mistake 1: Using `Char` instead of `char`

Incorrect:

```java
for (Char c : s.toCharArray())
```

Correct:

```java
for (char c : s.toCharArray())
```

Java's primitive character type is `char`, with a lowercase `c`.

### Mistake 2: Calling `isDigit()` on a primitive `char`

Incorrect:

```java
c.isDigit()
```

Correct:

```java
Character.isDigit(c)
```

`Character.isDigit()` is a static method.

### Mistake 3: Forgetting the value in `stack.push()`

Incorrect:

```java
stack.push();
```

Correct:

```java
stack.push(c);
```

### Mistake 4: Returning the stack in reverse order

Repeatedly popping a stack changes the order in which characters are retrieved.

**Lesson:** Preserve the original character order when building the result.

### Mistake 5: Using two stacks unnecessarily

A second stack can restore order, but a `StringBuilder` used as a stack is simpler for this problem.

---

## 9. Key Takeaways

- Use a stack when the most recently added item must be removed first.
- Digits remove the nearest remaining non-digit character to their left.
- Do not push digits onto the stack.
- `Character.isDigit(c)` checks whether a character is a digit.
- `StringBuilder.deleteCharAt(sb.length() - 1)` can simulate popping the last character.
- Always consider whether a second data structure is necessary.

---

## 10. Quick Revision Questions

1. Why is a stack suitable for Clear Digits?
2. What happens when a digit is encountered?
3. Why aren't digits pushed onto the stack?
4. What is the purpose of `Character.isDigit(c)`?
5. Why can a second stack be unnecessary?
6. How can a `StringBuilder` simulate a stack?
7. What are the time and space complexities?
8. How does the solution handle a string with no digits?

---

## Final Summary

**Problem:** LeetCode 3174 — Clear Digits

**Primary concept:** Stack

**Core rule:**

```text
Non-digit -> Keep it
Digit     -> Remove the closest remaining character to its left
```

**Time Complexity:** O(n)

**Auxiliary Space Complexity:** O(n)

**Recommended implementation:** Use a `StringBuilder` as a stack to keep the code concise and preserve the remaining characters in their original order.
