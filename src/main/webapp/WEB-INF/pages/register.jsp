<%@ include file="../partials/header.jsp" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value="/assets/css/register.css" />">
<div class="container">
    <div class="flex-container">
        <div class="row full">
            <div class="col-md-12">
                <div class="form-container">
                    <div class="form-container-in">
                    </div>
                    <div class="log-container">
                        <img id="logo" src="<c:url value="/assets/img/logo.png"/>" alt="">
                        <div class="para">
                            <p class="lead mg">
                                Join our social network, for 2.0 journalism
                            </p>
                        </div>
                    </div>

                    <div class="row">

                        <div class="col-md-6">
                            <h3 class="lead-text"> Join Us with social media </h3>
                            <div id="social-platforms">
                                <form action="<c:url value="/facebook"/>" method="POST">
                                    <div class="media-container">
                                        <button type="submit" style="padding: 0" class="btnn btnn-icon btnn-facebook"
                                                href="#"><i
                                                class="fa fa-facebook"> </i><span>Facebook</span></button>
                                    </div>
                                </form>
                                <form action="<c:url value="/google"/>" method="POST">
                                    <div class="media-container">
                                        <button style="padding: 0" type="submit" class="btnn btnn-icon btnn-google"
                                                href="#"><i class="fa fa-google">
                                        </i><span>Google+</span></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="left-divider"></div>
                        <div class="col-md-6">
                            <form action="<c:url value="/signup"/>" method="POST">
                                <div class="form-group">
                                    <label for="fname">First Name :</label>
                                    <input id="fname" name="firstName" type="text" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="lname">Last Name :</label>
                                    <input id="lname" type="text" name="lastName" class="form-control">
                                </div>

                                <div class="form-group">
                                    <label for="email">Email :</label>
                                    <input id="email" type="text" name="email" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="password">Password :</label>
                                    <input id="password" type="password" name="password" class="form-control">
                                </div>
                                <div class="form-group">
                                    <input type="submit" value="submit" class="btn-primary btnn form-submit"/>
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

</div>
<%@ include file="../partials/footer.jsp" %>