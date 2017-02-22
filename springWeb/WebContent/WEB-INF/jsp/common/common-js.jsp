<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/script" src="${pageContext.servletContext.contextPath}/js/jquery-1.4.min.js"></script>
<!-- 创建兼容 IE/FireFox 的 event 及 event 的 srcElement、fromElement、toElement 属性 -->
<script type="text/javascript">
   function pageNow(pageNow)
   {
	   //对表单数据进行序列化
	  var fy =  $("#fenye").serialize();
	  //获取表单中的action 属性 
	  var f = $("#fenye").attr("action");
	  var pCount = parseInt("${pageView.pageCount}");
	  if(pageNow<1)
      {
		  alert("不好意思,已经是第一页了!");  
		  return false;
	  }
	  else if(pCount<pageNow)
	  {
		  alert("已经是最后一页了!");
		  return false;
	  }
	  else 
	  {
		  window.location.href= f+"?pageNow="+pageNow+"&"+fy;
		  
	  };	  
   }


</script>