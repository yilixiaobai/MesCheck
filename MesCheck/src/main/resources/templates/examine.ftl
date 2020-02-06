<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>审核页面</title>
    <script src="../mesCheck/static/js/popups.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="../mesCheck/static/jquery-3.3.1.min.js"></script>

    <link rel="stylesheet" href="../mesCheck/static/css/mui.css">
    <script src="../mesCheck/static/js/mui.min.js"></script>
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
        .examine-header{
            width:100%;
            height:90px;
            background:url('./images/beij_20.png');
            background-size:cover;
            position:relative;
        }
        .examine-log{
            width:137px;
            height:36px;
            background:url('./images/yiddxyzxt_20.png');
            background-size:137px;
            position:absolute;
            left:29px;
            bottom:25px;
        }
        .mui-checkbox.mui-left input[type=checkbox], .mui-radio.mui-left input[type=radio]{
            left:10px;
        }
        .mui-checkbox.mui-left label, .mui-radio.mui-left label{
            margin-left:39px;
            padding:0;
            font-size:16px;
        }
        .mui-radio input[type='radio'], .mui-checkbox input[type='checkbox']{
            top:50%;
            transform: translateY(-60%);
        }
        .mui-radio label, .mui-checkbox label{
            width:auto;
        }
        .examine-input-row label{
            padding:0;
            height:49px;
            line-height:49px;
        }
        .examine-btn-box{
            position:absolute;
            left:80px;
            top:50%;
            transform: translateY(-50%);
        }
        .examine-btn{
            display: inline-block;
            width:58px;
            height:28px;
            line-height:28px;
            color:#fff;
            text-align:center;
            border-radius:4px;
            margin-right:8px;
        }
        .examine-logout{
            position:absolute;
            background:#67b7f6;
            border-radius:4px;
            color:#fff;
            width:58px;
            height:28px;
            line-height:28px;
            text-align:center;
            right:12px;
            top:50%;
            transform: translateY(-50%);
        }
        .examine-qy-item.mui-input-row label{
            width:100%;
            border-bottom:1px solid #ccc;
            padding:11px 10px;
        }
        .examine-pz-btn{
            background:#67b7f6;
        }
        .examine-bh-btn{
            background:#e54545;
        }
        .examine-list-box{
            background:#fff;
        }
        .examine-list-box ul{
            margin:0;
            padding:0;
            list-style:none;
        }
        .examine-header-img{
            float:left;
            width:46px;
            height:46px;
            border-radius:23px;
            background:#6ec2fd;
            line-height:46px;
            text-align:center;
            font-size:16px;
            color:#fff;
        }
        .examine-item-info{
            float: left;
            margin-left:12px;
            padding: 4px 0;
        }
        .examine-item-info .examine-item-name{
            line-height:1;
            margin-bottom:10px;
            font-size:17px;
            color:#31343b;
        }
        .examine-item-qyname{
            line-height:1;
            font-size:14px;
            color:#a0a0a0;
        }
        .examine-item-qyaddress{
            line-height:1;
            font-size:14px;
            color:#a0a0a0;
            display: none;
        }
        .examine-item-qyaddress div{
            margin-top:10px;
        }
        .examine-item-details{
            position: absolute;
            top:0;
            height:77px;
            line-height:77px;
            right:0;
            padding:0 12px;
            color:#b0b0b0;
        }
    </style>
</head>

<body>
<div class="examine-header">
    <div class="examine-log">
    </div>
