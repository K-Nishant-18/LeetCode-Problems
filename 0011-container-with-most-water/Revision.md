
# LeetCode 11: Container With Most Water

- **Difficulty:** Medium
- **Pattern:** Two Pointers + Greedy
- **Language:** Java
- **Problem:** [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)

---

## 1. Problem Statement

You are given an integer array `height` of length `n`.

There are `n` vertical lines drawn such that the two endpoints of the `i-th` line are `(i, 0)` and `(i, height[i])`.

Find two lines that, together with the x-axis, form a container that holds the maximum amount of water.

Return the maximum amount of water the container can store.

### Example 1

**Input:**
```text
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
```

**Output:**
```text
49
```

**Explanation:**

Choose the lines at indices `1` and `8` (0-based indexing).

- Left height = 8
- Right height = 7
- Width = 8 - 1 = 7
- Water level = min(8, 7) = 7

Area:

```text
7 * 7 = 49
```

### Example 2

**Input:**
```text
height = [1, 1]
```

**Output:**
```text
1
```

---

## 2. Core Intuition

Imagine two walls holding water.

The amount of water depends on two things:

1. The distance between the walls (width).
2. The height of the shorter wall.

The shorter wall determines the maximum water level. Water cannot rise above it because it would overflow.

Therefore:

**Area = Width × Minimum Height**

Mathematically:

\[
Area = (j-i)\times\min(height[i],height[j])
\]

We need to find the maximum possible area.

---

## 3. Two-Pointer Approach

We use two pointers:

- `i`: Starts at the beginning of the array.
- `j`: Starts at the end of the array.

Initially:

```java
int i = 0;
int j = height.length - 1;
int maxVol = 0;
```

At every iteration:

1. Calculate the current container area.
2. Update the maximum area.
3. Move the pointer pointing to the shorter wall.
4. Continue until both pointers meet.

### Why move the shorter wall?

Suppose:

```text
Left height  = 3
Right height = 7
Width        = 5
```

Current area:

```text
min(3, 7) * 5 = 15
```

The shorter wall limits the water level to 3.

If we move the taller wall:
- Width decreases.
- The limiting height cannot exceed 3 because the left wall is still 3.
- Therefore, the area cannot improve.

If we move the shorter wall:
- Width decreases.
- But we might find a taller wall.
- This gives us a chance to increase the area.

**Key rule: Always move the shorter wall.**

If both walls have equal heights, moving either one is valid.

---

## 4. Your Final Java Solution

```java
class Solution {
    public int maxArea(int[] height) {
        int i = 0;
        int j = height.length - 1;
        int maxVol = 0;

        while (i < j) {

            int volume = Math.min(height[i], height[j]) * (j - i);

            maxVol = Math.max(maxVol, volume);

            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }

        return maxVol;
    }
}
```

**Status:** Correct optimal solution.

---

## 5. Step-by-Step Explanation

### Step 1: Initialize pointers

```java
int i = 0;
int j = height.length - 1;
```

Start with the widest possible container.

### Step 2: Calculate the area

```java
int volume = Math.min(height[i], height[j]) * (j - i);
```

- `Math.min()` finds the shorter wall.
- `j - i` calculates the width.

### Step 3: Update maximum area

```java
maxVol = Math.max(maxVol, volume);
```

Keep track of the largest area found so far.

### Step 4: Move the shorter wall

```java
if (height[i] < height[j]) {
    i++;
} else {
    j--;
}
```

Moving the shorter wall gives us a chance to find a taller boundary.

### Step 5: Return the answer

```java
return maxVol;
```

When the pointers meet, no more pairs remain to examine.

---

## 6. Dry Run

Input:

```text
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
```

Initially:

```text
i = 0
j = 8
maxVol = 0
```

| Step | `i` | `j` | Left height | Right height | Width | Area | Action |
|---:|---:|---:|---:|---:|---:|---:|---|
| 1 | 0 | 8 | 1 | 7 | 8 | 8 | Move `i` |
| 2 | 1 | 8 | 8 | 7 | 7 | 49 | Move `j` |
| 3 | 1 | 7 | 8 | 3 | 6 | 18 | Move `j` |
| 4 | 1 | 6 | 8 | 8 | 5 | 40 | Move either; code moves `j` |
| 5 | 1 | 5 | 8 | 4 | 4 | 16 | Move `j` |
| 6 | 1 | 4 | 8 | 5 | 3 | 15 | Move `j` |
| 7 | 1 | 3 | 8 | 2 | 2 | 4 | Move `j` |
| 8 | 1 | 2 | 8 | 6 | 1 | 6 | Move `j` |

Maximum area:

