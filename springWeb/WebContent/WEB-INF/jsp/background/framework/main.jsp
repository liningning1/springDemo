<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String path=request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝缘后台管理系统</title>
</head>
  <frameset rows="83,*" cols="*" framespacing="0" border="0" frameborder="no"> 
    <frame src="${pageContext.servletContext.contextPath}/background/top.html" name="topFrame" scrolling="no" noresize="noresize" id="topFrame"/>
    <frame src="${pageContext.servletContext.contextPath }/background/center.html" name="mainFrame" id="mainFrame" />
  </frameset>
<noframes><body>
</body></noframes>
</html>