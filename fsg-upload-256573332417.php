<?php

/**
 * Configuration V3.00
 */

/** Where to put a new sitemap */
$SITEMAPDIR = "./";		

/**
  * No changes needed below this line
  * ==================================================================================
  */

/**
  * This file receives the newly created sitemaps
  * by www.freesitemapgenerator.com
  *
  * Please do not make any changes
  *
  * (c) 2015 www.freesitemapgenerator.com
  *
  * This product comes with ABSOLUTELY NO WARRANTY, to the extent
  * permitted by applicable law.
  *
  * Use at own risk, we cannot be held responsable for any damages
  * caused directly or indirectly by installing this sofware.
  */

$FSG_VERSION = "3.00";					// fsg-upload version
$FSG_UNIQUEID = "fd994595f0dd31a459d0eb9ddb32fcc0";	// unique identifier

/**
  * Act like a 404 page if FSG_UNIQUEID is incorrect or method is not POST
  */
if ((count($_POST) != 4 && count($_POST) !=5) || $_POST["uniqueid"] != $FSG_UNIQUEID)
{
  /**
    * Produce 404 error message
    */
  header("HTTP/1.1 404 Not Found");
 
  echo   "<HTML><HEAD><META NAME=\"ROBOTS\" CONTENT=\"NOINDEX,FOLLOW\"><META ".
	 "HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=utf-8\">".
	 "<TITLE>404 Not Found</TITLE>".
	 "</HEAD><BODY>".
	 "<H1>404 Page not found</H1>".
	 "Go to the <A HREF=\"/\">Homepage</A>".
	 "</BODY></HTML>";

  die();
}

/**
  * Check version
  */
if ($_POST["minversion"] > $FSG_VERSION)
{
  /**
    * We need to upgrade!
    */
  error_log("fsg-update.php: You need to upgrade to a newer version of fsg-update.");
  echo "error=101\n";
  die();
}

/**
 * Check command
 */
if ($_POST['cmd'] == 'getversion') {
  echo $FSG_VERSION;
  die();
}

$outfile = $_POST["file"];
// remove slashes from file, just to make sure
$outfile = str_replace("/", "", $outfile);

if (substr($outfile, 0,7) != "sitemap") {
  error_log("fsg-upload.php: eeks. upload of $outfile tried");
  echo "error=103";
  die();
}

$outdata= @$_POST["data"];
$outdata = str_replace("\\", "", $outdata); 

if ($_POST['cmd'] == 'truncate') {
  $fp = @fopen($SITEMAPDIR .  "/" . $outfile, "wb");
} elseif ($_POST['cmd'] == 'append') {
  $fp = @fopen($SITEMAPDIR . "/" . $outfile, "ab");
} elseif ($_POST['cmd'] == 'appendfile') {
  $fp = @fopen($SITEMAPDIR . "/" . $outfile, "ab");
  $outdata = file_get_contents($_FILES['data']['tmp_name']);
} else {
  error_log("unknown command");
  echo "error=155";
  die();
}

if (!$fp) {
  /**
    * File permissions ?
    */
  error_log("fsg-upload.php: Could not write to $SITEMAPDIR/$outfile");
  echo "error=100";
  die();
}

@fwrite($fp, $outdata);
@fclose($fp);

/**
  * Write done!
  */
error_log("fsg-upload.php: Received OK '" . $outfile . "', cmd = " . $_POST['cmd']);
echo "error=0";
die();
