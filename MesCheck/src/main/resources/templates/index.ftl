<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>登录</title>
    <link rel="stylesheet" href="../mesCheck/static/css/mui.css">
    <script type="text/javascript" src="../mesCheck/static/jquery-3.3.1.min.js"></script>
    <script src="../mesCheck/static/js/mui.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="../mesCheck/static/js/mui.picker.min.js" type="text/javascript" charset="utf-8"></script>
    <script>
        setSize();
        addEventListener('resize',setSize);
        function setSize() {
            document.documentElement.style.fontSize = document.documentElement.clientWidth/750*100+'px';
        }
    </script>
    <style>
        body{
            background:#f3f4f5;
            font-size:16px;
        }
        .w{
            box-sizing:border-box;
            padding:0 28px;
        }
        .login-header{
            width:100%;
            height:140px;
            position:relative;
            padding-top:26px;
            box-sizing: border-box;
            background: -webkit-linear-gradient(left,#64dff3, #6a85fa); /* Safari 5.1 - 6.0 */
            background: -o-linear-gradient(left,#64dff3, #6a85fa); /* Opera 11.1 - 12.0 */
            background: -moz-linear-gradient(left,#64dff3, #6a85fa); /* Firefox 3.6 - 15 */
            background: linear-gradient(left,#64dff3, #6a85fa); /* 标准的语法 */
        }
        .login-log{
            background:url('../mesCheck/static/images/yiddxyzxt_20.png');
            background-size:cover;
            width:137px;
            height:57px;
        }
        .login-tab{
            width:190px;
            position:absolute;
            left:50%;
            transform: translateX(-50%);
            bottom:3px;
        }
        .login-tab-btn{
            width:95px;
            float:left;
            color:#c2e3fb;
            padding-bottom:13px;
            text-align:center;
        }
        .login-tab-btn.action{
            color:#fff;
            border-bottom:3px solid #fff;
        }
        .login-content,.login-content-qy{
            padding-top:10px;
        }
        .login-content-qy{
            display: none;
        }
        .login-input-box{
            padding-top:40px;
            border-bottom:1px solid #ccc;
            position:relative;
        }
        .login-input{
            height:auto;
            border:none;
            padding: 0 10px;
            background:#f3f4f5;
            font-size:16px;
            margin-bottom: 18px;
        }
        .login-input-phone-yz{
            width:60%;
        }
        .login-btn{
            display: block;
            margin:45px auto;
            margin-bottom:0;
            width:98%;
            height:52px;
            line-height:42px;
            text-align:center;
            color:#fff;
            background:url('../mesCheck/static/images/ann_20_z.png') no-repeat;
            background-size:100%;
        }
        /* width:320px;;
          height:45px;
          background:url('./images/ann_20_x.png'); */
        a.login-btn:link{

        }
        a.login-btn:visited{

        }
        a.login-btn:hover{

        }
        a.login-btn:active{
            color:#fff;
            width:98%;
            height:45px;
            background:url('../mesCheck/static/images/ann_20_x.png') no-repeat;
            background-size:100%;
        }
        a.login-btn:active .rz{
            margin-right:-4px;
        }
        .login-phone-yz-btn{
            display:block;
            width:120px;
            height:42px;
            line-height:36px;
            text-align:center;
            color:#fff;
            background:url('../mesCheck/static/images/huoqyzm_20_z.png');
            position:absolute;
            background-size:cover;
            right:0;
            top:24px;
        }
        a.login-phone-yz-btn:link{

        }
        a.login-phone-yz-btn:visited{

        }
        a.login-phone-yz-btn:hover{

        }
        a.login-phone-yz-btn:active{
            color:#fff;
            width:120px;
            height:36px;
            background:url('../mesCheck/static/images/huoqyzm_20_x.png');
            background-size:cover;
        }
        .login-yz-btn{
            width:120px;
            height:36px;
            border-radius:18px;
            position:absolute;
            right:0;
            bottom:11px;
            background: url('../mesCheck/static/images/yanzm_20.png') no-repeat;
            background-size:cover;
            overflow: hidden;
            box-sizing:border-box;
            text-align:center;
        }
        .login-yz-btn img{
            width:95%;
            height:32px;
            margin:2px auto;
            border-radius:16px;
        }
        .login-tip{
            font-size:16px;
            color:#675f5f;
            margin-top:30px;
        }
        .login-tip p{
            text-indent:2em;
            font-size:16px;
            color:#675f5f;
            margin:0;
            padding:0;
            margin-top:10px;
        }
        .login-register-box{
            margin-top:12px;
            text-align:center;
        }
        .login-forget-password{
            position: absolute;
            right:0;
            top:40px;
            color:#6994f8;
            font-size:16px;
        }
        .login-input-half{
            width:65%;
        }
    </style>
</head>
<body>
    <div class="login-header w">
        <div class="login-log">

        </div>
        <!-- 点击个人登录/企业登录切换按钮 -->
        <div class="login-tab">
            <div class="login-tab-btn action" data-loginFlag="1">
                个人登录
            </div>
            <div class="login-tab-btn" data-loginFlag="2">
                企业登录
            </div>
        </div>
    </div>
        <!-- 个人登录 -->
        <div class="login-content w">

            <div class="login-input-box">
                <div class="mui-input-row">
                    <input type="text" class="login-input mui-input-clear" placeholder="请输入手机号" id="userTel" name="userTel">
                </div>
            </div>

            <div class="login-input-box">
                <div class="mui-input-row">
                    <input type="text" id="passwords" name="passwords" class="login-input login-input-half login-input-phone-yz" placeholder="请输入手机验证码">
                </div>
                <a class="login-phone-yz-btn getPasswordBtn" data-f="1">
                    获取验证码
                </a>
            </div>

            <div style="height:52px;">
                <a onclick="phoneLogin()" class="login-btn loginbtn">
                    <span>登</span>
                    <span class="rz" style="margin-left:13px;">录</span>
                </a>
            </div>

            <div class="login-tip">
                温馨提示：
                <div>
                    <p>本系统用于移动短信合法性验证；</p>
                    <p>主要针对伪基站下发的垃圾行业短信；</p>
                    <p>仅支持移动用户手机登录；</p>
                </div>
            </div>
    	</div>
        <!-- /个人登录 -->
        <!-- 企业登录 -->
        <div class="login-content-qy w">


               <div class="login-input-box">
                   <div class="mui-input-row">
                       <input type="text" id="userTel2" name="username" class="login-input mui-input-clear" placeholder="请输入手机号">
                   </div>
               </div>


            <div class="login-input-box">
                <div class="mui-input-row">
                    <input type="password" id="password" name="password" class="login-input login-input-half" placeholder="请输入密码">
                </div>
                <div class="login-forget-password" onclick="forgetpassword()">
                    忘记密码？
                </div>
            </div>

            <div class="login-input-box">
                <div class="mui-input-row">
                    <input type="text" id="verify_input" name="verify_input" class="login-input login-input-half login-input-yz" placeholder="请输入验证码">
                </div>
                <div class="login-yz-btn">
                    <img id="imgVerify" onclick="getVerify();"  src="" alt="验证图片">
                </div>
            </div>

            <div style="height:52px;">
                <a href="javascript:;" class="login-btn loginbtn" onclick="checkAndLogin()">
                    <span>登</span>
                    <span class="rz" style="margin-left:13px;">录</span>
                </a>
            </div>
            <div class="login-register-box">
                <a class="login-register-box" href="javascript:;" onclick="register()">
                    <span>注</span>
                    <span style="margin-left:13px;">册</span>
                </a>
            </div>

            <div class="login-tip">
                温馨提示：
                <div>
                    <p>本系统用于移动短信合法性验证；</p>
                    <p>主要针对伪基站下发的垃圾行业短信；</p>
                    <p>仅支持移动用户手机登录；</p>
                </div>
            </div>
        </div>
</body>
<script>

    function getVerify(){
        var arrUrl = window.location.href.split("?");
        var para = arrUrl[0];
        para=para.substring(0,para.indexOf('/index')+1);
        $("#imgVerify").attr("src", para + "getVerify?"+Math.random());
    }
    $(function(){
        getVerify();
        mui('body').on('tap', '.login-tab-btn', function (e) {
            $(e.target).addClass('action').siblings().removeClass('action');
            if($(e.target).attr('data-loginFlag') == "1"){
                $(".login-content").show();
                $(".login-content-qy").hide();
            }else if($(e.target).attr('data-loginFlag') == "2"){
                $(".login-content").hide();
                $(".login-content-qy").show();
            }
        })
    })

    var reg = /^1[0-9]{10}$/;  //电话号码的正则匹配式
    var reg2 = /[0-9]{4}$/;
    $(document).ready(function () {
        var ordertime = 60;  //设置再次发送验证码等待时间
        var timeleft = ordertime;
        //计时函数
        function timeCount() {
            timeleft -= 1;
            if (timeleft > 0) {
                $(".getPasswordBtn").text(timeleft + " 秒后重发");
                $(".getPasswordBtn").unbind();
                setTimeout(timeCount, 1000)
            }
            else {
                $(".getPasswordBtn").text("重新发送");
                timeleft = ordertime;  //重置等待时间
                $(".getPasswordBtn").click(function(){
                    getyz()
                })
            }
        }

        //事件处理函数
        $(".getPasswordBtn").on("click", function () {
            getyz()
        })
        function getyz(){
            //防止多次点击
                    $(".getPasswordBtn").attr('data-f','2');
                    if(reg.test($("#userTel").val())){
                        $.ajax({
                            type: "GET",
                            dataType: 'json',
                            url: "getVerCode?phoneNum=" + $("#userTel").val(),
                            success: function (result) {
                                if (result.resultCode == "200") {
                                    mui.alert('获取验证码成功')
                                }
                                else{
                                    if (result.resultMes!=""){
                                        mui.alert(result.resultMes);
                                    } else {
                                        mui.alert('验证码发送失败，请稍后再尝试')
                                    }
                                    return;

                                }
                            }

                        });
                    }else{
                        mui.alert('手机号码格式错误');
                        return;
                    }

            //此处可添加 ajax请求 向后台发送 获取验证码请求
            timeCount(this);
        }
    });

    function phoneLogin() {
        if (reg.test($("#userTel").val())) {
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: "login",
                data:{phoneNum: $("#userTel").val(),authCode:$("#passwords").val()},
                success: function (result) {
                    if (result.resultCode == "200") {
                        var arrUrl = window.location.href.split("?");
                        var para = arrUrl[0];
                        para=para.substring(0,para.indexOf('/index')+1);
                        window.location.href = para+"check";
                    }
                    else {
                        mui.alert(result.resultMes);
                    }
                }
            });

        }
        else {
            mui.alert('手机号码格式不正确');
        }
    }
    function phoneLogin2() {

        if (reg.test($("#userTel2").val())) {
            if($("#password").val().length<=0){
                mui.alert('密码不能为空');
            }else{
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: "loginCompany",
                data:{inputStr: $("#verify_input").val(),phoneNum: $("#userTel2").val(),password:$("#password").val()},
                success: function (result) {
                    if (result.resultCode == "200") {
                        var arrUrl = window.location.href.split("?");
                        var para = arrUrl[0];
                        para=para.substring(0,para.indexOf('/index')+1);
                        window.location.href = para+"check";
                    }
                    else if(result.resultCode == "206"||result.resultCode == "205"){
                        mui.alert(result.resultMes,function () {
                            var arrUrl = window.location.href.split("?");
                            var para = arrUrl[0];
                            para=para.substring(0,para.indexOf('/index')+1);
                            window.location.href = para+"register";
                        });

                    }
                    else {
                        getVerify();
                        mui.alert(result.resultMes);
                    }
                }
            });}

        }
        else {
            mui.alert('手机号码格式不正确')


        }
    }
    function checkAndLogin() {
      if(reg2.test($("#verify_input").val())){
          phoneLogin2();
      }else{
          mui.alert('验证码格式错误');
      }

    }
    function register() {
        var arrUrl = window.location.href.split("?");
        var para = arrUrl[0];
        para=para.substring(0,para.indexOf('/index')+1);
        window.location.href = para+"register";
    }
    /*忘记密码*/
    function  forgetpassword() {
        var arrUrl = window.location.href.split("?");
        var para = arrUrl[0];
        para=para.substring(0,para.indexOf('/index')+1);
        window.location.href = para+"forgetpassword";
    }
</script>
</html>