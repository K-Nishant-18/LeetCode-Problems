
# LeetCode 219 — Contains Duplicate II

## 1. Problem Statement

Given an integer array `nums` and an integer `k`, return `true` if there are two distinct indices `i` and `j` such that:

- `nums[i] == nums[j]`
- `abs(i - j) <= k`

Otherwise, return `false`.

The important condition is that duplicate values must occur within a maximum index distance of `k`.

## 2. Examples

### Example 1

**Input:**
```java
nums = [1, 2, 3, 1], k = 3
```

**Output:**
```java
true
```

Explanation: `nums[0]` and `nums[3]` are both `1`, and their index distance is `3`.

### Example 2

**Input:**
```java
nums = [1, 0, 1, 1], k = 1
```

**Output:**
```java
true
```

Explanation: The duplicate `1` values at indices `2` and `3` have a distance of `1`.

### Example 3

**Input:**
```java
nums = [1, 2, 3, 1, 2, 3], k = 2
```

**Output:**
```java
false
```

Explanation: Duplicate values exist, but none occur within a distance of `2`.

---

## 3. Pattern Used

**Sliding Window + HashSet**

We use:
- `HashSet` to detect duplicates efficiently.
- Sliding Window to maintain only the elements within distance `k`.

### Core idea

At index `i`, the set should contain elements from the previous `k` indices:

```text
[i-k, i-1]
```

We check whether the current element already exists in this window.

If it exists, we have found a nearby duplicate.

Otherwise, we insert it.

---

## 4. Intuition

Imagine checking the last `k` people who entered a room.

A person is suspicious only if someone with the same ID is already among those last `k` people.

As we move forward:
1. Remove the person who is now outside the allowed range.
2. Check whether the current person's ID already exists.
3. Add the current person.

This is exactly what our sliding window does.

---

## 5. Final Java Solution

```java
import java.util.*;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {

        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {

            // Remove the element outside the valid window.
            if (i > k) {
                set.remove(nums[i - k - 1]);
            }

            // Check for duplicate and insert if absent.
            if (!set.add(nums[i])) {
                return true;
            }
        }

        return false;
    }
}
```

### Why does `set.add()` work?

Java's `HashSet.add()` returns:

| Situation | Return value |
|---|---|
| Element is new | `true` |
| Element already exists | `false` |

Therefore:

```java
if (!set.add(nums[i])) {
    return true;
}
```

Combines duplicate detection and insertion in one operation.

---

## 6. Understanding the Window Boundary

This is the most important part of the problem.

At index `i`, we want to retain the previous `k` elements.

The valid previous indices are:

```text
i-k, i-k+1, ..., i-1
```

The index that becomes invalid is:

```text
i-k-1
```

Therefore, remove:

```java
set.remove(nums[i - k - 1]);
```

But only when:

```java
i > k
```

### Why `i > k`?

Consider `k = 3`.

| Current index | Previous valid indices | Remove? |
|---:|---|---|
| 0 | None | No |
| 1 | 0 | No |
| 2 | 0, 1 | No |
| 3 | 0, 1, 2 | No |
| 4 | 1, 2, 3 | Remove index 0 |
| 5 | 2, 3, 4 | Remove index 1 |

At index `3`, index `0` must remain because:

```text
3 - 0 = 3
```

The distance is exactly `k`, so it is valid.

At index `4`:

```text
4 - 0 = 4
```

Now it is outside the allowed range.

---

## 7. Dry Run

**Input:**

```java
nums = [1, 2, 3, 1]
k = 3
```

Initially:

```text
Set = {}
```

| Index | Current | Removal | Set before duplicate check | Result |
|---:|---:|---|---|---|
| 0 | 1 | None | `{}` | Add 1 |
| 1 | 2 | None | `{1}` | Add 2 |
| 2 | 3 | None | `{1, 2}` | Add 3 |
| 3 | 1 | None | `{1, 2, 3}` | Duplicate found |

At index `3`:

```java
!set.add(1)
```

Since `1` already exists, `add()` returns `false`.

Therefore:

```java
return true;
```

Final output:

```java
true
```

---

## 8. Edge Cases

| Input | Expected output | Reason |
|---|---|---|
| `[1,2,3,1], k=3` | `true` | Distance exactly `k` |
| `[1,2,3,1], k=2` | `false` | Duplicate too far away |
| `[1,0,1,1], k=1` | `true` | Adjacent duplicates |
| `[1,2,3], k=0` | `false` | Different indices cannot have distance zero |
| `[1], k=1` | `false` | Only one element |
| `[1,1], k=1` | `true` | Adjacent duplicate |

---

## 9. Complexity Analysis

Let `n` be the length of the array.

### Time Complexity: O(n) average

- We traverse the array once.
- Each insertion and removal in a HashSet takes O(1) average time.
- Each element is processed once.

Therefore:

```text
O(n)
```

### Space Complexity: O(min(n, k+1))

The set stores at most `k+1` elements, or all `n` elements if the array is shorter.

Therefore:

```text
O(min(n, k+1))
```

---

## 10. Common Mistakes

### Mistake 1: Keeping the wrong window size

A window of only `k` elements before checking the current element is correct.

But remember that the current element itself is not yet in the set.

### Mistake 2: Removing too early

Removing index `i-k` would incorrectly discard an element whose distance from the current index is exactly `k`.

Correct removal:

```java
nums[i - k - 1]
```

### Mistake 3: Incorrect boundary condition

Incorrect:

```java
if (i - k > 0)
```

Correct:

```java
if (i > k)
```

### Mistake 4: Redundant HashSet insertion

Unnecessary:

```java
if (!set.add(nums[i])) {
    return true;
} else {
    set.add(nums[i]);
}
```

`add()` already inserts a new element.

---

## 11. Interview Explanation

"I solve this problem using a sliding window and a HashSet.

The HashSet helps me detect duplicates in O(1) average time, while the sliding window ensures that I only consider elements within distance `k`.

I iterate through the array. Before checking the current element, I remove the element that has moved outside the valid window. Then I attempt to insert the current element into the set.

If insertion fails, the element already exists in the window, which means we found two equal values whose index difference is at most `k`.

If the entire array is processed without finding a duplicate, I return false.

The overall average time complexity is O(n), and the space complexity is O(min(n, k+1))."

---

## 12. Related Problems

| Problem | Pattern |
|---|---|
| 217. Contains Duplicate | HashSet |
| 219. Contains Duplicate II | Sliding Window + HashSet |
| 220. Contains Duplicate III | Sliding Window + Ordered Set / Bucket |
| 3. Longest Substring Without Repeating Characters | Sliding Window + HashSet |
| 438. Find All Anagrams in a String | Sliding Window + Frequency Map |

---

## 13. Quick Revision

Remember these points:

1. Use a HashSet to detect duplicates.
2. Maintain only the previous `k` elements.
3. Remove the outdated element before checking the current one.
4. Use `!set.add(nums[i])` for duplicate detection.
5. Do not remove an element whose distance is exactly `k`.

**Core code:**

```java
if (i > k) {
    set.remove(nums[i - k - 1]);
}

if (!set.add(nums[i])) {
    return true;
}
```

**Pattern:** Sliding Window + HashSet  
**Time:** O(n) average  
**Space:** O(min(n, k+1))
