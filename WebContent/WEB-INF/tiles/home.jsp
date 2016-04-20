<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
 <!-- Bootstrap Core CSS -->
   
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/bootstrap-3.3.6-dist/css/bootstrap.min.css">
    <!-- Custom CSS -->
 <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/bootstrap-3.3.6-dist/css/one-page-wonder.css">
    
    
     <!-- Full Width Image Header -->
    <header class="header-image">
        <div class="headline">
            <div class="container">
                <h2>Cooking</h2>
                <h3>Made Easy</h3>
            </div>
        </div>
    </header>

    <!-- Page Content -->
    <div class="container">

        <hr class="featurette-divider">

        <!-- First Featurette -->
        <div class="featurette" id="about">
            <img class="featurette-image img-circle img-responsive pull-right" src="${pageContext.request.contextPath}/static/images/pic.jpg">
            <h2 class="featurette-heading">Dont Know What To Cook?
                <span class="text-muted">Try The Recipe Generator </span>
            </h2>
            <p class="lead">All you have to do is enter the ingredients you have at home, and the generator will pick the best suited recipes for you.</p>
        </div>

        <hr class="featurette-divider">

        <!-- Second Featurette -->
        <div class="featurette" id="services">
            <img class="featurette-image img-circle img-responsive pull-left" src="${pageContext.request.contextPath}/static/images/pic3.jpg">
            <h2 class="featurette-heading">Plan Your Meals
                <span class="text-muted">With The Recipe Planner </span>
            </h2>
            <p class="lead">To help orgainise your meals for the week. Planning your meals will help you eat healthier, shop more efficently and will save you money.</p>
        </div>

        <hr class="featurette-divider">

        <!-- Third Featurette -->
        <div class="featurette" id="contact">
            <img class="featurette-image img-circle img-responsive pull-right" src="${pageContext.request.contextPath}/static/images/pic2.jpg">
            <h2 class="featurette-heading">Save Money
                <span class="text-muted">By Using the Shopping List.</span>
            </h2>
            <p class="lead">Using your recipe planner will add the ingredients of any recipe saved to your shopping list. Using the shopping list will save you time, reduce waste and will help you eat healthier.</p>
        </div>

        <hr class="featurette-divider">

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    
    <style>
    .header-image {
    display: block;
    width: 100%;
    text-align: center;
    background: url('${pageContext.request.contextPath}/static/images/home2.jpg') no-repeat center center scroll;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    background-size: cover;
    -o-background-size: cover;
    }
    </style>

