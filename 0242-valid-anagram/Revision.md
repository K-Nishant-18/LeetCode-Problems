
# 242. Valid Anagram

- **LeetCode:** https://leetcode.com/problems/valid-anagram/
- **Difficulty:** Easy
- **Pattern:** String, Sorting, Frequency Counting
- **Primary Approach:** Character Frequency Counting
- **Alternative Approach:** Sorting

---

## 1. Problem Statement

Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.

An anagram is a word or phrase formed by rearranging the letters of another word or phrase, using all the original letters exactly once.

### Examples

**Example 1:**

```text
Input: s = "anagram", t = "nagaram"
Output: true
```

Explanation: Both strings contain exactly the same characters with the same frequencies.

**Example 2:**

```text
Input: s = "rat", t = "car"
Output: false
```

Explanation: The character frequencies are different.

**Example 3:**

```text
Input: s = "a", t = "ab"
Output: false
```

Explanation: The strings have different lengths.

---

## 2. Key Observation

Two strings are anagrams if:

1. Both strings have the same length.
2. Every character appears the same number of times in both strings.

For example:

```text
s = "listen"
t = "silent"
```

Character frequencies:

| Character | s | t |
|---|---:|---:|
| l | 1 | 1 |
| i | 1 | 1 |
| s | 1 | 1 |
| t | 1 | 1 |
| e | 1 | 1 |
| n | 1 | 1 |

Both frequency distributions are identical.

**Important:** Character order does not matter. Character frequency does.

---

## 3. Approach 1: Sorting

### Intuition

If two strings contain exactly the same characters with the same frequencies, sorting both strings will produce identical character sequences.

Example:

```text
s = "anagram"
t = "nagaram"

After sorting:

s = "aaagmnr"
t = "aaagmnr"
```

Since both sorted arrays are equal, they are anagrams.

### Java Solution

```java
import java.util.Arrays;

class Solution {
    public boolean isAnagram(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }

        char[] arr1 = s.toCharArray();
        char[] arr2 = t.toCharArray();

        Arrays.sort(arr1);
        Arrays.sort(arr2);

        return Arrays.equals(arr1, arr2);
    }
}
```

### Dry Run

```text
s = "rat"
t = "tar"

Step 1: Convert strings into arrays

arr1 = ['r', 'a', 't']
arr2 = ['t', 'a', 'r']

Step 2: Sort both arrays

arr1 = ['a', 'r', 't']
arr2 = ['a', 'r', 't']

Step 3: Compare arrays

Arrays.equals(arr1, arr2) = true

Output: true
```

### Complexity Analysis

Let `n` be the length of each string.

| Operation | Complexity |
|---|---|
| Convert to character arrays | O(n) |
| Sort both arrays | O(n log n) |
| Compare arrays | O(n) |
| **Total Time** | **O(n log n)** |
| **Auxiliary Space** | **O(n)** |

Space is O(n) for the two character arrays.

### Advantages

- Simple and easy to implement.
- Works with uppercase and lowercase characters.
- Works with Java `char` values beyond lowercase English letters.

### Disadvantage

Sorting takes O(n log n), which is slower than frequency counting for a constrained character set.

---

## 4. Approach 2: Frequency Counting (Optimal)

### Intuition

Instead of sorting characters, count how many times each character appears.

If a character appears in `s`, increase its frequency.

If the same character appears in `t`, decrease its frequency.

At the end:

- If every frequency is zero, both strings are anagrams.
- If any frequency is non-zero, they are not anagrams.

### Why an array of size 26?

The problem's lowercase-English-letter constraint means there are only 26 possible characters:

```text
a, b, c, d, ... z
```

We can use an integer array of size 26.

Character-to-index mapping:

```text
'a' - 'a' = 0
'b' - 'a' = 1
'c' - 'a' = 2
...
'z' - 'a' = 25
```

### Java Solution

```java
class Solution {
    public boolean isAnagram(String s, String t) {

        if (s.length() != t.length()) {
            return false;
        }

        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
            freq[t.charAt(i) - 'a']--;
        }

        for (int count : freq) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }
}
```

### Dry Run

```text
s = "anagram"
t = "nagaram"

Initially:
freq = [0, 0, 0, ... 0]

For each position:

i = 0:
s.charAt(0) = 'a' -> increment a
t.charAt(0) = 'n' -> decrement n

i = 1:
s.charAt(1) = 'n' -> increment n
t.charAt(1) = 'a' -> decrement a

Continue for all positions...

Final frequency array:

All values = 0

Output: true
```

Notice that each character's increments and decrements cancel out because both strings contain identical character frequencies.

