<?php
include 'db.php';
session_start();

if (!isset($_SESSION['admin'])) {
  header("Location: login.php");
  exit;
}

$msg = "";
$msg_type = "";

if (isset($_POST['add'])) {
  $name = mysqli_real_escape_string($conn, $_POST['pname']);
  $price = (int)$_POST['price'];
  $cat = (int)$_POST['category'];

  if (isset($_FILES['image']) && $_FILES['image']['error'] == 0) {
    $tmp = $_FILES['image']['tmp_name'];
    $info = getimagesize($tmp);

    if ($info && $info['mime'] == 'image/jpeg') {
      $img = imagecreatefromjpeg($tmp);
      $new = imagecreatetruecolor(200, 200);

      imagecopyresized(
        $new,
        $img,
        0,
        0,
        0,
        0,
        200,
        200,
        imagesx($img),
        imagesy($img)
      );

      if (!is_dir('uploads')) {
        mkdir('uploads', 0777, true);
      }

      $file = time() . ".jpg";
      imagejpeg($new, "uploads/" . $file);

      imagedestroy($img);
      imagedestroy($new);

      $res = mysqli_query(
        $conn,
        "INSERT INTO product(pname,price,cid,image) VALUES('$name',$price,$cat,'$file')"
      );

      if ($res) {
        $msg = "Product Added!";
        $msg_type = "success";
      } else {
        $msg = "Failed To Insert.";
        $msg_type = "error";
      }
    } else {
      $msg = "Please only Add JPG/JPEG image.";
      $msg_type = "error";
    }
  } else {
    $msg = "Please Add Image.";
    $msg_type = "error";
  }
}

if (isset($_GET['delete'])) {
  $id = (int)$_GET['delete'];

  $img_q = mysqli_query($conn, "SELECT image FROM product WHERE id=$id");
  if ($img_r = mysqli_fetch_assoc($img_q)) {
    if (file_exists("uploads/" . $img_r['image'])) {
      unlink("uploads/" . $img_r['image']);
    }
  }

  mysqli_query($conn, "DELETE FROM product WHERE id=$id");
  header("Location: product.php");
  exit;
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Products</title>
  <style>
    :root {
      --primary-color: #4f46e5;
      --secondary-color: #4338ca;
      --bg-color: #f3f4f6;
      --card-bg: #ffffff;
      --text-color: #1f2937;
      --border-color: #e5e7eb;
      --danger-color: #ef4444;
      --success-color: #10b981;
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

    h2,
    h3 {
      margin-top: 0;
      color: var(--text-color);
      border-bottom: 2px solid var(--border-color);
      padding-bottom: 10px;
    }

    h3 {
      margin-top: 40px;
      font-size: 20px;
    }

    .form-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 15px;
      margin-bottom: 20px;
    }

    .form-group {
      display: flex;
      flex-direction: column;
    }

    .form-group label {
      font-size: 14px;
      font-weight: 500;
      margin-bottom: 6px;
      color: #4b5563;
    }

    input[type="text"],
    input[type="number"],
    select,
    input[type="file"] {
      padding: 10px 14px;
      border: 1px solid var(--border-color);
      border-radius: 6px;
      font-size: 15px;
      outline: none;
      background-color: #fff;
      box-sizing: border-box;
    }

    input:focus,
    select:focus {
      border-color: var(--primary-color);
      box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
    }

    .btn-submit {
      background-color: var(--primary-color);
      color: white;
      border: none;
      padding: 12px 20px;
      border-radius: 6px;
      font-size: 15px;
      cursor: pointer;
      font-weight: 500;
      margin-top: 10px;
      transition: background-color 0.2s;
      width: 100%;
    }

    .btn-submit:hover {
      background-color: var(--secondary-color);
    }

    .product-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }

    .product-card {
      background: #f9fafb;
      border: 1px solid var(--border-color);
      border-radius: 8px;
      padding: 15px;
      text-align: center;
      transition: transform 0.2s, box-shadow 0.2s;
    }

    .product-card:hover {
      transform: translateY(-4px);
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
    }

    .product-card img {
      border-radius: 6px;
      object-fit: cover;
      margin-bottom: 12px;
      border: 1px solid var(--border-color);
      background-color: #eee;
    }

    .product-name {
      font-weight: 600;
      font-size: 16px;
      margin: 8px 0 4px 0;
    }

    .product-price {
      color: var(--primary-color);
      font-weight: 700;
      font-size: 15px;
      margin-bottom: 12px;
    }

    .btn-delete {
      display: inline-block;
      color: var(--danger-color);
      text-decoration: none;
      font-weight: 500;
      font-size: 14px;
      border: 1px solid var(--danger-color);
      padding: 6px 12px;
      border-radius: 4px;
      transition: all 0.2s;
      width: 80%;
    }

    .btn-delete:hover {
      background-color: var(--danger-color);
      color: white;
    }

    .alert {
      padding: 12px;
      border-radius: 6px;
      margin-bottom: 20px;
      font-size: 14px;
      font-weight: 500;
      text-align: center;
    }

    .alert-success {
      background-color: #d1fae5;
      color: var(--success-color);
      border: 1px solid #a7f3d0;
    }

    .alert-error {
      background-color: #fee2e2;
      color: var(--danger-color);
      border: 1px solid #fca5a5;
    }

    .navigation-links {
      display: flex;
      justify-content: space-between;
      border-top: 1px solid var(--border-color);
      padding-top: 15px;
      margin-top: 30px;
    }

    .navigation-links a {
      color: var(--primary-color);
      text-decoration: none;
      font-weight: 500;
    }

    .navigation-links a:hover {
      text-decoration: underline;
    }
  </style>
