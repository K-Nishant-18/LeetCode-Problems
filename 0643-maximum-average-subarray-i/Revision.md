
# LeetCode 643 — Maximum Average Subarray I

## 1. Problem Statement

Given an integer array `nums` consisting of `n` elements and an integer `k`, find a contiguous subarray of length exactly `k` that has the maximum average value.

Return the maximum average.

**Important:** The subarray must contain exactly `k` consecutive elements.

## 2. Examples

### Example 1

**Input:**
```java
nums = [1, 12, -5, -6, 50, 3]
k = 4
```

**Output:**
```java
12.75
```

Explanation:

Possible windows:

| Subarray | Sum | Average |
|---|---:|---:|
| `[1, 12, -5, -6]` | 2 | 0.5 |
| `[12, -5, -6, 50]` | 51 | 12.75 |
| `[-5, -6, 50, 3]` | 42 | 10.5 |

Maximum average = `51 / 4 = 12.75`.

### Example 2

**Input:**
```java
nums = [5]
k = 1
```

**Output:**
```java
5.0
```

Explanation: Only one window exists, containing the single element `5`.

---

## 3. Pattern Used

**Fixed-Size Sliding Window**

This problem is a classic fixed-size sliding-window problem.

### How to recognize this pattern?

Look for these clues:

- The problem asks for a contiguous subarray.
- The subarray must have exactly `k` elements.
- We need to calculate a sum or average.
- We need the maximum or minimum among all such windows.

Whenever you see these conditions, consider Fixed-Size Sliding Window.

---

## 4. Intuition

Imagine a window containing exactly `k` elements moving across an array.

Instead of calculating the sum of every window from scratch, we maintain a running sum.

When the window moves one position to the right:

1. Add the new element entering the window.
2. Remove the element leaving the window.
3. Update the maximum sum.

The window size always remains `k`.

### Sliding Window Formula

```text
New Window Sum =
Previous Window Sum
+ Incoming Element
- Outgoing Element
```

This avoids repeatedly calculating the sum of all `k` elements.

---

## 5. Final Java Solution

```java
class Solution {
    public double findMaxAverage(int[] nums, int k) {

        double sum = 0;

        // Step 1: Calculate the first window sum.
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        // Step 2: Initialize maximum with a valid window.
        double maxSum = sum;

        // Step 3: Slide the window.
        for (int i = k; i < nums.length; i++) {

            sum += nums[i];       // Add incoming element
            sum -= nums[i - k];   // Remove outgoing element

            maxSum = Math.max(maxSum, sum);
        }

        // Step 4: Return maximum average.
        return maxSum / k;
    }
}
```

---

## 6. Understanding the Code

### Step 1: Calculate the first window

```java
double sum = 0;

for (int i = 0; i < k; i++) {
    sum += nums[i];
}
```

We calculate the sum of the first `k` elements.

Why?

We need a complete window before comparing its sum with other windows.

### Step 2: Initialize maximum sum

```java
double maxSum = sum;
```

This is important.

We initialize `maxSum` using the first valid window instead of zero.

Why?

The array can contain negative numbers. If every valid window has a negative sum, initializing the maximum to zero would produce an incorrect result.

### Step 3: Slide the window

```java
for (int i = k; i < nums.length; i++) {
    sum += nums[i];
    sum -= nums[i - k];

    maxSum = Math.max(maxSum, sum);
}
```

For every new position:

- `nums[i]` enters the window.
- `nums[i-k]` leaves the window.
- Compare the updated sum with the maximum.

### Step 4: Calculate average

```java
return maxSum / k;
```

Since every valid window contains exactly `k` elements, maximizing the sum also maximizes the average.

---

## 7. Step-by-Step Dry Run

**Input:**

```java
nums = [1, 12, -5, -6, 50, 3]
k = 4
```

### Step 1: First window

```text
Window = [1, 12, -5, -6]

Sum = 1 + 12 - 5 - 6
Sum = 2

maxSum = 2
```

### Step 2: Move window right

Incoming element = `50`

Outgoing element = `1`

```text
sum = 2 + 50 - 1
sum = 51

maxSum = 51
```

New window:

```text
[12, -5, -6, 50]
```

### Step 3: Move window right again

Incoming element = `3`

Outgoing element = `12`

