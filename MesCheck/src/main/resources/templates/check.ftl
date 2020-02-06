<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>短信验证</title>
    <script type="text/javascript" src="../mesCheck/static/jquery-3.3.1.min.js"></script>

    <link rel="stylesheet" href="../mesCheck/static/css/mui.css">
    <link rel="stylesheet" href="../mesCheck/static/css/mui.picker.min.css">
    <script src="../mesCheck/static/js/mui.min.js"></script>
    <script src="../mesCheck/static/js/mui.picker.min.js"></script>
    <style>
        body{
            background:#f3f4f5;
            font-size:16px;
        }
        .index-header{
            width:100%;
            height:90px;
            background:url('./images/beij_20.png');
            background-size:cover;
            position:relative;
        }

        .index-log{
            position:absolute;
            left:33px;
            bottom:32px;
            color:#fff;
            font-size:19px;
            font-weight: 500;
        }
        .index-tab-btn-box{
            position:absolute;
            right:33px;
            bottom:32px;
        }
        .index-tab-btn{
            display: inline-block;
            text-decoration: underline;
            font-size:16px;
            color:#fff;
            margin-left:20px;
        }
        .index-content{
            padding:0 25px;
            margin-top:7px;
        }
        .index-input-box{
            margin-top:25px;
        }
        .index-textarea{
            padding:8px;
            width:100%;
            height:105px;
            font-size:16px;
            border:1px solid #ccc;
            box-sizing: border-box;
        }
        .index-textarea::-webkit-input-placeholder{
            color:#c2c2c2;
        }
        .index-textarea:-moz-placeholder{
            color:#c2c2c2;
        }
        .index-textarea::-moz-placeholder{
            color:#c2c2c2;
        }
        .index-textarea:-ms-input-placeholder{
            color:#c2c2c2;
        }
        .index-input-title{
            padding-left:10px;
            color:#262626;
            font-size:16px;
            margin-bottom:15px;
        }
        .index-input{
            background:none;
            box-sizing: border-box;
            padding:0 8px;
        }
        .index-input::-webkit-input-placeholder{
            color:#c2c2c2;
        }
        .index-input:-moz-placeholder{
            color:#c2c2c2;
        }
        .index-input::-moz-placeholder{
            color:#c2c2c2;
        }
        .index-input:-ms-input-placeholder{
            color:#c2c2c2;
        }
        .index-date-selecter-box{
            padding-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .rz{
            margin-left:3px;
        }
        .index-submit-btn{
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
        a.index-submit-btn:link{

        }
        a.index-submit-btn:visited{

        }
        a.index-submit-btn:hover{

        }
        a.index-submit-btn:active{
            color:#fff;
            width:320px;
            height:45px;
            background:url('./images/ann_20_x.png');
            background-size:cover;
        }
        a.index-submit-btn:active .rz{
            margin-right:-3px;
        }
        .index-tips{
            color:#675f5f;
            padding:32px 33px;
            font-size:12px;
        }
        .index-tips p{
            color:#675f5f;
            font-size:12px;
            margin-bottom:6px;
        }
    </style>
</head>
</head>
<body>
<div class="index-header">
    <div class="index-log">
        短信验证
    </div>

    <div class="index-tab-btn-box">
    <#if "0"==flag>
        <div class="index-tab-btn index-examine-btn" onclick="master()">
        审核
        </div>
    </#if>
        <div class="index-tab-btn index-logout-btn" onclick="logout()">
            退出
        </div>
    </div>
</div>
<div class="index-content">

   <#if "2"==flag>
       <div class="mui-input-row index-input-box index-date-selecter-box">
       <p class="index-input-title">
           手机号码
       </p>
       <input type="text" class="index-input" placeholder="请输入手机号码" name="phone" id="phone">
   </div>
   </#if>
    <div class="mui-input-row index-input-box">
        <p class="index-input-title">
            短信内容
        </p>
        <textarea id="messageContent" name="messageContent" class="index-textarea" placeholder="请输入您要验证的信息内容（含密印）"></textarea>
    </div>
    <div class="mui-input-row index-input-box index-date-selecter-box">
        <p class="index-input-title">
            选择日期
        </p>
        <input type="text" class="index-input index-date-selecter" placeholder="点击选择日期" name="dateTime" id="dateTime" >
    </div>
    <div style="height:52px;">
        <a href="javascrip:;" class="index-submit-btn submitbtn" onclick="checkMes()">
            <span>提</span>
            <span class="rz">交</span>
        </a>
    </div>
</div>
<div class="index-tips">
    <p>温馨提示</p>
    <div>
        &emsp;&emsp;当前只支持两小时内发送短信内容精确验证，如因以外（网络延迟、关机等）因素导致短信延期下发的，查询结果不精确。
    </div>
</div>
<!-- <input type="text" readonly placeholder="点击选择日期" class="dateselecter"> -->
<script>
    $(function(){
        mui('body').on('tap', ".index-date-selecter",function(){
            var dtPicker = new mui.DtPicker({
                'type': 'hour'
            });
            dtPicker.show(function (selectItems) {
                $(".index-date-selecter").val(selectItems.text+"时");
            })
        })
    })
</script>
</body>
<script type="text/javascript">
    setSize();
    addEventListener('resize',setSize);
    function setSize() {
        document.documentElement.style.fontSize = document.documentElement.clientWidth/750*100+'px';
    }

    function logout() {$.ajax({
        type:"GET",
        url:"lougout",
        dataType:"json",
        success:function (result) {
            if(result.resultCode == "0000"){
                var arrUrl = window.location.href.split("?");
                var para = arrUrl[0];
                para=para.substring(0,para.indexOf('/check')+1);
                window.location.href = para+"index";
            }

        }
    });


    }
    function master() {
        var arrUrl = window.location.href.split("?");
        var para = arrUrl[0];
        para=para.substring(0,para.indexOf('/check')+1);
        window.location.href = para+"examine";
    }
    var reg = /^1[0-9]{10}$/;
    function checkMes() {

        if($("#messageContent").val().length<=0){
            mui.alert('短信内容不能为空，请检查输入');
        }
        else if($("#dateTime").val().length<=0){
            mui.alert('日期输入不能为空，请检查输入');
        }
        else{$.ajax({
            type: "POST",
            dataType: 'json',
            url: "checkMessage",
            data:{phone: $("#phone").val(),mesContent: $("#messageContent").val(),dateTime:$("#dateTime").val()},
            success: function (result) {
                if (result.resultCode == "0000") {
                    mui.alert('验证通过');
                }
                else {
                    mui.alert(result.resultMes);
                }
            }
        });}
    }
</script>
</html>