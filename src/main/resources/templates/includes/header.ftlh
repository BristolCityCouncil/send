<!DOCTYPE html>
<html class="aui bcc-aui" lang="en-GB">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>SEND | Bristol City Council</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://style.bristol.gov.uk/bcc-beta-theme/css/base.css" />
    <link rel="stylesheet" href="https://style.bristol.gov.uk/bcc-beta-theme/css/aui.css" />
    <link rel="stylesheet" href="https://style.bristol.gov.uk/css/anti-aui.css" />
    <link rel="stylesheet" href="https://style.bristol.gov.uk/css/custom.css" />
    <link rel="stylesheet" href="/main.css" />
    <link rel="stylesheet" href="/fileupload.css" />
    <link rel="icon" type="image/png" href="/logo.png">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>   
  </head>

  <body>
    <header id="header">
      <div class="row-fluid">
        <a id="skip-to-content" href="#main-content" tabindex="1">
          <span>Skip to Content</span>
        </a>
      </div>
      <div id="header__inner" class="row-fluid">
        <div id="logo">
          <p>
            <a href="https://bristol.gov.uk" title="Bristol City Council"> Bristol City Council </a>
          </p>
        </div>
        <div id="nav">
          <p>
            <a href="https://bristol.gov.uk">bristol.gov.uk</a>
          </p>
        </div>
        <div class="myaccount">
          <div class="myaccount_links">
            <#if currentUser??>
              <span>Hello, ${currentUser.ownerName}</span>
              <span class="pipe"></span>
            </#if>
            <a id="myaccount__logout" href="/.auth/logout">Sign out</a>
          </div>
        </div>
      </div>
    </header>

    <main id="main">
      <div class="page-content" id="content">
        <div id="main-content">
          <div class="row-fluid">
            <div class="span12">
              