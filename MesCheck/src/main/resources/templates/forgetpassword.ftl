<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>忘记密码</title>
    <link rel="stylesheet" href="../mesCheck/static/css/mui.css">
    <script type="text/javascript" src="../mesCheck/static/jquery-3.3.1.min.js"></script>
    <script src="../mesCheck/static/js/mui.min.js" type="text/javascript" charset="utf-8"></script>
    <style>
        body{
            background:#f3f4f5;
            font-size:16px;
        }
        .forget-header{
            width:100%;
            height:90px;
            background:url('./images/beij_20.png');
            background-size:cover;
            position:relative;
        }
        .forget-log{
            width:137px;
            height:36px;
            background:url('./images/yiddxyzxt_20.png');
            background-size:137px;
            position:absolute;
            left:29px;
            bottom:25px;
        }
        .forget-content{
            padding:10px 25px;
        }
        .forget-input-box{
            padding-top:40px;
            border-bottom:1px solid #ccc;
            position:relative;
        }
        .forget-input{
            height:auto;
            border:none;
            padding: 0 10px;
            background:#f3f4f5;
            font-size:16px;
            margin-bottom: 18px;
        }
        .forget-btn{
            display: block;
            margin:45px auto;
            margin-bottom:0;
            width:325px;;
            height:52px;
            line-height:45px;
            text-align:center;
            color:#fff;
            background:url('./images/ann_20_z.png');
            background-size:cover;
        }
        a.forget-btn:link{

        }
        a.forget-btn:visited{

        }
        a.forget-btn:hover{

        }
        a.forget-btn:active{
            color:#fff;
            width:320px;
            height:45px;
            background:url('./images/ann_20_x.png');
            background-size:cover;
        }
        a.forget-btn:active .rz{
            margin-right:-4px;
        }
        .forget-phone-yz-btn{
            display:block;
            width:120px;
            height:42px;
            line-height:36px;
            text-align:center;
            color:#fff;
            background:url('./images/huoqyzm_20_z.png');
            position:absolute;
            background-size:cover;
            right:0;
            top:24px;
        }
        a.forget-phone-yz-btn:link{

        }
        a.forget-phone-yz-btn:visited{

        }
        a.forget-phone-yz-btn:hover{

        }
        a.forget-phone-yz-btn:active{
            color:#fff;
            width:120px;
            height:36px;
            background:url('./images/huoqyzm_20_x.png');
            background-size:cover;
        }
        /*加css*/
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
        .login-input-half{
            width:65%;
        }
        /**/
    </style>
</head>

<body>
<div class="forget-header">
    <div class="forget-log">
    </div>
</div>
<div class="forget-content">
    <div class="forget-input-box">
        <div class="mui-input-row">
            <input type="text" class="forget-input mui-input-clear" placeholder="请输入手机号" id="phone" name="phone">
        </div>
    </div>

<#--加的验证码-->
    <div class="forget-input-box">
        <div class="mui-input-row">
            <input type="text" id="verify_input" name="verify_input" class="forget-input forget-input-half login-input-yz" placeholder="请输入验证码">
        </div>
        <div class="login-yz-btn">
            <img id="imgVerify" onclick="getVerify();"  src="" alt="验证图片">
        </div>
    </div>
<#---->

    <div class="forget-input-box">
        <div class="mui-input-row">
            <input type="text" class="forget-input forget-input-half forget-input-phone-yz" placeholder="请输入手机验证码" id="VerificationCode" name="VerificationCode">
        </div>
        <a  class="forget-phone-yz-btn getPasswordBtn">
            获取验证码
        </a>
    </div>

    <div style="height:52px;">
        <a  class="forget-btn forgetbtn" onclick="editpassword()">
            <span class="rz">重设密码</span>
        </a>
    </div>
</div>
</body>
<script>
    var reg = /^1[0-9]{10}$/;  //电话号码的正则匹配式
    var reg2 = /[0-9]{4}$/;   //验证码正则
    $(document).ready(function () {
        getVerify();
        var ordertime = 60;  //设置再次发送验证码等待时间
        var timeleft = ordertime;


/*        $("#phone").keyup(function () {
            if (reg.test($("#phone").val())) {
                //若未注册则不能发送短信
                $.ajax({
                    type: "POST",
                    dataType: 'json',
                    url: "checkphone",
                    data:{phone: $("#phone").val()},
                    success: function (result) {
                        if (result.resultCode == "200") {
                            if (timeleft == 60) {
                                $(".getPasswordBtn").click(function(){
                                    getyz();
                                })
                            }
                        }
                        else {
                            $(".getPasswordBtn").unbind();
                            $(".getPasswordBtn").click(function(){
                                mui.alert(result.resultMes);
                            })
                        }
                    }
                });
                 //当号码符合规则后发送验证码按钮可点击
            }
            else {
                $(".getPasswordBtn").unbind();
            }
        })*/

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
            getyz();
        })
        function getyz(){
            //防止多次点击
            $(".getPasswordBtn").attr('data-f','2');
            if(reg.test($("#phone").val())){
                //若未注册则不能发送短信
                $.ajax({
                    type: "POST",
                    dataType: 'json',
                    url: "checkphone",
                    data:{phone: $("#phone").val()},
                    success: function (result) {
                        if (result.resultCode == "200") {
                            //发送验证码
                            $.ajax({
                                type: "GET",
                                dataType: 'json',
                                url: "getcompVerCode?phoneNum=" + $("#phone").val(),
                                success: function (result) {
                                    if (result.resultCode == "200") {
                                        //mui.alert('')
                                        mui.alert('获取验证码成功');
                                        timeCount(this);
                                    }
                                    else {
                                        mui.alert('验证码发送失败，请稍后再尝试')
                                        return;

                                    }
                                }

                            });
                            //发送验证码
                        }
                        else {
                            mui.alert(result.resultMes);
                            return;
                        }
                    }
                });
                //当号码符合规则后发送验证码按钮可点击

            }else{
                mui.alert('手机号码格式错误');
                return;
            }

            //此处可添加 ajax请求 向后台发送 获取验证码请求

        }
    });

    function  editpassword() {
        if (reg.test($("#phone").val())) {
            if (reg2.test($("#verify_input").val())){
                if (reg2.test($("#VerificationCode").val())){
                    $.ajax({
                        type: "POST",
                        dataType: 'json',
                        url: "editpassword",
                        data:{phoneNum: $("#phone").val(),authCode:$("#VerificationCode").val(),verify_input:$("#verify_input").val()},
                        success: function (result) {
                            if (result.resultCode == "200") {
                                var arrUrl = window.location.href.split("?");
                                var para = arrUrl[0];
                                para=para.substring(0,para.indexOf('/forgetpassword')+1);
                                window.location.href = para+"editpasswordindex";
                            }
                            else {
                                getVerify();
                                mui.alert(result.resultMes);
                            }
                        }
                    });
                }else {
                    getVerify();
                    mui.alert('短信验证码格式不正确');
                }
            }else {
                getVerify();
                mui.alert('图形验证码格式不正确');
            }
        }
        else {
            getVerify();
            mui.alert('手机号码格式不正确');
        }
    }


    /*加js*/
    function getVerify(){
        var arrUrl = window.location.href.split("?");
        var para = arrUrl[0];
        para=para.substring(0,para.indexOf('/index')+1);
        $("#imgVerify").attr("src", para + "getVerify?"+Math.random());
    }
</script>
</html>