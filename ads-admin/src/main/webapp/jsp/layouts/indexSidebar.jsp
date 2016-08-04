<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="activate" value="class='active open'"/>
<div class="sidebar" id="sidebar">
	<ul class="nav nav-list">
		<li ${cmpName=="advertiser"?activate:""}><a href="/advertiser/"> <i class="icon-list-alt"></i> <span class="menu-text">广告主管理</span> </a></li>
		<li ${cmpName=="ads"?activate:""}><a href="/ads/"> <i class="icon-list"></i> <span class="menu-text">广告管理</span></a></li>
		<li ${cmpName=="cp"?activate:""}><a href="/cp/"> <i class="icon-list-alt"></i> <span class="menu-text">CP管理</span></a></li>
		<li ${cmpName=="app"?activate:""}><a href="/app/"> <i class="icon-list-alt"></i> <span class="menu-text">游戏管理</span></a></li>
		<li ${cmpName=="adsStat"?activate:""}><a href="/adsStat/"> <i class="icon-list-alt"></i> <span class="menu-text">数据统计</span></a></li>
		<li ${cmpName=="setting"?activate:""}><a href="/setting/"> <i class="icon-list-alt"></i> <span class="menu-text">设置</span> </a></li>
	</ul>
	<div class="sidebar-collapse" id="sidebar-collapse"> <i class="icon-double-angle-left"></i></div>
</div>

	