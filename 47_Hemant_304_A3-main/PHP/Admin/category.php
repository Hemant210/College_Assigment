<?php
include('db.php');
session_start();

if (!isset($_SESSION['admin'])) {
  header("Location:login.php");
  exit;
}

if (isset($_POST['add'])) {
  $name = mysqli_real_escape_string($conn, $_POST['name']);
  $qry = "insert into category(cname) values('$name')";
  $result = mysqli_query($conn, $qry);
  if ($result) {
    header("Location:category.php");
    exit;
  }
}

if (isset($_POST['update'])) {
  $id = (int)$_POST['id'];
  $name = mysqli_real_escape_string($conn, $_POST['cname']);
  $qry = "update category set cname='$name' where id=$id";
  $result = mysqli_query($conn, $qry);
  if ($result) {
    header("Location:category.php");
    exit;
  }
}

if (isset($_GET['delete'])) {
  $id = (int)$_GET['delete'];
  $qry = "DELETE FROM category where id=$id";
  $result = mysqli_query($conn, $qry);
  if ($result) {
    header("Location: category.php");
    exit;
  }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Category</title>
  <style>
    :root {
      --primary-color: #4f46e5;
      --secondary-color: #4338ca;
      --bg-color: #f3f4f6;
      --card-bg: #ffffff;
      --text-color: #1f2937;
      --border-color: #e5e7eb;
      --danger-color: #ef4444;
      --edit-color: #f59e0b;
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
      max-width: 700px;
      background: var(--card-bg);
      padding: 30px;
      border-radius: 12px;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    }

    h2 {
      margin-top: 0;
      color: var(--text-color);
      font-size: 24px;
      border-bottom: 2px solid var(--border-color);
      padding-bottom: 10px;
    }

    .form-group {
      display: flex;
      gap: 10px;
      margin-bottom: 25px;
    }

    input[type="text"],
    input[type="search"] {
      flex: 1;
      padding: 10px 14px;
      border: 1px solid var(--border-color);
      border-radius: 6px;
      font-size: 15px;
      outline: none;
      transition: border-color 0.2s;
    }

    input[type="text"]:focus {
      border-color: var(--primary-color);
      box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
    }

    button,
    input[type="submit"] {
      background-color: var(--primary-color);
      color: white;
      border: none;
      padding: 10px 20px;
      border-radius: 6px;
      font-size: 15px;
      cursor: pointer;
      font-weight: 500;
      transition: background-color 0.2s;
    }

    button:hover,
    input[type="submit"]:hover {
      background-color: var(--secondary-color);
    }

    .category-table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 15px;
      margin-bottom: 30px;
    }

    .category-table th,
    .category-table td {
      padding: 12px 15px;
      text-align: left;
      border-bottom: 1px solid var(--border-color);
    }

    .category-table th {
      background-color: #f9fafb;
      font-weight: 600;
    }

    .btn-edit {
      color: var(--edit-color);
      text-decoration: none;
      font-weight: 500;
      margin-right: 15px;
    }

    .btn-delete {
      color: var(--danger-color);
      text-decoration: none;
      font-weight: 500;
    }

    .btn-edit:hover,
    .btn-delete:hover {
      text-decoration: underline;
    }

    .navigation-links {
      display: flex;
      justify-content: space-between;
      border-top: 1px solid var(--border-color);
      padding-top: 15px;
      margin-top: 20px;
    }

    .navigation-links a {
      color: var(--primary-color);
      text-decoration: none;
      font-weight: 500;
    }

    .navigation-links a:hover {
      text-decoration: underline;
    }

    .edit-box {
      background: #f9fafb;
      border: 1px solid var(--border-color);
      padding: 20px;
      border-radius: 8px;
      margin-bottom: 25px;
    }

    .edit-box h3 {
      margin-top: 0;
      font-size: 18px;
    }
  </style>
</head>

<body>

  <div class="container">
    <h2>Manage Category</h2>

    <form method="post" class="form-group">
      <input name="name" placeholder="Category Name" required autocomplete="off">
      <button name="add">Add Category</button>
    </form>

    <?php
    if (isset($_GET['edit'])) {
      $id = (int)$_GET['edit'];
      $qry = "select * from category where id=$id";
      $result = mysqli_query($conn, $qry);
      if ($r = mysqli_fetch_assoc($result)) {
    ?>
        <div class="edit-box">
          <h3>Edit Category</h3>
          <form action="" method="post" class="form-group">
            <input type="hidden" name="id" value="<?php echo $r['id'] ?>">
            <input type="text" name="cname" value="<?php echo htmlspecialchars($r['cname']) ?>" required>
            <input type="submit" value="Update" name="update">
          </form>
        </div>
    <?php
      }
    }
    ?>

    <table class="category-table">
      <thead>
        <tr>
          <th>Category Name</th>
          <th style="text-align: right;">Actions</th>
        </tr>
      </thead>
      <tbody>
        <?php
        $qry = "select * from category order by id desc";
        $result = mysqli_query($conn, $qry);
        if (mysqli_num_rows($result) > 0) {
          while ($r = mysqli_fetch_assoc($result)) {
            echo "<tr>";
            echo "<td>" . htmlspecialchars($r['cname']) . "</td>";
            echo "<td style='text-align: right;'>";
            echo "<a href='category.php?edit=" . $r['id'] . "' class='btn-edit'>Edit</a>";
            echo "<a href='category.php?delete=" . $r['id'] . "' class='btn-delete' onclick='return confirm(\"શું તમે ખરેખર આ કેટેગરી ડીલીટ કરવા માંગો છો?\")'>Delete</a>";
            echo "</td>";
            echo "</tr>";
          }
        } else {
          echo "<tr><td colspan='2' style='text-align:center; color:#9ca3af;'>No categories found.</td></tr>";
        }
        ?>
      </tbody>
    </table>

    <div class="navigation-links">
      <a href="product.php">📦 Manage Products</a>
      <a href="logout.php" style="color: var(--danger-color);">🚪 Logout</a>
    </div>
  </div>

</body>

</html>