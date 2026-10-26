
# LeetCode 20 — Valid Parentheses

- **Difficulty:** Easy
- **Topic:** Stack, String
- **Pattern:** Matching Brackets Using a Stack
- **Problem Link:** https://leetcode.com/problems/valid-parentheses/

---

## 1. Problem Statement

Given a string `s` containing only the characters `'('`, `')'`, `'{'`, `'}'`, `'['`, and `']'`, determine whether the input string is valid.

A string is valid if:

1. Every opening bracket has a corresponding closing bracket of the same type.
2. Opening brackets are closed in the correct order.
3. Every closing bracket matches the most recently opened unmatched bracket.

### Examples

**Example 1**

Input:
```text
s = "()"
```

Output:
```text
true
```

**Example 2**

Input:
```text
s = "()[]{}"
```

Output:
```text
true
```

**Example 3**

Input:
```text
s = "(]"
```

Output:
```text
false
```

**Example 4**

Input:
```text
s = "([)]"
```

Output:
```text
false
```

**Example 5**

Input:
```text
s = "((("
```

Output:
```text
false
```

---

## 2. My Original Approach

My approach was to use a stack to store opening brackets and check each closing bracket against the top of the stack.

```java
class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' ||
                s.charAt(i) == '{' ||
                s.charAt(i) == '[') {

                stack.push(s.charAt(i));

            } else if (s.charAt(i) == ')' &&
                       stack.isEmpty() == false) {

                if (stack.peek() == '(') {
                    stack.pop();
                } else {
                    return false;
                }

            } else if (s.charAt(i) == '}' &&
                       stack.isEmpty() == false) {

                if (stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }

            } else if (s.charAt(i) == ']' &&
                       stack.isEmpty() == false) {

                if (stack.peek() == '[') {
                    stack.pop();
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }

        return stack.isEmpty();
    }
}
```

### What I Learned While Debugging

**Mistake 1: Not handling an empty stack before matching a closing bracket**

Consider:

```text
s = ")"
```

Calling `stack.peek()` on an empty stack returns `null`. Comparing this with a primitive `char` can cause a `NullPointerException` due to unboxing.

The final `else` in my corrected implementation handles this situation by returning `false`.

**Mistake 2: Forgetting to check leftover opening brackets**

Consider:

```text
s = "((("
```

All three characters are pushed onto the stack, but none are closed.

Returning `true` unconditionally would be incorrect.

The final check solves this:

```java
return stack.isEmpty();
```

If opening brackets remain, the stack is not empty, so the method returns `false`.

---

## 3. Understanding the Stack

A stack follows **LIFO — Last In, First Out**.

The last item pushed onto the stack is the first item removed.

For example, consider:

```text
s = "({})"
```

Process each character from left to right:

| Character | Operation | Stack (top first) |
|---|---|---|
| `(` | Push | `(` |
| `{` | Push | `{`, `(` |
| `}` | Match and pop | `(` |
| `)` | Match and pop | Empty |

The stack becomes empty, so the string is valid.

### Why Does a Stack Work?

Nested brackets must close in reverse order of opening.

For example:

```text
Opening order: (
                {

Closing order:  }
                )
```

The most recently opened bracket must close first. A stack naturally enforces this rule.

---

## 4. Corrected Solution — My Approach

This is my final working solution, preserving the logic I developed.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);

            } else if (ch == ')' && !stack.isEmpty()) {
                if (stack.peek() == '(') {
                    stack.pop();
                } else {
                    return false;
                }

            } else if (ch == '}' && !stack.isEmpty()) {
                if (stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }

            } else if (ch == ']' && !stack.isEmpty()) {
                if (stack.peek() == '[') {
                    stack.pop();
                } else {
                    return false;
                }

            } else {
                return false;
            }
        }

        return stack.isEmpty();
    }
}
```

### Key Operations

| Method | Purpose |
|---|---|
| `stack.push(ch)` | Adds an opening bracket to the top |
| `stack.peek()` | Reads the top without removing it |
| `stack.pop()` | Removes the matched opening bracket |
| `stack.isEmpty()` | Checks whether the stack contains any unmatched brackets |

In Java, `Deque<Character>` with `ArrayDeque` is a good choice for stack operations.

---

## 5. Optimized and Cleaner Solution

We can reduce the repeated conditions by handling all closing brackets in one branch and checking the expected opening bracket.

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                stack.push(ch);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                if (ch == ')' && top != '(' ||
                    ch == '}' && top != '{' ||
                    ch == ']' && top != '[') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
```

