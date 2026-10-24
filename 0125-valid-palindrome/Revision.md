
# 🐞 Problem Statement

**LeetCode 125 — Valid Palindrome**

**Difficulty:** Easy  
**Topics:** String, Two Pointers

A string is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward.

Given a string `s`, return `true` if it is a palindrome, otherwise return `false`.

Only letters and digits are considered alphanumeric characters.

## 📌 Examples

### Example 1

```text
Input:  s = "A man, a plan, a canal: Panama"
Output: true

Explanation:
After filtering and converting to lowercase:
"amanaplanacanalpanama"
It reads the same forward and backward.
```

### Example 2

```text
Input:  s = "race a car"
Output: false

Explanation:
After filtering:
"raceacar"
It is not a palindrome.
```

### Example 3

```text
Input:  s = " "
Output: true

Explanation:
After filtering, the string is empty.
An empty string is considered a palindrome.
```

---

## 📟 Solution 1 — Your Original Approach

**Approach:** Filter Characters + Two Pointers

### Java Code

```java
class Solution {
    public boolean isPalindrome(String s) {
        List<Character> list = new ArrayList<>();
        char[] c = s.toCharArray();

        for (Character ele : c) {
            if (Character.isLetterOrDigit(ele)) {
                char temp = Character.toLowerCase(ele);
                list.add(temp);
            }
        }

        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            if (list.get(left) != list.get(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```

### 🎀 Explanation

**Step 1: Create a list**

```java
List<Character> list = new ArrayList<>();
```

The list stores only letters and digits after converting them to lowercase.

**Step 2: Filter and normalize characters**

```java
for (Character ele : c) {
    if (Character.isLetterOrDigit(ele)) {
        char temp = Character.toLowerCase(ele);
        list.add(temp);
    }
}
```

- `Character.isLetterOrDigit(ele)` checks whether the character is a letter or digit.
- `Character.toLowerCase(ele)` converts uppercase letters to lowercase.
- The normalized character is added to the list.

For example:

```text
Original:  "A man, a plan!"
Filtered:  "amanaplan"
```

**Step 3: Initialize two pointers**

```java
int left = 0;
int right = list.size() - 1;
```

- `left` starts at the beginning.
- `right` starts at the end.

**Step 4: Compare characters**

```java
while (left <= right) {
    if (list.get(left) != list.get(right)) {
        return false;
    }

    left++;
    right--;
}
```

If any pair of characters differs, the string is not a palindrome.

Otherwise, move both pointers toward the center.

**Step 5: Return the result**

```java
return true;
```

If every comparison succeeds, the string is a palindrome.

### ⏱️ Complexity Analysis

Let `n` be the length of the input string.

- **Time Complexity:** `O(n)` — filtering and comparing require linear time.
- **Auxiliary Space Complexity:** `O(n)` — the list may store every character.

---

## 🚀 Solution 2 — Optimized Approach

**Approach:** Two Pointers Without Extra Storage

This solution avoids creating a list or character array. Instead, it checks characters directly in the original string.

### Java Code

```java
class Solution {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            // Skip non-alphanumeric characters from the left
            while (left < right &&
                   !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            // Skip non-alphanumeric characters from the right
            while (left < right &&
                   !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare characters without case sensitivity
            if (Character.toLowerCase(s.charAt(left)) !=
                Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```

### 🎀 Explanation

#### Step 1: Initialize the pointers

```java
int left = 0;
int right = s.length() - 1;
```

The left pointer starts at the first character, and the right pointer starts at the last character.

#### Step 2: Skip invalid characters

```java
while (left < right &&
       !Character.isLetterOrDigit(s.charAt(left))) {
    left++;
}
```

Move `left` forward until it reaches a letter or digit.

Similarly:

```java
while (left < right &&
       !Character.isLetterOrDigit(s.charAt(right))) {
    right--;
}
```

Move `right` backward until it reaches a letter or digit.

The condition `left < right` prevents the pointers from crossing while skipping characters.

#### Step 3: Compare characters

```java
if (Character.toLowerCase(s.charAt(left)) !=
    Character.toLowerCase(s.charAt(right))) {
    return false;
}
```

Convert both characters to lowercase before comparing them.

If they differ, return `false` immediately.

#### Step 4: Move toward the center

```java
left++;
right--;
```

After matching a pair, move both pointers inward.

Repeat until the pointers meet or cross.

#### Step 5: Return true

```java
return true;
```

If no mismatch is found, the string is a palindrome.

---

## 🔍 Dry Run — Optimized Solution

Input:

```text
s = "A man, a plan, a canal: Panama"
```

The pointers skip punctuation and spaces and compare valid characters case-insensitively.

| Left character | Right character | Result |
|---|---|---|
| `A` | `a` | Match |
| `m` | `m` | Match |
| `a` | `a` | Match |
| `n` | `n` | Match |
| `a` | `a` | Match |
| `p` | `P` | Match |
| `l` | `l` | Match |
| `a` | `a` | Match |
| `n` | `n` | Match |
| `a` | `a` | Match |

The process continues until the pointers meet or cross.

**Output:**

```text
true
```

---

## 📊 Comparison of Both Approaches

| Feature | Original Approach | Optimized Approach |
|---|---|---|
| Technique | Filter + Two Pointers | Two Pointers |
| Time Complexity | `O(n)` | `O(n)` |
| Auxiliary Space | `O(n)` | `O(1)` |
| Extra List | Yes | No |
| Modifies Input | No | No |
| Interview Preference | Good initial solution | Preferred optimal solution |

---

## 🧠 Summary of the Idea

A palindrome reads the same forward and backward after ignoring punctuation, spaces, and letter case.

### Original Approach

1. Convert the string to a character array.
2. Filter non-alphanumeric characters.
3. Convert valid characters to lowercase.
4. Store them in a list.
5. Compare the list from both ends.

### Optimized Approach

1. Initialize two pointers at opposite ends of the original string.
2. Skip non-alphanumeric characters.
3. Compare the remaining characters case-insensitively.
4. Return `false` immediately if they differ.
5. Move both pointers inward.
6. Return `true` if no mismatch is found.

**Key improvement:** Both approaches run in `O(n)` time, but the optimized approach reduces auxiliary space from `O(n)` to `O(1)`.

---

## 🎯 Interview Takeaways

- **Pattern:** Two Pointers.
- Use `Character.isLetterOrDigit()` to identify valid characters.
- Use `Character.toLowerCase()` for case-insensitive comparison.
- Use `s.charAt(index)` to access a string character.
- Use `s.length()` to get a string's length.
- Use `list.size()` for an `ArrayList`, not `list.length`.
- The empty string is a palindrome.
- Skip punctuation and spaces rather than comparing them.
- Return `false` immediately when a mismatch is found.
- Prefer the optimized solution when constant auxiliary space is required.

### 🔑 Memory Trick

**Skip → Compare → Move inward.**

Use two pointers, ignore invalid characters, compare lowercase versions, and stop at the first mismatch.
