<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>没权限</title>
  <style type="text/css">
     
     .attention{
       background:#FFFBCC;
       border:1px #E6DB55 solid;
       color:#333;
       margin:10px;
       padding:8px 8px 8px 35px;
       line-height:22px;
       font-size:12px;
     }
  </style>
</head>
<body>
    <div class="attention">
                  你没有权限执行此操作,请联系管理员!
    </div>
</body>
</html>