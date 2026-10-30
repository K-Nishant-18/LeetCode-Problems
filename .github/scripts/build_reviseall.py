#!/usr/bin/env python3
"""
build_reviseall.py
------------------
Builds ReviseAll.md: a clean, linked index of every Revision.md in the repo.

* Finds Revision.md files recursively, so new problem folders are picked up
  automatically (and deleted ones disappear on the next run).
* Does NOT copy the notes - it only links to each Revision.md.
* Reads README.md (the LeetCode-sync tables) to look up each problem's
  difficulty and topics.
* Rewrites ONLY the part of ReviseAll.md between the two marker comments
  below. Anything you write above/below the markers (header, footer, badges)
  is preserved, exactly like the LeetCode Topics block in README.md.

Run locally from anywhere:   python .github/scripts/build_reviseall.py
No third-party packages needed (Python 3.8+).
"""

import os
import re
import subprocess
import sys
from pathlib import Path
from urllib.parse import quote

# --------------------------------------------------------------------------- #
# Config
# --------------------------------------------------------------------------- #
ROOT = Path(os.environ.get("REPO_ROOT") or Path(__file__).resolve().parents[2])
OUTPUT = ROOT / "ReviseAll.md"
README = ROOT / "README.md"

REVISION_FILENAME = "revision.md"                    # matched case-insensitively
SKIP_DIRS = {"node_modules", "venv", "__pycache__"}  # dot-folders are skipped too

START = "<!---Revision Notes Start-->"
END = "<!---Revision Notes End-->"

DIFFICULTY_EMOJI = {"Easy": "🟢", "Medium": "🟡", "Hard": "🔴"}
DIFFICULTY_COLOR = {"Easy": "brightgreen", "Medium": "orange", "Hard": "red"}

TOPIC_EMOJI = {
    "array": "🧮", "string": "🔤", "linked list": "🔗", "stack": "🥞", "queue": "🚶",
    "two pointers": "👉", "hash table": "#️⃣", "dynamic programming": "🧠",
    "recursion": "🔁", "memoization": "📝", "sorting": "📶", "math": "➗",
    "greedy": "🪙", "heap (priority queue)": "⛰️", "design": "🏗️", "binary search": "🔍",
    "prefix sum": "➕", "simulation": "🎮", "divide and conquer": "✂️", "counting": "🔢",
    "monotonic stack": "📈", "database": "🗄️", "trie": "🌿", "bucket sort": "🪣",
    "quicksort": "⚡", "quickselect": "🎯", "bubble sort": "🫧", "timsort": "⏱️",
}

DEFAULT_HEADER = """\
<div align="center">

# 📚 Revise All

### One page to revise every problem I've solved ✨

*Click any **📖 Notes** link to jump straight to that problem's `Revision.md`.*

<br>

[![Last updated](https://img.shields.io/github/last-commit/__REPO__?path=ReviseAll.md&label=last%20updated&color=7c3aed&style=for-the-badge)](https://github.com/__REPO__/commits/__BRANCH__/ReviseAll.md)
[![Auto updated](https://img.shields.io/badge/auto%20updated%20by-GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)](https://github.com/__REPO__/actions)

</div>

---

"""

DEFAULT_FOOTER = """

---

<div align="center">

### 🔄 How this page stays fresh

Add a `Revision.md` inside any problem folder → push → this page updates itself.
Only the section between the `Revision Notes Start/End` markers is regenerated — the header and footer are yours to edit.

<br>

### 🤝 Connect with Me

[![GitHub](https://img.shields.io/badge/GitHub-__OWNER__-181717?style=for-the-badge&logo=github)](https://github.com/__OWNER__)

⭐ **If this repo helps you, consider giving it a star!** ⭐

</div>
"""


# --------------------------------------------------------------------------- #
# Repo info (used for links)
# --------------------------------------------------------------------------- #
def detect_repo() -> str:
    repo = os.environ.get("GITHUB_REPOSITORY")
    if repo:
        return repo
    try:
        url = subprocess.check_output(
            ["git", "-C", str(ROOT), "config", "--get", "remote.origin.url"],
            text=True,
            stderr=subprocess.DEVNULL,
        ).strip()
        m = re.search(r"github\.com[:/]+([^/]+/[^/]+?)(?:\.git)?$", url)
        if m:
            return m.group(1)
    except Exception:
        pass
    return "K-Nishant-18/LeetCode-Problems"


REPO = detect_repo()
OWNER = REPO.split("/")[0]
BRANCH = os.environ.get("REVISEALL_BRANCH") or "main"
BLOB = f"https://github.com/{REPO}/blob/{BRANCH}"
TREE = f"https://github.com/{REPO}/tree/{BRANCH}"