</div>
<div class="examine-content">
    <!-- 操作按钮 -->
    <div class="mui-input-row examine-input-row mui-checkbox mui-left">
        <input type="checkbox" class="checkall" id="checkall" name="checkall">
        <label>全选</label>
        <div class="examine-btn-box">
            <div class="examine-btn examine-pz-btn" onclick="allowAll()">
                批准
            </div>
            <div class="examine-btn examine-bh-btn" onclick="refuseAll()">
                驳回
            </div>
        </div>
        <div class="examine-logout" onclick="logout()" >
            退出
        </div>
    </div>
    <!-- /操作按钮 -->
    <!-- 企业列表 -->
    <div class="examine-list-box">
        <ul>
            <li>
                <#list list as being>
                    <div class="mui-input-row examine-qy-item mui-checkbox mui-left"><#--disabled="true"-->

                        <#if being.status==0>
                        <input type="checkbox" class="checks" name="${being.comPanyID}">
                        <label>
                            <div class="examine-header-img">
                                ${being.name}
                            </div>
                            <div class="examine-item-info">
                                <div class="examine-item-name">
                                    ${being.name}<span>${being.phone}</span>
                                </div>
                                <div class="examine-item-qyname">
                                    ${being.cpName}
                                </div>
                                <div class="examine-item-qyaddress">
                                    <div>${being.adress}</div>
                                    <div>审核时间：${being.time!}</div>
                                    <div>
                                        审核状态：
                                        <#if being.status==0>
                                            <span style="color:dodgerblue">${being.statusVlaue}</span>
                                        </#if>
                                        <#if being.status==1>
                                            <span>${being.statusVlaue}</span>
                                        </#if>
                                        <#if being.status==2>
                                            <span style="color:red">${being.statusVlaue}</span>
                                        </#if>
                                    </div>
                                </div><#--${being.time!}  ${being.statusVlaue}-->
                            </div>
                        </label>
                        <div class="examine-item-details" data-flag="1">
                          <span class="mui-icon mui-icon-arrowdown">
                          </span>
                        </div>
                        </#if>
                        <#if being.status==1>
                        <#--<input type="checkbox" class="checks" name="${being.comPanyID}" disabled="disabled" >-->
                        <label>
                            <div class="examine-header-img">
                                ${being.name}
                            </div>
                            <div class="examine-item-info">
                                <div class="examine-item-name">
                                    ${being.name}<span>${being.phone}</span>
                                </div>
                                <div class="examine-item-qyname">
                                    ${being.cpName}
                                </div>
                                <div class="examine-item-qyaddress">
                                    <div>${being.adress}</div>
                                    <div>审核时间：${being.time!}</div>
                                    <div>
                                        审核状态：
                                        <#if being.status==0>
                                            <span>${being.statusVlaue}</span>
                                        </#if>
                                        <#if being.status==1>
                                            <span>${being.statusVlaue}</span>
                                        </#if>
                                        <#if being.status==2>
                                            <span style="color:red">${being.statusVlaue}</span>
                                        </#if>
                                    </div>
                                </div><#--${being.time!}  ${being.statusVlaue}-->
                            </div>
                        </label>
                        <div class="examine-item-details" data-flag="1">
                          <span class="mui-icon mui-icon-arrowdown">
                          </span>
                        </div>
                        </#if>

                        <#if being.status==2>
                        <#--<input type="checkbox" class="checks" name="${being.comPanyID}" disabled="disabled">-->
                        <label>
                            <div class="examine-header-img">
                                ${being.name}
                            </div>
                            <div class="examine-item-info">
                                <div class="examine-item-name">
                                    ${being.name}<span>${being.phone}</span>
                                </div>
                                <div class="examine-item-qyname">
                                    ${being.cpName}
                                </div>
                                <div class="examine-item-qyaddress">
                                    <div>${being.adress}</div>
                                    <div>审核时间：${being.time!}</div>
                                    <div>
                                        审核状态：
                                        <#if being.status==0>
                                            <span style="color:yellow">${being.statusVlaue}</span>
                                        </#if>
                                        <#if being.status==1>
                                            <span>${being.statusVlaue}</span>
                                        </#if>
                                        <#if being.status==2>
                                            <span style="color:red">${being.statusVlaue}</span>
                                        </#if>
                                    </div>
                                </div><#--${being.time!}  ${being.statusVlaue}-->
                            </div>
                        </label>
                        <div class="examine-item-details" data-flag="1">
                          <span class="mui-icon mui-icon-arrowdown">
                          </span>
                        </div>
                        </#if>


                        <#--<input type="checkbox" class="checks" name="${being.comPanyID}">
                        <label>
                            <div class="examine-header-img">
                                ${being.name}
                            </div>
                            <div class="examine-item-info">
                                <div class="examine-item-name">
                                    ${being.name}<span>${being.phone}</span>
                                </div>
                                <div class="examine-item-qyname">
                                    ${being.cpName}
                                </div>
                                <div class="examine-item-qyaddress">
                                    <div>${being.adress}</div>
                                    <div>审核时间：${being.time!}</div>
                                    <div>
                                        审核状态：
                                        <#if being.status==0>
                                            <span style="color:yellow">${being.statusVlaue}</span>
                                        </#if>
                                        <#if being.status==1>
                                            <span>${being.statusVlaue}</span>
                                        </#if>
                                        <#if being.status==2>
                                            <span style="color:red">${being.statusVlaue}</span>
                                        </#if>
                                    </div>
                                </div>&lt;#&ndash;${being.time!}  ${being.statusVlaue}&ndash;&gt;
                            </div>
                        </label>
                        <div class="examine-item-details" data-flag="1">
                          <span class="mui-icon mui-icon-arrowdown">
                          </span>
                        </div>-->
                    </div>
                </#list>
            </li>
        </ul>
    </div>
    <!-- /企业列表 -->
</div>
<script>
    $(function(){
        $(".examine-item-details").click(function(){
            if($(this).data('flag')=='1'){
                $(this).data('flag', '2');
                $(this).siblings('label').find('.examine-item-info .examine-item-qyaddress').show();
                $(this).find('span.mui-icon').removeClass('mui-icon-arrowdown').addClass('mui-icon-arrowup')
            }else if($(this).data('flag') == '2'){
                $(this).data('flag', '1');
                $(this).siblings('label').find('.examine-item-info .examine-item-qyaddress').hide();
                $(this).find('span.mui-icon').removeClass('mui-icon-arrowup').addClass('mui-icon-arrowdown')
            }
        })
        $(".checkall").click(function(){
            $('.checks').prop('checked', $(this).prop('checked'))
        })
        $('body').on('click','.checks',function(e){
            if($(".checks:checked").length==$('.checks').length){
                $(".checkall").prop('checked',true)
            }else{
                $(".checkall").prop('checked', false)
            }
        })
    })
