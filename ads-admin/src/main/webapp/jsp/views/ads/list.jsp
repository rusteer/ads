<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li><i class="icon-home home-icon"></i><a href="/${cmpName}/${cp.id}">广告列表</a> <span class="divider"> <i class="icon-angle-right arrow-icon"></i>
		</span></li>
	</ul>
</div>
<div class="page-content">
	<div class="row-fluid">
		<div class="span12">
			<div class="table-toolbar">
				<form class="form-inline">
					<a href="javascript:showForm()" class="btn btn-small btn-success">新增广告</a>
					<!--  -->
					<select class="input-large" id="advertiser">
						<option value="">选择广告主</option>
						<c:forEach var="element" items="${advertiserList}">
							<option value="${element.id}" ${param.advertiserId==element.id?"selected":""}>${element.name}</option>
						</c:forEach>
					</select>
				</form>
			</div>
			<div class="hr"></div>
			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover" id="example2">
				<thead>
					<tr>
						<th>ID</th>
						<th>广告主</th>
						<th>数据</th>
						<th>价格</th>
						<th>别名</th>
						<th>名称</th>
						<th>包名</th>
						<th>可安装</th>
						<th>相关链接</th>
						<th>横幅</th>
						<th>弹窗</th>
						<th>推送</th>
						<th>快捷方式</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="entity" items="${list}">
						<tr class='odd gradeX ${entity.enabled?"text-success":"text-error"}'>
							<td><a href="/${cmpName}/${entity.id}">${entity.id}</a></td>
							<td>
								<c:forEach var="element" items="${advertiserList}">
										<c:if test="${entity.advertiserId==element.id}">
											<a href="/advertiser/${element.id}">${element.name}</a>
										</c:if>
								</c:forEach>
							</td>
							<td><a href="/adsStat/?adsId=${entity.id}">查看</a></td>
							<td>${entity.price}</td>
							<td>${entity.alias}</td>
							<td>${entity.name}</td>
							
							<td>${entity.packageName}</td>
							<td>${entity.installable?"是":""}</td>
							<td><a href="${entity.url}" target="_blank">下载</a> <a href="${entity.iconUrl}" target="_blank">图标</a> 
							 <a href="${entity.screenUrl}" target="_blank">截图</a></td>
							<td><c:if test="${entity.banner.enabled}">${entity.banner.startHour}|${entity.banner.endHour}|${entity.banner.interval}|${entity.banner.maxCount}</c:if></td>
							<td><c:if test="${entity.pop.enabled}">${entity.pop.startHour}|${entity.pop.endHour}|${entity.pop.interval}|${entity.pop.maxCount}</c:if></td>
							<td><c:if test="${entity.push.enabled}">${entity.push.startHour}|${entity.push.endHour}|${entity.push.interval}|${entity.push.maxCount}</c:if></td>
							<td><c:if test="${entity.shortcut.enabled}">${entity.shortcut.startHour}|${entity.shortcut.endHour}|${entity.shortcut.interval}|${entity.shortcut.maxCount}</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script>
	var showForm=function(){
		var advertiserId = $("#advertiser").val();
		window.location.href = "/${cmpName}/0/?advertiserId=" + advertiserId;
	}
	var refreshList = function() {
		var advertiserId = $("#advertiser").val();
		window.location.href = "/${cmpName}/?advertiserId=" + advertiserId;
	}
	$(function() {
		$("#advertiser").change(function() {
			refreshList();
		});
	});
</script>