# --------------------------------------------------------------------------- #
# README metadata (difficulty + topics)
# --------------------------------------------------------------------------- #
TOPIC_RE = re.compile(r"^##\s+(.+?)\s*$")
ROW_RE = re.compile(r"^\|\s*\[([^\]]+)\]\([^)]*\)\s*\|\s*(Easy|Medium|Hard)\s*\|", re.I)


def read_readme_meta() -> dict:
    """slug -> {"difficulty": str, "topics": [str, ...]} from the README tables."""
    meta = {}
    if not README.exists():
        return meta
    text = README.read_text(encoding="utf-8")
    m = re.search(r"<!---LeetCode Topics Start-->(.*?)<!---LeetCode Topics End-->", text, re.S)
    block = m.group(1) if m else text
    topic = None
    for line in block.splitlines():
        t = TOPIC_RE.match(line)
        if t:
            topic = t.group(1)
            continue
        r = ROW_RE.match(line.strip())
        if r and topic:
            slug, diff = r.group(1), r.group(2).capitalize()
            entry = meta.setdefault(slug, {"difficulty": diff, "topics": []})
            if topic not in entry["topics"]:
                entry["topics"].append(topic)
    return meta


# --------------------------------------------------------------------------- #
# Finding Revision.md files
# --------------------------------------------------------------------------- #
def find_revisions() -> list:
    found = []
    for dirpath, dirnames, filenames in os.walk(ROOT):
        dirnames[:] = sorted(
            d for d in dirnames if not d.startswith(".") and d not in SKIP_DIRS
        )
        for name in filenames:
            if name.lower() == REVISION_FILENAME:
                found.append(Path(dirpath) / name)
    return found


SMALL_WORDS = {"of", "in", "the", "a", "an", "to", "and", "with", "for", "on", "at", "from", "by", "or"}
ROMAN = {"i", "ii", "iii", "iv", "v", "vi"}


def smart_title(text: str) -> str:
    out = []
    for i, w in enumerate(text.split()):
        lw = w.lower()
        if lw in ROMAN:
            out.append(lw.upper())
        elif i > 0 and lw in SMALL_WORDS:
            out.append(lw)
        elif re.match(r"^\d+[a-z]+$", lw):          # "3sum" -> "3Sum"
            digits = re.match(r"^\d+", lw).group(0)
            out.append(digits + lw[len(digits):].capitalize())
        else:
            out.append(lw[:1].upper() + lw[1:])
    return " ".join(out)


def build_items(paths: list, meta: dict) -> list:
    items = []
    for path in paths:
        rel = path.relative_to(ROOT)
        folder = rel.parent.as_posix()                       # "." for repo root
        slug = rel.parent.name if folder != "." else "root"
        m = re.match(r"^(\d+)-(.*)$", slug)
        number = m.group(1) if m else ""
        title = smart_title((m.group(2) if m else slug).replace("-", " ").replace("_", " "))
        info = meta.get(slug, {})
        items.append({
            "slug": slug,
            "number": number,
            "title": title,
            "difficulty": info.get("difficulty", ""),
            "topics": info.get("topics", []),
            "notes_url": f"{BLOB}/{quote(rel.as_posix())}",
            "folder_url": f"{TREE}/{quote(folder)}" if folder != "." else TREE,
        })
    items.sort(key=lambda i: (int(i["number"]) if i["number"] else 10**9, i["slug"].lower()))
    return items


# --------------------------------------------------------------------------- #
# Rendering helpers
# --------------------------------------------------------------------------- #
def level(d: str) -> str:
    return f"{DIFFICULTY_EMOJI.get(d, '')} {d}".strip() if d else "—"


def chips(topics: list, limit: int = 4) -> str:
    """Topic chips; long lists are cut to `limit` plus a '+N' counter."""
    if not topics:
        return "—"
    shown = " ".join(f"`{t}`" for t in topics[:limit])
    return shown + (f" `+{len(topics) - limit}`" if len(topics) > limit else "")


def progress_bar(done: int, total: int, width: int = 20) -> str:
    if total <= 0:
        return ""
    filled = round(width * done / total)
    return f"{'▰' * filled}{'▱' * (width - filled)}  **{round(100 * done / total)}%**"


def topic_icon(topic: str) -> str:
    return TOPIC_EMOJI.get(topic.lower(), "🏷️")


def problem_folders() -> list:
    """Top-level problem folders (e.g. 0001-two-sum) to measure coverage."""
    return sorted(p.name for p in ROOT.iterdir() if p.is_dir() and re.match(r"^\d+-", p.name))


def row(i: dict) -> str:
    return (f"| {i['number'] or '—'} | [{i['title']}]({i['folder_url']}) | {level(i['difficulty'])} "
            f"| {chips(i['topics'])} | [📖 Notes]({i['notes_url']}) |")


