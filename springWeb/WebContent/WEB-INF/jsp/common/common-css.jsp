<!--  
其实在style里面加上< !--  -- >的原因是，
有些低版本的浏览器不能识别style标记，会把style里面的内容以文本形式直接显示到页面上，
为避免这种情况，最好加上注释符，不让它显示。
不单只是高低浏览器版本的问题，不同的浏览器，解释的标准也不一样-->
<!--
当浏览器能够识别style标签时候，<  !- - -- >这对标签就会过滤掉，继续执行核心的样式代码；
当浏览器识别不了style标签的时候，<  !-- -- >就发挥作用了，也就是注释作用！就是把内容屏蔽掉，不让显示出来 。
它非常类似java语言里的异常处理。
try...catch...
-->
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css">
<!--
  body
  {
     margin-left:0px;
     margin-top:0px;
     margin-right:0px;
     margin-bottom:opx;
  }
 .STYLE1{font-size:12px;}
 .STYLE3{font-size:12px;font-width:bold;}
 .STYLE4{font-size:12px;color:#03515d;}
 a.HOVER{color:red;text-decoration:underline;}
 a{color:#03515d;text-decoration:none;}
 .listtable td{
     vertical-align:middle;
     text-align:center;
 }
 .ttab td{
   border:solid 1px #b5d6e6;
 }
table{
   border:0;
   border-collapse:collapse;
   border-spacing:0;
}
th,td{  
	border:0px;
	border-collapse:collapse;  
	border-spacing:0;
	padding:0;  
}
/*   search */
.search_k{width:98%;margin:5px auto}
.search{border:1px solid #999999}
.search legend{width:70px;margin-left:5px;}
.search_content{font-size:12px;line-height:100%;padding:10px;}
/*clear 属性规定元素的哪一侧不允许其他浮动元素。*/
.select { width: 98%; text-align: right; margin: 10px auto; clear: both; }
.input_btn_style1{ background:#ffffff url(${pageContext.servletContext.contextPath }/images/bg_x.gif) 0px 0px repeat-x; height: 23px; border: 1px solid #7F9DB9; color: #1E5494; font-size: 12px; line-height: 20px; text-align: center; cursor: pointer;}
input.sub{padding:0px 10px 0px 10px;height:28px;color:#000066;background:url("${pageContext.servletContext.contextPath }/images/sub.png");border:none;}
    .btn3_mouseout {
BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#C3DAF5); BORDER-LEFT: #2C59AA 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #2C59AA 1px solid
}
.btn3_mouseover {
BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#D7E7FA); BORDER-LEFT: #2C59AA 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #2C59AA 1px solid
}
-->
</style>

