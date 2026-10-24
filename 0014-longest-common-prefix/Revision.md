
# 🐞 Problem Statement

**LeetCode 14 — Longest Common Prefix**

**Difficulty:** Easy  
**Topics:** String, Array

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string `""`.

A **prefix** is a sequence of characters at the beginning of a string.

## 📌 Examples

### Example 1

```text
Input:  strs = ["flower", "flow", "flight"]
Output: "fl"
```

### Example 2

```text
Input:  strs = ["dog", "racecar", "car"]
Output: ""
```

Explanation: The strings do not share a common starting character.

### Example 3

```text
Input:  strs = ["interspecies", "interstellar", "interstate"]
Output: "inters"
```

---

## 📟 Solution 1 — Your Original Approach

**Approach:** Vertical Scanning

Your solution finds the shortest string length first, then compares characters at the same index across all strings.

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        int minLength = strs[0].length();

        for (String s : strs) {
            minLength = Math.min(minLength, s.length());
        }

        for (int j = 0; j < minLength; j++) {
            for (int i = 1; i < strs.length; i++) {
                if (strs[i].charAt(j) != strs[0].charAt(j)) {
                    return strs[0].substring(0, j);
                }
            }
        }

        return strs[0].substring(0, minLength);
    }
}
```

### 🎀 Explanation

**Step 1: Handle an empty input**

```java
if (strs == null || strs.length == 0) {
    return "";
}
```

Without this check, accessing `strs[0]` could throw an exception.

**Step 2: Find the shortest string length**

```java
int minLength = strs[0].length();

for (String s : strs) {
    minLength = Math.min(minLength, s.length());
}
```

The common prefix cannot be longer than the shortest string.

**Step 3: Compare characters vertically**

```java
for (int j = 0; j < minLength; j++) {
    for (int i = 1; i < strs.length; i++) {
        if (strs[i].charAt(j) != strs[0].charAt(j)) {
            return strs[0].substring(0, j);
        }
    }
}
```

At each character index `j`, compare every string against the first string.

If a mismatch occurs, return the portion before that index.

**Step 4: Return the prefix**

If no mismatch occurs, return the first string up to `minLength`.

### 🔍 Dry Run

Input:

```text
strs = ["flower", "flow", "flight"]
```

| Index | `flower` | `flow` | `flight` | Result |
|---:|---|---|---|---|
| 0 | f | f | f | Match |
| 1 | l | l | l | Match |
| 2 | o | o | i | Mismatch |

The mismatch occurs at index `2`, so:

```text
"flower".substring(0, 2) = "fl"
```

Output: `"fl"`

### ⏱️ Complexity Analysis

Let `n` be the number of strings and `m` the length of the shortest string.

- **Time:** `O(n × m)` in the worst case.
- **Auxiliary space:** `O(1)`, excluding the returned string.

---

## 🚀 Solution 2 — Optimized Alternative

**Approach:** Horizontal Scanning

Start with the first string as the prefix. Compare it with each subsequent string, shortening the prefix until it matches the beginning of that string.

This approach has the same worst-case asymptotic complexity as your original solution, but its logic is often simpler to explain.

### Java Code

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);

                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        return prefix;
    }
}
```

### 🎀 Explanation

**Step 1: Assume the first string is the prefix**

```java
String prefix = strs[0];
```

Initially, the entire first string is our candidate.

**Step 2: Compare against every other string**

```java
while (!strs[i].startsWith(prefix)) {
    prefix = prefix.substring(0, prefix.length() - 1);
}
```

If a string doesn't start with the current prefix, remove its last character and try again.

**Step 3: Stop if the prefix becomes empty**

If no common prefix exists, return `""`.

**Step 4: Return the final prefix**

Once every string starts with the same prefix, return it.

### 🔍 Dry Run

Input:

```text
strs = ["flower", "flow", "flight"]
```

| Comparison | Prefix | Action |
|---|---|---|
| Start | `flower` | Initial candidate |
| Compare with `flow` | `flower` → `flow` | Shorten until it matches |
| Compare with `flight` | `flow` → `flo` → `fl` | Shorten until it matches |
| Finish | `fl` | Return `"fl"` |

Output: `"fl"`

### ⏱️ Complexity Analysis

- **Time:** `O(n × m)` in the worst case.
- **Auxiliary space:** `O(1)`, excluding string operations and the returned result. Java creates new strings for `substring`, so actual allocation can occur during shortening.

---

## 📊 Comparison of Both Approaches

| Feature | Vertical Scanning | Horizontal Scanning |
|---|---|---|
| Core idea | Compare same-index characters | Shorten a candidate prefix |
| Time | `O(n × m)` | `O(n × m)` worst case |
| Auxiliary space | `O(1)` | `O(1)` algorithmic state |
| Early mismatch detection | Yes | Yes |
| Extra prefix string allocation | Mainly on return | Can happen repeatedly |
| Best use | Efficient character-by-character checking | Simple, intuitive implementation |

---

## 🧠 Summary of the Idea

The longest common prefix must match at the beginning of every string.

### Vertical Scanning

1. Handle empty input.
2. Find the shortest string length.
3. Compare characters at each index across all strings.
4. Return the prefix at the first mismatch.

### Horizontal Scanning

1. Initialize the candidate prefix to the first string.
2. Compare it with each remaining string.
3. Shorten it until the current string starts with it.
4. Return the final prefix.

### 🔑 Memory Trick

**Vertical:** Compare down each character column.

**Horizontal:** Compare strings one by one and shrink the prefix.

---

## 🎯 Interview Takeaways

- A common prefix must begin at index `0` in every string.
- The shortest string sets the maximum possible prefix length.
- Handle an empty array before accessing `strs[0]`.
- `startsWith()` makes horizontal scanning straightforward.
- Both approaches have worst-case `O(n × m)` time complexity.
- Vertical scanning is a strong choice when you want direct character comparisons without repeatedly creating shorter candidate strings.
