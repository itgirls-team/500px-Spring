<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>

<link href="<c:url value="/static/css/styleError.css" />" rel="stylesheet" type="text/css">
<script src="/static/js/jquery-1.8.3.js" type = "text/javascript"></script>

</head>
	<body id="errorpage" class="error404">
        <div id="pagewrap">
            <!--Header Start-->
            <div id="header" class="header">
          
            </div><!--Header End-->
			<!-- Plane -->
			<div id="main-content">
				<div class="duck-animation" style="background-color : black; margin-top:30px;"></div>
				<!-- mg src="<c:url value="/static/genres/POP.jpg" />" />-->
			</div>
			<!--page content-->
            <div id="wrapper" class="clearfix">
                <div id="parallax_wrapper">
                    <div id="content">
                        <h1>Oh Sorry :( </h1>
                        <p style = "background-color : black;">The page you're looking for is not here.</p>
                        <a href="http://localhost:8080/page/main" title="" class="button">Go Home</a>
                    </div>
					<!--parallax-->
                    <span class="scene scene_1"></span>
                    <span class="scene scene_2"></span>
                    <span class="scene scene_3"></span>
                </div>
            </div>

        </div><!-- end pagewrap -->

		<!--page footer-->
        <div id="footer">
            <div class="container">
                <ul class="copyright_info">
                    <li>&copy; 2017 500px</li>
					<li>&middot;</li>
					<li>Made by Snezhi and Vesi.</li>
                </ul>
				<!--social links-->

            </div>
        </div>
        
<!--end page footer-->
<script type="text/javascript">
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-43553901-1', 'squirrellabs.net');
  ga('send', 'pageview');
</script>

<script type="text/javascript">
$(function(e){
	var x=document.documentElement.clientHeight;
	var y=e(".header").outerHeight();
	e("#parallax_wrapper").css("height",x-y+"px");
	e("#parallax_wrapper").css("left",50+"%");
	e(".scene_1").plaxify({"xRange":0,"yRange":0,"invert":true}),
	e(".scene_2").plaxify({"xRange":70,"yRange":20,"invert":true}),
	e(".scene_3").plaxify({"xRange":0,"yRange":40,"invert":true}),
	e.plax.enable();
	e(document).ready(function(){});
	window.setInterval(function(){
  $('.duck-animation').delay(0).css('background-position-x','-1000px').animate({'background-position-x':'2000px'},20000,'linear');
}, 100);
});
</script>

</body>
</html>