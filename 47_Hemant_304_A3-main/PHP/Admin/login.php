<?php
session_start();
include('db.php');

$error_msg = "";

if (isset($_POST['login'])) {
  $email = mysqli_real_escape_string($conn, $_POST['email']);
  $password = mysqli_real_escape_string($conn, $_POST['password']);

  $qry = "select * from admin where email='$email' and password='$password'";
  $result = mysqli_query($conn, $qry);

  if (mysqli_num_rows($result) > 0) {
    $_SESSION['admin'] = $email;
    header("Location: category.php");
    exit;
  } else {
    $error_msg = "ઇમેઇલ અથવા પાસવર્ડ ખોટો છે!";
  }
}
?>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Login</title>
  <style>
    :root {
      --primary-color: #4f46e5;
      --secondary-color: #4338ca;
      --bg-color: #f3f4f6;
      --card-bg: #ffffff;
      --text-color: #1f2937;
      --border-color: #e5e7eb;
      --danger-color: #ef4444;
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

    input[type="email"]:focus,
    input[type="password"]:focus {
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

    .error-box {
      background-color: #fee2e2;
      color: var(--danger-color);
      padding: 10px 12px;
      border-radius: 6px;
      font-size: 14px;
      margin-bottom: 20px;
      text-align: center;
      border: 1px solid #fca5a5;
    }
  </style>
</head>

<body>

  <div class="login-container">
    <h2>Admin Login</h2>

    <?php if (!empty($error_msg)): ?>
      <div class="error-box">
        <?php echo $error_msg; ?>
      </div>
    <?php endif; ?>

    <form action="" method="post">
      <div class="form-group">
        <label for="email">Email Address</label>
        <input type="email" name="email" id="email" placeholder="admin@example.com" required autocomplete="off">
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" name="password" id="password" placeholder="••••••••" required>
      </div>

      <button type="submit" name="login" class="btn-login">Login</button>
    </form>
  </div>

</body>

</html>