<?php
/* 
 * Products Display Page (products.php)
 * Handles client-side dynamic content rendering using AJAX XMLHttpRequest.
 */

// Include database connection
include '../Admin/db.php';

// Sanitize and validate incoming Category ID from GET request
$id = isset($_GET['id']) ? (int)$_GET['id'] : 0;
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Products Catalogue</title>
  <style>
    :root {
      --primary-color: #4f46e5;
      --secondary-color: #4338ca;
      --bg-color: #f3f4f6;
      --card-bg: #ffffff;
      --text-color: #1f2937;
      --border-color: #e5e7eb;
    }

    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: var(--bg-color);
      color: var(--text-color);
      margin: 0;
      padding: 40px 20px;
      display: flex;
      justify-content: center;
    }

    .container {
      width: 100%;
      max-width: 900px;
      background: var(--card-bg);
      padding: 30px;
      border-radius: 12px;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    }

    h2 {
      margin-top: 0;
      color: var(--text-color);
      font-size: 24px;
      font-weight: 700;
      border-bottom: 2px solid var(--border-color);
      padding-bottom: 12px;
      margin-bottom: 20px;
    }

    /* Container for AJAX Loaded Products */
    #data {
      min-height: 200px;
      margin-bottom: 25px;
    }

    /* Pagination Buttons Wrapper */
    .pagination-wrapper {
      display: flex;
      justify-content: center;
      gap: 8px;
      margin-top: 20px;
      border-top: 1px solid var(--border-color);
      padding-top: 20px;
    }

    /* Modern Pagination Buttons Styling */
    .btn-page {
      background-color: #ffffff;
      color: var(--text-color);
      border: 1px solid var(--border-color);
      padding: 8px 16px;
      border-radius: 6px;
      font-size: 14px;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.2s ease;
    }

    .btn-page:hover {
      border-color: var(--primary-color);
      color: var(--primary-color);
      background-color: #f9fafb;
    }

    .btn-page:focus {
      background-color: var(--primary-color);
      color: #ffffff;
      border-color: var(--primary-color);
      outline: none;
    }

    /* Bottom Navigation Bar Links */
    .footer-nav {
      display: flex;
      justify-content: space-between;
      margin-top: 30px;
      font-size: 14px;
    }

    .footer-nav a {
      color: var(--primary-color);
      text-decoration: none;
      font-weight: 500;
    }

    .footer-nav a:hover {
      text-decoration: underline;
    }
  </style>
</head>

<body>

  <div class="container">
    <h2>Our Products</h2>

    <!-- Dynamic Content Area managed by AJAX -->
    <div id="data"></div>

    <!-- Pagination Controls Component -->
    <div class="pagination-wrapper">
      <button class="btn-page" onclick="loadData(1)">1</button>
      <button class="btn-page" onclick="loadData(2)">2</button>
    </div>

    <!-- Footer Navigation Links -->
    <div class="footer-nav">
      <a href="index.php">🏠 Home Directory</a>
      <a href="register.php">Create Account</a>
    </div>
  </div>

  <script>
    /**
     * Asynchronously fetches filtered products page from server-side database
     * @param {number} page - The current page sequence to request
     */
    function loadData(page) {
      var x = new XMLHttpRequest();

      // Trigger state change updates dynamically inside target block
      x.onload = function() {
        if (this.status == 200) {
          document.getElementById("data").innerHTML = this.responseText;
        } else {
          document.getElementById("data").innerHTML = "<p style='color:red; text-align:center;'>Failed to load product data.</p>";
        }
      };

      // Open connection stream secure referencing escaped variable
      x.open("GET", "load.php?id=<?php echo htmlspecialchars($id, ENT_QUOTES, 'UTF-8'); ?>&page=" + page);
      x.send();
    }

    // Initial runtime load to render standard first page sequence
    loadData(1);
  </script>

</body>

</html>