### Why Is This Version Cleaner?

- Opening brackets are pushed onto the stack.
- For any closing bracket, we first verify that the stack is not empty.
- We pop the most recent opening bracket and check whether the types match.
- If they do not match, we return `false`.
- After processing the string, the stack must be empty.

This avoids repeating a separate branch for every closing bracket.

**Important:** The stack being empty at the end is necessary, but not sufficient on its own. Every closing bracket must also match the correct opening bracket.

### Complexity

Both solutions have the same asymptotic complexity.

- **Time:** `O(n)` — each character is processed at most once.
- **Auxiliary space:** `O(n)` — in the worst case, the stack stores all opening brackets.

Here, `n` is the length of the string.

---

## 6. Dry Run

Consider:

```text
s = "([{}])"
```

Initially:

```text
Stack = []
```

| Step | Character | Action | Stack (top first) |
|---|---|---|---|
| 1 | `(` | Push | `(` |
| 2 | `[` | Push | `[`, `(` |
| 3 | `{` | Push | `{`, `[`, `(` |
| 4 | `}` | Match `{`, pop | `[`, `(` |
| 5 | `]` | Match `[`, pop | `(` |
| 6 | `)` | Match `(`, pop | Empty |

The stack is empty at the end.

Output:
```text
true
```

### Dry Run of an Invalid String

Consider:

```text
s = "([)]"
```

| Step | Character | Action | Stack (top first) |
|---|---|---|---|
| 1 | `(` | Push | `(` |
| 2 | `[` | Push | `[`, `(` |
| 3 | `)` | Top is `[`, not `(` | Invalid |

The algorithm immediately returns `false`.

This demonstrates why matching the most recent unmatched opening bracket is essential.

---

## 7. Important Edge Cases

| Input | Output | Reason |
|---|---|---|
| `""` | `true` | Empty string has no unmatched brackets |
| `"()"` | `true` | Correct matching pair |
| `"()[]{}"` | `true` | Multiple valid pairs |
| `"(]"` | `false` | Wrong bracket type |
| `"([)]"` | `false` | Incorrect nesting |
| `")"` | `false` | Closing bracket without an opening bracket |
| `"((("` | `false` | Unmatched opening brackets |
| `"(()"` | `false` | One opening bracket remains unmatched |

---

## 8. Common Mistakes to Avoid

### Mistake 1: Accessing the top of an empty stack

Always ensure that a closing bracket has an opening bracket to match.

```java
if (stack.isEmpty()) {
    return false;
}
```

### Mistake 2: Checking only whether the stack is empty at the end

A string can contain mismatched brackets even if the number of opening and closing brackets is equal.

For example:

```text
([)]
```

The counts match, but the nesting order is invalid.

### Mistake 3: Checking only bracket counts

Equal numbers of opening and closing brackets do not guarantee a valid string. Their order and types matter.

### Mistake 4: Popping without checking the type

The top opening bracket must match the current closing bracket. Otherwise, return `false`.

### Mistake 5: Confusing `peek()` and `pop()`

- `peek()` reads the top element without removing it.
- `pop()` removes and returns the top element.

---

## 9. Interview Takeaways

- Recognize when a problem involves nested structures or last-in-first-out processing.
- Use a stack to track unmatched opening brackets.
- Always handle an empty stack before matching a closing bracket.
- Check the bracket type, not just the number of brackets.
- Verify that the stack is empty after processing the entire string.
- Prefer `Deque` with `ArrayDeque` for stack operations in modern Java.
- Explain why the solution takes `O(n)` time and `O(n)` auxiliary space.

### The Core Algorithm

```text
For each character:
    If it is an opening bracket:
        Push it onto the stack.
    Otherwise:
        If the stack is empty:
            Return false.
        Pop the top opening bracket.
        If the types do not match:
            Return false.

Return whether the stack is empty.
```

---

## 10. Final Revision Checklist

- [ ] I understand why LIFO is useful for nested brackets.
- [ ] I can explain `push()`, `pop()`, `peek()`, and `isEmpty()`.
- [ ] I handle closing brackets when the stack is empty.
- [ ] I reject mismatched bracket types.
- [ ] I check for leftover opening brackets.
- [ ] I can trace nested brackets manually.
- [ ] I can implement the solution without looking at my notes.
- [ ] I can explain the time and auxiliary space complexity.

**Final lesson:** A stack helps you match each closing bracket with the most recently opened bracket that has not yet been closed.
