
# 🐞 Problem Statement

**LeetCode 229 — Majority Element II**

**Difficulty:** Medium  
**Topics:** Array, HashMap, Counting, Boyer–Moore Voting Algorithm

Given an integer array `nums` of size `n`, return all elements that appear **more than** `⌊n / 3⌋` times.

The answer can contain at most two elements.

## 📌 Examples

### Example 1

```text
Input:  nums = [3, 2, 3]
Output: [3]
```

### Example 2

```text
Input:  nums = [1]
Output: [1]
```

### Example 3

```text
Input:  nums = [1, 2]
Output: [1, 2]
```

---

## 📟 Your Logical Code — HashMap Approach

```java
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();

        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        map.forEach((key, value) -> {
            if (value > nums.length / 3) {
                list.add(key);
            }
        });

        return list;
    }
}
```

### 🎀 Explanation

**Step 1: Count frequencies**

```java
for (int n : nums) {
    map.put(n, map.getOrDefault(n, 0) + 1);
}
```

For each number:
- If it already exists in the map, increase its count.
- Otherwise, initialize its count to `1`.

**Step 2: Find majority elements**

```java
map.forEach((key, value) -> {
    if (value > nums.length / 3) {
        list.add(key);
    }
});
```

Check each number's frequency. Add it to the answer only if its count is strictly greater than `nums.length / 3`.

**Step 3: Return the result**

```java
return list;
```

The result contains every number satisfying the frequency condition.

---

## 🔍 Dry Run — HashMap Approach

Input:

```text
nums = [1, 2, 1, 1, 2, 2, 3]
n = 7
```

The threshold is:

```text
n / 3 = 7 / 3 = 2
```

An element must appear **more than 2 times**, meaning at least 3 times.

### Step 1: Build the frequency map

| Number processed | Frequency map |
|---:|---|
| 1 | `{1=1}` |
| 2 | `{1=1, 2=1}` |
| 1 | `{1=2, 2=1}` |
| 1 | `{1=3, 2=1}` |
| 2 | `{1=3, 2=2}` |
| 2 | `{1=3, 2=3}` |
| 3 | `{1=3, 2=3, 3=1}` |

### Step 2: Filter by the threshold

| Element | Frequency | `frequency > 2`? |
|---:|---:|---|
| 1 | 3 | Yes |
| 2 | 3 | Yes |
| 3 | 1 | No |

Final output:

```text
[1, 2]
```

---

## 🧠 Important Mathematical Insight

### Why can there be at most two majority elements?

Each majority element must appear more than `n / 3` times.

Suppose three distinct elements satisfied this condition. Together, their frequencies would exceed:

\[
\frac{n}{3}+\frac{n}{3}+\frac{n}{3}=n
\]

That is impossible because the array contains only `n` elements.

Therefore, **there can be at most two majority elements**.

This fact allows us to optimize the solution using a generalized Boyer–Moore Voting Algorithm.

---

## 🚀 Optimal Approach — Boyer–Moore Voting Algorithm

**Target complexity:**
- Time: `O(n)`
- Auxiliary space: `O(1)`, excluding the output list.

Instead of storing every element's frequency in a HashMap, maintain only two candidates and their vote counts.

### Variables

```java
int candidate1 = 0;
int candidate2 = 1;
int count1 = 0;
int count2 = 0;
```

The initial candidate values are placeholders. Their counts start at zero, so neither candidate has an active vote yet.

### Phase 1: Find the two potential candidates

For each number `num`, apply these rules in order:

1. If `num == candidate1`, increment `count1`.
2. Else if `num == candidate2`, increment `count2`.
3. Else if `count1 == 0`, assign `num` to `candidate1` and set `count1 = 1`.
4. Else if `count2 == 0`, assign `num` to `candidate2` and set `count2 = 1`.
5. Otherwise, decrement both counts.

When a number matches neither candidate and both vote counts are positive, it cancels one vote for each candidate.

### Phase 2: Verify the candidates

The voting phase identifies potential candidates, not necessarily actual majority elements.

Count the occurrences of each candidate in a second pass. Add a candidate to the result only if:

```java
frequency > nums.length / 3
```

Check that the two candidates are distinct before adding them to avoid duplicate results.

### Why verification matters

Unlike LeetCode 169, this problem does not guarantee that a majority element exists. The final candidates must therefore be checked against the original array.

---

## ⏱️ Complexity Analysis

Let `n` be the array length and `m` be the number of distinct values.

| Approach | Time | Auxiliary Space |
|---|---|---|
| HashMap | `O(n)` expected | `O(m)`, worst case `O(n)` |
| Boyer–Moore | `O(n)` | `O(1)` |

The HashMap solution counts frequencies in one pass and examines the map afterward.

The Boyer–Moore solution uses two passes but keeps only a fixed number of variables, making it optimal for auxiliary space.

---

## 🎯 Interview Takeaways

- The threshold is **strictly greater than `n / 3`**, not greater than or equal to it.
- At most two elements can appear more than `n / 3` times.
- A HashMap frequency count is a correct and straightforward solution.
- Generalized Boyer–Moore voting reduces auxiliary space to `O(1)`.
- Candidate selection and candidate verification are separate steps.
- Always verify candidates when the problem does not guarantee a majority element.
- Use the same integer-division threshold in Java: `nums.length / 3`.

### 🔑 Memory Trick

**LeetCode 169:** One candidate, one count, threshold `n / 2`.

**LeetCode 229:** Two candidates, two counts, threshold `n / 3`.

The smaller the threshold fraction, the more candidates you may need to track. For a threshold of `n / k`, the generalized approach tracks at most `k - 1` candidates.

---

## 🏁 Final Summary

Your HashMap solution is correct and a great first approach.

To reach the optimal solution, remember:

1. There can be at most two valid majority elements.
2. Use two candidates and two counters to eliminate competing votes.
3. Verify both candidates in a second pass.
4. Return only candidates appearing more than `n / 3` times.
