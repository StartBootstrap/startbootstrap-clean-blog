document.getElementById("mainNav").innerHTML =
 '<div class="container">'+


   '<a class="navbar-brand" href="index.html">Maegan Jong</a>' +
    '<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">' +
    'Menu'+
     '<i class="fas fa-bars"></i>'+
   '</button>'+
   '<div class="collapse navbar-collapse" id="navbarResponsive">'+
     '<ul class="navbar-nav ml-auto">'+

     '<li class="nav-item dropdown">'+
       '<a class="nav-link" href="post.html" >Service</a>'+
     '</li>'+

       '<li class="nav-item dropdown">'+
         '<a class="nav-link" href="writing.html" >Writing</a>'+
       '</li>'+


       '<li class="nav-item"> <a class="nav-link" href="about.html">About</a></li></ul></div></div>' ;

document.getElementById("footer").innerHTML =
'<div class="container">'+
  '<div class="row">'+
  '  <div class="col-lg-8 col-md-10 mx-auto">'+
      '<ul class="list-inline text-center">'+
      '  <li class="list-inline-item">'+
          '<a href="mailto:maegan.jong@gmail.com">'+
            '<span class="fa-stack fa-lg">'+
              '<i class="fas fa-circle fa-stack-2x"></i>'+
              '<i class="fa fa-envelope-square fa-stack-1x fa-inverse"></i>'+
            '</span>'+
          '</a>'+
        '</li>'+
        '<li class="list-inline-item">'+
          '<a href="https://www.facebook.com/maeganjong">'+
          '  <span class="fa-stack fa-lg">'+
          '    <i class="fas fa-circle fa-stack-2x"></i>'+
          '    <i class="fab fa-facebook-f fa-stack-1x fa-inverse"></i>'+
          '  </span>'+
          '</a>'+
        '</li>'+
        '<li class="list-inline-item">'+
        '  <a href="https://github.com/maeganjong">'+
        '    <span class="fa-stack fa-lg">'+
        '      <i class="fas fa-circle fa-stack-2x"></i>'+
        '      <i class="fab fa-github fa-stack-1x fa-inverse"></i>'+
        '    </span>'+
          '</a>'+
        '</li>'+
        '<li class="list-inline-item">'+
        '  <a href="https://www.linkedin.com/in/maegan-jong/">'+
        '    <span class="fa-stack fa-lg">'+
        '      <i class="fas fa-circle fa-stack-2x"></i>'+
        '      <i class="fab fa-linkedin fa-stack-1x fa-inverse"></i>'+
        '    </span>'+
          '</a>'+
        '</li>'+
      '</ul>'+
      '</div>'+
  '</div>'+
'</div>';
