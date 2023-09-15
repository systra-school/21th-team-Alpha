<!-- login.jsp -->
<%@page contentType="text/html; charset=Shift_JIS" pageEncoding="Shift_JIS"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html>
<head>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
	<meta name="viewport" content="width=device-width">
	<link rel="stylesheet" type="text/css" href="common.css">
	<%-- 黒岩 エラーメッセージ表示 --%>
	<% 
   Boolean loginErrorFlag = (Boolean) request.getAttribute("loginErrorFlag");
   String displayAlert = (loginErrorFlag != null && loginErrorFlag) ? "true" : "false";
%>
		<script type="text/javascript" src="/kikin_test/pages/js/message.js"></script>
		<script type="text/javascript">
   	 if (<%= displayAlert %>) {
        alert(messageArr["E-MSG-000002"]);
    	}
   	 </script>
	<html:javascript formName="loginForm" />
	<title>ログイン画面</title>
	<link href="/kikin_test/pages/css/common.css" rel="stylesheet" type="text/css" />
</head>
<body>
        <div id="wrapper">
        <div id="header">
        <div align="center">
            <table>
              <tr>
                  <td id="headLeft">
                    　
                  </td>
                  <td id="headCenter">
                    ログイン
                  </td>
                  <td id="headRight">
                    　
                  </td>
              </tr>
            </table>
        </div>
        </div>


		<div id="gymBody">
		  <div align="center">
		    <div style="width:350px; height: 30px; font-size:20">ID・パスワードを入力してください。</div>
		    <html:form action="/login" onsubmit="return validateLoginForm(this)">
		      <html:text property="shainId" size="16" value="" style="width:350px; height:35px"/>
		      <br/>
		      <html:password property="password" size="16" redisplay="false" value="" style="width:350px; height:35px"/>
		      <br/>
		      <br/>
		      <html:submit  property="submit" value="ログイン" style="border-radius: 5px; width:170px; height:30px; border:1px solid #808080; background-color:	#00FFFF; " onmouseover="this.style.opacity=0.7" onmouseout="this.style.opacity=1.0"/>
		      <br>
		      <br>
		      <html:reset value="リセット" style="border-radius: 5px; border:1px solid #808080;width:170px; height:30px; background-color:white;" onmouseover="this.style.opacity=0.7" onmouseout="this.style.opacity=1.0" />
		    </html:form>
		  </div>
		</div>
	    <div id="footer">
	        <table>
	          <tr>
	              <td id="footLeft">
	                　
	              </td>
	              <td id="footCenter">
	                　
	              </td>
	              <td id="footRight">
	                　
	              </td>
	          </tr>
	        </table>
	    </div>

		</div>
		</body>
</html>