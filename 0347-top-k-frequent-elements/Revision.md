
# 🔥 LeetCode 347 — Top K Frequent Elements

## 📝 Problem Statement

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements.

The answer can be returned in any order.

**Important:** The solution should be better than sorting the entire array by frequency.

## 🧪 Examples

**Example 1**

Input:
```java
nums = [1,1,1,2,2,3], k = 2
```

Output:
```java
[1,2]
```

Explanation: `1` appears 3 times and `2` appears 2 times.

**Example 2**

Input:
```java
nums = [1], k = 1
```

Output:
```java
[1]
```

---

## 💡 Approach 1: Frequency Map + Sorting

### Idea

1. Count the frequency of each number using a `HashMap`.
2. Sort the distinct numbers by their frequency in descending order.
3. Select the first `k` numbers.

### Java Solution — Original Approach

```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        List<Integer> list = new ArrayList<>(map.keySet());

        list.sort((a, b) -> map.get(b) - map.get(a));

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = list.get(i);
        }

        return result;
    }
}
```

### 🔍 Step-by-Step Explanation

For `nums = [1,1,1,2,2,3]` and `k = 2`:

**Step 1 — Count frequencies**

| Number | Frequency |
|---:|---:|
| 1 | 3 |
| 2 | 2 |
| 3 | 1 |

**Step 2 — Sort by frequency descending**

```text
[1, 2, 3]
```

**Step 3 — Take the first `k = 2` elements**

```text
[1, 2]
```

### Complexity Analysis

Let:
- `n` = number of elements in `nums`
- `u` = number of distinct elements

| Complexity | Cost | Reason |
|---|---|---|
| Time | `O(n + u log u)` | Count frequencies, then sort distinct elements |
| Space | `O(u + k)` | Map, list, and result array |

Since `u` can be as large as `n`, the worst-case time complexity is **O(n log n)**.

---

## ⚡ Approach 2: Frequency Map + Bucket Sort (Optimized)

### 💡 Core Idea

A number cannot appear more than `n` times. Therefore, we can create buckets indexed by frequency.

For example:

```text
Frequency 1 → [3]
Frequency 2 → [2]
Frequency 3 → [1]
```

Then traverse the buckets from highest frequency to lowest and collect numbers until we have `k` elements.

### Java Solution — Optimized

```java
import java.util.*;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequency = new HashMap<>();

        // Step 1: Count frequencies
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        // Step 2: Create buckets indexed by frequency
        List<Integer>[] buckets = new List[nums.length + 1];

        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();

            if (buckets[count] == null) {
                buckets[count] = new ArrayList<>();
            }

            buckets[count].add(num);
        }

        // Step 3: Collect the k most frequent elements
        int[] result = new int[k];
        int index = 0;

        for (int freq = nums.length; freq >= 1 && index < k; freq--) {
            if (buckets[freq] != null) {
                for (int num : buckets[freq]) {
                    result[index++] = num;

                    if (index == k) {
                        break;
                    }
                }
            }
        }

        return result;
    }
}
```

### 🔍 Dry Run

Input:

```java
nums = [1,1,1,2,2,3], k = 2
```

**Step 1 — Frequency map**

```text
1 → 3
2 → 2
3 → 1
```

**Step 2 — Build buckets**

| Bucket index (frequency) | Elements |
|---:|---|
| 1 | `[3]` |
| 2 | `[2]` |
| 3 | `[1]` |
| 4–6 | Empty |

**Step 3 — Traverse backward**

| Frequency checked | Elements found | Result |
|---:|---|---|
| 6 | None | `[]` |
| 5 | None | `[]` |
| 4 | None | `[]` |
| 3 | `1` | `[1]` |
| 2 | `2` | `[1, 2]` |

We have collected `k = 2` elements, so we stop.

**Final output:**

```java
[1, 2]
```

### Complexity Analysis

| Complexity | Cost | Reason |
|---|---|---|
| Time | `O(n)` | Frequency counting, bucket creation, and bucket traversal |
| Space | `O(n)` | Frequency map and buckets |

The bucket approach avoids sorting the distinct numbers, giving **linear time complexity**.

---

## ⚖️ Original vs. Optimized

| Feature | Frequency Map + Sorting | Frequency Map + Buckets |
|---|---|---|
| Frequency counting | `O(n)` | `O(n)` |
| Ordering step | `O(u log u)` | `O(n)` bucket traversal |
| Overall time | `O(n + u log u)` | `O(n)` |
| Extra space | `O(u)` plus output | `O(n)` |
| Best when | Simplicity matters | Linear-time performance matters |

---

## 🎯 Interview Takeaways

- Use `HashMap` to count occurrences efficiently.
- Sorting by frequency is straightforward, but costs `O(u log u)`.
- Bucket sort exploits the fact that frequencies range from `1` to `n`.
- A min-heap of size `k` is another valid approach, typically taking `O(n + u log k)` time.
- Avoid comparator subtraction such as `map.get(b) - map.get(a)` in production code when values might risk integer overflow. Prefer:
  ```java
  list.sort((a, b) ->
      Integer.compare(map.get(b), map.get(a)));
  ```

## 🧠 Quick Summary

**Frequency Map + Sorting:** Count → Sort by frequency → Take `k`.

**Frequency Map + Bucket Sort:** Count → Group by frequency → Traverse from highest frequency → Take `k`.

Remember: when the possible frequency range is bounded by the input size, buckets can eliminate the need to sort.
