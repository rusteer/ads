<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="activate" value="class='active open'"/>
<div class="sidebar" id="sidebar">
	<ul class="nav nav-list">
		<c:if test="${isAdmin}">
			<li ${cmpName=="ads"?activate:""}><a href="/ads/"> <i class="icon-list"></i> <span class="menu-text">广告管理</span></a></li>
		</c:if>
		<li ${cmpName=="device"?activate:""}><a href="/device/?systemApp=-1"> <i class="icon-list-alt"></i> <span class="menu-text">设备统计</span></a></li>
		<c:if test="${isAdmin}">
			<li ${cmpName=="summary"?activate:""}><a href="/summary/"> <i class="icon-list-alt"></i> <span class="menu-text">综合统计</span></a></li>
			<li ${cmpName=="retention"?activate:""}><a href="/retention/"> <i class="icon-list-alt"></i> <span class="menu-text">留存统计</span></a></li>
			<li ${cmpName=="setting"?activate:""}><a href="/setting/"> <i class="icon-list-alt"></i> <span class="menu-text">设置</span> </a></li>
		</c:if>
	</ul>
	<div class="sidebar-collapse" id="sidebar-collapse"> <i class="icon-double-angle-left"></i></div>
</div>

	