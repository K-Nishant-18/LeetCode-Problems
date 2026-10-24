
# LeetCode 1768 — Merge Strings Alternately

## 📝 Problem Statement

You are given two strings, `word1` and `word2`.

Merge the strings by adding characters in alternating order, starting with `word1`. If one string is longer than the other, append the remaining characters of the longer string to the end of the merged string.

Return the merged string.

## 🧪 Examples

### Example 1

**Input:**
```java
word1 = "abc", word2 = "pqr"
```

**Output:**
```text
"apbqcr"
```

Explanation: Alternate characters from both strings: `a`, `p`, `b`, `q`, `c`, `r`.

### Example 2

**Input:**
```java
word1 = "ab", word2 = "pqrs"
```

**Output:**
```text
"apbqrs"
```

Explanation: After alternating `a`, `p`, `b`, and `q`, append the remaining `rs`.

### Example 3

**Input:**
```java
word1 = "abcd", word2 = "pq"
```

**Output:**
```text
"apbqcd"
```

Explanation: After alternating `a`, `p`, `b`, and `q`, append the remaining `cd`.

---

## 💡 Approach 1: Two Pointers + Counter

### Original Solution

```java
class Solution {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j = 0;
        int count = 0;

        while (i < word1.length() && j < word2.length()) {
            if (count % 2 == 0) {
                sb.append(word1.charAt(i));
                i++;
                count++;
            } else {
                sb.append(word2.charAt(j));
                j++;
                count++;
            }
        }

        while (j < word2.length()) {
            sb.append(word2.charAt(j));
            j++;
        }

        while (i < word1.length()) {
            sb.append(word1.charAt(i));
            i++;
        }

        return sb.toString();
    }
}
```

### How It Works

1. `StringBuilder` efficiently builds the merged string.
2. `i` tracks the current position in `word1`.
3. `j` tracks the current position in `word2`.
4. `count % 2 == 0` selects a character from `word1`; otherwise, it selects one from `word2`.
5. Once either string is exhausted, the remaining loops append the rest of the other string.

### Dry Run

Input:

```java
word1 = "abc";
word2 = "pqr";
```

| Iteration | `count` before | Character appended | Result |
|---|---:|---|---|
| 1 | 0 | `a` from `word1` | `a` |
| 2 | 1 | `p` from `word2` | `ap` |
| 3 | 2 | `b` from `word1` | `apb` |
| 4 | 3 | `q` from `word2` | `apbq` |
| 5 | 4 | `c` from `word1` | `apbqc` |
| 6 | 5 | `r` from `word2` | `apbqcr` |

Output: `"apbqcr"`

### Complexity Analysis

Let:
- `n` = length of `word1`
- `m` = length of `word2`

- **Time:** `O(n + m)` — every character is appended once.
- **Auxiliary space:** `O(1)` excluding the output buffer.
- **Output space:** `O(n + m)`.

The solution is correct, but the counter is unnecessary.

---

## ⚡ Approach 2: Two Pointers Without a Counter

### Key Idea

Instead of tracking whose turn it is, append one character from each string during every iteration.

Use two independent `if` statements:

- If `word1` has a character available, append it.
- If `word2` has a character available, append it.

The order of these statements guarantees alternation.

### Optimized Solution

```java
class Solution {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j = 0;

        while (i < word1.length() || j < word2.length()) {
            if (i < word1.length()) {
                sb.append(word1.charAt(i));
                i++;
            }

            if (j < word2.length()) {
                sb.append(word2.charAt(j));
                j++;
            }
        }

        return sb.toString();
    }
}
```

### Why Does This Work?

Consider:

```java
word1 = "abc";
word2 = "pq";
```

Initially, `i = 0` and `j = 0`.

| Iteration | First `if` | Second `if` | Result |
|---|---|---|---|
| 1 | Append `a` | Append `p` | `ap` |
| 2 | Append `b` | Append `q` | `apbq` |
| 3 | Append `c` | Skipped: `word2` exhausted | `apbqc` |

Output: `"apbqc"`

### Understanding the Loop Condition

```java
while (i < word1.length() || j < word2.length())
```

The `||` operator means **OR**. The loop continues as long as at least one string has characters remaining.

If we used `&&` instead, the loop would stop as soon as either string was exhausted, leaving the remaining characters unprocessed.

### Why Two Separate `if` Statements?

```java
if (i < word1.length()) {
    sb.append(word1.charAt(i++));
}

if (j < word2.length()) {
    sb.append(word2.charAt(j++));
}
```

Both statements can execute during the same iteration. This lets us append one character from each string without a counter.

Using `else if` would be incorrect because it would append at most one character per iteration and could break the required alternating order.

---

## 🚀 Approach 3: Single Index

We can also use one index because both strings are processed from left to right at the same pace.

```java
class Solution {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        while (i < word1.length() || i < word2.length()) {
            if (i < word1.length()) {
                sb.append(word1.charAt(i));
            }

            if (i < word2.length()) {
                sb.append(word2.charAt(i));
            }

            i++;
        }

        return sb.toString();
    }
}
```

### Why Does One Index Work?

At each index `i`:

1. Append `word1.charAt(i)` if available.
2. Append `word2.charAt(i)` if available.
3. Increment `i`.

For `word1 = "abc"` and `word2 = "pq"`, the result is `"apbqc"`.

The single-index solution is concise because the two strings are read in parallel.

---

## ⚖️ Comparison of Approaches

| Feature | Counter Approach | Two Pointers | Single Index |
|---|---|---|---|
| Variables | `i`, `j`, `count` | `i`, `j` | `i` |
| Extra counter | Yes | No | No |
| Time complexity | `O(n + m)` | `O(n + m)` | `O(n + m)` |
| Output space | `O(n + m)` | `O(n + m)` | `O(n + m)` |
| Handles unequal lengths | Yes | Yes | Yes |

All three approaches are linear-time solutions. The two-pointer and single-index approaches avoid tracking turns explicitly.

## 🎯 Interview Takeaways

1. Use `StringBuilder` when constructing strings incrementally in Java.
2. Every character should be appended exactly once.
3. Use `||` when processing should continue until both inputs are exhausted.
4. Separate `if` statements allow both inputs to contribute in the same iteration.
5. A counter is not always necessary when loop structure and pointer positions already determine the operation order.
6. The single-index approach works here because both strings advance at the same rate.

## 🧠 Quick Summary

**Original:** Alternate using `count`, then append leftovers with separate loops.

**Simpler:** Append from each string when available, using two `if` statements inside a single loop.

**Most compact:** Use one index to read both strings in parallel.

**Time complexity:** `O(n + m)`.

**Auxiliary space:** `O(1)` excluding the output buffer.