# --------------------------------------------------------------------------- #
# Rendering
# --------------------------------------------------------------------------- #
def render(items: list) -> str:
    L = []
    folders = problem_folders()
    covered = {i["slug"] for i in items}
    missing = [f for f in folders if f not in covered]
    counts = {d: sum(1 for i in items if i["difficulty"] == d) for d in DIFFICULTY_EMOJI}
    done = len(covered & set(folders)) if folders else len(items)
    total = len(folders) if folders else len(items)

    # ---- overview --------------------------------------------------------
    L.append("## 📊 Overview")
    L.append("")
    L.append('<div align="center">')
    L.append("")
    L.append(
        f"![Notes](https://img.shields.io/badge/📚%20Revision%20Notes-{len(items)}-7c3aed?style=for-the-badge) "
        + " ".join(
            f"![{d}](https://img.shields.io/badge/{d}-{counts[d]}-{DIFFICULTY_COLOR[d]}?style=for-the-badge)"
            for d in DIFFICULTY_EMOJI
        )
    )
    L.append("")
    if folders:
        L.append(f"**Coverage** — {done} of {total} solved problems have revision notes")
        L.append("")
        L.append(progress_bar(done, total))
        L.append("")
    L.append("</div>")
    L.append("")

    if not items:
        L.append("> _No `Revision.md` files found yet. Add one inside any problem folder and it will appear here._")
        return "\n".join(L)

    # ---- all notes -------------------------------------------------------
    L.append("## 🗂️ All Revision Notes")
    L.append("")
    L.append("| # | Problem | Level | Topics | Revise |")
    L.append("|:---:|:---|:---:|:---|:---:|")
    for i in items:
        L.append(row(i))
    L.append("")

    # ---- by topic --------------------------------------------------------
    by_topic = {}
    for i in items:
        for t in i["topics"]:
            by_topic.setdefault(t, []).append(i)
    if by_topic:
        L.append("## 🏷️ Browse by Topic")
        L.append("")
        L.append("> Tap a topic to expand it.")
        L.append("")
        for topic in sorted(by_topic, key=lambda t: (-len(by_topic[t]), t.lower())):
            group = by_topic[topic]
            L.append("<details>")
            L.append(f"<summary><b>{topic_icon(topic)} {topic}</b> &nbsp;·&nbsp; {len(group)}</summary>")
            L.append("")
            L.append("| # | Problem | Level | Revise |")
            L.append("|:---:|:---|:---:|:---:|")
            for i in group:
                L.append(f"| {i['number'] or '—'} | [{i['title']}]({i['folder_url']}) "
                         f"| {level(i['difficulty'])} | [📖 Notes]({i['notes_url']}) |")
            L.append("")
            L.append("</details>")
            L.append("")

    # ---- still to write --------------------------------------------------
    if missing:
        L.append("## ✍️ Still To Write")
        L.append("")
        L.append("<details>")
        L.append(f"<summary><b>Problems without a Revision.md yet</b> &nbsp;·&nbsp; {len(missing)}</summary>")
        L.append("")
        cols = 3
        L.append("| " + " | ".join([" "] * cols) + " |")
        L.append("|" + "|".join([":---"] * cols) + "|")
        links = [f"[{f}]({TREE}/{quote(f)})" for f in missing]
        for k in range(0, len(links), cols):
            chunk = links[k:k + cols] + [" "] * (cols - len(links[k:k + cols]))
            L.append("| " + " | ".join(chunk) + " |")
        L.append("")
        L.append("</details>")

    return "\n".join(L).rstrip("\n")


# --------------------------------------------------------------------------- #
# Assemble + write
# --------------------------------------------------------------------------- #
def fill(template: str) -> str:
    badge_owner = OWNER.replace("-", "--")        # shields.io needs '--' for a literal dash
    return (template.replace("__REPO__", REPO).replace("__BRANCH__", BRANCH)
            .replace("GitHub-__OWNER__-", f"GitHub-{badge_owner}-").replace("__OWNER__", OWNER))


def assemble(generated: str) -> str:
    block = f"{START}\n{generated}\n{END}"
    if OUTPUT.exists():
        old = OUTPUT.read_text(encoding="utf-8")
        if START in old and END in old:
            head, rest = old.split(START, 1)
            _, tail = rest.split(END, 1)
            return head + block + tail            # keep the user's header/footer
    return fill(DEFAULT_HEADER) + block + fill(DEFAULT_FOOTER)


def main() -> int:
    items = build_items(find_revisions(), read_readme_meta())
    new_text = assemble(render(items))
    if not new_text.endswith("\n"):
        new_text += "\n"

    old_text = OUTPUT.read_text(encoding="utf-8") if OUTPUT.exists() else None
    if old_text == new_text:
        print(f"ReviseAll.md already up to date ({len(items)} revision notes).")
    else:
        OUTPUT.write_text(new_text, encoding="utf-8", newline="\n")
        print(f"ReviseAll.md written: {len(items)} revision notes, {len(new_text.encode('utf-8')) / 1024:.0f} KB.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
