
# 217. Contains Duplicate

- **LeetCode:** https://leetcode.com/problems/contains-duplicate/
- **Difficulty:** Easy
- **Pattern:** Array, HashSet
- **Approach:** HashSet
- **Key Concept:** Duplicate detection using `Set.add()`

---

## 1. Problem Statement

Given an integer array `nums`, return `true` if any value appears at least twice in the array, and return `false` if every element is distinct.

### Examples

**Example 1:**

```text
Input: nums = [1, 2, 3, 1]
Output: true
```

Explanation: The element `1` appears twice.

**Example 2:**

```text
Input: nums = [1, 2, 3, 4]
Output: false
```

Explanation: Every element is distinct.

**Example 3:**

```text
Input: nums = [1, 1, 1, 3, 3, 4, 3, 2, 4, 2]
Output: true
```

Explanation: Multiple elements appear more than once.

---

## 2. Key Observation

We need to determine whether an element has already appeared while traversing the array.

A `HashSet` is ideal because:

- It stores only unique elements.
- It does not allow duplicate elements.
- It provides O(1) average-time insertion and lookup.

**Important:** We don't need to count how many times an element appears. We only need to know whether it has appeared before.

---

## 3. Optimal Approach: HashSet

### Intuition

Think of a HashSet as a collection of elements you've already visited.

For every element:

1. Try adding it to the set.
2. If insertion succeeds, it is a new element.
3. If insertion fails, the element already exists.
4. Return `true` immediately when a duplicate is found.

If the complete array is traversed without finding a duplicate, return `false`.

### Java Solution

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return true;
            }
        }

        return false;
    }
}
```

---

## 4. Important Concept: HashSet.add()

This is the most important part of the solution.

The `add()` method returns a boolean.

| Situation | Return value |
|---|---|
| Element is not present | `true` |
| Element is already present | `false` |

Example:

```java
Set<Integer> set = new HashSet<>();

System.out.println(set.add(10)); // true
System.out.println(set.add(20)); // true
System.out.println(set.add(10)); // false
```

After these operations:

```text
Set = [10, 20]
```

The duplicate `10` is not inserted again.

### Why use `!set.add(nums[i])`?

```java
if (!set.add(nums[i])) {
    return true;
}
```

Breaking it down:

- `set.add(nums[i])` returns `true` for a new element.
- It returns `false` for an existing element.
- `!` reverses the boolean result.

Therefore, the condition becomes true when a duplicate is found.

### Alternative implementation

```java
if (set.contains(nums[i])) {
    return true;
}

set.add(nums[i]);
```

Both approaches work.

However, `!set.add(nums[i])` is cleaner because it combines checking and insertion into one operation.

---

## 5. Dry Run

Input:

```text
nums = [1, 2, 3, 1]
```

Initially:

```text
set = {}
```

| Iteration | Element | `set.add()` | Set after operation |
|---|---:|---|---|
| 1 | 1 | true | [1] |
| 2 | 2 | true | [1, 2] |
| 3 | 3 | true | [1, 2, 3] |
| 4 | 1 | false | [1, 2, 3] |

At iteration 4:

```java
!set.add(1)
```

Becomes:

```java
!false
```

Which evaluates to:

```java
true
```

Therefore:

```text
Output: true
```

The method returns immediately without processing any remaining elements.

---

## 6. Complexity Analysis

Let `n` be the length of the input array.

### Time Complexity: O(n) average

- We traverse the array once.
- Each HashSet insertion takes O(1) average time.
- In the worst case, all elements are unique, so every element is inserted.

Therefore, average time complexity is O(n).

HashSet operations can degrade to O(n) in pathological collision scenarios, so the theoretical worst-case time for the complete algorithm can be O(n²).

### Space Complexity: O(n)

If every element is unique, all `n` elements are stored in the HashSet.

Therefore, auxiliary space complexity is O(n).

---

## 7. Alternative Approaches

### Approach 1: Brute Force

Compare every element with every other element.

```java
class Solution {
    public boolean containsDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }

        return false;
    }
}
```

Complexity:

- Time: O(n²)
- Space: O(1)

Disadvantage: Too slow for large arrays.

### Approach 2: Sorting

Sort the array and check adjacent elements.

```java
import java.util.Arrays;

