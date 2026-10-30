
# LeetCode 881: Boats to Save People

- **Difficulty:** Medium
- **Pattern:** Sorting + Two Pointers + Greedy
- **Language:** Java
- **Problem:** [Boats to Save People](https://leetcode.com/problems/boats-to-save-people/)

---

## 1. Problem Statement

You are given an array `people`, where `people[i]` represents the weight of the `i-th` person, and an integer `limit` representing the maximum weight a boat can carry.

Each boat can carry:

- At most **two people**.
- A combined weight that does not exceed `limit`.

Return the minimum number of boats required to rescue everyone.

### Example 1

**Input:**
```text
people = [1, 2]
limit = 3
```

**Output:**
```text
1
```

**Explanation:** Both people can share one boat because their combined weight is 3.

### Example 2

**Input:**
```text
people = [3, 2, 2, 1]
limit = 3
```

**Output:**
```text
3
```

**Explanation:**

- Boat 1: Person weighing 3.
- Boat 2: People weighing 1 and 2.
- Boat 3: Person weighing 2.

### Example 3

**Input:**
```text
people = [3, 5, 3, 4]
limit = 5
```

**Output:**
```text
4
```

Every person needs a separate boat because no two people can share a boat within the weight limit.

---

## 2. Core Intuition

The objective is to minimize the number of boats.

Since each boat can carry at most two people, we need to decide which two people should share a boat.

A useful greedy strategy is:

1. Sort people by weight.
2. Pair the heaviest person with the lightest person whenever possible.
3. If they cannot fit together, send the heaviest person alone.
4. Repeat until everyone is assigned a boat.

### Why pair the lightest and heaviest?

The heaviest person is the hardest person to accommodate.

If the heaviest person cannot share a boat even with the lightest person, they cannot share a boat with anyone.

Therefore, they must go alone.

If they can share a boat with the lightest person, pairing them is a safe greedy choice.

---

## 3. Two-Pointer Approach

After sorting, initialize two pointers:

```java
int i = 0;
int j = people.length - 1;
int count = 0;
```

- `i` points to the lightest person.
- `j` points to the heaviest person.
- `count` tracks the number of boats used.

At each iteration, consider the heaviest person.

### Case 1: Both people fit

```java
if (people[i] + people[j] <= limit) {
    i++;
}
```

The lightest and heaviest people share one boat.

Move `i` forward because that lightest person has been rescued.

### Case 2: They cannot fit

If:

```java
people[i] + people[j] > limit
```

The heaviest person must travel alone.

We do not move `i` because the lightest person is still waiting.

### In both cases

```java
j--;
count++;
```

The heaviest person always gets a boat, so we always move `j` backward and increment the boat count.

---

## 4. Your Final Java Solution

```java
import java.util.Arrays;

class Solution {
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);

        int i = 0;
        int j = people.length - 1;
        int count = 0;

        while (i <= j) {
            if (people[i] + people[j] <= limit) {
                i++;
            }

            j--;
            count++;
        }

        return count;
    }
}
```

**Status:** Correct optimal greedy approach.

Notice that `j--` and `count++` are outside the `if` block.

Why? Because the heaviest person always gets a boat, regardless of whether they travel alone or with the lightest person.

---

## 5. Dry Run

Input:

```text
people = [3, 2, 2, 1]
limit = 3
```

### Step 1: Sort the array

```text
people = [1, 2, 2, 3]
          ↑        ↑
          i        j
```

Initially:

```text
i = 0
j = 3
count = 0
```

### Step 2: Process each boat

| Step | Lightest | Heaviest | Sum | Action | Boats |
|---:|---:|---:|---:|---|---:|
| 1 | 1 | 3 | 4 | 3 travels alone | 1 |
| 2 | 1 | 2 | 3 | 1 and 2 travel together | 2 |
| 3 | 2 | 2 | 4 | 2 travels alone | 3 |

Final state:

```text
i = 2
j = 1
count = 3
```

Since `i > j`, everyone has been assigned a boat.

**Output:**

```text
3
```

---

## 6. Why Does This Greedy Algorithm Work?

The most important observation is:

**The heaviest remaining person must be assigned a boat.**

Consider two situations.

### Situation A: The heaviest person cannot fit with the lightest person

```java
people[i] + people[j] > limit
```

Because `people[i]` is the lightest remaining person, every other remaining person weighs at least as much.

Therefore, nobody can share a boat with the heaviest person.

Sending them alone is unavoidable.

### Situation B: The heaviest person can fit with the lightest person

```java
people[i] + people[j] <= limit
```

The lightest person is the best candidate to pair with the heaviest person.

Pairing them is a safe greedy choice: if an optimal arrangement pairs the heaviest person with someone else, we can exchange those pairings so the heaviest and lightest share a boat without increasing the number of boats.

After making that decision, remove the assigned person or people and repeat.

---

## 7. Complexity Analysis

Let `n` be the number of people.

| Operation | Complexity |
|---|---|
| Sorting | `O(n log n)` |
| Two-pointer traversal | `O(n)` |
| Total time | `O(n log n)` |

### Time Complexity

```text
O(n log n)
```

Sorting dominates the overall complexity. The two-pointer loop processes each person at most once.

### Auxiliary Space Complexity

```text
O(log n)
```

The two-pointer traversal uses `O(1)` extra variables.

Java's sorting implementation may use additional stack space. The exact auxiliary-space behavior depends on the implementation.

The returned integer is a single count, so there is no additional output collection.

---

## 8. Brute Force vs Optimal Approach

### Greedy Two-Pointer Approach

Sort the array and pair the heaviest person with the lightest person whenever possible.

- Time: `O(n log n)`
- Auxiliary space: `O(log n)` for sorting implementation overhead.

This is the standard optimal approach.

### Why not simply pair the two lightest people?

Consider:

```text
people = [1, 1, 2, 2, 3, 3]
limit = 4
```

Pairing the two lightest first:

- `1 + 1`
- `2 + 2`
- `3` alone
- `3` alone

Total: 4 boats.

Optimal pairing:

- `1 + 3`
- `1 + 3`
- `2 + 2`

Total: 3 boats.

The problem is not about filling boats with the lightest available people. It is about accommodating the heaviest people efficiently.

---

## 9. Common Mistakes

### Mistake 1: Pairing the two lightest people first

This can waste opportunities to pair light people with heavy people.

Always consider the heaviest remaining person first.

### Mistake 2: Using a nested while loop

A boat can carry at most two people. You do not need a loop to keep adding people to a boat.

Use a single loop with two pointers.

### Mistake 3: Moving both pointers when the pair exceeds the limit

Incorrect:

```java
else {
    i++;
    j--;
}
```

If the pair exceeds the limit, only the heaviest person is assigned a boat.

The lightest person must remain available for another pairing.

### Mistake 4: Forgetting that the heaviest person always leaves

Whether the pair fits or not, `j` must always decrease.

### Mistake 5: Using `i < j` instead of `i <= j`

Incorrect:

```java
while (i < j)
```

When `i == j`, one person remains and still needs a boat.

Correct:

```java
while (i <= j)
```

### Mistake 6: Forgetting to sort

The greedy pointer strategy depends on knowing which person is lightest and which is heaviest.

Always sort first.

---

## 10. Edge Cases

| Input | Limit | Expected boats |
|---|---:|---:|
| `[1]` | 1 | 1 |
| `[1, 2]` | 3 | 1 |
| `[3, 5, 3, 4]` | 5 | 4 |
| `[1, 1, 1, 1]` | 2 | 2 |
| `[2, 2, 2]` | 3 | 3 |
| `[1, 2, 2, 3]` | 3 | 3 |

---

## 11. Interview Explanation

"I use a greedy two-pointer approach. First, I sort the people by weight. I initialize one pointer at the lightest person and another at the heaviest person. In every iteration, I assign the heaviest person a boat. If the lightest and heaviest people can fit together within the weight limit, I move both pointers. Otherwise, I move only the heaviest pointer because that person must travel alone. I increment the boat count in every iteration. Since sorting takes O(n log n) and the traversal takes O(n), the overall time complexity is O(n log n)."

---

## 12. Revision Questions

Try answering these without looking at the solution.

1. Why do we sort the people array?
2. Why do we start with the lightest and heaviest people?
3. Why is the heaviest person always assigned a boat?
4. If the lightest and heaviest people exceed the limit, why must the heaviest person travel alone?
5. Why do we move both pointers when the pair fits?
6. Why do we move only `j` when the pair does not fit?
7. Why is the loop condition `i <= j`?
8. Why is sorting necessary for this greedy strategy?
9. What is the time complexity of the complete algorithm?
10. Why is pairing the two lightest people first not always optimal?

---

## 13. Related Problems

Practice these to strengthen your greedy and two-pointer problem-solving skills.

1. [LeetCode 167: Two Sum II - Input Array Is Sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)
2. [LeetCode 11: Container With Most Water](https://leetcode.com/problems/container-with-most-water/)
3. [LeetCode 15: 3Sum](https://leetcode.com/problems/3sum/)
4. [LeetCode 455: Assign Cookies](https://leetcode.com/problems/assign-cookies/)
5. [LeetCode 881: Boats to Save People](https://leetcode.com/problems/boats-to-save-people/)

---

## 14. Key Takeaways

- **Pattern:** Sorting + Two Pointers + Greedy.
- **Sort:** Arrange people by weight.
- **Left pointer:** Lightest remaining person.
- **Right pointer:** Heaviest remaining person.
- **If the pair fits:** Move both pointers.
- **If the pair does not fit:** Move only the right pointer.
- **Every iteration:** Increment the boat count.
- **Time Complexity:** `O(n log n)`.
- **Auxiliary Space:** Sorting implementation overhead, typically `O(log n)`.

**Remember this rule:**

> Always accommodate the heaviest person first. Pair them with the lightest person if possible; otherwise, send them alone.
