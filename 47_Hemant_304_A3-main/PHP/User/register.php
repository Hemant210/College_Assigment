<?php
/* 
 * User Registration Page (register.php)
 * Handles user sign-up with password hashing, secure prepared statements, and mathematical CAPTCHA validation.
 */

// Start session at the very beginning to handle CAPTCHA validation safely
session_start();
include '../Admin/db.php';

$msg = "";
$msg_type = "";

// Process form submission
if (isset($_POST['register'])) {
  // Check if session CAPTCHA is set and matches the user input
  if (!isset($_SESSION['captcha']) || $_POST['captcha'] != $_SESSION['captcha']) {
    $msg = "Invalid CAPTCHA code. Please try again.";
    $msg_type = "error";
  } else {
    $name = mysqli_real_escape_string($conn, $_POST['name']);
    $email = mysqli_real_escape_string($conn, $_POST['email']);
    $pass = password_hash($_POST['password'], PASSWORD_DEFAULT);

    // Secure database insert operation using prepared statements
    $q = mysqli_prepare(
      $conn,
      "INSERT INTO users(name,email,password) VALUES(?,?,?)"
    );

    mysqli_stmt_bind_param($q, "sss", $name, $email, $pass);

    if (mysqli_stmt_execute($q)) {
      $msg = "Registration successful! You can now log in.";
      $msg_type = "success";
      // Clear CAPTCHA after successful registration to prevent reuse
      unset($_SESSION['captcha']);
    } else {
      $msg = "Email already exists or registration failed.";
      $msg_type = "error";
    }
    mysqli_stmt_close($q);
  }
}

// Generate new random math CAPTCHA values for security
$a = rand(1, 9);
$b = rand(1, 9);
$_SESSION['captcha'] = $a + $b;
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Registration</title>
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

    .register-container {
      width: 100%;
      max-width: 420px;
      background: var(--card-bg);
      padding: 35px 30px;
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
      margin-bottom: 18px;
    }

    .form-group label {
      display: block;
      margin-bottom: 6px;
      font-size: 14px;
      font-weight: 500;
      color: #4b5563;
    }

    input[type="text"],
    input[type="email"],
    input[type="password"],
    input[type="number"] {
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

    /* Captcha Section Layout */
    .captcha-box {
      background-color: #f9fafb;
      border: 1px dashed var(--border-color);
      padding: 12px;
      border-radius: 6px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      gap: 15px;
    }

    .captcha-label {
      font-weight: 600;
      color: var(--primary-color);
      font-size: 16px;
      white-space: nowrap;
    }

    .btn-register {
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

    .btn-register:hover {
      background-color: var(--secondary-color);
    }

    /* Notification Alert System */
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

  <div class="register-container">
    <h2>Create Account</h2>

    <!-- Alert Messages Block -->
    <?php if (!empty($msg)): ?>
      <div class="alert alert-<?php echo $msg_type; ?>">
        <?php echo $msg; ?>
      </div>
    <?php endif; ?>

    <form action="" method="post">
      <div class="form-group">
        <label for="name">Full Name</label>
        <input type="text" name="name" id="name" placeholder="John Doe" required autocomplete="off">
      </div>

      <div class="form-group">
        <label for="email">Email Address</label>
        <input type="email" name="email" id="email" placeholder="john@example.com" required autocomplete="off">
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" name="password" id="password" placeholder="••••••••" required>
      </div>

      <div class="form-group">
        <label>Security Verification</label>
        <div class="captcha-box">
          <span class="captcha-label">Solve: <?php echo $a . " + " . $b; ?> =</span>
          <input type="number" name="captcha" placeholder="Answer" required style="width: 120px; margin: 0;">
        </div>
      </div>

      <button type="submit" name="register" class="btn-register">Register</button>
    </form>

    <div class="footer-links">
      Already have an account? <a href="userlogin.php">Login here</a>
    </div>
  </div>

</body>

</html>