class Solution {
    public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }

        return false;
    }
}
```

Example:

```text
Original: [4, 1, 3, 2, 1]

Sorted:   [1, 1, 2, 3, 4]

             ↑
        Adjacent duplicate
```

Complexity:

- Time: O(n log n)
- Auxiliary space: Depends on the sorting implementation.
- Important: Sorting modifies the original array.

### Approach 3: HashSet

Use a HashSet to detect duplicates during traversal.

Complexity:

- Average time: O(n)
- Space: O(n)

This is the recommended approach for this problem.

---

## 8. Approach Comparison

| Approach | Time | Space | Modifies Input? |
|---|---|---|---|
| Brute Force | O(n²) | O(1) | No |
| Sorting | O(n log n) | Depends on sort | Yes |
| HashSet | O(n) average | O(n) | No |

**Final recommendation:** HashSet is the best general-purpose solution when extra memory is allowed.

---

## 9. Edge Cases

| Input | Output | Reason |
|---|---|---|
| `[]` | false | Empty array |
| `[1]` | false | Only one element |
| `[1, 1]` | true | Duplicate exists |
| `[1, 2, 3, 4]` | false | All distinct |
| `[0, 0]` | true | Zero appears twice |
| `[-1, -2, -1]` | true | Negative duplicate |

---

## 10. Common Mistakes

### Mistake 1: Checking only the first occurrence

You must check every element until a duplicate is found or the array ends.

### Mistake 2: Using ArrayList instead of HashSet

An ArrayList allows duplicates and checking whether an element exists takes O(n) time.

Repeated membership checks can make the complete algorithm O(n²).

### Mistake 3: Performing two HashSet operations unnecessarily

Less efficient:

```java
if (set.contains(nums[i])) {
    return true;
}

set.add(nums[i]);
```

Cleaner:

```java
if (!set.add(nums[i])) {
    return true;
}
```

### Mistake 4: Forgetting early return

Once a duplicate is found, there is no reason to continue traversing the array.

---

## 11. Interview Follow-up Questions

1. What is a HashSet?
2. Why does HashSet not allow duplicate elements?
3. What does `HashSet.add()` return?
4. What happens internally when we insert a duplicate?
5. What is the average time complexity of HashSet insertion?
6. What is the worst-case time complexity?
7. What is the difference between HashSet and ArrayList?
8. Can you solve this problem without extra space?
9. Why does sorting help detect duplicates?
10. Does sorting modify the original array?
11. How does HashSet handle hash collisions?
12. What is the relationship between `equals()` and `hashCode()`?

---

## 12. Related LeetCode Problems

| Problem | Pattern |
|---|---|
| 242. Valid Anagram | Frequency Counting |
| 349. Intersection of Two Arrays | HashSet |
| 350. Intersection of Two Arrays II | HashMap / Frequency Counting |
| 128. Longest Consecutive Sequence | HashSet |
| 36. Valid Sudoku | HashSet |
| 219. Contains Duplicate II | HashSet + Sliding Window |

---

## 13. Quick Revision

**Q1. What data structure did we use?**

HashSet.

**Q2. Why HashSet?**

It stores unique elements and supports O(1) average-time insertion.

**Q3. What does `add()` return when an element already exists?**

`false`.

**Q4. Why use `!set.add(nums[i])`?**

To detect an element that already exists in the set.

**Q5. What is the average time complexity?**

O(n).

**Q6. What is the space complexity?**

O(n).

**Q7. Does this solution modify the original array?**

No.

---

## 14. Final Takeaway

**Pattern: HashSet for duplicate detection.**

Whenever a problem asks:

- Has this element appeared before?
- Are all elements unique?
- Does this collection contain duplicates?
- Have I already visited this value?

Think about using a HashSet.

Remember this one-liner:

```java
if (!set.add(element)) {
    return true;
}
```

It checks whether an element is new and inserts it in a single operation.
