<?php
include '../Admin/db.php';
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Shopping Website - Home</title>
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
      padding: 0;
    }

    /* Navbar / Header Styling */
    .navbar {
      background-color: var(--card-bg);
      padding: 15px 40px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    }

    .navbar h2 {
      margin: 0;
      color: var(--primary-color);
      font-size: 24px;
      font-weight: 700;
    }

    .nav-auth-links a {
      color: var(--text-color);
      text-decoration: none;
      font-weight: 500;
      padding: 8px 16px;
      border-radius: 6px;
      transition: all 0.2s;
    }

    .nav-auth-links a:hover {
      color: var(--primary-color);
      background-color: #f3f4f6;
    }

    .nav-auth-links .btn-register {
      background-color: var(--primary-color);
      color: white;
      margin-left: 10px;
    }

    .nav-auth-links .btn-register:hover {
      background-color: var(--secondary-color);
      color: white;
    }

    /* Main Container */
    .main-container {
      max-width: 1000px;
      margin: 40px auto;
      padding: 0 20px;
    }

    .section-title {
      font-size: 22px;
      font-weight: 600;
      margin-bottom: 20px;
      padding-bottom: 8px;
      border-bottom: 2px solid var(--border-color);
    }

    /* Categories Grid */
    .categories-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }

    .category-card {
      background: var(--card-bg);
      border: 1px solid var(--border-color);
      border-radius: 10px;
      padding: 25px 20px;
      text-align: center;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
      transition: transform 0.2s, box-shadow 0.2s;
    }

    .category-card:hover {
      transform: translateY(-5px);
      box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
      border-color: var(--primary-color);
    }

    .category-card a {
      color: var(--text-color);
      text-decoration: none;
      font-size: 18px;
      font-weight: 600;
      display: block;
      transition: color 0.2s;
    }

    .category-card:hover a {
      color: var(--primary-color);
    }
  </style>
</head>

<body>

  <!-- Modern Header / Navbar -->
  <header class="navbar">
    <h2>Shopping Website</h2>
    <nav class="nav-auth-links">
      <a href="userlogin.php">Login</a>
      <a href="register.php" class="btn-register">Register</a>
    </nav>
  </header>

  <!-- Main Content Area -->
  <main class="main-container">
    <h3 class="section-title">Browse Categories</h3>

    <div class="categories-grid">
      <?php
      $q = mysqli_query($conn, "SELECT * FROM category");
      if (mysqli_num_rows($q) > 0) {
        while ($r = mysqli_fetch_assoc($q)) {
          echo "<div class='category-card'>";
          echo "<a href='products.php?id=" . $r['id'] . "'>" . htmlspecialchars($r['cname']) . "</a>";
          echo "</div>";
        }
      } else {
        echo "<p style='grid-column: 1/-1; text-align: center; color: #9ca3af;'>No categories available at the moment.</p>";
      }
      ?>
    </div>
  </main>

</body>

</html>