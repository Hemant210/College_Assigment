<?php
/* 
 * Product Loader File (load.php)
 * Fetches products based on Category ID with Pagination and modern CSS Grid layout.
 */

// Include database connection
include '../Admin/db.php';

// Sanitize and validate incoming GET requests
$id = isset($_GET['id']) ? (int)$_GET['id'] : 0;
$page = isset($_GET['page']) ? max(1, (int)$_GET['page']) : 1;

// Set record limit per page
$limit = 2;
$start = ($page - 1) * $limit;

// Fetch filtered products using LIMIT clause for pagination
$q = mysqli_query(
  $conn,
  "SELECT * FROM product
     WHERE cid=$id
     LIMIT $start, $limit"
);

// Check if records exist
if (mysqli_num_rows($q) > 0) {
  // Start Product Grid Container
  echo "<div class='product-grid'>";

  while ($r = mysqli_fetch_assoc($q)) {
    // Generate secure clean variables
    $product_name = htmlspecialchars($r['pname'], ENT_QUOTES, 'UTF-8');
    $product_price = htmlspecialchars($r['price'], ENT_QUOTES, 'UTF-8');
    $product_image = htmlspecialchars($r['image'], ENT_QUOTES, 'UTF-8');

    // Output modern product card layout
    echo "<div class='product-card'>";
    echo "<div class='img-box'>";
    echo "<img src='../Admin/uploads/" . $product_image . "' alt='" . $product_name . "'>";
    echo "</div>";
    echo "<div class='product-info'>";
    echo "<h4 class='product-title'>" . $product_name . "</h4>";
    echo "<p class='product-price'>Rs. " . $product_price . "</p>";
    echo "<a href='#' class='btn-buy'>Add to Cart</a>";
    echo "</div>";
    echo "</div>";
  }

  echo "</div>"; // End Product Grid Container
} else {
  // English message when no records found
  echo "<p class='no-data-msg'>No products available in this category.</p>";
}
?>

<!-- Modern CSS Grid Layout for Products -->
<style>
  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: 20px;
    padding: 15px 0;
  }

  .product-card {
    background: #ffffff;
    border: 1px solid #e5e7eb;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    display: flex;
    flex-direction: column;
  }

  .product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
    border-color: #4f46e5;
  }

  .product-card .img-box {
    width: 100%;
    height: 180px;
    background-color: #f9fafb;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom: 1px solid #f3f4f6;
  }

  .product-card .img-box img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
  }

  .product-card .product-info {
    padding: 15px;
    display: flex;
    flex-direction: column;
    flex-grow: 1;
  }

  .product-card .product-title {
    font-size: 16px;
    font-weight: 600;
    color: #1f2937;
    margin: 0 0 8px 0;
  }

  .product-card .product-price {
    font-size: 15px;
    font-weight: 700;
    color: #4f46e5;
    margin: 0 0 15px 0;
  }

  .product-card .btn-buy {
    display: block;
    text-align: center;
    background-color: #4f46e5;
    color: #ffffff;
    text-decoration: none;
    padding: 10px 0;
    border-radius: 6px;
    font-size: 14px;
    font-weight: 500;
    margin-top: auto;
    transition: background-color 0.2s ease;
  }

  .product-card .btn-buy:hover {
    background-color: #4338ca;
  }

  .no-data-msg {
    text-align: center;
    color: #9ca3af;
    font-size: 16px;
    padding: 30px;
    grid-column: 1 / -1;
  }
</style>