</script>
</body>



<#--<body>
	<div class="main">
		<div class="logoBoxs">
    		<img src="images/logo.png">
    	</div>
		<div class="content">
            <div class="checkallbox">
                <input type="checkbox" id="checkall" name="checkall"><label for="checkall" style="margin-left:10px;">全选</label>
                <span class="agreeall editbtns" onclick="allowAll()">批准</span><span class="refuseall editbtns"  onclick="refuseAll()">驳回</span>
                <span class="logouts" onclick="logout()" >退出</span>
            </div>

            <div class="qyBox">
                <table class="qyList">
                    <tr>
                        <td>选择</td>
                        <td>企业法人</td>
                        <td>手机号</td>
                        <td>企业名称</td>
                        <td>企业地址</td>
                        <td>审核时间</td>
                        <td>审核状态</td>
                    </tr>
						<#list list as being>
                        <#if being.status==0>
                              <tr style="background: #09d3d9;">
                                  <td><input type="checkbox" class="checks" name="${being.comPanyID}" ></td>
                                  <td>${being.name}</td>
                                  <td>${being.phone}</td>
                                  <td>${being.cpName}</td>
                                  <td>${being.adress}</td>
                                  <td>${being.time!}</td>
                                  <td>${being.statusVlaue}</td>
                              </tr>
                        </#if>
                            <#if being.status==1>
                              <tr style="background: #d1ffd5">
                                  <td><input type="checkbox" class="check" name="${being.comPanyID}" disabled="true" ></td>
                                  <td>${being.name}</td>
                                  <td>${being.phone}</td>
                                  <td>${being.cpName}</td>
                                  <td>${being.adress}</td>
                                  <td>${being.time!}</td>
                                  <td>${being.statusVlaue}</td>
                              </tr>
                            </#if>
                            <#if being.status==2>
                              <tr  style="background: #fce0ff;">
                                  <td><input type="checkbox" class="check" name="${being.comPanyID}" disabled="true"></td>
                                  <td>${being.name}</td>
                                  <td>${being.phone}</td>
                                  <td>${being.cpName}</td>
                                  <td>${being.adress}</td>
                                  <td>${being.time!}</td>
                                  <td>${being.statusVlaue}</td>
                              </tr>
                            </#if>
						</#list>
                </table>
            </div>

		</div>
	</div>

</body>-->
<script>
    $(function(){
/*        $("#checkall").click(function(){
            if($(this).prop("checked")){
                $(".checks").prop("checked",true);
            }else{
                $(".checks").prop("checked",false);
            }
        })*/
        $(".checks").click(function(){
            if($(".checks:checked").length==$(".checks").length){
                $("#checkall").prop("checked",true);
            }else{
                $("#checkall").prop("checked",false);
            }
        })
    })

    function updateOne(id) {
        $.ajax({
            type: "POST",
            dataType: 'json',
            url: "updateOne",
            data:{comId:id},
            success: function (result) {
                mui.alert(result.resultMes);
            }
        });

    }
    function updateTwo(id) {
        $.ajax({
            type: "POST",
            dataType: 'json',
            url: "updateTwo",
            data:{comId:id},
            success: function (result) {
                mui.alert(result.resultMes);
            }
        });

    }
    function logout() {$.ajax({
        type:"GET",
        url:"lougout",
        dataType:"json",
        success:function (result) {
            if(result.resultCode == "0000"){
                var arrUrl = window.location.href.split("?");
                var para = arrUrl[0];
                para=para.substring(0,para.indexOf('/examine')+1);
                window.location.href = para+"masterLogin";
            }

        }
    });


    }
    function allowAll(){
        var  obj = document.getElementsByClassName("checks");
        var check_val =new Array();
        for(var i=0;i<obj.length;i++){

            if(obj[i].checked){
                check_val.push(obj[i].getAttribute("name"));
            }
        }

        $.ajax({
            type:"POST",
            url:"allowAll",
            dataType:"json",
            data:{list:check_val},
            traditional:true,
            success:function (result) {
                if(result.resultCode == "200"){
                    mui.alert(result.resultMes,function () {
                        window.location.reload();
                    });
                }
                else {
                    mui.alert(result.resultMes);
                }
            }
        });
    }
    function refuseAll(){

        var  obj = document.getElementsByClassName("checks");
        var check_val =new Array();
        for(var i=0;i<obj.length;i++){

            if(obj[i].checked){
                check_val.push(obj[i].getAttribute("name"));
            }
        }

        $.ajax({
            type:"POST",
            url:"refuseAll",
            dataType:"json",
            data:{list:check_val},
            traditional:true,
            success:function (result) {
                if(result.resultCode == "200"){
                    mui.alert(result.resultMes,function () {
                        window.location.reload();
                    });
                }
                else {
                    mui.alert(result.resultMes);
                }
            }
        });
    }
</script>
</html>