</head>

<body>

  <div class="container">
    <h2>📦 Manage Products</h2>

    <?php if (!empty($msg)): ?>
      <div class="alert alert-<?php echo $msg_type; ?>">
        <?php echo $msg; ?>
      </div>
    <?php endif; ?>

    <!-- પ્રોડક્ટ ઉમેરવાનું ફોર્મ -->
    <form method="post" enctype="multipart/form-data">
      <div class="form-grid">
        <div class="form-group">
          <label>Product Name</label>
          <input name="pname" placeholder="દા.ત. શર્ટ, ઘડિયાળ" required autocomplete="off">
        </div>

        <div class="form-group">
          <label>Price (Rs.)</label>
          <input type="number" name="price" placeholder="0.00" required>
        </div>

        <div class="form-group">
          <label>Category</label>
          <select name="category" required>
            <option value="">Select Category</option>
            <?php
            $q = mysqli_query($conn, "SELECT * FROM category");
            while ($r = mysqli_fetch_assoc($q)) {
              echo "<option value='" . $r['id'] . "'>" . htmlspecialchars($r['cname']) . "</option>";
            }
            ?>
          </select>
        </div>

        <div class="form-group">
          <label>Product Image (JPG only)</label>
          <input type="file" name="image" accept="image/jpeg" required>
        </div>
      </div>

      <button name="add" class="btn-submit">Add Product</button>
    </form>

    <h3>🛒 Product List</h3>

    <div class="product-grid">
      <?php
      $q = mysqli_query($conn, "SELECT * FROM product ORDER BY id DESC");
      if (mysqli_num_rows($q) > 0) {
        while ($r = mysqli_fetch_assoc($q)) {
          echo "<div class='product-card'>";
          echo "<img src='uploads/" . htmlspecialchars($r['image']) . "' width='150' height='150' alt='product'>";
          echo "<div class='product-name'>" . htmlspecialchars($r['pname']) . "</div>";
          echo "<div class='product-price'>Rs. " . htmlspecialchars($r['price']) . "</div>";
          echo "<a href='product.php?delete=" . $r['id'] . "' class='btn-delete' onclick='return confirm(\"શું તમે ખરેખર આ પ્રોડક્ટ ડીલીટ કરવા માંગો છો?\")'>Delete</a>";
          echo "</div>";
        }
      } else {
        echo "<p style='grid-column: 1/-1; text-align: center; color: #9ca3af;'>હજી સુધી કોઈ પ્રોડક્ટ ઉમેરવામાં આવી નથી.</p>";
      }
      ?>
    </div>

    <div class="navigation-links">
      <a href="category.php">📂 Back to Categories</a>
      <a href="logout.php" style="color: var(--danger-color);">🚪 Logout</a>
    </div>
  </div>

</body>

</html>