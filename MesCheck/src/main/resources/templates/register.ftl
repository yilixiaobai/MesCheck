<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>注册</title>
    <script type="text/javascript" src="../mesCheck/static/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="../mesCheck/static/css/mui.css">
    <script src="../mesCheck/static/js/mui.min.js"></script>
</head>
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
    .register-header{
        width:100%;
        height:90px;
        background:url('./images/beij_20.png');
        background-size:cover;
        position:relative;
    }
    .register-log{
        width:137px;
        height:36px;
        background:url('./images/yiddxyzxt_20.png');
        background-size:137px;
        position:absolute;
        left:29px;
        bottom:25px;
    }
    .register-content{
        padding: 32px 25px;
    }
    .register-input-box{
        border-bottom:1px solid #ccc;
        position:relative;
        margin-bottom:24px;
    }
    .register-input-box p{
        color:#262626;
        font-size:16px;
        padding-left:10px;
    }
    .register-input{
        height:auto;
        border:none;
        background:#f3f4f5;
        font-size:16px;
        padding:0;
        padding: 0 10px;
        margin-bottom:10px;
    }
    .register-btn{
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
    a.register-btn:link{

    }
    a.register-btn:visited{

    }
    a.register-btn:hover{

    }
    a.register-btn:active{
        color:#fff;
        width:320px;
        height:45px;
        background:url('./images/ann_20_x.png');
        background-size:cover;
    }
    a.register-btn:active .rz{
        margin-right:-3px;
    }
    .register-tips{
        padding:0 36px;
    }
    .register-tips p{
        font-size:16px;
        color:#a5a2a3;
    }
</style>

<body>
<div class="register-header">
    <div class="register-log">
    </div>
</div>
<div class="register-content">
    <div class="register-input-box">
        <p>企业名称</p>
        <div class="mui-input-row">
            <input type="text" class="register-input mui-input-clear" placeholder="请输入企业名称" id="complanName" name="complanName">
        </div>
    </div>
    <div class="register-input-box">
        <p>企业地址</p>
        <div class="mui-input-row">
            <input type="text" class="register-input mui-input-clear" placeholder="请输入企业地址" id="address" name="address">
        </div>
    </div>
    <div class="register-input-box">
        <p>企业法人</p>
        <div class="mui-input-row">
            <input type="text" class="register-input mui-input-clear" placeholder="请输入中文姓名" id="username" name="username">
        </div>
    </div>
    <div class="register-input-box">
        <p>手机号码</p>
        <div class="mui-input-row">
            <input type="text" class="register-input mui-input-clear" placeholder="请输入手机号码（仅限移动号码）" id="userTel" name="userTel">
        </div>
    </div>
    <div class="register-input-box">
        <p>登录密码</p>
        <div class="mui-input-row">
            <input type="password" class="register-input mui-input-clear" placeholder="请输入密码" id="password" name="password">
        </div>
    </div>
    <div class="register-input-box">
        <p>确认密码</p>
        <div class="mui-input-row">
            <input type="password" class="register-input mui-input-clear" placeholder="请再次输入密码" id="password2" name="password2">
        </div>
    </div>
    <div style="height:52px;">
        <a href="javascript:;" onclick="check()" class="register-btn registerbtn">
            <span class="rz">立即注册</span>
        </a>
    </div>
</div>
<div class="register-tips">
    <p>1.密码长度应为8-15位</p>
    <p>2.密码应包括数字、小写字母、大写字母、特殊符号（!@#$%^&*()_+）4类中至少3类</p>
</div>
</body>

<script>
    var regPhone = /^1[0-9]{10}$/;
    var regPass = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,16}$/;
    var regName = /^[\u4E00-\u9FA5]{1,5}$/;


    function check() {
        if($("#complanName").val().trim().length<=0){
            mui.alert('企业名称不能为空，请检查输入');
        }
        else if($("#address").val().trim().length<=0){
            mui.alert('企业地址不能为空，请检查输入');
        }
        else if(!regName.test($("#username").val())){
            mui.alert('法人姓名格式不正确，请检查输入');
        }
        else if(!regPhone.test($("#userTel").val())){
            mui.alert('手机号码格式不正确，请检查输入');
        }
        else if(!regPass.test($("#password").val())){
            mui.alert('密码格式不正确，请检查输入');
        }
        else if(!regPass.test($("#password2").val())){
            mui.alert('确认密码格式不正确，请检查输入');
        }



        else{
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: "registerCom",
                data:{comPanyID: $("#comPanyId").val(),cpName: $("#complanName").val(),adress: $("#address").val(),name: $("#username").val(),password2: $("#password2").val(),password: $("#password").val(),phone:$("#userTel").val()},
                success: function (result) {
                    if (result.resultCode == "200") {
                        mui.alert(result.resultMes,function () {
                            var arrUrl = window.location.href.split("?");
                            var para = arrUrl[0];
                            para=para.substring(0,para.indexOf('/register')+1);
                            window.location.href = para+"index";

                        });
                    }
                    else {
                        mui.alert(result.resultMes);
                    }
                }
            });
        }

    }
</script>
</html>