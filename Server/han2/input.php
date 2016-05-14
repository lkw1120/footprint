<?php

  $showbutton = TRUE;

  if (isset($_POST['submit'])) { // If form submitted

    // Get vars
    $lat = $_POST['latitude'];
    $lng = $_POST['longitude'];

    // Check string lengths
    if (strlen($lat) < 6) {
      $problem = TRUE;
      $response0 = "LATITUDE too short.<br />";
    }
    if (strlen($lng) < 6) {
      $problem = TRUE;
      $response1 = "LONGITUDE too short.<br />";
    }

    // Check if numeric
    if(!is_numeric($lat)) {
      $problem = TRUE;
      $response0a = "LATITUDE not numeric.<br />";
    }
    if(!is_numeric($lng)) {
      $problem = TRUE;
      $response1a = "LONGITUDE not numeric.<br />";
    }

    if (!$problem) { // If no problem, connect to database
      require("path/to/your_connection_script.php");
      // Build MySQL query
      $query = "INSERT INTO markers (lat, lng, datetime) 
      VALUES ('$latitude', '$longitude', NOW())";
      // Run query
      $result = @mysql_query ($query);
      // Check result
      if ($result) {
        mysql_close();
        $response2 = "MySQL query OK.<br />";
      } else { // No result
        $response2 = "MySQL query didn't run.<br />";
      }
      $response3 = "Co-ordinates entered."; // End
      $showbutton = FALSE;
    } else { // Problem
      $response3 = "Try again."; // End
    }

  }

?>
<form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post">
<input type="text" name="lat" size="12" maxlength="12" value="
<?php if (isset($_POST['submit'])) echo $_POST['latitude']; ?>" 
tabindex="1"><br />
<input type="text" name="longitude" size="12" maxlength="12" value="
<?php if (isset($_POST['submit'])) echo $_POST['longitude']; ?>" 
tabindex="2"><br />
<?php
  if ($showbutton == TRUE) {
 // Only show the Insert button if form not yet submitted
?>
<input type="submit" name="submit" value="Insert" tabindex="3">
<?php } ?>
</form>
<?php

  // Response section
  if (isset($_POST['submit'])) {
    echo "\n";
    echo $response0;
    echo $response0a;
    echo $response1;
    echo $response1a;
    echo $response2;
    echo $response3;
  }

?>