```text
sum = 51 + 3 - 12
sum = 42

maxSum = max(51, 42)
maxSum = 51
```

New window:

```text
[-5, -6, 50, 3]
```

### Dry-run table

| Window | Incoming | Outgoing | Sum | Max Sum |
|---|---:|---:|---:|---:|
| `[1,12,-5,-6]` | — | — | 2 | 2 |
| `[12,-5,-6,50]` | 50 | 1 | 51 | 51 |
| `[-5,-6,50,3]` | 3 | 12 | 42 | 51 |

Final calculation:

```text
Maximum Average = 51 / 4

Answer = 12.75
```

---

## 8. Why Does This Approach Work?

Consider two consecutive windows:

```text
Window 1: [1, 2, 3, 4]
Window 2:    [2, 3, 4, 5]
```

The common elements are:

```text
2, 3, 4
```

Only two elements change:

- `1` leaves.
- `5` enters.

Therefore:

```text
Window 2 Sum = Window 1 Sum - 1 + 5
```

We don't need to calculate the entire window sum again.

This is why sliding window improves the performance.

---

## 9. Complexity Analysis

Let `n` be the length of the array.

### Time Complexity: O(n)

- First loop processes `k` elements.
- Second loop processes the remaining `n-k` elements.
- Each element is processed a constant number of times.

Total:

```text
O(k) + O(n-k) = O(n)
```

### Auxiliary Space Complexity: O(1)

We use only:
- `sum`
- `maxSum`
- Loop variable

No additional data structure is required.

---

## 10. Common Mistakes

### Mistake 1: Initializing maximum sum to zero

Incorrect:

```java
double maxSum = 0;
```

Why?

All valid window sums could be negative.

Correct:

```java
double maxSum = sum;
```

Initialize using the first complete window.

### Mistake 2: Updating maximum before the first complete window

Incorrect logic:

```java
if (sum > maxSum) {
    maxSum = sum;
}
```

If this is executed while building the first window, partial sums may incorrectly become candidates.

Only compare complete windows.

### Mistake 3: Recalculating the entire window sum

Inefficient approach:

```java
for (int i = 0; i <= nums.length - k; i++) {
    int sum = 0;

    for (int j = i; j < i + k; j++) {
        sum += nums[j];
    }
}
```

This takes O(n*k) time.

Use the sliding-window update to achieve O(n).

### Mistake 4: Confusing sum and average

You don't need to calculate the average for every window.

Since `k` is constant:

```text
Maximum Average = Maximum Window Sum / k
```

Maximizing the sum automatically maximizes the average.

---

## 11. Interview Explanation

"I use the fixed-size sliding-window technique because the problem asks for a contiguous subarray of exactly k elements.

First, I calculate the sum of the first k elements and initialize my maximum sum with that value.

Then I slide the window across the array. For each movement, I add the incoming element and subtract the outgoing element from the running sum.

I update the maximum sum after every complete window.

Finally, I divide the maximum sum by k to obtain the maximum average.

This approach takes O(n) time and O(1) auxiliary space, which is optimal because we must examine every element."

---

## 12. Related Problems

| Problem | Pattern |
|---|---|
| 643. Maximum Average Subarray I | Fixed-Size Sliding Window |
| 219. Contains Duplicate II | Sliding Window + HashSet |
| 1456. Maximum Number of Vowels in a Substring of Given Length | Fixed-Size Sliding Window |
| 1343. Number of Sub-arrays of Size K and Average Greater Than or Equal to Threshold | Fixed-Size Sliding Window |
| 2461. Maximum Sum of Distinct Subarrays With Length K | Sliding Window + HashSet |
| 239. Sliding Window Maximum | Sliding Window + Monotonic Deque |

---

## 13. Quick Revision

Remember these five steps:

1. Calculate the first window sum.
2. Initialize `maxSum` using the first complete window.
3. Add the incoming element.
4. Remove the outgoing element.
5. Update the maximum and divide by `k`.

**Core code:**

```java
for (int i = k; i < nums.length; i++) {
    sum += nums[i];
    sum -= nums[i - k];
    maxSum = Math.max(maxSum, sum);
}
```

**Pattern:** Fixed-Size Sliding Window

**Time Complexity:** O(n)

**Auxiliary Space:** O(1)

**Key lesson:** When the window size is fixed, reuse the previous window's calculation instead of starting from scratch.
