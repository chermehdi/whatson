<%@ include file="../partials/header.jsp" %>
<div class="container main">
    <div class="row">
        <div class="col-md-7">
            <p class="lead big-text"> Ideas matter, news count </p>
            <p class="smaller-text"> all you have to do is write it up </p>
            <a class="btn btn-primary" href="#">Get Started</a>
        </div>
        <div class="col-md-5">
            <!-- signup/form -->
            <div class="row">
                <div class="col-md-12 social-container">
                    <button class="btn btn-social blue"><i class="zmdi zmdi-facebook"></i> facebook</button>
                    <button class="btn btn-social red" id="google"><i class="zmdi zmdi-google"></i> google</button>
                    <!--    <div class="g-signin2" data-width="300" data-onload="false" data-onsuccess="onSignIn"></div>-->
                </div>
            </div>
            <form method="POST">
                <div class="form-group">
                    <label for="InputEmail">Email address</label>
                    <input type="email" name="email" class="form-control" id="InputEmail" aria-describedby="emailHelp"
                           placeholder="Enter email">
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.
                    </small>
                </div>
                <div class="form-group">
                    <label for="InputPassword">Password</label>
                    <input type="password" name="password" class="form-control" id="InputPassword"
                           placeholder="Password">
                </div>
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input">
                        remember me for a month
                    </label>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <button type="submit" class="btn btn-primary" style="width: 100%">Sign in</button>
                    </div>
                    <div class="col-md-6"  style="text-align: right"><a href="<c:url value="/signup"/>"> don't have Account ? </a></div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="../partials/footer.jsp" %>