### Complexity Analysis

Let `n` be the length of the strings.

| Operation | Complexity |
|---|---|
| Length comparison | O(1) |
| Frequency counting | O(n) |
| Checking frequency array | O(26) |
| **Total Time** | **O(n)** |
| **Auxiliary Space** | **O(1)** |

Why O(1) space?

The frequency array always contains exactly 26 elements, regardless of input size.

---

## 5. Approach Comparison

| Criteria | Sorting | Frequency Counting |
|---|---|---|
| Time | O(n log n) | O(n) |
| Auxiliary Space | O(n) | O(1) |
| Implementation | Simple | Slightly more logic |
| Character constraint | General Java chars | Lowercase English only |
| Performance | Good | Better |
| Interview preference | Acceptable | Preferred when constraints allow |

**Final recommendation:** Use frequency counting when the input is restricted to lowercase English letters. Use sorting when you want a straightforward solution that supports a broader character range.

---

## 6. Common Mistakes

### Mistake 1: Not checking string lengths

```java
if (s.length() != t.length()) {
    return false;
}
```

Different-length strings cannot be anagrams.

### Mistake 2: Using a frequency array of size 26 without checking constraints

```java
freq[ch - 'a']++;
```

This assumes lowercase English letters. Uppercase letters, digits, punctuation, and Unicode characters need a different strategy.

### Mistake 3: Checking only whether characters exist

Two strings can contain the same distinct characters but have different frequencies.

Example:

```text
s = "aab"
t = "abb"
```

Both contain `a` and `b`, but they are not anagrams.

Frequency counting handles this correctly.

---

## 7. Edge Cases

| Input s | Input t | Output | Reason |
|---|---|---|---|
| `"a"` | `"a"` | true | Identical |
| `"a"` | `"b"` | false | Different characters |
| `"ab"` | `"a"` | false | Different lengths |
| `"aab"` | `"abb"` | false | Different frequencies |
| `""` | `""` | true | Both empty |
| `"anagram"` | `"nagaram"` | true | Same frequencies |

---

## 8. Interview Explanation

"I first check whether both strings have the same length. If their lengths differ, they cannot be anagrams.

Since the problem restricts the input to lowercase English letters, I use an integer array of size 26 to track character frequencies.

I iterate through both strings simultaneously. For every character in the first string, I increment its frequency. For every character in the second string, I decrement its frequency.

If both strings contain exactly the same characters with identical frequencies, all array values will become zero.

Therefore, I return true if every frequency is zero; otherwise, I return false.

This approach takes O(n) time and O(1) auxiliary space."

---

## 9. Important Concepts to Remember

- Anagram means same characters with the same frequencies.
- Character order does not matter.
- `toCharArray()` converts a String into a character array.
- `Arrays.sort()` sorts a character array.
- `Arrays.equals()` compares array contents.
- `charAt(i) - 'a'` maps a lowercase letter to an index from 0 to 25.
- Incrementing and decrementing frequencies allows us to compare two strings in one traversal.
- A fixed-size frequency array uses O(1) auxiliary space.

---

## 10. Interview Follow-up Questions

1. Can you solve Valid Anagram without sorting?
2. Why do we check string lengths first?
3. Why is the frequency array size 26?
4. Why does `charAt(i) - 'a'` work?
5. Why is the frequency-counting solution O(n)?
6. Why is the auxiliary space O(1)?
7. How would you handle uppercase letters?
8. How would you handle arbitrary Unicode characters?
9. Can you solve this using a HashMap?
10. What is the difference between an anagram and a palindrome?

---

## 11. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| 49. Group Anagrams | HashMap + Frequency Counting |
| 383. Ransom Note | Character Frequency |
| 387. First Unique Character in a String | Frequency Counting |
| 438. Find All Anagrams in a String | Sliding Window + Frequency Counting |
| 567. Permutation in String | Sliding Window + Frequency Counting |

---

## 12. Quick Revision

**Question:** What makes two strings anagrams?

Answer: Same characters with the same frequencies.

**Question:** What is the simplest approach?

Answer: Sort both strings and compare.

**Question:** What is the optimal approach for lowercase English letters?

Answer: Frequency counting using an integer array of size 26.

**Question:** What is the optimal complexity?

Answer: O(n) time and O(1) auxiliary space.

**Question:** Why increment one string and decrement the other?

Answer: Matching character frequencies cancel each other out, leaving all zeros.

---

### Final Takeaway

**Sorting compares the order after rearrangement. Frequency counting compares how many times each character occurs.**

Remember this pattern. It appears frequently in string-based interview problems.
