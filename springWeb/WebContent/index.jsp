<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
 //其作用是获取当前的系统路径。
 String path = request.getContextPath();
 //返回当前连接使用的协议
 String scheme = request.getScheme();
 String serverName = request.getServerName();
 String basePath  =scheme+"://"+serverName+":"+request.getServerPort()+path+"/";
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
     <!-- base标记是一个基链接标记，是一个单标记。用以改变文件中所有连结标记的参数内定值。
     它只能应用于标记<head>与</head>之间。你网页上的所有相对路径在链接时都将在前面加上基链接指向的地址。
  jsp页面可以定义：/</%/ String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
      在head标签后加上<base href="</%=basePath%>" >即可。 -->
     <base href="<%=basePath%>">
     <title>My Jsp 'index.jsp' starting page</title>
     <!-- meta是用来在HTML文档中模拟HTTP协议的响应头报文 -->
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <meta http-equiv="pragma" content="no-cache">
     <meta http-equiv="cache-control" content="no-cache">
     <meta http-equiv="expires" content="0">
     <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
     <meta http-equiv="description" content="This is my page">
</head>
<body>
 <center>
   <h3>
   <a href="<%=basePath%>background/index.html">进入后台</a>
   </h3>
 </center>
   
</body>
</html>