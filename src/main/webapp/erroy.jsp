
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>健身房管理系统</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/HTmoban/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/HTmoban/css/font.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/HTmoban/css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/HTmoban/lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/HTmoban/js/xadmin.js"></script>

</head>
<body class="login-bg">
    
    <div class="login layui-anim layui-anim-up">
        <div  class="message">尊敬会员，系统出现错误，请联系前台管理员！</div>

    </div>

    <%--<script>--%>
        <%--$(function  () {--%>
            <%--layui.use('form', function(){--%>
              <%--var form = layui.form;--%>

              <%--// layer.msg('玩命卖萌中', function(){--%>
              <%--//   //关闭后的操作--%>
              <%--//   });--%>
              <%--//监听提交--%>
              <%--form.on('submit(login)', function(data){--%>
                 <%--alert(888);--%>
                <%--layer.msg(JSON.stringify(data.field),function(){--%>
                    <%--location.href='${pageContext.request.contextPath}/dl/yz'--%>
                <%--});--%>
                <%--return false;--%>
              <%--});--%>
            <%--});--%>
        <%--})--%>

        <%----%>
    <%--</script>--%>

    
    <!-- 底部结束 -->
    <div class="footer">
        <div align="center">
            <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=11010602007158" style="display:inline-block;text-decoration:none;color: #fff;"><img src="/static/HTmoban/images/备案图标.png">健身房系统</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a style=" text-decoration: none; color: #fff;" href="http://www.beian.miit.gov.cn/" target="_blank">健身房系统</a>
        </div>
    </div>
</body>
</html>
