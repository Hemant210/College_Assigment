<?php
/* 
 * User Login Page (userlogin.php)
 * Handles secure user authentication using prepared statements and password_verify().
 */

// Start session to store logged-in user information
session_start();
include '../Admin/db.php';

$msg = "";
$msg_type = "";

if (isset($_POST['login'])) {
  $email = mysqli_real_escape_string($conn, $_POST['email']);
  $pass = $_POST['password'];

  // Secure user validation using prepared statements
  $q = mysqli_prepare(
    $conn,
    "SELECT * FROM users WHERE email=?"
  );

  mysqli_stmt_bind_param($q, "s", $email);
  mysqli_stmt_execute($q);

  $result = mysqli_stmt_get_result($q);
  $user = mysqli_fetch_assoc($result);

  // Verify password hash integrity
  if ($user && password_verify($pass, $user['password'])) {
    $_SESSION['user'] = $user['name'];
    $msg = "Login successful! Welcome, " . htmlspecialchars($user['name'], ENT_QUOTES, 'UTF-8');
    $msg_type = "success";

    // Optional: You can redirect the user to index.php after 2 seconds
    // header("refresh:2;url=index.php");
  } else {
    $msg = "Invalid email address or password.";
    $msg_type = "error";
  }
  mysqli_stmt_close($q);
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Login</title>
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
      height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .login-container {
      width: 100%;
      max-width: 400px;
      background: var(--card-bg);
      padding: 40px 30px;
      border-radius: 12px;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
      box-sizing: border-box;
    }

    h2 {
      margin-top: 0;
      margin-bottom: 25px;
      color: var(--text-color);
      font-size: 24px;
      text-align: center;
      font-weight: 600;
    }

    .form-group {
      margin-bottom: 20px;
    }

    .form-group label {
      display: block;
      margin-bottom: 6px;
      font-size: 14px;
      font-weight: 500;
      color: #4b5563;
    }

    input[type="email"],
    input[type="password"] {
      width: 100%;
      padding: 10px 14px;
      border: 1px solid var(--border-color);
      border-radius: 6px;
      font-size: 15px;
      outline: none;
      box-sizing: border-box;
      transition: border-color 0.2s, box-shadow 0.2s;
    }

    input:focus {
      border-color: var(--primary-color);
      box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
    }

    .btn-login {
      width: 100%;
      background-color: var(--primary-color);
      color: white;
      border: none;
      padding: 12px;
      border-radius: 6px;
      font-size: 16px;
      cursor: pointer;
      font-weight: 500;
      margin-top: 10px;
      transition: background-color 0.2s;
    }

    .btn-login:hover {
      background-color: var(--secondary-color);
    }

    /* Notification Alerts Layout */
    .alert {
      padding: 12px;
      border-radius: 6px;
      font-size: 14px;
      margin-bottom: 20px;
      text-align: center;
      font-weight: 500;
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

    .footer-links {
      text-align: center;
      margin-top: 20px;
      font-size: 14px;
    }

    .footer-links a {
      color: var(--primary-color);
      text-decoration: none;
      font-weight: 500;
    }

    .footer-links a:hover {
      text-decoration: underline;
    }
  </style>
</head>

<body>

  <div class="login-container">
    <h2>User Login</h2>

    <!-- Dynamic Notification Message System -->
    <?php if (!empty($msg)): ?>
      <div class="alert alert-<?php echo $msg_type; ?>">
        <?php echo $msg; ?>
      </div>
    <?php endif; ?>

    <form action="" method="post">
      <div class="form-group">
        <label for="email">Email Address</label>
        <input type="email" name="email" id="email" placeholder="username@example.com" required autocomplete="off">
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" name="password" id="password" placeholder="••••••••" required>
      </div>

      <button type="submit" name="login" class="btn-login">Login</button>
    </form>

    <div class="footer-links">
      Don't have an account? <a href="register.php">Register here</a>
    </div>
  </div>

</body>

</html>