```text
maxVol = 49
```

**Output:**
```text
49
```

---

## 7. Why Does This Algorithm Work?

At any point, we have a pair of boundaries.

The shorter boundary determines the water height.

Suppose the left wall is shorter:

```text
height[i] < height[j]
```

If we keep the left wall fixed and move the right wall inward:

- The width decreases.
- The new right wall might be taller or shorter.
- But the water height cannot exceed `height[i]`.

Therefore, the new area cannot be greater than the current area.

So there is no reason to keep the shorter left wall. We can safely discard it by moving `i` forward.

The same reasoning applies when the right wall is shorter.

This greedy decision lets us eliminate one boundary at every iteration without missing a better answer.

---

## 8. Complexity Analysis

Let `n` be the length of the height array.

- **Time Complexity:** `O(n)`
- **Auxiliary Space Complexity:** `O(1)`

### Why O(n) time?

Each iteration moves either `i` forward or `j` backward.

Both pointers move only inward, and each can move at most `n - 1` times.

Therefore, the total number of iterations is linear.

### Why O(1) space?

We only use three integer variables:

- `i`
- `j`
- `maxVol`

No additional array, list, or collection is created.

---

## 9. Brute Force vs Optimal Approach

### Brute Force

Check every possible pair of boundaries.

```java
for (int i = 0; i < height.length; i++) {
    for (int j = i + 1; j < height.length; j++) {
        int area = Math.min(height[i], height[j]) * (j - i);
        maxVol = Math.max(maxVol, area);
    }
}
```

Complexity:

- Time: `O(n²)`
- Auxiliary Space: `O(1)`

### Two Pointers

Start from opposite ends and eliminate the shorter wall at every step.

Complexity:

- Time: `O(n)`
- Auxiliary Space: `O(1)`

The two-pointer approach is optimal for this problem.

---

## 10. Common Mistakes

### Mistake 1: Moving the taller wall

Incorrect:

```java
if (height[i] > height[j]) {
    i++;
} else {
    j--;
}
```

This moves the taller wall, which does not help improve the limiting height.

### Mistake 2: Using the maximum height

Incorrect:

```java
int area = Math.max(height[i], height[j]) * (j - i);
```

The shorter wall determines the water level.

Correct:

```java
int area = Math.min(height[i], height[j]) * (j - i);
```

### Mistake 3: Incorrect width calculation

Incorrect:

```java
int width = j - i + 1;
```

The distance between two indices is `j - i`, not `j - i + 1`.

### Mistake 4: Moving both pointers

Moving both pointers at the same time can skip potentially optimal pairs.

Move only the shorter wall.

### Mistake 5: Comparing indices instead of heights

Incorrect:

```java
if (i < j)
```

The loop already guarantees `i < j`. Pointer movement must depend on:

```java
height[i] < height[j]
```

---

## 11. Interview Explanation

"I use the two-pointer greedy approach. Initially, I place one pointer at the beginning and another at the end to get the maximum possible width. At every iteration, I calculate the area using the minimum of the two heights multiplied by the distance between the pointers. I update the maximum area and move the pointer corresponding to the shorter wall. This is because the shorter wall limits the water level, and moving the taller wall cannot improve that limiting height. I continue until the pointers meet. The algorithm takes O(n) time and O(1) auxiliary space."

---

## 12. Revision Questions

Try answering these without looking at the solution.

1. What determines the amount of water a container can hold?
2. Why do we use `Math.min()` instead of `Math.max()`?
3. Why do we start with pointers at opposite ends?
4. Why do we move the shorter wall?
5. Why is moving the taller wall not useful?
6. Why do we move only one pointer per iteration?
7. Why is the time complexity O(n)?
8. Why is the auxiliary space O(1)?
9. What happens when both walls have equal heights?
10. Why is this considered a greedy two-pointer problem?

---

## 13. Related Problems

Practice these to strengthen your understanding of two pointers and greedy decisions:

1. [LeetCode 167: Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)
2. [LeetCode 15: 3Sum](https://leetcode.com/problems/3sum/)
3. [LeetCode 42: Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)
4. [LeetCode 125: Valid Palindrome](https://leetcode.com/problems/valid-palindrome/)

---

## 14. Key Takeaways

- **Pattern:** Two Pointers + Greedy.
- **Starting positions:** Opposite ends of the array.
- **Area formula:** `min(height[i], height[j]) * (j - i)`.
- **Pointer movement:** Always move the shorter wall.
- **Time Complexity:** `O(n)`.
- **Auxiliary Space:** `O(1)`.

**Remember:**

> The shorter wall limits the water. Move it, because only that movement gives you a chance to improve the limiting height while reducing the width.
