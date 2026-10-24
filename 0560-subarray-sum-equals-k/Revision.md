
# 🐞 Problem Statement

**LeetCode 560 — Subarray Sum Equals K**

**Difficulty:** Medium  
**Topics:** Array, HashMap, Prefix Sum

Given an array of integers `nums` and an integer `k`, return the total number of **contiguous subarrays** whose sum equals `k`.

A subarray must contain consecutive elements.

## 📌 Examples

### Example 1

```text
Input:  nums = [1, 1, 1], k = 2
Output: 2

Explanation:
The subarrays [1, 1] at indices 0–1 and 1–2
both have a sum of 2.
```

### Example 2

```text
Input:  nums = [1, 2, 3], k = 3
Output: 2

Explanation:
[1, 2] is not valid because its sum is 3? 
Correction: [1, 2] sums to 3, and [3] also sums to 3.
Output: 2
```

---

## 📟 Solution 1 — Your Original Approach

**Approach:** Brute Force

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum += nums[j];

                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }
}
```

## 🎀 Explanation

1. The outer loop selects each possible starting index `i`.
2. Initialize `sum = 0` for each starting index.
3. The inner loop extends the subarray from `i` to `j`.
4. Add each new element to `sum`.
5. If `sum == k`, increment `count`.
6. Return the total count.

### 🔍 Dry Run

Input:

```text
nums = [1, 2, 1]
k = 3
```

| Starting index `i` | Ending index `j` | Subarray | Sum | Count |
|---:|---:|---|---:|---:|
| 0 | 0 | `[1]` | 1 | 0 |
| 0 | 1 | `[1, 2]` | 3 | 1 |
| 0 | 2 | `[1, 2, 1]` | 4 | 1 |
| 1 | 1 | `[2]` | 2 | 1 |
| 1 | 2 | `[2, 1]` | 3 | 2 |
| 2 | 2 | `[1]` | 1 | 2 |

Output: `2`

## ⏱️ Complexity Analysis

- **Time:** `O(n²)` — examines every possible contiguous subarray.
- **Auxiliary Space:** `O(1)` — uses only a few variables.

---

## 🚀 Solution 2 — Optimized Approach

**Approach:** Prefix Sum + HashMap

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        int currentSum = 0;
        int count = 0;

        for (int num : nums) {
            currentSum += num;

            int required = currentSum - k;
            count += map.getOrDefault(required, 0);

            map.put(
                currentSum,
                map.getOrDefault(currentSum, 0) + 1
            );
        }

        return count;
    }
}
```

## 🎀 Explanation

### 1. Initialize the HashMap

```java
map.put(0, 1);
```

This records an initial prefix sum of zero. It allows us to count subarrays that start at index `0`.

### 2. Calculate the running prefix sum

```java
currentSum += num;
```

`currentSum` is the sum of all elements processed so far.

### 3. Find the required previous prefix sum

The key formula is:

\[
\text{required}=\text{currentSum}-k
\]

If an earlier prefix sum equals `currentSum - k`, the elements between that prefix and the current position sum to `k`.

### 4. Add the number of matching prefix sums

```java
count += map.getOrDefault(required, 0);
```

The map stores **frequencies**, not just whether a prefix sum exists. If the same prefix sum appeared multiple times, each occurrence identifies another valid subarray.

### 5. Record the current prefix sum

```java
map.put(
    currentSum,
    map.getOrDefault(currentSum, 0) + 1
);
```

Record the current prefix sum so future elements can use it.

## 🔍 Optimized Dry Run

Input:

```text
nums = [1, 2, 1]
k = 3
```

Initial state:

```text
map = {0=1}
currentSum = 0
count = 0
```

| Number | Current sum | Required sum | Matches in map | Count after | Map update |
|---:|---:|---:|---:|---:|---|
| 1 | 1 | -2 | 0 | 0 | Record `1` |
| 2 | 3 | 0 | 1 | 1 | Record `3` |
| 1 | 4 | 1 | 1 | 2 | Record `4` |

Output: `2`

## ⏱️ Complexity Analysis

- **Time:** `O(n)` expected — one pass through the array with expected constant-time HashMap operations.
- **Auxiliary Space:** `O(n)` — the map may contain up to `n + 1` distinct prefix sums.

---

## 📊 Approach Comparison

| Feature | Brute Force | Prefix Sum + HashMap |
|---|---|---|
| Time | `O(n²)` | `O(n)` expected |
| Auxiliary Space | `O(1)` | `O(n)` |
| Handles negative numbers | Yes | Yes |
| Interview preference | Good baseline | Optimal for general inputs |

---

## 🧠 Summary of the Idea

### Brute Force

- Choose every starting index.
- Extend the subarray and maintain a running sum.
- Count whenever the sum equals `k`.

### Optimized

- Maintain a running prefix sum.
- Calculate `currentSum - k`.
- Add the frequency of that required prefix sum to the answer.
- Record the current prefix sum in the HashMap.

### 🔑 Memory Trick

**Current prefix sum − target = required previous prefix sum.**

---

## 🎯 Interview Takeaways

- A subarray is contiguous.
- Negative numbers make a standard sliding-window approach unreliable.
- Store prefix-sum frequencies, not just prefix-sum values.
- Initialize the map with `{0=1}` to handle subarrays beginning at index `0`.
- Check for the required prefix sum before recording the current prefix sum.
- The optimized solution takes `O(n)` expected time and `O(n)` auxiliary space.
