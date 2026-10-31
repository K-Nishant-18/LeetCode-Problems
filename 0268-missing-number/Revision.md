
# 268. Missing Number

- **LeetCode:** https://leetcode.com/problems/missing-number/
- **Difficulty:** Easy
- **Pattern:** Array, Mathematics, Bit Manipulation
- **Primary Approach:** Mathematical Sum
- **Alternative Approach:** XOR
- **Time Complexity:** O(n)
- **Auxiliary Space:** O(1)

---

## 1. Problem Statement

Given an array `nums` containing `n` distinct numbers in the range `[0, n]`, return the only number in the range that is missing from the array.

### Examples

**Example 1:**

```text
Input: nums = [3, 0, 1]
Output: 2
```

**Example 2:**

```text
Input: nums = [0, 1]
Output: 2
```

**Example 3:**

```text
Input: nums = [9,6,4,2,3,5,7,0,1]
Output: 8
```

---

## 2. Key Observation

The array contains:

- Exactly `n` distinct numbers.
- Numbers from `0` to `n`.
- Exactly one missing number.

The complete range should contain:

```text
0, 1, 2, 3, ..., n
```

We can calculate the expected sum of this complete range and subtract the actual array sum.

The difference is the missing number.

---

## 3. Approach 1: Mathematical Sum (Your Solution)

### Intuition

The sum of the first `n` natural numbers is:

\[
S = \frac{n(n+1)}{2}
\]

Since our range starts at zero, the formula still gives the sum of all numbers from `0` to `n`.

Therefore:

\[
Missing = ExpectedSum - ActualSum
\]

### Java Solution

```java
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;

        int rangeSum = n * (n + 1) / 2;
        int sum = 0;

        for (int num : nums) {
            sum += num;
        }

        return rangeSum - sum;
    }
}
```

### Dry Run

Input:

```text
nums = [3, 0, 1]
```

**Step 1: Calculate n**

```text
n = 3
```

**Step 2: Calculate expected sum**

\[
ExpectedSum = \frac{3(3+1)}{2} = 6
\]

Complete range:

```text
0 + 1 + 2 + 3 = 6
```

**Step 3: Calculate actual sum**

```text
ActualSum = 3 + 0 + 1 = 4
```

**Step 4: Find the missing number**

```text
Missing = 6 - 4
        = 2
```

Output:

```text
2
```

---

## 4. Important Implementation Detail: Integer Overflow

Your original expression:

```java
int rangeSum = n * (n + 1) / 2;
```

Can overflow an `int` in problems with sufficiently large constraints because multiplication happens before division.

A safer general implementation uses `long`:

```java
class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;

        long expectedSum = (long) n * (n + 1) / 2;
        long actualSum = 0;

        for (int num : nums) {
            actualSum += num;
        }

        return (int) (expectedSum - actualSum);
    }
}
```

For the original LeetCode constraints, the `int` implementation is safe. The `long` version is more robust.

---

## 5. Approach 2: XOR (Alternative Optimal Solution)

### Important XOR Properties

XOR is represented by the `^` operator in Java.

Remember these properties:

\[
a \oplus a = 0
\]

\[
a \oplus 0 = a
\]

\[
a \oplus b \oplus a = b
\]

XOR is useful because identical numbers cancel each other out.

### Intuition

The complete range is:

```text
0, 1, 2, 3
```

The given array is:

```text
3, 0, 1
```

If we XOR all numbers from the complete range with all array elements, every number appearing in both collections cancels out.

Only the missing number remains.

### Java Solution

```java
class Solution {
    public int missingNumber(int[] nums) {
        int xor = nums.length;

        for (int i = 0; i < nums.length; i++) {
            xor ^= i ^ nums[i];
        }

        return xor;
    }
}
```

### Dry Run

Input:

```text
nums = [3, 0, 1]
```

Initially:

```text
xor = 3
```

The loop performs:

```text
xor = 3 ^ 0 ^ 3 = 0
xor = 0 ^ 1 ^ 0 = 1
xor = 1 ^ 2 ^ 1 = 2
```

Final result:

```text
2
```

The duplicate values cancel out, leaving the missing number.

---

## 6. Approach 3: Sorting (Your Initial Idea)

### Intuition

After sorting, every number should match its index.

Example:

```text
Input:  [3, 0, 1]

Sorted: [0, 1, 3]

Index:   0  1  2
Value:   0  1  3
               ↑
        Missing number = 2
```

### Java Solution

```java
import java.util.Arrays;

class Solution {
    public int missingNumber(int[] nums) {
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i) {
                return i;
            }
        }

        return nums.length;
    }
}
```

The final return handles the case where the missing number is `n`.

Example:

```text
nums = [0, 1]
```

There is no mismatch in the loop, so the answer is `2`.

---

## 7. Complexity Comparison

| Approach | Time | Auxiliary Space |
|---|---|---|
| Sorting | O(n log n) | Depends on sorting implementation |
| Mathematical Sum | O(n) | O(1) |
| XOR | O(n) | O(1) |

**Recommendation:** Use the mathematical sum approach as your primary solution. Understand XOR as an important alternative.

---

## 8. Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `[3,0,1]` | 2 | Middle number missing |
| `[0,1]` | 2 | Last number missing |
| `[1]` | 0 | First number missing |
| `[0]` | 1 | Last number missing |
| `[9,6,4,2,3,5,7,0,1]` | 8 | Middle number missing |
| `[0,1,2,3]` | 4 | Last number missing |

---

## 9. Common Mistakes

1. Forgetting that the range starts at zero.
2. Returning the wrong value when the missing number is `n`.
3. Forgetting the possibility of integer overflow in the sum formula.
4. Sorting the array and assuming the missing number must be inside the array.
5. Confusing XOR (`^`) with exponentiation. In Java, `^` means bitwise XOR.

---

## 10. Interview Explanation

"I know that the array contains `n` distinct values from the range zero to n, with exactly one number missing.

I calculate the expected sum of the complete range using the formula n times n plus one divided by two.

Then I calculate the actual sum of the given array in one traversal.

The difference between the expected sum and actual sum gives the missing number.

This solution takes O(n) time and O(1) auxiliary space."

---

## 11. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| 136. Single Number | XOR |
| 268. Missing Number | Mathematics / XOR |
| 287. Find the Duplicate Number | Array / Cycle Detection |
| 448. Find All Numbers Disappeared in an Array | Array / Index Marking |
| 41. First Missing Positive | Array / Index Placement |
| 645. Set Mismatch | Array / Mathematics |

---

## 12. Quick Revision

**Q1. What is the expected sum formula?**

\[
n(n+1)/2
\]

**Q2. How do we find the missing number?**

Expected sum minus actual sum.

**Q3. What is the time complexity?**

O(n).

**Q4. What is the auxiliary space complexity?**

O(1).

**Q5. What is the XOR cancellation property?**

\[
a \oplus a = 0
\]

**Q6. Why does the XOR approach work?**

All numbers that appear in both the complete range and input array cancel out, leaving only the missing number.

### Key Takeaway

Whenever a problem involves a missing number from a known consecutive range, think about:

- Mathematical sum.
- XOR cancellation.
- Sorting and index comparison.

Look for mathematical properties before choosing an additional data structure.
