# Complex Data Handling

This repository contains the complete implementations and reports for the three (3) laboratory projects developed for the advanced **"Complex Data Handling"** university course. All implementations have been built entirely from scratch using **Python**, simulating low-level operations of Database Management Systems (DBMS), spatial indexing structures, and complex query processing optimization.

---

## 📁 Repository Structure & Projects Overview

### 🛠️ 01-query-evaluation: Relational Query Evaluation Engine
Implementation of core database query processing operators over CSV files without relying on any external database libraries.
* **Part 1 (Group-By with Aggregation):** Implements the **Merge Sort** algorithm to sort datasets based on a specific grouping attribute and computes aggregation functions (e.g., SUM) with custom column filtering.
* **Part 2 (Merge Join):** Implements a low-level **Natural Merge Join** algorithm to combine two relations (CSV files) over a common key, ensuring pipelined streaming processing.
* **Part 3 (Composite Query Optimization):** Optimizes a complex composite query involving selection, joining, and aggregation into a **One-Pass Evaluation** engine to significantly reduce disk I/O operations.

### 📍 02-spatial-data: Spatial Data Indexing System
Design and implementation of a spatial indexing structure optimized for the efficient management and querying of geographical coordinates $(x, y)$.
* **Bulk Loading (STR Algorithm):** Parses spatial data points and constructs an in-memory **R-Tree** utilizing the **Sort-Tile-Recursive (STR)** bulk-loading technique. Handles stripe partitioning, Minimum Bounding Boxes (MBRs) creation, and node hierarchy configuration.
* **Incremental Nearest Neighbor Search (INNS):** Implements an optimized $k$-Nearest Neighbor ($k$-NN) search algorithm powered by a custom **Priority Queue**. Elements are dynamically evaluated by their distance to the target query point, enabling incremental retrieval without traversing the entire tree.

### 📉 03-topk-joins: Top-$k$ & Rank Join Queries Optimizer
Development and performance evaluation of advanced ranking query algorithms designed to identify the top-$k$ optimal pairs from pre-sorted inputs based on an aggregate scoring function.
* **Algorithm A (Threshold-based Top-$k$ Join / HRJN):** Implements a pipelined variant of the Threshold Algorithm. It accesses sorted streams alternatively using Buffered Readers, applies conditional evaluation filters, maintains internal HashMaps, and leverages a **Max-Heap** to manage top candidates. The termination condition is checked dynamically via a moving threshold ($T$).
* **Algorithm B (Brute-Force / Nested-Loop Hash Join):** A baseline approach that loads all valid records into memory, executes an exhaustive cross-join evaluation, and maintains a fixed-size **Min-Heap** of size $k$ to store the top-ranked results.
* **Performance Analysis:** Conducts an empirical comparison between the two approaches regarding execution time and memory footprint, highlighting the importance of early-stopping conditions.

---

## 💻 Tech Stack & Core Concepts
* **Programming Language:** Python 3.x
* **Core Data Structures:** Custom Min/Max Heaps, Priority Queues, HashMaps, R-Tree Nodes, and Minimum Bounding Boxes (MBRs).
* **File Management:** Custom CSV parsing and Buffered I/O stream processing.

---
