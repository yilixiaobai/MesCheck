<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>重置密码</title>
    <link rel="stylesheet" href="../mesCheck/static/css/mui.css">
    <script type="text/javascript" src="../mesCheck/static/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="../mesCheck/static/js/mui.min.js"></script>
    <script src="../mesCheck/static/js/mui.min.js" type="text/javascript" charset="utf-8"></script>
    <style>
        body{
            background:#f3f4f5;
            font-size:16px;
        }
        .edit-header{
            width:100%;
            height:90px;
            background:url('./images/beij_20.png');
            background-size:cover;
            position:relative;
        }
        .edit-log{
            width:137px;
            height:36px;
            background:url('./images/yiddxyzxt_20.png');
            background-size:137px;
            position:absolute;
            left:29px;
            bottom:25px;
        }
        .edit-content{
            padding:10px 25px;
        }
        .edit-input-box{
            padding-top:40px;
            border-bottom:1px solid #ccc;
            position:relative;
        }
        .edit-input{
            height:auto;
            border:none;
            padding: 0 10px;
            background:#f3f4f5;
            font-size:16px;
            margin-bottom: 18px;
        }
        .edit-btn{
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
        a.edit-btn:link{

        }
        a.edit-btn:visited{

        }
        a.edit-btn:hover{

        }
        a.edit-btn:active{
            color:#fff;
            width:320px;
            height:45px;
            background:url('./images/ann_20_x.png');
            background-size:cover;
        }
        a.edit-btn:active .rz{
            margin-right:-4px;
        }
        .edit-tips{
            padding:32px 36px;
        }
        .edit-tips p{
            font-size:16px;
            color:#a5a2a3;
        }
    </style>
</head>
<body>
<div class="edit-header">
    <div class="edit-log">
    </div>
</div>
<div class="edit-content">
    <div class="edit-input-box">
        <div class="mui-input-row" >
            <input type="password" class="edit-input mui-input-clear" placeholder="请输入新密码" id="password">
        </div>
    </div>
    <div class="edit-input-box">
        <div class="mui-input-row">
            <input type="password" class="edit-input edit-input-half edit-input-phone-yz" placeholder="请再次输入新密码" id="password2">
        </div>
    </div>
    <div style="height:52px;">
        <a  class="edit-btn editbtn" onclick="edit()">
            <span>确</span>
            <span class="rz" style="margin-left:13px;">认</span>
        </a>
    </div>
</div>
<div class="edit-tips">
    <p>1.密码长度应为8-15位</p>
    <p>2.密码应包括数字、小写字母、大写字母、特殊符号（!@#$%^&*()_+）4类中至少3类</p>
</div>
</body>
<script>
    var regPass = /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\W_]+$)(?![a-z0-9]+$)(?![a-z\W_]+$)(?![0-9\W_]+$)[a-zA-Z0-9\W_]{8,16}$/;
    function edit() {
        if(!regPass.test($("#password").val())){
            mui.alert('密码格式不正确，请检查输入');
        }
        else if ($("#password").val()!=$("#password2").val()){
            mui.alert('两次输入密码不一致，请检查输入');
        }else {
            $.ajax({
                type: "POST",
                dataType: 'json',
                url: "editpass",
                data:{phone: $("#phone").val(),password: $("#password").val(),password2: $("#password2").val()},
                success: function (result) {
                    if (result.resultCode == "200") {
                        mui.alert(result.resultMes,function () {
                            var arrUrl = window.location.href.split("?");
                            var para = arrUrl[0];
                            para=para.substring(0,para.indexOf('/forgetpassword')+1);
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